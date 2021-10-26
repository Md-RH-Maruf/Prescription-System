package prescription.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.MenuSelectionManager;

import com.sun.org.apache.bcel.internal.generic.NEW;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.Notification;
import shareClasses.TableContextMenu;
import shareClasses.TableItemRowFactory;
import shareClasses.LoadedInfo.TableItemInfo;

public class ClinicalExaminationCreateController implements Initializable{


	@FXML
	TextField txtExaminationId;
	@FXML
	TextField txtExaminationName;
	@FXML
	TextField txtUnit;
	@FXML
	TextField txtResult;

	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnDelete;
	@FXML
	Button btnRefresh;
	@FXML
	Button btnFind;
	@FXML
	Button btnAdd;

	@FXML
	ComboBox<String> cmbInputField;
	@FXML
	ComboBox<String> cmbResultType;

	FxComboBox cmbExaminationHead = new FxComboBox<>();
	FxComboBox cmbFind = new FxComboBox<>();

	MenuItem menuItemRefresh;
	MenuItem menuItemDeleteExamination;
	MenuItem menuItemUpdateSerial;

	ContextMenu contextMenuExamination;

	MenuItem menuItemDeleteResult ;

	ContextMenu contextMenuResult ;


	@FXML
	TreeView treeExamination;
	TreeItem<ClinicalExaminationItem> treeItemRoot=new TreeItem<ClinicalExaminationItem>(new ClinicalExaminationItem("1", "0", "Clinical Examination"));

	@FXML
	private TableView<TableItemInfo> tableClinicalExamination;
	@FXML
	private TableColumn<TableItemInfo, String> slCol;
	@FXML
	private TableColumn<TableItemInfo, String> examinationNameCol;


	@FXML
	VBox vBox;
	@FXML
	HBox hBoxFind;

	@FXML
	private TableView<Result> tableResult;
	ObservableList<Result> listResult = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Result, String> resultCol;

	DatabaseHandler databaseHandler;
	String sql;

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

		if(!isIdExist()) {
			if(validationCheck()) {
				if(confirmationCheck("Save This Examination")) {
					if(databaseHandler.execAction("insert into tbclinicalexamination (id,Name,unit,headId,headNameList,inputField,resultType,type) "
							+ "values ("+getTxtExaminationId()+",'"+getTxtExaminationName()+"','"+getTxtUnit()+"','"+getExaminationId(getCmbExaminationHead())+"','"+getHeadName(getCmbExaminationHead())+"','"+getCmbInputField()+"','"+getCmbResultType()+"','1');")){

						new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfull!","Examination Save Successfully...");
						clinicalExaminationLoad();
						treeLoad();
					}
				}	
			}	
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "This Id allready exist!","You Can not save this id agin.\nYou Can Edit this examination...\nOr refresh for save new examination...");
			btnRefresh.requestFocus();
		}



	}




	@FXML
	private void btnEditAction(ActionEvent event) {
		if(isIdExist()) {
			if(validationCheck()) {
				if(confirmationCheck("Edit This Examination")) {
					if(databaseHandler.execAction("update tbclinicalexamination set Name='"+getTxtExaminationName()+"',unit='"+getTxtUnit()+"',headId='"+getExaminationId(getCmbExaminationHead())+"',headNameList='"+getHeadName(getCmbExaminationHead())+"',inputField = '"+getCmbInputField()+"',resultType = '"+getCmbResultType()+"',type='1' where id="+getTxtExaminationId()+"")){					
						new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfull!","Examination Edit Successfully...");
						clinicalExaminationLoad();
						treeLoad();
					}
				}	
			}	
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "This Id not exist!","You Should save this id...\nOr Edit Exis Id..");
			btnRefresh.requestFocus();
		}
	}

	@FXML
	private void btnDeleteAction(ActionEvent event) {
		if(confirmationCheck("Delete This Examination")) {
			if(databaseHandler.execAction("delete from tbclinicalexamination where id="+getTxtExaminationId()+"")){
				new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Examination Delete Successfully...");
				clinicalExaminationLoad();
				treeLoad();
			}
		}
	}

	@FXML
	private void btnResultAddAction(ActionEvent event) {
		if(isIdExist()) {
			if(!getTxtResult().isEmpty()) {
				if(duplicateResultCheck(getTxtResult(), getTxtExaminationId())) {
					if(confirmationCheck("Save This Result?")) {
						if(databaseHandler.execAction("insert into tbclinicalexamination (id,Name,unit,headId,type) values ("+getMaxId()+",'"+getTxtResult()+"','','"+getTxtExaminationId()+"','2');")) {
							new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfull!","Result Save Successfully...");
							listResult.add(new Result(getResultId(getTxtResult(), getTxtExaminationId()), getTxtExaminationId(), getTxtResult()));
							tableResult.setItems(listResult);
							setTxtResult("");
						}
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "All ready exist!","This Result Allready Exist...");
					txtExaminationName.requestFocus();
				}			
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Result!","Please Enter any Result for this Examination...");
				txtResult.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "This Id not exist!","You Can not save any result In This Id...\nYou Should Save Result In Exist Id..");
			btnRefresh.requestFocus();
		}

	}
	@FXML
	private void btnFindAction(ActionEvent event) {
		try {
			if(!getCmbFind().isEmpty()) {
				if(LoadedInfo.isClinicalExaminationExist(getCmbFind())) {
					findAction(LoadedInfo.getClinicalExaminationInfo(getCmbFind()).getItemId());
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Examination Name!","Please Select a valid Examination name...");
					cmbFind.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Examination!","Please Select Any Examination...");
				cmbFind.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		setTxtExaminationId(getMaxId());
		setCmbExaminationHead("");
		setTxtExaminationName("");
		setTxtUnit("");
		setTxtResult("");
		setCmbInputField(null);
		setCmbResultType(null);
		setCmbFind("");
		treeLoad();
		clinicalExaminationLoad();

		tableClinicalExamination.setItems(LoadedInfo.getClinicalExaminationList());

		listResult.clear();
		tableResult.setItems(listResult);
	}

	private void deleteResultAction() {
		// TODO Auto-generated method stub
		if(tableResult.getSelectionModel().getSelectedItem() != null) {
			if(confirmationCheck("Delete This Result?")) {
				Result tempResult = tableResult.getSelectionModel().getSelectedItem();
				if(databaseHandler.execAction("delete from tbclinicalexamination where id= '"+tempResult.getId()+"' and type= '2'")){

					new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Result Delete Successfully...");
					listResult.remove(tempResult);
					tableResult.setItems(listResult);
				}
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Result!","Please Select Any Result for Delete...");
			tableResult.requestFocus();
		}

	}

	@FXML
	private void examinationTreeClickAction(MouseEvent event) {
		if(treeExamination.getSelectionModel().getSelectedItem() != null) {
			TreeItem<ClinicalExaminationItem> tempTreeItem = (TreeItem<ClinicalExaminationItem>) treeExamination.getSelectionModel().getSelectedItem();
			setCmbFind(tempTreeItem.getValue().getText());
			findAction(tempTreeItem.getValue().getItemId());
			tableClinicalExamination.setItems(LoadedInfo.getClinicalExaminationListByHeadId(tempTreeItem.getValue().getItemId()));
		}
	}

	@FXML
	private void examinationTableClickAction(MouseEvent event) {
		if(tableClinicalExamination.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTreeItem = tableClinicalExamination.getSelectionModel().getSelectedItem();
			setCmbFind(tempTreeItem.getItemName());
			findAction(tempTreeItem.getItemId());

		}
	}


	private void findAction(String id){
		try {

			sql = "SELECT *,(SELECT NAME FROM tbclinicalexamination WHERE id = c.headid) AS headName FROM tbclinicalexamination c WHERE id =  '"+id+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtExaminationId(rs.getString("id"));
				setCmbExaminationHead(rs.getString("headName"));
				setTxtExaminationName(rs.getString("name"));
				setTxtUnit(rs.getString("unit"));
				setCmbInputField(rs.getString("InputField"));
				setCmbResultType(rs.getString("ResultType"));
			}

			listResult.clear();
			sql = "SELECT * FROM tbclinicalexamination WHERE headId =  '"+getTxtExaminationId()+"' and type='2' ";
			rs = databaseHandler.execQuery(sql);	
			while(rs.next()) {
				listResult.add(new Result(rs.getString("id"), rs.getString("headid"), rs.getString("name")));
			}

			tableResult.setItems(listResult);

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private boolean validationCheck() {
		// TODO Auto-generated method stub
		if(!getCmbExaminationHead().isEmpty()) {
			if(!getTxtExaminationName().isEmpty()) {
				if(!getCmbInputField().isEmpty()) {
					if(!getCmbResultType().isEmpty()) {
						if(LoadedInfo.isClinicalExaminationExist(getCmbExaminationHead())) {
							if(duplicateCheck(getTxtExaminationName(),getTxtExaminationId())) {

								return true;

							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "All ready exist!","This Clinical Examination Name Allready Exist...");
								txtExaminationName.requestFocus();
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Examination Head!","Please Select valid examination head...");
							cmbExaminationHead.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Result Type!","Please Select Result Type..");
						cmbResultType.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Input Field!","Please Select Input Field Type..");
					cmbInputField.requestFocus();
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Examination Name!","Please Enter any examination name...");
				txtExaminationName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Examination Head!","Please Select any valid examination head...");
			cmbExaminationHead.requestFocus();
		}
		return false;
	}

	/*protected boolean duplicateCheck(String examinationName) {
		try {
			String sql="SELECT * FROM tbClinicalExamination WHERE  NAME='"+examinationName+"' ;";

			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}*/

	protected boolean duplicateCheck(String examinationName,String examinationId) {
		try {
			String sql="SELECT * FROM tbClinicalExamination WHERE  NAME='"+examinationName+"' AND id != '"+examinationId+"';";

			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private String getHeadName(String cmbExaminationHead) {
		// TODO Auto-generated method stub
		try {
			if(cmbExaminationHead.equals("Clinical Examination")) {
				return "";
			}else {
				ResultSet rs = databaseHandler.execQuery("select (select name from tbclinicalexamination where id = e.headId) as headName from tbclinicalExamination e where name = '"+cmbExaminationHead+"' and type = '1'");
				if(rs.next()) {
					String headName = getHeadName(rs.getString("headname"));
					return (headName.equals("")?"":headName+"> ")+cmbExaminationHead;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

		return "";
	}

	private String getExaminationId(String examinationName) {
		try {
			sql = "select id from tbclinicalExamination where name = '"+examinationName+"' and type = '1'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private String getResultId(String examinationName,String headId) {
		try {
			sql = "select id from tbclinicalExamination where name = '"+examinationName+"' and type = '2' and headid='"+headId+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}
	private boolean duplicateResultCheck(String result, String headId) {
		try {
			String sql="select * from tbClinicalExamination where name='"+result+"' and headId = "+headId+";";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private boolean isIdExist() {
		try {
			String sql="SELECT * FROM tbClinicalExamination WHERE id = '"+getTxtExaminationId()+"';";

			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		return false;
	}

	private String getMaxId() {
		try {
			sql="select (select ifnull(max(id),0)+1)as maxid from tbclinicalexamination ;";

			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("maxid");
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return "0";

	}

	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message);
	}

	private void clinicalExaminationLoad() {
		// TODO Auto-generated method stub
		try {
			cmbExaminationHead.data.clear();
			cmbExaminationHead.data.add("");
			cmbFind.data.clear();
			cmbFind.data.add("");
			sql = "SELECT Name FROM tbclinicalexamination WHERE type=1 ORDER BY name ";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbExaminationHead.data.add(rs.getString("name"));
				cmbFind.data.add(rs.getString("name"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private void treeLoad() {
		// TODO Auto-generated method stub
		treeExamination.setRoot(treeLoad(treeItemRoot,"1"));
	}


	private TreeItem<ClinicalExaminationItem> treeLoad(TreeItem<ClinicalExaminationItem> treeItem,String pHeadId) {
		// TODO Auto-generated method stub
		try {
			treeItem.getChildren().clear();

			ArrayList<String> treeName = new ArrayList<>();
			ArrayList<String> treeId = new ArrayList<>();

			sql = "select * FROM tbclinicalexamination WHERE headid = '"+pHeadId+"' and type != '2' order by sn";

			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				treeName.add(rs.getString("name"));
				treeId.add(rs.getString("id"));
			}

			for(int i =0 ;i<treeId.size();i++) {
				treeItem.getChildren().add(treeLoad(new TreeItem<ClinicalExaminationItem>(new ClinicalExaminationItem(treeId.get(i), pHeadId, treeName.get(i))),treeId.get(i)));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		treeItem.setExpanded(true);
		return treeItem;
	}

	private void setCmpAction() {
		// TODO Auto-generated method stub
		menuItemDeleteResult.setOnAction(e ->{
			deleteResultAction();
		});

		menuItemRefresh.setOnAction(e->{
			tableClinicalExamination.setItems(LoadedInfo.getClinicalExaminationList());
		});

		menuItemDeleteExamination.setOnAction(e->{
			if(tableClinicalExamination.getSelectionModel().getSelectedItem() != null){
				if(confirmationCheck("Delete This Examination")) {
					if(databaseHandler.execAction("delete from tbclinicalexamination where id="+tableClinicalExamination.getSelectionModel().getSelectedItem().getItemId()+"")){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfull!","Examination Delete Successfully...");
						clinicalExaminationLoad();
						treeLoad();
					}
				}
			}

		});

		menuItemUpdateSerial.setOnAction(e->{
			if(confirmationCheck("Update Serial No?")){
				for(int i=0;i < tableClinicalExamination.getItems().size();i++){
					sql = "update tbclinicalexamination set sn = '"+i+"' where sn = '"+tableClinicalExamination.getItems().get(i).getSlNo()+"' and id = '"+tableClinicalExamination.getItems().get(i).getItemId()+"' ;";
					databaseHandler.execAction(sql);

				}
				LoadedInfo.loadClinicalExaminationInfo();
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Update Successfully...!","Serial No Update Successfully....");
				treeLoad();
			}

		});
	}




	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbExaminationHead.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbExaminationHead);
		});

		cmbFind.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbFind);
		});

		txtExaminationName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtExaminationName);
		});

		txtUnit.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtUnit);
		});

		txtResult.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtResult);
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
	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control =  {txtResult,btnAdd};
		new FocusMoveByEnter(control);

		Control[] control1 =  {cmbExaminationHead,txtExaminationName,txtUnit,cmbInputField,cmbResultType,btnSave};
		new FocusMoveByEnter(control1);
	}



	private void setCmpData() {
		// TODO Auto-generated method stub
		tableResult.setColumnResizePolicy(tableResult.CONSTRAINED_RESIZE_POLICY);
		resultCol.setCellValueFactory(new PropertyValueFactory("result"));

		menuItemRefresh = new MenuItem("Refresh");
		menuItemDeleteExamination = new MenuItem("Delete");
		menuItemUpdateSerial = new MenuItem("Update Serial");
		contextMenuExamination = new ContextMenu();

		contextMenuExamination.getItems().addAll(menuItemRefresh,new SeparatorMenuItem(),menuItemDeleteExamination,menuItemUpdateSerial);

		tableClinicalExamination.setContextMenu(contextMenuExamination);

		menuItemDeleteResult = new MenuItem("Delete");
		contextMenuResult = new ContextMenu();

		contextMenuResult.getItems().add(menuItemDeleteResult);

		tableResult.setContextMenu(contextMenuResult);

		cmbInputField.getItems().addAll("Text Field","Combo Box");

		cmbResultType.getItems().addAll("Single","Multiple");

		tableClinicalExamination.setColumnResizePolicy(tableClinicalExamination.CONSTRAINED_RESIZE_POLICY);
		slCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		examinationNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		examinationNameCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(examinationNameCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		new TableItemRowFactory(tableClinicalExamination);
	}


	private void addCmp() {
		// TODO Auto-generated method stub

		vBox.getChildren().remove(1);

		cmbExaminationHead.setPrefWidth(1000);
		cmbExaminationHead.setPrefHeight(28);
		cmbExaminationHead.setPromptText("Examination Head");
		vBox.getChildren().add(1,cmbExaminationHead);

		hBoxFind.getChildren().remove(1);

		cmbFind.setPrefWidth(275);
		cmbFind.setPrefHeight(28);
		cmbFind.setPromptText("Find Examination");
		hBoxFind.getChildren().add(1,cmbFind);
	}

	public class ClinicalExaminationItem extends Label{
		String itemId;
		String headId;
		public ClinicalExaminationItem(String itemId,String headId,String examinationName){
			super(examinationName);
			this.itemId = itemId;
			this.headId = headId;
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


	}

	public class Result{

		private SimpleStringProperty id;
		private SimpleStringProperty result;
		private SimpleStringProperty examinationId;

		public Result(String id,String examinationId,String result) {
			this.id = new SimpleStringProperty(id);
			this.result = new SimpleStringProperty(result);
			this.examinationId = new SimpleStringProperty(examinationId);
		}

		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getResult() {
			return result.get();
		}

		public void setResult(String result) {
			this.result = new SimpleStringProperty(result);
		}

		public String getExaminationId() {
			return examinationId.get();
		}

		public void setExaminationId(String examinationId) {
			this.examinationId = new SimpleStringProperty(examinationId);
		}

	}


	public String getTxtExaminationId() {
		return txtExaminationId.getText().replace("'", "''").trim();
	}


	public void setTxtExaminationId(String txtExaminationId) {
		this.txtExaminationId.setText(txtExaminationId);
	}


	public String getTxtExaminationName() {
		return txtExaminationName.getText().replace("'", "''").trim();
	}


	public void setTxtExaminationName(String txtExaminationName) {
		this.txtExaminationName.setText(txtExaminationName);
	}


	public String getTxtUnit() {
		return txtUnit.getText().replace("'", "''").trim();
	}


	public void setTxtUnit(String txtUnit) {
		this.txtUnit.setText(txtUnit);
	}


	public String getTxtResult() {
		return txtResult.getText().replace("'", "''").trim();
	}


	public void setTxtResult(String txtResult) {
		this.txtResult.setText(txtResult);
	}


	public String getCmbInputField() {
		if(cmbInputField.getValue() != null)
			return cmbInputField.getValue().toString().replace("'", "''").trim();
		else return "";
	}


	public void setCmbInputField(String cmbInputField) {	
		this.cmbInputField.setValue(cmbInputField);
	}


	public String getCmbResultType() {
		if(cmbResultType.getValue() != null)
			return cmbResultType.getValue().toString().replace("'", "''").trim();
		else return "";
	}


	public void setCmbResultType(String cmbResultType) {
		this.cmbResultType.setValue(cmbResultType);	
	}


	public String getCmbExaminationHead() {
		if(cmbExaminationHead.getValue() != null)
			return cmbExaminationHead.getValue().toString().replace("'", "''").trim();
		else return "";
	}


	public void setCmbExaminationHead(String cmbExaminationHead) {
		this.cmbExaminationHead.setValue(cmbExaminationHead);
	}

	public String getCmbFind() {
		if(cmbFind.getValue() != null)
			return cmbFind.getValue().toString().replace("'", "''").trim();
		else return "";
	}


	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}



}
