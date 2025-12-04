package agent;
import java.io.Serializable;
import java.util.Hashtable;

public interface Agent extends Serializable {
    public void init(String name, Node origin);
    public void setNameServer(Hashtable<String, Object> ns);
    public Hashtable<String, Object> getNameServer();
    public void move(Node target);
    public void back();
    public void main();
}