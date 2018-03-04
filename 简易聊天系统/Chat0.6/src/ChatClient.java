import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame{
	
	TextField tftxt = new TextField();
	TextArea taContent = new TextArea();
	DataOutputStream dos = null;
	Socket s = null;
	
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
				System.exit(0);;
			}
			
		});
		tftxt.addActionListener(new TFListener()); 
		setVisible(true);
		connect();
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1", 6666);
System.out.println("connected!");
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tftxt.getText();
			taContent.setText(str);
			tftxt.setText("");
			try {
				dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF(str);
				dos.flush();
				dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	

}
