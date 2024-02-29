package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;



	public class Fgame extends JFrame {
		
		private BufferedImage img = null;
		JLayeredPane FgamePane = new JLayeredPane();
		public Fgame() {
			
			
	        setTitle("�¶��� ����"); //Ÿ��Ʋ
	        setSize(1100, 750);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	 
	        // ���̾ƿ� ����
	        setLayout(null);
	        
	        FgamePane.setBounds(0, 0, 1100, 750);
	        FgamePane.setLayout(null);
	        
	        // �г�1 
	        MyPanel panel = new MyPanel();
	        panel.setBounds(0, 0, 1100, 750);
	        FgamePane.add(panel);
	        
	        // �̹��� 
	        try {
	            img = ImageIO.read(new File("img/fgame1.png"));
	        } catch (IOException e) {
	            System.out.println("�̹��� �ҷ����� ����");
	            System.exit(0);
	       }
	        
	        add(panel);
	        add(FgamePane);
	        FgamePane.add(panel);
	        
	        Dimension frameSize = getSize();
	        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
	        setLocation((windowSize.width - frameSize.width) / 2,
	                (windowSize.height - frameSize.height) / 2); 
	        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	        setVisible(true);
	       
	        
		}
		class MyPanel extends JPanel {
	    	public void paint(Graphics g) {
	        g.drawImage(img, 0, 0, null);
	    }
	  }


	public static void main(String[] args) {
		new Fgame();
	}	
	}


