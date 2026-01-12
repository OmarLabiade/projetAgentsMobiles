package agent;

import java.io.*;
import java.net.Socket;
import java.util.Hashtable;

public abstract class AgentImpl implements Agent {
    private String name;
    private Node origin;
    private transient Hashtable<String, Object> serverData;

    public void init(String name, Node origin) {
        this.name = name;
        this.origin = origin;
    }

    public void setNameServer(Hashtable<String, Object> ns) {
        this.serverData = ns;
    }

    public Hashtable<String, Object> getNameServer() {
        return serverData;
    }

    public void move(Node target) {
        try {
            String className = this.getClass().getName();
            String path = className.replace('.', '/') + ".class";
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            byte[] bytecode = buffer.toByteArray();

            Socket s = new Socket(target.getHost(), target.getPort());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            dos.writeUTF(className);
            dos.writeInt(bytecode.length);
            dos.write(bytecode);

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(this);

            s.close();
            Thread.currentThread().stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back() {
        move(origin);
    }
}