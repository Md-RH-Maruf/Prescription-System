package prescription.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.w3c.dom.ls.LSInput;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import shareClasses.AlertMaker;
import shareClasses.CommonMethod;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.NodeType;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import shareClasses.TableItemRowFactory;
import shareClasses.LoadedInfo.TableItemInfo;

public class CheapComplainCreateController implements Initializable{

	FxComboBox cmbSystem = new FxComboBox<>();
	FxComboBox cmbCheapComplain = new FxComboBox<>();
	FxComboBox cmbCheapComplainFilter = new FxComboBox<>();

	@FXML
	HBox hBoxSystem;
	@FXML
	HBox hBoxCheapComplain;
	@FXML
	HBox hBoxCheapComplainFilter;

	@FXML
	TextField txtCC;
	@FXML
	TextField txtSuggestedCause;
	@FXML
	TextField txtFilterBy;
	@FXML
	TextArea txtFilterArea;


	@FXML
	Button btnSaveCC;
	@FXML
	Button btnEditCC;
	@FXML
	Button btnDeleteCC;
	@FXML
	Button btnRefreshCC;
	@FXML
	Button btnSaveCause;
	@FXML
	Button btnEditCause;
	@FXML
	Button btnDeleteCause;
	@FXML
	Button btnRefreshCause;
	@FXML
	Button btnFilterText;
	@FXML
	Button btnSaveAsCause;

	DatabaseHandler databaseHandler;
	String sql;

	MenuItem menuItemCCDelete ;
	MenuItem menuItemCCUpdateSerial ;
	ContextMenu contextMenuCC ;

	MenuItem menuItemSugCauseDelete ;
	MenuItem menuItemSugCauseUpdateSerial ;
	ContextMenu contextMenuSugCause ;

	@FXML
	private TableView<TableItemInfo> tableCheapComplain;
	//ObservableList<TableItemInfo> listCheapComplain = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> ccSlCol;
	@FXML
	private TableColumn<TableItemInfo, String> cheapComplainCol;


	@FXML
	private TableView<TableItemInfo> tableSuggestedCause;
	//ObservableList<TableItemInfo> listSuggestedCause = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> suggestSlCol;
	@FXML
	private TableColumn<TableItemInfo, String> suggestedCauseCol;


	StringTokenizer strToken;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		focusMoveAction();
		setCmpData();
		setCmpAction();
		setCmpFocusAction();
		btnCheapComplainRefreshAction(null);
		btnSuggestedCauseRefreshAction(null);
	}

	@FXML
	private void btnCheapComplainAddAction(ActionEvent event) {
		if(!getTxtCC().isEmpty()) {
			if(LoadedInfo.getSystemInfo(getCmbSystem()) != null) {
				if(!LoadedInfo.isCheapComplainExist(LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()+getTxtCC())) {
					if(confirmationCheck("Save This Cheap-Complain?")) {
						int maxid = CommonMethod.getMaxCCId();
						if(databaseHandler.execAction("insert into tbcc (slno,id,NAME,headId,type,entryTime,entryBy) values ("+maxid+","+maxid+",'"+getTxtCC()+"',"+LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()+",'1',now(),'"+SessionBeam.getUserId()+"');")) {
							LoadedInfo.loadCheapComplainInfo();
							LoadedInfo.loadMapCheapComplainListBySystemId();
							tableCheapComplain.setItems(LoadedInfo.getCheapComplainListBySystemId(LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()));
							new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Cheap Complain Save Successfully...");
						}
					}								
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","Cheap Complain Name Allready Exist in This System Name..\nPlease Change your Cheap Complain or System Name");
					txtCC.requestFocus();
				}			
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid System Name!","Your System Name is Invalid..");
				cmbSystem.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter C/C!","Enter Cheap Complain Name..");
			txtCC.requestFocus();
		}


	}

	@FXML
	private void btnCheapComplainEditAction(ActionEvent event) {
		if(!getTxtCC().isEmpty()) {
			if(LoadedInfo.getSystemInfo(getCmbSystem()) != null) {
				if(tableCheapComplain.getSelectionModel().getSelectedItem() != null) {
					if(!LoadedInfo.isCheapComplainExist(LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()+getTxtCC())) {
						if(confirmationCheck("Edit This Cheap-Complain?")) {
							if(databaseHandler.execAction("update tbcc set Name='"+getTxtCC()+"',entryTime=now(),entryBy='"+SessionBeam.getUserId()+"',headid="+LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()+" where type = '1' and id="+tableCheapComplain.getSelectionModel().getSelectedItem().getItemId()+";")) {
								LoadedInfo.loadCheapComplainInfo();
								LoadedInfo.loadMapCheapComplainListBySystemId();
								tableCheapComplain.setItems(LoadedInfo.getCheapComplainListBySystemId(LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()));
								new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Cheap Complain Edit Successfully...");
							}
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","Cheap Complain Name Allready Exist in This System Name..\nPlease Change your Cheap Complain or System Name");
						txtCC.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Cheap Complain!","Please Select Any Cheap Complain");
					tableCheapComplain.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid System Name!","Your System Name is Invalid..");
				cmbSystem.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter C/C!","Enter Cheap Complain Name..");
			txtCC.requestFocus();
		}
	}

	@FXML
	private void btnCheapComplainDeleteAction(ActionEvent event) {

		if(tableCheapComplain.getSelectionModel().getSelectedItem() != null) {

			if(confirmationCheck("Delete This Cheap-Complain?")) {
				if(databaseHandler.execAction("delete from tbcc where type = '1' and id="+tableCheapComplain.getSelectionModel().getSelectedItem().getItemId()+";")) {
					tableCheapComplain.setItems(LoadedInfo.getCheapComplainListBySystemId(LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()));
					new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfully!","Cheap Complain Delete Successfully...");
				}
			}

		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Cheap Complain!","Please Select Any Cheap Complain");
			tableCheapComplain.requestFocus();
		}

	}


	@FXML
	private void cheapComplainTableClickAtion(MouseEvent event) {
		if(tableCheapComplain.getSelectionModel().getSelectedItem() != null) {
			setCmbCheapComplain(tableCheapComplain.getSelectionModel().getSelectedItem().getItemName());
			setCmbCheapComplainFilter(tableCheapComplain.getSelectionModel().getSelectedItem().getItemName());
			setTxtCC(tableCheapComplain.getSelectionModel().getSelectedItem().getItemName());

		}
	}

	@FXML
	private void btnCheapComplainRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		setCmbSystem("");
		setTxtCC("");
		systemComboLoad();
		tableCheapComplain.setItems(LoadedInfo.getCheapComplainList());
	}


	@FXML
	private void btnSuggestedCauseSaveAction(ActionEvent event) {
		if(!getTxtSuggestedCause().isEmpty()) {
			if(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()) != null) {
				if(LoadedInfo.isCheapComplainCauseExist(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()+getTxtSuggestedCause())) {
					if(confirmationCheck("Save This Suggested Cause?")) {
						int maxid = CommonMethod.getMaxCCId();
						if(databaseHandler.execAction("insert into tbcc (slno,id,NAME,headId,type,entryTime,entryBy) values ('"+maxid+"','"+maxid+"','"+getTxtSuggestedCause()+"',"+LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()+",'2',now(),'"+SessionBeam.getUserId()+"');")) {
							LoadedInfo.loadCheapComplainCauseInfo();
							LoadedInfo.loadMapCheapComplainCauseListByHeadId();
							tableSuggestedCause.setItems(LoadedInfo.getCheapComplainCauseListByHeadId(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()));
							new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Suggested Cause Save Successfully...");
						}
					}								
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","Suggested Cause Name Allready Exist in This Cheap Complain..\nPlease Change your Suggested Cause or Cheap Complain...");
					txtSuggestedCause.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Cheap Complain!","Your Cheap Complain Name is Invalid..");
				cmbCheapComplain.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Suggested Cause!","Enter Suggested Cause Name..");
			txtSuggestedCause.requestFocus();
		}


	}




	@FXML
	private void btnSuggestedCauseEditAction(ActionEvent event) {
		if(!getTxtSuggestedCause().isEmpty()) {
			if(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()) != null) {
				if(tableSuggestedCause.getSelectionModel().getSelectedItem() != null) {
					if(!LoadedInfo.isCheapComplainCauseExist(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()+getTxtSuggestedCause())) {
						if(confirmationCheck("Edit This Suggested Cause?")) {
							if(databaseHandler.execAction("update tbcc set Name='"+getTxtSuggestedCause()+"',entryTime=now(),entryBy='"+SessionBeam.getUserId()+"',headid="+LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()+" where type = '2' and id="+tableSuggestedCause.getSelectionModel().getSelectedItem().getItemId()+";")) {
								LoadedInfo.loadCheapComplainCauseInfo();
								LoadedInfo.loadMapCheapComplainCauseListByHeadId();
								tableSuggestedCause.setItems(LoadedInfo.getCheapComplainCauseListByHeadId(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()));
								new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Suggested Cause Edit Successfully...");
							}
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","Suggested Cause Name Allready Exist in This Cheap Complain..\nPlease Change your Suggested Cause or Cheap Complain...");
						txtSuggestedCause.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Suggested Cause!","Please Select Any Suggested Cause");
					tableSuggestedCause.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Cheap Complain!","Your Cheap Complain Name is Invalid..");
				cmbCheapComplain.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Suggested Cause!","Enter Suggested Cause Name..");
			txtSuggestedCause.requestFocus();
		}
	}

	@FXML
	private void btnSuggestedCauseDeleteAction(ActionEvent event) {
		if(tableSuggestedCause.getSelectionModel().getSelectedItem() != null) {	
			if(confirmationCheck("Delete This Suggested Cause?")) {
				if(databaseHandler.execAction("delete from tbcc where type = '2' and id="+tableSuggestedCause.getSelectionModel().getSelectedItem().getItemId()+";")) {
					LoadedInfo.loadCheapComplainCauseInfo();
					LoadedInfo.loadMapCheapComplainCauseListByHeadId();
					tableSuggestedCause.setItems(LoadedInfo.getCheapComplainCauseListByHeadId(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()));
					new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfully!","Suggested Cause Delete Successfully...");
				}
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Suggested Cause!","Please Select Any Suggested Cause");
			tableSuggestedCause.requestFocus();
		}
	}


	@FXML
	private void suggestedCauseTableClickAtion(MouseEvent event) {
		if(tableSuggestedCause.getSelectionModel().getSelectedItem() != null) {
			setTxtSuggestedCause(tableSuggestedCause.getSelectionModel().getSelectedItem().getItemName());
		}
	}
	@FXML
	private void btnSuggestedCauseRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		setCmbCheapComplain("");
		setTxtSuggestedCause("");
		cheapComplainComboLoad();
		tableSuggestedCause.setItems(LoadedInfo.getCheapComplainCauseList());
	}

	@FXML
	private void btnFilterTextAction(ActionEvent event){
		try{
			if(!getTxtFilterBy().isEmpty()){
				if(!getTxtFilterArea().isEmpty()){
					strToken = new StringTokenizer(getTxtFilterArea(),getTxtFilterBy());
					String newString="";
					while(strToken.hasMoreTokens()){
						newString = newString+"\n#"+strToken.nextToken().trim();
					}

					setTxtFilterArea(newString);
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Filtering Text!","Please Enter Filtering Text..");
					txtFilterArea.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Filter By Charecter!","Please Enter Filter By Charecter..");
				txtFilterBy.requestFocus();
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnSaveAsSuggestedCauseAction(ActionEvent event) {
		if(!getTxtFilterArea().isEmpty()) {
			if(!getCmbCheapComplainFilter().isEmpty()) {

				if(confirmationCheck("Save This Suggested Cause as new?")) {
					String headId  = LoadedInfo.getCheapComplainInfo(getCmbCheapComplainFilter()).getItemId();
					strToken = new StringTokenizer(getTxtFilterArea(),"#");
					int maxId = CommonMethod.getMaxCCId();
					while(strToken.hasMoreTokens()){
						databaseHandler.execAction("insert into tbcc (slNo,id,NAME,headId,type,entryTime,entryBy) values ('"+maxId+"','"+maxId+"','"+strToken.nextToken().trim()+"',"+headId+",'"+NodeType.CHEAP_COMPLAIN_CAUSE.getType()+"',now(),'"+SessionBeam.getUserId()+"');");
						maxId++;
					}
					LoadedInfo.loadCheapComplainCauseInfo();
					LoadedInfo.loadMapCheapComplainCauseListByHeadId();
					tableSuggestedCause.setItems(LoadedInfo.getCheapComplainCauseListByHeadId(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()));
					new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Suggested Cause Save Successfully...");

				}										
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Cheap Complain Name!","Please Select Cheap Complain..");
				cmbCheapComplainFilter.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Filtered Text!","Please Enter Filter Text..");
			txtFilterArea.requestFocus();
		}


	}

	private void systemComboLoad() {

		try {
			cmbSystem.data.clear();
			cmbSystem.data.add("");
			String sql="select * from tbmedicinegroup where type=0 group by GroupName order by sn,GroupName  ";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbSystem.data.add(rs.getString("GroupName"));
			}


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}


	private void cheapComplainComboLoad() {

		try {
			String sql;
			cmbCheapComplain.data.clear();
			cmbCheapComplain.data.add("");

			cmbCheapComplainFilter.data.clear();
			cmbCheapComplainFilter.data.add("");

			sql="select * from tbcc where type = '1' group by name order by name ";
			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbCheapComplain.data.add(rs.getString("Name"));
				cmbCheapComplainFilter.data.add(rs.getString("Name"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	/*private void tableLoad(String pid) {

		try {
			String sql;
			listCheapComplain.clear();
			if(pid.equals("0")) {
				sql="select * from tbcc where type = '1' group by name order by name ";
			}else {
				sql="select * from tbcc where type = '1' and headid="+pid+" group by name order by name ";
			}

			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				listCheapComplain.add(new TableItemInfo(rs.getInt("slNo"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));

			}
			tableCheapComplain.setItems(listCheapComplain);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/


	/*private void suggestedTableLoad(String pid) {

		try {
			String sql;
			listSuggestedCause.clear();
			if(pid.equals("0")) {
				sql="select * from tbcc where type = '2' group by name order by name ";
			}else {
				sql="select * from tbcc where type = '2' and headid="+pid+" group by name order by name ";
			}

			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				listSuggestedCause.add(new TableItemInfo(rs.getInt("slNo"), rs.getString("id"), rs.getString("headId"), rs.getString("name"), rs.getInt("type")));
			}
			tableSuggestedCause.setItems(listSuggestedCause);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/



	/*private String getMedicineId(String name) {
		try {
			ResultSet rs = databaseHandler.execQuery("select * from tbmedicinegroup where groupname='"+name+"' and type =0");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}*/

	/*private String getCCId(String name) {
		try {
			ResultSet rs = databaseHandler.execQuery("select * from tbCC where name='"+name+"' and type ='1'");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}*/

	/*private int getMaxId() {
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

	}*/
	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message);
	}
	private boolean duplicateCheck(String name, String id,String headId,String type) {
		try {
			String sql="select * from tbcc where type = '"+type+"' and name='"+name+"' and id != "+id+" and headid="+headId+";";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	private boolean duplicateCheck(String name,String headID,String type) {
		try {
			String sql="select * from tbcc where type = '"+type+"' and name='"+name+"' and headID="+headID+";";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbSystem.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystem);

		});
		cmbCheapComplain.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCheapComplain);

		});

		cmbCheapComplainFilter.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCheapComplainFilter);

		});
		txtCC.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtCC);
		});

		txtSuggestedCause.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtSuggestedCause);
		});

		txtFilterArea.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtFilterArea);
		});

		txtFilterBy.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtFilterBy);
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

	private void selectTextIfFocused(TextArea text){
		Platform.runLater(() -> {
			if (text.isFocused()  && !text.getText().isEmpty()) {
				text.selectAll();
			}
		});
	}
	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control =  {cmbSystem,txtCC,btnSaveCC};
		new FocusMoveByEnter(control);

		Control[] control1 =  {cmbCheapComplain,txtSuggestedCause,btnSaveCause};
		new FocusMoveByEnter(control1);
	}

	private void setCmpAction() {
		// TODO Auto-generated method stub
		cmbSystem.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					tableCheapComplain.setItems(LoadedInfo.getCheapComplainListBySystemId(LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()));
				}
			}    
		});

		cmbCheapComplain.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					tableSuggestedCause.setItems(LoadedInfo.getCheapComplainCauseListByHeadId(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()));

				}
			}    
		});

		menuItemCCDelete.setOnAction(e ->{
			if(tableCheapComplain.getSelectionModel().getSelectedItem() != null) {
				if(confirmationCheck("Delete This Cheap Complain?")) {
					TableItemInfo tempInvestigation = tableCheapComplain.getSelectionModel().getSelectedItem();
					if(databaseHandler.execAction("delete from tbcc where id= '"+tempInvestigation.getItemId()+"'")){

						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Cheap Complain Delete Successfully...");
						LoadedInfo.loadCheapComplainInfo();
						tableCheapComplain.setItems(LoadedInfo.getCheapComplainListBySystemId(LoadedInfo.getSystemInfo(getCmbSystem()).getItemId()));
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Cheap-Complain!","Please Select Any Cheap Complain for Delete...");
				tableCheapComplain.requestFocus();
			}
		});

		menuItemCCUpdateSerial.setOnAction(e->{
			if(confirmationCheck("Update Serial No?")){
				for(int i=0;i < tableCheapComplain.getItems().size();i++){
					sql = "update tbcc set slno = '"+i+"' where slno = '"+tableCheapComplain.getItems().get(i).getSlNo()+"' and id = '"+tableCheapComplain.getItems().get(i).getItemId()+"' ;";
					databaseHandler.execAction(sql);

				}
				LoadedInfo.loadCheapComplainInfo();
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Update Successfully...!","Serial No Update Successfully....");
				tableCheapComplain.setItems(LoadedInfo.getCheapComplainList());
			}

		});

		menuItemSugCauseDelete.setOnAction(e ->{
			if(tableSuggestedCause.getSelectionModel().getSelectedItem() != null) {
				if(confirmationCheck("Delete This Suggested Cause?")) {
					TableItemInfo tempInvestigation = tableSuggestedCause.getSelectionModel().getSelectedItem();
					if(databaseHandler.execAction("delete from tbcc where id= '"+tempInvestigation.getItemId()+"'")){

						LoadedInfo.loadCheapComplainCauseInfo();
						LoadedInfo.loadMapCheapComplainCauseListByHeadId();
						tableSuggestedCause.setItems(LoadedInfo.getCheapComplainCauseListByHeadId(LoadedInfo.getCheapComplainInfo(getCmbCheapComplain()).getItemId()));
						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Suggested Cause Delete Successfully...");				
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Suggested Cause!","Please Select Any Suggested Cause for Delete...");
				tableSuggestedCause.requestFocus();
			}
		});

		menuItemSugCauseUpdateSerial.setOnAction(e->{
			if(confirmationCheck("Update Serial No?")){
				for(int i=0;i < tableSuggestedCause.getItems().size();i++){
					sql = "update tbcc set slno = '"+i+"' where slno = '"+tableSuggestedCause.getItems().get(i).getSlNo()+"' and id = '"+tableSuggestedCause.getItems().get(i).getItemId()+"' ;";
					databaseHandler.execAction(sql);
				}
				LoadedInfo.loadCheapComplainCauseInfo();
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Update Successfully...!","Serial No Update Successfully....");
				tableSuggestedCause.setItems(LoadedInfo.getCheapComplainCauseList());
			}

		});
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		tableCheapComplain.setColumnResizePolicy(tableCheapComplain.CONSTRAINED_RESIZE_POLICY);
		ccSlCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		cheapComplainCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableSuggestedCause.setColumnResizePolicy(tableSuggestedCause.CONSTRAINED_RESIZE_POLICY);
		suggestSlCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		suggestedCauseCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		menuItemCCDelete = new MenuItem("Delete");
		menuItemCCUpdateSerial = new MenuItem("Update Serial");
		contextMenuCC = new ContextMenu();
		contextMenuCC.getItems().addAll(menuItemCCDelete,new SeparatorMenuItem(),menuItemCCUpdateSerial);
		tableCheapComplain.setContextMenu(contextMenuCC);

		menuItemSugCauseDelete = new MenuItem("Delete");
		menuItemSugCauseUpdateSerial = new MenuItem("Update Serial");
		contextMenuSugCause = new ContextMenu();
		contextMenuSugCause.getItems().addAll(menuItemSugCauseDelete,new SeparatorMenuItem(),menuItemSugCauseUpdateSerial);
		tableSuggestedCause.setContextMenu(contextMenuSugCause);

		new TableItemRowFactory(tableCheapComplain);
		new TableItemRowFactory(tableSuggestedCause);
	}

	private void addCmp() {
		// TODO Auto-generated method stub
		hBoxSystem.getChildren().remove(1);

		cmbSystem.setPrefWidth(330);
		cmbSystem.setPrefHeight(25);
		cmbSystem.setPromptText("System Name");
		hBoxSystem.getChildren().add(1,cmbSystem);


		hBoxCheapComplain.getChildren().remove(1);

		cmbCheapComplain.setPrefWidth(340);
		cmbCheapComplain.setPrefHeight(25);
		cmbCheapComplain.setPromptText("Cheap Complain");
		hBoxCheapComplain.getChildren().add(1,cmbCheapComplain);

		hBoxCheapComplainFilter.getChildren().remove(1);

		cmbCheapComplainFilter.setPrefWidth(340);
		cmbCheapComplainFilter.setPrefHeight(25);
		cmbCheapComplainFilter.setPromptText("Cheap Complain");
		hBoxCheapComplainFilter.getChildren().add(1,cmbCheapComplainFilter);

	}


	/*public class TableItem{

		private SimpleStringProperty id;
		private SimpleStringProperty itemName;

		public TableItem(String id,String itemName) {
			this.id = new SimpleStringProperty(id);
			this.itemName = new SimpleStringProperty(itemName);
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


	}*/


	public String getCmbSystem() {
		if(cmbSystem.getValue() != null)
			return cmbSystem.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystem(String cmbSystem) {
		this.cmbSystem.setValue(cmbSystem);
	}

	public String getCmbCheapComplain() {
		if(cmbCheapComplain.getValue() != null)
			return cmbCheapComplain.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbCheapComplain(String cmbCheapComplain) {
		this.cmbCheapComplain.setValue(cmbCheapComplain);
	}

	public String getCmbCheapComplainFilter() {
		if(cmbCheapComplainFilter.getValue() != null)
			return cmbCheapComplainFilter.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbCheapComplainFilter(String cmbCheapComplainFilter) {
		this.cmbCheapComplainFilter.setValue(cmbCheapComplainFilter);
	}

	public String getTxtCC() {
		return txtCC.getText().replace("'", "''").trim();
	}

	public void setTxtCC(String txtCC) {
		this.txtCC.setText(txtCC);
	}

	public String getTxtSuggestedCause() {
		return txtSuggestedCause.getText().replace("'", "''").trim();
	}

	public void setTxtSuggestedCause(String txtSuggestedCause) {
		this.txtSuggestedCause.setText(txtSuggestedCause);
	}

	public String getTxtFilterArea() {
		return txtFilterArea.getText().replace("'", "''").trim();
	}

	public void setTxtFilterArea(String txtFilterArea) {
		this.txtFilterArea.setText(txtFilterArea);
	}

	public String getTxtFilterBy() {
		return txtFilterBy.getText().trim();
	}

	public void setTxtFilterBy(String txtFilterBy) {
		this.txtFilterBy.setText(txtFilterBy);
	}



}
