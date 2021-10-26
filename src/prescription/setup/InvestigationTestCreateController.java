package prescription.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
import prescription.setup.AdviceCreateController.DialogCreateNewGroup;
import prescription.setup.AdviceCreateController.DialogEditGroupName;
import prescription.setup.ClinicalExaminationCreateController.Result;
import prescription.setup.MedicineScheduleSetController.TableItem;
import shareClasses.AlertMaker;
import shareClasses.CommonMethod;
import shareClasses.CreateNewGroupController;
import shareClasses.EditGroupNameController;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import shareClasses.TableItemRowFactory;
import shareClasses.LoadedInfo.TableItemInfo;
import shareClasses.NodeType;

public class InvestigationTestCreateController implements Initializable{

	FxComboBox cmbSystemDisease = new FxComboBox<>();
	FxComboBox cmbSystemInvestigation = new FxComboBox<>();
	FxComboBox cmbDisease = new FxComboBox<>();
	FxComboBox cmbInvestigation = new FxComboBox<>();
	FxComboBox cmbInvestigationGroup = new FxComboBox<>();

	@FXML
	VBox vBoxInvestigationCmp;	
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

	ContextMenu contextMenuGroupName ;

	private DialogCreateNewGroup dialogCreateNewGroup ;
	private DialogEditGroupName dialogEditGroupName;

	@FXML
	private TableView<TableItemInfo> tableSystem;
	@FXML
	private TableColumn<TableItemInfo, String> systemCol;

	@FXML
	private TableView<TableItemInfo> tableDisease;
	@FXML
	private TableColumn<TableItemInfo, String> diseaseCol;

	@FXML
	private TableView<TableItemInfo> tableInvestigation;
	@FXML
	private TableColumn<TableItemInfo, String> investigationCol;
	@FXML
	private TableColumn<TableItemInfo, String> slCol;

	@FXML
	private TableView<TableItemInfo> tableInvestigationGroup;
	@FXML
	private TableColumn<TableItemInfo, String> investigationGroupCol;


	NodeType nodeType = NodeType.INVESTIGATION;
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
	}


	@FXML
	private void btnSaveAction(ActionEvent event) {

		try {
			if(!getCmbDisease().isEmpty()){
				if(!getCmbInvestigation().isEmpty()){
					if(LoadedInfo.isDiseaseExist(getCmbDisease())) {
						if(duplicateCheck(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId(),getCmbInvestigation())) {
							if(confirmationCheck("Save This Investigation?")) {
								int id = CommonMethod.getMaxInvestigationId();
								if(databaseHandler.execAction("insert into tbtestgroup (slNo,id,groupname,pId,type) values('"+id+"','"+id+"','"+getCmbInvestigation()+"','"+getId(getCmbDisease(), 1)+"',2)")) {

									LoadedInfo.loadInvestigationInfo();
									tableInvestigation.setItems(LoadedInfo.getInvestigationList());
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully...!","Investigation Save Successfully...");
								}

							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready exist!","This Investigation Name Allready exist in this Disease...");
							cmbInvestigation.requestFocus();
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Disease!","Please Select a valid Disease Name");
						cmbDisease.requestFocus();
					}
				}
				else{
					new Notification(Pos.TOP_CENTER, "Information graphic", "Insert Investigation Name..","Please Enter Invastigation Name");
					cmbInvestigation.requestFocus();
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
			if(tableInvestigation.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempTableItem = tableInvestigation.getSelectionModel().getSelectedItem();

				if(!getCmbDisease().isEmpty()){
					if(!getCmbInvestigation().isEmpty()){
						if(!getId(getCmbDisease(), 1).equals("0")) {
							if(duplicateCheck(getId(getCmbDisease(),1),getCmbInvestigation())) {
								if(confirmationCheck("Edit This Investigation?")) {
									if(databaseHandler.execAction("update tbTestGroup set groupname='"+getCmbInvestigation()+"',pId='"+getId(getCmbDisease(), 1)+"' where id="+tempTableItem.getItemId()+"")){
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully...!","Investigation Edit Successfully...");
										LoadedInfo.loadInvestigationInfo();
										tableInvestigation.setItems(LoadedInfo.getInvestigationList());
									}

								}
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready exist!","This Investigation Name Allready exist in this Disease...");
								cmbInvestigation.requestFocus();
							}

						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Disease!","Please Select a valid Disease Name");
							cmbDisease.requestFocus();
						}
					}
					else{
						new Notification(Pos.TOP_CENTER, "Information graphic", "Insert Investigation Name..","Please Enter Invastigation Name");
						cmbInvestigation.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Disease..","Please Select Disease Name");
					cmbDisease.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation!","Please Select Any Investigation...");
				tableInvestigation.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnDeleteAction(ActionEvent event) {
		try {
			if(tableInvestigation.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempTableItem = tableInvestigation.getSelectionModel().getSelectedItem();


				if(confirmationCheck("Delete This Investigation?")) {
					if(databaseHandler.execAction("delete from tbTestGroup where id="+tempTableItem.getItemId()+"")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfully...!","Investigation Delete Successfully...");
						LoadedInfo.loadInvestigationInfo();
						tableInvestigation.setItems(LoadedInfo.getInvestigationList());
					}

				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation!","Please Select Any Investigation...");
				tableInvestigation.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	@FXML
	private void btnAddToGroupAction(ActionEvent event){
		try{
			if(LoadedInfo.getInvestigationInfo(getCmbInvestigation()) != null) {
				if(!getCmbInvestigationGroup().isEmpty()){
					if(LoadedInfo.getInvestigationGroupInfo(getCmbInvestigationGroup()) != null){
						String groupId = LoadedInfo.getInvestigationGroupInfo(getCmbInvestigationGroup()).getItemId();
						String InvestigationId = LoadedInfo.getInvestigationInfo(getCmbInvestigation()).getItemId();
						int size = LoadedInfo.getInvestigationListByGroupId(groupId).size();
								
						sql = "INSERT INTO tbInvestigationgroup (slNo,id,groupname,InvestigationId,TYPE,headId,entryTime,userId) VALUES ('"+(size+1)+"','"+groupId+"','"+getCmbInvestigationGroup()+"','"+InvestigationId+"','"+NodeType.INVESTIGATION_GROUP.getType()+"','0',NOW(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
						
						new Notification(Pos.TOP_CENTER, "Information graphic", "Add Successfully...!","Investigation Add Successfully.....");
						
						LoadedInfo.loadMapInvestigationListByGroupId();
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Valid Investigation Group!","Your Selected Group is Invalid....\nPlease Select Any Valid Investigation Group...");
						cmbInvestigationGroup.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation Group!","Please Select Any Investigation Group...");
					cmbInvestigationGroup.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation!","Please Select Any Investigation...");
				cmbInvestigation.requestFocus();
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub

		setCmbSystemDisease("");
		setCmbDisease("");
		setCmbInvestigation("");
		comboBoxLoad(cmbSystemDisease, 0);
		comboBoxLoad(cmbSystemInvestigation, 0);
		comboBoxLoad(cmbDisease, 1);
		investigationComboLoad();
		groupLoad();

		tableSystem.setItems(LoadedInfo.getSystemList());
		tableDisease.setItems(LoadedInfo.getDiseaseList());
		tableInvestigation.setItems(LoadedInfo.getInvestigationList());
		tableInvestigationGroup.setItems(LoadedInfo.getInvestigationGroupList());

	}

	@FXML
	private void systemTableClickAction(MouseEvent event) {

		if(tableSystem.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableSystem.getSelectionModel().getSelectedItem();
			setCmbDisease("");
			setCmbSystemDisease(tempTableItem.getItemName());
			setCmbSystemInvestigation(tempTableItem.getItemName());
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
	private void investigationTableClickAction(MouseEvent event) {

		if(tableInvestigation.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableInvestigation.getSelectionModel().getSelectedItem();
			setCmbInvestigation(tempTableItem.getItemName());
		}
	}
	
	@FXML
	private void investigationGroupTableClickAction(MouseEvent event) {

		if(tableInvestigationGroup.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableInvestigationGroup.getSelectionModel().getSelectedItem();
			setCmbInvestigationGroup(tempTableItem.getItemName());
			tableInvestigation.setItems(LoadedInfo.getInvestigationListByGroupId(tempTableItem.getItemId()));
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
					list.add(new TableItem(rs.getString("id"),rs.getString("groupName"),rs.getInt("sn")));
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

	/*private void investigationTableLoad(){
		try {
			listInvestigation.clear();
			String sql="select * from tbtestgroup where type=2 group by groupname order by groupname";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()){
				listInvestigation.add(new TableItem(rs.getString("slno"), rs.getString("groupname")));
			}
			tableInvestigation.setItems(listInvestigation);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/

	private void investigationComboLoad(){
		try {
			cmbInvestigation.data.clear();
			cmbInvestigation.data.add("");
			String sql="select * from tbtestgroup where type=2 group by groupname order by groupname";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()){
				cmbInvestigation.data.add(rs.getString("groupname"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void groupLoad() {
		try {
			
			cmbInvestigationGroup.data.clear();
			
			
			sql = "SELECT slno,id,GROUPname,headid,type FROM tbInvestigationgroup GROUP BY groupname";
			
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				
				cmbInvestigationGroup.data.add(rs.getString("GroupName"));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/*private void investigationTableLoadBySystemName(String name) {
		try {
			listInvestigation.clear();
			if(name.equals("")) {
				sql = "select tg.id,tg.GroupName from tbmedicinegroup mg join tbtestgroup tg on mg.id=tg.pId where mg.type=1 group by groupname order by groupname";
			}else
				sql = "select tg.id,tg.GroupName from tbmedicinegroup mg join tbtestgroup tg on mg.id=tg.pId where mg.type=1 and mg.pid=(select id from tbmedicinegroup where groupname='"+name+"') group by groupname order by groupname";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listInvestigation.add(new TableItem(rs.getString("tg.id"), rs.getString("tg.groupname")));

			}
			tableInvestigation.setItems(listInvestigation);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}*/

	/*private void investigationTableLoadByPid(String pid, int type) {
		try {

			listInvestigation.clear();
			if(pid.equals("0"))
				sql ="select * from tbtestgroup where type="+type+" group by groupname order by groupname";
			else
				sql="select * from tbtestgroup where pid="+pid+" and type="+type+" group by groupname order by groupname";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listInvestigation.add(new TableItem(rs.getString("id"),rs.getString("GroupName")));
			}

			tableInvestigation.setItems(listInvestigation);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}*/

	/*private String getMaxId() {
		try {
			ResultSet rs=databaseHandler.execQuery("select ifnull(max(id),0)+1 as id from tbtestGroup");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();

		}
		return "0";
	}*/

	private String getId(String name, int type) {
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
	}

	protected boolean duplicateCheck(String pId, String name) {
		try {
			sql="select * from tbtestGroup where pid="+pId+" and groupname='"+name+"'";
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

	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message);
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbSystemDisease.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemDisease);

		});

		cmbSystemInvestigation.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemInvestigation);

		});

		cmbDisease.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDisease);

		});
		cmbInvestigation.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbInvestigation);

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
					setCmbSystemInvestigation(newValue);
					tableDisease.setItems(LoadedInfo.getDiseaseListBySystemId(LoadedInfo.getSystemInfo(getCmbSystemDisease()).getItemId()));
				}
			}    
		});

		cmbSystemInvestigation.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					tableInvestigation.setItems(LoadedInfo.getInvestigationListBySystemId(LoadedInfo.getSystemInfo(getCmbSystemInvestigation()).getItemId()));
				}
			}    
		});

		cmbDisease.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					tableInvestigation.setItems(LoadedInfo.getInvestigationListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
				}
			}    
		});

		menuItemDelete.setOnAction(e ->{
			if(tableInvestigation.getSelectionModel().getSelectedItem() != null) {
				if(confirmationCheck("Delete This Result?")) {
					TableItemInfo tempInvestigation = tableInvestigation.getSelectionModel().getSelectedItem();
					if(databaseHandler.execAction("delete from tbtestgroup where id= '"+tempInvestigation.getItemId()+"' and type= '2'")){

						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Investigation Delete Successfully...");
						LoadedInfo.loadInvestigationInfo();
						LoadedInfo.loadMapInvestigationListByDiseaseId();
						tableInvestigation.setItems(LoadedInfo.getInvestigationList());
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation!","Please Select Any Investigation for Delete...");
				tableInvestigation.requestFocus();
			}
		});

		menuItemCreateGroup.setOnAction(e ->{
			if(tableInvestigation.getSelectionModel().getSelectedItem() != null) {
				ObservableList<TableItemInfo> selectItem = tableInvestigation.getSelectionModel().getSelectedItems();
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
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation!","Please Select More than 1 Investigation for Create New Group...");
					tableInvestigation.requestFocus();
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation!","Please Select More than 1 Investigation for Create New Group...");
				tableInvestigation.requestFocus();
			}
		});
		
		menuItemAddToGroup.setOnAction(e->{
			if(tableInvestigation.getSelectionModel().getSelectedItem() != null) {
				if(!getCmbInvestigationGroup().isEmpty()){
					if(LoadedInfo.getInvestigationGroupInfo(getCmbInvestigationGroup()) != null){
						String groupId = LoadedInfo.getInvestigationGroupInfo(getCmbInvestigationGroup()).getItemId();
						int size = LoadedInfo.getInvestigationListByGroupId(groupId).size();
								
						sql = "INSERT INTO tbInvestigationgroup (slNo,id,groupname,InvestigationId,TYPE,headId,entryTime,userId) VALUES ('"+(size+1)+"','"+groupId+"','"+getCmbInvestigationGroup()+"','"+tableInvestigation.getSelectionModel().getSelectedItem().getItemId()+"','"+NodeType.INVESTIGATION_GROUP.getType()+"','0',NOW(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
						
						new Notification(Pos.TOP_CENTER, "Information graphic", "Add Successfully...!","Investigation Add Successfully.....");
						
						LoadedInfo.loadMapInvestigationListByGroupId();
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Valid Investigation Group!","Your Selected Group is Invalid....\nPlease Select Any Valid Investigation Group...");
						cmbInvestigationGroup.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation Group!","Please Select Any Investigation Group...");
					cmbInvestigationGroup.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation!","Please Select Any Investigation...");
				tableInvestigation.requestFocus();
			}
		});
		
		menuItemRemoveFromGroup.setOnAction(e->{
			if(tableInvestigation.getSelectionModel().getSelectedItem() != null) {
				
					if(tableInvestigationGroup.getSelectionModel().getSelectedItem() != null) {
						TableItemInfo temp = tableInvestigation.getSelectionModel().getSelectedItem();
								
						sql = "delete From tbInvestigationgroup where id = '"+tableInvestigationGroup.getSelectionModel().getSelectedItem().getItemId()+"' and InvestigationId = '"+temp.getItemId()+"' and slNo = '"+temp.getSlNo()+"'";
						databaseHandler.execAction(sql);
						
						new Notification(Pos.TOP_CENTER, "Information graphic", "Remove Successfully...!","Investigation Remove Successfully.....");
						
						LoadedInfo.loadMapInvestigationListByGroupId();
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select a Investigation Group!","Please Select a  Investigation Group...");
						tableInvestigationGroup.requestFocus();
					}
				
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation!","Please Select Any Investigation...");
				tableInvestigation.requestFocus();
			}
		});
		
		menuItemUpdateSerial.setOnAction(e->{
			if(confirmationCheck("Update Serial No?")){
				if(getCmbInvestigationGroup().isEmpty()){
					for(int i=0;i < tableInvestigation.getItems().size();i++){
						sql = "update tbTestGroup set slNo = '"+i+"' where slNo = '"+tableInvestigation.getItems().get(i).getSlNo()+"' and id = '"+tableInvestigation.getItems().get(i).getItemId()+"' ;";
						databaseHandler.execAction(sql);
						
					}
					LoadedInfo.loadInvestigationInfo();
				}else{
					for(int i=0;i < tableInvestigation.getItems().size();i++){
						sql = "update tbInvestigationGroup set slNo = '"+i+"' where slNo = '"+tableInvestigation.getItems().get(i).getSlNo()+"' and InvestigationId = '"+tableInvestigation.getItems().get(i).getItemId()+"' ;";
						databaseHandler.execAction(sql);
						
					}
					LoadedInfo.loadMapInvestigationListByGroupId();
				}
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Update Successfully...!","Serial No Update Successfully....");
				tableInvestigation.refresh();
			}
			
		});
		
		menuItemDeleteGroupName.setOnAction(e ->{
			if(tableInvestigationGroup.getSelectionModel().getSelectedItem() != null) {
				if(confirmationCheck("Delete This Group?")) {
					TableItemInfo tempInvestigation = tableInvestigationGroup.getSelectionModel().getSelectedItem();
					if(databaseHandler.execAction("delete from tbInvestigationGroup where groupname= '"+tempInvestigation.getItemName()+"'")){

						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Group Delete Successfully...");
						//InvestigationTableLoadByPId(getId(getCmbDisease(), 1));
						if(getCmbDisease().isEmpty()){
							tableInvestigation.setItems(LoadedInfo.getInvestigationList());
						}else{
							tableInvestigation.setItems(LoadedInfo.getInvestigationListByDiseaseId(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId()));
						}
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Group!","Please Select Any Group for Delete...");
				tableInvestigationGroup.requestFocus();
			}
		});
		
		menuItemEditGroupName.setOnAction(e ->{
			if(tableInvestigationGroup.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempInvestigation = tableInvestigationGroup.getSelectionModel().getSelectedItem();
				
					if(dialogEditGroupName != null) {
						dialogEditGroupName.setCurrentGroupName(tempInvestigation.getItemName());
						dialogEditGroupName.show();
					}else {
						dialogEditGroupName = new DialogEditGroupName();
						dialogEditGroupName.setCurrentGroupName(tempInvestigation.getItemName());
						dialogEditGroupName.show();
					}
					
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Investigation Group!","Please Select A Investigation Group...");
				tableInvestigationGroup.requestFocus();
			}
		});
	}

	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control2 =  {cmbSystemInvestigation,cmbDisease,cmbInvestigation,btnSave};
		new FocusMoveByEnter(control2);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		tableSystem.setColumnResizePolicy(tableSystem.CONSTRAINED_RESIZE_POLICY);
		systemCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		slCol.setCellValueFactory(new PropertyValueFactory("slNo"));

		tableDisease.setColumnResizePolicy(tableDisease.CONSTRAINED_RESIZE_POLICY);
		diseaseCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableInvestigation.setColumnResizePolicy(tableInvestigation.CONSTRAINED_RESIZE_POLICY);
		investigationCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableInvestigation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		investigationCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(investigationCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});


		tableInvestigationGroup.setColumnResizePolicy(tableInvestigationGroup.CONSTRAINED_RESIZE_POLICY);
		investigationGroupCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		menuItemDelete = new MenuItem("Delete");
		menuItemCreateGroup = new MenuItem("Create New Group");
		menuItemAddToGroup = new MenuItem("Add To Group");
		menuItemRemoveFromGroup = new MenuItem("Remove From Group");
		menuItemUpdateSerial = new MenuItem("Update Serial No");
		contextMenuResult = new ContextMenu();
		contextMenuResult.getItems().addAll(menuItemDelete,menuItemCreateGroup,new SeparatorMenuItem(),menuItemAddToGroup,menuItemRemoveFromGroup,new SeparatorMenuItem(),menuItemUpdateSerial);
		tableInvestigation.setContextMenu(contextMenuResult);

		menuItemDeleteGroupName = new MenuItem("Delete Group");
		menuItemEditGroupName = new MenuItem("Edit Group Name");

		contextMenuGroupName = new ContextMenu();
		contextMenuGroupName.getItems().addAll(menuItemEditGroupName,menuItemDeleteGroupName);
		tableInvestigationGroup.setContextMenu(contextMenuGroupName);
		
		new TableItemRowFactory(tableInvestigation);
	}

	private void addCmp() {
		// TODO Auto-generated method stub
		vBoxInvestigationCmp.getChildren().remove(0,4);
		cmbSystemInvestigation.setPrefWidth(700);
		cmbSystemInvestigation.setPrefHeight(25);
		cmbSystemInvestigation.setPromptText("System Name");

		cmbDisease.setPrefWidth(700);
		cmbDisease.setPrefHeight(25);
		cmbDisease.setPromptText("Disease Name");

		cmbInvestigation.setPrefWidth(700);
		cmbInvestigation.setPrefHeight(25);
		cmbInvestigation.setPromptText("Investigation Name");

		cmbInvestigationGroup.setPrefWidth(700);
		cmbInvestigationGroup.setPrefHeight(25);
		cmbInvestigationGroup.setPromptText("Investigation Group");

		vBoxInvestigationCmp.getChildren().add(0,cmbSystemInvestigation);
		vBoxInvestigationCmp.getChildren().add(1,cmbDisease);
		vBoxInvestigationCmp.getChildren().add(2,cmbInvestigationGroup);
		vBoxInvestigationCmp.getChildren().add(3,cmbInvestigation);

		vBoxSystemDisease.getChildren().clear();
		cmbSystemDisease.setPrefWidth(700);
		cmbSystemDisease.setPrefHeight(25);
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
				newGroupController.setNodeType(NodeType.INVESTIGATION_GROUP);

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
				editGroupNameController.setNodeType(NodeType.INVESTIGATION_GROUP);

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

	public String getCmbSystemInvestigation() {
		if(cmbSystemInvestigation.getValue() != null)
			return cmbSystemInvestigation.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystemInvestigation(String cmbSystemInvestigation) {
		this.cmbSystemInvestigation.setValue(cmbSystemInvestigation);
	}

	public String getCmbDisease() {
		if(cmbDisease.getValue() != null)
			return cmbDisease.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbDisease(String cmbDisease) {
		this.cmbDisease.setValue(cmbDisease);
	}

	public String getCmbInvestigation() {
		if(cmbInvestigation.getValue() != null)
			return cmbInvestigation.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbInvestigation(String cmbInvestigation) {
		this.cmbInvestigation.setValue(cmbInvestigation);
	}

	public String getCmbInvestigationGroup() {
		if(cmbInvestigationGroup.getValue() != null)
			return cmbInvestigationGroup.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbInvestigationGroup(String cmbInvestigationGroup) {
		this.cmbInvestigationGroup.setValue(cmbInvestigationGroup);
	}


}
