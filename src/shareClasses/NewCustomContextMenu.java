package shareClasses;

import javax.swing.JOptionPane;

import impl.org.controlsfx.tools.rectangle.change.NewChangeStrategy;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import prescription.main.MainFrameController;
import prescription.setup.MedicineCreateController;
import shareClasses.LoadedInfo.MedicineItemInfo;


import shareClasses.LoadedInfo.TableItemInfo;

public class NewCustomContextMenu extends ContextMenu{

	MenuItem menuRefresh = new MenuItem("Refresh");
	MenuItem menuDelete = new MenuItem("Delete");
	MenuItem menuUpdateSl = new MenuItem("Update Sl No");	
	MenuItem menuAddToPrescribed = new MenuItem("Add To Prescribed");
	MenuItem menuSaveAs;
	MenuItem menuEdit;
	MenuItem menuAddDisease;
	MenuItem menuAddMedicineGroup;
	MenuItem menuAddGeneric;
	MenuItem menuAddBrand;
	MenuItem menuAddCheapComplain;
	MenuItem menuAddInvestigation;
	MenuItem menuAddAdvise;

	MenuItem menuShowSuggestQues;

	NodeType nodeType;
	TextField txt;
	TextArea txtArea;
	TableView<TableItemInfo> table;
	TableView<MedicineItemInfo> medicineTable;
	public static MainFrameController mainController;
	public MedicineCreateController medicineCreateController;

	public NewCustomContextMenu(NodeType nodeType,TextArea txt,MainFrameController mainController) {
		this.txtArea = txt;
		this.mainController = mainController;
		this.nodeType = nodeType;
		switch(nodeType){

		case ADVISE:
			this.menuSaveAs = new MenuItem("Save As Advise");
			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuSaveAs);

			menuAddToPrescribed.setOnAction(e->{
				if(!this.txtArea.getText().trim().isEmpty()){
					if(LoadedInfo.getAdviseInfo(txt.getText().trim()) != null){
						mainController.addPrescribedAdvise(LoadedInfo.getAdviseInfo(txt.getText().trim()));
					}else{
						mainController.addPrescribedAdvise(new TableItemInfo(0, "0", "0", txt.getText().trim(), NodeType.ADVISE.getType()));
					}
				}
			});
			menuRefresh.setOnAction(e->{
				this.txtArea.setText("");
				this.mainController.tableAdvise.setItems(LoadedInfo.getAdviseList());
			});

			menuSaveAs.setOnAction(e->{
				if(mainController.tableDisease.getSelectionModel().getSelectedItem() != null){
					if(!this.txtArea.getText().trim().isEmpty()){
						if(LoadedInfo.getAdviseInfo(txt.getText().trim()) == null){
							DialogSaveAsNew dialog = new DialogSaveAsNew(nodeType, mainController.tableDisease.getSelectionModel().getSelectedItem().getItemId());

							dialog.setTxtParentsName(mainController.tableDisease.getSelectionModel().getSelectedItem().getItemName());
							dialog.setTxtAreaNewName(txt.getText().trim());
							dialog.show();
						}else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Advise..!","This Advise already exist... \nPlease Enter New Investigation Name..");
							txt.requestFocus();
						}
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Advise Name..!","Please Enter a Advise Name..");
						txt.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select A Disease Name..!","Please Select a Disease Name..");
					mainController.tableDisease.requestFocus();
				}
			});

			txt.setContextMenu(this);
			break;
		}


		this.txtArea.setContextMenu(this);

	}
	public NewCustomContextMenu(NodeType nodeType,TextField txt,MainFrameController mainController) {
		this.txt = txt;
		this.mainController = mainController;
		this.nodeType = nodeType;
		switch(nodeType){
		case SYSTEM:
			this.menuSaveAs = new MenuItem("Save As System");
			this.getItems().addAll(menuRefresh,new SeparatorMenuItem(),menuSaveAs);


			menuRefresh.setOnAction(e->{
				this.txt.setText("");
				this.mainController.tableSystem.setItems(LoadedInfo.getSystemList());
			});
			menuSaveAs.setOnAction(e->{
				if(!this.txt.getText().trim().isEmpty()){

					DialogSaveAsNew dialog = new DialogSaveAsNew(nodeType, "0");
					dialog.setTxtNewName(txt.getText().trim());
					dialog.show();

				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty System Name..!","Please Enter a System Name..");
					txt.requestFocus();
				}
			});

			txt.setContextMenu(this);
			break;
		case DISEASE:
			this.menuSaveAs = new MenuItem("Save As Disease");
			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuSaveAs);

			menuAddToPrescribed.setOnAction(e->{
				if(!this.txt.getText().trim().isEmpty()){
					if(LoadedInfo.getDiseaseInfo(txt.getText().trim()) != null){
						mainController.addPrescribedDiseaseItems(LoadedInfo.getDiseaseInfo(txt.getText().trim()));

					}else{
						mainController.addPrescribedDiseaseItems(new TableItemInfo(0, "0", "0", txt.getText().trim(), NodeType.DISEASE.getType()));
					}
				}
			});
			menuRefresh.setOnAction(e->{
				this.txt.setText("");
				this.mainController.tableDisease.setItems(LoadedInfo.getDiseaseList());
			});

			menuSaveAs.setOnAction(e->{
				if(mainController.tableSystem.getSelectionModel().getSelectedItem() != null){
					if(!this.txt.getText().trim().isEmpty()){

						DialogSaveAsNew dialog = new DialogSaveAsNew(nodeType, mainController.tableSystem.getSelectionModel().getSelectedItem().getItemId());

						dialog.setTxtParentsName(mainController.tableSystem.getSelectionModel().getSelectedItem().getItemName());
						dialog.setTxtNewName(txt.getText().trim());
						dialog.show();

					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Disease Name..!","Please Enter a disease Name..");
						txt.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select A System Name..!","Please Select a System Name..");
					mainController.tableSystem.requestFocus();
				}
			});

			txt.setContextMenu(this);
			break;
		case MEDICINE_GROUP:
			this.menuSaveAs = new MenuItem("Save As Medicine Group");
			this.getItems().addAll(menuRefresh,new SeparatorMenuItem(),menuSaveAs);


			menuRefresh.setOnAction(e->{
				this.txt.setText("");
				this.mainController.tableMedicineGroup.setItems(LoadedInfo.getMedicineGroupList());
			});

			menuSaveAs.setOnAction(e->{
				if(mainController.tableDisease.getSelectionModel().getSelectedItem() != null){
					if(!this.txt.getText().trim().isEmpty()){

						DialogSaveAsNew dialog = new DialogSaveAsNew(nodeType, mainController.tableDisease.getSelectionModel().getSelectedItem().getItemId());

						dialog.setTxtParentsName(mainController.tableDisease.getSelectionModel().getSelectedItem().getItemName());
						dialog.setTxtNewName(txt.getText().trim());
						dialog.show();

					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Medicine Group Name..!","Please Enter a Medicine Group Name..");
						txt.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select A Disease Name..!","Please Select a Disease Name..");
					mainController.tableDisease.requestFocus();
				}
			});

			txt.setContextMenu(this);
			break;
		case GENERIC:
			this.menuSaveAs = new MenuItem("Save As Generic");
			this.getItems().addAll(menuRefresh,new SeparatorMenuItem(),menuSaveAs);

			menuRefresh.setOnAction(e->{
				this.txt.setText("");
				this.mainController.tableGeneric.setItems(LoadedInfo.getGenericList());
			});

			menuSaveAs.setOnAction(e->{
				if(mainController.tableMedicineGroup.getSelectionModel().getSelectedItem() != null){
					if(!this.txt.getText().trim().isEmpty()){

						DialogSaveAsNew dialog = new DialogSaveAsNew(nodeType, mainController.tableMedicineGroup.getSelectionModel().getSelectedItem().getItemId());

						dialog.setTxtParentsName(mainController.tableMedicineGroup.getSelectionModel().getSelectedItem().getItemName());
						dialog.setTxtNewName(txt.getText().trim());
						dialog.show();

					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Generic Name..!","Please Enter a Generic Name..");
						txt.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select A Medicine Group Name..!","Please Select a Medicine Group Name..");
					mainController.tableMedicineGroup.requestFocus();
				}
			});

			txt.setContextMenu(this);
			break;
		case MEDICINE_BRAND:
			this.menuSaveAs = new MenuItem("Save As Brand");
			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuSaveAs);

			menuAddToPrescribed.setOnAction(e->{
				if(!this.txt.getText().trim().isEmpty()){
					if(LoadedInfo.getMedicineBrandInfo(txt.getText().trim()) != null){
						MedicineItemInfo temp = LoadedInfo.getMedicineBrandInfo(txt.getText()); 						
						mainController.addPrescribedMedicine(new MedicineItemInfo(0, temp.getItemId(), temp.getHeadId(), temp.getItemName(), temp.companyName, mainController.getCmbSchedule(), mainController.getCmbTime(), mainController.getCmbCourse(), temp.type));
					}else{
						mainController.addPrescribedMedicine(new MedicineItemInfo(0, "0", "0", txt.getText().trim(),mainController.getCmbCompanyName(),mainController.getCmbSchedule(),mainController.getCmbTime(),mainController.getCmbCourse(), NodeType.MEDICINE_BRAND.getType()));
					}
				}
			});

			menuRefresh.setOnAction(e->{
				this.txt.setText("");
				this.mainController.setCmbSchedule("");
				this.mainController.setCmbTime("");
				this.mainController.setCmbCourse("");
				this.mainController.setCmbCompanyName("");
				this.mainController.tableMedicineBrand.setItems(LoadedInfo.getMedicineBrandList());
			});

			menuSaveAs.setOnAction(e->{
				if(mainController.tableGeneric.getSelectionModel().getSelectedItem() != null){
					if(!this.txt.getText().trim().isEmpty()){
						if(!mainController.getCmbCompanyName().isEmpty()){
							DialogSaveAsNew dialog = new DialogSaveAsNew(nodeType, mainController.tableGeneric.getSelectionModel().getSelectedItem().getItemId());

							dialog.setTxtParentsName(mainController.tableGeneric.getSelectionModel().getSelectedItem().getItemName());
							dialog.setTxtNewName(txt.getText().trim());
							dialog.setCompanyName(mainController.getCmbCompanyName());
							dialog.show();
						}else{
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Company Name..!","Please Enter a Company Name..");
							
						}
					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Medicine Brand Name..!","Please Enter a Medcine Brand Name..");
						txt.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select A Generic Name..!","Please Select a Generic Name..");
					mainController.tableGeneric.requestFocus();
				}
			});

			break;
		case CHEAP_COMPLAIN:
			this.menuSaveAs = new MenuItem("Save As Cheap-Complain");
			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuSaveAs);

			menuAddToPrescribed.setOnAction(e->{
				if(!this.txt.getText().trim().isEmpty()){
					if(LoadedInfo.getCheapComplainInfo(txt.getText().trim()) != null){
						mainController.addPrescribedCheapComplainItems(LoadedInfo.getCheapComplainInfo(txt.getText().trim()));

					}else{
						mainController.addPrescribedCheapComplainItems(new TableItemInfo(0, "0", "0", txt.getText().trim(), NodeType.CHEAP_COMPLAIN.getType()));
					}
				}
			});
			menuRefresh.setOnAction(e->{
				this.txt.setText("");
				this.mainController.tableCheapComplain.setItems(LoadedInfo.getCheapComplainList());
			});

			menuSaveAs.setOnAction(e->{
				if(mainController.tableSystem.getSelectionModel().getSelectedItem() != null){
					if(!this.txt.getText().trim().isEmpty()){

						DialogSaveAsNew dialog = new DialogSaveAsNew(nodeType, mainController.tableSystem.getSelectionModel().getSelectedItem().getItemId());

						dialog.setTxtParentsName(mainController.tableSystem.getSelectionModel().getSelectedItem().getItemName());
						dialog.setTxtNewName(txt.getText().trim());
						dialog.show();

					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Cheap Complain Name..!","Please Enter a Cheap Complain Name..");
						txt.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select A System Name..!","Please Select a System Name..");
					mainController.tableSystem.requestFocus();
				}
			});

			txt.setContextMenu(this);
			break;
		case INVESTIGATION:

			this.menuSaveAs = new MenuItem("Save As Investigation");
			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuSaveAs);

			menuAddToPrescribed.setOnAction(e->{
				if(!this.txt.getText().trim().isEmpty()){
					if(LoadedInfo.getInvestigationInfo(txt.getText().trim()) != null){
						mainController.addPrescribedInvestigation(LoadedInfo.getInvestigationInfo(txt.getText().trim()));
					}else{
						mainController.addPrescribedInvestigation(new TableItemInfo(0, "0", "0", txt.getText().trim(), NodeType.INVESTIGATION.getType()));
					}
				}
			});
			menuRefresh.setOnAction(e->{
				this.txt.setText("");
				this.mainController.tableInvestigation.setItems(LoadedInfo.getInvestigationList());
			});

			menuSaveAs.setOnAction(e->{
				if(mainController.tableDisease.getSelectionModel().getSelectedItem() != null){
					if(!this.txt.getText().trim().isEmpty()){

						DialogSaveAsNew dialog = new DialogSaveAsNew(nodeType, mainController.tableDisease.getSelectionModel().getSelectedItem().getItemId());

						dialog.setTxtParentsName(mainController.tableDisease.getSelectionModel().getSelectedItem().getItemName());
						dialog.setTxtNewName(txt.getText().trim());
						dialog.show();

					}else{
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Investigation Name..!","Please Enter a Investigation Name..");
						txt.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select A Disease Name..!","Please Select a Disease Name..");
					mainController.tableDisease.requestFocus();
				}
			});

			txt.setContextMenu(this);
			break;

		}


		this.txt.setContextMenu(this);

	}

	public NewCustomContextMenu(NodeType nodeType,TableView table,MainFrameController mainController) {
		this.table = table;
		this.mainController = mainController;
		this.nodeType = nodeType;
		switch(nodeType){
		case SYSTEM:
			this.menuAddDisease = new MenuItem("Add Disease");
			this.menuAddGeneric = new MenuItem("Add Generic");
			this.menuAddCheapComplain = new MenuItem("Add Cheap-Complain");
			this.menuEdit = new MenuItem("Edit System Name");
			this.getItems().addAll(menuRefresh,new SeparatorMenuItem(),menuAddDisease,menuAddGeneric,menuAddCheapComplain,new SeparatorMenuItem(),menuEdit);

			menuRefresh.setOnAction(e->{
				this.mainController.setTxtSystemName("");
				table.setItems(LoadedInfo.getSystemList());
			});

			menuAddDisease.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					DialogAddChild dialog = new DialogAddChild(NodeType.DISEASE,((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemId());
					dialog.setTxtParentsName(((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemName());
					dialog.setCmbChildNameData(LoadedInfo.getDiseaseDataList());
					dialog.show();
				}
			});

			menuAddGeneric.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					DialogAddChild dialog = new DialogAddChild(NodeType.GENERIC,((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemId());
					dialog.setTxtParentsName(((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemName());
					dialog.setCmbChildNameData(LoadedInfo.getGenericDataList());
					dialog.show();
				}
			});

			menuAddCheapComplain.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					DialogAddChild dialog = new DialogAddChild(NodeType.CHEAP_COMPLAIN,((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemId());
					dialog.setTxtParentsName(((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemName());
					dialog.show();
				}
			});

			menuEdit.setOnAction(e ->{
				if(table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = (TableItemInfo)table.getSelectionModel().getSelectedItem();
					DialogEdit dialog = new DialogEdit(nodeType,temp.getItemId(),temp.getHeadId());
					dialog.setTxtNewItemName(temp.getItemName());
					dialog.show();
				}
			});

			this.table.setContextMenu(this);
			break;
		case DISEASE:

			menuAddMedicineGroup = new MenuItem("Add Medicine Group");
			menuAddGeneric = new MenuItem("Add Generic");
			menuAddInvestigation = new MenuItem("Add Investigation");
			menuAddAdvise = new MenuItem("Add Advise");
			menuEdit = new MenuItem("Edit Disease Name");
			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuAddMedicineGroup,menuAddGeneric,menuAddInvestigation,menuAddAdvise,new SeparatorMenuItem(),menuEdit);

			menuAddToPrescribed.setOnAction(e->{				
				if(table.getSelectionModel().getSelectedItem() != null){
					mainController.addPrescribedDiseaseItems((TableItemInfo)table.getSelectionModel().getSelectedItem());						
				}			
			});
			menuRefresh.setOnAction(e->{
				this.mainController.setTxtDiseaseName("");;
				this.table.setItems(LoadedInfo.getDiseaseList());
			});

			menuAddMedicineGroup.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					DialogAddChild dialog = new DialogAddChild(NodeType.MEDICINE_GROUP,((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemId());
					dialog.setTxtParentsName(((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemName());
					dialog.setCmbChildNameData(LoadedInfo.getMedicineBrandDataList());
					dialog.show();
				}
			});

			menuAddGeneric.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					DialogAddChild dialog = new DialogAddChild(NodeType.GENERIC,((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemId());
					dialog.setTxtParentsName(((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemName());
					dialog.setCmbChildNameData(LoadedInfo.getGenericDataList());
					dialog.show();
				}
			});

			menuAddInvestigation.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					DialogAddChild dialog = new DialogAddChild(NodeType.INVESTIGATION,((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemId());
					dialog.setTxtParentsName(((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemName());
					dialog.setCmbChildNameData(LoadedInfo.getInvestigationDataList());
					dialog.show();
				}
			});
			menuAddAdvise.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					DialogAddChild dialog = new DialogAddChild(NodeType.ADVISE,((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemId());
					dialog.setTxtParentsName(((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemName());
					dialog.setCmbChildNameData(LoadedInfo.getAdviseDataList());
					dialog.show();
				}
			});

			menuEdit.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = (TableItemInfo)table.getSelectionModel().getSelectedItem();
					DialogEdit dialog = new DialogEdit(nodeType,temp.getItemId(),temp.getHeadId());
					dialog.setTxtNewItemName(temp.getItemName());
					dialog.show();
				}
			});
			this.table.setContextMenu(this);
			break;
		case MEDICINE_GROUP:

			menuAddGeneric = new MenuItem("Add Generic");

			menuEdit = new MenuItem("Edit Medicine Group Name");
			this.getItems().addAll(menuRefresh,new SeparatorMenuItem(),menuAddGeneric,new SeparatorMenuItem(),menuEdit);


			menuRefresh.setOnAction(e->{
				this.mainController.setTxtMedicineGroup("");;
				table.setItems(LoadedInfo.getMedicineGroupList());
			});

			menuAddGeneric.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					DialogAddChild dialog = new DialogAddChild(NodeType.GENERIC,((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemId());
					dialog.setTxtParentsName(((TableItemInfo)table.getSelectionModel().getSelectedItem()).getItemName());
					dialog.setCmbChildNameData(LoadedInfo.getGenericDataList());
					dialog.show();
				}
			});


			menuEdit.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = (TableItemInfo)table.getSelectionModel().getSelectedItem();
					DialogEdit dialog = new DialogEdit(nodeType,temp.getItemId(),temp.getHeadId());
					dialog.setTxtNewItemName(temp.getItemName());
					dialog.show();
				}
			});
			this.table.setContextMenu(this);
			break;

		case GENERIC:

			menuEdit = new MenuItem("Edit Generic Name");
			menuAddBrand = new MenuItem("Add Brand");
			this.getItems().addAll(menuRefresh,new SeparatorMenuItem(),menuEdit);


			menuRefresh.setOnAction(e->{
				this.mainController.setTxtGeneric("");
				this.table.setItems(LoadedInfo.getGenericList());
			});


			menuEdit.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = (TableItemInfo)table.getSelectionModel().getSelectedItem();
					DialogEdit dialog = new DialogEdit(nodeType,temp.getItemId(),temp.getHeadId());
					dialog.setTxtNewItemName(temp.getItemName());
					dialog.show();
				}
			});
			this.table.setContextMenu(this);
			break;
		case MEDICINE_BRAND:
			this.medicineTable = table;
			menuEdit = new MenuItem("Edit Brand Name");
			menuAddBrand = new MenuItem("Add Brand");
			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuEdit);


			menuAddToPrescribed.setOnAction(e->{				
				if(table.getSelectionModel().getSelectedItem() != null){
					mainController.addPrescribedMedicine((MedicineItemInfo)table.getSelectionModel().getSelectedItem());						
				}			
			});

			menuRefresh.setOnAction(e->{
				this.mainController.setTxtMedicineBrand("");
				this.mainController.setCmbSchedule("");
				this.mainController.setCmbTime("");
				this.mainController.setCmbCourse("");
				this.mainController.setCmbCompanyName("");
				this.medicineTable.setItems(LoadedInfo.getMedicineBrandList());
			});


			menuEdit.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					MedicineItemInfo temp = (MedicineItemInfo)table.getSelectionModel().getSelectedItem();
					DialogEdit dialog = new DialogEdit(nodeType,temp.getItemId(),temp.getHeadId());
					dialog.setTxtNewItemName(temp.getItemName());
					dialog.show();
				}
			});
			this.medicineTable.setContextMenu(this);
			break;
		case CHEAP_COMPLAIN:

			menuEdit = new MenuItem("Edit Cheap-Complain");
			menuShowSuggestQues = new MenuItem("Show Suggested Question");
			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuShowSuggestQues,new SeparatorMenuItem(),menuEdit);

			menuAddToPrescribed.setOnAction(e->{				
				if(table.getSelectionModel().getSelectedItem() != null){
					mainController.addPrescribedCheapComplainItems((TableItemInfo)table.getSelectionModel().getSelectedItem());						
				}			
			});
			menuRefresh.setOnAction(e->{
				this.mainController.setTxtCheapComplain("");;
				this.table.setItems(LoadedInfo.getCheapComplainList());
			});

			menuShowSuggestQues.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = ((TableItemInfo)table.getSelectionModel().getSelectedItem());
					this.mainController.stageCcSuggest.setList(temp.getItemId());
					this.mainController.stageCcSuggest.setCheapComplainId(temp.getItemId());
					this.mainController.stageCcSuggest.setCheapComplain(temp.getItemName());
					this.mainController.stageCcSuggest.setList(temp.getItemId());
					this.mainController.stageCcSuggest.show();
				}
				
			});

			menuEdit.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = (TableItemInfo)table.getSelectionModel().getSelectedItem();
					DialogEdit dialog = new DialogEdit(nodeType,temp.getItemId(),temp.getHeadId());
					dialog.setTxtNewItemName(temp.getItemName());
					dialog.show();
				}
			});
			this.table.setContextMenu(this);
			break;
		case INVESTIGATION:

			menuEdit = new MenuItem("Edit Investigation");

			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuEdit);

			menuAddToPrescribed.setOnAction(e->{				
				if(table.getSelectionModel().getSelectedItem() != null){
					mainController.addPrescribedInvestigation((TableItemInfo)table.getSelectionModel().getSelectedItem());						
				}			
			});
			menuRefresh.setOnAction(e->{
				this.mainController.setTxtInvestigation("");
				this.table.setItems(LoadedInfo.getInvestigationList());
			});

			menuEdit.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = (TableItemInfo)table.getSelectionModel().getSelectedItem();
					DialogEdit dialog = new DialogEdit(nodeType,temp.getItemId(),temp.getHeadId());
					dialog.setTxtNewItemName(temp.getItemName());
					dialog.show();
				}
			});
			this.table.setContextMenu(this);
			break;
		case ADVISE:

			menuEdit = new MenuItem("Edit Addvise");

			this.getItems().addAll(menuAddToPrescribed,new SeparatorMenuItem(),menuRefresh,new SeparatorMenuItem(),menuEdit);

			menuAddToPrescribed.setOnAction(e->{				
				if(table.getSelectionModel().getSelectedItem() != null){
					mainController.addPrescribedAdvise((TableItemInfo)table.getSelectionModel().getSelectedItem());						
				}			
			});
			menuRefresh.setOnAction(e->{
				this.mainController.setTxtAdvise("");;
				this.table.setItems(LoadedInfo.getAdviseList());
			});

			menuEdit.setOnAction(e->{
				if(table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = (TableItemInfo)table.getSelectionModel().getSelectedItem();
					DialogEdit dialog = new DialogEdit(nodeType,temp.getItemId(),temp.getHeadId());
					dialog.setTxtAreaNewItemName(temp.getItemName());
					dialog.show();
				}
			});
			this.table.setContextMenu(this);
			break;

		}




	}


	public NewCustomContextMenu(NodeType nodeType,TableView table,MedicineCreateController medicineCreateController) {
		this.table = table;
		this.medicineCreateController = medicineCreateController;
		this.nodeType = nodeType;
		this.getItems().addAll(menuRefresh,new SeparatorMenuItem(),menuDelete,new SeparatorMenuItem(),menuUpdateSl);
		switch(nodeType){
		
		case SYSTEM:			

			menuRefresh.setOnAction(e->{
				this.medicineCreateController.setCmbSystem("");
				table.setItems(LoadedInfo.getSystemList());
				table.refresh();
			});
			menuUpdateSl.setOnAction(e->{
				this.medicineCreateController.updateSerial(table, nodeType.type);
			});
			
			menuDelete.setOnAction(e->{
				if(this.table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = this.table.getSelectionModel().getSelectedItem();
					
					if(this.medicineCreateController.deleteFromMedicineGroup(temp.getItemId(), temp.getHeadId(), nodeType.getType())){
						LoadedInfo.loadSystemInfo();
					}
				}
			});

			this.table.setContextMenu(this);
			break;
		case DISEASE:

			
			menuRefresh.setOnAction(e->{
				//this.medicineCreateController.setTxtDiseaseName("");;
				this.medicineCreateController.setCmbSystemDisease("");
				this.medicineCreateController.setCmbDisease("");
				this.table.setItems(LoadedInfo.getDiseaseList());
				this.table.refresh();
			});
			
			menuUpdateSl.setOnAction(e->{
				this.medicineCreateController.updateSerial(table, nodeType.type);
			});
			
			menuDelete.setOnAction(e->{
				if(this.table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = this.table.getSelectionModel().getSelectedItem();
					this.medicineCreateController.deleteFromMedicineGroup(temp.getItemId(), temp.getHeadId(), nodeType.getType());
				}
			});

			
			this.table.setContextMenu(this);
			break;
		case MEDICINE_GROUP:

		
			menuRefresh.setOnAction(e->{
				//this.medicineCreateController.setTxtMedicineGroup("");;
				this.medicineCreateController.setCmbSystemGroup("");
				this.medicineCreateController.setCmbDiseaseGroup("");
				this.medicineCreateController.setCmbGroup("");
				this.table.setItems(LoadedInfo.getMedicineGroupList());
				this.table.refresh();
			});

			menuUpdateSl.setOnAction(e->{
				this.medicineCreateController.updateSerial(table, nodeType.type);
			});
			
			menuDelete.setOnAction(e->{
				if(this.table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = this.table.getSelectionModel().getSelectedItem();
					this.medicineCreateController.deleteFromMedicineGroup(temp.getItemId(), temp.getHeadId(), nodeType.getType());
				}
			});
			this.table.setContextMenu(this);
			break;

		case GENERIC:

			menuRefresh.setOnAction(e->{
				this.medicineCreateController.setCmbSystemGeneric("");
				this.medicineCreateController.setCmbDiseaseGeneric("");
				this.medicineCreateController.setCmbGroupGeneric("");
				this.medicineCreateController.setCmbGeneric("");
				this.table.setItems(LoadedInfo.getGenericList());
				table.refresh();
			});
			menuUpdateSl.setOnAction(e->{
				this.medicineCreateController.updateSerial(table, nodeType.type);
			});
			
			menuDelete.setOnAction(e->{
				if(this.table.getSelectionModel().getSelectedItem() != null){
					TableItemInfo temp = this.table.getSelectionModel().getSelectedItem();
					this.medicineCreateController.deleteFromMedicineGroup(temp.getItemId(), temp.getHeadId(), nodeType.getType());
				}
			});
			
			this.table.setContextMenu(this);
			break;
		case MEDICINE_BRAND:
			this.medicineTable = table;
	

			menuRefresh.setOnAction(e->{
				//this.medicineCreateController.setTxtMedicineBrand("");
				this.medicineCreateController.setCmbGenericBrand("");;
				this.medicineCreateController.setCmbBrand("");;
				this.medicineCreateController.setCmbCompanyName("");
				this.medicineTable.setItems(LoadedInfo.getMedicineBrandList());
				table.refresh();
			});

			menuUpdateSl.setOnAction(e->{
				this.medicineCreateController.updateSerial(medicineTable);
			});
			
			menuDelete.setOnAction(e->{
				if(this.medicineTable.getSelectionModel().getSelectedItem() != null){
					MedicineItemInfo temp = this.medicineTable.getSelectionModel().getSelectedItem();
					this.medicineCreateController.deleteFromMedicineGroup(temp.getItemId(), temp.getHeadId(), nodeType.getType());
				}
			});
			
			this.medicineTable.setContextMenu(this);
			break;
		

		}




	}

	

	public String getTxt() {
		return txt.getText().trim();
	}

	public String getSelectedTxt() {
		return txt.getSelectedText();
	}

	public void setTxt(String txt) {
		if(getSelectedTxt().isEmpty())
			if(getTxt().isEmpty())
				this.txt.setText(txt);
			else
				this.txt.setText(getTxt()+txt);
		else
			this.txt.replaceSelection(txt);
	}



}
