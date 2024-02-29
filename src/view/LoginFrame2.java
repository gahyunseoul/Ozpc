package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import pj.ClientGUI;

import client.ClientMain;

public class LoginFrame2 extends JFrame implements ActionListener {
	
		 private BufferedImage img = null;
		 private JTextField loginTextField;
		 private JPasswordField passwordField;
		 private JButton bt;
		 private JButton jbt;
		 Userinfo users;
		 visitordao dao;
		 join2 jo;
		 OzServerGui sg;
		 VVIEW vi;
		 public static String id;
		 LocalDate now = LocalDate.now();
		 String str = now.toString();
		 public LoginFrame2() {
	 		 
	 	 this.Main();
		 users = new Userinfo();
		 dao = new visitordao();
		
		 }
	 

	 	// ������
	 	public void Main() {

        setTitle("Oz PC cafe visitor Login"); //Ÿ��Ʋ
        setSize(631, 439);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        // ���̾ƿ� ����
        setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 631, 439);
        layeredPane.setLayout(null);
 
        // �г�1 
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 631, 439);
        layeredPane.add(panel);
        
        // �����̹��� 
        try {
            img = ImageIO.read(new File("img/ozis.png"));
        } catch (IOException e) {
            System.out.println("�̹��� �ҷ����� ����");
            System.exit(0);
       }
        
        // �α��� 
        loginTextField = new JTextField(15);
        loginTextField.setBounds(248, 163, 100, 24);
        layeredPane.add(loginTextField); 
        loginTextField.setForeground(Color.blue);
        loginTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        // �н�����
        passwordField = new JPasswordField(15);
        passwordField.setBounds(248, 209, 100, 24);
        passwordField.setForeground(Color.blue);
        passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        layeredPane.add(passwordField);
 
        // �α���, ȸ������ ��ư �߰�
        bt = new JButton(new ImageIcon("img/login1.png"));
        bt.setBounds(121, 326, 153, 32);
        
        jbt = new JButton(new ImageIcon("img/join.png"));
        jbt.setBounds(326, 324, 132, 40);
      
        layeredPane.add(bt);
        layeredPane.add(jbt);
        
        bt.addActionListener(this);
        
        //  �߰�
        add(panel);
        add(layeredPane);
        layeredPane.add(panel);
        
        //ȭ�� �׻� �߾ӿ� ����
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
	 	
	 	//ȸ������â���� �Ѿ��~
        	jbt.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	new join2(); //�Ѿ~
        	setVisible(false);  // ȸ������â���� �Ѿ�� �ʴ� ����
        }
        	
    });
	 	}
    
       
    @Override
		public void actionPerformed(ActionEvent e) {
    		id = loginTextField.getText();
    		char[] pass = passwordField.getPassword();
			String pwd = new String(pass);
			
			if(id.equals("") || pwd.equals("")) {
			//��ĭ�Ұ� �޽���
			 JOptionPane.showMessageDialog(null, "��ĭ�Ұ�");
	 } 		else if(id != null && pwd != null) {
	 		if(dao.userLogin1(id, pwd)) {	//�� �κ��� �����ͺ��̽��� ������ �α��� ������ Ȯ���ϴ� �κ�
	 			//id -> Ŭ���̾�Ʈ�� ���� 
	 		 JOptionPane.showMessageDialog(null, "ȯ���մϴ� ����");
	 		//�湮�� ȭ�� 
	 		 new VVIEW();
	 		 ClientGUI frame = new ClientGUI("���� ȯ���մϴ�");
	 		 dao.insertDate(str);
	 		 dispose();
	 	 }
			else {
			//�α��� ����
			JOptionPane.showMessageDialog(null, "�α��� ^X^");
	 	}   
     } 		
		
    }
    
	
        

    	class MyPanel extends JPanel {
    	public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
  }
 
    	public Object getUsers() {
		
		return null;
	}

public static void main(String[] args) {
	new LoginFrame2();
}	
}
        