package media_lib;

import java.sql.*;

public class mydb {
	
	Pref mypref;
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1/";
	
	//  Database credentials
	static final String USER = "User";
	static final String PASS = "User";
	
	// SQL
	Connection conn;
	Statement stmt;
	   
	public mydb(Pref mypref) {
		
		this.mypref = mypref;
		
		conn = null;
		stmt = null;
	}

	@SuppressWarnings("finally")
	public int connect() {
		int ret = 0;
		try{
			//Register JDBC driver
			Class.forName(JDBC_DRIVER);
			//Open a connection
			System.out.println("Connecting to db server...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connecting to db successfully...");
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
			ret = 1;
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
			ret = 2;
		}finally{
			return ret;
		}
	}

	@SuppressWarnings("finally")
	public int createDB() {
		int ret = 0;
		try{
			//Execute a query
			System.out.println("Creating database...");
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE MEDIA";
			stmt.executeUpdate(sql);
			mypref.setDB(true);
			System.out.println("Database created successfully...");
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
			ret = 1;
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
			ret = 2;
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
				ret = 3;
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
				ret = 4;
			}
			return ret;
		}
	}
	
	@SuppressWarnings("finally")
	public int deleteDB() {
		int ret = 0;
		try{
			//Execute a query
			System.out.println("Deleting database...");
			stmt = conn.createStatement();
			String sql = "DROP DATABASE MEDIA";
			stmt.executeUpdate(sql);
			mypref.setDB(false);
			System.out.println("Database deleted successfully...");
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
			ret = 1;
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
			ret = 2;
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
				ret = 3;
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
				ret = 4;
			}
			return ret;
		}
	}
}
