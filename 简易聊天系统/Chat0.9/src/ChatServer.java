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
		new ChatServer().start();         //�����ھ�̬������Newһ����̬���ࡣ    ����һ��Ϸ�����������˼�롣
	}

	public void start() {
		try {
			ss = new ServerSocket(6666);
		} catch(BindException e) {
			System.out.println("�˿�����ʹ����.....");
			System.out.println("��ر���ض˿��ϵ�Ӧ�ó���");
			System.exit(0);
		} catch(IOException e) {
			e.printStackTrace();	
		}
		
		started = true;
		
		try {
			while(started) {
				s = ss.accept();   //����ʽ������
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
					if(dis != null) dis.close();        //�Է��ص��ˣ�����������Ҳ�ص�
					if(s != null) s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
