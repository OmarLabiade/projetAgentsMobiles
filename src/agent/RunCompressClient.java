package agent;

public class RunCompressClient {
    public static long startTime;

    public static void main(String[] args) {
        String serverHost = args[0];
        String myHost = args[1];
        try {
            new Thread(() -> new AgentServer(6000).start()).start();
            Thread.sleep(1000);

            CompressAgent agent = new CompressAgent();
            agent.init("Compress", new Node(myHost, 6000));
            agent.setInfo(new Node(serverHost, 5000), "bigfile");

            System.out.println("Lancement de l'agent vers " + serverHost + ":5000");

            startTime = System.currentTimeMillis();
            agent.main();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}