import java.net.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.awt.*;

public class ChatServer extends Frame{
	
	ServerSocket ss = null;
	Socket s = null;
	boolean started = false;
	//List<Socket> clts = new ArrayList<Socket>();
	List<Clients> client = new ArrayList<Clients>();       //面向对象思维，不用socket用client。
	
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
		Clients clients = null;
		
		try {
			while(started) {
				s = ss.accept();   //阻塞式！！！
System.out.println("a client connected!");
				clients = new Clients(s);
				client.add(clients);
				new Thread(clients).start();
			}
		} catch(EOFException e) {
			System.out.println("client closed!");
			client.remove(clients);
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
		
		private Clients c = null; 
		private Socket s = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bconnected = false;
		
		public Clients(Socket s) {
			bconnected = true;
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch(SocketException e) {
				try {
					if(dis != null) dis.close();
					if(dos != null) dos.close();
					s.close();
					//client.remove(this);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try{
				while(bconnected) {
					String str = dis.readUTF();
					for(int i=0; i<client.size(); i++) {
						c = client.get(i);
						c.send(str);
					}
System.out.println(str);
				}
			} catch(EOFException e) {
				System.out.println("Client closed");
				try {
					if(dis != null) dis.close();
					if(dos != null) dos.close();
					s.close();
					s = null;     //只有s为Null了，垃圾回收器才能给它回收
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(dis != null) dis.close();
					if(dos != null) dos.close();
					s.close();
					s = null;
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			  }
		}
	}

}
