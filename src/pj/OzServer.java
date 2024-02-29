package pj;
import java.io.*;
import java.util.*;

import view.*;

import java.net.*;
import client.*;
import pj.*;

// ���� ���� ����
public class OzServer{
	private ServerSocket ss;
	private Socket soc;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Scanner sc;
	private ClientMessage cm;
	
	// ��ü�� �����ϱ� ���� hashtable �ڷ� ���� ���
	// hashtable �ڷ� ���� ���� ���� :
	// hashtable�� ����ȭ�� �޼���� �����Ǿ� �ֱ� ������ ��Ƽ �����尡 ���ÿ� hashtable�� �޼ҵ����
	// ������ �� ����, ���� ��Ƽ ������ ��Ȳ������ �����ϰ� ��ü�� �߰��ϰ� ������ �� �ִ�
	
	private Hashtable<String, ClientMessage> clientMHT  = new Hashtable<>();	// Ŭ���̾�Ʈ�� �����ϴ� hashtable
	private Hashtable<String, Integer> seatNum = new Hashtable<>();	// �ڸ��� �����ϴ� hashtable
	private Hashtable<String, TimeSetThread> timeSetHt = new Hashtable<>();	
	private Hashtable<String, ChatServerGUI>chatHt = new Hashtable<>();	// ä�� ���α׷� ���� �ִ��� ���� �ִ��� Ȯ��
	private Hashtable<String, ServerChatThread>serChatHt = new Hashtable<>();
	
	UserTimePro userTime = new UserTimeProImpl();
	OzServerGui oz = new OzServerGui("������ ȭ��");
	
	// ���� ���� ����
	public OzServer() {
		try {
			ss = new ServerSocket(24511);
			System.out.println("���� ��� ��.....");
			
			//sc = new Scanner(System.in);
			
			//serverGUI = new view.OzServerGui("������ ����");
			
			while (true) {
				soc = ss.accept();	
				System.out.println(soc.toString());// ������ ���������� ���� ������
				OzClient ozc = new OzClient(soc);	// ���� Ŭ���̾�Ʈ ��ü ������ ����
				ozc.start();						// Ŭ���̾�Ʈ ������ ��ŸƮ
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// �α��� �ϸ� => DB�� �ִ� �ð� Ȯ�� �� Ŭ���̾�Ʈ���� �ð� �˷��ֱ�
    public void login(Socket soc, String id) {
    	cm = clientMHT.get(id);	
        System.out.println("server sendTime id : " + id);
        oz.addMessageToAlarm("@" + id + "���� �α��� �Ͽ����ϴ�.\n");
        
        // �ڸ� ���� ���� : �̹� ���õ� �ڸ��� ���� X
        boolean duplication;
        while (true) {
            Enumeration<String> keys = seatNum.keys();
            int seat = (int) ((Math.random() * 30) );
            duplication = false; // ���� ���� �� duplication�� �ʱ�ȭ

            while (keys.hasMoreElements()) {
                String key = keys.nextElement(); // ���� �ݺ� ���� Ű ��������
                if (seatNum.get(key).equals(seat)) {
                    System.out.println("�ڸ� " + seat + "�� �̹� �ٸ� ����ڰ� �����Ǿ� �ֽ��ϴ�.");
                    duplication = true;
                    break;
                }
            }
            if (duplication) continue;
            seatNum.put(id, seat);
            System.out.println("id :" + seatNum.get(id));
            break;
        }
		System.out.println("�ڸ� " + (seatNum.get(id)+1) + "�� �� " + id + " ���� �Ϸ�.");
    }
    
	
    // �α׾ƿ� �ϸ� => ���̵� ���� �ð� �� DB�� �����ϱ�
    public void logout(Socket soc, String id) throws Exception{
    	try {
    		// ������ �α׾ƿ� �Ѵٰ� ���� id                  
            System.out.println("server get logout id : " + cm.getSendId());
            // ������ �޽��� ������
            oz.addMessageToAlarm("@" + cm.getSendId() + "���� �α׾ƿ� �Ͽ����ϴ�.\n");
            int num = seatNum.get(id);
            System.out.println("�ڸ� �� : " + num);
            oz.ititial_seat(num+1);
            seatNum.remove(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }	
    }
  
	// Ŭ���̾�Ʈ ��ü ������
	class OzClient extends Thread {
		private boolean stop = false;
		Socket soc;
		ClientMessage cm;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;

		OzClient (Socket soc) {	// Ŭ���̾�Ʈ�� ���� ��ȣ �б�
			this.soc = soc;
		}

		public void run() {
			while (true) {
				try{
					try {
		        	oos = new ObjectOutputStream(new BufferedOutputStream((soc.getOutputStream())));
		        	ois = new ObjectInputStream(new BufferedInputStream((soc.getInputStream())));
		            cm = (ClientMessage) ois.readObject();
		            cm.disp();
					}catch(Exception e) {}
					
					String type = cm.getSendType();
					String id = cm.getSendId();
					System.out.println(id);
					System.out.println(type);
					if ("login".equals(type)) {
						clientMHT.put(id, cm);	// id ���̶� clientMessage ��ü ����
						int time = userTime.UserLogin(id);		// DB���� id ���� ����� time �ҷ�����
						//System.out.println(time);
						login(soc, id);		// �α��� �޼��� ����
						int addtime = 0;
						TimeSetThread tst = new TimeSetThread(soc, id, time, addtime); // time thread ����
						timeSetHt.put(cm.getSendId(), tst);	// timethread ��ü ����
						tst.start();	// ������ ����
						//oos.close();
					}else if("logout".equals(type)){
						try {
							logout(soc, id);	// ���⸦ �� �Ѿ�� �������� ����
							System.out.println("�α׾ƿ� �� ����");
							//�α׾ƿ� �ϴ� timeSetThread ��������
							System.out.println(timeSetHt.get(cm.getSendId()));
							TimeSetThread tst_logout = timeSetHt.get(cm.getSendId());
							tst_logout.interrupt();	// interrupt �߻����� ������ ����
							ClientMessage oldcm = clientMHT.get(cm.getSendId());

							try {
								chatHt.remove(id);	// ä�� �α��� ��� ����
								ServerChatThread sct = serChatHt.get(id);	// ä�� ������ ����
								sct.interrupt();
								serChatHt.remove(cm.getSendId());		// ä�� ������ ����
								
							}catch(Exception e) {
								System.out.println("ä���� �������� ���� �մ�");
							}
							
							// Ŭ���̾�Ʈ ��ü ��������
							userTime.UserLogout(id, timeSetHt.get(cm.getSendId()).getTime());
							System.out.println("DB�� ���� �ð� ���� : " + timeSetHt.get(cm.getSendId()).getTime());
							clientMHT.remove(cm.getSendId(), oldcm);	// ��ü ����
							//timeSetHt.remove(cm.getSendId(), tst_logout);	// ������ ����
							
							//oos.close();
							this.interrupt();
							break;
						}catch(EOFException e){
							e.printStackTrace();
						}
					}else if("Time".equals(type)) {
						TimeSetThread tst_logout = timeSetHt.get(cm.getSendId());
						// ���� id �� ����Ǿ� �ִ� time set thread ������
						int time = tst_logout.getTime();	// ���� ��ü�� �ִ� �ð� ������
						tst_logout.interrupt();	// interrupt �߻����� ���� ������ ����
						int addtime = cm.getSendAddTime(); // ������ �ð�
						TimeSetThread tst = new TimeSetThread(soc, id, time, addtime);
						// ���ο� time thread ����
						timeSetHt.put(cm.getSendId(), tst);
						tst.start();
						oz.addMessageToAlarm(seatNum.get(id) +" �� �ڸ� "+ cm.getSendId() + "���� " + (addtime/60) + " �� �߰��ϼ̽��ϴ�\n");
						//oos.close();
					}else if("Food".equals(type)) {
						oz.addMessageToAlarm("@" + cm.getSendId() + "���� " + seatNum.get(id) + "�� �ڸ�����" + cm.getSendMenuList() + " �ֹ��ϼ̽��ϴ�\n");
						
					//oos.close();
					}else if("chat".equals(type)) {
						ServerChatThread sct = new ServerChatThread(soc, id, cm);
						serChatHt.put(id, sct);
						sct.start();
						// ���� Ŭ���̾�Ʈ���� ä���� �����ٸ�?
						// ä�� �����尡 ������ �ϹǷ� Ŭ���̾�Ʈ���� chatStop ��ȣ�� �ޱ�
					}
	
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		
		}	
	}
	
	// �ð� ���� ������
	public class TimeSetThread extends Thread {
		Socket soc;
		String id;
		int time;
		int addtime;
		int sum;
		
		public TimeSetThread(Socket soc, String id, int time, int addtime) {
			this.soc = soc;
			this.id = id;
			this.time = time;
			this.addtime = addtime;
			sum = time+addtime;
		}
		
		public int getTime() {
			return sum;
		}
		
		public void run() {
            while (!interrupted()) {

            	// System.out.println("���� �ð�" + sum);
                oz.set_start_seat((seatNum.get(cm.getSendId())+1), sum);	// �ð��� ������ ����
                sendTime(id, sum);
                
                
                try {
                    Thread.sleep(60000);	// 60�ʵ��� ���
                }catch (Exception e) {
                    //e.printStackTrace();
                    break;
                }
                
                sum -= 60; // �ð��� 60�ʾ� ����
                
            }
            System.out.println("���ҽ� ����");
            System.out.println("Ÿ�� ������ ����");
		}
	}

	// Ŭ���̾�Ʈ���� �ð� ������ ������
	public void sendTime(String id, int time) {
    	try {
    		oos = new ObjectOutputStream(new BufferedOutputStream((soc.getOutputStream())));
			ClientMessage cm = new ClientMessage();
			cm.setSendAddTime(time);
			cm.setSendType("Time");
			cm.disp();
			
			try {
				oos.writeObject(cm);
				oos.flush();
			}catch(IOException e) {
				e.printStackTrace();
			}
			System.out.println("Ŭ���̾�Ʈ�� �ܿ��ð� ���� ����!" + time);

		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	// �޽��� �޴� ������ : �޽��� ������ ChatServerGUI ���� ��
	public class ServerChatThread extends Thread implements Serializable {
		Socket soc;
		String id;
		boolean duplication;
		ClientMessage cm;
		
		public ServerChatThread(Socket soc, String id, ClientMessage cm) {
			this.soc = soc;
			this.id = id;
			this.cm = cm;

			// ���� ä���� ���� ���� �ʴٸ�? 
	        while (true) {
	        	
	            Enumeration<String> keys = chatHt.keys();
	            duplication = false; // ���� ���� �� duplication�� �ʱ�ȭ

	            while (keys.hasMoreElements()) {
	            	try {
	            		if (!chatHt.get(id).isVisible()) break;
	            	}catch(Exception e) {}
	                String key = keys.nextElement(); // ���� �ݺ� ���� Ű ��������
	                if (key.equals(id)) {
	                    System.out.println(id + " �԰��� ä���� ���� �ֽ��ϴ�.");
	                    duplication = true;
	                    break;
	                }
	            }
	            if (duplication) break;
	            oz.addMessageToAlarm("@" + cm.getSendId() + "���� ä���� �����ϼ̽��ϴ�.\n");
	            ChatServerGUI csg = new ChatServerGUI(id + "�� ���� ä���Դϴ�", soc, id);
	            chatHt.put(id, csg);
	            System.out.println(id + " �԰��� ä���� �������ϴ�.");
	            break;
	        }
			
		}
		
		public void run() {
			try{
				String idcm = cm.getSendId();
				String message = cm.getSendChat();
				
				
				chatHt.get(id).clientMessage(id, message);
				// Ŭ���̾�Ʈ �޽����� chatServerGUI���� �����ֱ�-
				System.out.println(cm.getSendId());
				System.out.println(cm.getSendChat());
				// csg�� id���� message ���� �༭ Ŭ���̾�Ʈ �޽��� �ޱ�
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

    
	
	public static void main(String[] args) {
		new OzServer();
	}
}