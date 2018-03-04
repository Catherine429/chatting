import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class ChatServer extends Frame{
	
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(6666);
			while(true) {
				Socket s = ss.accept();   //×èÈûÊ½£¡£¡£¡
System.out.println("a client connected!");
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
