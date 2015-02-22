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
	static Connection conn;
	static Statement stmt;
	   
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
	
	private static void execute_update(String sql) throws SQLException
	{
		//Execute a query
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	}
	
	public static ResultSet execute_query(String sql) throws SQLException
	{
		// use Media lib
		System.out.println("Use db...");
		execute_update("USE MEDIA");
		System.out.println("Db use successfully...");
		//Execute a query
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}

	@SuppressWarnings("finally")
	public int createDB() {
		int ret = 0;
		try{
			// create DB
			System.out.println("Creating database...");
			execute_update("CREATE DATABASE MEDIA");
			System.out.println("Database created successfully...");
			
			// use Media lib
			System.out.println("Use db...");
			execute_update("USE MEDIA");
			System.out.println("Db use successfully...");
			System.out.println("Creating tables...");
			
			// create Keywords table
			execute_update("CREATE TABLE Keywords ( "
					+ "KeywordID int NOT NULL AUTO_INCREMENT, "
					+ "PRIMARY KEY(KeywordID), "
					+ "Name varchar ( 255 ), "
					+ "Descri TEXT )");
			// create Files table
			execute_update("CREATE TABLE Files ( "
					+ "FileID int NOT NULL AUTO_INCREMENT, "
					+ "PRIMARY KEY(FileID), "
					+ "Path TEXT, "
					+ "Type int, "
					+ "Name varchar ( 255 ) )");
			// create KeyFil table
			execute_update("CREATE TABLE KeyFil ( "
					+ "KeyFilID int NOT NULL AUTO_INCREMENT, "
					+ "PRIMARY KEY(KeyFilID), "
					+ "FileID int, "
					+ "FOREIGN KEY (FileID) REFERENCES Files(FileID),"
					+ "KeywordID int, "
					+ "FOREIGN KEY (KeywordID) REFERENCES Keywords(KeywordID) ) "
					+ "ENGINE=INNODB;");
			// create Person table
			execute_update("CREATE TABLE Person ( "
					+ "PersonID int NOT NULL AUTO_INCREMENT, "
					+ "PRIMARY KEY(PersonID), "
					+ "Name varchar ( 255 ), "
					+ "Surname varchar ( 255 ) )");
			// create FilPer table
			execute_update("CREATE TABLE FilPer ( "
					+ "FilPerID int NOT NULL AUTO_INCREMENT, "
					+ "PRIMARY KEY(FilPerID), "
					+ "FileID int, "
					+ "FOREIGN KEY (FileID) REFERENCES Files(FileID),"
					+ "PersonID int, "
					+ "FOREIGN KEY (PersonID) REFERENCES Person(PersonID) ) "
					+ "ENGINE=INNODB;");
			// create Job table
			execute_update("CREATE TABLE Job ( "
					+ "JobID int NOT NULL AUTO_INCREMENT, "
					+ "PRIMARY KEY(JobID), "
					+ "Name varchar ( 255 ) )");
			// create JoFiPe table
			execute_update("CREATE TABLE JoFiPe ( "
					+ "JoFiPeID int NOT NULL AUTO_INCREMENT, "
					+ "PRIMARY KEY(JoFiPeID), "
					+ "JobID int, "
					+ "FilPerID int )");
			mypref.setDB(true);
			System.out.println("Tables created successfully...");
		}catch(Exception e){
			if(e.getClass().equals(SQLException.class))
			{
				SQLException se = (SQLException) e;
				if(se.getErrorCode()==1007) // alread exists
				{
					System.out.println(se.getMessage());
					mypref.setDB(true);
					ret = 0;
				}
				else
				{
					System.out.println("error code: "+se.getErrorCode());
					se.printStackTrace();
					ret = 1;
				}
			}
			else
			{
				e.printStackTrace();
				ret = 2;
			}
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
