package redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Redes {

    public static void main(String[] args) throws IOException {
        //se crean los socket
        ServerSocket servidor = null;
        Socket sock = null;
        try{
            servidor = new ServerSocket(8500);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Socket clientSock = null;
        String peticion_HTTP = "GET / HTTP/1.1\r\nHost: "+"192.168.1.2"+":"+"8400"+"\r\nConnection: keep-alive\r\n\r\n";
        
        while(true){
            try{
                sock = new Socket("192.168.1.2",8080);
                clientSock = servidor.accept();
                final Socket fin = clientSock;
                Thread trd = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try{
                            Thread.sleep(5000);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                        DataInputStream input = null;
                        DataOutputStream output = null;
                        
                        try {
                            output = new DataOutputStream(fin.getOutputStream());
                        } catch (IOException ex) {
                            Logger.getLogger(Redes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            output.writeBytes(peticion_HTTP);
                        } catch (IOException ex) {
                            Logger.getLogger(Redes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            output.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(Redes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //entrada
                        
                    }
                });
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    } 
} 