import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Usage:
 *   java RmiHotelsServer <MY_IP> [count]
 * Example:
 *   java RmiHotelsServer 192.168.1.10 100
 */
public class RmiHotelsServer extends UnicastRemoteObject implements RmiServiceForward {

    private final List<String> hotels;

    protected RmiHotelsServer(List<String> hotels) throws Exception {
        super();
        this.hotels = hotels;
    }

    @Override
    public List<String> getHotels() {
        return new ArrayList<>(hotels);
    }

    @Override
    public java.util.Map<String, String> getDirectory() {
        return new java.util.HashMap<>();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java RmiHotelsServer <MY_IP> [count]");
            System.exit(1);
        }
        try {
            String myIp = args[0];
            int count = args.length >= 2 ? Integer.parseInt(args[1]) : 100;

            // Hotel_Toulouse_i
            List<String> data = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                data.add("Hotel_Toulouse_" + i);
            }

            System.setProperty("java.rmi.server.hostname", myIp);

            // port 2000
            LocateRegistry.createRegistry(2000);

            RmiHotelsServer srv = new RmiHotelsServer(data);
            Naming.rebind("//" + myIp + ":2000/HotelsService", srv);

            System.out.println("Hotels RMI Server running on port 2000, entries: " + count);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
