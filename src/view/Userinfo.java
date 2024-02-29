package view;
import java.util.ArrayList;

public class Userinfo {
	private ArrayList<UserDTO> users;
	
	public Userinfo() {
		users = new ArrayList<UserDTO>();
	}
	
	// ȸ�� �߰�
	public void addUsers(UserDTO user) {
	        users.add(user);
	}
	// ���̵� �ߺ� Ȯ��
    public boolean isIdOverlap(String id) {
    	return users.contains(new UserDTO(id));
    }
    // ȸ�� ����
	public void withdraw(String id) {
       users.remove(getUser(id));
    }
	// ���� ���� ��������
	public UserDTO getUser(String id) {
		return users.get(users.indexOf(new UserDTO(id)));
	}
	// ȸ������ �ƴ��� Ȯ��
	public boolean contains(UserDTO user) {
		return users.contains(user);
	}
	

}