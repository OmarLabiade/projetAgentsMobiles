package agent;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiService extends Remote {
    // Le client veut récupérer le fichier brut (10 Mo)
    public byte[] downloadFile(String filename) throws RemoteException;
}