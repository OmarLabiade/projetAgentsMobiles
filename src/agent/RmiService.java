import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiService extends Remote {
    byte[] compressFile(String filename) throws RemoteException;
}
