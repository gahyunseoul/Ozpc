package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.Timer;
//import OZpc_sql.*;

// import Time_Dialog.Add_Time;

class Time_Dialog extends Dialog implements ActionListener {
	// �ð� �߰��ϱ� dialog ���
	// �ð� �߰� ��ư ������ button���� ������� �¼� ��
	// �װ� ������ �ð� �󸶳� �߰�? �߰� �ű⼭ �δ��� ��� ��
	JButton seat_click[] = new JButton[30];
	private Add_Time addTimeDialog;
    int click_seat = 0; // click_seat�� Ŭ���� ��� ������ ����
    //UserTimePro userTime = new UserTimeProImpl();

	
	Panel p = new Panel();
	
	public Time_Dialog(String title) {
		super(title);
		addTimeDialog = null;
		this.setLayout(new GridLayout(6,5,80,5));
		for (int i = 0; i < 30; ++i) {
			seat_click[i] = new JButton(i + 1 + "��\n");
			this.add(seat_click[i]);
			seat_click[i].addActionListener(this);
			seat_click[i].setActionCommand(Integer.toString(i)); 
            // i ���� ������ ��ư�� ActionCommand�� ����
		}

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setBounds(xpos-200, ypos, 800, 500);
		this.setResizable(false);
	}
	public void isVisible(boolean isVisible) {
		this.setVisible(isVisible);
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < 30; ++i) {
			if (seat_click[i] == arg0.getSource() ) {
				click_seat = Integer.parseInt(arg0.getActionCommand());
	            new Add_Time("�ð� �߰�");
			}
		}
	}
	
	class Add_Time extends Dialog implements ActionListener{
		//private OzServerGui oz = new ();
		JButton add = new JButton("�ð� �߰�");
		JButton cancel = new JButton("���");
		
		private ButtonGroup buttonGroup = new ButtonGroup();
		private JRadioButton hour = new JRadioButton("50�� ����");
		private JRadioButton hour_2 = new JRadioButton("1�ð� 50�� ����");
		private JRadioButton hour_3 = new JRadioButton("2�ð� 50�� ����");
		private JRadioButton hour_5 = new JRadioButton("4�ð� 50�� ����");
		private JRadioButton hour_9 = new JRadioButton("8�ð� 50�� ����");
		private JRadioButton hour_12 = new JRadioButton("11�ð� 50�� ����");
		private JRadioButton hour_24 = new JRadioButton("23�ð� 50�� ����");
		
		private Panel south_p = new Panel();
		private Panel center_p = new Panel();
		
		
		 public Add_Time(String title) {
			super(title);
			
			this.setLayout(new BorderLayout());
			 
			
			this.add("South", south_p);
			south_p.setLayout(new GridLayout(2,1));
			south_p.add(add);		add.addActionListener(this);
			south_p.add(cancel);	cancel.addActionListener(this);
			
			this.add("Center", center_p);
			center_p.setLayout(new GridLayout(7,1));
			
			buttonGroup.add(hour);
			buttonGroup.add(hour_2);
			buttonGroup.add(hour_3);
			buttonGroup.add(hour_5);
			buttonGroup.add(hour_9);
			buttonGroup.add(hour_12);
			buttonGroup.add(hour_24);
			
			center_p.add(hour);
			center_p.add(hour_2);
			center_p.add(hour_3);
			center_p.add(hour_5);
			center_p.add(hour_9);
			center_p.add(hour_12);
			center_p.add(hour_24);
			
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
			int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
			this.setBounds(xpos-100, ypos, 400, 300);
			this.setResizable(false);
		}
		public void isVisible(boolean isVisible) {
			this.setVisible(isVisible);
		}
		
		public void run(int initialTime) {
		    Timer timer = new Timer(1000, new ActionListener() {
		        int timeLeft = initialTime;

		        @Override
		        public void actionPerformed(ActionEvent e) {
		            if (timeLeft >= 0) {
		                OzServerGui.set_start_seat(click_seat, timeLeft);
		                timeLeft--;
		            } else {
		                ((Timer) e.getSource()).stop(); // Ÿ�̸� ����
		                OzServerGui.ititial_seat(click_seat);
		            }
		        }
		    });
            setVisible(false);
		    timer.start();
		}
		
		@Override
		// �ð� �귯���� �� ����� �ʷ� ������ ����!!
		public void actionPerformed(ActionEvent arg0) {
			if (add == arg0.getSource()) {
		        if (hour.isSelected()) {
		        	run(50);
		        	this.setVisible(false);
		        } else if (hour_2.isSelected()) {
		        	run(100);
		        }else if (hour_3.isSelected()) {
		        	run(170);
		        }else if (hour_5.isSelected()) {
		        	run(290);
		        }else if (hour_9.isSelected()) {
		        	run(570);
		        }else if (hour_12.isSelected()) {
		        	run(710);
		        }else if (hour_24.isSelected()) {
		        	run(1430);
		        }
			}else if(cancel == arg0.getSource()) {
				this.setVisible(false);
			}
			
           

		}
			
		public void add_time() {
			int mul = 1;
			int time = 3600;
			while(time>0) {
				try {
					Thread.sleep(1000);
				}catch(InterruptedException e) {}
				time--;
				// .setText("�ð� : "+time+"��");
			}
		}
	}
}	