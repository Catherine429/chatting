import java.net.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class ChatServer extends Frame{
	
	ServerSocket ss = null;
	Socket s = null;
	boolean started = false;
	//List<Socket> clts = new ArrayList<Socket>();
	List<Clients> client = new ArrayList<Clients>();       //�������˼ά������socket��client��
	
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
		private DataOutputStream dos = null;
		private boolean bconnected = false;
		
		public Clients(Socket s) {
			bconnected = true;
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
		public void send(String str) {
			try {
				dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try{
				while(bconnected) {
					String str = dis.readUTF();
					for(int i=0; i<client.size(); i++) {
						Clients c = client.get(i);
						c.send(str);
					}
System.out.println(str);
				}
			} catch(EOFException e) {
				System.out.println("Client closed");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(dis != null) dis.close();        //�Է��ص��ˣ�����������Ҳ�ص�
					if(dos != null) dos.close();
					if(s != null) s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
