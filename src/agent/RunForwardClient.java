package agent;

import java.io.IOException;
import java.net.InetAddress;

public class RunForwardClient {
    public static void main(String[] args) {
        String hostA = args[0];
        String hostB = args[1];

        try {
            new Thread(() -> {
                try {
                    new AgentServer(4000).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            Thread.sleep(1000);

            System.out.println("Lancement ForwardAgent vers " + hostA + " et " + hostB);

            ForwardAgent agent = new ForwardAgent();
            String myIp = InetAddress.getLocalHost().getHostName();

            agent.init("JamesBond", new Node(myIp, 4000));
            agent.setRoute(new Node(hostA, 2000), new Node(hostB, 3000));
            agent.main();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}