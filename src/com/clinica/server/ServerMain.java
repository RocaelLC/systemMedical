package com.clinica.server;
import com.clinica.rmi.MedicalService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            MedicalService impl = new MedicalServiceImpl();
            Registry reg = LocateRegistry.getRegistry();
            reg.rebind("MedService", impl);
            System.out.println("Servidor RMI listo en puerto 1099");
        } catch (RemoteException e) {
        }
    }
}
