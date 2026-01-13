import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class RmiServer extends UnicastRemoteObject implements RmiService {

    private byte[] data; 

    public RmiServer(int sizeMB) throws Exception {
        data = new byte[sizeMB * 1024 * 1024];
        System.out.println("Bigfile created: " + data.length + " bytes");
    }

    @Override
    public byte[] compressFile(String filename) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(data);
            gzip.close();
            return baos.toByteArray();
        } catch(Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static void main(String[] args) {
        try {
            String myIP = args[0];
            int sizeMB = Integer.parseInt(args[1]);

            System.setProperty("java.rmi.server.hostname", myIP);
            LocateRegistry.createRegistry(1099);

            RmiServer server = new RmiServer(sizeMB);
            Naming.rebind("//" + myIP + ":1099/FileService", server);

            System.out.println("RMI Compression Server ready");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
