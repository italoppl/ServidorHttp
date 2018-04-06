package br.com.www.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorSocket {
	
	public static void main (String [] args) throws IOException, ClassNotFoundException {

		ServerSocket servidor = new ServerSocket(7070);
	    System.out.println("Porta 7070 aberta!");  
	    
	    Socket socket = servidor.accept();

	    InputStream is = socket.getInputStream(); 
	    boolean obj = true;
		do
	    {   	
				Thread threadRequest = new Thread(new RequestHTTP(socket));
				threadRequest.start(); 
	    } while(socket.equals(true));
	    
	    
	}

}
