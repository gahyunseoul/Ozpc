package client;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;

import client.FoodDTO;
import client.FoodDAOUpdate;
import pj.ClientMessage;
//�޴���ư�� Ŭ���� �̸��� ������ �ֹ��������� ���� ���� �̹���, �̸�, ���� ����
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

public class FoodGUI extends JFrame implements ActionListener{
	FoodDAOUpdate dao = new FoodDAOUpdate();
	FoodDAOList daoList = new FoodDAOList();
	FoodDTO dto = new FoodDTO();
	Exit exit = new Exit();
	ClientMessage cm = new ClientMessage();

	private String menu_n = "";
	
	private int total;
	
	public int getTotal() {
		return total;
	}
	
	LocalDate now = LocalDate.now();	
	String str = now.toString();
	
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
    private Image img = Toolkit.getDefaultToolkit().getImage("src/pcfood.jpg");
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
        for (int i = 0; i < coffeeFoods.length; ++i) {
        	if (daoList.list(c_name[i]) == 0) {
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
        	if (daoList.list(si_name[i]) == 0) {
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
            if (daoList.list(b_name[i]) == 0) {
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
        	if (daoList.list(n_name[i]) == 0) {
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
        	if (daoList.list(r_name[i]) == 0) {
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
        	if (daoList.list(s_name[i]) == 0) {
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
        	if (daoList.list(a_name[i]) == 0) {
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
        
        //â �ݱ�
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
    
    public FoodGUI(String title) {
        super(title);
        this.init();
        super.setSize(1300, 1000);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
        int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
        
        this.setLocation(xpos, ypos);
        this.setResizable(false);
        this.setVisible(true);
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
    		for (Food food : list) {
    			String menuName = food.getName(); 
    			menu_n += " + " + menuName;
    			if (daoList.list(menuName) > 0) {
    				if (dao.update(menuName)) {
    				}else {
    					JOptionPane.showMessageDialog(this, menuName + "��/�� �ֹ� �����߽��ϴ�." );
    			}
    			
    			//��� ������ ���
    			}else {
    				JOptionPane.showMessageDialog(this, menuName + "��/�� ǰ���Դϴ�.");
    			}
    		}
    		exit.updateMoney(total, str);
    		JOptionPane.showMessageDialog(this, "�ֹ��� �Ϸ�ƽ��ϴ�.");
    		
    		
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
}
