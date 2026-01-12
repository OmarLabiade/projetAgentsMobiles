package agent;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class AgentServer {
    private int port;
    private Hashtable<String, Object> services = new Hashtable<>();

    public AgentServer(int port) {
        this.port = port;
    }

    public void addService(String key, Object value) {
        this.services.put(key, value);
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Serveur demarre sur le port " + port);

            while (true) {
                Socket client = server.accept();

                DataInputStream dis = new DataInputStream(client.getInputStream());
                String className = dis.readUTF();
                int length = dis.readInt();
                byte[] bytecode = new byte[length];
                dis.readFully(bytecode);

                AgentClassLoader cl = new AgentClassLoader(className, bytecode);
                AgentInputStream ais = new AgentInputStream(client.getInputStream(), cl);

                Agent agent = (Agent) ais.readObject();
                agent.setNameServer(this.services);

                new Thread(() -> agent.main()).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}