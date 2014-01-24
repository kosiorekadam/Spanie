package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class dbDAO {

	private final static String PROPS = "resources//db.properties";
	private final static String URL = "url";
	private final static String DB_NAME = "dbName";
	private final static String DRIVER = "driver";
	private final static String USER_NAME = "userName";
	private final static String PASSWORD = "password";
	
	private Connection connection;
	private Statement statement;
	
	private String dbName;
	private String userName;
	private String password;
	private String driver;
	private String url;
	
	private static final String QUERIES = "resources//query.properties";
	private static final String reportA = "report1";
	private static final String reportB = "report2";
	private static final String reportC = "report3";
	private static final String reportD = "report4";
	
	private dbDAO db;
	
	public String query1;
	public String query2;
	public String query3;
	public String query4;
	
	public dbDAO() {
		
		connection = null;
		readProperties();
		readQueries();
	}
	
	
	private void readProperties() {
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(PROPS));
			
			driver = prop.getProperty(DRIVER);
			url = prop.getProperty(URL);
			dbName = prop.getProperty(DB_NAME);
			userName = prop.getProperty(USER_NAME);
			password = prop.getProperty(PASSWORD);
			
		} catch(IOException e) {
			System.err.println("Couldn't read any database properties");
		}		
	}
	
	private void readQueries() {
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(QUERIES));
			
			query1 = prop.getProperty(reportA);
			query2 = prop.getProperty(reportB);
			
		} catch(IOException e) {
			System.err.println("Couldn't read any database properties");
		}		
	}
	
	public void establishConnection() {
		
		try {
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to a database");
		} catch (Exception e) {
			System.err.println("No connection");
		}
	}
	
	
	public void closeConnection() {
		
		if(statement != null)
			try {
				statement.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		if(connection != null) {
			
			try {
				connection.close();
				System.out.println("Disconnected from the database");
			} catch (SQLException e) {
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	public ResultSet executeQuery(String query) {
		
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
		} catch(SQLException e) {
			
			System.err.println("Couldn't execute the following statement:");
			System.err.println(query);
			if(statement != null)
				try {
					statement.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		return rs;
	}
}
