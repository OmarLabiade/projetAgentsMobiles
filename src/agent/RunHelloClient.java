package agent;

import java.net.InetAddress;

public class RunHelloClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java agent.RunHelloClient <IP_Cible> <Port_Cible>");
            return;
        }

        String targetHost = args[0];
        int targetPort = Integer.parseInt(args[1]);

        try {
            new Thread(() -> new AgentServer(4444).start()).start();
            Thread.sleep(500);

            HelloAgent agent = new HelloAgent();
            String myIp = InetAddress.getLocalHost().getHostAddress();
            agent.init("Hello", new Node(myIp, 4444));

            System.out.println("Lancement de HelloAgent vers " + targetHost + ":" + targetPort);
            agent.move(new Node(targetHost, targetPort));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}