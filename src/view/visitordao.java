package view;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import client.Exit;


public class visitordao {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	String url, user, pw;		
	UserDTO2 dto;
	
	
	
	public visitordao() {	//�����ͺ��̽��� �����Ѵ�.
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("�����̾߼���");
		} catch(ClassNotFoundException e) {
			System.out.println("���� ����");
			e.printStackTrace();
		}
		url = "jdbc:oracle:thin:@localhost:1521:xe";
		user = "fin01";
		pw = "fin01";
		
	}

		// ȸ������
	public int userInsert1(String id, String pwd, String name, String birth, String phone, String email) {
			String sql1 = "insert into visitor(id, pwd, name, birth, phone, email)" + 
						 "values(?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = null;
			int res = 0;
		
		try {
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql1);
			dto = new UserDTO2(id, pwd, null, name, birth, phone, email);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPwd());
			ps.setString(3, dto.getName());
			ps.setString(4, dto.getBirth());
			ps.setString(5, dto.getPhone());
			ps.setString(6, dto.getEmail());
			res = ps.executeUpdate();
			
			
		}catch(SQLException e) {
			System.err.println("insert ���� �߻�");
			e.printStackTrace();
			
		}finally {
			//DB close
			try {
				if(con != null) {
					con.close();
				}if(ps != null) {
					ps.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
		
	}
		// �α���
	public boolean userLogin1(String id, String pwd) {
		String sql1 = "select * from visitor where id=? and pwd=?";
		try {
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql1);
			
			ps.setString(1, id);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			return false;
		}catch(SQLException e) {
			System.out.println("�α��� SQL ����");
		}finally {
			try {
				if(rs!= null) {
					rs.close();
				}
				if(ps!= null) {
					con.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public int insertDate(String today) {
		try {
			con = DriverManager.getConnection(url, user, pw);
			con = new Exit().getCon();
			String sql3 = "insert into datetable values(?, 0)";
			ps = con.prepareStatement(sql3);
			ps.setString(1, today);
			int res = ps.executeUpdate();
			System.out.println("����");
			return res;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("insert �޼ҵ� ����");
			return 0;
		}
	}
		//DB���� ȸ�����
	/*public List<UserDTO2> listTest(String id, String pwd, String name, String birth, String phone, String email){
		try {
			String sql = "select * from visitor order by no asc";
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			List<UserDTO2> list = new ArrayList<>();
			while(rs.next()) {
				dto = new UserDTO2(id, pwd, null, name, birth, phone, email);
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setPhone(rs.getString("phone"));
				dto.setEmail(rs.getString("email"));
				list.add(dto);
			}
			return list;
		}catch(SQLException e) {
			System.err.println("list �޼ҵ� ���� �� ���� �߻�!!");
			e.printStackTrace();
			return null;
		}
	}*/
	

	private List<UserDTO2> listTest() {
		
			try {
				String sql = "select * from visitor order by no asc";
				con = DriverManager.getConnection(url, user, pw);
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				List<UserDTO2> list = new ArrayList<>();
				while(rs.next()) {
					
					dto = new UserDTO2();
					dto.setNo(rs.getInt("no"));
					dto.setId(rs.getString("id"));
					dto.setPwd(rs.getString("pwd"));
					dto.setName(rs.getString("name"));
					dto.setBirth(rs.getString("birth"));
					dto.setPhone(rs.getString("phone"));
					dto.setEmail(rs.getString("email"));
					list.add(dto);
				}
				return list;
			}catch(SQLException e) {
				System.err.println("list �޼ҵ� ���� �� ���� �߻�!!");
				e.printStackTrace();
		return null;
	}
}
	public void disp() {
		List<UserDTO2> list = listTest();
		for (UserDTO2 dto : list) {
			System.out.println(dto.getId());
			System.out.println(dto.getPwd());
			System.out.println(dto.getName());
			System.out.println(dto.getBirth());
			System.out.println(dto.getPhone());
			System.out.println(dto.getEmail());
		}
	}
	}


		