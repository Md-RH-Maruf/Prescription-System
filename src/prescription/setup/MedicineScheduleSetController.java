package prescription.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prescription.setup.MedicineCreateController.TableItem;
import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.LoadedInfo.SuggestionInfo;
import shareClasses.LoadedInfo.TableItemInfo;
import shareClasses.Notification;
import shareClasses.SessionBeam;


public class MedicineScheduleSetController implements Initializable{

	FxComboBox cmbSystem = new FxComboBox<>();
	FxComboBox cmbDisease = new FxComboBox<>();
	FxComboBox cmbGroup = new FxComboBox<>();
	FxComboBox cmbGenericInd = new FxComboBox<>();

	FxComboBox cmbGeneric = new FxComboBox<>();
	FxComboBox cmbMedicine = new FxComboBox<>();
	FxComboBox cmbSchedule = new FxComboBox<>();
	FxComboBox cmbNameAll = new FxComboBox<>();
	FxComboBox cmbTime = new FxComboBox<>();
	FxComboBox cmbCourse = new FxComboBox<>();

	@FXML
	ComboBox<String> cmbType;

	@FXML
	VBox vBoxComponent;
	@FXML
	VBox vBoxHead;
	@FXML
	HBox hBoxSchedule;
	@FXML
	HBox hBoxTime;
	@FXML
	HBox hBoxCourse;
	@FXML
	HBox hBoxAll;
	@FXML
	HBox hBoxGeneric;

	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnAllSave;
	@FXML
	Button btnAllEdit;
	@FXML
	Button btnDelete;
	@FXML
	Button btnRefresh;
	@FXML
	Button btnAddSchedule;
	@FXML
	Button btnAddTime;
	@FXML
	Button btnAddCourse;

	@FXML
	CheckBox checkScheduleAll;
	@FXML
	CheckBox checkTimeAll;
	@FXML
	CheckBox checkCourseAll;

	DatabaseHandler databaseHandler;
	String sql;

	@FXML
	private TableView<TableItem> tableGeneric;
	ObservableList<TableItem> listGeneric = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItem, String> genericCol;

	@FXML
	private TableView<TableItem> tableMedicine;
	ObservableList<TableItem> listMedicine = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItem, String> medicineCol;


	@FXML
	private TableView<TableItem> tableSchedule;
	ObservableList<TableItem> listSchedule = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItem, String> scheduleCol;
	@FXML
	private TableColumn<TableItem, RadioButton> defaultScheduleCol;

	@FXML
	private TableView<TableItem> tableTime;
	ObservableList<TableItem> listTime = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItem, String> timeCol;
	@FXML
	private TableColumn<TableItem, RadioButton> defaultTimeCol;

	@FXML
	private TableView<TableItem> tableCourse;
	ObservableList<TableItem> listCourse = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItem, String> courseCol;
	@FXML
	private TableColumn<TableItem, RadioButton> defaultCourseCol;


	@FXML
	private TableView<TableItem> tableAll;
	ObservableList<TableItem> listAll = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItem, String> nameAllCol;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		focusMoveAction();
		setCmpData();
		setCmpAction();
		setCmpFocusAction();
		btnRefreshAction(null);
		typeChangeAction(null);
	}

	@FXML
	private void btnSaveAction(ActionEvent event) {
		try {
			if(confirmationCheck("Save This Schedule?")) {
				if(validationCheck()) {
					String parentId = getSlectedParentId();
					int suggestionId = getSuggestionId(getCmbSchedule(), 1);
					if(!isGenericIdExist(parentId,suggestionId)) {
						saveAction(parentId);
						scheduleTimeComboBoxLoad();
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Schedule All ready Exist!","This Generic/Medicine have a schedule...\nYou Can Edit/Delete this Schedule!");
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		try {
			if(confirmationCheck("Edit This Schedule?")) {
				if(validationCheck()) {
					String parentId = getSlectedParentId();
					int suggestionId = getSuggestionId(getCmbSchedule(), 1);
					if(isGenericIdExist(parentId,suggestionId)) {
						editAction(parentId);
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Schedule Not Exist!","This Generic/Medicine have No schedule...\nYou Should Save this Schedule!");
						btnSave.requestFocus();
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnDeleteAction(ActionEvent event) {
		try {
			if(confirmationCheck("Delete This Schedule?")) {
				String parentId = getSlectedParentId();
				int suggestionId = getSuggestionId(getCmbSchedule(), 1);
				if(isGenericIdExist(parentId, suggestionId)) {
					deleteAction();
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Schedule Not Exist!","This Generic/Medicine have No schedule...\nYou Should Save this Schedule!");
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub

		setCmbSystem("");
		setCmbDisease("");
		setCmbGroup("");
		setCmbGeneric("");
		setCmbGenericInd("");
		setCmbMedicine("");
		setCmbSchedule("");
		setCmbTime("");
		setCmbCourse("");

		tableDataLoad(listGeneric, 3, "");
		tableDataLoad(listMedicine, 4, "");

		comboBoxLoad(cmbSystem, 0);
		comboBoxLoad(cmbDisease, 1);
		comboBoxLoad(cmbGroup, 2);
		comboBoxLoad(cmbGeneric, 3);
		comboBoxLoad(cmbGenericInd, 3);

		scheduleTimeComboBoxLoad();
	}

	@FXML
	private void typeChangeAction(ActionEvent event) {
		// TODO Auto-generated method stub

		int type = cmbType.getSelectionModel().getSelectedIndex()+1;

		if(type == 1){
			listAll.clear();
			for(SuggestionInfo suggestion: LoadedInfo.getScheduleList()){
				listAll.add(new TableItem(suggestion.getItemId(), suggestion.getSuggestion()));
			}
		}else if(type == 2){
			listAll.clear();
			for(SuggestionInfo suggestion: LoadedInfo.getTimeList()){
				listAll.add(new TableItem(suggestion.getItemId(), suggestion.getSuggestion()));
			}
		}else if(type == 3){
			listAll.clear();
			for(SuggestionInfo suggestion: LoadedInfo.getCourseList()){
				listAll.add(new TableItem(suggestion.getItemId(), suggestion.getSuggestion()));
			}
		}

		tableAll.setItems(listAll);

	}

	@FXML
	private void btnScheduleSaveAction(ActionEvent event){
		try {

			int type = cmbType.getSelectionModel().getSelectedIndex()+1;
			if(!getCmbNameAll().isEmpty()){
				if(confirmationCheck("Save This Schedule/Time/Course?")) {
					isScheduleExist(getCmbNameAll(),type);
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.LoadAllTypeSchedule();;
							typeChangeAction(null);
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Schedule/Time/Course!","Please Select/Enter Any Schedule/Time Course...");
				cmbNameAll.requestFocus();
			}


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnScheduleEditAction(ActionEvent event){
		try {

			int type = cmbType.getSelectionModel().getSelectedIndex()+1;
			if(tableAll.getSelectionModel().getSelectedItem() != null){
				TableItem tableItem = tableAll.getSelectionModel().getSelectedItem();
				if(!getCmbNameAll().isEmpty()){
					if(confirmationCheck("Edit This Schedule/Time/Course?")) {
						if(editSchedule(tableItem.getId(), getCmbNameAll(),type)){
							new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfull...","Schedule/Time/Course Save Successfully...");
							new Thread(new Runnable() {		
								@Override
								public void run() {
									// TODO Auto-generated method stub
									LoadedInfo.LoadAllTypeSchedule();;
									typeChangeAction(null);
								}
							}).start();
						}

					}
				}else{
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Schedule/Time/Course!","Please Select/Enter Any Schedule/Time Course...");
					cmbNameAll.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select A Item ","Please Select Any Schedule/Time/Course...");
				tableAll.requestFocus();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnScheduleAddAction(ActionEvent event) {
		try {
			if(isCheckScheduleAll()){

			}else{
				if(!getCmbSchedule().isEmpty()){
					if(addValidationCheck()){
						//if(confirmationCheck("Add This Schedule?")){
						if(isScheduleExist(getCmbSchedule(),1)){
							String parentId = getSlectedParentId();
							int suggestionId = getSuggestionId(getCmbSchedule(), 1);
							if(!isGenericIdExist(parentId,suggestionId)) {
								addAction(parentId,String.valueOf(suggestionId),"1");
								scheduleTimeComboBoxLoad();
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Schedule All ready Exist!","This Generic/Medicine have a schedule...\nYou Can Edit/Delete this Schedule!");
							}
						}
						//}
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Schedule!","Please Select/Enter Any Schedule...");
					cmbSchedule.requestFocus();
				}

			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void genericTableClickAction(MouseEvent event) {

		if(tableGeneric.getSelectionModel().getSelectedItem() != null) {
			TableItem tempTableItem = tableGeneric.getSelectionModel().getSelectedItem();

			//tableDataLoad(listBrand, 4,tempTableItem.getItemName());
			setCmbMedicine("");
			setCmbGeneric(tempTableItem.getItemName());
			setCmbGenericInd(tempTableItem.getItemName());	

			findHeadName(cmbGroup,tempTableItem.getItemName(),3);
			findHeadName(cmbDisease,cmbGroup.getEditor().getText(),2);
			findHeadName(cmbSystem,cmbDisease.getEditor().getText(),1);

			scheduleFind();
		}
	}

	@FXML
	private void scheduleTimeAllTableClickAction(MouseEvent event) {

		if(tableAll.getSelectionModel().getSelectedItem() != null) {
			TableItem tempTableItem = tableAll.getSelectionModel().getSelectedItem();

			//tableDataLoad(listBrand, 4,tempTableItem.getItemName());
			setCmbNameAll(tempTableItem.getItemName());
		}
	}

	@FXML
	private void medicineTableClickAction(MouseEvent event) {

		if(tableMedicine.getSelectionModel().getSelectedItem() != null) {
			TableItem tempTableItem = tableMedicine.getSelectionModel().getSelectedItem();

			//tableDataLoad(listBrand, 4,tempTableItem.getItemName());
			setCmbMedicine(tempTableItem.getItemName());
			scheduleFind();

		}
	}

	private void saveAction(String pId) {
		try {
			sql= "insert INTO tbschedule (genericid,suggestionid,type,entryTime,entryBy) values("+pId+","+getSuggestionId(getCmbSchedule(), 1)+",1,now(),'"+SessionBeam.getUserId()+"');";
			databaseHandler.execAction(sql);

			sql= "insert INTO tbschedule (genericid,suggestionid,type,entryTime,entryBy) values("+pId+","+getSuggestionId(getCmbTime(), 2)+",2,now(),'"+SessionBeam.getUserId()+"');";	
			databaseHandler.execAction(sql);

			sql= "insert INTO tbschedule (genericid,suggestionid,type,entryTime,entryBy) values("+pId+","+getSuggestionId(getCmbCourse(), 3)+",3,now(),'"+SessionBeam.getUserId()+"');";		
			databaseHandler.execAction(sql);

			new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","This Schedule Save Successfully...");

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void addAction(String pId,String suggestionId,String type) {
		try {

			sql= "insert INTO tbschedule (genericid,suggestionid,type,entryTime,entryBy) values("+pId+","+suggestionId+","+type+",now(),'"+SessionBeam.getUserId()+"');";
			databaseHandler.execAction(sql);
			new Notification(Pos.TOP_CENTER, "Information graphic", "Add Successfully!","This Schedule Add Successfully...");


		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void editAction(String pId) {
		try {
			sql= "update tbschedule set suggestionid="+getSuggestionId(getCmbSchedule(), 1)+" where genericid="+pId+" and type=1;";		
			databaseHandler.execAction(sql);

			sql= "update tbschedule set suggestionid="+getSuggestionId(getCmbTime(), 2)+" where genericid="+pId+" and type=2;";	
			databaseHandler.execAction(sql);

			sql= "update tbschedule set suggestionid="+getSuggestionId(getCmbCourse(), 3)+" where genericid="+pId+" and type=3;";	
			databaseHandler.execAction(sql);

			new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","This Schedule Edit Successfully...");
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void deleteAction() {
		try {
			sql= "delete from tbschedule  where genericid="+getSlectedParentId()+" ;";
			databaseHandler.execAction(sql);
			new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfully!","This Schedule Delete Successfully...");
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void scheduleTimeComboBoxLoad(){
		try {	
			cmbSchedule.data.clear();
			cmbSchedule.data.add("");

			cmbTime.data.clear();
			cmbTime.data.add("");

			cmbCourse.data.clear();
			cmbCourse.data.add("");

			cmbNameAll.data.clear();
			cmbNameAll.data.add("");

			sql="Select * from tbsuggestions group by suggestion order by suggestion ";
			ResultSet rs1= databaseHandler.execQuery(sql);
			while(rs1.next()){
				if(rs1.getInt("type")==1) {
					cmbSchedule.data.add(rs1.getString("suggestion"));
				}else if(rs1.getInt("type")==2) {
					cmbTime.data.add(rs1.getString("suggestion"));
				}else if(rs1.getInt("type")==3) {
					cmbCourse.data.add(rs1.getString("suggestion"));
				}
				cmbNameAll.data.add(rs1.getString("suggestion"));
			}


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}





	private void comboBoxLoad(FxComboBox tempSuggest,int type) {
		try {
			tempSuggest.data.clear();
			tempSuggest.data.add("");
			String sql= "select * from tbmedicineGroup where type='"+type+"' group by groupname order by sn,groupname";

			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				tempSuggest.data.add(rs.getString("GroupName"));
			}
			if(tempSuggest.data.size()<=1) {
				tempSuggest.getEditor().setText("Empty");
			}else {
				tempSuggest.getEditor().setText("");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void comboBoxLoad(FxComboBox tempSuggest,int type,String headName) {
		String id=getId(headName,type-1);
		tempSuggest.data.clear();
		tempSuggest.data.add("");

		String sql="";
		if(headName.equals("")) {
			sql="select * from tbmedicineGroup where type='"+type+"' group by groupname order by sn,groupname";
		}else {
			sql="select * from tbmedicineGroup where type='"+type+"' and pid='"+id+"' group by groupname order by sn,groupname";
		}
		try {
			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				tempSuggest.data.add(rs.getString("GroupName"));
			}

			if(tempSuggest.data.size()<=1) {
				tempSuggest.getEditor().setText("Empty");
			}else {
				tempSuggest.getEditor().setText("");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void tableDataLoad(ObservableList list,int parentType,int ChildType,String parentName) {
		String id=getId(parentName,parentType);

		String type=String.valueOf(ChildType);

		list.clear();

		String sql="";
		if(parentName.equals("")) {
			sql="select * from tbmedicineGroup where type='"+ChildType+"' group by groupname order by sn,groupname";
		}else {
			sql="select * from tbmedicineGroup where type='"+ChildType+"' and pid='"+id+"' group by groupname order by sn,groupname";
		}
		try {
			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				list.add(new TableItem(rs.getString("id"),rs.getString("groupName"),type));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}

		if(ChildType == 3) {
			tableGeneric.setItems(list);
		}else if(ChildType == 4) {
			tableMedicine.setItems(list);
		}
	}

	private void tableDataLoad(ObservableList list,int type,String name) {
		try {
			String id=getId(name,type-1);


			String childType= String.valueOf(type);

			list.clear();

			String sql="";

			if(type==4) {
				if(name.equals("")) {
					sql="select mg.id,mg.pId,mg.groupname,mc.Name from tbmedicineGroup mg join tbmedicinecompany mc on mg.id = mc.id where mg.type='"+childType+"' group by groupname order by sn,groupname";
				}else {
					sql="select mg.id,mg.pId,mg.groupname,mc.Name from tbmedicineGroup mg join tbmedicinecompany mc on mg.id = mc.id where mg.type='"+childType+"' and mg.pid='"+id+"' group by groupname order by sn,groupname";
				}


				ResultSet rs=databaseHandler.execQuery(sql);
				while(rs.next()) {
					list.add(new TableItem(rs.getString("id"),rs.getString("groupName"),childType));
				}
			}else {
				if(name.equals("")) {
					sql="select * from tbmedicineGroup where type='"+childType+"' group by groupname order by sn,groupname";
				}else {
					sql="select * from tbmedicineGroup where type='"+childType+"' and pid='"+id+"' group by groupname order by sn,groupname";
				}


				ResultSet rs= databaseHandler.execQuery(sql);
				while(rs.next()) {
					list.add(new TableItem(rs.getString("id"),rs.getString("groupName"),childType));
				}
			}

			if(type == 3) {
				tableGeneric.setItems(list);
			}else if(type == 4) {
				tableMedicine.setItems(list);
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void scheduleFind() {
		try {

			String sql = "select *,(select suggestion from tbsuggestions s where s.id=sc.suggestionId) as suggession   from tbschedule sc where sc.genericid="+getSlectedParentId()+"";
			ResultSet rs= databaseHandler.execQuery(sql);
			boolean  row=true;
			while(rs.next()) {
				if(rs.getInt("type")==1) {
					setCmbSchedule(rs.getString("suggession"));

				}else if(rs.getInt("type")==2) {
					setCmbTime(rs.getString("suggession"));
				}else if(rs.getInt("type")==3) {
					setCmbCourse(rs.getString("suggession"));
				}
				row = false;
			}


			if(getSlectedParentId().equals("0") || row) {
				setCmbSchedule("");
				setCmbTime("");
				setCmbCourse("");
			}



		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	private String getSlectedParentId() {
		try {
			String sql;
			if(getCmbMedicine().isEmpty())
				sql ="select * from tbmedicinegroup where groupname='"+getCmbGeneric()+"' and type =3;";
			else
				sql ="select * from tbmedicinegroup where groupname='"+getCmbMedicine()+"' and type =4;";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	private void findHeadName(FxComboBox combo, String name, int type) {
		try {
			sql= "select (select groupname from tbmedicinegroup where id= mg.pid GROUP BY groupname) as headname from tbmedicinegroup mg where groupname = '"+name+"' and type = "+type+"";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				combo.getEditor().setText(rs.getString("headname"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private String getId(String name, int type) {
		try {
			sql="select id from tbmedicineGroup where type='"+type+"' and Groupname='"+name+"'";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return "-1";
	}

	private boolean validationCheck() {
		if(!getCmbMedicine().isEmpty()) {
			if(!getCmbSchedule().isEmpty()) {
				if(!getCmbCourse().isEmpty()) {
					if(isBrandNameExist()) {
						if(isScheduleExist(getCmbSchedule(),1)) {
							if(isScheduleExist(getCmbTime(),2)) {
								if(isScheduleExist(getCmbCourse(),3)) {
									return true;
								}
							}
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Medicine Name!","Your Selected Medicine Name is invalid!");
						cmbMedicine.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Course!","Please Select/Enter Any Course...");
					cmbCourse.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Schedule!","Please Select/Enter Any Schedule...");
				cmbSchedule.requestFocus();
			}
		}else if(!getCmbGeneric().isEmpty()) {
			if(!getCmbSchedule().isEmpty()) {
				if(!getCmbCourse().isEmpty()) {
					if(isGenericNameExist()) {
						if(isScheduleExist(getCmbSchedule(),1)) {
							if(isScheduleExist(getCmbTime(),2)) {
								if(isScheduleExist(getCmbCourse(),3)) {
									System.out.println("Return true from validation check");
									return true;
								}
							}
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Generic Name!","Your Selected Generic Name is invalid!");
						cmbGeneric.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Course!","Please Select/Enter Any Course...");
					cmbCourse.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Schedule!","Please Select/Enter Any Schedule...");
				cmbCourse.requestFocus();
			}
		}
		else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Select Generic..","Please Select/Enter Any Generic...");
			cmbGeneric.requestFocus();
		}
		return false;
	}

	private boolean addValidationCheck() {
		if(!getCmbMedicine().isEmpty()){
			if(isBrandNameExist()) {
				return true;
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Medicine Name!","Your Selected Medicine Name is invalid!");
				cmbMedicine.requestFocus();
			}
		}else if(!getCmbGeneric().isEmpty()){
			if(isGenericNameExist()) {
				return true;
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Generic Name!","Your Selected Generic Name is invalid!");
				cmbGeneric.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Medicine/Generic Name!","Please Select a Medicine Brand or Generic!");
			cmbMedicine.requestFocus();
		}
		return false;
	}
	private boolean isGenericIdExist(String id,int suggestionId) {
		try {
			sql="select * from tbschedule where genericid="+id+" and suggestionId='"+suggestionId+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

	private boolean isScheduleExist(String name,int type) {
		try {
			if(!name.equals("")) {
				sql = "select * from tbsuggestions where suggestion='"+name+"' and type="+type+"";				
				ResultSet rs= databaseHandler.execQuery(sql);;
				if(rs.next()) {
					return true;
				}else {
					sql="insert into tbsuggestions (id,suggestion,type) values("+getSuggestionMaxId()+",'"+name+"',"+type+")";		
					databaseHandler.execAction(sql);
					return true;
				}
			}else{
				return true;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return false;
	}

	private boolean editSchedule(String id,String name,int type){
		try {
			if(!name.equals("")) {
				sql = "select * from tbsuggestions where suggestion='"+name+"' and id != "+id+" and type='"+type+"'";				
				ResultSet rs= databaseHandler.execQuery(sql);;
				if(rs.next()) {
					return true;
				}else {
					sql="update tbsuggestions set suggestion = '"+name+"' where id = '"+id+"'";		
					databaseHandler.execAction(sql);
					return true;
				}


			}else{
				return true;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return false;
	}

	private int getSuggestionMaxId() {

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

	private int getSuggestionId(String name,int type) {

		try {
			ResultSet rs=databaseHandler.execQuery("select id from tbsuggestions where suggestion='"+name+"' and type="+type+"");
			if(rs.next()) {
				return rs.getInt("id");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}

	private boolean isGenericNameExist() {
		try {
			sql = "Select * from tbmedicinegroup where type = 3 and groupname='"+getCmbGeneric()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);;
			if(rs.next()) {
				return true;
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

	private boolean isBrandNameExist() {
		try {
			sql = "Select * from tbmedicinegroup where type = 4 and groupname='"+getCmbMedicine()+"'";

			ResultSet rs = databaseHandler.execQuery(sql);;
			if(rs.next()) {
				return true;
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

	protected boolean duplicateCheck(int pId, String name) {
		try {
			sql="select * from tbtestGroup where pid="+pId+" and groupname='"+name+"'";

			ResultSet rs= databaseHandler.execQuery(sql);;
			if(rs.next()) {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}


	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message);
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbSystem.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystem);	
		});

		cmbDisease.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDisease);	
		});

		cmbGroup.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbGroup);	
		});

		cmbGenericInd.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbGenericInd);	
		});

		cmbGeneric.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbGeneric);	
		});
		cmbMedicine.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbMedicine);	
		});
		cmbSchedule.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSchedule);	
		});
		cmbTime.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbTime);	
		});
		cmbCourse.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCourse);	
		});
		/*txtCC.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtCC);
		});*/
	}

	private void selectCombboxIfFocused(ComboBox box){
		Platform.runLater(() -> {
			if ((box.getEditor().isFocused() || box.isFocused()) && !box.getEditor().getText().isEmpty()) {
				box.getEditor().selectAll();
			}
		});
	}
	private void selectTextIfFocused(TextField text){
		Platform.runLater(() -> {
			if (text.isFocused()  && !text.getText().isEmpty()) {
				text.selectAll();
			}
		});
	}

	private void setCmpAction() {
		// TODO Auto-generated method stub
		cmbSystem.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					cmbDisease.getEditor().setText("");
					cmbGroup.getEditor().setText("");
					comboBoxLoad(cmbGroup, 2);
					comboBoxLoad( cmbDisease, 1, getCmbSystem());
					tableDataLoad(listGeneric, 0,3, getCmbSystem());			
				}
			}    
		});

		cmbDisease.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					cmbGroup.getEditor().setText("");
					comboBoxLoad(cmbGroup, 2, getCmbDisease());
					tableDataLoad(listGeneric, 1,3, getCmbDisease());

				}
			}    
		});

		cmbGroup.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					tableDataLoad(listGeneric, 3, getCmbGroup());
					scheduleFind();
				}
			}    
		});

		cmbGenericInd.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					tableDataLoad(listMedicine, 4,getCmbGenericInd());
					setCmbGeneric(newValue);
					scheduleFind();
				}
			}    
		});

		cmbGeneric.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					comboBoxLoad( cmbMedicine , 4 , getCmbGeneric());
					scheduleFind();
				}
			}    
		});

		cmbMedicine.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					scheduleFind();
				}
			}    
		});
	}

	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control =  {cmbSchedule,cmbTime,cmbCourse,cmbGeneric};
		new FocusMoveByEnter(control);

		Control[] control2 =  {cmbGenericInd,cmbMedicine};
		new FocusMoveByEnter(control2);

		Control[] control3 =  {cmbGeneric,cmbMedicine,cmbSchedule,cmbTime,cmbCourse,btnSave};
		new FocusMoveByEnter(control3);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub

		tableGeneric.setColumnResizePolicy(tableGeneric.CONSTRAINED_RESIZE_POLICY);
		genericCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableMedicine.setColumnResizePolicy(tableMedicine.CONSTRAINED_RESIZE_POLICY);
		medicineCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableAll.setColumnResizePolicy(tableAll.CONSTRAINED_RESIZE_POLICY);
		nameAllCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableAll.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		nameAllCol.setCellFactory(tc -> {
			TableCell<TableItem, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(nameAllCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		tableSchedule.setColumnResizePolicy(tableSchedule.CONSTRAINED_RESIZE_POLICY);
		scheduleCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		defaultScheduleCol.setCellValueFactory(new PropertyValueFactory("radioDefault"));

		tableTime.setColumnResizePolicy(tableTime.CONSTRAINED_RESIZE_POLICY);
		timeCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		defaultTimeCol.setCellValueFactory(new PropertyValueFactory("radioDefault"));

		tableCourse.setColumnResizePolicy(tableCourse.CONSTRAINED_RESIZE_POLICY);
		courseCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		defaultCourseCol.setCellValueFactory(new PropertyValueFactory("radioDefault"));
	}

	private void addCmp() {
		// TODO Auto-generated method stub
		vBoxHead.getChildren().clear();
		cmbSystem.setPrefWidth(700);
		cmbSystem.setPrefHeight(25);
		cmbSystem.setPromptText("System Name");

		cmbDisease.setPrefWidth(700);
		cmbDisease.setPrefHeight(25);
		cmbDisease.setPromptText("Disease Name");

		cmbGroup.setPrefWidth(700);
		cmbGroup.setPrefHeight(25);
		cmbGroup.setPromptText("Group Name");


		vBoxHead.getChildren().addAll(cmbSystem,cmbDisease,cmbGroup);

		hBoxGeneric.getChildren().clear();
		cmbGenericInd.setPrefWidth(700);
		cmbGenericInd.setPrefHeight(25);
		cmbGenericInd.setPromptText("Group Name");
		hBoxGeneric.getChildren().addAll(cmbGenericInd);

		vBoxComponent.getChildren().clear();


		cmbGeneric.setPrefWidth(700);
		cmbGeneric.setPrefHeight(25);
		cmbGeneric.setPromptText("Generic Name");

		cmbMedicine.setPrefWidth(700);
		cmbMedicine.setPrefHeight(25);
		cmbMedicine.setPromptText("Medicine Name");

		cmbSchedule.setPrefWidth(575);
		cmbSchedule.setPrefHeight(25);
		cmbSchedule.setPromptText("Schedule");

		cmbTime.setPrefWidth(246);
		cmbTime.setPrefHeight(25);
		cmbTime.setPromptText("Time");

		cmbCourse.setPrefWidth(246);
		cmbCourse.setPrefHeight(25);
		cmbCourse.setPromptText("Course");

		hBoxSchedule.getChildren().remove(1);
		hBoxSchedule.getChildren().add(1, cmbSchedule);

		hBoxTime.getChildren().remove(1);
		hBoxTime.getChildren().add(1, cmbTime);

		hBoxCourse.getChildren().remove(1);
		hBoxCourse.getChildren().add(1, cmbCourse);

		cmbNameAll.setPrefWidth(415);
		cmbNameAll.setPrefHeight(25);
		hBoxAll.getChildren().remove(1);
		hBoxAll.getChildren().add(1,cmbNameAll);

		vBoxComponent.getChildren().addAll(cmbGeneric,cmbMedicine);

		cmbType.getItems().addAll("Schedule","Time","Course");
		cmbType.getSelectionModel().select(0);
	}

	public class TableItem{

		private SimpleStringProperty id;
		private SimpleStringProperty itemName;
		private SimpleStringProperty companyName;
		private SimpleBooleanProperty isDefault;
		private RadioButton radioDefault;

		public TableItem(String id,String itemName) {
			this.id = new SimpleStringProperty(id);
			this.itemName = new SimpleStringProperty(itemName);
			this.companyName = new SimpleStringProperty("");
			this.isDefault = new SimpleBooleanProperty(false);
		}

		public TableItem(String id,String itemName,boolean isDefault) {
			this.id = new SimpleStringProperty(id);
			this.itemName = new SimpleStringProperty(itemName);
			this.isDefault = new SimpleBooleanProperty(isDefault);
			this.companyName = new SimpleStringProperty("");
			this.radioDefault = new RadioButton();
			this.radioDefault.setSelected(isDefault);
		}

		public TableItem(String id,String itemName,String companyName) {
			this.id = new SimpleStringProperty(id);
			this.itemName = new SimpleStringProperty(itemName);
			this.companyName = new SimpleStringProperty(companyName);
			this.isDefault = new SimpleBooleanProperty(false);
		}

		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getItemName() {
			return itemName.get();
		}

		public void setItemName(String itemName) {
			this.itemName = new SimpleStringProperty(itemName);
		}

		public String getCompanyName() {
			return companyName.get();
		}

		public void setCompanyName(String companyName) {
			this.companyName = new SimpleStringProperty(companyName);
		}

		public SimpleBooleanProperty getIsDefault() {
			return isDefault;
		}

		public void setIsDefault(SimpleBooleanProperty isDefault) {
			this.isDefault = isDefault;
		}

		public RadioButton getRadioDefault() {
			return radioDefault;
		}

		public void setRadioDefault(RadioButton radioDefault) {
			this.radioDefault = radioDefault;
		}


	}

	public String getCmbSystem() {
		if(cmbSystem.getValue() != null)
			return cmbSystem.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystem(String cmbSystem) {
		this.cmbSystem.setValue(cmbSystem);
	}

	public String getCmbDisease() {
		if(cmbDisease.getValue() != null)
			return cmbDisease.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbDisease(String cmbDisease) {
		this.cmbDisease.setValue(cmbDisease);
	}

	public String getCmbGroup() {
		if(cmbGroup.getValue() != null)
			return cmbGroup.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbGroup(String cmbGroup) {
		this.cmbGroup.setValue(cmbGroup);
	}

	public String getCmbGenericInd() {
		if(cmbGenericInd.getValue() != null)
			return cmbGenericInd.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbGenericInd(String cmbGenericInd) {
		this.cmbGenericInd.setValue(cmbGenericInd);
	}

	public String getCmbGeneric() {
		if(cmbGeneric.getValue() != null)
			return cmbGeneric.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbGeneric(String cmbGeneric) {
		this.cmbGeneric.setValue(cmbGeneric);
	}

	public String getCmbMedicine() {
		if(cmbMedicine.getValue() != null)
			return cmbMedicine.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbMedicine(String cmbMedicine) {
		this.cmbMedicine.setValue(cmbMedicine);
	}

	public String getCmbSchedule() {
		if(cmbSchedule.getValue() != null)
			return cmbSchedule.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSchedule(String cmbSchedule) {
		this.cmbSchedule.setValue(cmbSchedule);
	}

	public String getCmbNameAll() {
		if(cmbNameAll.getValue() != null)
			return cmbNameAll.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbNameAll(String cmbNameAll) {
		this.cmbNameAll.setValue(cmbNameAll);
	}

	public String getCmbTime() {
		if(cmbTime.getValue() != null)
			return cmbTime.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbTime(String cmbTime) {
		this.cmbTime.setValue(cmbTime);
	}

	public String getCmbCourse() {
		if(cmbCourse.getValue() != null)
			return cmbCourse.getValue().toString().replace("'", "''").trim();
		else return "";
	}


	public void setCmbCourse(String cmbCourse) {
		this.cmbCourse.setValue(cmbCourse);
	}

	public void setCmbType(String cmbType) {
		this.cmbType.setValue(cmbType);
	}

	public String getCmbType() {
		if(cmbType.getValue() != null)
			return cmbType.getValue().toString().replace("'", "''").trim();
		else return "";
	}
	public boolean isCheckScheduleAll() {
		return checkScheduleAll.isSelected();
	}

	public void setCheckScheduleAll(boolean checkScheduleAll) {
		this.checkScheduleAll.setSelected(checkScheduleAll);
	}

	public boolean isCheckTimeAll() {
		return checkTimeAll.isSelected();
	}

	public void setCheckTimeAll(boolean checkTimeAll) {
		this.checkTimeAll.setSelected(checkTimeAll);
	}

	public boolean getCheckCourseAll() {
		return checkCourseAll.isSelected();
	}

	public void setCheckCourseAll(boolean checkCourseAll) {
		this.checkCourseAll.setSelected(checkCourseAll);
	}



}
