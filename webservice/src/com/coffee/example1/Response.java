package com.coffee.example1;

/*import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
*//**
 * 服务器返回的对象
 * @author coffee
 *
 *//*
public class Response {
	private static final int BUFFER_SIZE = 1024;
	Request request;
	OutputStream output;
	
	public Response(OutputStream output) {
		this.output = output;
	}
	
	public Response(OutputStream output, Request request) {
		this.output = output;
		this.request = request;
	}
	
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(HttpServer.WEB_ROOT,request.getUri());
			if(file.exists()) {
				fis = new FileInputStream(file);
				int ch = fis.read(bytes,0,BUFFER_SIZE);
				while(ch != -1) {
					output.write(bytes, 0, BUFFER_SIZE);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
			}else {
				//file not found
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n"+
				"Content-Type:text/html\r\n"+
						"Content-Length:23\r\n"+
				"\r\n"+
						"<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			//System.out.println(e.toString()); 
			e.printStackTrace();
		}finally {
			if(fis != null) {
				fis.close();
			}
		}
	}
}
*/

import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.OutputStream;  
  
/** 
 * HTTP Response = Status-Line 
 *      *(( general-header | response-header | entity-header ) CRLF)  
 *      CRLF 
 *      [message-body] 
 *      Status-Line=Http-Version SP Status-Code SP Reason-Phrase CRLF 
 * 
 */  
public class Response {  
    private static final int BUFFER_SIZE=1024;  
    Request request;  
    OutputStream output;  
      
    public Response(OutputStream output){  
        this.output=output;  
    }  
      
    public void setRequest(Request request){  
        this.request=request;  
    }  
      
    public void sendStaticResource()throws IOException{  
        byte[] bytes=new byte[BUFFER_SIZE];  
        FileInputStream fis=null;  
        try {  
            File file=new File(HttpServer.WEB_ROOT,request.getUri());  
            if(file.exists()){  
                fis=new FileInputStream(file);  
                int ch=fis.read(bytes,0,BUFFER_SIZE);  
                String http = "HTTP/1.1 200\r\n" + "Content-Type: text/html\r\n" + "Content-Length: " + file.length() + "\r\n" + "\r\n";
                output.write(http.getBytes());
                while(ch!=-1){  
                    output.write(bytes, 0, BUFFER_SIZE);  
                    ch=fis.read(bytes, 0, BUFFER_SIZE);  
                }  
                output.flush();
                output.close();
            }else{  
                //file not found  
                String errorMessage="HTTP/1.1 404 File Not Found\r\n"+  
                "Content-Type:text/html\r\n"+  
                "Content-Length:23\r\n"+  
                "\r\n"+  
                "<h1>File Not Found</h1>";  
                output.write(errorMessage.getBytes());  
                output.flush();
                output.close();
            }  
        } catch (Exception e) {  
            System.out.println(e.toString());  
        }finally{  
            if(fis!=null){  
                fis.close();  
            }  
        }  
    }  
}