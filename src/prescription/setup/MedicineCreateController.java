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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import shareClasses.AlertMaker;
import shareClasses.CustomContextMenu;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.NewCustomContextMenu;
import shareClasses.LoadedInfo.MedicineItemInfo;
import shareClasses.LoadedInfo.TableItemInfo;
import shareClasses.NodeType;
import shareClasses.Notification;
import shareClasses.TableItemRowFactory;

public class MedicineCreateController implements Initializable{


	FxComboBox cmbSystem = new FxComboBox<>();
	FxComboBox cmbDisease = new FxComboBox<>();
	FxComboBox cmbGroup = new FxComboBox<>();
	FxComboBox cmbGeneric = new FxComboBox<>();
	FxComboBox cmbBrand = new FxComboBox<>();
	FxComboBox cmbSystemDisease = new FxComboBox<>();
	FxComboBox cmbSystemGroup = new FxComboBox<>();
	FxComboBox cmbDiseaseGroup = new FxComboBox<>();
	FxComboBox cmbSystemGeneric = new FxComboBox<>();
	FxComboBox cmbDiseaseGeneric = new FxComboBox<>();
	FxComboBox cmbGroupGeneric = new FxComboBox<>();
	FxComboBox cmbGenericBrand = new FxComboBox<>();
	FxComboBox cmbCompanyName = new FxComboBox<>();


	/*@FXML
	TextField txtSystem;
	@FXML
	TextField txtDisease;
	@FXML
	TextField txtGroup;
	@FXML
	TextField txtGeneric;
	@FXML
	TextField txtBrand;*/

	@FXML
	Button btnSaveSystem;
	@FXML
	Button btnEditSystem;

	@FXML
	Button btnSaveDisease;
	@FXML
	Button btnEditDisease;

	@FXML
	Button btnSaveGroup;
	@FXML
	Button btnEditGroup;

	@FXML
	Button btnSaveGeneric;
	@FXML
	Button btnEditGeneric;

	@FXML
	Button btnSaveBrand;
	@FXML
	Button btnEditBrand;

	
	
	@FXML
	HBox hBoxGroup;
	@FXML
	HBox hBoxGroup2;
	
	@FXML
	HBox hBoxGeneric;
	@FXML
	HBox hBoxGeneric2;
	@FXML
	HBox hBoxCompanyNameBrand;
	@FXML
	HBox hBoxSystem;
	@FXML
	HBox hBoxDisease2;
	@FXML
	VBox vBoxSystemDisease;
	@FXML
	VBox vBoxGenericBrand;

	CustomContextMenu contextMenuTxtSystem;
	CustomContextMenu contextMenuSystemDisease;
	CustomContextMenu contextMenuTxtDisease;
	CustomContextMenu contextMenuSystemGroup;
	CustomContextMenu contextMenuDiseaseGroup;
	CustomContextMenu contextMenuTxtGroup;
	CustomContextMenu contextMenuSystemGeneric;
	CustomContextMenu contextMenuDiseaseGeneric;
	CustomContextMenu contextMenuGroupGeneric;
	CustomContextMenu contextMenuTxtGeneric;
	CustomContextMenu contextMenuGenericBrand;
	CustomContextMenu contextMenuTxtBrand;
	CustomContextMenu contextMenuCompany;


	DatabaseHandler databaseHandler;
	String sql;

	@FXML
	private TableView<TableItemInfo> tableSystemName;
	ObservableList<TableItemInfo> listSystemName = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> systemSlNoCol;
	@FXML
	private TableColumn<TableItemInfo, String> systemNameCol;

	@FXML
	private TableView<TableItemInfo> tableDisease;
	ObservableList<TableItemInfo> listDisease = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> diseaseSlNoCol;
	@FXML
	private TableColumn<TableItemInfo, String> diseaseCol;

	@FXML
	private TableView<TableItemInfo> tableGroup;
	ObservableList<TableItemInfo> listGroup = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> groupSlNoCol;
	@FXML
	private TableColumn<TableItemInfo, String> groupCol;

	@FXML
	private TableView<TableItemInfo> tableGeneric;
	ObservableList<TableItemInfo> listGeneric = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> genericSlNoCol;
	@FXML
	private TableColumn<TableItemInfo, String> genericCol;

	@FXML
	private TableView<MedicineItemInfo> tableBrand;
	ObservableList<MedicineItemInfo> listBrand = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> brandSlNoCol;
	@FXML
	private TableColumn<MedicineItemInfo, String> brandNameCol;
	@FXML
	private TableColumn<MedicineItemInfo, String> companyNameCol;

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

	private void btnRefreshAction(Object object) {
		// TODO Auto-generated method stub	

		setCmbSystem("");	
		setCmbSystemDisease("");
		setCmbDisease("");
		setCmbSystemGroup("");
		setCmbDiseaseGroup("");
		setCmbGroup("");
		setCmbSystemGeneric("");
		setCmbDiseaseGeneric("");
		setCmbGroupGeneric("");
		setCmbGeneric("");
		setCmbGenericBrand("");
		setCmbBrand("");
		setCmbCompanyName("");


		systemComboLoad();
		diseaseComboLoad();
		comboBoxLoad(cmbGroup, 2);
		comboBoxLoad(cmbGroupGeneric, 2);
		comboBoxLoad(cmbGeneric, 3);
		comboBoxLoad(cmbGenericBrand, 3);
		comboBoxLoad(cmbBrand, 4);
		companyNameComboLoad();


		tableDataLoad(listSystemName, 0, "");
		tableDataLoad(listDisease, 1, "");
		tableDataLoad(listGroup, 2, "");
		tableDataLoad(listGeneric, 3, "");
		tableDataLoad(listBrand, 4, "");

	}

	@FXML
	private void btnSystemSaveAction(ActionEvent event) {
		try {

			if(!getCmbSystem().isEmpty()) {
				if(!LoadedInfo.isSystemExist(getCmbSystem())) {
					if(confirmationCheck("Save This System?")) {
						if(saveAction(getMaxId(), getCmbSystem(), "0", 0)) {
							new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","System Name Save Successfully...");

							tableDataLoad(listSystemName, 0, "");

							comboBoxLoad(cmbSystemDisease, 0);
							comboBoxLoad(cmbSystemGroup, 0);
							comboBoxLoad(cmbSystemGeneric, 0);
						}

					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate System Name!","This System Name All ready Exist...\nPlease Enter New System Name");
					cmbSystem.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty System Name!","Please Enter System Name");
				cmbSystem.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnSystemEditAction(ActionEvent event) {
		try {

			if(tableSystemName.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempTableItem = tableSystemName.getSelectionModel().getSelectedItem();
				if(!getCmbSystem().isEmpty()) {
					if(!LoadedInfo.isSystemExist(getCmbSystem())) {
						if(confirmationCheck("Edit This System Name?")) {
							if(editAction(tempTableItem.getItemId(), getCmbSystem(), 0)) {
								new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","System Name Edit Successfully...");

								tableDataLoad(listSystemName, 0, "");

								comboBoxLoad(cmbSystemDisease, 0);
								comboBoxLoad(cmbSystemGroup, 0);
								comboBoxLoad(cmbSystemGeneric, 0);
							}
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate System Name!","This System Name All ready Exist...\nPlease Enter New System Name");
						cmbSystem.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty System Name!","Please Enter System Name");
					cmbSystem.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select System Name!","Please Select Any Sysste Name");
				tableSystemName.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnDiseaseSaveAction(ActionEvent event) {
		try {
			if(!getCmbDisease().isEmpty()) {
				if(LoadedInfo.isSystemExist(getCmbSystemDisease())) {
					String pId= LoadedInfo.getSystemIdByName(getCmbSystemDisease());
					if(duplicateItemCheck(getCmbDisease(), pId, 1)) {
						if(confirmationCheck("Save This Disease?")) {
							if(LoadedInfo.isDiseaseExist(getCmbDisease())){
								if(saveAction(LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId(), getCmbDisease(), pId, 1)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Disease Name Save Successfully...");

									tableDataLoad(listDisease, 1, "");

									comboBoxLoad(cmbDiseaseGeneric, 1);
									comboBoxLoad(cmbDiseaseGroup, 1);

								}
							}else{
								if(saveAction(getMaxId(), getCmbDisease(), pId, 1)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Disease Name Save Successfully...");

									tableDataLoad(listDisease, 1, "");

									comboBoxLoad(cmbDiseaseGeneric, 1);
									comboBoxLoad(cmbDiseaseGroup, 1);

								}
							}
							

						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Disease Name!","This Disease Name All ready Exist In This System...\nPlease Enter New Disease Name For This System Name");
						cmbDisease.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid System Name!","Please Select a valid System Name");
					cmbSystemDisease.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Disease Name!","Please Enter Disease Name");
				cmbDisease.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnDiseaseEditAction(ActionEvent event) {
		try {
			if(tableDisease.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempTableItem = tableDisease.getSelectionModel().getSelectedItem();
				if(!getCmbDisease().isEmpty()) {
					if(LoadedInfo.isSystemExist(getCmbSystemDisease())) {
						String pId= LoadedInfo.getSystemIdByName(getCmbSystemDisease());
						
						if(duplicateItemCheck(getCmbDisease(), pId, 1)) {
							if(confirmationCheck("Edit This Disease Name?")) {
								
								if(LoadedInfo.isDiseaseExist(getCmbDisease())){
									if(editAction(tempTableItem.getItemId(), LoadedInfo.getDiseaseInfo(getCmbDisease()).getItemId(), getCmbDisease(), tempTableItem.getHeadId(), pId, 1)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Disease Name Edit Successfully...");

										tableDataLoad(listDisease, 1, "");

										comboBoxLoad(cmbDiseaseGeneric, 1);
										comboBoxLoad(cmbDiseaseGroup, 1);
									}
								}else{
									
									if(editAction(tempTableItem.getItemId(), getCmbDisease(), 1)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Disease Name Edit Successfully...");

										tableDataLoad(listDisease, 1, "");

										comboBoxLoad(cmbDiseaseGeneric, 1);
										comboBoxLoad(cmbDiseaseGroup, 1);
									}
								}
								
								
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Disease Name!","This Disease Name All ready Exist In This System...\nPlease Enter New Disease Name For This System Name");
							cmbDisease.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid System Name!","Please Select a valid System Name");
						cmbSystemDisease.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Disease Name!","Please Enter Disease Name");
					cmbDisease.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Disease Name!","Please Select Any Disease Name");
				tableDisease.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnGroupSaveAction(ActionEvent event) {
		try {
			if(!getCmbGroup().isEmpty()) {
				if(LoadedInfo.isDiseaseExist(getCmbDiseaseGroup())) {
					String pId= LoadedInfo.getDiseaseInfo(getCmbDiseaseGroup()).getItemId();
					if(duplicateItemCheck(getCmbGroup(), pId, 2)) {
						if(confirmationCheck("Save This Group?")) {
							if(LoadedInfo.isMedicineGroupExist(getCmbGroup())){
								if(saveAction(LoadedInfo.getMedicineGroupInfo(getCmbGroup()).getItemId(), getCmbGroup(), getId(getCmbDiseaseGroup(), 1), 2)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Group Name Save Successfully...");

									tableDataLoad(listGroup, 2, "");

									comboBoxLoad(cmbGroupGeneric, 2);
								}
							}else{
								if(saveAction(getMaxId(), getCmbGroup(), pId, 2)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Group Name Save Successfully...");

									tableDataLoad(listGroup, 2, "");

									comboBoxLoad(cmbGroupGeneric, 2);
								}
							}
							

						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Group Name!","This Group Name All ready Exist In This Disease...\nPlease Enter Different Group Name For This Disease..");
						cmbGroup.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid Disease Name!","Please Select a valid Disease Name");
					cmbDiseaseGroup.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Group Name!","Please Enter Group Name");
				cmbGroup.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnGroupEditAction(ActionEvent event) {
		try {

			if(tableGroup.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempTableItem = tableGroup.getSelectionModel().getSelectedItem();
				if(!getCmbGroup().isEmpty()) {
					if(LoadedInfo.isDiseaseExist(getCmbDiseaseGroup())) {
						String pId= LoadedInfo.getDiseaseInfo(getCmbDiseaseGroup()).getItemId();
						if(duplicateItemCheck(getCmbGroup(), pId, 2)) {
							if(confirmationCheck("Edit This Group Name?")) {
								
								if(LoadedInfo.isMedicineGroupExist(getCmbGroup())){
									if(editAction(tempTableItem.getItemId(), LoadedInfo.getMedicineGroupInfo(getCmbGroup()).getItemId(),getCmbGroup(),tempTableItem.getHeadId(),pId, 2)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Group Name Edit Successfully...");

										tableDataLoad(listGroup, 2, "");

										comboBoxLoad(cmbGroupGeneric, 2);

									}
								}else{
									if(editAction(tempTableItem.getItemId(), getCmbGroup(), 2)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Group Name Edit Successfully...");

										tableDataLoad(listGroup, 2, "");

										comboBoxLoad(cmbGroupGeneric, 2);

									}
								}

							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Group Name!","This Group Name All ready Exist In This Disease...\nPlease Enter Different Group Name For This Disease..");
							cmbGroup.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid Disease Name!","Please Select a valid Disease Name");
						cmbDiseaseGroup.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Group Name!","Please Enter Group Name");
					cmbGroup.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Group Name!","Please Select Any Group Name");
				tableGroup.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnGenericSaveAction(ActionEvent event) {
		try {
			if(!getCmbGeneric().isEmpty()) {
				if(LoadedInfo.isMedicineGroupExist(getCmbGroupGeneric())) {
					String pId = LoadedInfo.getMedicineGroupInfo(getCmbGroupGeneric()).getItemId();
					if(duplicateItemCheck(getCmbGeneric(), pId, 3)) {
						if(confirmationCheck("Save This Generic?")) {
							if(LoadedInfo.isGenericExist(getCmbGeneric())){
								if(saveAction(LoadedInfo.getGenericInfo(getCmbGeneric()).getItemId(), getCmbGeneric(), pId, 3)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Generic Name Save Successfully...");
									tableDataLoad(listGeneric, 3, "");
									comboBoxLoad(cmbGenericBrand, 3);
								}
							}else{
								if(saveAction(getMaxId(), getCmbGeneric(), pId, 3)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Generic Name Save Successfully...");
									tableDataLoad(listGeneric, 3, "");
									comboBoxLoad(cmbGenericBrand, 3);
								}
							}
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name!","This Generic Name All ready Exist In This Group...\nPlease Enter Different Generic Name For This Group..");
						cmbGeneric.requestFocus();
					}
				}else if(LoadedInfo.isDiseaseExist(getCmbDiseaseGeneric())){
					String pId = LoadedInfo.getDiseaseInfo(getCmbDiseaseGeneric()).getItemId();
					if(duplicateItemCheck(getCmbGeneric(), pId, 3)) {
						if(confirmationCheck("Save This Generic?")) {

							if(confirmationCheck("Save This Generic?")) {
								if(LoadedInfo.isGenericExist(getCmbGeneric())){
									if(saveAction(LoadedInfo.getGenericInfo(getCmbGeneric()).getItemId(), getCmbGeneric(), pId, 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Generic Name Save Successfully...");
										tableDataLoad(listGeneric, 3, "");
										comboBoxLoad(cmbGenericBrand, 3);
									}
								}else{
									if(saveAction(getMaxId(), getCmbGeneric(), pId, 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Generic Name Save Successfully...");
										tableDataLoad(listGeneric, 3, "");
										comboBoxLoad(cmbGenericBrand, 3);
									}
								}
							}

						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name!","This Generic Name All ready Exist In This Disease...\nPlease Enter Different Generic Name For This Disease..");
						cmbGeneric.requestFocus();
					}
				}else if(LoadedInfo.isSystemExist(getCmbSystemGeneric())){
					String pId = LoadedInfo.getSystemInfo(getCmbSystemGeneric()).getItemId();
					if(duplicateItemCheck(getCmbGeneric(), pId , 3)) {
						if(confirmationCheck("Save This Generic?")) {

							if(confirmationCheck("Save This Generic?")) {
								if(LoadedInfo.isGenericExist(getCmbGeneric())){
									if(saveAction(LoadedInfo.getGenericInfo(getCmbGeneric()).getItemId(), getCmbGeneric(), pId, 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Generic Name Save Successfully...");
										tableDataLoad(listGeneric, 3, "");
										comboBoxLoad(cmbGenericBrand, 3);
									}
								}else{
									if(saveAction(getMaxId(), getCmbGeneric(), pId, 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Generic Name Save Successfully...");
										tableDataLoad(listGeneric, 3, "");
										comboBoxLoad(cmbGenericBrand, 3);
									}
								}
							}

						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name!","This Generic Name All ready Exist In This System...\nPlease Enter Different Generic Name For This System..");
						cmbGeneric.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid Head(System/Disease/Group)  Name!","Please Select a Valid System/Disease/Group Name");
					cmbGroupGeneric.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Group Name!","Please Enter Group Name");
				cmbGeneric.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnGenericEditAction(ActionEvent event) {
		try {
			if(tableGeneric.getSelectionModel().getSelectedItem() != null) {
				TableItemInfo tempTableItem = tableGeneric.getSelectionModel().getSelectedItem();
				if(!getCmbGeneric().isEmpty()) {
					if(LoadedInfo.isMedicineGroupExist(getCmbGroupGeneric())) {
						String pId = LoadedInfo.getMedicineGroupInfo(getCmbGroupGeneric()).getItemId();
						if(duplicateItemCheck(getCmbGeneric(), pId, 3)) {
							if(confirmationCheck("Edit This Generic Name?")) {
								if(LoadedInfo.isGenericExist(getCmbGeneric())){
									if(editAction(tempTableItem.getItemId(),LoadedInfo.getGenericInfo(getCmbGeneric()).getItemId(), getCmbGeneric(),tempTableItem.getHeadId(),pId, 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Generic Name Edit Successfully...");

										tableDataLoad(listGeneric, 3, "");

										comboBoxLoad(cmbGenericBrand, 3);

									}

								}else{
									if(editAction(tempTableItem.getItemId(), getCmbGeneric(), 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Generic Name Edit Successfully...");

										tableDataLoad(listGeneric, 3, "");

										comboBoxLoad(cmbGenericBrand, 3);

									}
								}
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name!","This Generic Name All ready Exist In This Group...\nPlease Enter Different Generic Name For This Group..");
							cmbGeneric.requestFocus();
						}
					}else if(LoadedInfo.isDiseaseExist(getCmbDiseaseGeneric())) {
						String pId = LoadedInfo.getDiseaseInfo(getCmbDiseaseGeneric()).getItemId();
						if(duplicateItemCheck(getCmbGeneric(),pId, 3)) {
							if(confirmationCheck("Edit This Generic Name?")) {
								if(LoadedInfo.isGenericExist(getCmbGeneric())){
									if(editAction(tempTableItem.getItemId(),LoadedInfo.getGenericInfo(getCmbGeneric()).getItemId(), getCmbGeneric(),tempTableItem.getHeadId(),pId, 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Generic Name Edit Successfully...");

										tableDataLoad(listGeneric, 3, "");

										comboBoxLoad(cmbGenericBrand, 3);

									}

								}else{
									if(editAction(tempTableItem.getItemId(), getCmbGeneric(), 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Generic Name Edit Successfully...");

										tableDataLoad(listGeneric, 3, "");

										comboBoxLoad(cmbGenericBrand, 3);

									}
								}
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name!","This Generic Name All ready Exist In This Disease...\nPlease Enter Different Generic Name For This Disease..");
							cmbGeneric.requestFocus();
						}
					}else if(LoadedInfo.isSystemExist(getCmbSystemGeneric())) {
						String pId = LoadedInfo.getSystemInfo(getCmbSystemGeneric()).getItemId();
						if(duplicateItemCheck(getCmbGeneric(), pId, 3)) {
							if(confirmationCheck("Edit This Generic Name?")) {
								if(LoadedInfo.isGenericExist(getCmbGeneric())){
									if(editAction(tempTableItem.getItemId(),LoadedInfo.getGenericInfo(getCmbGeneric()).getItemId(), getCmbGeneric(),tempTableItem.getHeadId(),pId, 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Generic Name Edit Successfully...");

										tableDataLoad(listGeneric, 3, "");

										comboBoxLoad(cmbGenericBrand, 3);

									}

								}else{
									if(editAction(tempTableItem.getItemId(), getCmbGeneric(), 3)) {
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Generic Name Edit Successfully...");

										tableDataLoad(listGeneric, 3, "");

										comboBoxLoad(cmbGenericBrand, 3);

									}
								}
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name!","This Generic Name All ready Exist In This System...\nPlease Enter Different Generic Name For This System..");
							cmbGroup.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid Head(System/Disease/Group)  Name!","Please Select a Valid System/Disease/Group Name");
						cmbGroupGeneric.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Generic Name!","Please Enter Generic Name");
					cmbGeneric.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Generic Name!","Please Select Any Generic Name");
				tableGeneric.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnBrandSaveAction(ActionEvent event) {
		try {
			if(!getCmbBrand().isEmpty()) {
				if(LoadedInfo.isGenericExist(getCmbGenericBrand())) {
					String pId = LoadedInfo.getGenericInfo(getCmbGenericBrand()).getItemId();
					if(duplicateItemCheck(getCmbBrand(), pId, 4)) {
						
						if(confirmationCheck("Save This Brand?")) {
							if(LoadedInfo.isMedicineBrandExist(getCmbBrand())){
								String id = LoadedInfo.getMedicineBrandInfo(getCmbBrand()).getItemId();
								if(saveAction(id, getCmbBrand(), getId(getCmbGenericBrand(), 3), 4)) {
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Brand Name Save Successfully...");
									tableDataLoad(listBrand, 4, "");

								}
							}else{
								String id = getMaxId();
								if(saveAction(id, getCmbBrand(), getId(getCmbGenericBrand(), 3), 4)) {
									if (!getCmbCompanyName().isEmpty()) {
										sql="insert into tbmedicinecompany (id,name) values('"+id+"','"+getCmbCompanyName()+"')";
										databaseHandler.execAction(sql);
									}
									new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","Brand Name Save Successfully...");
									tableDataLoad(listBrand, 4, "");

								}
							}
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Brand Name!","This Brand Name All ready Exist In This Generic...\nPlease Enter Different Brand Name For This Generic..");
						cmbBrand.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid Generic Name!","Please Select a valid Generic Name");
					cmbGenericBrand.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Brand Name!","Please Enter Brand Name");
				cmbBrand.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnBrandEditAction(ActionEvent event) {
		try {
			if(tableBrand.getSelectionModel().getSelectedItem() != null) {
				MedicineItemInfo tempTableItem = tableBrand.getSelectionModel().getSelectedItem();
				if(!getCmbBrand().isEmpty()) {
					if(LoadedInfo.isGenericExist(getCmbGenericBrand())) {
						String pId = LoadedInfo.getGenericInfo(getCmbGenericBrand()).getItemId();
						if(duplicateItemCheck(getCmbBrand(), pId, 4)) {
							if(confirmationCheck("Edit This Brand Name?")) {
								if(LoadedInfo.isMedicineBrandExist(getCmbBrand())){
									String newId = LoadedInfo.getMedicineBrandInfo(getCmbBrand()).getItemId();
									if(editAction(tempTableItem.getItemId(),newId, getCmbBrand(),tempTableItem.getHeadId(),pId, 4)) {
										
										sql="update tbmedicinecompany set name='"+getCmbCompanyName()+"' where id='"+newId+"'";
										databaseHandler.execAction(sql);
										
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Brand Name Edit Successfully...");

										tableDataLoad(listBrand, 4, "");

									}
								}else{
									if(editAction(tempTableItem.getItemId(), getCmbBrand(), 4)) {
										
										sql="update tbmedicinecompany set name='"+getCmbCompanyName()+"' where id='"+tempTableItem.getItemId()+"'";
										databaseHandler.execAction(sql);
										
										new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","Brand Name Edit Successfully...");

										tableDataLoad(listBrand, 4, "");

									}	
								}
								
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Brand Name!","This Brand Name All ready Exist In This Generic...\nPlease Enter Different Brand Name For This Generic..");
							cmbBrand.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid Generic Name!","Please Select a valid Generic Name");
						cmbGenericBrand.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Brand Name!","Please Enter Brand Name");
					cmbBrand.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Brand Name!","Please Select Any Brand Name");
				tableGroup.requestFocus();
			}
		
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void systemTableClickAction(MouseEvent event) {

		if(tableSystemName.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableSystemName.getSelectionModel().getSelectedItem();
			setCmbSystem(tempTableItem.getItemName());
			setCmbSystemDisease(tempTableItem.getItemName());
			setCmbSystemGeneric(tempTableItem.getItemName());
		}
	}

	@FXML
	private void diseaseTableClickAction(MouseEvent event) {

		if(tableDisease.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableDisease.getSelectionModel().getSelectedItem();

			setCmbDisease(tempTableItem.getItemName());
			setCmbDiseaseGroup(tempTableItem.getItemName());
			setCmbDiseaseGeneric(tempTableItem.getItemName());
			findHeadName(cmbSystemDisease,getCmbDisease(),1);
		}
	}

	@FXML
	private void groupTableClickAction(MouseEvent event) {

		if(tableGroup.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableGroup.getSelectionModel().getSelectedItem();

			setCmbGroup(tempTableItem.getItemName());
			setCmbGroupGeneric(tempTableItem.getItemName());
			findHeadName(cmbDiseaseGroup,getCmbGroup(),2);
			findHeadName(cmbSystemGroup,cmbDiseaseGroup.getEditor().getText(),1);
		}
	}

	@FXML
	private void genericTableClickAction(MouseEvent event) {

		if(tableGeneric.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempTableItem = tableGeneric.getSelectionModel().getSelectedItem();

			//tableDataLoad(listBrand, 4,tempTableItem.getItemName());
			setCmbGenericBrand(tempTableItem.getItemName());
			setCmbGeneric(tempTableItem.getItemName());
			findHeadName(cmbGroupGeneric,getCmbGeneric(),3);
			findHeadName(cmbDiseaseGeneric,cmbGroupGeneric.getEditor().getText(),2);
			findHeadName(cmbSystemGeneric,cmbDiseaseGeneric.getEditor().getText(),1);
		}
	}

	@FXML
	private void brandTableClickAction(MouseEvent event) {

		if(tableBrand.getSelectionModel().getSelectedItem() != null) {
			MedicineItemInfo tempTableItem = tableBrand.getSelectionModel().getSelectedItem();

			setCmbBrand(tempTableItem.getItemName());
			setCmbCompanyName(tempTableItem.getCompanyName());
			findHeadName(cmbGenericBrand,getCmbBrand(),4);
		}
	}

	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message);
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
	private void systemComboLoad() {
		try {
			cmbSystem.data.clear();
			cmbSystemDisease.data.clear();
			cmbSystemGroup.data.clear();
			cmbSystemGeneric.data.clear();
			
			cmbSystem.data.add("");
			cmbSystemDisease.data.add("");
			cmbSystemGroup.data.add("");
			cmbSystemGeneric.data.add("");
			
			String sql= "select * from tbmedicineGroup where type='"+NodeType.SYSTEM.getType()+"' group by groupname order by sn,groupname";
			String item;
			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				item = rs.getString("GroupName");
				cmbSystem.data.add(item);
				cmbSystemDisease.data.add(item);
				cmbSystemGroup.data.add(item);
				cmbSystemGeneric.data.add(item);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}
	
	private void diseaseComboLoad() {
		try {
			cmbDisease.data.clear();
			cmbDiseaseGroup.data.clear();
			cmbDiseaseGeneric.data.clear();
			
			cmbDisease.data.add("");
			cmbDiseaseGroup.data.add("");
			cmbDiseaseGeneric.data.add("");
			
			
			String sql= "select * from tbmedicineGroup where type='"+NodeType.DISEASE.getType()+"' group by groupname order by sn,groupname";
			String item;
			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				item = rs.getString("GroupName");
				cmbDisease.data.add(item);
				cmbDiseaseGroup.data.add(item);
				cmbDiseaseGeneric.data.add(item);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}
	private void tableDataLoad(ObservableList list,int parentType,int ChildType,String parentName) {
		String id=getId(parentName,parentType);

		//String type=String.valueOf(ChildType);

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
				list.add(new TableItemInfo(rs.getInt("sn"),rs.getString("id"),rs.getString("pId"),rs.getString("groupName"),ChildType));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}

		if(ChildType == 0) {
			tableSystemName.setItems(list);
		}else if(ChildType == 1) {
			tableDisease.setItems(list);
		}else if(ChildType == 2) {
			tableGroup.setItems(list);
		}else if(ChildType == 3) {
			tableGeneric.setItems(list);
		}else if(ChildType == 4) {
			tableBrand.setItems(list);
		}
	}

	private void tableDataLoad(ObservableList list,int type,String name) {
		try {
			String id=getId(name,type-1);


			//String childType= String.valueOf(type);

			list.clear();

			String sql="";

			if(type==4) {
				if(name.equals("")) {
					sql="select mg.sn,mg.id,mg.pId,mg.groupname,mc.Name from tbmedicineGroup mg join tbmedicinecompany mc on mg.id = mc.id where mg.type='"+type+"' group by groupname order by sn,groupname";
				}else {
					sql="select mg.sn,mg.id,mg.pId,mg.groupname,mc.Name from tbmedicineGroup mg join tbmedicinecompany mc on mg.id = mc.id where mg.type='"+type+"' and mg.pid='"+id+"' group by groupname order by sn,groupname";
				}


				ResultSet rs=databaseHandler.execQuery(sql);
				while(rs.next()) {
					
					list.add(new MedicineItemInfo(rs.getInt("sn"),rs.getString("id"),rs.getString("pId"),rs.getString("groupName"),rs.getString("Name"),"","","",NodeType.MEDICINE_BRAND.getType()));
				}
			}else {
				if(name.equals("")) {
					sql="select * from tbmedicineGroup where type='"+type+"' group by groupname order by sn,groupname";
				}else {
					sql="select * from tbmedicineGroup where type='"+type+"' and pid='"+id+"' group by groupname order by sn,groupname";
				}


				ResultSet rs= databaseHandler.execQuery(sql);
				while(rs.next()) {
					
					list.add(new TableItemInfo(rs.getInt("sn"),rs.getString("id"),rs.getString("pId"),rs.getString("groupName"),type));
				}
			}

			if(type == 0) {
				tableSystemName.setItems(list);
			}else if(type == 1) {
				tableDisease.setItems(list);
			}else if(type == 2) {
				tableGroup.setItems(list);
			}else if(type == 3) {
				tableGeneric.setItems(list);
			}else if(type == 4) {
				tableBrand.setItems(list);
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	/*public void systemTableLoad(){
		try {
			listSystemName.clear();
			String sql="Select * from tbmedicineGroup where type=0 group by groupname order by sn,groupname";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()){
				listSystemName.add(new TableItemInfo(rs.getString("Id"),rs.getString("groupName"), "0"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	private void companyNameComboLoad() {
		try {
			cmbCompanyName.data.clear();
			cmbCompanyName.data.add("");
			String sql= "select * from tbmedicinecompany group by name";

			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbCompanyName.data.add(rs.getString("name"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private boolean editAction(String id, String newName, int type) {

		try {
			sql = "update tbmedicinegroup set groupname= '"+newName+"' where id='"+id+"' and type="+type+"";
			return databaseHandler.execAction(sql);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	
	private boolean editAction(String prevItemId,String newItemId, String newName,String prevPId,String newPid, int type) {

		try {
			sql = "update tbmedicinegroup set groupname= '"+newName+"',pId='"+newPid+"',id='"+newItemId+"' where id='"+prevItemId+"' pId='"+prevPId+"' and type="+type+"";
			return databaseHandler.execAction(sql);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

	private boolean saveAction(String id,String itemName,String pId,int type) {
		try {
			String sql="insert into tbmedicinegroup (id,GroupName,pId,gpID,Type,sn) values('"+id+"','"+itemName+"',"+pId+",'0',"+type+",'"+id+"')";
			return databaseHandler.execAction(sql);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	
	public boolean deleteFromMedicineGroup(String id,String headId, int type) {
		try {
			if((!isHaveAnyChild(id)) || isMultipleId(id)) {
				if(confirmationCheck("Delete This Item?")) {
					String sql = "delete from tbmedicineGroup where id="+id+" and pId='"+headId+"' and type="+type+";";
					if( databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Delete Successfully...!","Item Successfully Deleted....");
						return true;
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Delete Can Not Successfull...!","Something Went Wrong....");
					}
				}
			}else {
				if(type==0) {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "It's Have Disease/Generic!","There are Disease Or Generic under this System Name...");
				}else if(type==1) {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "It's Have Group/Generic!","There are Group Or Generic under this Disease Name...");
				}else if(type==2) {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "It's Have Generic!","There are Generic under this Group Name...");
				}else if(type==3) {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "It's Have Brand!","There are Brand under this Generic Name...");
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
		
	}
	
	public void updateSerial(TableView<TableItemInfo> table,int type){
		if(confirmationCheck("Update Serial No?")){
			
				for(int i=0;i < table.getItems().size();i++){
					sql = "update tbMedicineGroup set sn = '"+i+"' where sn = '"+table.getItems().get(i).getSlNo()+"' and id = '"+table.getItems().get(i).getItemId()+"' and pId = '"+table.getItems().get(i).getHeadId()+"' and type="+type+" ;";
					databaseHandler.execAction(sql);
					
				}
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Update Successfully...!","Serial No Update Successfully....");
			
		}
	}
	public void updateSerial(TableView<MedicineItemInfo> table){
		if(confirmationCheck("Update Serial No?")){
				int type=NodeType.MEDICINE_BRAND.getType();
				for(int i=0;i < table.getItems().size();i++){
					sql = "update tbMedicineGroup set sn = '"+i+"' where sn = '"+table.getItems().get(i).getSlNo()+"' and id = '"+table.getItems().get(i).getItemId()+"' and pId = '"+table.getItems().get(i).getHeadId()+"' and type="+type+" ;";
					databaseHandler.execAction(sql);
					
				}
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Update Successfully...!","Serial No Update Successfully....");
			
		}
	}

	protected void findHeadName(FxComboBox combo, String name, int type) {
		try {
			sql= "select (select groupname from tbmedicinegroup where id= mg.pid group by groupname) as headname from tbmedicinegroup mg where groupname = '"+name+"' and type = "+type+"";
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

	private String getMaxId(){
		try {
			sql="select (select ifnull(max(id),0)+1)as GroupId from tbmedicineGroup ";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()){
				return rs.getString("groupId");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "0";
	}

	private boolean isHaveAnyChild(String pid) {
		try {
			String sql="Select * from tbmedicinegroup where pid="+pid+"";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	
	private boolean isMultipleId(String id) {
		try {
			String sql="Select count(*) as count from tbmedicinegroup where id="+id+"";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				if(rs.getInt("count")>2)return true; 
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

	private boolean duplicateItemChck(String Name,String ColumnName,String TableName) {
		try {
			String sql="select * from "+TableName+" where "+ColumnName+"='"+Name+"';";		
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private boolean duplicateItemCheck(String Name,String Pid,int type) {
		try {
			String sql="select * from tbmedicinegroup where GroupName='"+Name+"' and pid="+Pid+" and type="+type+" order by sn,groupname;";		
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private boolean duplicateItemCheck(String Name,int type) {
		try {
			String sql="select * from tbmedicinegroup where GroupName='"+Name+"' and type="+type+" order by sn,groupname;";		
			ResultSet rs=databaseHandler.execQuery(sql);
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
		cmbSystemDisease.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemDisease);

		});
		cmbSystemGroup.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemGroup);

		});
		cmbDiseaseGroup.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDiseaseGroup);

		});
		cmbSystemGeneric.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemGeneric);

		});
		cmbDiseaseGeneric.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDiseaseGeneric);

		});
		cmbGroupGeneric.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbGroupGeneric);

		});

		cmbGenericBrand.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbGenericBrand);

		});
		cmbSystem.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectCombboxIfFocused(cmbSystem);
		});

		cmbDisease.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectCombboxIfFocused(cmbDisease);
		});

		cmbGroup.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectCombboxIfFocused(cmbGroup);
		});

		cmbGeneric.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectCombboxIfFocused(cmbGeneric);
		});

		cmbBrand.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectCombboxIfFocused(cmbBrand);
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
		btnSaveSystem.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnSystemSaveAction(null); });
		
		btnSaveDisease.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnDiseaseSaveAction(null);});
		
		btnSaveGroup.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnGroupSaveAction(null); });
		
		btnSaveGeneric.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnGenericSaveAction(null); });
		
		btnSaveBrand.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnBrandSaveAction(null); });
		
		cmbSystemDisease.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {		
					tableDataLoad(listDisease, 1,getCmbSystemDisease());
					//tableDataLoad(listGeneric, 0,3,getCmbSystemDisease());
				}
			}    
		});

		cmbSystemGroup.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					comboBoxLoad( cmbDiseaseGroup , 1 , getCmbSystemGroup());
				}
			}    
		});

		cmbDiseaseGroup.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					tableDataLoad(listGroup, 2,getCmbDiseaseGroup());
					//tableDataLoad(listGeneric, 1,3,getCmbDiseaseGroup());
				}
			}    
		});

		cmbSystemGeneric.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					cmbDiseaseGeneric.getEditor().setText("");
					cmbGroupGeneric.getEditor().setText("");
					comboBoxLoad(cmbGroupGeneric, 2);
					comboBoxLoad( cmbDiseaseGeneric, 1, getCmbSystemGeneric());
					tableDataLoad(listGeneric, 0,3, getCmbSystemGeneric());			
				}
			}    
		});

		cmbDiseaseGeneric.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					cmbGroupGeneric.getEditor().setText("");
					comboBoxLoad(cmbGroupGeneric, 2, getCmbDiseaseGeneric());
					tableDataLoad(listGeneric, 1,3, getCmbDiseaseGeneric());

				}
			}    
		});

		cmbGroupGeneric.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					tableDataLoad(listGeneric, 3, getCmbGroupGeneric());

				}
			}    
		});

		cmbGenericBrand.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					tableDataLoad(listBrand, 4,getCmbGenericBrand());

				}
			}    
		});
	}



	private void setCmpData() {
		// TODO Auto-generated method stub
		tableSystemName.setColumnResizePolicy(tableSystemName.CONSTRAINED_RESIZE_POLICY);
		systemSlNoCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		systemNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableDisease.setColumnResizePolicy(tableDisease.CONSTRAINED_RESIZE_POLICY);
		diseaseSlNoCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		diseaseCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableGroup.setColumnResizePolicy(tableGroup.CONSTRAINED_RESIZE_POLICY);
		groupSlNoCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		groupCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableGeneric.setColumnResizePolicy(tableGeneric.CONSTRAINED_RESIZE_POLICY);
		genericSlNoCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		genericCol.setCellValueFactory(new PropertyValueFactory("itemName"));

		tableBrand.setColumnResizePolicy(tableBrand.CONSTRAINED_RESIZE_POLICY);
		brandSlNoCol.setCellValueFactory(new PropertyValueFactory("slNo"));
		brandNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		companyNameCol.setCellValueFactory(new PropertyValueFactory("companyName"));
		
		new TableItemRowFactory(tableSystemName);

		contextMenuTxtSystem = new CustomContextMenu(cmbSystem.getEditor());
		contextMenuSystemDisease = new CustomContextMenu(cmbSystemDisease.getEditor());
		contextMenuTxtDisease = new CustomContextMenu(cmbDisease.getEditor());
		contextMenuSystemGroup = new CustomContextMenu(cmbSystemGroup.getEditor());
		contextMenuDiseaseGroup = new CustomContextMenu(cmbDiseaseGroup.getEditor());
		contextMenuTxtGroup = new CustomContextMenu(cmbGroup.getEditor());
		contextMenuSystemGeneric = new CustomContextMenu(cmbSystemGeneric.getEditor());
		contextMenuDiseaseGeneric = new CustomContextMenu(cmbDiseaseGeneric.getEditor());
		contextMenuDiseaseGroup = new CustomContextMenu(cmbDiseaseGroup.getEditor());
		contextMenuTxtGeneric = new CustomContextMenu(cmbGeneric.getEditor());
		contextMenuGenericBrand = new CustomContextMenu(cmbGenericBrand.getEditor());
		contextMenuCompany = new CustomContextMenu(cmbCompanyName.getEditor());
		contextMenuTxtBrand = new CustomContextMenu(cmbBrand.getEditor());

		/*new MedicineCreateTableItemRowFactory(tableSystemName);
		new MedicineCreateTableItemRowFactory(tableDisease);
		new MedicineCreateTableItemRowFactory(tableGroup);
		new MedicineCreateTableItemRowFactory(tableGeneric);
		new MedicineCreateTableItemRowFactory(tableBrand);*/

		new TableItemRowFactory(tableSystemName);
		new TableItemRowFactory(tableDisease);
		new TableItemRowFactory(tableGroup);
		new TableItemRowFactory(tableGeneric);
		//new TableItemRowFactory(tableBrand);
		
		/*new MedicineCreateTableContextMenu(tableSystemName, listSystemName,"0");
		new MedicineCreateTableContextMenu(tableDisease, listDisease,"1");
		new MedicineCreateTableContextMenu(tableGroup, listGroup,"2");
		new MedicineCreateTableContextMenu(tableGeneric, listGeneric,"3");*/
		//new MedicineCreateTableContextMenu(tableBrand, listBrand,"4");
		
		new NewCustomContextMenu(NodeType.SYSTEM, tableSystemName, this);
		new NewCustomContextMenu(NodeType.DISEASE, tableDisease, this);
		new NewCustomContextMenu(NodeType.MEDICINE_GROUP, tableGroup, this);
		new NewCustomContextMenu(NodeType.GENERIC, tableGeneric, this);
		new NewCustomContextMenu(NodeType.MEDICINE_BRAND, tableBrand, this);
	}

	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control =  {cmbSystem,btnSaveSystem};
		new FocusMoveByEnter(control);

		Control[] control2 =  {cmbSystemDisease,cmbDisease,btnSaveDisease};
		new FocusMoveByEnter(control2);

		Control[] control3 =  {cmbSystemGroup,cmbDiseaseGroup,cmbGroup,btnSaveGroup};
		new FocusMoveByEnter(control3);

		Control[] control4 =  {cmbSystemGeneric,cmbDiseaseGeneric,cmbGroupGeneric,cmbGeneric,btnSaveGeneric};
		new FocusMoveByEnter(control4);

		Control[] control5 =  {cmbGenericBrand,cmbBrand,cmbCompanyName,btnSaveBrand};
		new FocusMoveByEnter(control5);

	}

	private void addCmp() {
		// TODO Auto-generated method stub
		vBoxSystemDisease.getChildren().clear();
		cmbSystemDisease.setPrefWidth(700);
		cmbSystemDisease.setPrefHeight(25);
		cmbSystemDisease.setPromptText("System Name");
		vBoxSystemDisease.getChildren().add(cmbSystemDisease);


		hBoxGroup.getChildren().clear();
		cmbSystemGroup.setPrefWidth(700);
		cmbSystemGroup.setPrefHeight(25);
		cmbSystemGroup.setPromptText("System Name");

		cmbDiseaseGroup.setPrefWidth(700);
		cmbDiseaseGroup.setPrefHeight(25);
		cmbDiseaseGroup.setPromptText("Disease Name");
		hBoxGroup.getChildren().addAll(cmbSystemGroup,cmbDiseaseGroup);


		hBoxGeneric.getChildren().clear();
		cmbSystemGeneric.setPrefWidth(700);
		cmbSystemGeneric.setPrefHeight(25);
		cmbSystemGeneric.setPromptText("System Name");

		cmbDiseaseGeneric.setPrefWidth(700);
		cmbDiseaseGeneric.setPrefHeight(25);
		cmbDiseaseGeneric.setPromptText("Disease Name");

		cmbGroupGeneric.setPrefWidth(700);
		cmbGroupGeneric.setPrefHeight(25);
		cmbGroupGeneric.setPromptText("Group Name");
		hBoxGeneric.getChildren().addAll(cmbSystemGeneric,cmbDiseaseGeneric,cmbGroupGeneric);


		vBoxGenericBrand.getChildren().clear();
		cmbGenericBrand.setPrefWidth(900);
		cmbGenericBrand.setPrefHeight(25);
		cmbGenericBrand.setPromptText("Generic Name");
		vBoxGenericBrand.getChildren().add(cmbGenericBrand);


		hBoxCompanyNameBrand.getChildren().remove(1);
		cmbCompanyName.setPrefWidth(180);
		cmbCompanyName.setPrefHeight(25);
		cmbCompanyName.setPromptText("Company Name");
		hBoxCompanyNameBrand.getChildren().add(1,cmbCompanyName);
		
		hBoxSystem.getChildren().remove(0);
		cmbSystem.setPrefWidth(300);
		cmbSystem.setPrefHeight(28);
		cmbSystem.setPromptText("System Name");
		hBoxSystem.getChildren().add(0,cmbSystem);
		
		hBoxDisease2.getChildren().remove(0);
		cmbDisease.setPrefWidth(300);
		cmbDisease.setPrefHeight(28);
		cmbDisease.setPromptText("DiseaseName Name");
		hBoxDisease2.getChildren().add(0,cmbDisease);
		
		hBoxGroup2.getChildren().remove(0);
		cmbGroup.setPrefWidth(432);
		cmbGroup.setPrefHeight(28);
		cmbGroup.setPromptText("Medicine Group Name");
		hBoxGroup2.getChildren().add(0,cmbGroup);
		
		
		hBoxGeneric2.getChildren().remove(0);
		cmbGeneric.setPrefWidth(432);
		cmbGeneric.setPrefHeight(28);
		cmbGeneric.setPromptText("Generic Name");
		hBoxGeneric2.getChildren().add(0,cmbGeneric);
		
		hBoxCompanyNameBrand.getChildren().remove(0);
		cmbBrand.setPrefWidth(388);
		cmbBrand.setPrefHeight(28);
		cmbBrand.setPromptText("Brand Name");
		hBoxCompanyNameBrand.getChildren().add(0,cmbBrand);
	}

	public class TableItem{

		private SimpleIntegerProperty slNo;
		private SimpleStringProperty id;
		private SimpleStringProperty parentId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty type;
		private SimpleStringProperty companyName;

		public TableItem(String id,String itemName,String type) {
			this.id = new SimpleStringProperty(id);
			this.parentId = new SimpleStringProperty("0");
			this.itemName = new SimpleStringProperty(itemName);
			this.type = new SimpleStringProperty(type);
			this.companyName = new SimpleStringProperty("");
		}

		public TableItem(int slNo,String id,String parentId,String itemName,String type) {
			this.slNo = new SimpleIntegerProperty(slNo);
			this.id = new SimpleStringProperty(id);
			this.parentId = new SimpleStringProperty(parentId);
			this.itemName = new SimpleStringProperty(itemName);
			this.type = new SimpleStringProperty(type);
			this.companyName = new SimpleStringProperty("");
		}

		public TableItem(int slNo,String id,String parentId,String itemName,String type,String companyName) {
			this.slNo = new SimpleIntegerProperty(slNo);
			this.id = new SimpleStringProperty(id);
			this.parentId = new SimpleStringProperty(parentId);
			this.itemName = new SimpleStringProperty(itemName);
			this.type = new SimpleStringProperty(type);
			this.companyName = new SimpleStringProperty(companyName);
		}
		
		public String getSlNo() {
			return String.valueOf(slNo.get());
		}

		public void setSlNo(int slNo) {
			this.slNo = new SimpleIntegerProperty(slNo);
		}
		
		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getParentId() {
			return parentId.get();
		}

		public void setParentId(String parentId) {
			this.parentId = new SimpleStringProperty(parentId);
		}

		public String getItemName() {
			return itemName.get();
		}

		public void setItemName(String itemName) {
			this.itemName = new SimpleStringProperty(itemName);
		}

		public String getType() {
			return type.get();
		}

		public void setType(String type) {
			this.type = new SimpleStringProperty(type);
		}

		public String getCompanyName() {
			return companyName.get();
		}

		public void setCompanyName(String companyName) {
			this.companyName = new SimpleStringProperty(companyName);
		}


	}

	public String getCmbSystemDisease() {
		if(cmbSystemDisease.getValue() != null)
			return cmbSystemDisease.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystemDisease(String cmbSystemDisease) {
		this.cmbSystemDisease.setValue(cmbSystemDisease);
	}

	public String getCmbSystemGroup() {
		if(cmbSystemGroup.getValue() != null)
			return cmbSystemGroup.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystemGroup(String cmbSystemGroup) {
		this.cmbSystemGroup.setValue(cmbSystemGroup);
	}

	public String getCmbDiseaseGroup() {
		if(cmbDiseaseGroup.getValue() != null)
			return cmbDiseaseGroup.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbDiseaseGroup(String cmbDiseaseGroup) {
		this.cmbDiseaseGroup.setValue(cmbDiseaseGroup);
	}

	public String getCmbSystemGeneric() {
		if(cmbSystemGeneric.getValue() != null)
			return cmbSystemGeneric.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbSystemGeneric(String cmbSystemGeneric) {
		this.cmbSystemGeneric.setValue(cmbSystemGeneric);
	}

	public String getCmbDiseaseGeneric() {
		if(cmbDiseaseGeneric.getValue() != null)
			return cmbDiseaseGeneric.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbDiseaseGeneric(String cmbDiseaseGeneric) {
		this.cmbDiseaseGeneric.setValue(cmbDiseaseGeneric);
	}

	public String getCmbGroupGeneric() {
		if(cmbGroupGeneric.getValue() != null)
			return cmbGroupGeneric.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbGroupGeneric(String cmbGroupGeneric) {
		this.cmbGroupGeneric.setValue(cmbGroupGeneric);
	}

	public String getCmbGenericBrand() {
		if(cmbGenericBrand.getValue() != null)
			return cmbGenericBrand.getValue().toString().replace("'", "''").trim();
		else return "";
	}

	public void setCmbGenericBrand(String cmbGenericBrand) {
		this.cmbGenericBrand.setValue(cmbGenericBrand);
	}

	public String getCmbCompanyName() {
		if(cmbCompanyName.getValue() != null)
			return cmbCompanyName.getValue().toString().replace("'", "''").trim();
		else return "";

	}

	public void setCmbCompanyName(String cmbCompanyName) {
		this.cmbCompanyName.setValue(cmbCompanyName);
	}

	public String getCmbSystem() {
		return cmbSystem.getValue().toString().replace("'", "''").trim();
	}

	public void setCmbSystem(String cmbSystem) {
		this.cmbSystem.setValue(cmbSystem);
	}

	public String getCmbDisease() {
		return cmbDisease.getValue().toString().replace("'", "''").trim();
	}

	public void setCmbDisease(String cmbDisease) {
		this.cmbDisease.setValue(cmbDisease);
	}

	public String getCmbGroup() {
		return cmbGroup.getValue().toString().replace("'", "''").trim();
	}

	public void setCmbGroup(String cmbGroup) {
		this.cmbGroup.setValue(cmbGroup);
	}

	public String getCmbGeneric() {
		return cmbGeneric.getValue().toString().replace("'", "''").trim();
	}

	public void setCmbGeneric(String cmbGeneric) {
		this.cmbGeneric.setValue(cmbGeneric);
	}

	public String getCmbBrand() {
		return cmbBrand.getValue().toString().replace("'", "''").trim();
	}

	public void setCmbBrand(String cmbBrand) {
		this.cmbBrand.setValue(cmbBrand);
	}
	

	

}
