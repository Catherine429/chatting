import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class ChatServer extends Frame{
	
	public static void main(String[] args) {
		
		ServerSocket ss = null;
		Socket s = null;
		DataInputStream dis = null;
		boolean bconnected = false;
		boolean started = false;
		
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
				dis = new DataInputStream(s.getInputStream());
				while(bconnected) {
					String str = dis.readUTF();
System.out.println(str);
				}
				dis.close();
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
