package agent;
import java.rmi.Naming;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class RmiClient {
    public static void main(String[] args) {
        try {
            String url = "rmi://localhost/FileService";
            RmiService service = (RmiService) Naming.lookup(url);

            long start = System.currentTimeMillis();

            byte[] data = service.downloadFile("bigfile");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.close();
            byte[] result = bos.toByteArray();

            long end = System.currentTimeMillis();

            System.out.println("Termine !");
            System.out.println("Temps Total RMI : " + (end - start) + " ms");
            System.out.println("Données transferees : " + data.length + " octets");

        } catch (Exception e) { e.printStackTrace(); }
    }
}