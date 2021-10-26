package prescription.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import shareClasses.AlertMaker;
import shareClasses.CommonMethod;
import shareClasses.CreateNewGroupController;
import shareClasses.EditGroupNameController;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.MedicineRowFactory;
import shareClasses.NodeType;
import shareClasses.LoadedInfo.TableItemInfo;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import shareClasses.TableItemRowFactory;

public class AdviceCreateController implements Initializable{

	FxComboBox cmbSystemDisease = new FxComboBox<>();
	FxComboBox cmbSystemAdvise = new FxComboBox<>();
	FxComboBox cmbDisease = new FxComboBox<>();
	FxComboBox cmbAdvise = new FxComboBox<>();
	FxComboBox cmbAdviseGroup = new FxComboBox<>();

	@FXML
	VBox vBoxAdviseCmp;	
	@FXML
	VBox vBoxSystemDisease;



	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnAddToGroup;
	@FXML
	Button btnRefresh;

	DatabaseHandler databaseHandler;
	String sql;

	MenuItem menuItemDelete ;
	MenuItem menuItemCreateGroup;

	ContextMenu contextMenuResult ;
	
	MenuItem menuItemEditGroupName ;
	MenuItem menuItemDeleteGroupName ;
	MenuItem menuItemAddToGroup;
	MenuItem menuItemRemoveFromGroup;
	MenuItem menuItemUpdateSerial;
	
	ContextMenu contextMenuGroupName;

	@FXML
	private TableView<TableItemInfo> tableSystem;
	@FXML
	private TableColumn<TableItemInfo, String> systemCol;

	@FXML
	private TableView<TableItemInfo> tableDisease;
	@FXML
	private TableColumn<TableItemInfo, String> diseaseCol;

	@FXML
	private TableView<TableItemInfo> tableAdvise;
	@FXML
	private TableColumn<TableItemInfo, String> slCol;
	@FXML
	private TableColumn<TableItemInfo, String> adviseCol;
	
	
	@FXML
	private TableView<TableItemInfo> tableAdviseGroup;
	//ObservableList<TableItemInfo> listAdviseGroup = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> adviseGroupCol;
	
	private DialogCreateNewGroup dialogCreateNewGroup;
	private DialogEditGroupName dialogEditGroupName;
	NodeType nodeType = NodeType.ADVISE;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		
		//dialogCreateNewGroup 
		addCmp();
		focusMoveAction();
		setCmpData();
		setCmpAction();
		setCmpFocusAction();
		btnRefreshAction(null);
	}

	@FXML
	private void btnSaveAction(ActionEvent event) {

		try {
			if(!getCmbDisease().isEmpty()){
				if(!getCmbAdvise().isEmpty()){
					if(LoadedInfo.getDiseaseInfo(getCmbDisease()) != null) {
						if(!LoadedInfo.isAdviseExist(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()+getCmbAdvise())) {
							if(confirmationCheck("Save This Advise?")) {
								int id = CommonMethod.getMaxAdviseId();
								sql="insert into tbadvise (id,advise,pid,type,entryTime,entryBy) values("+id+",'"+getCmbAdvise()+"',"+LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()+",2,NOW(),"+SessionBeam.getUserId()+");";
								if(databaseHandler.execAction(sql)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully...!","Advise Save Successfully...");
									LoadedInfo.loadAdviseInfo();
									LoadedInfo.loadMapAdviseListBySystemId();
									LoadedInfo.loadMapAdviseListByDiseaseId();
									tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
									//adviseTableLoadByPId(getId(getCmbDisease(), 1));
								}

							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready exist!","This Advise Name Allready exist in this Disease...");
							cmbAdvise.requestFocus();
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Disease!","Please Select a valid Disease Name");
						cmbDisease.requestFocus();
					}
				}
				else if(!getCmbAdviseGroup().isEmpty()) {
					if(LoadedInfo.getDiseaseInfo(getCmbDisease()) != null) {
						if(!isGroupNameExist(getCmbAdviseGroup())) {
							if(confirmationCheck("Save Disease As Group Head?")) {
								
								sql="update tbAdviseGroup set headId = '"+LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()+"' where groupname = '"+getCmbAdviseGroup()+"';";
								if(databaseHandler.execAction(sql)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully...!","Advise Save Successfully...");
									//adviseTableLoadByPId(getId(getCmbDisease(), 1));
									if(getCmbDisease().isEmpty()){
										tableAdvise.setItems(LoadedInfo.getAdviseList());
									}else{
										tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
									}
								}

							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Group Name!","This Group name not exist...");
							cmbAdviseGroup.requestFocus();
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Disease!","Please Select a valid Disease Name");
						cmbDisease.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Information graphic", "Insert Advise Name..","Please Enter Advise Name");
					cmbAdvise.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Disease..","Please Select Disease Name");
				cmbDisease.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		try {
			if(tableAdvise.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempTableItem = tableAdvise.getSelectionModel().getSelectedItem();

				if(!getCmbDisease().isEmpty()){
					if(!getCmbAdvise().isEmpty()){
						if(LoadedInfo.getDiseaseInfo(getCmbDisease()) != null) {
							if(LoadedInfo.isAdviseExist(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemName()+getCmbAdvise())) {
								if(confirmationCheck("Edit This Advise?")) {
									String pId= LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId();
									sql = "update tbadvise set advise='"+getCmbAdvise()+"',pId ='"+pId+"' where id="+tempTableItem.getItemId()+"";
									if(databaseHandler.execAction(sql)){
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully...!","Advise Edit Successfully...");
										//adviseTableLoadByPId(getId(getCmbDisease(), 1));
										LoadedInfo.loadAdviseInfo();
										LoadedInfo.loadMapAdviseListByDiseaseId();
										if(getCmbDisease().isEmpty()){
											tableAdvise.setItems(LoadedInfo.getAdviseList());
										}else{
											tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
										}
									}

								}
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready exist!","This Advise Name Allready exist in this Disease...");
								cmbAdvise.requestFocus();
							}

						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Disease!","Please Select a valid Disease Name");
							cmbDisease.requestFocus();
						}
					}
					else{
						new Notification(Pos.TOP_CENTER, "Information graphic", "Insert Advise Name..","Please Enter Advise Name");
						cmbAdvise.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Disease..","Please Select Disease Name");
					cmbDisease.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise!","Please Select Any Advise...");
				tableAdvise.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnAddToGroupAction(ActionEvent event){
		try{
			if(LoadedInfo.getAdviseInfo(getCmbAdvise()) != null) {
				if(!getCmbAdviseGroup().isEmpty()){
					if(LoadedInfo.getAdviseGroupInfo(getCmbAdviseGroup()) != null){
						String groupId = LoadedInfo.getAdviseGroupInfo(getCmbAdviseGroup()).getItemId();
						String adviseId = LoadedInfo.getAdviseInfo(getCmbAdvise()).getItemId();
						int size = LoadedInfo.getAdviseListByGroupId(groupId).size();
								
						sql = "INSERT INTO tbadvisegroup (slNo,id,groupname,adviseId,TYPE,headId,entryTime,userId) VALUES ('"+(size+1)+"','"+groupId+"','"+getCmbAdviseGroup()+"','"+adviseId+"','"+NodeType.ADVISE_GROUP.getType()+"','0',NOW(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
						
						new Notification(Pos.TOP_CENTER, "Information graphic", "Add Successfully...!","Advise Add Successfully.....");
						
						LoadedInfo.loadMapAdviseListByGroupId();
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Valid Advise Group!","Your Selected Group is Invalid....\nPlease Select Any Valid Advise Group...");
						cmbAdviseGroup.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise Group!","Please Select Any Advise Group...");
					cmbAdviseGroup.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise!","Please Select Any Advise...");
				cmbAdvise.requestFocus();
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnDeleteAction(ActionEvent event) {
		try {
			if(tableAdvise.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempTableItem = tableAdvise.getSelectionModel().getSelectedItem();


				if(confirmationCheck("Delete This Advise?")) {
					String pId= LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId();
					sql = "delete from tbadvise where id="+tempTableItem.getItemId()+"";
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfully...!","Advise Delete Successfully...");
						//adviseTableLoadByPId(getId(getCmbDisease(), 1));
						LoadedInfo.loadAdviseInfo();
						LoadedInfo.loadMapAdviseListByDiseaseId();
						if(getCmbDisease().isEmpty()){
							tableAdvise.setItems(LoadedInfo.getAdviseList());
						}else{
							tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
						}
					}

				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise!","Please Select Any Advise...");
				tableAdvise.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		setCmbSystemDisease("");
		setCmbDisease("");
		setCmbAdvise("");
		comboBoxLoad(cmbSystemDisease, 0);
		comboBoxLoad(cmbSystemAdvise, 0);
		comboBoxLoad(cmbDisease, 1);

		tableSystem.setItems(LoadedInfo.getSystemList());
		tableAdviseGroup.setItems(LoadedInfo.getAdviseGroupList());
		tableAdvise.setItems(LoadedInfo.getAdviseList());
		groupLoad();
		adviseLoad();
	}

	@FXML
	private void systemTableClickAction(MouseEvent event) {

		if(tableSystem.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableSystem.getSelectionModel().getSelectedItem();
			setCmbDisease("");
			setCmbSystemDisease(tempTableItem.getItemName());
			setCmbSystemAdvise(tempTableItem.getItemName());
		}
	}

	@FXML
	private void diseaseTableClickAction(MouseEvent event) {

		if(tableDisease.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableDisease.getSelectionModel().getSelectedItem();

			setCmbDisease(tempTableItem.getItemName());
		}
	}

	@FXML
	private void adviseTableClickAction(MouseEvent event) {

		if(tableAdvise.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableAdvise.getSelectionModel().getSelectedItem();
			setCmbAdvise(tempTableItem.getItemName());
		}
	}
	
	@FXML
	private void adviseGroupTableClickAction(MouseEvent event) {

		if(tableAdviseGroup.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableAdviseGroup.getSelectionModel().getSelectedItem();
			setCmbAdviseGroup(tempTableItem.getItemName());
			tableAdvise.setItems(LoadedInfo.getAdviseListByGroupId(tempTableItem.getItemId()));
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
		String id="0";
		switch(type - 1){
		case 0:
			id = LoadedInfo.getSystemInfo(headName).getItemId();
			break;
		case 1:
			id = LoadedInfo.getDiseaseInfo(headName).getItemId();
			break;
		}
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

	/*private void tableDataLoad(ObservableList list,int parentType,int ChildType,String parentName) {
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
				list.add(new TableItem(rs.getString("id"),rs.getString("groupName"),rs.getInt("sn")));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}

		if(ChildType == 0) {
			tableSystem.setItems(list);
		}else if(ChildType == 1) {
			tableDisease.setItems(list);
		}
	}*/

	/*private void tableDataLoad(ObservableList list,int type,String name) {
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
					list.add(new TableItemInfo(rs.getInt("slno"),rs.getString("id"),rs.getString("groupName"),rs.getInt("sn")));
				}
			}else {
				if(name.equals("")) {
					sql="select * from tbmedicineGroup where type='"+childType+"' group by groupname order by sn,groupname";
				}else {
					sql="select * from tbmedicineGroup where type='"+childType+"' and pid='"+id+"' group by groupname order by sn,groupname";
				}


				ResultSet rs= databaseHandler.execQuery(sql);
				while(rs.next()) {
					list.add(new TableItem(rs.getString("id"),rs.getString("groupName"),rs.getInt("sn")));
				}
			}

			if(type == 0) {
				tableSystem.setItems(list);
			}else if(type == 1) {
				tableDisease.setItems(list);
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}*/
	
	private void groupLoad() {
		try {
			
			cmbAdviseGroup.data.clear();
			
			
			sql = "SELECT slno,id,GROUPname,headid,type FROM tbadvisegroup GROUP BY groupname";
			
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				
				cmbAdviseGroup.data.add(rs.getString("GroupName"));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private void adviseLoad() {
		try {
			String sql;
			//listAdvise.clear();
			cmbAdvise.data.clear();

			sql="select * from tbadvise group by advise order by id";
			ResultSet rs= databaseHandler.execQuery(sql);
			while (rs.next()){
				cmbAdvise.data.add(rs.getString("advise"));
				//listAdvise.add(new TableItemInfo(rs.getInt("slno"),rs.getString("id"),rs.getString("pid"), rs.getString("advise"),rs.getInt("type")));
			}

			//tableAdvise.setItems(listAdvise);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*private void adviseTableLoadByPId(String PId) {
		try {
			String sql;
			listAdvise.clear();
			if(PId.equals("0")) {
				sql="select * from tbadvise group by advise order by slNo";
			}else{
				sql= "select * from tbadvise where pid="+PId+" group by advise order by slNo";
			}

			ResultSet rs= databaseHandler.execQuery(sql);
			while (rs.next()){
				listAdvise.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("id"),rs.getString("pId"), rs.getString("advise"),rs.getInt("type")));
			}

			tableAdvise.setItems(listAdvise);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	/*protected void adviseTableLoadByGroupName(String newValue) {
		// TODO Auto-generated method stub
		try {
			String sql;
			//listAdvise.clear();
			if(newValue.equals("")) {
				sql = "SELECT a.id,a.advise FROM tbadvisegroup ag\r\n" + 
						"JOIN tbadvise a\r\n" + 
						"ON a.id = ag.adviseId ";
			}else {
				sql = "SELECT a.id,a.advise FROM tbadvisegroup ag\r\n" + 
						"JOIN tbadvise a\r\n" + 
						"ON a.id = ag.adviseId WHERE groupname = '"+newValue+"' ";
			}

			ResultSet rs= databaseHandler.execQuery(sql);
			while (rs.next()){
				//listAdvise.add(new TableItemInfo(rs.getInt("slid"),rs.getString("id"),rs.getString("pId"), rs.getString("advise"),rs.getInt("Type")));
			}

			//tableAdvise.setItems(listAdvise);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	/*private void adviseTableLoadBySystemName(String name) {
		try {
			//listAdvise.clear();
			if(name.equals("")) {
				sql = "select tg.slNo,tg.id,tg.pid,tg.advise,tg.type from tbmedicinegroup mg join tbadvise tg on mg.id=tg.pId where mg.type=1 group by advise order by tg.slNo";
			}else
				sql = "select tg.slNo,tg.id,tg.pid,tg.advise,tg.type from tbmedicinegroup mg join tbadvise tg on mg.id=tg.pId where mg.type=1 and mg.pid=(select id from tbmedicinegroup where groupname='"+name+"') group by advise order by tg.slNo";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()) {

				listAdvise.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("id"),rs.getString("pId"), rs.getString("advise"),rs.getInt("Type")));

			}
			tableAdvise.setItems(listAdvise);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}*/

	/*private String getMaxId() {
		try {
			ResultSet rs=databaseHandler.execQuery("select ifnull(max(id),0)+1 as id from tbAdvise");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();

		}
		return "0";
	}*/
	/*private String getId(String name, int type) {
		try {
			String sql="select id from tbmedicineGroup where type='"+type+"' and Groupname='"+name+"'";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return "0";
	}*/

	protected boolean duplicateCheck(String pId, String name) {
		try {
			sql="select * from tbadvise where pid="+pId+" and advise='"+name+"'";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	private boolean isGroupNameExist(String groupName) {
		// TODO Auto-generated method stub
		try {
			sql = "select groupName from tbAdviseGroup where groupname = '"+groupName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
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

		cmbDisease.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDisease);

		});

		cmbAdvise.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbAdvise);

		});

		cmbSystemDisease.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemDisease);

		});

		cmbSystemAdvise.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemAdvise);

		});
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
		cmbSystemDisease.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					setCmbSystemAdvise(newValue);
					if(LoadedInfo.getSystemInfo(getCmbSystemDisease())!= null){
						tableDisease.setItems(LoadedInfo.getDiseaseListBySystemId(LoadedInfo.getSystemInfo(getCmbSystemDisease()).getItemId()));
					}else{
						tableDisease.setItems(LoadedInfo.getDiseaseList());
					}
					//tableDataLoad(listDisease, 1, getCmbSystemDisease());
				}
			}    
		});

		cmbSystemAdvise.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					//adviseTableLoadBySystemName(getCmbSystemInvestigation());
					tableAdvise.setItems(LoadedInfo.getAdviseListBySystemId(LoadedInfo.getSystemInfo(getCmbSystemAdvise()).getItemId()));
				}
			}    
		});

		cmbDisease.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					//adviseTableLoadByPId(getId(getCmbDisease(), 1));
					if(getCmbDisease().isEmpty()){
						tableAdvise.setItems(LoadedInfo.getAdviseList());
					}else{
						tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
					}
				}
			}    
		});
		
		/*cmbAdviseGroup.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					adviseTableLoadByGroupName(newValue);
				}
			}    
		});*/

		menuItemDelete.setOnAction(e ->{
			if(tableAdvise.getSelectionModel().getSelectedItem() != null) {
				if(confirmationCheck("Delete This Advise?")) {
					TableItemInfo tempInvestigation = tableAdvise.getSelectionModel().getSelectedItem();
					if(databaseHandler.execAction("delete from tbAdvise where id= '"+tempInvestigation.getItemId()+"' and type= '2'")){

						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Advise Delete Successfully...");
						//adviseTableLoadByPId(getId(getCmbDisease(), 1));
						LoadedInfo.loadAdviseInfo();
						LoadedInfo.loadMapAdviseListByDiseaseId();
						if(getCmbDisease().isEmpty()){
							tableAdvise.setItems(LoadedInfo.getAdviseList());
						}else{
							tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
						}
						
						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Advise Delete Successfully...");
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise!","Please Select Any Advise for Delete...");
				tableAdvise.requestFocus();
			}
		});
		
		
		
		//ObservableList<TableItem> selectItem ;
		menuItemCreateGroup.setOnAction(e ->{
			if(tableAdvise.getSelectionModel().getSelectedItem() != null) {
				ObservableList<TableItemInfo> selectItem = tableAdvise.getSelectionModel().getSelectedItems();
				if(selectItem.size()>1) {
					if(dialogCreateNewGroup != null) {
						dialogCreateNewGroup.setList(selectItem);
						dialogCreateNewGroup.show();
					}else {
						dialogCreateNewGroup = new DialogCreateNewGroup();
						dialogCreateNewGroup.setList(selectItem);
						dialogCreateNewGroup.show();
					}
					
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise!","Please Select More than 1 Advise for Create New Group...");
					tableAdvise.requestFocus();
				}
					
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise!","Please Select More than 1 Advise for Create New Group...");
				tableAdvise.requestFocus();
			}
		});
		
		menuItemAddToGroup.setOnAction(e->{
			if(tableAdvise.getSelectionModel().getSelectedItem() != null) {
				if(!getCmbAdviseGroup().isEmpty()){
					if(LoadedInfo.getAdviseGroupInfo(getCmbAdviseGroup()) != null){
						String groupId = LoadedInfo.getAdviseGroupInfo(getCmbAdviseGroup()).getItemId();
						int size = LoadedInfo.getAdviseListByGroupId(groupId).size();
								
						sql = "INSERT INTO tbadvisegroup (slNo,id,groupname,adviseId,TYPE,headId,entryTime,userId) VALUES ('"+(size+1)+"','"+groupId+"','"+getCmbAdviseGroup()+"','"+tableAdvise.getSelectionModel().getSelectedItem().getItemId()+"','"+NodeType.ADVISE_GROUP.getType()+"','0',NOW(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
						
						new Notification(Pos.TOP_CENTER, "Information graphic", "Add Successfully...!","Advise Add Successfully.....");
						
						LoadedInfo.loadMapAdviseListByGroupId();
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Valid Advise Group!","Your Selected Group is Invalid....\nPlease Select Any Valid Advise Group...");
						cmbAdviseGroup.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise Group!","Please Select Any Advise Group...");
					cmbAdviseGroup.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise!","Please Select Any Advise...");
				tableAdvise.requestFocus();
			}
		});
		
		menuItemRemoveFromGroup.setOnAction(e->{
			if(tableAdvise.getSelectionModel().getSelectedItem() != null) {
				
					if(tableAdviseGroup.getSelectionModel().getSelectedItem() != null) {
						TableItemInfo temp = tableAdvise.getSelectionModel().getSelectedItem();
								
						sql = "delete From tbadvisegroup where id = '"+tableAdviseGroup.getSelectionModel().getSelectedItem().getItemId()+"' and adviseId = '"+temp.getItemId()+"' and slNo = '"+temp.getSlNo()+"'";
						databaseHandler.execAction(sql);
						
						new Notification(Pos.TOP_CENTER, "Information graphic", "Remove Successfully...!","Advise Remove Successfully.....");
						
						LoadedInfo.loadMapAdviseListByGroupId();
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select a Advise Group!","Please Select a  Advise Group...");
						tableAdviseGroup.requestFocus();
					}
				
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise!","Please Select Any Advise...");
				tableAdvise.requestFocus();
			}
		});
		
		menuItemUpdateSerial.setOnAction(e->{
			if(confirmationCheck("Update Serial No?")){
				if(getCmbAdviseGroup().isEmpty()){
					for(int i=0;i < tableAdvise.getItems().size();i++){
						sql = "update tbAdvise set slNo = '"+i+"' where slNo = '"+tableAdvise.getItems().get(i).getSlNo()+"' and id = '"+tableAdvise.getItems().get(i).getItemId()+"' ;";
						databaseHandler.execAction(sql);
						
					}
					LoadedInfo.loadAdviseInfo();
				}else{
					for(int i=0;i < tableAdvise.getItems().size();i++){
						sql = "update tbAdviseGroup set slNo = '"+i+"' where slNo = '"+tableAdvise.getItems().get(i).getSlNo()+"' and adviseId = '"+tableAdvise.getItems().get(i).getItemId()+"' ;";
						databaseHandler.execAction(sql);
						
					}
					LoadedInfo.loadMapAdviseListByGroupId();
				}
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Update Successfully...!","Serial No Update Successfully....");
				tableAdvise.refresh();
			}
			
		});
		
		menuItemDeleteGroupName.setOnAction(e ->{
			if(tableAdviseGroup.getSelectionModel().getSelectedItem() != null) {
				if(confirmationCheck("Delete This Group?")) {
					TableItemInfo tempInvestigation = tableAdviseGroup.getSelectionModel().getSelectedItem();
					if(databaseHandler.execAction("delete from tbAdviseGroup where groupname= '"+tempInvestigation.getItemName()+"'")){

						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Group Delete Successfully...");
						//adviseTableLoadByPId(getId(getCmbDisease(), 1));
						if(getCmbDisease().isEmpty()){
							tableAdvise.setItems(LoadedInfo.getAdviseList());
						}else{
							tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
						}
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Group!","Please Select Any Group for Delete...");
				tableAdviseGroup.requestFocus();
			}
		});
		
		menuItemEditGroupName.setOnAction(e ->{
			if(tableAdviseGroup.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempInvestigation = tableAdviseGroup.getSelectionModel().getSelectedItem();
				
					if(dialogEditGroupName != null) {
						dialogEditGroupName.setCurrentGroupName(tempInvestigation.getItemName());
						dialogEditGroupName.show();
					}else {
						dialogEditGroupName = new DialogEditGroupName();
						dialogEditGroupName.setCurrentGroupName(tempInvestigation.getItemName());
						dialogEditGroupName.show();
					}
					
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Advise Group!","Please Select A Advise Group...");
				tableAdviseGroup.requestFocus();
			}
		});
	}

	

	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control2 =  {cmbSystemAdvise,cmbDisease,cmbAdvise,btnSave};
		new FocusMoveByEnter(control2);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		tableSystem.setColumnResizePolicy(tableSystem.CONSTRAINED_RESIZE_POLICY);
		systemCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableDisease.setColumnResizePolicy(tableDisease.CONSTRAINED_RESIZE_POLICY);
		diseaseCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableAdvise.setColumnResizePolicy(tableAdvise.CONSTRAINED_RESIZE_POLICY);
		slCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		adviseCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableAdvise.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		adviseCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(adviseCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});
		
		tableAdviseGroup.setColumnResizePolicy(tableAdvise.CONSTRAINED_RESIZE_POLICY);
		adviseGroupCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		menuItemDelete = new MenuItem("Delete");
		menuItemCreateGroup = new MenuItem("Create New Group");
		menuItemAddToGroup = new MenuItem("Add To Group");
		menuItemRemoveFromGroup = new MenuItem("Remove From Group");
		menuItemUpdateSerial = new MenuItem("Update Serial No");
		contextMenuResult = new ContextMenu();
		contextMenuResult.getItems().addAll(menuItemDelete,menuItemCreateGroup,new SeparatorMenuItem(),menuItemAddToGroup,menuItemRemoveFromGroup,new SeparatorMenuItem(),menuItemUpdateSerial);
		tableAdvise.setContextMenu(contextMenuResult);
		
		menuItemDeleteGroupName = new MenuItem("Delete Group");
		menuItemEditGroupName = new MenuItem("Edit Group Name");
		
		contextMenuGroupName = new ContextMenu();
		contextMenuGroupName.getItems().addAll(menuItemEditGroupName,menuItemDeleteGroupName);
		tableAdviseGroup.setContextMenu(contextMenuGroupName);
		
		new TableItemRowFactory(tableAdvise);
		
	}

	private void addCmp() {
		// TODO Auto-generated method stub
		vBoxAdviseCmp.getChildren().remove(0,4);
		cmbSystemAdvise.setPrefWidth(700);
		cmbSystemAdvise.setPrefHeight(28);
		cmbSystemAdvise.setPromptText("System Name");

		cmbDisease.setPrefWidth(700);
		cmbDisease.setPrefHeight(28);
		cmbDisease.setPromptText("Disease Name");

		cmbAdvise.setPrefWidth(700);
		cmbAdvise.setPrefHeight(28);
		cmbAdvise.setPromptText("Advise");

		cmbAdviseGroup.setPrefWidth(700);
		cmbAdviseGroup.setPrefHeight(28);
		cmbAdviseGroup.setPromptText("Advise Group");

		vBoxAdviseCmp.getChildren().add(0,cmbSystemAdvise);
		vBoxAdviseCmp.getChildren().add(1,cmbDisease);
		vBoxAdviseCmp.getChildren().add(2,cmbAdviseGroup);
		vBoxAdviseCmp.getChildren().add(3,cmbAdvise);

		vBoxSystemDisease.getChildren().clear();
		cmbSystemDisease.setPrefWidth(700);
		cmbSystemDisease.setPrefHeight(28);
		cmbSystemDisease.setPromptText("System Name");

		vBoxSystemDisease.getChildren().add(cmbSystemDisease);

	}
	public class DialogCreateNewGroup extends Dialog{
		
		CreateNewGroupController newGroupController;
		
		public DialogCreateNewGroup() {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shareClasses/CreateNewGroup.fxml"));
				Parent root = fxmlLoader.load();
				newGroupController = fxmlLoader.getController();
				newGroupController.setNodeType(NodeType.ADVISE_GROUP);
				
				getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
				Node closeButton = this.getDialogPane().lookupButton(ButtonType.CANCEL);
				closeButton.managedProperty().bind(closeButton.visibleProperty());
				closeButton.setVisible(false);
				this.setTitle("Create New Group....");
				this.initModality(Modality.APPLICATION_MODAL);
				this.getDialogPane().setContent(root);
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}
			
		}
		
		private void setList(ObservableList<TableItemInfo> selectedItem) {
			newGroupController.setItemList(selectedItem);
		}
	}
	
public class DialogEditGroupName extends Dialog{
		
		EditGroupNameController editGroupNameController;
		
		
		public DialogEditGroupName() {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shareClasses/EditGroupName.fxml"));
				Parent root = fxmlLoader.load();
				editGroupNameController = fxmlLoader.getController();
				editGroupNameController.setNodeType(NodeType.ADVISE_GROUP);
				
				getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
				Node closeButton = this.getDialogPane().lookupButton(ButtonType.CANCEL);
				closeButton.managedProperty().bind(closeButton.visibleProperty());
				closeButton.setVisible(false);
				this.setTitle("Edit Group Name....");
				this.initModality(Modality.APPLICATION_MODAL);
				this.getDialogPane().setContent(root);
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}
			
		}
		
		private void setCurrentGroupName(String groupName) {
			editGroupNameController.setCurrentGroupName(groupName);
			editGroupNameController.setLbl(groupName);
		}
	}
	
	
	/*public class TableItem{

		private SimpleStringProperty id;
		private SimpleStringProperty itemName;
		private SimpleIntegerProperty sn;

		public TableItem(String id,String itemName) {
			this.id = new SimpleStringProperty(id);
			this.itemName = new SimpleStringProperty(itemName);
			this.sn = new SimpleIntegerProperty(0);
		}

		public TableItem(String id,String itemName,int sn) {
			this.id = new SimpleStringProperty(id);
			this.itemName = new SimpleStringProperty(itemName);
			this.sn = new SimpleIntegerProperty(sn);
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

		public int getSn() {
			return sn.get();
		}

		public void setCompanyName(int sn) {
			this.sn = new SimpleIntegerProperty(sn);
		}


	}*/

	public String getCmbSystemDisease() {
		if(cmbSystemDisease.getValue() != null)
			return cmbSystemDisease.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystemDisease(String cmbSystemDisease) {
		this.cmbSystemDisease.setValue(cmbSystemDisease);
	}

	public String getCmbSystemAdvise() {
		if(cmbSystemAdvise.getValue() != null)
			return cmbSystemAdvise.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystemAdvise(String cmbSystemInvestigation) {
		this.cmbSystemAdvise.setValue(cmbSystemInvestigation);
	}

	public String getCmbDisease() {
		if(cmbDisease.getValue() != null)
			return cmbDisease.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbDisease(String cmbDisease) {
		this.cmbDisease.setValue(cmbDisease);
	}

	public String getCmbAdvise() {
		if(cmbAdvise.getValue() != null)
			return cmbAdvise.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbAdvise(String cmbAdvise) {
		this.cmbAdvise.setValue(cmbAdvise);
	}
	
	
	public String getCmbAdviseGroup() {
		if(cmbAdviseGroup.getValue() != null)
			return cmbAdviseGroup.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbAdviseGroup(String cmbAdvise) {
		this.cmbAdviseGroup.setValue(cmbAdvise);
	}


}
