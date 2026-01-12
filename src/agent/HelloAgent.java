package agent;

import java.net.InetAddress;

public class HelloAgent extends AgentImpl {
    @Override
    public void main() {
        try {
            String host = InetAddress.getLocalHost().getHostName();
            System.out.println("Notre Hello test  ! Je suis arrive sur : " + host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}