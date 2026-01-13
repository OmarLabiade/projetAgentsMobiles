package agent;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer extends UnicastRemoteObject implements RmiService {
    private byte[] data = new byte[1024 * 1024 * 10];

    public RmiServer() throws Exception { super(); }

    @Override
    public byte[] downloadFile(String filename) {
        return data;
    }

    public static void main(String[] args) {
        try {

            String myIp = args[0];
            System.setProperty("java.rmi.server.hostname", myIp);

            LocateRegistry.createRegistry(1099);
            Naming.rebind("//" + myIp + ":1099/FileService", new RmiServer());

            System.out.println("Serveur RMI demarre sur : " + myIp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}