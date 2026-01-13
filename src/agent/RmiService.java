package agent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiService extends Remote {
    public byte[] downloadFile(String filename) throws RemoteException;
}