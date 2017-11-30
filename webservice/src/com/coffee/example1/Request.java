package com.coffee.example1;

import java.io.IOException;

/*import java.io.InputStream;

public class Request {
	private InputStream input;
	private String uri;
	
//	只有一个带参构造。所以Request的对象的input变量是通过外部初始化的
	public Request(InputStream input) {
		this.input = input;
		parse();
	}
	
	*//**
	 * 调用该方法前必定已存在了Request对象，且input变量有值了。
	 * 此方法是对input进行解析转换
	 *//*
	private void parse() {
		//Read a set of characters from the socket
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			//把input中的内容读取到buffer数组中
			i = input.read(buffer);
		}catch(Exception e) {
			e.printStackTrace();
			i=-1;
		}
		//把buffer数组中的数据放入request字符串中。
		for(int j=0;j<i;j++) {
			request.append((char)buffer[j]);
		}
		System.out.println(request.toString());
		uri=parseUri(request.toString());
	}
	
	*//**
	 * 解析request字符转中的uri并返回
	 * @param requestString
	 * @return
	 *//*
	private String parseUri(String requestString) {
		int index1,index2;
		index1=requestString.indexOf(" ");
		if(index1 != -1) {
			index2 = requestString.indexOf(" ",index1+1);
			if(index2>index1) {
				return requestString.substring(index1+1,index2);
			}
		}
		return null;
	}
	
	public String getUri() {
		return this.uri;
	}
}*/

import java.io.InputStream;  

public class Request {  
    private InputStream input;  
      
    private String uri;  
      
    public Request(InputStream input){  
        this.input=input;  
    }  
      
    public void parse(){  
        //Read a set of characters from the socket  
        StringBuffer request=new StringBuffer(2048);  
        int i;  
        byte[] buffer=new byte[2048];  
        try {  
            i=input.read(buffer);  
        } catch (Exception e) {  
            e.printStackTrace();  
            i=-1;  
        }
        for(int j=0;j<i;j++){  
            request.append((char)buffer[j]);  
        }  
        System.out.print(request.toString());  
        uri=parseUri(request.toString());  
    }  
      
    public String parseUri(String requestString){  
        int index1,index2;  
        index1=requestString.indexOf(" ");  
        if(index1!=-1){  
            index2=requestString.indexOf(" ",index1+1);  
            if(index2>index1){  
                return requestString.substring(index1+1,index2);  
            }  
        }  
        return null;  
    }  
      
    public String getUri(){  
        return this.uri;  
    }  
}
