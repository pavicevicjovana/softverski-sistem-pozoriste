/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.KlijentNit;

/**
 *
 * @author Jovana
 */
public class ServerNit extends Thread {

    private ServerSocket serverSocket;

    public ServerNit() {
        try {
            serverSocket=new ServerSocket(9000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        
        while(!serverSocket.isClosed()){
            try {
                Socket socket=serverSocket.accept();
                System.out.println("Klijent se povezao!");
                KlijentNit kn= new KlijentNit(socket);
                kn.start();
            } catch (Exception ex) {
               ex.printStackTrace();
            }
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    
    

    
}
