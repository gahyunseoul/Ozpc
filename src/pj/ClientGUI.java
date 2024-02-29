package pj;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import view.LoginFrame2;
import client.*;

//�߾� �� main GUI <- �̰����� id �޾ƾ� ��
public class ClientGUI extends JFrame implements ActionListener{
	LocalDate now = LocalDate.now();
	static ClientGUI cg;
	static TDialog td;
	static ChatClientGUI ccg;
	static PCDAO dao;
	static Client cl;
	static FoodGUI fg;
	
	String str = now.toString();
	int hour, minute;
	
	public String id = LoginFrame2.id;  //�α��� ȭ��� ����
	
	private Container con;
	private JPanel center_p = new JPanel();
	private JPanel center_south_p = new JPanel();
			JButton eat_b = new JButton();
			JButton timeReload_b = new JButton("");
			JButton chat_b = new JButton("");
	private JPanel center_center_p = new JPanel();
	private JLabel timeRemaining_l = new JLabel("00 : 00", JLabel.CENTER);
	private JPanel center_center_west_p = new JPanel();
	private JLabel seat_l = new JLabel("OZPC", JLabel.CENTER);
	private JPanel center_center_west_center_p = new JPanel();
	private JLabel id_l = new JLabel("ID : 00.000", JLabel.CENTER);
			JButton logout_b = new JButton("�α׾ƿ�");
	private JPanel popuralgame_p = new JPanel();
			JButton popuralgame_b = new JButton();
			JButton popuralgame_b2 = new JButton();
			JButton popuralgame_b3 = new JButton();
	private InetAddress ia;
	private Socket soc;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	//private PrintWriter pw;
			
	//�߾� �� main GUI ����
	public void init() {
		con = this.getContentPane();
		con.setLayout(new BorderLayout());
		//���� �ٽ� ���������
		con.add("South", popuralgame_p);
		popuralgame_p.setLayout(new GridLayout(1,3));
		popuralgame_b = new JButton(new ImageIcon("img/LOL3.png"));
		popuralgame_b2 = new JButton(new ImageIcon("img/FIFA43.png"));
		popuralgame_b3 = new JButton(new ImageIcon("img/MAPLE5.png"));
		popuralgame_p.add(popuralgame_b);
		popuralgame_p.add(popuralgame_b2);
		popuralgame_p.add(popuralgame_b3);
		popuralgame_p.setPreferredSize(new Dimension(500,50));
		con.add(center_p);
		center_p.setLayout(new BorderLayout());
		
		center_p.add("South", center_south_p);
		center_south_p.setLayout(new GridLayout(1,3));
		
		eat_b = new JButton(new ImageIcon("img/order2.png"));
		center_south_p.add(eat_b);
		eat_b.addActionListener(this);
		
		timeReload_b = new JButton(new ImageIcon("img/time1.png"));
		center_south_p.add(timeReload_b);
		timeReload_b.addActionListener(this);
		
		chat_b = new JButton(new ImageIcon("img/chat1.png"));
		center_south_p.add(chat_b);
		chat_b.addActionListener(this);
		center_south_p.setPreferredSize(new Dimension(420,60));
		
		center_p.add(center_center_p);
		center_center_p.setLayout(new BorderLayout());
		timeRemaining_l.setFont(new Font("", Font.BOLD, 55));
		timeRemaining_l.setText(hour + ":" + minute);
		center_center_p.add(timeRemaining_l);
		
		center_center_p.add("West", center_center_west_p);
		center_center_west_p.setLayout(new GridLayout(2,1));
		seat_l.setFont(new Font("", Font.BOLD, 25));
		center_center_west_p.add("North", seat_l);
		center_center_west_p.setPreferredSize(new Dimension(135,60));
		
		center_center_west_p.add(center_center_west_center_p);
		center_center_west_center_p.setLayout(new GridLayout(2,1));
		id_l.setFont(new Font("", Font.PLAIN, 15));
		id_l.setText(id);
		center_center_west_center_p.add(id_l);		//�α��� ȭ�����κ��� ID �޾ƿ���
		logout_b = new JButton(new ImageIcon("img/logout1.png"));
		center_center_west_center_p.add(logout_b);
		logout_b.addActionListener(this);
		center_center_west_center_p.setPreferredSize(new Dimension(100,100));
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	public ClientGUI(String title) {
		super(title);
		System.out.println("client ()");
		try {
			ia = InetAddress.getByName("localhost");
			soc = new Socket(ia, 24511);
			
			
			System.out.println("���� ���� ����");
		}catch(Exception e) {
			 e.printStackTrace();
		}
		System.out.println("socket stream()");
		dao = new PCDAO();
		System.out.println("PCDAO()");
		dao.selectLogintPc(id);
		cl = new Client();
		System.out.println("Client()");
		init();
		System.out.println("init()");
		td = new TDialog(this, "��ݰ���", false);	//��ݰ��� Dialog ��üȭ
		ccg = new ChatClientGUI(this, "ä��", false);//ä�� Dialog ��üȭ
		fg = new FoodGUI(this, "�԰Ÿ� �ֹ�", false);
		super.setSize(500, 250);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth() - this.getWidth());
		int ypos = 0;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}
			
	//�����ð� Ÿ�̸� ����
	public void timer(int dbtime) {
		hour = dbtime/3600;
		minute = (dbtime%3600)/60;
		if(minute <= 9) {
			timeRemaining_l.setText(String.valueOf(hour) + ":0" + String.valueOf(minute));
		}else {
			timeRemaining_l.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
		}
	}
	//�߾� �� main GUI �̺�Ʈ ó��
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == eat_b) {			//�԰Ÿ� ����
			fg.isview(true);
		}
		if(e.getSource() == timeReload_b) {		//��� ����
			td.isview(true);
		}
		if(e.getSource() == chat_b) {			//ä��
			ccg.isview(true);
		}
		if(e.getSource() == logout_b) {			//�α׾ƿ�
			cl.sendClientID(id, "f", "logout");
			try {
				dao.con.close();
				soc.close();
				System.exit(0);
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("con.close ����!");
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("soc.close ����!");
			}
		}
			popuralgame_b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Desktop desktop = Desktop.getDesktop();
		            try {
		                URI uri = new URI("https://www.leagueoflegends.com/ko-kr/");
		                desktop.browse(uri);
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            } catch (URISyntaxException ex) {
		                ex.printStackTrace();
		            }
		}
	});
			popuralgame_b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Desktop desktop = Desktop.getDesktop();
		            try {
		                URI uri = new URI("https://fconline.nexon.com/main/index");
		                desktop.browse(uri);
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            } catch (URISyntaxException ex) {
		                ex.printStackTrace();
		            }
		}
	});
			popuralgame_b3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Desktop desktop = Desktop.getDesktop();
		            try {
		                URI uri = new URI("https://maplestory.nexon.com/Home/Main");
		                desktop.browse(uri);
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            } catch (URISyntaxException ex) {
		                ex.printStackTrace();
		            }
		}
	});
}
	//TCP��ſ�
	class Client extends Thread{
		//Client ����
		public Client() {
			sendClientID(id, "t", "login");
			this.start();
		}
		//server���� �α��� ���� �ѱ��
		public void sendClientID(String id, String loginox, String type) {
			try {
				dao.login(id, loginox);
				ClientMessage cm = dao.selectLogintPc(id);
				cm.setSendType(type);
				cm.setSendChat("0");
				oos = new ObjectOutputStream(soc.getOutputStream());
				oos.writeObject(cm);
				cm.disp();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//server���� ���� �۽ſ� ��ü ���� -> ���� (�԰Ÿ�, �ð�, ä��)
		public void sendClientBuy(String id, int sumTime, String menuList, String chat, String type) {
			ClientMessage cm = new ClientMessage();
			cm = dao.selectLogintPc(id);
			cm.setSendAddTime(sumTime);
			cm.setSendMenuList(menuList);
			cm.setSendType(type);
			cm.setSendChat(chat);
			try {
				oos = new ObjectOutputStream(soc.getOutputStream());
				oos.writeObject(cm);
				System.out.println("ttt:");
				cm.disp();
				oos.flush();
			}catch(IOException e) {
				e.printStackTrace();}
			System.out.println("[������ ���� cm]" + "\n");
			cm.disp();
		}
		//server�κ��� ��ü ���޹޾� typeȮ��
		public void run() {
			while(true) {
				try {
					ois = new ObjectInputStream(soc.getInputStream());
					Object obj = ois.readObject();
					ClientMessage cm = (ClientMessage)obj;
					System.out.println("[�����κ��� ���޹���]");
					cm.disp();
					if(cm.getSendType().equals("Time")) {
						timer(cm.getSendAddTime());
					}else {
						ccg.memberMessageView(cm.getSendChat(), true);
					}
				}catch(IOException e) {}
				 catch (ClassNotFoundException e) {}
			}
		}
	}
	
	//������� GUI
	class TDialog extends JDialog implements ActionListener{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		private int xpos = (int)(screen.getWidth() - 1300);
		private int ypos = (int)(screen.getHeight() -(screen.getHeight() - 250));
		private String timeStr = "1�ð�(1,000��) 2�ð�(2,000��) 3�ð�(3,000��) 4�ð�(4,000��) 10�ð�(10,000��) 20�ð�(20,000��) 30�ð�(30,000��) 40�ð�(40,000��)";
			    String timeStrArr[] = timeStr.split(" ");
			    
	    private int sumTimePrice = 0;	//�� ���� �ݾ�
		private int sumTime = 0;
		
		private DecimalFormat df = new DecimalFormat("###,###");
		
		private BorderLayout bl = new BorderLayout();
		private Container con = new Container();
				JButton time_b[] = new JButton[8];
		private JPanel time_p = new JPanel();
				TextArea ta = new TextArea();
		private JLabel sum_l = new JLabel("�� �����ݾ� : ", JLabel.LEFT);
				JButton pay_b = new JButton("����");
		private JButton cancle_b = new JButton("���");
		private JPanel east_p = new JPanel();
		private JPanel east_south_p = new JPanel();
		
		//������� GUI ����
		public void init() {
			this.add(con);
			con.setLayout(new BorderLayout());
			con.add(time_p);
			time_p.setLayout(new GridLayout(4,2,0,50));
			
			for(int i=0; i<timeStrArr.length; ++i) {
				time_b[i] = new JButton();
				time_b[i].setText(timeStrArr[i]);
				time_p.add(time_b[i]);
				time_b[i].addActionListener(this);
			}
			con.add("East", east_p);
			east_p.setPreferredSize(new Dimension(300,800));
			east_p.setLayout(new BorderLayout());
			east_p.add(ta);
			east_p.add("South", east_south_p);
			east_south_p.setPreferredSize(new Dimension(300,150));
			east_south_p.setLayout(new GridLayout(3,1));
			sum_l.setFont(new Font("", Font.BOLD, 15));
			east_south_p.add(sum_l);
			east_south_p.add(cancle_b);
			cancle_b.addActionListener(this);
			east_south_p.add(pay_b);
			pay_b.addActionListener(this);
		}
		public TDialog(JFrame owner, String title, boolean modal) {
			super(owner, title, modal);
			this.init();
			super.setSize(800, 800);
			this.setLocation(xpos, ypos);
			this.setResizable(false);
		}
		//������� GUI â ���� ����
		public void isview(boolean b) {
			this.setVisible(b);
		}
		
		//������� GUI �̺�Ʈ ó��
		@Override
		public void actionPerformed(ActionEvent e) {
			//����� ��ư �̺�Ʈ ó��
			for(int i=0; i<8; ++i) {
				if(e.getSource() == time_b[i]) {
					int sumTimePriceDB = 0;	//DB���� ����� �ݾ�
					sumTimePriceDB = dao.setPrice(id); //DB���� �ݾ� ���� ����
					ta.append(time_b[i].getText() + "\n");
					//System.out.println(i);
					sumTime += dao.buttonTime(time_b[i].getText());  //DB�κ��� �� ��ư�� �ð� ��������
					sumTimePriceDB += dao.buttonPrice(time_b[i].getText()); //DB�κ��� �� ��ư�� ��� ��������
					sumTimePrice += dao.buttonPrice(time_b[i].getText());
					System.out.println(time_b[i].getText());
					sum_l.setText("�� �����ݾ� : " + df.format(sumTimePrice) + "��");
				}
			}
			//��� ���� ��ư �̺�Ʈ ó��
			if(e.getSource() == pay_b) {
				String taText = ta.getText();
				if(!taText.equals("")) {//������ �ƴϸ�
					int res1 = dao.buyTimePricePC(sumTimePrice, id);	//������ ��� DB�� ���� ó�� �޼ҵ�(�α��ν� ���̵� �޾ƿ���)
					//System.out.println("str : " + str);
					boolean res2 = dao.updateMoney(sumTimePrice, str);
					//System.out.println("(TDialog.eventó�� res1 : " + res1);
					//System.out.println("(TDialog.eventó�� res2 : " + res2);
					if(res1 <= 0) {
						JOptionPane.showMessageDialog(this, "������ �Ϸ���� �ʾҽ��ϴ�.");
					}else if(res1 > 0){
						JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
						isview(false);
						ta.setText("");
						sum_l.setText("�� �����ݾ� : ");
						//System.out.println("������ �ð� : " + sumTime);
						//System.out.println("(TDialog)cl : " + cl);
						cl.sendClientBuy(id,sumTime,"0","0", "Time");
						sumTimePrice = 0;
						sumTime = 0;
						//System.out.println("���� sumTime�� : " + sumTime);
					}
				}else {
					JOptionPane.showMessageDialog(this, "�����Ͻ� ����� ���õ��� �ʾҽ��ϴ�.");
				}
			}else if(e.getSource() == cancle_b) {
				ta.setText("");
				sum_l.setText("�� �����ݾ� : ");
				sumTimePrice = 0;
				sumTime = 0;
			}
		}
	}
public class ChatClientGUI extends JDialog implements Serializable, KeyListener, ActionListener{
	private Container con;
	private JTextArea jta = new JTextArea("");
	private JTextField jtf = new JTextField();
	private JButton input_bt = new JButton("����");
	private JButton exit_bt = new JButton("������");
	private JPanel south_p = new JPanel();
	private JPanel east_p = new JPanel();
	
	private boolean View = true;
	
	//��ũ�� �����
	private JScrollPane scrollPane = new JScrollPane(jta);
	
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
	}

	//��ũ�� �׻� �Ʒ���
	public void addLog(String log) {
		jta.append(log + "\n");
		jta.setCaretPosition(jta.getDocument().getLength());
	}

	public ChatClientGUI(JFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		this.init();	
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(false);
		/*
		try {
			Thread th = new Thread(this);
			th.start();
		}catch(Exception e) {}
		
	}

	@Override
	public void run() {
		while(true) {
			try {
				String msg = br.readLine();
				addLog("������ : " + msg + "n");
			}catch(IOException e) {}
		}
		*/
	}
	//�������� �޼��� ȭ�鿡 ����
	public void memberMessageView(String memberMessage, boolean ox) {
		boolean viewox = ox;
		while(viewox) {
			addLog("������ : " + memberMessage);
			viewox = false;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input_bt) {
			String message = jtf.getText();
			addLog("�� : " + message);
			cl.sendClientBuy(id, 0, "0", message, "chat");
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
			String message = jtf.getText();
			addLog("�� : " + message);
			cl.sendClientBuy(id, 0, "0", message, "chat");
			jtf.setText("");
		}
	}
	//chat GUI â ���� ����
	public void isview(boolean b) {
		this.setVisible(b);
	}
	@Override
	public void keyReleased(KeyEvent e) {}
}
	class Food extends JButton {
	    private ImageIcon imgIcon; // �̹��� �������� ���
	    private String name;
	    private int price;
	    private int stock;
	    
	    //�̹��� �гο� �� �̹���, �̸�, ����
	    public Food (ImageIcon imgIcon, String name, int price, int stock) {
	    	this.imgIcon = imgIcon;
	        this.name = name;
	        this.price = price;
	        this.stock = stock;
	        setIcon(imgIcon); // �������� ��ư�� ����
	        setLayout(new BorderLayout()); // �̹��� �������� ��� �����ϱ� ���� ���̾ƿ� ����
	        add(new JLabel(name, JLabel.CENTER), BorderLayout.NORTH); // ��ư ���� �޴� �̸� ǥ��
	        add(new JLabel(price + "��", JLabel.CENTER), BorderLayout.SOUTH); // ��ư �Ʒ��� ���� ǥ��
	    }
	    public Food(ImageIcon imgIcon, String name, int price) {
	        this.imgIcon = imgIcon;
	        this.name = name;
	        this.price = price;
	        setIcon(imgIcon); // �������� ��ư�� ����
	        setLayout(new BorderLayout()); // �̹��� �������� ��� �����ϱ� ���� ���̾ƿ� ����
	        add(new JLabel(name, JLabel.CENTER), BorderLayout.NORTH); // ��ư ���� �޴� �̸� ǥ��
	        add(new JLabel(price + "��", JLabel.CENTER), BorderLayout.SOUTH); // ��ư �Ʒ��� ���� ǥ��
	    }  
	    public String getName() {
	        return name;
	    }    
	    public int getPrice() {
	        return price;
	    }
	    public int getStock(String name) {
	    	return stock;
	    }
	    public void setStock(int newStock) {
	    	stock = newStock;
	    }
	}
	//ó�� �̹��� �ֱ� ���� Ŭ����
	class mainFood extends Canvas {
		private Image img;
		public mainFood(Image img) {
			this.img = img;
		}
		public void setImage(Image img) {
			this.img = img;
		}
		public void paint(Graphics g) {
			g.drawImage(img, 0,0,this.getWidth(), this.getHeight(), this);
		} 
	}

	public class FoodGUI extends JDialog implements ActionListener, Runnable{

		private String menu_n = "";
		private int total;
		
		public int getTotal() {
			return total;
		}
		
	    private Container con;
	    
	    private JPanel north_jp = new JPanel(); //�α�, ����, ��� �� �޴� ������
	    private JPanel center_jp = new JPanel(); //�޴� ���� �ֱ�
	    private JPanel east_jp = new JPanel(); //�ֹ������� �ֱ�
	    private JPanel mini_jp = new JPanel(); //�ֹ��� ��� ��ư �ֱ�
	    
	    //�α�, ���, ��, ���̵� �� �̹��� ��ư �г�
	    private JPanel noodlePanel = new JPanel();
	    private JPanel ricePanel = new JPanel();
	    private JPanel snackPanel = new JPanel();
	    private JPanel coffeePanel = new JPanel();
	    private JPanel sidePanel = new JPanel();
	    private JPanel bestPanel = new JPanel();
	    private JPanel addPanel = new JPanel();
	    
	    //ù ȭ��     
	    private Image img = Toolkit.getDefaultToolkit().getImage("img/pcfood.png");
	    private mainFood mf = new mainFood(img);

	    //�г� ��ȯ �ϱ� ����
	    private CardLayout cards = new CardLayout();

	    //��ư�� ����
	    private JButton best_bt = new JButton("�α�");
	    private JButton noodle_bt = new JButton("����");
	    private JButton rice_bt = new JButton("���");
	    private JButton snack_bt = new JButton("���ڷ�");
	    private JButton coffee_bt = new JButton("�����");
	    private JButton side_bt = new JButton("���̵��");
	    private JButton add_bt = new JButton("�߰�");
	    
	    //�̹��� ��ư �гε�
	    private Food[] bestFoods = new Food[9];
	    private Food[] noodleFoods = new Food[9];
	    private Food[] riceFoods = new Food[6];
	    private Food[] snackFoods = new Food[6];
	    private Food[] sideFoods = new Food[9];
	    private Food[] addFoods = new Food[4];
	    private Food[] coffeeFoods = new Food[8];
	    
	    //�ֹ�, ���, ������ ��ư
	    private JButton money_bt = new JButton("����");
	    private JButton card_bt = new JButton("ī��");
	    private JButton cancle_bt = new JButton("���");
	    private JButton exit_bt = new JButton("������");
	    
	    //�ֹ� ����
	    private JTextArea menu_ta = new JTextArea();
	    
	    //�ֹ� �޴� ���� ����Ʈ
	    private ArrayList<Food> list = new ArrayList<>();
	  
	    //���� 3�ڸ��� , �ֱ�
	    private DecimalFormat df = new DecimalFormat("###,###");
	    
	    public void init() {
	        con = this.getContentPane();
	        getContentPane().setLayout(cards);
	        con.setLayout(new BorderLayout());
	        con.add("North", north_jp);
	        con.add("Center", center_jp);
	        con.add("East", east_jp);
	        
	        //���ʿ� ��ư �ֱ�
	        north_jp.setLayout(new GridLayout(1, 7));
	        north_jp.add(best_bt);
	        best_bt.addActionListener(this);
	        north_jp.add(noodle_bt);
	        noodle_bt.addActionListener(this);
	        north_jp.add(rice_bt);
	        rice_bt.addActionListener(this);
	        north_jp.add(snack_bt);
	        snack_bt.addActionListener(this);
	        north_jp.add(coffee_bt);
	        coffee_bt.addActionListener(this);
	        north_jp.add(side_bt);
	        side_bt.addActionListener(this);
	        north_jp.add(add_bt);
	        add_bt.addActionListener(this);     
	        
	        //cardsLayout ����ϱ� ���� �̸� ����
	        center_jp.setLayout(cards);
	        
	        //ó�� �̹��� �ֱ�
	        center_jp.add(mf);
	        
	        //�̹��� ��ư �г� �־�α�
	        center_jp.add(bestPanel, "best");
	        center_jp.add(noodlePanel, "noodle");
	        center_jp.add(ricePanel, "rice");
	        center_jp.add(addPanel, "add");
	        center_jp.add(coffeePanel, "coffee");
	        center_jp.add(sidePanel, "side");
	        center_jp.add(snackPanel, "snack");
	        
	        //�гε� ����
	        noodlePanel.setLayout(new GridLayout(3,3));
	        ricePanel.setLayout(new GridLayout(3,3));
	        bestPanel.setLayout(new GridLayout(3,3));
	        snackPanel.setLayout(new GridLayout(3,3));
	        sidePanel.setLayout(new GridLayout(3,3));
	        addPanel.setLayout(new GridLayout(3,3));
	        coffeePanel.setLayout(new GridLayout(3,3));
	        
	        //��ư�� �̹���, �̸�, �� �ֱ�
	        String coffee_n = "�ƾ�,���̽�Ƽ,������,��ġ�� ����,ĥ�����̴�,ĭŸŸ��,���,�־Ƹ޸�ī��";
		    String coffee_p = "1500,1500,700,1800,1800,2000,1800,1500";
			String[] c_name = coffee_n.split(",");
	        String[] c_price = coffee_p.split(",");
	        for (int i = 0; i < coffeeFoods.length; ++i) { //i<coffeeFoods.length���� ���Ŀ� �ٲٱ�
	        	if (dao.list(c_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/ǰ��.jpg");
	            	coffeeFoods[i] = new Food(imgIcon, c_name[i], Integer.parseInt(c_price[i]));
	            	coffeeFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + c_name[i] + ".jpg");
	            	coffeeFoods[i] = new Food(imgIcon, c_name[i], Integer.parseInt(c_price[i]));
	            	coffeeFoods[i].addActionListener(this);
	            }
	            coffeePanel.add(coffeeFoods[i]);
	        	coffeeFoods[i].repaint();
	        }  
	        String side_n = "����Ƣ��,������,�踻��,�߲�ġ,�Ҽ���,����ġŲ,�����ð�,����,�ֵ���"; 
		    String side_p = "2500,2500,2500,3000,3000,3500,700,2500,3000";
			String[] si_name = side_n.split(",");
	        String[] si_price = side_p.split(",");
	        for (int i = 0; i < sideFoods.length; ++i) {
	        	if (dao.list(si_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/ǰ��.jpg");
	                sideFoods[i] = new Food(imgIcon, si_name[i], Integer.parseInt(si_price[i]));
	                sideFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + si_name[i] + ".jpg");
	            	sideFoods[i] = new Food(imgIcon, si_name[i], Integer.parseInt(si_price[i]));
	            	sideFoods[i].addActionListener(this);
	            }
	            sidePanel.add(sideFoods[i]);
	        	sideFoods[i].repaint();
	        }        
	        String best_n = "�ֵ���,ġŲ���䵤��,�Ŷ��,¥�ı���,����Ƣ��,����ġŲ,��ġ������,�Ҽ���,����鼼Ʈ";
		    String best_p = "3000,4800,2000,2700,2500,3500,4500,3000,3000";
			String[] b_name = best_n.split(",");
	        String[] p_price = best_p.split(",");
	        for (int i = 0; i < bestFoods.length; ++i) {
	            if (dao.list(b_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/ǰ��.jpg");
	                bestFoods[i] = new Food(imgIcon, b_name[i], Integer.parseInt(p_price[i]));
	                bestFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + b_name[i] + ".jpg");
	                bestFoods[i] = new Food(imgIcon, b_name[i], Integer.parseInt(p_price[i]));
	                bestFoods[i].addActionListener(this);
	            }
	            bestPanel.add(bestFoods[i]);
	            bestFoods[i].repaint();
	        }        
	        String noodle_n = "�Ŷ��,�ʱ���,�Ҵߺ�����,���ζ��,����鼼Ʈ,¥��ġ,¥�İ�Ƽ,¥�ı���,�����";
		    String noodle_p = "2000,2000,2500,2500,3000,3500,2300,2700,2000";
			String[] n_name = noodle_n.split(",");
	        String[] n_price = noodle_p.split(",");
	        for (int i = 0; i < noodleFoods.length; ++i) {
	        	if (dao.list(n_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/ǰ��.jpg");
	                noodleFoods[i] = new Food(imgIcon, n_name[i], Integer.parseInt(n_price[i]));
	                noodleFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + n_name[i] + ".jpg");
	            	noodleFoods[i] = new Food(imgIcon, n_name[i], Integer.parseInt(n_price[i]));
	            	noodleFoods[i].addActionListener(this);
	            }
	            noodlePanel.add(noodleFoods[i]);
	        	noodleFoods[i].repaint();
	        }      
	        String rice_n = "��ġ������,�ٺ�ť����,��������,ġŲ���䵤��,��ġ���䵤��,��������";
		    String rice_p = "4500,5000,5000,4800,4800,4000";
			String[] r_name = rice_n.split(",");
	        String[] r_price = rice_p.split(",");
	        for (int i = 0; i < riceFoods.length; ++i) {
	        	if (dao.list(r_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/ǰ��.jpg");
	                riceFoods[i] = new Food(imgIcon, r_name[i], Integer.parseInt(r_price[i]));
	                riceFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + r_name[i] + ".jpg");
	            	riceFoods[i] = new Food(imgIcon, r_name[i], Integer.parseInt(r_price[i]));
	            	riceFoods[i].addActionListener(this);
	            }
	        	ricePanel.add(riceFoods[i]);
	        	riceFoods[i].repaint();
	        }  
	        String snack_n = "��Ĩ,��¡����,��īĨ,�����۽�,Ȩ����,����Ĩ"; 
		    String snack_p = "1500,1200,1800,2500,2500,1800";
			String[] s_name = snack_n.split(",");
	        String[] s_price = snack_p.split(",");
	        for (int i = 0; i < snackFoods.length; ++i) {
	        	if (dao.list(s_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/ǰ��.jpg");
	            	snackFoods[i] = new Food(imgIcon, s_name[i], Integer.parseInt(s_price[i]));
	            	snackFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + s_name[i] + ".jpg");
	            	snackFoods[i] = new Food(imgIcon, s_name[i], Integer.parseInt(s_price[i]));
	            	snackFoods[i].addActionListener(this);
	            }
	            snackPanel.add(snackFoods[i]);
	            snackFoods[i].repaint();
	        }      
	        String add_n = "����Ķ���,�����,���,ġ��";
		    String add_p = "500,1000,300,500";
			String[] a_name = add_n.split(",");
	        String[] a_price = add_p.split(",");
	        for (int i = 0; i < addFoods.length; ++i) {
	        	if (dao.list(a_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/ǰ��.jpg");
	            	addFoods[i] = new Food(imgIcon, a_name[i], Integer.parseInt(a_price[i]));
	            	addFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + a_name[i] + ".jpg");
	            	addFoods[i] = new Food(imgIcon, a_name[i], Integer.parseInt(a_price[i]));
	                addFoods[i].addActionListener(this);
	            }
	            addPanel.add(addFoods[i]);
	            addFoods[i].repaint();
	        }  
	        
	        //�ֹ��������� �ֹ�, ��� ��ư
	        east_jp.setLayout(new BorderLayout());
	        mini_jp.setLayout(new GridLayout(1, 4));
	        setTextArea();
	        east_jp.setPreferredSize(new Dimension(300, 300));
	        east_jp.add("Center", menu_ta);
	        east_jp.add("South", mini_jp);
	        mini_jp.add(money_bt);
	        money_bt.addActionListener(this);
	        mini_jp.add(card_bt);
	        card_bt.addActionListener(this);
	        mini_jp.add(cancle_bt);
	        cancle_bt.addActionListener(this);
	        mini_jp.add(exit_bt);
	        exit_bt.addActionListener(this);
	    }

	    //�ֹ����� �ֱ�
	    public void setTextArea() {
	        menu_ta.setText("\t           �ֹ�������");
	        menu_ta.append("\n===========================================");
	        total = 0;
	        for (Food fd : list) {
	            menu_ta.append("\n" + fd.getName() + " \t\t" + df.format(fd.getPrice()) + "��");
	            total += fd.getPrice();
	        }
	        if (total > 0) {
	            menu_ta.append("\n===========================================");
	            menu_ta.append("\n�ѱݾ� \t\t" + df.format(total) + "��");
	        }
	    }
	    
	    //�ֹ�, ��ҽ� ���� �޴� ����
	    public void clear() {
	        menu_ta.setText("\t           �ֹ�������");
	        menu_ta.append("\n===========================================");
	        list.clear();
	        total = 0;
	    }
	    
	    public FoodGUI(JFrame owner, String title, boolean modal) {
	        super(owner, title, modal);
	        this.init();
	        super.setSize(1300, 800);
	        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	        int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
	        int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
	        
	        this.setLocation(xpos, ypos);
	        this.setResizable(false);
	        this.setVisible(false);
	    }
	    //FoodGUI â ���� ����
	  	public void isview(boolean b) {
	  		this.setVisible(b);
	  	}
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	//��ư Ŭ���� �з��� �޴� �̹��� �гη� ��ȯ
	    	if (e.getSource() == best_bt) {
	    		cards.show(center_jp, "best");
	    	}else if (e.getSource() == noodle_bt) {
	    		cards.show(center_jp, "noodle");
	    	}else if (e.getSource() == rice_bt) {
	    		cards.show(center_jp, "rice");
	    	}else if (e.getSource() == snack_bt) {
	    		cards.show(center_jp, "snack");
	    	}else if (e.getSource() == coffee_bt) {
	    		cards.show(center_jp, "coffee");
	    	}else if (e.getSource() == add_bt) {
	    		cards.show(center_jp, "add");
	    	}else if (e.getSource() == side_bt) {
	    		cards.show(center_jp, "side");
	    	}
	    	//�����̳� ī�� ����
	    	if (e.getSource() == money_bt || e.getSource() == card_bt) { 
	    		//menu_n = menu_n.substring(7);
	    		for (Food food : list) {
	    			String menuName = food.getName(); 
	    			menu_n += " + " + menuName;
	    			if (dao.list(menuName) > 0) {
	    				if (dao.update(menuName)) {
	    				}else {
	    					JOptionPane.showMessageDialog(this, menuName + "��/�� �ֹ� �����߽��ϴ�." );
	    			}
	    			//��� ������ ���
	    			}else {
	    				JOptionPane.showMessageDialog(this, menuName + "��/�� ǰ���Դϴ�.");
	    			}
	    		}
	    		dao.updateMoney(total, str);
	    		JOptionPane.showMessageDialog(this, "�ֹ��� �Ϸ�ƽ��ϴ�.");
	    		//ClientMessage.sendMeniList = menu_n;
	    		String sendString = menu_n;
	    		cl.sendClientBuy(id, 0, sendString,"0", "Food");
	    		list.clear();
	    		menu_n = "";
	    		clear();
	    	}
	    	//��� ��ư
	    	else if (e.getSource() == cancle_bt) {
	    		list.clear();
	    		clear();
	    	}
	    	//������ ��ư
	    	else if (e.getSource() == exit_bt) {
	    		list.clear();
	    		dispose();
	    	}
	    	//��ư Ŭ���� �ֹ����� �̸�, ���� �ֱ�
	    	for (Food[] foods : new Food[][]{bestFoods, noodleFoods, riceFoods, snackFoods, sideFoods, addFoods, coffeeFoods}) {    	
	    		for (int i = 0; i < foods.length; i++) {
	                if (foods[i] == e.getSource()) {
	                    list.add(foods[i]);
	                    setTextArea();
	                    break;
	                }
	    		}
			}
	}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
	public static void main (String[] args) {
		cg = new ClientGUI("OzPC CAFE");
	}
}