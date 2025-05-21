// src/com/clinica/server/ServerMain.java
package com.clinica.server;
import com.clinica.rmi.MedicalService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
  public static void main(String[] args) {
    try {
      LocateRegistry.createRegistry(1099);
      MedicalService service = new MedicalServiceImpl();
      Registry reg = LocateRegistry.getRegistry();
      reg.rebind("MedService", service);
      System.out.println("Servidor RMI listo en puerto 1099");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
