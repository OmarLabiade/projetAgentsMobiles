package agent;

import java.io.*;
import java.util.Hashtable;
import java.net.Socket;

public abstract class AgentImpl implements Agent {

    private String name;
    private Node origin;
    private transient Hashtable<String, Object> serverData;

    @Override
    public void init(String name, Node origin) {
        this.name = name;
        this.origin = origin;
    }

    @Override
    public void setNameServer(Hashtable<String, Object> ns) {
            this.serverData = ns;
    }

    @Override
    public Hashtable<String, Object> getNameServer() {
        return serverData;
    }

    @Override
    public void move(Node target) {
        try{
            String className = target.getClass().getName();
            String classFile = className.replace('.', '/') + ".class";
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(classFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int nRead;
            while ((nRead = is.read(buffer, 0, buffer.length)) != -1){
                baos.write(buffer, 0, nRead);
            }
            byte[] code = baos.toByteArray();

            Socket socket = new Socket(target.getHost(), target.getPort());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(className);
            dos.writeInt(code.length);
            dos.write(code);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(this);
            socket.close();
            Thread.currentThread().stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void back() {
        move(origin);
    }
}
