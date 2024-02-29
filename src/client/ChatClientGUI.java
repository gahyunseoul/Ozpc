//Ŭ���̾�Ʈ ä��â

//Ŭ���̾�Ʈ ������ �� �ٽ� �����ϸ� ä�� �ȵ�
//�ٸ� Ŭ���̾�Ʈ�� �����ϸ� ä�� �ȵ�
package client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ChatClientGUI extends JFrame 
					implements KeyListener, ActionListener, Runnable{
	private Container con;
	private JTextArea jta = new JTextArea("");
	private JTextField jtf = new JTextField();
	private JButton input_bt = new JButton("����");
	private JButton exit_bt = new JButton("������");
	private JPanel south_p = new JPanel();
	private JPanel east_p = new JPanel();
	
	//��ũ�� �����
	private JScrollPane scrollPane = new JScrollPane(jta);

	private InetAddress ia;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;
	
	public void init() {
		con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("Center", scrollPane);
		jta.setEditable(false);
		con.add("South", south_p);
		south_p.setLayout(new BorderLayout());
		south_p.add("Center", jtf);
		south_p.add("East", east_p);
		east_p.setLayout(new GridLayout(1,2));
		east_p.add(input_bt);
		east_p.add(exit_bt);	
		jtf.addKeyListener(this);
		input_bt.addActionListener(this);
		exit_bt.addActionListener(this);
		/* ä��â���� X ������ ���� ȭ����� ���� ����� �ּ�ó���ص�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); */
	}
	
	//��ũ�� �׻� �Ʒ���
	public void addLog(String log) {
		jta.append(log + "\n");
		jta.setCaretPosition(jta.getDocument().getLength());
	}
	
	public ChatClientGUI(String title) {
		super(title);
		this.init();	
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
		
		try {
			ia = InetAddress.getByName("localhost");
			soc = new Socket(ia, 12345);
			pw = new PrintWriter(soc.getOutputStream());
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			Thread th = new Thread(this);
			th.start();
			pw.flush();
		}catch(Exception e) {}
	}

	@Override
	public void run() {
		while(true) {
			try {
				String msg = br.readLine();
				addLog("������ : " + msg);
			}catch(IOException e) {}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input_bt) {
			String message = jtf.getText();
			addLog("�� : " + message);
			jtf.setText("");
		}else if (e.getSource() == exit_bt) {
			dispose();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			addLog("�� : " + jtf.getText());
			pw.println(jtf.getText());
			pw.flush();
			jtf.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
