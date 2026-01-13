package agent;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer extends UnicastRemoteObject implements RmiService {
    private byte[] bigFile = new byte[1024 * 1024 * 1000];

    public RmiServer() throws Exception { super(); }

    @Override
    public byte[] downloadFile(String filename) {
        return bigFile; // On renvoie tout le paquet (LOURD !)
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Port standard RMI
            Naming.rebind("FileService", new RmiServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}