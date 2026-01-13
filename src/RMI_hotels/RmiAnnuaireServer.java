import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Usage:
 *   java RmiAnnuaireServer <MY_IP> [count]
 * Example:
 *   java RmiAnnuaireServer 192.168.1.10 100
 */
public class RmiAnnuaireServer extends UnicastRemoteObject implements RmiServiceForward {

    private final Map<String,String> directory;

    protected RmiAnnuaireServer(Map<String,String> directory) throws Exception {
        super();
        this.directory = directory;
    }

    @Override
    public java.util.List<String> getHotels() {
        return new java.util.ArrayList<>();
    }

    @Override
    public Map<String, String> getDirectory() {
        return new HashMap<>(directory);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java RmiAnnuaireServer <MY_IP> [count]");
            System.exit(1);
        }
        try {
            String myIp = args[0];
            int count = args.length >= 2 ? Integer.parseInt(args[1]) : 100;

            Map<String,String> dir = new HashMap<>(count);
            for (int i = 0; i < count; i++) {
                String name = "Hotel_Toulouse_" + i;
                // same as celle d'agent
                dir.put(name, "06.00.00." + i);
            }

            System.setProperty("java.rmi.server.hostname", myIp);

            // port 3000
            LocateRegistry.createRegistry(3000);

            RmiAnnuaireServer srv = new RmiAnnuaireServer(dir);
            Naming.rebind("//" + myIp + ":3000/AnnuaireService", srv);

            System.out.println("Annuaire RMI Server running on port 3000, entries: " + count);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
