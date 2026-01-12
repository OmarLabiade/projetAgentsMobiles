package agent;

import java.net.InetAddress;

public class RunForwardClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java agent.RunForwardClient <IP_A> <IP_B>");
            return;
        }

        String hostA = args[0];
        String hostB = args[1];

        try {
            new Thread(() -> new AgentServer(4000).start()).start();
            Thread.sleep(1000);

            System.out.println("Lancement ForwardAgent vers " + hostA + " et " + hostB);

            ForwardAgent agent = new ForwardAgent();
            String myIp = InetAddress.getLocalHost().getHostAddress();
            agent.init("Hotel", new Node(myIp, 4000));
            agent.setRoute(new Node(hostA, 2000), new Node(hostB, 3000));

            agent.main();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}