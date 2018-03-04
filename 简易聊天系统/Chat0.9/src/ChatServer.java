import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class ChatServer extends Frame{
	
	boolean bconnected = false;
	ServerSocket ss = null;
	Socket s = null;
	boolean started = false;
	
	public static void main(String[] args) {    
		new ChatServer().start();         //不能在静态方法中New一个动态的类。    且这一句较符合面向对象的思想。
	}

	public void start() {
		try {
			ss = new ServerSocket(6666);
		} catch(BindException e) {
			System.out.println("端口正在使用中.....");
			System.out.println("请关闭相关端口上的应用程序");
			System.exit(0);
		} catch(IOException e) {
			e.printStackTrace();	
		}
		
		started = true;
		
		try {
			while(started) {
				s = ss.accept();   //阻塞式！！！
System.out.println("a client connected!");
				bconnected = true;
				Clients clients = new Clients(s);
				new Thread(clients).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class Clients implements Runnable {
		
		private Socket s = null;
		private DataInputStream dis = null;
		
		public Clients(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		@Override
		public void run() {
			try{
				while(bconnected) {
					String str = null;
				
					str = dis.readUTF();
System.out.println(str);
				}
			} catch(EOFException e) {
				System.out.println("Client closed");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(dis != null) dis.close();        //对方关掉了，服务器必须也关掉
					if(s != null) s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
