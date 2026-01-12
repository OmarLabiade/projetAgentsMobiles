package agent;

import java.util.ArrayList;
import java.util.HashMap;

public class ForwardAgent extends AgentImpl {
    private int step = 0;
    private Node destA;
    private Node destB;
    private ArrayList<String> hotels;
    private HashMap<String, String> results = new HashMap<>();

    public void setRoute(Node a, Node b) {
        this.destA = a;
        this.destB = b;
    }

    @Override
    public void main() {
        if (step == 0) {
            System.out.println("Depart vers Serveur A.");
            step = 1;
            move(destA);
        }
        else if (step == 1) {
            System.out.println("Arrivee sur A pour ecuperation liste.");
            this.hotels = (ArrayList<String>) getNameServer().get("Hotels");
            step = 2;
            move(destB);
        }
        else if (step == 2) {
            System.out.println("Arrivee sur B pour recherche numeros.");
            HashMap<String, String> directory = (HashMap<String, String>) getNameServer().get("Annuaire");

            if (hotels != null && directory != null) {
                for (String h : hotels) {
                    String num = directory.get(h);
                    if (num == null) num = "Inconnu";
                    results.put(h, num);
                }
            }
            step = 3;
            back();
        }
        else if (step == 3) {
            System.out.println("Resultat");
            System.out.println(results);
        }
    }
}