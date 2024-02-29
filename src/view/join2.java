package view;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import client.FoodGUI;

	public class join2 extends JFrame /*implements ViewPro*/ {
		LoginFrame owner;
		Userinfo users;
		UserDTO2 user;
		visitordao dao; 
		
		
		//��ư
		JButton join = new JButton("ȸ������");
		JButton cancel = new JButton("���");
		//�ؽ�Ʈ�ʵ�
		JTextField id = new JTextField(15);
		JPasswordField pwd1 = new JPasswordField(15);
		JPasswordField pwd2 = new JPasswordField(15);
		JTextField birth = new JTextField(15);
		JTextField name = new JTextField(15);
		JTextField phone = new JTextField(15);
		JTextField email = new JTextField(15);
		//�г�
		JPanel TotalPanel = new JPanel();
		
		
		public join2()	{	
				addAct1();
				Blank();
				init();
			dao = new visitordao();
		}
		//����
		public void init()	{
		//�гθ����		
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		idPanel.add(new JLabel("���̵� : "));
		idPanel.add(id);
			
		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwdPanel.add(new JLabel("��й�ȣ : "));
		pwdPanel.add(pwd1);
			
		JPanel pwd1Panel = new JPanel();
		pwd1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwd1Panel.add(new JLabel("��й�ȣȮ�� : "));
		pwd1Panel.add(pwd2);
			
			
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		namePanel.add(new JLabel("��    �� : "));
		namePanel.add(name);
		
			
		JPanel birthPanel = new JPanel();
		birthPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		birthPanel.add(new JLabel("������� : "));
		birthPanel.add(birth);
			
			
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		phonePanel.add(new JLabel("��ȭ��ȣ : "));
		phonePanel.add(phone);
			
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		emailPanel.add(new JLabel("E-mail : "));
		emailPanel.add(email);
			
			
		TotalPanel.setLayout(new GridLayout(8, 2));
		TotalPanel.add(idPanel);
		TotalPanel.add(pwdPanel);
		TotalPanel.add(pwd1Panel);
		TotalPanel.add(namePanel);
		TotalPanel.add(birthPanel);
		TotalPanel.add(phonePanel);
		TotalPanel.add(emailPanel);
			
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.add(TotalPanel);
		//ȸ������, ��ҹ�ư
		JPanel panel = new JPanel();
		panel.add(join);
		panel.add(cancel);
		add(panel, BorderLayout.SOUTH);
		add(contentPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		setBounds(200, 200, 300, 300);
		setVisible(true);
		//ũ�� ������~
		setResizable(false);
		
		setTitle("Oz pc cafe ȸ������"); // Ÿ��Ʋ
		JPanel jPanel = new JPanel();
		jPanel.setBackground(null);
		setSize(350,320);
			
		add(jPanel);
		//ȭ�� �߾ӿ� ����
		Dimension frameSize = getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation((windowSize.width - frameSize.width) / 2,
	            (windowSize.height - frameSize.height) / 2); 
	    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    setVisible(true);
	}
			
		//�̺�Ʈ���� 
	    public void addAct1()  {
		//ȸ������ ��ư 
		join.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

	
		//���� �ϳ��� ���������
	    if(Blank()) {
	        JOptionPane.showMessageDialog(join2.this, "��ĭ���� �Է����ּ���." );
	        return;
	        } 
	       
	        // ��й�ȣ�� ���Ȯ���� ��ġ���� �ʾ��� ��
	    if(!String.valueOf(pwd1.getPassword()).equals(String.valueOf(pwd2.getPassword()))) {
	        		JOptionPane.showMessageDialog(join2.this, "��й�ȣ�� ��й�ȣȮ���� ��ġ���� �ʽ��ϴ�." );
	        		pwd1.requestFocus();
	   }else {
	            
	            user = new UserDTO2(id.getText(), String.valueOf(pwd1.getPassword()), String.valueOf(pwd2.getPassword()),
	            		name.getText(), birth.getText(), phone.getText(), email.getText());

	    int result = dao.userInsert1(user.getId(), user.getPwd(), user.getName(), 
	            		user.getBirth(), user.getPhone(), user.getEmail());

	     if (result > 0) {
	                JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����߽��ϴ�");
	                new LoginFrame2();
	                dispose(); 
	            }
	        
		 else {
				System.out.println("ȸ������ ����");
				JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����Ͽ����ϴ�");
				new join2();
        		dispose();
			}
	        }
	      
		}
		}	        		
 
	);
	
		//��� ��ư 
		cancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
					new LoginFrame2();
					dispose();
	}
});
	}
	    //��ĭ�϶�
	    public boolean Blank() {
		   boolean result = false;
		   if(id.getText().isEmpty()) {
	        	id.requestFocus();
	        	return true;
}
		   if(String.valueOf(pwd1.getPassword()).isEmpty()) {
			    pwd1.requestFocus();
	        	return true;
}
		   if(String.valueOf(pwd2.getPassword()).isEmpty()) {
	        	pwd2.requestFocus();
	        	return true;
}
	       if(name.getText().isEmpty()) {
	        	name.requestFocus();
	        	return true;
	}
	       if(birth.getText().isEmpty()) {
	        	birth.requestFocus();
	        	return true;
	   }
	       if(phone.getText().isEmpty()) {
	        	phone.requestFocus();
	        	return true;
	    	}
	       if(String.valueOf(email.getText()).isEmpty()) {
	        	email.requestFocus();
	        	return true;
	 	}
	        	return result;
	    }
        
 }