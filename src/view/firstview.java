package view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.WindowConstants;







public class firstview extends JFrame implements ActionListener {

	JButton mbt = new JButton("������");
	JButton vbt = new JButton("�湮��");
	BufferedImage img = null;
	JPanel fpan = new JPanel();
	
	public firstview() {
		Selectview();
	}
	public void Selectview() {

        setTitle("Oz PC cafe Select"); //Ÿ��Ʋ
        setSize(631, 439);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        // ���̾ƿ� ����
        setLayout(null);
        JLayeredPane firstPane = new JLayeredPane();
        firstPane.setBounds(0, 0, 631, 439);
        firstPane.setLayout(null);
 
        // �г�1 
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 631, 439);
        firstPane.add(panel);
        add(panel);
        add(firstPane);
        firstPane.add(panel);
        firstPane.add(mbt);
        firstPane.add(vbt);
        // �����̹��� 
        try {
            img = ImageIO.read(new File("img/ozmain.png"));
        } catch (IOException e) {
            System.out.println("�̹��� �ҷ����� ����");
            System.exit(0);
       }
        
        
        mbt.addActionListener(this);
	 // ������ / �湮��
    mbt = new JButton(new ImageIcon("img/member1.png"));
    mbt.setBounds(90, 196, 200, 70);
    
    vbt = new JButton(new ImageIcon("img/visitor.png"));
    vbt.setBounds(343, 194, 200, 70);
  
    firstPane.add(mbt);
    firstPane.add(vbt);
    
    //��ư �׵θ� �Ⱥ��̱�
    mbt.setBorderPainted(false);
    vbt.setBorderPainted(false);
    
    mbt.addActionListener(this);
    
    setVisible(true);
    
    //ȭ�� �׻� �߾ӿ� ����
    Dimension frameSize = getSize();
    Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((windowSize.width - frameSize.width) / 2,
            (windowSize.height - frameSize.height) / 2); 
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);
 	
 	//������ �α���â���� �Ѿ��
    	mbt.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    	new LoginFrame(); //�Ѿ~
    	setVisible(false);  // �Ѿ�� �ʴ� ����
    }
    	
});
    	vbt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			new LoginFrame2(); //�Ѿ~
			setVisible(false);  // �Ѿ�� �ʴ� ����
				
			}
    		
    	});
	}















	class MyPanel extends JPanel {
    	public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
  }



//����
public static void main(String[] args) {
	new firstview();
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}


}
