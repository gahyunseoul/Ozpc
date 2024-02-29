package view;
import java.sql.*;


public class memdao {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	String url, user, pw;		
	Userinfo users;
	UserDTO dto;
	
	public memdao() {	//�����ͺ��̽��� �����Ѵ�.
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("���� ����");
		} catch(ClassNotFoundException e) {
			System.out.println("���� ����");
			e.printStackTrace();
		}
		url = "jdbc:oracle:thin:@localhost:1521:xe";
		user = "fin01";
		pw = "fin01";
	}

		// ȸ������
	public int userInsert(String id, String pwd, String name, String birth, String phone, String email) {
			String sql = "insert into member(id, pwd, name, birth, phone, email)" + 
						 "values(?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = null;
			int res = 0;
		
		try {
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql);
			dto = new UserDTO(id, pwd, null, name, birth, phone, email);
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
	public boolean userLogin(String id, String pwd) {
		String sql = "select * from member where id=? and pwd=?";
		try {
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql);
			
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
	}	
		