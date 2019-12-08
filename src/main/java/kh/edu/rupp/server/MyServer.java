package kh.edu.rupp.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Chhai Chivon on Dec 8, 2019
 * Senior Software Developer
 */

public class MyServer {

	private static OutputStreamWriter osw;
	private static Scanner sn;
	
	public static void main(String[] args) {
		try {
			ServerSocket ss  = new ServerSocket(1234);
			while(true) {
				Socket s = ss.accept();
				
				osw = new OutputStreamWriter(s.getOutputStream());
				sn  = new Scanner(s.getInputStream());
				while(sn.hasNext()) {
					String reqStr = sn.nextLine();
					requestFromClient(reqStr);
				}
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void responseToClient(String res) {
		try {
			osw.write(res+"\n");
			osw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void requestFromClient(String req) {
		String res = "";
		//sum#=>1,2<=
		String reqArr[] =  req.split("#");
		String reqType = reqArr[0];
		String reqVal = reqArr[1];
		if(reqType.equals("sum")) {
			String reqValArr[] = reqVal.split(",");
			int val1 = Integer.parseInt(reqValArr[0]);
			int val2 = Integer.parseInt(reqValArr[1]);
			String total = sum(val1,val2);
			res = "ok#=>"+total+"<=";
		}else {
			//invalid request#
			res = "invalid request#";
		}
		responseToClient(res);
	}
	
	private static String sum(int val1 , int val2) {
		int total = val1 + val2;
		return total + "";
	}
}
