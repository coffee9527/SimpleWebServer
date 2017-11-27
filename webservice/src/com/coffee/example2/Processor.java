package com.coffee.example2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Processor extends Thread{
	private static final String WEB_ROOT = System.getProperty("user.dir")+File.separator+"WebRoot";
	private PrintStream out;
	private Socket socket;
	private InputStream in;
	public Processor(Socket socket) {
		this.socket = socket;
		try {
			out = new PrintStream(socket.getOutputStream());
			in = socket.getInputStream();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String parse(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String filename = "";
		try {
			String httpMessage = br.readLine();
			String[] content = httpMessage.split(" ");
			if(content.length != 3) {
				SendErrorMessage(400, "clent query error!");
				return null;
			}
			filename = content[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}
	
	public void start() {
		String filename = parse(in);
		sendFile(filename);
	}
	
	public void sendFile(String filename) {
		File file = new File(Processor.WEB_ROOT+filename);
		if(!file.exists()) {
			SendErrorMessage(404, "File Not Found");
			return;
		}
		
		try {
			InputStream in = new FileInputStream(file);
			byte content[] = new byte[(int)file.length()];
			in.read(content);
			out.println("HTTP/1.0 200 queryfile");
			out.println("Content-length:" + content.length);
			out.println();
			out.write(content);
			out.flush();
			out.close();
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SendErrorMessage(int errorCode, String errorMessage) {
		out.println("HTTP/1.0" + errorCode + " " +errorMessage);
		out.println("ContentType:text/html");
		out.println();
		out.println("<html>");
		out.println("<title>ErrorMessage</title>");
		out.println("<body>");
		out.println("<h1>ErroCode:"+errorCode+",Message:"+errorMessage+"</h1>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
}
