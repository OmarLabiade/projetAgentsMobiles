import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RmiServiceForward extends Remote {
    List<String> getHotels() throws RemoteException;
    Map<String,String> getDirectory() throws RemoteException;
}
