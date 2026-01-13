import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface HotelsService extends Remote {
    Map<String,String> getMergedResult(String annuaireIP) throws RemoteException;
}
