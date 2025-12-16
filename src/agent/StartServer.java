package agent;

import java.util.ArrayList;
import java.util.HashMap;

public class StartServer {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java agent.StartServer <PORT> <TYPE>");
            System.out.println("Types: HOTELS | ANNUAIRE | FILE");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String type = args[1];

        AgentServer server = new AgentServer(port);

        // CONFIGURATION AUTOMATIQUE SELON LE RÔLE
        if (type.equalsIgnoreCase("HOTELS")) {
            ArrayList<String> h = new ArrayList<>();
            // On génère 100 hôtels fictifs
            for(int i=0; i<100; i++) h.add("Hotel_Num_" + i);
            server.addService("Hotels", h);
            System.out.println("SERVEUR HOTELS PRET (Port " + port + ")");
        }
        else if (type.equalsIgnoreCase("ANNUAIRE")) {
            HashMap<String, String> annuaire = new HashMap<>();
            for(int i=0; i<100; i++) annuaire.put("Hotel_Num_" + i, "06.00.00." + i);
            server.addService("Annuaire", annuaire);
            System.out.println("SERVEUR ANNUAIRE PRÊT (Port " + port + ")");
        }
        else if (type.equalsIgnoreCase("FILE")) {
            byte[] data = new byte[1024 * 1024 * 10];
            server.addService("bigfile", data);
            System.out.println("SERVEUR FICHIER PRÊT (Port " + port + ")");
        }

        server.start();
    }
}