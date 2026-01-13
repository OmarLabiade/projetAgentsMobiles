package agent;

import java.util.ArrayList;
import java.util.HashMap;

public class StartServer {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java agent.StartServer <PORT> <TYPE>");
            System.out.println("TYPE: HOTELS | ANNUAIRE | FILE");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String type = args[1];
        AgentServer server = new AgentServer(port);

        if (type.equalsIgnoreCase("HOTELS")) {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) data.add("Hotel_Toulouse_" + i);
            server.addService("Hotels", data);
        }
        else if (type.equalsIgnoreCase("ANNUAIRE")) {
            HashMap<String, String> data = new HashMap<>();
            for (int i = 0; i < 100; i++) data.put("Hotel_Toulouse_" + i, "06.00.00." + i);
            server.addService("Annuaire", data);
        }
        else if (type.equalsIgnoreCase("FILE")) {
            byte[] bigData = new byte[1024 * 1024 * 1000];
            server.addService("bigfile", bigData);
        }

        server.start();
    }
}