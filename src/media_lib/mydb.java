package media_lib;

import java.sql.*;

public class mydb {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1/";
	
	//  Database credentials
	static final String USER = "User";
	static final String PASS = "User";
	   
	@SuppressWarnings("resource")
	public mydb() {
		Connection conn = null;
		Statement stmt = null;
		System.out.println("Try Connecting to database...");
		try{
			//Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			//Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			//Execute a query
			System.out.println("Creating database...");
			stmt = conn.createStatement();
			  
			String sql = "CREATE DATABASE STUDENTS";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
			
			//Execute a query
			System.out.println("Deleting database...");
			stmt = conn.createStatement();
			sql = "DROP DATABASE STUDENTS";
			stmt.executeUpdate(sql);
			System.out.println("Database deleted successfully...");
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
		}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye");
	}

	public int connect() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int createDB() {
		// TODO Auto-generated method stub
		return 0;
	}
}
