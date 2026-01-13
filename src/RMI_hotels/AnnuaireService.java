import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface AnnuaireService extends Remote {
    Map<String,String> getDirectory() throws RemoteException;
}
