package com.wlw.EnvironmentTest.com.cn;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class sendMessage {
	public int dlgIndex = 0;
	MainActivity mainactivity = new MainActivity();
		// TODO Auto-generated constructor stub
	public void MySend(String message){
		try{		    	    
	    	PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(mainactivity.socket.getOutputStream())),true); 
		    out.println(message);
		    out.flush();
		}catch(Exception e){
	   		e.printStackTrace();
		}
	}
}
