package redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Redes {

    public static void main(String[] args) throws IOException {
        //se crean los socket
        ServerSocket servidor = null;
        Socket sock = null;
        byte[] datos = new byte[2048];
        DataInputStream input = null;
        DataOutputStream output = null;
        DataOutputStream peticion = null;
        
        try{
            servidor = new ServerSocket(8400); //socket receptor en puerto 8400
            sock = new Socket("ip_origen_video",8500); //socket del origen en puerto 8500
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Socket clientSock = servidor.accept(); //socket de conexion para el servidor
        String peticion_HTTP = "GET / HTTP/1.1\r\nHost: "+"ip_origen_video"+":"+"Puerto"+"\r\nConnection: keep-alive\r\n\r\n";
        peticion = new DataOutputStream(sock.getOutputStream());
        peticion.writeBytes(peticion_HTTP);
        peticion.flush();
        
        System.out.println("Enviando peticion");
        if(clientSock.isConnected()) System.out.println("esta conectado");
        input = new DataInputStream(sock.getInputStream());
        output = new DataOutputStream(clientSock.getOutputStream());
        boolean run = true;
        while(run){
            System.out.println("Datos recibidos");
            int tamaño = input.read(datos,0,datos.length);            
            output.write(datos,0,tamaño);
            output.flush();
        }
        sock.close();
        clientSock.close();
        servidor.close();
    } 
} 