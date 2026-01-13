import java.rmi.Naming;

public class RmiClient {
    public static void main(String[] args) {
        try {
            String ip = args[0];

            long start = System.currentTimeMillis();

            RmiService service =
              (RmiService) Naming.lookup("//" + ip + ":1099/FileService");

            byte[] compressed = service.compressFile("bigfile");

            long end = System.currentTimeMillis();

            System.out.println("Temps RMI : " + (end - start) + " ms");
            System.out.println("Taille compressée : " + compressed.length + " octets");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
