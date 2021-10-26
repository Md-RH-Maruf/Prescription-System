package shareClasses;

import java.sql.ResultSet;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;

public class CommonMethod {

	private static DatabaseHandler databaseHandler;
	private static String sql;
	public CommonMethod(){
		databaseHandler = DatabaseHandler.getInstance();
	}

	public static int getMaxMedicineGroupId(){

		try{
			sql = "select max(id)+1  as maxId from tbMedicineGroup";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()){
				return rs.getInt("maxId");
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}

	public static int getMaxCCId(){

		try {
			String sql="select (select ifnull(max(id),0)+1)as maxid from tbcc ;";

			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getInt("maxid");
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getMaxInvestigationId(){

		try {
			String sql="select (select ifnull(max(id),0)+1)as maxid from tbtestgroup ;";

			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getInt("maxid");
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getMaxInvestigationGroupId() {
		try {
			ResultSet rs=databaseHandler.execQuery("select ifnull(max(id),0)+1 as id from tbInvestigationGroup");
			if(rs.next()) {
				return rs.getInt("id");
			}
		}catch(Exception e) {
			e.printStackTrace();

		}
		return 0;
	}
	
	public static int getMaxAdviseId(){

		try {
			String sql="select (select ifnull(max(id),0)+1)as maxid from tbadvise ;";

			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getInt("maxid");
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getMaxAdviseGroupId() {
		try {
			ResultSet rs=databaseHandler.execQuery("select ifnull(max(id),0)+1 as id from tbAdviseGroup");
			if(rs.next()) {
				return rs.getInt("id");
			}
		}catch(Exception e) {
			e.printStackTrace();

		}
		return 0;
	}
	public static int getMaxMedicineId(){

		try{
			sql = "select (ifnull(max(id),0)+1) as maxId from tbmedicinegroup";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()){
				return rs.getInt("maxId");
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}
	
	public static int getMaxCheapComplainId() {
		try {
			String sql="select (select ifnull(max(id),0)+1)as maxid from tbcc ;";

			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getInt("maxid");
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return 0;

	}
	
	public static int getMaxSuggestionId() {

		try {
			ResultSet rs=databaseHandler.execQuery("select ifnull(max(id),0)+1 as id from tbsuggestions");
			if(rs.next()) {
				return rs.getInt("id");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}
}
