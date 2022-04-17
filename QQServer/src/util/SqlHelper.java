package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlHelper {
	public static Connection getConnection() {
		Connection connection = null;
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";// SQL数据库引擎
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=qq;encrypt=true;trustServerCertificate=true;";
		String Name = "sa";
		String Pwd = "123";
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(dbURL, Name, Pwd);
			System.out.println("连接数据库成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接失败");
		}
		return connection;
	}
	public static int register(String username,String passwd,String phone) {
		Statement stmt = null;
		Connection conn = getConnection();
		try {
			stmt = conn.createStatement();
			String sql = "insert into userTable values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, passwd);
			ps.setString(3, phone);
			int rs = ps.executeUpdate();
			conn.close();
			return rs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static int Login(String username,String passwd) {
		Statement stmt = null;
		Connection conn = getConnection();
		try {
			stmt = conn.createStatement();
			String sql = "select count(*) from userTable where username=? and passwd=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, passwd);
			//int n  = stmt.executeUpdate(sql);
			//System.out.println(n);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int num = rs.getInt(1);
			conn.close();
			return num;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static int findPasswd(String username,String passwd,String phone) {
		Statement stmt = null;
		Connection conn = getConnection();
		try {
			//验证账号
			stmt = conn.createStatement();
			String sql = "select count(*) from userTable where username=? and phone=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, phone);
			//int n  = stmt.executeUpdate(sql);
			//System.out.println(n);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int num = rs.getInt(1);
			if(num<=0) {
				return num;
			}
			
			stmt = conn.createStatement();
			String sql2 = "update userTable set passwd='"+passwd+"' where username='"+username+"'";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			/*
			ps.setString(1, username);
			ps.setString(2, passwd);
			ps.setString(3, phone);
			*/
			int rs2 = ps2.executeUpdate();
			conn.close();
			return rs2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static void main(String args[]) {
		System.out.println(findPasswd("123","12345","123"));
	}
}
