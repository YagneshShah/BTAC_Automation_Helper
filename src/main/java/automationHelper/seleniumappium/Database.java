/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah, Adil Imroz
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */
package automationHelper.seleniumappium;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Assert;

/**
 * The Class Database.
 */
public class Database 
{
	
	/** The jdbc driver. */
	// JDBC driver name and database URL
	static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	/** The Db url. */
	static String DbUrl; 
	
	/** The User. */
	static String User;
	
	/** The Password. */
	static String Password;

	/**
	 * Initialize db domain. Used internally within this class file.
	 * 
	 * @author Yagnesh Shah: yash.shah.g@gmail.com/yagnesh23.wordpress.com
	 * @throws Exception 
	 */
	private static void initializeDbDomain() throws Exception
	{
		int flag;
		String DbDomain = FilesAndFolders.getPropValue("DbDomain");
		
		String DbUrlForJenkins = FilesAndFolders.getPropValue("DbUrlForJenkins");
		String DbUrlForQA = FilesAndFolders.getPropValue("DbUrlForQA");
		String DbUrlForAutomation = FilesAndFolders.getPropValue("DbUrlForAutomation");
		String DbUrlForProduction = FilesAndFolders.getPropValue("DbUrlForProduction");
		
		String DBUserForJenkins = FilesAndFolders.getPropValue("DBUserForJenkins");
		String DBPassForJenkins = FilesAndFolders.getPropValue("DBPassForJenkins");
		String DbUserForQA = FilesAndFolders.getPropValue("DbUserForQA");
		String DBPassForQA = FilesAndFolders.getPropValue("DBPassForQA");
		String DBUserForAutomation = FilesAndFolders.getPropValue("DBUserForAutomation");
		String DBPassForAutomation = FilesAndFolders.getPropValue("DBPassForAutomation");
		String DBUserForProduction = FilesAndFolders.getPropValue("DBUserForProduction");
		String DBPassForProduction = FilesAndFolders.getPropValue("DBPassForProduction");
		
		
		switch(DbDomain)
		{
		case "jenkins":    //.10 QA server
			// JDBC driver name and database URL
			DbUrl = DbUrlForJenkins;

			//  Database credentials
			User = DBUserForJenkins;
			Password = DBPassForJenkins;
			flag=1;
			break;

		case "qa":    //.10 QA server
			// JDBC driver name and database URL
			DbUrl = DbUrlForQA;

			//  Database credentials
			User = DbUserForQA;
			Password = DBPassForQA;
			flag=1;
			break;

		case "automation":    //.10 QA Server
			// JDBC driver name and database URL
			DbUrl = DbUrlForAutomation;

			//  Database credentials
			User = DBUserForAutomation;
			Password = DBPassForAutomation;
			flag=1;
			break;

		case "production":    //.13 Auto server public ip
			// JDBC driver name and database URL
			DbUrl = DbUrlForProduction;

			//  Database credentials
			User = DBUserForProduction;
			Password = DBPassForProduction;
			flag=1;
			break;

		default:
			flag=0;
			Assert.assertTrue(flag==1, "DbDomain value in config.properties file is not a valid match with switch case");
		}
	}


	/**
	 * Mysql update query executor.
	 * 
	 * @author Yagnesh Shah: yash.shah.g@gmail.com/yagnesh23.wordpress.com, Adil Imroz
	 * @param queryMessage the query message
	 * @param queryStatements the query statements
	 * @throws Exception 
	 */
	//MySqlUpdateQueryExecutor supports to execute queries for Updating, deleting....
	public static void MySqlUpdateQueryExecutor(String queryMessage, String[] queryStatements) throws Exception
	{
		Connection conn = null;
		Statement stmt = null;
		int flag=1; //0=indicates no failure, 1=indicates success of all query execution
		initializeDbDomain();

		if(queryMessage.isEmpty())
		{
			queryMessage="Executing a Query in MySql...";
		}

		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DbUrl, User, Password);
			System.out.println("Connected database successfully...");

			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();

			System.out.println(queryMessage);

			for(int i=0;i<queryStatements.length;i++)
			{
				String sql = queryStatements[i];
				stmt.executeUpdate(sql);
			}

			System.out.println("Successfully completed " + queryMessage);

		} //end try
		catch(SQLException se){
			//Handle errors for JDBC
			flag=0;
			se.printStackTrace();
		}

		catch(Exception e){
			//Handle errors for Class.forName
			flag=0;
			e.printStackTrace();
		}

		finally
		{
			//finally block used to close resources
			try
			{
				if(stmt!=null)
					conn.close();
			}
			catch(SQLException se){
			}// do nothing
			try
			{
				if(conn!=null)
					conn.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}

			Assert.assertTrue(flag==1, "Error!! Query execution failed...");
		}//end finally
		System.out.println("Goodbye!");
	}
	
	
}