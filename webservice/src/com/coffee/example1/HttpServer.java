package com.coffee.example1;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	/**
	 * WEB_ROOT is the directory where our html and other files reside.
	 * For this package,WEB_ROOT is the "webroot" directory under the 
	 * working directory.
	 * the working directory is the location in the file system
	 * from where the java command was invoke.
	 */
	
	public static final String WEB_ROOT = System.getProperty("user.dir")+File.separator+"WebRoot";
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	
	public static void main(String[] args) throws IOException {
		HttpServer server = new HttpServer();
//		server.await();
		server.await1();
	}
	
	public void await() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		while(!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				//create Request object and parse
				Request request = new Request(input);
				request.parse();
				
				//create Response object
				Response reqsponse = new Response(output);
				reqsponse.setRequest(request);
				reqsponse.sendStaticResource();
			}catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public void await1() throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
		Socket socket = serverSocket.accept();
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();

		System.out.println("inputStream:");
		BufferedReader bd = new BufferedReader(new InputStreamReader(inputStream));
		/**
		 * 接受HTTP请求，并解析数据
		 */
		String requestHeader;
		int contentLength = 0;
		while ((requestHeader = bd.readLine()) != null && !requestHeader.isEmpty()) {
			System.out.println(requestHeader);
			/**
			 * 获得GET参数
			 */
			if (requestHeader.startsWith("GET")) {
				int begin = requestHeader.indexOf("/?") + 2;
				int end = requestHeader.indexOf("HTTP/");
				String condition = requestHeader.substring(begin, end);
				System.out.println("GET参数是：" + condition);
			}
			/**
			 * 获得POST参数
			 * 1.获取请求内容长度
			 */
			if (requestHeader.startsWith("Content-Length")) {
				int begin = requestHeader.indexOf("Content-Length:") + "Content-Length:".length();
				String postParamterLength = requestHeader.substring(begin).trim();
				contentLength = Integer.parseInt(postParamterLength);
				System.out.println("POST参数长度是：" + Integer.parseInt(postParamterLength));
			}
		}
		StringBuffer sb = new StringBuffer();
		if (contentLength > 0) {
			for (int i = 0; i < contentLength; i++) {
				sb.append((char) bd.read());
			}
			System.out.println("POST参数是：" + sb.toString());
		}

		System.out.println("outputStream:");

		byte[] bytes = "HelloWorld".getBytes();
		int len = bytes.length;
		String http = "HTTP/1.1 200\r\n" + "Content-Type: text/html\r\n" + "Content-Length: " + len + "\r\n" + "\r\n";
		outputStream.write(http.getBytes());

		outputStream.write(bytes, 0, len);
		outputStream.flush();
		outputStream.close();
		bd.close();

	}
}
