package pj;

import java.util.*;

public class UserTimeProImpl implements UserTimePro {
	UserTimeDAO dao = new UserTimeDAO();
	Scanner in = new Scanner(System.in);
	
	@Override
	
	public int UserLogin(String id) {
		return dao.getTime(id);
		// getTime(id);
		// DB���� ���̵�, ���� �ð� �� �޾ƿ� �� gui ȭ�鿡 ��� �ش�
	}

	@Override
	public void UserLogout(String id, int time) {
		dao.setTime(id, time);
	}



}