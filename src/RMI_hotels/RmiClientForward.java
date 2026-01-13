import java.rmi.Naming;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RmiClientForward {

    public static void main(String[] args) {

        try {
            String ipHotels = args[0];
            String ipAnnuaire = args[1];

            long start = System.currentTimeMillis();

            // Sff
            System.out.println("Arrivee sur A pour recuperation liste");
            RmiServiceForward hotelsSrv =
                (RmiServiceForward) Naming.lookup("//" + ipHotels + ":2000/HotelsService");
            List<String> listeHotels = hotelsSrv.getHotels();


            System.out.println("Arrivee sur B pour recuperation annuaire");
            RmiServiceForward annuaireSrv =
                (RmiServiceForward) Naming.lookup("//" + ipAnnuaire + ":3000/AnnuaireService");
            Map<String,String> annuaire = annuaireSrv.getDirectory();


            Map<String,String> result = new HashMap<>();
            for (String h : listeHotels) {
                result.put(h, annuaire.get(h));
            }

            long end = System.currentTimeMillis();


            System.out.println("Resultat:");
            System.out.println(result);
            System.out.println("Elapsed time (ms): " + (end - start));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
