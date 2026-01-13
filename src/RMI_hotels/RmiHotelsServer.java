import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RmiHotelsServer extends UnicastRemoteObject implements HotelsService {

    private final Map<String,String> hotels;

    public RmiHotelsServer(int count) throws Exception {
        hotels = new HashMap<>();
        for(int i=0;i<count;i++){
            hotels.put("Hotel_Toulouse_" + i, ""); 
        }
    }

    @Override
    public Map<String,String> getMergedResult(String annuaireIP) {
        try {
            // Connect to Annuaire server internally (server-to-server call)
            AnnuaireService annuaire =
              (AnnuaireService) Naming.lookup("//" + annuaireIP + ":3000/AnnuaireService");

            Map<String,String> directory = annuaire.getDirectory();

            Map<String,String> result = new HashMap<>();
            for(String h : hotels.keySet()) {
                result.put(h, directory.get(h));
            }
            return result;

        } catch(Exception e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void main(String[] args) {
        try {
            String myIP = args[0];
            int count = Integer.parseInt(args[1]);

            System.setProperty("java.rmi.server.hostname", myIP);
            LocateRegistry.createRegistry(2000);

            RmiHotelsServer srv = new RmiHotelsServer(count);
            Naming.rebind("//" + myIP + ":2000/HotelsService", srv);

            System.out.println("Hotels Server running on port 2000, entries: "+count);

        } catch(Exception e){ e.printStackTrace(); }
    }
}
