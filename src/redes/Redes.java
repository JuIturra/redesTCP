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
            servidor = new ServerSocket(8400); //socket del servidor
            sock = new Socket("127.0.0.1",8500); //socket del cliente
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Socket clientSock = servidor.accept(); //socket de conexion para el servidor
        String peticion_HTTP = "GET / HTTP/1.1\r\nHost: "+"127.0.0.1"+":"+"8500"+"\r\nConnection: keep-alive\r\n\r\n";
        //peticion = new DataOutputStream(sock.getOutputStream());
        //peticion.writeBytes(peticion_HTTP);
        //peticion.flush();
        if(clientSock.isConnected()) System.out.println("esta conectado");
        
        Boolean run = true;
        while(run){
            System.out.println("Enviando peticion");
            peticion = new DataOutputStream(sock.getOutputStream());
            peticion.writeBytes(peticion_HTTP);
            peticion.flush();
            System.out.println("Trafico de datos");
            input = new DataInputStream(sock.getInputStream());
            int tamaño = input.read(datos,0,datos.length);
            output = new DataOutputStream(clientSock.getOutputStream());
            output.write(datos,0,tamaño);
        }
        sock.close();
        clientSock.close();
        servidor.close();
    } 
} 