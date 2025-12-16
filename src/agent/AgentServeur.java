package agent;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class AgentServeur {
    private int port;
    private Hashtable<String, Object> services = new Hashtable<>();
    public AgentServeur(int port) {
        this.port = port;
    }
    public void addService(String name, Object service) {
        services.put(name, service);
    }
    public void start() throws IOException {
        try{
            ServerSocket server = new ServerSocket(port);
            System.out.println(" Serveur pret sur le port " + port);
            while(true){

                Socket client = server.accept();
                DataInputStream dis = new DataInputStream(client.getInputStream());
                String className = dis.readUTF();
                int taille = dis.readInt();
                byte[] code = new byte[taille];
                dis.readFully(code);

                AgentClassLoader loader = new AgentClassLoader(className, code);

                AgentInputStream ais = new AgentInputStream(client.getInputStream(),loader);
                Agent agent = (Agent) ais.readObject();

                agent.setNameServer(this.services);
                new Thread(agent::main).start();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
