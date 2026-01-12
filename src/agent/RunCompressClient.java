package agent;

import java.net.InetAddress;

public class RunCompressClient {
    public static void main(String[] args) {
        String serverHost = "localhost";
        if (args.length > 0) serverHost = args[0];

        try {
            new Thread(() -> new AgentServer(6000).start()).start();
            Thread.sleep(1000);

            CompressAgent agent = new CompressAgent();
            String myIp = InetAddress.getLocalHost().getHostAddress();
            agent.init("Compress", new Node(myIp, 6000));
            agent.setInfo(new Node(serverHost, 5000), "bigfile");

            System.out.println("Lancement de l'agent vers " + serverHost + ":5000");
            agent.main();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}