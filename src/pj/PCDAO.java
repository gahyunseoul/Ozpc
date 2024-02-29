package pj;
import java.sql.*;
import javax.swing.table.*;
import java.util.ArrayList;
import java.util.List;

//import client.Exit;
import client.FoodDAOUpdate;
import client.FoodDTO;

public class PCDAO {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	String url, user, pass;
	Statement st;
	
	public Connection getCon() {
		return con;
	}
	
	public PCDAO(){
		try {
			url = "jdbc:oracle:thin:@localhost:1521:xe";
			user = "fin01";
			pass = "fin01";
			//con = DriverManager.getConnection(url, user, pass);
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e) {
			System.err.println("PCDAO() ���� �� ���� �߻�!!");
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("PCDAO() ���� �� ���� �߻�!!");
		}
	}
	
	
	
	//�ֹ� �� ��� -1 ����
		public boolean update(String name) {
			int flag = 0;	
			try {
				con = DriverManager.getConnection(url, user, pass);
				//con = new FoodDAOUpdate().getCon();
				String sql = "update menuname set stock=stock-1 where name=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, name);
				flag = ps.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("menuname update �޼ҵ� ����");
			}finally {
				try {
					if (ps != null) ps.close();
					if (con != null) con.close();
				}catch (SQLException e) {}
			}
			if (flag > 0) {
				return true;
			}else {
				return false;
			}
		}
		
	//�α��� ���ο� ���� DB�� �α��� ���� ����
	public int login(String id, String tf) {
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "update visitor set login=? where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1,tf);
			ps.setString(2,id);
			int res = ps.executeUpdate();
			return res;
		}catch(SQLException e) {
			System.err.println("login �޼ҵ� ���� �� ���� �߻�!!");
			e.printStackTrace();
			return 0;
		}
	}
	//(server��ſ�) id�� DB���� ��ȸ�� ȸ���� ������ ClientMessage�� ����(�α���, �α׾ƿ�)
	public ClientMessage selectLogintPc(String id){
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select * from visitor where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1,id);
			rs = ps.executeQuery();
			ClientMessage cm = new ClientMessage();
			while(rs.next()) {
				cm.setSendLoginox(rs.getString("login"));
				cm.setSendId(rs.getString("id"));
				cm.setSendMenuList("0");
				cm.setSendAddTime(0);
			}
			return cm;
		}catch(SQLException e) {
			System.err.println("selectPc �޼ҵ� ���� �� ���� �߻�!!");
			e.printStackTrace();
			return null;
		}
	}
	//������ �ð��� DB�� ������ �޼ҵ�
	public int buyTimePricePC(int sumPrice, String id) {
		int DBPrice = setPrice(id); 
		try {
			con = DriverManager.getConnection(url, user, pass);
			DBPrice += sumPrice;
			System.out.println("�����ͺ��̽� ���� �ݾ� : " + DBPrice);
			String sql = "update visitor set price=? where id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, DBPrice);
			ps.setString(2, id);
			int res = ps.executeUpdate();
			return res;
		}catch(SQLException e) {
			System.err.println("buyTimePricePC �޼ҵ� ���� �� ���� �߻�!!");
			e.printStackTrace();
			return 0;
		}
	}
	//DB�� ����ݾ� �ҷ��ͼ� ��ȯ�ϱ�
	public int setPrice(String id) {
		int dbPrice = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select price from visitor where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				dbPrice = rs.getInt("price");
			}
			return dbPrice;
		}catch(SQLException e) {
			System.err.println("setPrice �޼ҵ� ���� �� ���� �߻�!!");
			e.printStackTrace();
			return -1;
		}
	}
	//��� �������� ����
	public int list(String name) {
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select name, stock from menuname where name=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			List<FoodDTO> list = new ArrayList<>();
			while (rs.next()) {
				FoodDTO dto = new FoodDTO();
				dto.setStock(rs.getInt("STOCK"));
				list.add(dto);
			}
			for (FoodDTO dto : list) {
				return dto.getStock();
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("menuname list�޼ҵ� ����");
			return 0;
		}
		return 0;
	}
	//DB�κ��� ��ư�� ��� ��������
	public int buttonPrice(String buttonName) {
		int bPrice = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select buttonprice from button where name=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, buttonName);
			rs = ps.executeQuery();
			while(rs.next()) {
				bPrice = rs.getInt("buttonPrice");
			}
			//System.out.println("���� ��ư�� �ݾ� : " + bPrice);
			return bPrice;
		}catch(SQLException e) {
			System.err.println("buttonPrice �޼ҵ� ���� �� ���� �߻�!!");
			e.printStackTrace();
			return -1;
		}
	}
	//DB�κ��� ��ư�� �ð� ��������
	public int buttonTime(String buttonName) {
		int bTime = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select buttontime from button where Name=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, buttonName);
			rs = ps.executeQuery();
			while(rs.next()) {
				bTime = rs.getInt("buttonTime");
			}
			//System.out.println("���� ��ư�� �ð� : " + bTime);
			return bTime;
		}catch(SQLException e) {
			System.err.println("buttonTime �޼ҵ� ���� �� ���� �߻�!!");
			e.printStackTrace();
			return -1;
		}
	}
	public int insertDate(String today) {
		try {
			con = DriverManager.getConnection(url, user, pass);
			//con = new Exit().getCon();
			String sql = "insert into datetable values(?, 0)";
			ps = con.prepareStatement(sql);
			ps.setString(1, today);
			int res = ps.executeUpdate();
			System.out.println("����");
			return res;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("datetable insert �޼ҵ� ����");
			return 0;
		}
	}
	public boolean updateMoney (int money, String today) {
		int flag = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			//conn = new Exit().getCon();
			String sql = "update datetable set money=money+? where datecol=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, money);
			ps.setString(2, today);
			flag = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("datetable update �޼ҵ� ����");
		}finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			}catch(SQLException e) {}
		}
		if (flag > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<FoodDTO> list() {
		try {
			con = DriverManager.getConnection(url, user, pass);
			//con = new Exit().getCon();
			String sql = "select distinct * from datetable";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			List<FoodDTO> list = new ArrayList<>();
			while (rs.next()) {
				FoodDTO dto = new FoodDTO();
				dto.setTotal(rs.getInt("money"));
				dto.setToday(rs.getString("datecol"));
				list.add(dto);
			}
			return list;
		}catch(SQLException e) {
			System.out.println("datetable list �޼ҵ� ����");
			e.printStackTrace();
			return null;
		}
	}
	//db close 
	public void dbClose() {
	        try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
	            if (ps != null) ps.close();
	        } catch (Exception e) {
	            System.out.println(e + "=> dbClose fail");
	    }
	}
	
	//���� ���̺� ���� ��������
	public void selectAll(DefaultTableModel t_model) {
		try {
			con = DriverManager.getConnection(url, user, pass);
			st = con.createStatement();
			String sql = "select distinct * from datetable";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Object data[] = {rs.getString(1), rs.getInt(2)};
				t_model.addRow(data);
			}
		}catch(SQLException e) {
			System.out.println(e + "=> fail");
		}finally {
			dbClose();
		}
	}
	
	//ȸ�� ��� ���� ��������
	public void selectAll_visitor (DefaultTableModel t_model) {
		try {
			con = DriverManager.getConnection(url, user, pass);
			st = con.createStatement();
			String sql = "select * from visitor";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Object data[] = {rs.getInt(1), rs.getInt(9), rs.getString(5), rs.getString(2), rs.getString(3), rs.getString(6), rs.getString(7), rs.getString(8)};
				t_model.addRow(data);
			}
		}catch(SQLException e) {
			System.out.println(e + "=> fail");
		}finally {
			dbClose();
		}
	}

	public void disp() {
		List<FoodDTO> list = list();
		for (FoodDTO dto : list) {
			System.out.println(dto.getToday());
			System.out.println(dto.getTotal() + "��");
		}
	}
}