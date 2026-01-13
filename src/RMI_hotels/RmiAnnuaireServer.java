import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RmiAnnuaireServer extends UnicastRemoteObject implements AnnuaireService {

    private final Map<String,String> directory;

    public RmiAnnuaireServer(int count) throws Exception {
        directory = new HashMap<>();
        for(int i=0;i<count;i++){
            String name = "Hotel_Toulouse_" + i;
            directory.put(name, "06.00.00." + i);
        }
    }

    @Override
    public Map<String,String> getDirectory() {
        return new HashMap<>(directory);
    }

    public static void main(String[] args) {
        try {
            String myIP = args[0];
            int count = Integer.parseInt(args[1]);

            System.setProperty("java.rmi.server.hostname", myIP);
            LocateRegistry.createRegistry(3000);

            RmiAnnuaireServer srv = new RmiAnnuaireServer(count);
            Naming.rebind("//" + myIP + ":3000/AnnuaireService", srv);

            System.out.println("Annuaire Server running on port 3000, entries: "+count);

        } catch(Exception e){ e.printStackTrace(); }
    }
}
