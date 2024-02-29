//����, ä�� Ŭ�� �� â ���� ���Ѱ�

package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientMain extends JFrame implements ActionListener{
	
	//�԰Ÿ� ����
	private int total = 0;
	
	Exit exit = new Exit();
	
	LocalDate now = LocalDate.now();	
	String str = now.toString();
	
	private Container con;
	private JPanel jp = new JPanel();
	private JButton food_bt = new JButton("���� �ֹ�");
	private JButton chat_bt = new JButton("ä��");
	private JButton open_bt = new JButton("����");
	private JButton exit_bt = new JButton("����");
	
	public void updateTotal(int newTotal) {
		total = newTotal;
	}
	public void init() {
    	con = this.getContentPane();
    	con.setLayout(new BorderLayout());
    	con.add(jp);
    	jp.setLayout(new GridLayout(1,4));
    	jp.add(chat_bt); chat_bt.addActionListener(this);
    	jp.add(food_bt); food_bt.addActionListener(this);
    	jp.add(open_bt); open_bt.addActionListener(this);
    	jp.add(exit_bt); exit_bt.addActionListener(this);
    	
    }
	public ClientMain(String title) {
        super(title);
        this.init();
        super.setSize(800, 500);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
        int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
        
        this.setLocation(xpos, ypos);
        this.setResizable(false);
        this.setVisible(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == food_bt) {
			FoodGUI foodGUI = new FoodGUI("�԰Ÿ��ֹ�");
		}else if (e.getSource() == chat_bt) {
			new ChatClientGUI("�����ڿ� ä��");
		}else if (e.getSource() == open_bt) {
			exit.insertDate(str);
		}else if (e.getSource() == exit_bt) {
			new TotalGUI("����");
		}
	}
}

