import java.rmi.Naming;
import java.util.Map;

public class RmiClientForward {

    public static void main(String[] args) {
        try {
            String hotelsIP = args[0];
            String annuaireIP = args[1];

            long start = System.currentTimeMillis();

            HotelsService hotels =
              (HotelsService) Naming.lookup("//" + hotelsIP + ":2000/HotelsService");

            // Single remote call — exactly like mobile-agent return
            Map<String,String> result = hotels.getMergedResult(annuaireIP);

            long end = System.currentTimeMillis();

            System.out.println("Resultat:");
            System.out.println(result);
            System.out.println("Elapsed time (ms): " + (end-start));

        } catch(Exception e){ e.printStackTrace(); }
    }
}
