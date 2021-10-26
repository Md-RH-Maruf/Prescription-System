package shareClasses;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import prescription.main.MainFrameController;
import shareClasses.LoadedInfo.TableItemInfo;


public class SuggestedDialogController implements Initializable{

	@FXML
	TextArea txtCheapComplain;
	
	@FXML
	public Button btnAdd;
	@FXML
	public Button btnRefresh;
	@FXML
	public Button btnCancel;
	
	
	
	@FXML
	private TableView<TableItem> tableSuggested;
	ObservableList<TableItem> listSuggested = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItem, String> suggestedCol;
	@FXML
	private TableColumn<TableItem, CheckBox> checkCol;
	
	DatabaseHandler databaseHandler;
	String pId = "" ;
	String sql;
	String cheapComplain;
	String cheapComplainId;
	
	HashMap<String, ObservableList<TableItem>> mapSuggestList = new HashMap<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		focusMoveAction();
		setCmpData();
		setCmpAction();
		setCmpFocusAction();
		//btnRefreshAction(null);
		
	}

	@FXML
	private void btnRefreshAction(ActionEvent object) {
		// TODO Auto-generated method stub
		setTxtCheapComplain(cheapComplain);
		//String temp="";
		/*ObservableList<TableItem> tableItems = tableSuggested.getItems();
		
		
		for(int i = 0 ;i<tableItems.size();i++){
			tableItems.get(i).setChecked(false);
		}*/
		
		mapSuggestList.put(pId,FXCollections.observableArrayList());
		ObservableList<TableItemInfo> temp = LoadedInfo.getCheapComplainCauseListByHeadId(pId);
		for(int i =0;i<temp.size();i++){
			mapSuggestList.get(pId).add(new TableItem(temp.get(i).getItemId(), temp.get(i).getItemName()));
		}
		
		
		tableSuggested.setItems(mapSuggestList.get(pId));
		tableSuggested.refresh();
	}
	
	

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		
	}

	private void setCmpAction() {
		// TODO Auto-generated method stub
		txtCheapComplain.setOnKeyReleased(e->{
			setCheapComplain(getTxtCheapComplain());
		});
	}
	
	private void checkBoxClickAction(){
		String temp="";
		ObservableList<TableItem> tableItems = tableSuggested.getItems();
		for(int i = 0 ;i<tableItems.size();i++){
			if(tableItems.get(i).isChecked()){
				temp += tableItems.get(i).getItemName()+",";
			}
		}
		if(temp.length()>0){
			temp = temp.substring(0, temp.length()-1);
			setTxtCheapComplain(cheapComplain+" ("+temp+")");
		}else{
			setTxtCheapComplain(cheapComplain);
		}
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		tableSuggested.setColumnResizePolicy(tableSuggested.CONSTRAINED_RESIZE_POLICY);
		suggestedCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		checkCol.setCellValueFactory(new PropertyValueFactory("check"));
		tableSuggested.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		suggestedCol.setCellFactory(tc -> {
			TableCell<TableItem, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(suggestedCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});
	}

	private void focusMoveAction() {
		// TODO Auto-generated method stub
		
	}

	private void addCmp() {
		// TODO Auto-generated method stub
		
	}
	
	public class TableItem{

		private SimpleStringProperty id;
		private SimpleStringProperty itemName;
		private CheckBox check;

		public TableItem(String id,String itemName) {
			this.id = new SimpleStringProperty(id);
			this.itemName = new SimpleStringProperty(itemName);
			
			check = new CheckBox();
			check.setOnAction(e->{
				checkBoxClickAction();
			});
			
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

		public CheckBox getCheck() {
			return check;
		}

		public void setCheck(CheckBox check) {
			this.check = check;
		}

		public boolean isChecked(){
			return this.check.isSelected();
		}
		
		public void setChecked(boolean check){
			this.check.setSelected(check);
		}
	}

	public ObservableList<TableItem> getListSuggested() {
		return listSuggested;
	}

	public void setListSuggested(ObservableList<TableItem> listSuggested) {
		this.listSuggested = listSuggested;
	}

	public ObservableList<TableItem> getMapSuggestList(String pId) {
		return mapSuggestList.get(pId);
	}

	public void setSuggestList(String pId) {
		setPId(pId);
		if(mapSuggestList.get(pId) != null) {
			tableSuggested.setItems(mapSuggestList.get(pId));
		}else {
			try {
				mapSuggestList.put(pId,FXCollections.observableArrayList());
				
				ObservableList<TableItemInfo> temp = LoadedInfo.getCheapComplainCauseListByHeadId(pId);
				
				for(int i =0;i<temp.size();i++){
					mapSuggestList.get(pId).add(new TableItem(temp.get(i).getItemId(), temp.get(i).getItemName()));
				}
				//sql = "select * from tbcc where type = '2' and headId='"+pId+"' group by name order by name ";
				//ResultSet rs = databaseHandler.execQuery(sql);
				/*while(rs.next()) {
					
				}*/
				
				tableSuggested.setItems(mapSuggestList.get(pId));
				
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}
			
		}
	}
	
	public void clearMapList() {
		mapSuggestList.clear();
	}

	public String getCheapComplain() {
		return cheapComplain;
	}

	public void setCheapComplain(String cheapComplain) {
		this.cheapComplain = cheapComplain;
	}

	public String getCheapComplainId() {
		return cheapComplainId;
	}

	public void setCheapComplainId(String cheapComplainId) {
		this.cheapComplainId = cheapComplainId;
	}

	public String getTxtCheapComplain() {
		return txtCheapComplain.getText().trim();
	}

	public void setTxtCheapComplain(String txtCheapComplain) {
		this.txtCheapComplain.setText(txtCheapComplain);
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}
	

}
