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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.Notification;
import shareClasses.SessionBeam;

public class SystemicSurveyQuestionCreateController implements Initializable{

	FxComboBox cmbSystemName = new FxComboBox<>();

	@FXML
	TextField txtSurveyQuestion;


	@FXML
	HBox hBoxSystem;	

	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnRefresh;

	DatabaseHandler databaseHandler;
	String sql;
	
	MenuItem menuItemDelete ;
	
	ContextMenu contextMenuResult ;

	@FXML
	private TableView<TableItem> tableSurveyQuestion;
	ObservableList<TableItem> listSurveyQuestion = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItem, String> surveyQuestionCol;




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
		if(!getTxtSurveyQuestion().isEmpty()) {
			if(!getId(getCmbSystemName()).equals("0")) {
				if(duplicateCheck(getTxtSurveyQuestion(),getId(getCmbSystemName()))) {
					if(confirmationCheck("Save This Survey Question?")) {
						String id = getMaxId();
						sql = "insert into tbsurveyquestion values ("+id+","+id+",'"+getTxtSurveyQuestion()+"',"+getId(getCmbSystemName())+",now(),'"+SessionBeam.getUserId()+"');";
						if(databaseHandler.execAction(sql)) {
							tableLoad(getId(getCmbSystemName()));
							new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Survey Question Save Successfully...");
						}
					}
								
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","Survey Question Allready Exist in This System Name..\nPlease Change your Survey Qustion or System Name");
					txtSurveyQuestion.requestFocus();
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid System Name!","Your System Name is Invalid..");
				cmbSystemName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Survey Question!","Enter Survey Qustion..");
			txtSurveyQuestion.requestFocus();
		}


	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		if(!getTxtSurveyQuestion().isEmpty()) {
			if(!getId(getCmbSystemName()).equals("0")) {
				if(tableSurveyQuestion.getSelectionModel().getSelectedItem() != null) {
					if(duplicateCheck(getTxtSurveyQuestion(),tableSurveyQuestion.getSelectionModel().getSelectedItem().getId(),getId(getCmbSystemName()))) {
						if(confirmationCheck("Edit This Survey Question?")) {

							sql = "update tbsurveyquestion set question='"+getTxtSurveyQuestion()+"',entryTime=now(),entryBy='"+SessionBeam.getUserId()+"',headid="+getId(getCmbSystemName())+" where id="+tableSurveyQuestion.getSelectionModel().getSelectedItem().getId()+";";
							if(databaseHandler.execAction(sql)) {
								tableLoad(getId(getCmbSystemName()));
								new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Survey Question Edit Successfully...");
							}
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","Survey Question Allready Exist in This System Name..\nPlease Change your Survey Qustion or System Name");
						txtSurveyQuestion.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Survey Question!","Please Select Any Survey Question");
					tableSurveyQuestion.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid System Name!","Your System Name is Invalid..");
				cmbSystemName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Survey Question!","Enter Survey Qustion..");
			txtSurveyQuestion.requestFocus();
		}
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		setCmbSystemName("");
		setTxtSurveyQuestion("");
		systemComboLoad();
		tableLoad("0");
	}
	@FXML
	private void surveyQuestionTableClickAtion(MouseEvent event) {
		if(tableSurveyQuestion.getSelectionModel().getSelectedItem() != null) {
			setTxtSurveyQuestion(tableSurveyQuestion.getSelectionModel().getSelectedItem().getItemName());
		}
	}
	private void systemComboLoad() {

		try {
			cmbSystemName.data.clear();
			cmbSystemName.data.add("");
			String sql="select * from tbmedicinegroup where type=0 group by GroupName order by sn,GroupName  ";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbSystemName.data.add(rs.getString("GroupName"));
			}


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}



	private void tableLoad(String pid) {

		try {
			String sql;
			listSurveyQuestion.clear();
			if(pid.equals("0")) {
				sql="select * from tbsurveyquestion group by question order by question ";
			}else {
				sql="select * from tbsurveyquestion where headid="+pid+" group by question order by question ";
			}

			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				listSurveyQuestion.add(new TableItem(rs.getString("id"), rs.getString("question")));

			}
			tableSurveyQuestion.setItems(listSurveyQuestion);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private String getId(String name) {
		try {
			ResultSet rs = databaseHandler.execQuery("select * from tbmedicinegroup where groupname='"+name+"' and type =0");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	private String getMaxId() {
		try {
			String sql="select (select ifnull(max(id),0)+1)as maxid from tbsurveyquestion ;";

			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("maxid");
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return "0";

	}

	private boolean duplicateCheck(String question, String id,String headId) {
		try {
			String sql="select * from tbsurveyquestion where question='"+question+"' and id != "+id+" and headid="+headId+";";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	private boolean duplicateCheck(String question,String headID) {
		try {
			String sql="select * from tbsurveyquestion where question='"+question+"' and headID="+headID+";";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
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
		cmbSystemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemName);

		});
		txtSurveyQuestion.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtSurveyQuestion);
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
		cmbSystemName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					tableLoad(getId(getCmbSystemName()));
				}
			}    
		});
		
		menuItemDelete.setOnAction(e ->{
			if(tableSurveyQuestion.getSelectionModel().getSelectedItem() != null) {
				if(confirmationCheck("Delete This Survey Question?")) {
					TableItem tempInvestigation = tableSurveyQuestion.getSelectionModel().getSelectedItem();
					if(databaseHandler.execAction("delete from tbsurveyquestion where id= '"+tempInvestigation.getId()+"'")){
						
						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Survey Question Delete Successfully...");
						tableLoad(getId(""));
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Survey Question!","Please Select Any Survey Question for Delete...");
				tableSurveyQuestion.requestFocus();
			}
		});
	}

	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control =  {cmbSystemName,txtSurveyQuestion,btnSave};
		new FocusMoveByEnter(control);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		tableSurveyQuestion.setColumnResizePolicy(tableSurveyQuestion.CONSTRAINED_RESIZE_POLICY);
		surveyQuestionCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		
		menuItemDelete = new MenuItem("Delete");
		contextMenuResult = new ContextMenu();
		contextMenuResult.getItems().add(menuItemDelete);
		tableSurveyQuestion.setContextMenu(contextMenuResult);
	}

	private void addCmp() {
		// TODO Auto-generated method stub



		hBoxSystem.getChildren().remove(1);
		cmbSystemName.setPrefWidth(483);
		cmbSystemName.setPrefHeight(25);
		cmbSystemName.setPromptText("System Name");

		hBoxSystem.getChildren().add(cmbSystemName);

	}

	public class TableItem{

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


	}

	public String getCmbSystemName() {
		if(cmbSystemName.getValue() != null)
			return cmbSystemName.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystemName(String cmbSystemName) {
		this.cmbSystemName.setValue(cmbSystemName);
	}

	public String getTxtSurveyQuestion() {
		return txtSurveyQuestion.getText().trim();
	}

	public void setTxtSurveyQuestion(String txtSurveyQuestion) {
		this.txtSurveyQuestion.setText(txtSurveyQuestion);
	}



}
