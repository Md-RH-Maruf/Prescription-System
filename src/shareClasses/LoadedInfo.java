package shareClasses;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.CatchNode;
import prescription.main.LoadingSplashScreenController;
import prescription.main.MainFrameController;
import shareClasses.LoadedInfo.TableItemInfo;

public class LoadedInfo {
	static Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

	private static HashMap<String, TableItemInfo> mapSystem = new HashMap<>();
	private static HashMap<String, TableItemInfo> mapDisease = new HashMap<>();
	private static HashMap<String, TableItemInfo> mapMedicineGroup = new HashMap<>();
	private static HashMap<String, TableItemInfo> mapGeneric = new HashMap<>();
	private static HashMap<String, MedicineItemInfo> mapMedicineBrand = new HashMap<>();



	private static HashMap<String, TableItemInfo> mapCheapComplain = new HashMap<>();
	private static HashMap<String, TableItemInfo> mapCheapComplainCause = new HashMap<>();
	private static HashMap<String, TableItemInfo> mapInvestigation = new HashMap<>();
	private static HashMap<String, TableItemInfo> mapInvestigationGroup = new HashMap<>();
	private static HashMap<String, TableItemInfo> mapAdvise = new HashMap<>();
	private static HashMap<String, TableItemInfo> mapAdviseGroup = new HashMap<>();

	private static HashMap<String, TableItemInfo> mapClinicalExamination = new HashMap<>();



	private static HashMap<String, ObservableList<TableItemInfo>> mapGenericListBySystemId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapGenericListByDiseaseId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapGenericListByMedicineGroupId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapMedicineGroupListByDiseaseId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapMedicineGroupListBySystemId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapDiseaseListBySystemId = new HashMap<>();
	private static HashMap<String, ObservableList<MedicineItemInfo>> mapBrandListByGenericId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapCheapComplainListBySystemId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapCheapComplainCauseListByHeadId = new HashMap<>();

	private static HashMap<String, ObservableList<TableItemInfo>> mapInvestigationListByDiseaseId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapInvestigationListBySystemId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapInvestigationListByGroupId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapAdviseListBySystemId = new HashMap<>();
	private static HashMap<String, ObservableList<TableItemInfo>> mapAdviseListByDiseaseId = new HashMap<>();

	private static HashMap<String, ObservableList<TableItemInfo>> mapAdviseListByGroupId = new HashMap<>();

	private static HashMap<String, ObservableList<TableItemInfo>> mapClinicalExaminationListByHeadId = new HashMap<>();

	private static HashMap<String, SuggestionInfo> mapSchedule = new HashMap<>();
	private static HashMap<String, SuggestionInfo> mapTime = new HashMap<>();
	private static HashMap<String, SuggestionInfo> mapCourse = new HashMap<>();
	
	private static ArrayList<SuggestionInfo> scheduleList = new ArrayList<>();
	private static ArrayList<SuggestionInfo> timeList = new ArrayList<>();
	private static ArrayList<SuggestionInfo> courseList = new ArrayList<>();

	private static ArrayList<String> systemIdList = new ArrayList<>();
	private static ArrayList<String> diseaseIdList = new ArrayList<>();
	private static ArrayList<String> medicineGroupIdList = new ArrayList<>();
	private static ArrayList<String> genericIdList = new ArrayList<>();
	private static ArrayList<String> cheapComplainIdList = new ArrayList<>();

	private static ObservableList<TableItemInfo> listSystemList = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listDiseaseList = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listMedicineGroupList = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listGenericList = FXCollections.observableArrayList();
	private static ObservableList<MedicineItemInfo> listMedicineBrandList = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listCheapComplainList = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listCheapComplainCauseList = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listInvestigation = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listInvestigationGroup = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listAdvise = FXCollections.observableArrayList();
	private static ObservableList<TableItemInfo> listAdviseGroup = FXCollections.observableArrayList();

	private static ObservableList<TableItemInfo> listClinicalExamination = FXCollections.observableArrayList();

	public static HashMap<String, ObservableList<TableItemInfo>> mapMedicineListByDiseaseId = new HashMap<>();

	private static ObservableList<String> listDiseaseData = FXCollections.observableArrayList();
	private static ObservableList<String> listMedicineGroupData = FXCollections.observableArrayList();
	private static ObservableList<String> listGenericData = FXCollections.observableArrayList();
	private static ObservableList<String> listMedicineBrandData = FXCollections.observableArrayList();
	private static ObservableList<String> listInvestigationData = FXCollections.observableArrayList();
	private static ObservableList<String> listAdviseData = FXCollections.observableArrayList();

	private static DatabaseHandler databaseHandler;
	private static String sql;



	/*private static final int cheapComplainType = 1;
	private static final int surveyType = 2;*/
	//public static Stage stageMain = new Stage();
	public static LoadData loadData;
	static LoadingSplashScreenController splashController;
	public LoadedInfo(LoadingSplashScreenController splashController){
		databaseHandler = DatabaseHandler.getInstance();
		this.splashController = splashController;
		new LoadData().start();
	}

	public static class LoadData extends Thread{

		public void run(){

			loadCheapComplainList();
			splashController.setProgressValue(1.0/20);
			loadCheapComplainCauseInfo();
			splashController.setProgressValue(1.0/19);
			loadSystemInfo();
			splashController.setProgressValue(1.0/18);
			loadDiseaseInfo();
			splashController.setProgressValue(1.0/17);
			loadMedicineGroupInfo();
			splashController.setProgressValue(1.0/16);
			loadGenericInfo();
			splashController.setProgressValue(1.0/15);
			loadMedicineBrandInfo();
			splashController.setProgressValue(1.0/14);
			loadCheapComplainInfo();
			splashController.setProgressValue(1.0/13);
			loadInvestigationInfo();
			splashController.setProgressValue(1.0/12);
			loadInvestigationGroupInfo();
			splashController.setProgressValue(1.0/11);
			loadAdviseInfo();
			splashController.setProgressValue(1.0/10);
			loadAdviseGroupInfo();
			splashController.setProgressValue(1.0/9);
			loadClinicalExaminationInfo();
			splashController.setProgressValue(1.0/8);
			LoadAllTypeSchedule();

			//loadInvestigationList();
			//loadAdviseList();
			loadMapDiseaseListBySystemId();

			loadMapMedicineBrandListByGenericId();
			splashController.setProgressValue(1.0/6);
			loadMapMedicineGroupListByDiseaseId();
			splashController.setProgressValue(1.0/5);
			loadMapMedicineGroupListBySystemId();
			splashController.setProgressValue(1.0/4);
			loadMapGenericListByMedicineGroupId();
			loadMapGenericListByDiseaseId();
			splashController.setProgressValue(1.0/3);
			loadMapGenericListBySystemId();
			splashController.setProgressValue(1.0/2);
			loadMapCheapComplainListBySystemId();
			splashController.setProgressValue(1.0/1.8);
			loadMapCheapComplainCauseListByHeadId();
			splashController.setProgressValue(1.0/1.6);
			loadMapAdviseListByDiseaseId();
			splashController.setProgressValue(1.0/1.5);
			loadMapAdviseListBySystemId();
			splashController.setProgressValue(1.0/1.3);
			loadMapAdviseListByGroupId();
			splashController.setProgressValue(1.0/1.2);
			loadMapInvestigationListByDiseaseId();
			splashController.setProgressValue(1.0/1.1);
			loadMapInvestigationListBySystemId();

			loadMapInvestigationListByGroupId();
			loadMapClinicalExaminationListByHeadId();
			splashController.setProgressValue(1.0/1.0);
			stop();

		}

	};



	public static void loadAdviseList() {
		// TODO Auto-generated method stub
		try {
			listAdvise.clear();

			sql = "select slNo,id,pId,advise,type from tbAdvise group by advise order by slNo";

			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				//modelTest.addRow(new Object[] {rs.getString("tg.id"),rs.getString("tg.groupname"),"Del"});
				listAdvise.add(new TableItemInfo(rs.getInt("slNO"), rs.getString("id"), rs.getString("pId"), rs.getString("advise"), rs.getInt("type")));
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadCheapComplainList() {
		// TODO Auto-generated method stub
		try {
			listCheapComplainList.clear();

			sql = "select slNo,id,headId,name,type from tbcc cc group by name order by slNo";

			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				//modelTest.addRow(new Object[] {rs.getString("tg.id"),rs.getString("tg.groupname"),"Del"});
				listCheapComplainList.add(new TableItemInfo(rs.getInt("slNO"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadInvestigationList() {
		// TODO Auto-generated method stub
		try {
			listInvestigation.clear();

			sql = "select * from tbtestgroup where type='"+NodeType.INVESTIGATION.getType()+"' group by groupname order by slNo;";

			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				//modelTest.addRow(new Object[] {rs.getString("tg.id"),rs.getString("tg.groupname"),"Del"});
				listInvestigation.add(new TableItemInfo(rs.getInt("slNO"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapDiseaseListBySystemId(){
		try{
			mapDiseaseListBySystemId.clear();
			for(int i=0;i<systemIdList.size();i++){
				sql = "select sn,id,pId,groupName,type from tbMedicineGroup where pId = '"+systemIdList.get(i)+"' and type = '"+NodeType.DISEASE.getType()+"' order By groupName;";
				ResultSet rs = databaseHandler.execQuery(sql);
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;
				temp.clear();
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				}

				mapDiseaseListBySystemId.put(systemIdList.get(i), temp);
			}



		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	public static void loadMapGenericListBySystemId(){
		try{
			mapGenericListBySystemId.clear();
			for(int i=0;i<systemIdList.size();i++){
				sql = "select sn,id,pId,groupName,type from tbMedicineGroup where pId = '"+systemIdList.get(i)+"' and type = '"+NodeType.GENERIC.getType()+"' order By groupName;";
				ResultSet rs = databaseHandler.execQuery(sql);
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;
				temp.clear();
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				}

				if(mapDiseaseListBySystemId.get(systemIdList.get(i)) != null){
					ObservableList<TableItemInfo> diseaseList = mapDiseaseListBySystemId.get(systemIdList.get(i));
					for(int j=0; j<diseaseList.size() ;j++){
						if(mapGenericListByDiseaseId.get(diseaseList.get(j).getItemId()) != null)
							temp.addAll(mapGenericListByDiseaseId.get(diseaseList.get(j).getItemId()));
					}
				}


				mapGenericListBySystemId.put(systemIdList.get(i), temp);
			}



		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapMedicineGroupListByDiseaseId(){
		try{
			mapMedicineGroupListByDiseaseId.clear();
			for(int i=0;i<diseaseIdList.size();i++){
				sql = "select sn,id,pId,groupName,type from tbMedicineGroup where pId = '"+diseaseIdList.get(i)+"' and type = '"+NodeType.MEDICINE_GROUP.getType()+"' order By groupName;";
				ResultSet rs = databaseHandler.execQuery(sql);
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;
				temp.clear();
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				}

				mapMedicineGroupListByDiseaseId.put(diseaseIdList.get(i), temp);
			}



		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapMedicineGroupListBySystemId(){
		try{
			mapMedicineGroupListBySystemId.clear();
			for(int i = 0; i < systemIdList.size();i++){
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;

				if(mapDiseaseListBySystemId.get(systemIdList.get(i)) != null){
					ObservableList<TableItemInfo> diseaseList = mapDiseaseListBySystemId.get(systemIdList.get(i));

					for(int j = 0; j < diseaseList.size() ;j++){
						if(mapMedicineGroupListByDiseaseId.get(diseaseList.get(j).getItemId()) != null)
							temp.addAll(mapMedicineGroupListByDiseaseId.get(diseaseList.get(j).getItemId()));
					}
				}

				mapMedicineGroupListBySystemId.put(systemIdList.get(i), temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapGenericListByDiseaseId(){
		try{
			mapGenericListByDiseaseId.clear();
			for(int i=0;i<diseaseIdList.size();i++){
				sql = "select sn,id,pId,groupName,type from tbMedicineGroup where pId = '"+diseaseIdList.get(i)+"' and type = '"+NodeType.GENERIC.getType()+"' order By groupName;";
				ResultSet rs = databaseHandler.execQuery(sql);
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;
				temp.clear();
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				}


				ObservableList<TableItemInfo> medicineGroupList = mapMedicineGroupListByDiseaseId.get(diseaseIdList.get(i));
				for(int j=0 ;j<medicineGroupList.size() ; j++){
					if(mapGenericListByMedicineGroupId.get(medicineGroupList.get(j).getItemId()) != null)
						temp.addAll(mapGenericListByMedicineGroupId.get(medicineGroupList.get(j).getItemId()));
				}
				mapGenericListByDiseaseId.put(diseaseIdList.get(i), temp);
			}





		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapGenericListByMedicineGroupId(){
		try{
			mapGenericListByMedicineGroupId.clear();
			for(int i=0;i<medicineGroupIdList.size();i++){
				sql = "select sn,id,pId,groupName,type from tbMedicineGroup where pId = '"+medicineGroupIdList.get(i)+"' and type = '"+NodeType.GENERIC.getType()+"' order By groupName;";
				ResultSet rs = databaseHandler.execQuery(sql);
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;
				temp.clear();
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				}

				mapGenericListByMedicineGroupId.put(medicineGroupIdList.get(i), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapMedicineBrandListByGenericId(){
		try{

			mapBrandListByGenericId.clear();
			for(int i=0;i<genericIdList.size();i++){
				sql = "select mg.sn,mg.id,mg.pId,mg.groupName,mc.Name AS companyName,ifnull(scedulei.suggestion,ifnull(scedule.suggestion,'')) AS schedule,ifnull(stimei.suggestion,ifnull(stime.suggestion,'')) AS time,ifnull(courcei.suggestion,ifnull(cource.suggestion,'')) AS course,mg.type from tbmedicinegroup mg "+ 
						"left JOIN tbmedicinecompany mc  "+
						"ON mg.id = mc.id  "+ 					
						"LEFT JOIN tbschedule s1i  "+
						"ON mg.id = s1i.genericId  AND s1i.`type` = 1  "+
						"LEFT JOIN tbschedule s2i  "+
						"ON mg.id = s2i.genericId  AND s2i.`type` = 2  "+
						"LEFT JOIN tbschedule s3i  "+
						"ON mg.id = s3i.genericId  AND s3i.`type` = 3  "+
						"LEFT JOIN tbsuggestions scedulei  "+
						"ON s1i.suggestionId = scedulei.id  "+
						"LEFT JOIN tbsuggestions stimei  "+
						"ON s2i.suggestionId = stimei.id  "+
						"LEFT JOIN tbsuggestions courcei  "+
						"ON s3i.suggestionId = courcei.id "+			
						"LEFT JOIN tbschedule s1  "+
						"ON (mg.id = s1.genericId OR mg.pId = s1.genericId) AND s1.`type` = 1  "+
						"LEFT JOIN tbschedule s2  "+
						"ON (mg.id = s2.genericId OR mg.pId = s2.genericId) AND s2.`type` = 2  "+
						"LEFT JOIN tbschedule s3  "+
						"ON (mg.id = s3.genericId OR mg.pId = s3.genericId) AND s3.`type` = 3  "+
						"LEFT JOIN tbsuggestions scedule  "+
						"ON s1.suggestionId = scedule.id  "+
						"LEFT JOIN tbsuggestions stime  "+
						"ON s2.suggestionId = stime.id  "+
						"LEFT JOIN tbsuggestions cource  "+
						"ON s3.suggestionId = cource.id "+
						"where mg.Type='"+NodeType.MEDICINE_BRAND.getType()+"' and mg.pId = '"+genericIdList.get(i)+"' group by mg.groupName order by mg.groupName";
				ResultSet rs = databaseHandler.execQuery(sql);
				ObservableList<MedicineItemInfo> temp = FXCollections.observableArrayList() ;
				temp.clear();
				while(rs.next()){
					temp.add(new MedicineItemInfo(rs.getInt("sn"), rs.getString("id") , rs.getString("pId"),  rs.getString("groupName"),  rs.getString("companyName"),  rs.getString("schedule"),  rs.getString("time"),  rs.getString("course"), rs.getInt("type")));
				}

				mapBrandListByGenericId.put(genericIdList.get(i), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public static void loadMapInvestigationListBySystemId(){
		try{
			mapInvestigationListBySystemId.clear();
			for(int i=0;i<systemIdList.size();i++){

				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;

				ObservableList<TableItemInfo> diseaseList = mapDiseaseListBySystemId.get(systemIdList.get(i));

				for(int j=0 ; j < diseaseList.size(); j++){
					if(mapInvestigationListByDiseaseId.get(diseaseList.get(j).getItemId()) != null) 
						temp.addAll(mapInvestigationListByDiseaseId.get(diseaseList.get(j).getItemId()));
				}
				/*sql = "select tg.slNo,tg.id,tg.GroupName,tg.pId,tg.type "
						+ "from tbmedicinegroup mg "
						+ "join tbtestgroup tg "
						+ "on mg.id=tg.pId where mg.type='"+NodeType.DISEASE.getType()+"' and mg.pid='"+systemIdList.get(i)+"' group by groupname order by groupname";
				ResultSet rs = databaseHandler.execQuery(sql);
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;
				temp.clear();
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("slNO"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				}*/

				mapInvestigationListBySystemId.put(systemIdList.get(i), temp);
			}



		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapInvestigationListByDiseaseId(){
		try{
			mapInvestigationListByDiseaseId.clear();
			for(int i=0;i<diseaseIdList.size();i++){
				sql = "select * from tbtestgroup where type='"+NodeType.INVESTIGATION.getType()+"' and pId= '"+diseaseIdList.get(i)+"'  group by groupname order by slNo;";
				ResultSet rs = databaseHandler.execQuery(sql);
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList() ;
				temp.clear();
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("slNO"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				}

				mapInvestigationListByDiseaseId.put(diseaseIdList.get(i), temp);
			}



		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}






	public static void loadMapCheapComplainListBySystemId(){
		try{

			mapCheapComplainListBySystemId.clear();

			for(int i = 0; i<systemIdList.size();i++){
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList();
				ResultSet rs = databaseHandler.execQuery("select cc.slNo,cc.id,cc.name,cc.headId,cc.type from tbcc cc  where cc.headId= '"+systemIdList.get(i)+"' and cc.type='"+NodeType.CHEAP_COMPLAIN.getType()+"' order by cc.slNo,cc.name");
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("slNo"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				}

				mapCheapComplainListBySystemId.put(systemIdList.get(i), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapCheapComplainCauseListByHeadId(){
		try{

			mapCheapComplainCauseListByHeadId.clear();

			for(int i = 0; i<cheapComplainIdList.size();i++){
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList();
				ResultSet rs = databaseHandler.execQuery("select cc.slNo,cc.id,cc.name,cc.headId,cc.type from tbcc cc  where cc.headId= '"+cheapComplainIdList.get(i)+"' and cc.type='"+NodeType.CHEAP_COMPLAIN_CAUSE.getType()+"' order by cc.slNo,cc.name");
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("slNo"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				}

				mapCheapComplainCauseListByHeadId.put(cheapComplainIdList.get(i), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapAdviseListByDiseaseId(){
		try{

			mapAdviseListByDiseaseId.clear();

			for(int i = 0; i<diseaseIdList.size();i++){
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList();
				ResultSet rs = databaseHandler.execQuery("SELECT a.slNo,a.id,a.advise,a.pid,a.type from tbadvise a WHERE a.pid = '"+diseaseIdList.get(i)+"' GROUP BY a.advise ORDER BY a.slNo;");
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("slNo"), rs.getString("id"), rs.getString("pid"), rs.getString("advise"), rs.getInt("type")));
				}
				mapAdviseListByDiseaseId.put(diseaseIdList.get(i), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapAdviseListBySystemId(){
		try{

			mapAdviseListBySystemId.clear();

			for(int i = 0; i<systemIdList.size();i++){
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList();
				ObservableList<TableItemInfo> diseaseList = mapDiseaseListBySystemId.get(systemIdList.get(i));
				for(int j=0;j<diseaseList.size();j++){
					if(mapAdviseListByDiseaseId.get(diseaseList.get(j).getItemId()) != null)
						temp.addAll(mapAdviseListByDiseaseId.get(diseaseList.get(j).getItemId()));
				}

				mapAdviseListBySystemId.put(systemIdList.get(i), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapInvestigationListByGroupId(){
		try{

			mapInvestigationListByGroupId.clear();

			for(int i = 0; i<listInvestigationGroup.size();i++){
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList();
				ResultSet rs = databaseHandler.execQuery("SELECT ig.slNo,tg.id,tg.pid,tg.groupName,tg.`type` FROM tbinvestigationgroup ig  \r\n"+
						"JOIN tbtestGroup tg \r\n"+
						"ON tg.id = ig.investigationId  \r\n"+ 
						"WHERE ig.id = '"+listInvestigationGroup.get(i).getItemId()+"' order by ig.slno");
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("slNo"), rs.getString("id"), rs.getString("pid"), rs.getString("groupName"), rs.getInt("type")));
				}
				mapInvestigationListByGroupId.put(listInvestigationGroup.get(i).getItemId(), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapAdviseListByGroupId(){
		try{

			mapAdviseListByGroupId.clear();

			for(int i = 0; i<listAdviseGroup.size();i++){
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList();
				ResultSet rs = databaseHandler.execQuery("SELECT ag.slNo,a.id,a.pid,a.advise,a.`type` FROM tbadvisegroup ag  \r\n"+
						"JOIN tbadvise a \r\n"+
						"ON a.id = ag.adviseId  \r\n"+ 
						"WHERE ag.id = '"+listAdviseGroup.get(i).getItemId()+"' order by ag.slno");
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("slNo"), rs.getString("id"), rs.getString("pid"), rs.getString("advise"), rs.getInt("type")));
				}
				mapAdviseListByGroupId.put(listAdviseGroup.get(i).getItemId(), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMapClinicalExaminationListByHeadId(){
		try{

			mapClinicalExaminationListByHeadId.clear();

			for(int i = 0; i<listClinicalExamination.size();i++){
				ObservableList<TableItemInfo> temp = FXCollections.observableArrayList();
				sql = "select * FROM tbclinicalexamination WHERE headId='"+listClinicalExamination.get(i).getItemId()+"' and type = '"+NodeType.CLINICAL_EXAMINATION.getType()+"'order by sn ";
				ResultSet rs = databaseHandler.execQuery(sql);
				while(rs.next()){
					temp.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				}
				mapClinicalExaminationListByHeadId.put(listClinicalExamination.get(i).getItemId(), temp);
			}

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	public static ObservableList<TableItemInfo> getDiseaseListBySystemId(String systemId){

		if(mapDiseaseListBySystemId.get(systemId) != null){
			return mapDiseaseListBySystemId.get(systemId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getGenericListBySystemId(String systemId){

		if(mapGenericListBySystemId.get(systemId) != null){
			return mapGenericListBySystemId.get(systemId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getMedicineGroupListByDiseaseId(String diseaseId){

		if(mapMedicineGroupListByDiseaseId.get(diseaseId) != null){
			return mapMedicineGroupListByDiseaseId.get(diseaseId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getMedicineGroupListBySystemId(String systemId){

		if(mapMedicineGroupListBySystemId.get(systemId) != null){
			return mapMedicineGroupListBySystemId.get(systemId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getGenericListByDiseaseId(String diseaseId){

		if(mapGenericListByDiseaseId.get(diseaseId) != null){
			return mapGenericListByDiseaseId.get(diseaseId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getGenericListByMedicineGroupId(String medicineGroupId){

		if(mapGenericListByMedicineGroupId.get(medicineGroupId) != null){
			return mapGenericListByMedicineGroupId.get(medicineGroupId);
		}else
			return null;
	}

	public static ObservableList<MedicineItemInfo> getBrandListByGenericId(String genericId){

		if(mapBrandListByGenericId.get(genericId) != null){
			return mapBrandListByGenericId.get(genericId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getInvestigationListBySystemId(String systemId){

		if(mapInvestigationListBySystemId.get(systemId) != null){
			return mapInvestigationListBySystemId.get(systemId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getInvestigationListByDiseaseId(String diseaseId){

		if(mapInvestigationListByDiseaseId.get(diseaseId) != null){
			return mapInvestigationListByDiseaseId.get(diseaseId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getAdviseListBySystemId(String systemId){

		if(mapAdviseListBySystemId.get(systemId) != null){
			return mapAdviseListBySystemId.get(systemId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getAdviseListByDiseaseId(String diseaseId){

		if(mapAdviseListByDiseaseId.get(diseaseId) != null){
			return mapAdviseListByDiseaseId.get(diseaseId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getInvestigationListByGroupId(String investigationGroupId){

		if(mapInvestigationListByGroupId.get(investigationGroupId) != null){
			return mapInvestigationListByGroupId.get(investigationGroupId);
		}else
			return null;
	}

	public static ObservableList<TableItemInfo> getAdviseListByGroupId(String adviseGroupId){

		if(mapAdviseListByGroupId.get(adviseGroupId) != null){
			return mapAdviseListByGroupId.get(adviseGroupId);
		}else
			return null;
	}



	public static ObservableList<TableItemInfo> getClinicalExaminationListByHeadId(String headId){

		if(mapClinicalExaminationListByHeadId.get(headId) != null){
			return mapClinicalExaminationListByHeadId.get(headId);
		}else
			return null;
	}


	public static void loadMedicineGroupList(){
		try{
			sql = "select sn,id,pId,groupName,type from tbMedicineGroup where type='"+NodeType.MEDICINE_GROUP.getType()+"' group by groupName order by groupName";
			ResultSet rs = databaseHandler.execQuery(sql);
			listGenericList.clear();
			while(rs.next()){
				listGenericList.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));				
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public static void loadGenericList(){
		try{
			sql = "select sn,id,pId,groupName,type from tbMedicineGroup where type='"+NodeType.GENERIC.getType()+"' group by groupName order by groupName";
			ResultSet rs = databaseHandler.execQuery(sql);
			listGenericList.clear();
			while(rs.next()){
				listGenericList.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));				
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public static void loadMedicineBrandList(){
		try{
			sql = "select mg.sn,mg.id,mg.pId,mg.groupName,mc.Name AS companyName,ifnull(scedule.suggestion,'') AS schedule,ifnull(stime.suggestion,'') AS time,ifnull(cource.suggestion,'') AS course,mg.type from tbmedicinegroup mg "+
					"left JOIN tbmedicinecompany mc "+
					"ON mg.id = mc.id  "+
					"LEFT JOIN tbschedule s1 "+
					"ON mg.id = s1.genericId OR mg.pId = s1.genericId AND s1.`type` = 1 "+
					"LEFT JOIN tbschedule s2 "+
					"ON mg.id = s2.genericId OR mg.pId = s2.genericId AND s2.`type` = 2 "+
					"LEFT JOIN tbschedule s3 "+
					"ON mg.id = s3.genericId OR mg.pId = s3.genericId AND s3.`type` = 3 "+
					"LEFT JOIN tbsuggestions scedule "+
					"ON s1.suggestionId = scedule.id "+
					"LEFT JOIN tbsuggestions stime "+
					"ON s2.suggestionId = stime.id "+
					"LEFT JOIN tbsuggestions cource "+
					"ON s3.suggestionId = cource.id "+
					"where mg.Type='"+NodeType.MEDICINE_BRAND.getType()+"' group by mg.groupName order by mg.groupName";
			ResultSet rs = databaseHandler.execQuery(sql);
			listMedicineBrandList.clear();
			while(rs.next()){
				listMedicineBrandList.add(new MedicineItemInfo(rs.getInt("sn"), rs.getString("id") , rs.getString("pId"),  rs.getString("groupName"),  rs.getString("companyName"),  rs.getString("schedule"),  rs.getString("time"),  rs.getString("course"), rs.getInt("type")));				
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public static ObservableList<TableItemInfo> getSystemList(){

		return listSystemList;
	}

	public static ObservableList<TableItemInfo> getDiseaseList(){
		return listDiseaseList;
	}

	public static ObservableList<TableItemInfo> getMedicineGroupList(){
		return listMedicineGroupList;
	}

	public static ObservableList<TableItemInfo> getGenericList(){
		return listGenericList;
	}

	public static ObservableList<MedicineItemInfo> getMedicineBrandList(){
		return listMedicineBrandList;
	}

	public static ObservableList<TableItemInfo> getCheapComplainList(){
		return listCheapComplainList;
	}

	public static ObservableList<TableItemInfo> getCheapComplainListBySystemId(String systemId){
		return mapCheapComplainListBySystemId.get(systemId);
	}

	public static ObservableList<TableItemInfo> getCheapComplainCauseList(){
		return listCheapComplainCauseList;
	}

	public static ObservableList<TableItemInfo> getCheapComplainCauseListByHeadId(String headId){
		return mapCheapComplainCauseListByHeadId.get(headId);
	}

	public static ObservableList<TableItemInfo> getInvestigationList(){
		return listInvestigation;
	}

	public static ObservableList<TableItemInfo> getInvestigationGroupList(){
		return listInvestigationGroup;
	}

	public static ObservableList<TableItemInfo> getAdviseList(){
		return listAdvise;
	}

	public static ObservableList<TableItemInfo> getAdviseGroupList(){
		return listAdviseGroup;
	}

	public static ObservableList<TableItemInfo> getClinicalExaminationList(){
		return listClinicalExamination;
	}



	public static String getSystemIdByName(String systemName){
		return mapSystem.get(systemName.toLowerCase()).getItemId();
	}
	public static boolean isSystemExist(String systemNameOrId){
		if(mapSystem.get(systemNameOrId.toLowerCase()) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getSystemInfo(String systemNameOrId){
		if(mapSystem.get(systemNameOrId.toLowerCase()) != null){
			return mapSystem.get(systemNameOrId.toLowerCase());

		}else
			return null;
	}

	public static void loadSystemInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "SELECT sn,id,groupName,pId,type from tbmedicinegroup WHERE TYPE = '"+NodeType.SYSTEM.getType()+"' ORDER BY sn,GroupName";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapSystem.clear();
			systemIdList.clear();
			listSystemList.clear();
			while(rs.next()){
				systemIdList.add(rs.getString("id"));
				mapSystem.put((rs.getString("pId")+rs.getString("groupName")).toLowerCase(),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapSystem.put(rs.getString("id").toLowerCase(),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapSystem.put(rs.getString("groupName").toLowerCase(),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				listSystemList.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadDiseaseInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "SELECT sn,id,groupName,pId,type from tbmedicinegroup WHERE TYPE = '"+NodeType.DISEASE.getType()+"' group By groupName ORDER BY sn,GroupName";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapDisease.clear();
			diseaseIdList.clear();
			listDiseaseList.clear();
			while(rs.next()){
				listDiseaseData.add(rs.getString("groupName"));
				diseaseIdList.add(rs.getString("id"));
				mapDisease.put(rs.getString("pId")+rs.getString("groupName"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapDisease.put(rs.getString("id"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapDisease.put(rs.getString("groupName"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				listDiseaseList.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	public static void loadMedicineGroupInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "SELECT sn,id,groupName,pId,type from tbmedicinegroup WHERE TYPE = '"+NodeType.MEDICINE_GROUP.getType()+"' group By groupName ORDER BY sn,GroupName";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapMedicineGroup.clear();
			medicineGroupIdList.clear();
			listMedicineGroupList.clear();
			while(rs.next()){
				listMedicineGroupData.add(rs.getString("groupname"));
				medicineGroupIdList.add(rs.getString("id"));
				mapMedicineGroup.put(rs.getString("pId")+rs.getString("groupName"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapMedicineGroup.put(rs.getString("id"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapMedicineGroup.put(rs.getString("groupName"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				listMedicineGroupList.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}




	public static void loadGenericInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "SELECT sn,id,groupName,pId,type from tbmedicinegroup WHERE TYPE = '"+NodeType.GENERIC.getType()+"' group By groupName ORDER BY sn,GroupName";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapGeneric.clear();
			genericIdList.clear();
			listGenericList.clear();
			while(rs.next()){
				listGenericData.add(rs.getString("groupname"));
				genericIdList.add(rs.getString("id"));
				mapGeneric.put(rs.getString("pId")+rs.getString("groupName"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapGeneric.put(rs.getString("id"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapGeneric.put(rs.getString("groupName"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				listGenericList.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadMedicineBrandInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "select mg.sn,mg.id,mg.pId,mg.groupName,mc.Name AS companyName,ifnull(scedulei.suggestion,ifnull(scedule.suggestion,'')) AS schedule,ifnull(stimei.suggestion,ifnull(stime.suggestion,'')) AS time,ifnull(courcei.suggestion,ifnull(cource.suggestion,'')) AS course,mg.type from tbmedicinegroup mg "+ 
					"left JOIN tbmedicinecompany mc  "+
					"ON mg.id = mc.id  "+ 					
					"LEFT JOIN tbschedule s1i  "+
					"ON mg.id = s1i.genericId  AND s1i.`type` = 1 and s1i.default='1' "+
					"LEFT JOIN tbschedule s2i  "+
					"ON mg.id = s2i.genericId  AND s2i.`type` = 2 and s2i.default='1' "+
					"LEFT JOIN tbschedule s3i  "+
					"ON mg.id = s3i.genericId  AND s3i.`type` = 3 and s3i.default='1' "+
					"LEFT JOIN tbsuggestions scedulei  "+
					"ON s1i.suggestionId = scedulei.id  "+
					"LEFT JOIN tbsuggestions stimei  "+
					"ON s2i.suggestionId = stimei.id  "+
					"LEFT JOIN tbsuggestions courcei  "+
					"ON s3i.suggestionId = courcei.id "+			
					"LEFT JOIN tbschedule s1  "+
					"ON (mg.id = s1.genericId OR mg.pId = s1.genericId) AND s1.`type` = 1 and s1.default='1' "+
					"LEFT JOIN tbschedule s2  "+
					"ON (mg.id = s2.genericId OR mg.pId = s2.genericId) AND s2.`type` = 2  and s2.default='1' "+
					"LEFT JOIN tbschedule s3  "+
					"ON (mg.id = s3.genericId OR mg.pId = s3.genericId) AND s3.`type` = 3  and s3.default='1' "+
					"LEFT JOIN tbsuggestions scedule  "+
					"ON s1.suggestionId = scedule.id  "+
					"LEFT JOIN tbsuggestions stime  "+
					"ON s2.suggestionId = stime.id  "+
					"LEFT JOIN tbsuggestions cource  "+
					"ON s3.suggestionId = cource.id "+
					"where mg.Type='"+NodeType.MEDICINE_BRAND.getType()+"' group by mg.groupName order by mg.sn,mg.groupName";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapMedicineBrand.clear();
			listMedicineBrandList.clear();
			while(rs.next()){
				listMedicineBrandData.add(rs.getString("groupname"));
				mapMedicineBrand.put(rs.getString("pId")+rs.getString("groupName"),new MedicineItemInfo(rs.getInt("sn"), rs.getString("id") , rs.getString("pId"),  rs.getString("groupName"),  rs.getString("companyName"),  rs.getString("schedule"),  rs.getString("time"),  rs.getString("course"), rs.getInt("type")));
				mapMedicineBrand.put(rs.getString("id"),new MedicineItemInfo(rs.getInt("sn"), rs.getString("id") , rs.getString("pId"),  rs.getString("groupName"),  rs.getString("companyName"),  rs.getString("schedule"),  rs.getString("time"),  rs.getString("course"), rs.getInt("type")));
				mapMedicineBrand.put(rs.getString("groupName"),new MedicineItemInfo(rs.getInt("sn"), rs.getString("id") , rs.getString("pId"),  rs.getString("groupName"),  rs.getString("companyName"),  rs.getString("schedule"),  rs.getString("time"),  rs.getString("course"), rs.getInt("type")));
				listMedicineBrandList.add(new MedicineItemInfo(rs.getInt("sn"), rs.getString("id") , rs.getString("pId"),  rs.getString("groupName"),  rs.getString("companyName"),  rs.getString("schedule"),  rs.getString("time"),  rs.getString("course"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	public static void loadCheapComplainInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "SELECT slno,id,name,headId,type from tbCC WHERE TYPE = '"+NodeType.CHEAP_COMPLAIN.getType()+"' group By name ORDER BY slNo";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapCheapComplain.clear();
			cheapComplainIdList.clear();
			listCheapComplainList.clear();
			while(rs.next()){
				cheapComplainIdList.add(rs.getString("id"));
				mapCheapComplain.put(rs.getString("headId")+rs.getString("name"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				mapCheapComplain.put(rs.getString("id"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				mapCheapComplain.put(rs.getString("name"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				listCheapComplainList.add(new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadCheapComplainCauseInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "SELECT slno,id,name,headId,type from tbCC WHERE TYPE = '"+NodeType.CHEAP_COMPLAIN_CAUSE.getType()+"' group By name ORDER BY slNo";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapCheapComplainCause.clear();
			listCheapComplainCauseList.clear();
			while(rs.next()){
				mapCheapComplainCause.put(rs.getString("headId")+rs.getString("name"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				mapCheapComplainCause.put(rs.getString("id"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				mapCheapComplainCause.put(rs.getString("name"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
				listCheapComplainCauseList.add(new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadAdviseInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "select slNo,id,pId,advise,type from tbAdvise group by advise order by slNo";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapAdvise.clear();

			listAdvise.clear();
			while(rs.next()){
				listAdviseData.add(rs.getString("advise"));
				mapAdvise.put(rs.getString("pId")+rs.getString("advise"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("pId"), rs.getString("advise"), rs.getInt("type")));
				mapAdvise.put(rs.getString("id"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("pId"), rs.getString("advise"), rs.getInt("type")));
				mapAdvise.put(rs.getString("advise"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("pId"), rs.getString("advise"), rs.getInt("type")));
				listAdvise.add(new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("pId"), rs.getString("advise"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadInvestigationInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "select * from tbtestgroup where type='"+NodeType.INVESTIGATION.getType()+"' group by groupname order by slNo;";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapInvestigation.clear();

			listInvestigation.clear();
			while(rs.next()){
				listInvestigationData.add(rs.getString("groupname"));
				mapInvestigation.put(rs.getString("pId")+rs.getString("groupName"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapInvestigation.put(rs.getString("id"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				mapInvestigation.put(rs.getString("groupName"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
				listInvestigation.add(new TableItemInfo(rs.getInt("slNO"), rs.getString("id"), rs.getString("pId"), rs.getString("groupName"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadAdviseGroupInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "SELECT slno,id,GROUPname,headid,type FROM tbadvisegroup GROUP BY groupname";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapAdviseGroup.clear();

			listAdviseGroup.clear();
			while(rs.next()){

				mapAdviseGroup.put(rs.getString("headid")+rs.getString("groupName"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headid"), rs.getString("groupName"), rs.getInt("type")));
				mapAdviseGroup.put(rs.getString("id"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headid"), rs.getString("groupName"), rs.getInt("type")));
				mapAdviseGroup.put(rs.getString("groupName"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headid"), rs.getString("groupName"), rs.getInt("type")));
				listAdviseGroup.add(new TableItemInfo(rs.getInt("slNO"), rs.getString("id"), rs.getString("headid"), rs.getString("groupName"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadClinicalExaminationInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "select * FROM tbclinicalexamination WHERE type = '"+NodeType.CLINICAL_EXAMINATION.getType()+"'order by sn ";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapClinicalExamination.clear();

			listClinicalExamination.clear();
			while(rs.next()){

				mapClinicalExamination.put(rs.getString("headid")+rs.getString("name"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("headid"), rs.getString("name"), rs.getInt("type")));
				mapClinicalExamination.put(rs.getString("id"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("headid"), rs.getString("name"), rs.getInt("type")));
				mapClinicalExamination.put(rs.getString("name"),new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("headid"), rs.getString("name"), rs.getInt("type")));
				listClinicalExamination.add(new TableItemInfo(rs.getInt("sn"), rs.getString("id"), rs.getString("headid"), rs.getString("name"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadInvestigationGroupInfo() {
		// TODO Auto-generated method stub
		try{
			sql = "SELECT slno,id,GROUPname,headid,type FROM tbinvestigationgroup GROUP BY groupname";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapInvestigationGroup.clear();

			listInvestigationGroup.clear();
			while(rs.next()){

				mapInvestigationGroup.put(rs.getString("headid")+rs.getString("groupName"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headid"), rs.getString("groupName"), rs.getInt("type")));
				mapInvestigationGroup.put(rs.getString("id"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headid"), rs.getString("groupName"), rs.getInt("type")));
				mapInvestigationGroup.put(rs.getString("groupName"),new TableItemInfo(rs.getInt("slno"), rs.getString("id"), rs.getString("headid"), rs.getString("groupName"), rs.getInt("type")));
				listInvestigationGroup.add(new TableItemInfo(rs.getInt("slNO"), rs.getString("id"), rs.getString("headid"), rs.getString("groupName"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void LoadAllTypeSchedule(){
		try {	
			mapSchedule.clear();			
			mapTime.clear();
			mapCourse.clear();
			
			scheduleList.clear();
			timeList.clear();
			courseList.clear();

			sql="Select * from tbsuggestions group by suggestion order by suggestion ";
			ResultSet rs1= databaseHandler.execQuery(sql);
			while(rs1.next()){
				if(rs1.getInt("type")==1) {
					mapSchedule.put(rs1.getString("suggestion"), new SuggestionInfo(rs1.getString("id"), rs1.getString("suggestion"), rs1.getInt("type")));
					scheduleList.add(new SuggestionInfo(rs1.getString("id"), rs1.getString("suggestion"), rs1.getInt("type")));
				}else if(rs1.getInt("type")==2) {
					mapTime.put(rs1.getString("suggestion"), new SuggestionInfo(rs1.getString("id"), rs1.getString("suggestion"), rs1.getInt("type")));
					timeList.add(new SuggestionInfo(rs1.getString("id"), rs1.getString("suggestion"), rs1.getInt("type")));
				}else if(rs1.getInt("type")==3) {
					mapCourse.put(rs1.getString("suggestion"), new SuggestionInfo(rs1.getString("id"), rs1.getString("suggestion"), rs1.getInt("type")));
					courseList.add(new SuggestionInfo(rs1.getString("id"), rs1.getString("suggestion"), rs1.getInt("type")));
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static boolean isInvestigationExist(String headIdInvestigationMame){
		if(mapInvestigation.get(headIdInvestigationMame) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getInvestigationInfo(String headIdInvestigationNameOrId){
		if(mapInvestigation.get(headIdInvestigationNameOrId) != null){
			return mapInvestigation.get(headIdInvestigationNameOrId);

		}else
			return null;
	}

	public static boolean isDiseaseExist(String headIdDiseasename){
		if(mapDisease.get(headIdDiseasename) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getDiseaseInfo(String headIddiseaseNameOrId){
		if(mapDisease.get(headIddiseaseNameOrId) != null){
			return mapDisease.get(headIddiseaseNameOrId);

		}else
			return null;
	}

	public static ObservableList<String> getDiseaseDataList(){
		return listDiseaseData;
	}

	public static boolean isMedicineGroupExist(String headIdMedicineGroupName){
		if(mapMedicineGroup.get(headIdMedicineGroupName) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getMedicineGroupInfo(String headIdMedicineGroupNameOrId){
		if(mapMedicineGroup.get(headIdMedicineGroupNameOrId) != null){
			return mapMedicineGroup.get(headIdMedicineGroupNameOrId);

		}else
			return null;
	}
	public static ObservableList<String> getMedicineGroupDataList(){
		return listMedicineGroupData;
	}

	public static boolean isGenericExist(String headIdGenericname){
		if(mapGeneric.get(headIdGenericname) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getGenericInfo(String headIdGenericname){
		if(mapGeneric.get(headIdGenericname) != null){
			return mapGeneric.get(headIdGenericname);

		}else
			return null;
	}
	public static ObservableList<String> getGenericDataList(){
		return listGenericData;
	}
	public static boolean isMedicineBrandExist(String headIdMedicineBrand){
		if(mapMedicineBrand.get(headIdMedicineBrand) != null){
			return true;
		}
		return false;
	}

	public static MedicineItemInfo getMedicineBrandInfo(String headIdMedicineBrandname){
		if(mapMedicineBrand.get(headIdMedicineBrandname) != null){
			return mapMedicineBrand.get(headIdMedicineBrandname);

		}else
			return null;
	}

	public static ObservableList<String> getMedicineBrandDataList(){
		return listMedicineBrandData;
	}

	public static boolean isCheapComplainExist(String headIdCheapComplain){
		if(mapCheapComplain.get(headIdCheapComplain) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getCheapComplainInfo(String headIdCheapComplainOrId){
		if(mapCheapComplain.get(headIdCheapComplainOrId) != null){
			return mapCheapComplain.get(headIdCheapComplainOrId);

		}else
			return null;
	}

	public static boolean isCheapComplainCauseExist(String headIdCheapComplainCause){
		if(mapCheapComplainCause.get(headIdCheapComplainCause) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getCheapComplainCauseInfo(String headIdCheapComplainCauseOrId){
		if(mapCheapComplainCause.get(headIdCheapComplainCauseOrId) != null){
			return mapCheapComplainCause.get(headIdCheapComplainCauseOrId);

		}else
			return null;
	}


	public static boolean isAdviseExist(String headIdAdviseName){
		if(mapAdvise.get(headIdAdviseName) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getAdviseInfo(String headIdAdviseNameOrId){
		if(mapAdvise.get(headIdAdviseNameOrId) != null){
			return mapAdvise.get(headIdAdviseNameOrId);

		}else
			return null;
	}

	public static ObservableList<String> getAdviseDataList(){
		return listAdviseData;
	}

	public static boolean isAdviseGroupExist(String headIdAdviseGroupName){
		if(mapAdviseGroup.get(headIdAdviseGroupName) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getAdviseGroupInfo(String headIdAdviseGroupNameOrId){
		if(mapAdviseGroup.get(headIdAdviseGroupNameOrId) != null){
			return mapAdviseGroup.get(headIdAdviseGroupNameOrId);

		}else
			return null;
	}

	public static boolean isInvestigationGroupExist(String headIdInvestigationGroupName){
		if(mapInvestigationGroup.get(headIdInvestigationGroupName) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getInvestigationGroupInfo(String headIdInvestigationGroupNameOrId){
		if(mapInvestigationGroup.get(headIdInvestigationGroupNameOrId) != null){
			return mapInvestigationGroup.get(headIdInvestigationGroupNameOrId);

		}else
			return null;
	}

	public static ObservableList<String> getInvestigationDataList(){
		return listInvestigationData;
	}

	public static boolean isClinicalExaminationExist(String headIdClinicalExamination){
		if(mapClinicalExamination.get(headIdClinicalExamination) != null){
			return true;
		}
		return false;
	}

	public static TableItemInfo getClinicalExaminationInfo(String headIdClinicalExaminationOrId){
		if(mapClinicalExamination.get(headIdClinicalExaminationOrId) != null){
			return mapClinicalExamination.get(headIdClinicalExaminationOrId);
		}else
			return null;
	}

	public static SuggestionInfo getScheduleInfo(String schedule){
		if(mapSchedule.get(schedule) != null){
			return mapSchedule.get(schedule);
		}else
			return null;
	}

	public static SuggestionInfo getTimeInfo(String time){
		if(mapTime.get(time) != null){
			return mapTime.get(time);
		}else
			return null;
	}
	public static SuggestionInfo getCourseInfo(String course){
		if(mapCourse.get(course) != null){
			return mapCourse.get(course);
		}else
			return null;
	}

	
	
	public static ArrayList<SuggestionInfo> getScheduleList() {
		return scheduleList;
	}

	public static void setScheduleList(ArrayList<SuggestionInfo> scheduleList) {
		LoadedInfo.scheduleList = scheduleList;
	}

	public static ArrayList<SuggestionInfo> getTimeList() {
		return timeList;
	}

	public static void setTimeList(ArrayList<SuggestionInfo> timeList) {
		LoadedInfo.timeList = timeList;
	}

	public static ArrayList<SuggestionInfo> getCourseList() {
		return courseList;
	}

	public static void setCourseList(ArrayList<SuggestionInfo> courseList) {
		LoadedInfo.courseList = courseList;
	}

	public static class TableItemInfo{
		int slNo;
		String itemId;
		String headId;
		String itemName;
		int type;

		public TableItemInfo(int slNo,String itemId,String headId,String itemName,int type){
			this.slNo = slNo;
			this.itemId = itemId;
			this.headId = headId;
			this.itemName = itemName;
			this.type = type;
		}

		public int getSlNo() {
			return slNo;
		}

		public void setSlNo(int slNo) {
			this.slNo = slNo;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getHeadId() {
			return headId;
		}

		public void setHeadId(String headId) {
			this.headId = headId;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

	}

	public static class MedicineItemInfo{
		int slNo;
		String itemId;
		String headId;
		String itemName;
		String companyName;
		String schedule;
		String time;
		String course;
		int type;

		public MedicineItemInfo(int slNo,String itemId,String headId,String itemName,String companyName,String schedule,String time,String course,int type){
			this.slNo = slNo;
			this.itemId = itemId;
			this.headId = headId;
			this.itemName = itemName;
			this.companyName = companyName;
			this.schedule = schedule;
			this.time = time;
			this.course = course;
			this.type = type;
		}

		public int getSlNo() {
			return slNo;
		}

		public void setSlNo(int slNo) {
			this.slNo = slNo;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getHeadId() {
			return headId;
		}

		public void setHeadId(String headId) {
			this.headId = headId;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getSchedule() {
			return schedule;
		}

		public void setSchedule(String schedule) {
			this.schedule = schedule;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getCourse() {
			return course;
		}

		public void setCourse(String course) {
			this.course = course;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

	}

	public static class TreeItemInfo extends Text{
		int slNo;
		String itemId;
		String headId;
		String name;
		String ancestorId;
		int type;
		public TreeItemInfo(int slNo,String itemId,String headId,String name,String ancestorId,int type){
			this.slNo = slNo;
			this.itemId = itemId;
			this.headId = headId;
			this.name = name;
			this.ancestorId = ancestorId;
			this.type = type;
			setText(name);
		}
		public int getSlNo() {
			return slNo;
		}
		public void setSlNo(int slNo) {
			this.slNo = slNo;
		}
		public String getItemId() {
			return itemId;
		}
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public String getHeadId() {
			return headId;
		}
		public void setHeadId(String headId) {
			this.headId = headId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAncestorId() {
			return ancestorId;
		}
		public void setAncestorId(String ancestorId) {
			this.ancestorId = ancestorId;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}


	}

	public static class SuggestionInfo{
		String itemId;
		String suggestion;
		int type;

		public SuggestionInfo(String itemId,String suggestion,int type){
			this.itemId = itemId;
			this.suggestion = suggestion;
			this.type = type;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getSuggestion() {
			return suggestion;
		}

		public void setSuggestion(String suggestion) {
			this.suggestion = suggestion;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}


	}


}
