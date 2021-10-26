package databaseHandler;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;



public final class DatabaseHandler {

	private static DatabaseHandler handler;
	String a=null,username="root",b=null,password="12345",c=null,port="3306",d=null,server="localhost",e=null,db_file="prescriptionsystemfinal";
	//public Connection con;
	//public Statement sta;
//	public String dbFile="",servers="",ports="",passwords="",userNames="";

	//private static final String DB_URL="jdbc:sqlserver://localhost:1433;databaseName = CreativeTechnology";
	public static Connection conn = null;
	private static Statement stmt = null; 

	PreparedStatement pst;

	private DatabaseHandler() {
		createConnection();
	}

	public static DatabaseHandler getInstance() {
		if(handler == null) {
			handler = new DatabaseHandler();
		}
		return handler;
	}
	
	
	void createConnection() {
		try {
			/*File file=new File("src/db_connection.txt");
			Scanner scan=new Scanner(file);
			int temp=1;
			while(scan.hasNextLine()){
				
				if(temp==1){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					a=token.nextToken();
					username=token.nextToken();
					userNames=username;
					temp=2;
				}
				else if(temp==2){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					b=token.nextToken();
					password=token.nextToken();
					passwords=password;
					temp=3;
				}
				else if(temp==3){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					c=token.nextToken();
					port=token.nextToken();
					ports=port;
					temp=4;
				}
				else if(temp==4){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					d=token.nextToken();
					server=token.nextToken();
					servers=server;
					temp=5;
				}
				else if(temp==5){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					e=token.nextToken();
					db_file=token.nextToken();
					dbFile=db_file;
					System.out.println(" dbfs "+dbFile);
					break;
				}
			}*/
			
			String uni="?useUnicode=true&characterEncoding=UTF-8";
			String url="jdbc:mysql://"+server+":"+port+"/"+db_file;
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection(url+uni,username,password);
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//conn = DriverManager.getConnection(DB_URL, "sa", "Cursor777");
			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Can't Load Database", "Database Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			
		}
	}

	
	public ResultSet execQuery(String query) {
		ResultSet result;
		try {
			stmt = conn.createStatement();
			System.out.println(query);
			System.out.println();
			result = stmt.executeQuery(query);
		}catch(SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage()," Error Ocoured",JOptionPane.ERROR_MESSAGE );
			System.out.println("Exception at execQuery:dataHandler "+ex.getLocalizedMessage());
			return null;
		}finally {

		}

		return result;
	}

	public boolean execAction(String qu) {
		try {
			stmt = conn.createStatement();
			System.out.println(qu);
			System.out.println();
			stmt.execute(qu);
			return true;
		}catch(SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage()," Error Ocoured",JOptionPane.ERROR_MESSAGE );
			System.out.println("Exception at execQuery:dataHandler "+ex.getLocalizedMessage());
			return false;
		}finally {

		}
	}


}
