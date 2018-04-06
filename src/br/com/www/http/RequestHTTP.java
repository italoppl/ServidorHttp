package br.com.www.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RequestHTTP implements Runnable{
	private Socket socket;
	private String metodo;
	private String recurso;
	private String versaoHttp;
	private Map<String, String> parametros;
	private String primeiraLinha;

	public RequestHTTP(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() 
	{
	    InputStream input;
		try {
			input = socket.getInputStream();
		    int letra = 0;
		    int cont = 0;
		    String linha;
		    
		    StringBuilder builder = new StringBuilder();
		    Map<String, String> map = new HashMap<>();
		    
		    while((letra = input.read()) >= 0){
		    	builder.append((char) letra);
		    	if(letra != 13 && letra != 10 && cont == 1){
			    	builder.append((char) letra);
		    	}else if(letra == 10 || letra == 13 && cont == 0){
			   		this.primeiraLinha = builder.toString();
			   		cont++;
			   		if(cont == 1){
			   			String auxLinha = primeiraLinha;
			   	        String[] valores = auxLinha.split("/");
			   	        this.metodo = valores[0];
			   	        this.recurso = valores[1];
			   	        this.versaoHttp = valores[2];
			   		}
				    builder.delete(0, primeiraLinha.length()-1);
		    	}else if(letra == 10 || letra == 13 && cont > 1){
	   				linha = builder.toString();
		   			if(linha.contains(":")) {
			   			String[] valores = linha.split(":", 2);
			   			String chave = valores[0];
			   			String valor = valores[1];
			   			map.put(chave, valor);
		   			}
		   		
		    	}
		    } 
		    setParametros(map);
		    System.out.println(toString());
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String trataPrimeiraLinhaRequest(String linha) {
		return linha;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getVersaoHttp() {
		return versaoHttp;
	}

	public void setVersaoHttp(String versaoHttp) {
		this.versaoHttp = versaoHttp;
	}

	public Map<String, String> getParametos() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametos) {
		this.parametros = parametos;
	}
	
	public String toString() {
		String req ="Metodo: " + this.metodo + "/" + " Recurso: " + this.recurso + "/" + " Versão: " +this.versaoHttp + "\n";
				
		Iterator<String> it = parametros.keySet().iterator();

		while (it.hasNext()) {
			String chave = it.next(); 
			req += chave + " : " + parametros.get(chave);
		}	
		return req;
		
	}
}
