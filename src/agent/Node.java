package agent;
import java.io.Serializable;

public class Node implements Serializable {
    private String host;
    private int port;
    public Node(String h, int p) { host=h; port=p; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public String toString() { return host + ":" + port; }
}