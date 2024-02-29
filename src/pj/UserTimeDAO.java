package pj;


import java.sql.*;

public class UserTimeDAO {	// ������ ���̽� ����, CRUD ���
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	String url, user, pass;
	
	public UserTimeDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch (ClassNotFoundException e) {
			System.err.println("����̹� �˻� ���� !!");
			e.printStackTrace();
		}
		
		url = "jdbc:oracle:thin:@localhost:1521:xe";
		user = "fin01";
		pass = "fin01";
		
	}
		

	
	// ���̵�� �ð� ��������
	public int getTime(String id) {	
		try {
			System.out.println("DAO ���� id : " + id);
			String sql = "select time from visitor where id = ?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, id);  // �ε����� 1���� �����ϸ�, id ���� ����
			
			rs = ps.executeQuery();
			
            if (rs.next()) {
            	int user_time = rs.getInt("time");
                return user_time;
            } else {
                // �ش� ���̵��� ���ڵ尡 ���� �� ó��
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("getTime �޼��� ���� �� ���� �߻�");
            return 0;
        }
	}
	
	// ���̵� ���� �ð� ������ �ֱ�
	public void setTime(String id, int time) {
		try {
			String sql = "update visitor set time = ? where id = ?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setInt(1, time);
			ps.setString(2, id);
			ps.executeUpdate();
		}catch(SQLException e) {
			System.err.println("insert �޼ҵ� ���� �� ���� �߻�!!");
			e.printStackTrace();
		}
	}







	



	
		
	}
