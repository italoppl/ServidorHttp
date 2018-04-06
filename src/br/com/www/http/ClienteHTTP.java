package br.com.www.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHTTP {
	
	private static final int PORTA_HTTP_PADRAO = 80;
	private static final String HTTP_VERSION = "HTTP/1.1";
	private static PrintWriter saida;
	
	public static void main(String [] args)
	{
		try
		{
			String host = "cesed.br";
			String path = " /portal";
			Socket socket = new Socket(host, PORTA_HTTP_PADRAO);
			saida = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader entrada = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			//Enviando requisão
			sendData("GET" + path + " " + HTTP_VERSION);
			sendData("Host:" + host);
			sendData("Connection: keep-alive");
			sendData("");
			
			//recebendo resposta
			 
			boolean loop = true;
			StringBuffer resposta = new StringBuffer();
			do{
				if(entrada.ready())
				{
					int i = 0;
					while((i = entrada.read()) != -1)
					{
						resposta.append((char) i);
					}
					loop = false;
				}
			}while (loop);
			System.out.println(resposta.toString());
			socket.close();
			System.out.println("FIM");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	private static void sendData(String data)
	{	saida.println(data);
		System.out.println(data);
	}	

}
