import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class ChatServer extends Frame{
	
	public static void main(String[] args) {
		
		ServerSocket ss = null;
		Socket s = null;
		DataInputStream dis = null;
		
		try {
			ss = new ServerSocket(6666);
		} catch(IOException e) {
			e.printStackTrace();	
		}
		try {
			while(true) {
				s = ss.accept();   //×èÈûÊ½£¡£¡£¡
System.out.println("a client connected!");
				dis = new DataInputStream(s.getInputStream());
				String str = dis.readUTF();
System.out.println(str);
				dis.close();
			}
		} catch(EOFException e) {
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
