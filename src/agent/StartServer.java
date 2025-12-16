package agent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class StartServer {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java agent.StartServer <PORT> <TYPE>");
            System.out.println("Types: HOTELS | ANNUAIRE | FILE");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String type = args[1];

        AgentServer server = new AgentServer(port);

        if (type.equalsIgnoreCase("HOTELS")) {
            ArrayList<String> h = new ArrayList<>();
            for(int i=0; i<100; i++) h.add("Hotel_Num_" + i);
            server.addService("Hotels", h);
        }
        else if (type.equalsIgnoreCase("ANNUAIRE")) {
            HashMap<String, String> annuaire = new HashMap<>();
            for(int i=0; i<100; i++) annuaire.put("Hotel_Num_" + i, "06.00.00." + i);
            server.addService("Annuaire", annuaire);
        }
        else if (type.equalsIgnoreCase("FILE")) {
            byte[] data = new byte[1024 * 1024 * 10];
            server.addService("bigfile", data);
        }

        server.start();
    }
}