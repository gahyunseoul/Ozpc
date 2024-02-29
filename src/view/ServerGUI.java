package view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


class OzServerGui extends JFrame implements ActionListener {
	private Container con;
	private JPanel server_menu_p = new JPanel();
	private JPanel server_menu_east_p = new JPanel();
	private JPanel alarm_p = new JPanel();						// �˸� ���� ����
	private JTextArea alarm = new JTextArea();
	private JScrollPane alarm_scroll = new JScrollPane(alarm); 	// ��ũ���� �����ϵ��� TextArea ����
	private JButton menu_client = new JButton("�� ����");			// ���� �޴� ��ư��
	private JButton menu_time = new JButton("�ð� ����");			// �ð� ����?
	DefaultListModel<String> lm = new DefaultListModel<>();
	JList<String> ls = new JList<>(lm);   
	private static Seat seat[] = new Seat[30];
	private static Panel seat_p = new Panel(); 
	visitordao dao = new visitordao();
	visitorinfo vi;
	public static void set_seat() {
	    for (int i = 0; i < 30; ++i) {
	        seat[i] = new Seat(i, 0); // �� seat[i]�� Seat ��ü�� �ʱ�ȭ
	        seat_p.add(seat[i]);
	        seat[i].setHorizontalAlignment(JLabel.CENTER);
	        seat[i].setBorder(new LineBorder(Color.gray));
	    }
	}
	
	public static void set_start_seat(int click_seat, int time) {
	    seat[click_seat].setText("<html><body style='text-align:center;'>��� ��<br/>" + (click_seat + 1) + "��<br/>���� �ð�: " + time + "��</body></html>");
	}
	
	public static void ititial_seat(int click_seat) {
	    seat[click_seat].setText(click_seat+1 + "��");
	}
	
	public void init() {
		con = this.getContentPane();  
		this.setLayout(new BorderLayout());
		// �˸�â gui
		this.add("South", alarm_p);
        alarm.setRows(10); // ���ϴ� �� ��
        alarm.setColumns(120); // ���ϴ� �� ��
		alarm_p.add(alarm_scroll);
		
		// �޴� ��ư
		this.add("North", server_menu_p);
		server_menu_p.setLayout(new BorderLayout());
		server_menu_p.add("East", server_menu_east_p);
		server_menu_east_p.setLayout(new GridLayout(1,2));
		server_menu_east_p.add(menu_client);	menu_client.addActionListener(this);
		server_menu_east_p.add(menu_time);		menu_time.addActionListener(this);
		
		// �¼�
		this.add("Center", seat_p);	
		seat_p.setLayout(new GridLayout(6,5,200,5));
		
		set_seat();
		
		// this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	
	public OzServerGui(String title) {
		super(title);
		this.init();	
		super.setSize(1500, 900);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}


	@Override
	
	public void actionPerformed(ActionEvent e) {
		if (menu_client == e.getSource()) {
			visitorinfo frame = new visitorinfo();
		
			//List<UserDTO2> list = dao.listTest();
			//for(UserDTO2 UserDTO2 : list) {
		
		    //  }  
		}
		else if (menu_time == e.getSource()) {
			Time_Dialog td = new Time_Dialog("�ð� �߰�");
		}
	
}
public static class ServerGUI {
	public static void main(String[] args) {
		OzServerGui frame = new OzServerGui("������ ȭ��");
	}
}
}