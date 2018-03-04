import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame{
	
	TextField tftxt = new TextField();
	TextArea taContent = new TextArea();
	DataOutputStream dos = null;
	Socket s = null;
	DataInputStream dis = null;
	private boolean connected = false;
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame() {
		setLocation(400, 300);
		setSize(300, 300);
		add(tftxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		this.addWindowListener(new WindowAdapter() {


			@Override
			public void windowClosing(WindowEvent e) {
				disconnected();
				System.exit(0);;
			}
			
		});
		tftxt.addActionListener(new TFListener()); 
		setVisible(true);
		connect();
		
		RevThread rt = new RevThread();
		new Thread(rt).start();
	}
	
	public void connect() {
		String string = null;
		try {
			s = new Socket("127.0.0.1", 6666);
			                                //while(connected) {   疯了么。。循环加到这会new出无数个线程。。。
				                 //OtherClients oc = new OtherClients(s);
				                //new Thread(oc).start();
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
System.out.println("connected!");
			connected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void disconnected() {
		connected = false;
		try {
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tftxt.getText();
			tftxt.setText("");
			try {
				dos.writeUTF(str);
				dos.flush();
//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private class RevThread implements Runnable {

		@Override
		public void run() {
			try {
				while(connected) {
					String str = dis.readUTF();
					taContent.setText(taContent.getText() + str + '\n');
				}
			} catch(SocketException e) {
				System.out.println("退出咯，拜拜~~");
				try {
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				connected = false;
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}

