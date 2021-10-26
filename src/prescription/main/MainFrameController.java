
package prescription.main;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.sun.glass.ui.Size;
import com.sun.javafx.scene.traversal.WeightedClosestCorner;

import databaseHandler.DatabaseHandler;
import javafx.animation.Animation.Status;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import shareClasses.AlertMaker;
import shareClasses.CommonMethod;
import shareClasses.CustomContextMenu;
import shareClasses.DialogAddChild;
import shareClasses.DialogEdit;
import shareClasses.DialogRename;
import shareClasses.DialogSaveAsNew;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.LoadedInfo.MedicineItemInfo;
import shareClasses.LoadedInfo.TableItemInfo;
import shareClasses.MedicineRowFactory;
import shareClasses.NewCustomContextMenu;
import shareClasses.NodeType;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import shareClasses.SuggestedDialogController;
import shareClasses.TableContextMenu;
import shareClasses.TableItemRowFactory;
import ui.util.MainUtil;

public class MainFrameController implements Initializable{

	@FXML
	MenuBar menuBar;

	@FXML
	Menu menuSetup;
	@FXML
	Menu menuReport;
	@FXML
	Menu menuSettings;


	@FXML
	MenuItem menuItemCheapComplainCreate;
	@FXML
	MenuItem menuItemClinicalExaminationCreate;
	@FXML
	MenuItem menuItemMedicineNameCreate;
	@FXML
	MenuItem menuItemMedicineScheduleSet;
	@FXML
	MenuItem menuItemInvestigationTestCreate;
	@FXML
	MenuItem menuItemAdviseCreate;

	@FXML
	MenuItem menuItemSystemicSurveyQuestionCreate;

	@FXML
	MenuItem menuItemPatientReport;

	@FXML
	MenuItem menuItemUserCreate;
	@FXML
	MenuItem menuItemUserAuthentication;
	@FXML
	MenuItem menuItemMyProfile;
	@FXML
	MenuItem menuItemLogOut;



	@FXML
	MenuItem splitMenuItemLogOut;
	@FXML
	MenuItem splitMenuLoadData;



	//////--------- Previous Menu Item--------
	MenuItem menuItemShowSugQuesCheapComplain;
	MenuItem menuItemAddCheapComplain;
	MenuItem menuItemLoadAllCheapComplain;
	MenuItem menuItemLoadBySystemCheapComplain;


	//MenuItem menuItemSystemTableRefresh;

	//MenuItem menuItemSystemTableAddDisease;
	//MenuItem menuItemSystemTableAddCC;
	//MenuItem menuItemSystemTableAddGeneric;
	//MenuItem menuItemSystemTableEditSystemName;


	/*MenuItem menuItemAddInvestigation;
	MenuItem menuItemLoadAllInvestigation;
	MenuItem menuItemLoadByDiseaseInvestigation;
	MenuItem menuItemLoadBySystemInvestigation;*/

	/*MenuItem menuItemAddAdvise;
	MenuItem menuItemLoadAllAdvise;
	MenuItem menuItemLoadByDiseaseAdvise;
	MenuItem menuItemLoadBySystemAdvise;*/

	/*MenuItem menuItemLoadAllDiseaseHistory;
	MenuItem menuItemLoadBySystemDiseaseHistory;

	MenuItem menuItemLoadAllDiseaseMed;
	MenuItem menuItemLoadBySystemDiseaseMed;

	MenuItem menuItemLoadAllGroup;
	MenuItem menuItemLoadByDiseaseGroup;

	MenuItem menuItemLoadAllGeneric;
	MenuItem menuItemLoadBySystemGeneric;
	MenuItem menuItemLoadByDiseaseGeneric;
	MenuItem menuItemLoadByGroupGeneric;

	MenuItem menuItemLoadAllBrand;
	MenuItem menuItemLoadByGenericBrand;*/

	MenuItem menuItemLoadAllDiseaseHistory;
	MenuItem menuItemLoadBySystemDiseaseHistory;

	MenuItem menuItemLoadAllMedicineHistory;
	MenuItem menuItemLoadByGenericMedicineHistory;

	MenuItem menuItemDeleteIllnessHistory;
	MenuItem menuItemAddToPrescribedDrug;
	MenuItem menuItemDeleteDrugHistory;

	MenuItem menuItemSaveScheduleToGenericSchedule;
	MenuItem menuItemSaveScheduleToGenericCourse;	
	MenuItem menuItemSaveScheduleToMedicineBrandSchedule;
	MenuItem menuItemSaveScheduleToMedicineBrandCourse;

	@FXML
	DatePicker date;

	@FXML
	TextField txtAge;
	@FXML
	TextField txtContactNo;

	@FXML
	TextField txtSystemName;
	@FXML
	TextField txtDiseaseName;
	@FXML
	TextField txtCheapComplain;
	@FXML
	TextField txtMedicineGroup;
	@FXML
	TextField txtGeneric;
	@FXML
	TextField txtMedicineBrand;
	@FXML
	TextField txtInvestigation;
	@FXML
	TextArea txtAdvise;

	@FXML
	TextField txtSystemNameHistory;
	@FXML
	TextField txtDiseaseNameHistory;


	@FXML
	Spinner<Integer> spinnerNextVisit=new Spinner<Integer>(0, 365, 0, 7);



	@FXML
	ComboBox cmbVisitNo;

	FxComboBox cmbId = new FxComboBox<>();
	FxComboBox cmbPatientName = new FxComboBox<>();
	FxComboBox cmbSex = new FxComboBox<>();
	FxComboBox cmbAddress = new FxComboBox<>();
	//FxComboBox cmbSystem = new FxComboBox<>();
	FxComboBox cmbDisease = new FxComboBox<>();
	FxComboBox cmbCheapComplain = new FxComboBox<>();
	//FxComboBox cmbSystemName = new FxComboBox<>();
	//FxComboBox cmbDiseaseNameInd = new FxComboBox<>();
	//FxComboBox cmbInvestigationName = new FxComboBox<>();
	//FxComboBox cmbAdvise = new FxComboBox<>();
	//FxComboBox cmbSystemNameMed = new FxComboBox<>();
	//FxComboBox cmbDiseaseNameMed = new FxComboBox<>();
	//FxComboBox cmbGroupName = new FxComboBox<>();
	//FxComboBox cmbGenericName = new FxComboBox<>();
	//FxComboBox cmbBrandName = new FxComboBox<>();
	FxComboBox cmbCompanyName = new FxComboBox<>();
	FxComboBox cmbSchedule = new FxComboBox<>();
	FxComboBox cmbTime = new FxComboBox<>();
	FxComboBox cmbCourse = new FxComboBox<>();

	FxComboBox cmbSystemNameHistorySurvey = new FxComboBox<>();
	FxComboBox cmbSystemNameHistoryIllness = new FxComboBox<>();
	//FxComboBox cmbCCHistoryIllness = new FxComboBox<>();
	FxComboBox cmbDiseaseHistoryIllness = new FxComboBox<>();
	FxComboBox cmbGenericHistoryDrug = new FxComboBox<>();
	FxComboBox cmbMedicineHistoryDrug = new FxComboBox<>();
	FxComboBox cmbScheduleHistoryDrug = new FxComboBox<>();
	FxComboBox cmbJobProffession = new FxComboBox<>();
	FxComboBox cmbDesignation = new FxComboBox<>();
	FxComboBox cmbPosting = new FxComboBox<>();

	FxComboBox cmbRefferTo = new FxComboBox<>();

	HashMap<String , String> mapCompanyName = new HashMap<>();




	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnPrint;
	@FXML
	Button btnRefresh;
	@FXML
	Button btnAddIllnessHis;
	@FXML
	Button btnAddDrugHis;
	@FXML
	SplitMenuButton splitBtnLogOut;

	@FXML
	RadioButton radioPresentIllness;
	@FXML
	RadioButton radioPastIllness;
	@FXML
	RadioButton radioFatherIllness;
	@FXML
	RadioButton radioMotherIllness;
	@FXML
	RadioButton radioOtherIllness;
	@FXML
	RadioButton radioPresentDrug;
	@FXML
	RadioButton radioRecentDrug;
	@FXML
	RadioButton radioPastDrug;
	@FXML
	RadioButton radioAllergicDrug;


	@FXML
	CheckBox checkSmokingYes;
	@FXML
	CheckBox checkSmokingNO;
	@FXML
	CheckBox checkBetelYes;
	@FXML
	CheckBox checkBetelNo;
	@FXML
	CheckBox checkRegular;
	@FXML
	CheckBox checkEregular;



	@FXML
	VBox vBoxSystemSurvey;
	@FXML
	VBox vBoxIllnessHistory;
	@FXML
	VBox vBoxDrugHistory;
	@FXML
	VBox vBoxJobProffession;
	@FXML
	VBox vBoxRefferTo;

	@FXML
	HBox hBoxPatientInfo;
	@FXML
	HBox hBoxMedicineBrand;
	@FXML
	HBox hBoxSchedule;
	@FXML
	HBox hBoxNextVisit;

	TableContextMenu tableContextMenuCheapComplain;
	CustomContextMenu contextMenuCheapComplain;
	//CustomContextMenu contextMenuDisease;
	/*CustomContextMenu contextMenuSystem;
	CustomContextMenu contextMenuDiseaseInd;
	CustomContextMenu contextMenuInvestigation;
	CustomContextMenu contextMenuAdvise;
	CustomContextMenu contextMenuSystemMed;
	CustomContextMenu contextMenuDiseaseMed;
	CustomContextMenu contextMenuGroup;
	CustomContextMenu contextMenuGeneric;
	CustomContextMenu contextMenuBrandName;*/
	CustomContextMenu contextMenuSchedule;
	CustomContextMenu contextMenuTime;
	CustomContextMenu contextMenuCourse;
	CustomContextMenu contextMenuDiseaseHistory;
	CustomContextMenu contextMenuMedicineHistory;

	NewCustomContextMenu contextMenuTxtSystem;
	NewCustomContextMenu contextMenuTableSystem;
	NewCustomContextMenu contextMenuTxtDisease;
	NewCustomContextMenu contextMenuTableDisease;
	NewCustomContextMenu contextMenuTxtCheapComplain;
	NewCustomContextMenu contextMenuTableCheapComplain;
	NewCustomContextMenu contextMenuTxtMedicineGroup;
	NewCustomContextMenu contextMenuTableMedicineGroup;
	NewCustomContextMenu contextMenuTxtGeneric;
	NewCustomContextMenu contextMenuTableGeneric;

	NewCustomContextMenu contextMenuTxtMedicineBrand;
	NewCustomContextMenu contextMenuTableMedicineBrand;
	NewCustomContextMenu contextMenuTxtInvestigation;
	NewCustomContextMenu contextMenuTableInvestigation;
	NewCustomContextMenu contextMenuTxtAdvise;
	NewCustomContextMenu contextMenuTableAdvise;



	//ContextMenu contextMenuSystemTable = new ContextMenu();
	ContextMenu contextMenuIllnessHistory = new ContextMenu();
	ContextMenu contextMenuDrugHistory = new ContextMenu();


	ToggleGroup groupIllness;
	ToggleGroup groupDrug;


	Tooltip toolTipSystem = new Tooltip("System Name");
	Tooltip toolTipDisease = new Tooltip("Disease Name");
	Tooltip toolTipGroup = new Tooltip("Group Name");
	Tooltip toolTipGeneric = new Tooltip("Generic Name");
	Tooltip toolTipMedicine = new Tooltip("Medicine/Brand Name");
	Tooltip toolTipCheapComplain = new Tooltip("Cheap Complain");
	Tooltip toolTipAdvis = new Tooltip("Advise");
	Tooltip toolTipInvestigation = new Tooltip("Investigation");


	@FXML
	AnchorPane parentPane;

	@FXML
	TreeView treeIllnessHistory;
	@FXML
	TreeView treeDrugHistory;
	@FXML
	TreeView treeExamination;


	TreeItem<ExaminationItem> rootExamination= new TreeItem<>(new ExaminationItem("Clinical Examination"));

	TreeItem<String> rootIllnessHistory = new TreeItem<>("Illness History");
	TreeItem<String> rootDrugHistory = new TreeItem<>("Drug History");

	TreeItem<String> treeItemPresentIllness = new TreeItem<>("Present Illness");
	TreeItem<String> treeItemPastIllness = new TreeItem<>("Past Illness");
	TreeItem<String> treeItemFatherIllness = new TreeItem<>("Father Illness");
	TreeItem<String> treeItemMotherIllness = new TreeItem<>("Mother Illness");
	TreeItem<String> treeItemOtherIllness = new TreeItem<>("Other Illness");


	TreeItem<String> treeItemPresentDrug = new TreeItem<>("Present Drug");
	TreeItem<String> treeItemRecentDrug = new TreeItem<>("Recent Drug");
	TreeItem<String> treeItemPastDrug = new TreeItem<>("Past Drug");
	TreeItem<String> treeItemAllergicDrug = new TreeItem<>("Allergic Drug");

	ArrayList<String> presentDrugArray=new ArrayList<>();
	ArrayList<String> recentDrugArray=new ArrayList<>();
	ArrayList<String> pastDrugArray=new ArrayList<>();
	ArrayList<String> allergicDrugArray=new ArrayList<>();
	ArrayList<String> presentIllnessArray=new ArrayList<>();
	ArrayList<String> pastIllnessArray=new ArrayList<>();
	ArrayList<String> cheapComplaiArray=new ArrayList<>();
	ArrayList<String> fatherIllnessArray=new ArrayList<>();
	ArrayList<String> motherIllnessArray=new ArrayList<>();
	ArrayList<String> otherIllnessArray=new ArrayList<>();

	@FXML
	TabPane tabPane;

	Parent[] allTabs;

	private DecimalFormat df = new DecimalFormat("#0.00");
	private DecimalFormat dfId = new DecimalFormat("#00");

	boolean isSearch;

	DateTimeFormatter dbDateFormate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	HashMap<String,ExaminationItem> examinationMap = new HashMap<String,ExaminationItem>();
	ArrayList<String> examinationIdArray = new ArrayList<>();

	Calendar calender;

	public static StageCcSuggest stageCcSuggest;

	int examinationSerial;
	HashMap map;
	DatabaseHandler databaseHandler;
	String sql;

	/*private static final int systemType = 0;
	private static final int diseaseType = 1;
	private static final int medicineGroupType = 2;
	private static final int genericType = 3;
	private static final int brandType = 4;
	private static final int testType = 0;
	private static final int diagnosisType = 0;
	private static final int adviseType = 0;*/

	private static final int cheapComplainType = 1;
	private static final int surveyType = 2;

	@FXML
	public TableView<TableItemInfo> tableSystem;
	@FXML
	private TableColumn<TableItemInfo, String> systemCol;

	@FXML
	public TableView<TableItemInfo> tableDisease;
	@FXML
	private TableColumn<TableItemInfo, String> diseaseCol;


	@FXML
	public TableView<TableItemInfo> tableMedicineGroup;
	@FXML
	private TableColumn<TableItemInfo, String> medicineGroupCol;

	@FXML
	public TableView<TableItemInfo> tableGeneric;
	@FXML
	private TableColumn<TableItemInfo, String> genericCol;

	@FXML
	public TableView<MedicineItemInfo> tableMedicineBrand;
	@FXML
	private TableColumn<MedicineItemInfo, String> brandCol;
	@FXML
	private TableColumn<MedicineItemInfo, String> companyCol;

	@FXML
	public TableView<TableItemInfo> tableCheapComplain;
	@FXML
	private TableColumn<TableItemInfo, String> cheapComplainCol;

	@FXML
	public TableView<TableItemInfo> tablePrescribedCheapComplain;
	ObservableList<TableItemInfo> listPrescribedCheapComplain = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> prescribedCheapComplainCol;


	@FXML
	public TableView<TableItemInfo> tablePrescribedDisease;
	ObservableList<TableItemInfo> listPrescribedDisease = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> prscribedDiseaseCol;


	@FXML
	public TableView<TableItemInfo> tableInvestigation;
	@FXML
	private TableColumn<TableItemInfo, String> investigationCol;

	@FXML
	public TableView<TableItemInfo> tableInvestigationGroup;
	@FXML
	private TableColumn<TableItemInfo, String> investigationGroupCol;

	@FXML
	public TableView<TableItemInfo> tablePrescribedInvestigation;
	ObservableList<TableItemInfo> listPrescribedInvestigation = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> prescribedInvestigationCol;


	@FXML
	public TableView<TableItemInfo> tableAdvise;
	ObservableList<TableItemInfo> listAdvise = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> adviseCol;

	@FXML
	public TableView<TableItemInfo> tableAdviseGroup;
	@FXML
	private TableColumn<TableItemInfo, String> adviseGroupCol;

	@FXML
	private TableView<TableItemInfo> tablePrescribedAdvise;
	ObservableList<TableItemInfo> listPrescribedAdvise = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> prescribedAdviseCol;


	@FXML
	private TableView<TableItemInfo> tableSystemicSurvey;
	ObservableList<TableItemInfo> listSystemicSurvey = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> systemicSurveyCol;


	@FXML
	private TableView<TableItemInfo> tableSelectQuerySurvey;
	ObservableList<TableItemInfo> listSelectQuerySurvey = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TableItemInfo, String> selectQuerySurveyCol;


	@FXML
	private TableView<ClinicalExaminationItem> tableClinicalExamination;
	ObservableList<ClinicalExaminationItem> listClinicalExamination = FXCollections.observableArrayList();
	@FXML
	private TableColumn<ClinicalExaminationItem, String> examinationNameCol;
	@FXML
	private TableColumn<ClinicalExaminationItem, String> resultCol;


	@FXML
	public TableView<TableItemInfo> tableSystemHistory;
	@FXML
	private TableColumn<TableItemInfo, String> systemHistoryCol;

	@FXML
	public TableView<TableItemInfo> tableDiseaseHistory;
	@FXML
	private TableColumn<TableItemInfo, String> diseaseHistoryCol;


	/*@FXML
	private TableView<PresentDrugItem> tablePresentDrug;
	ObservableList<PresentDrugItem> listPresentDrug = FXCollections.observableArrayList();
	@FXML
	private TableColumn<PresentDrugItem, String> presentMedicineBrandCol;
	@FXML
	private TableColumn<PresentDrugItem, String> presentDrugScheduleCol;*/


	@FXML
	private TableView<MedicineItemInfo> tablePrescribedMedicine;
	ObservableList<MedicineItemInfo> listPrescribedMedicine = FXCollections.observableArrayList();
	@FXML
	private TableColumn<MedicineItemInfo, String> medicineNameCol;
	@FXML
	private TableColumn<MedicineItemInfo, String> scheduleCol;
	@FXML
	private TableColumn<MedicineItemInfo, String> timeCol;
	@FXML
	private TableColumn<MedicineItemInfo, String> courseCol;


	String pattern = "dd-MMM-yyyy";
	StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		@Override
		public String toString(LocalDate date) {
			if (date != null) {
				return dateFormatter.format(date);
			} else {
				return "";
			}
		}

		@Override
		public LocalDate fromString(String string) {
			if (string != null && !string.isEmpty()) {
				return LocalDate.parse(string, dateFormatter);
			} else {
				return null;
			}
		}
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		manuItemAdd();		
		addCmp();
		focusMoveAction();
		setCmpData();
		setCmpAction();
		setKeyAction();
		setCmpFocusAction();
		btnRefreshAction(null);
	}



	@FXML
	private void btnSaveAction(ActionEvent event) {
		try {
			if(validationCheck()) {
				//if(!isPatientIdExist(getCmbId())) {
				if(confirmationCheck("Save This Prescription?")) {
					int visitNo;
					if(isPatientIdExist(getCmbId())) {
						sql = "update tbpatientdetails set Name='"+getCmbPatientName()+"',sex='"+getCmbSex()+"',ContactNo='"+getTxtContactNo()+"',refferedBy='',refferedTo='"+getCmbRefferTo()+"',address='"+getCmbAddress()+"',entryTime=NOW(),Entryby='"+SessionBeam.getUserId()+"' where patientid='"+getCmbId()+"'";		
						databaseHandler.execAction(sql);
					}else {
						setMaxPatientId();
						sql = "insert into tbpatientdetails (patientID,Name,sex,ContactNo,refferedBy,refferedTo,address,entryTime,Entryby) values("+getCmbId()+",'"+getCmbPatientName()+"','"+getCmbSex()+"','"+getTxtContactNo()+"','','"+getCmbRefferTo()+"','"+getCmbAddress()+"',NOW(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
					}

					//insert tbvisithistory
					sql = "insert into tbvisithistory (PatientId,visitNo,age,wight,date,entryDate,Entryby) values("+getCmbId()+","+(getVisitNo()+1)+",'"+getTxtAge()+"','0','"+dbDateFormate.format(date.getValue())+"',NOW(),'"+SessionBeam.getUserId()+"')";
					databaseHandler.execAction(sql);

					String id= getCmbId();
					visitNo = getVisitNo();
					//insert tbMedicineHistory
					for(int i=0;i<listPrescribedMedicine.size();i++) {
						sql = "insert into tbmedicinehistory (patientId,visitNo,slNo,MedicineId,medicineName,Schedule,time,Course,entryTime,entryBy) values("+id+","+visitNo+","+i+",'"+listPrescribedMedicine.get(i).getItemId()+"','"+listPrescribedMedicine.get(i).getItemName()+"','"+listPrescribedMedicine.get(i).getSchedule()+"','"+listPrescribedMedicine.get(i).getTime()+"','"+listPrescribedMedicine.get(i).getCourse()+"',now(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
					}


					//insert tbDiagnosisHistory
					for(int i=0;i < listPrescribedDisease.size();i++) {
						sql = "insert into tbdiagnosishistory (patientId,visitNo,slNo,diagnosisId,diagnosisName,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listPrescribedDisease.get(i).getItemId()+"','"+listPrescribedDisease.get(i).getItemName()+"',now(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
					}

					//insert tbTestHistory
					for(int i=0;i<listPrescribedInvestigation.size();i++) {
						sql = "insert into tbtesthistory (patientId,visitNo,slNo,TestId,testname,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listPrescribedInvestigation.get(i).getItemId()+"','"+listPrescribedInvestigation.get(i).getItemName()+"',now(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
					}

					//insert tbadvisehistory
					for(int i=0;i < listPrescribedAdvise.size();i++) {
						sql = "insert into tbadvisehistory (patientId,visitNo,slNo,adviseId,advise,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listPrescribedAdvise.get(i).getItemId()+"','"+listPrescribedAdvise.get(i).getItemName()+"',now(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
					}

					//insert tbcchistory
					for(int i=0;i < listPrescribedCheapComplain.size();i++) {
						sql = "insert into tbcchistory (patientId,visitNo,slNO,id,CheapComplain,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listPrescribedCheapComplain.get(i).getItemId()+"','"+listPrescribedCheapComplain.get(i).getItemName()+"',now(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
					}

					//insert tbcinicalexaminationhistory
					for(int i=0;i<listClinicalExamination.size();i++) {
						sql = "insert into tbcinicalexaminationhistory (patientId,visitNo,slNo,id,result,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listClinicalExamination.get(i).getItemId()+"','"+listClinicalExamination.get(i).getResult()+"',now(),'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);
					}

					//insert tbadvisehistory
					/*sql = "insert into tbreporthistory (patientId,visitNo,History,entryTime,entryBy) values("+id+","+visitNo+",'"+txtReportHistory.getText().trim()+"',now(),'"+SessionBeam.getUserId()+"')";
					databaseHandler.execAction(sql);*/

					sql = "Delete from tbclinicalhistory where patientid="+id+" and visitno="+visitNo+" ;";
					databaseHandler.execAction(sql);

					for(int i=0;i<presentIllnessArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",2,'"+presentIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}

					for(int i=0;i<pastIllnessArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",3,'"+pastIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					for(int i=0;i<fatherIllnessArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",4,'"+fatherIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					for(int i=0;i<motherIllnessArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",5,'"+motherIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					for(int i=0;i<otherIllnessArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",6,'"+otherIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					for(int i=0;i<presentDrugArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",7,'"+presentDrugArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					for(int i=0;i<recentDrugArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",8,'"+recentDrugArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					for(int i=0;i<pastDrugArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",9,'"+pastDrugArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					for(int i=0;i<allergicDrugArray.size();i++) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",10,'"+allergicDrugArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}

					for(int i=0;i<listSelectQuerySurvey.size();i++) {
						sql = "insert into tbsurveyhistory (PatientId,visitNo,slNo,id,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"',"+listSelectQuerySurvey.get(i).getItemId()+",now(),'"+SessionBeam.getUserId()+"');" ;
						databaseHandler.execAction(sql);
					}

					if(!getCmbJobProffession().isEmpty()) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",11,'"+getCmbJobProffession()+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					if(!getCmbPosting().isEmpty()) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",12,'"+getCmbPosting()+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}
					if(!getCmbDesignation().isEmpty()) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",13,'"+getCmbDesignation()+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}

					if(getCheckSmokingYes()||getCheckSmokingNO()) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",14,'"+(getCheckSmokingYes()?"Yes":"NO")+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}

					if(getCheckBetelYes()||getCheckBetelNo()) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",15,'"+(getCheckBetelYes()?"Yes":"NO")+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}

					if(getCheckRegular()||getCheckEregular()) {
						sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",16,'"+(getCheckRegular()?"Regular":"Eregular")+"',now(),"+SessionBeam.getUserId()+")";
						databaseHandler.execAction(sql);
					}


					new Notification(Pos.TOP_CENTER, "Information graphic", "Prescription Save Successfull..!","Your Patient Prescription Save Successfully as new visit");

					visitNoLoadByPatientId(id);
					btnPrintAction(null);

					//btnRefreshAction(null);


				}
				/*}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Patient Id Not Exist..!","This Prescription Id is not Saved...\nPlease Select Valid Patient ID");
				}*/
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showInternalMessageDialog(null, e);
		}
	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		try {
			if(validationCheck()) {
				if(isPatientIdExist(getCmbId())) {
					if(confirmationCheck("Save This Prescription?")) {

						String visitNo=getCmbVisitNo();
						String id = getCmbId();

						databaseHandler.execAction("delete from tbmedicinehistory where patientid="+id+" and visitNo="+visitNo+"");
						databaseHandler.execAction("delete from tbdiagnosishistory where patientid="+id+" and visitNo="+visitNo+"");
						databaseHandler.execAction("delete from tbtesthistory where patientid="+id+" and visitNo="+visitNo+"");
						databaseHandler.execAction("delete from tbadvisehistory where patientid="+id+" and visitNo="+visitNo+"");
						databaseHandler.execAction("delete from tbcchistory where patientid="+id+" and visitNo="+visitNo+"");
						databaseHandler.execAction("delete from tbcinicalexaminationhistory where patientid="+id+" and visitNo="+visitNo+"");
						databaseHandler.execAction("delete from tbreporthistory where patientid="+id+" and visitNo="+visitNo+"");
						databaseHandler.execAction("delete from tbsurveyhistory where patientid="+id+" and visitNo="+visitNo+"");


						sql = "update tbpatientdetails set Name='"+getCmbPatientName()+"',sex='"+getCmbSex()+"',ContactNo='"+getTxtContactNo()+"',refferedBy='',refferedTo='"+getCmbRefferTo()+"',address='"+getCmbAddress()+"',entryTime=NOW(),Entryby='"+SessionBeam.getUserId()+"' where patientid='"+id+"'";
						databaseHandler.execAction(sql);

						//insert tbvisithistory
						sql = "update tbvisithistory set age='"+getTxtAge()+"',date = '"+dbDateFormate.format(date.getValue())+"',wight='0',entryDate=NOW(),Entryby='"+SessionBeam.getUserId()+"'  where patientid="+id+" and visitNo="+visitNo+";";
						databaseHandler.execAction(sql);

						//insert tbMedicineHistory
						for(int i=0;i<listPrescribedMedicine.size();i++) {
							sql = "insert into tbmedicinehistory (patientId,visitNo,slNo,MedicineId,medicineName,Schedule,time,Course,entryTime,entryBy) values("+id+","+visitNo+","+i+",'"+listPrescribedMedicine.get(i).getItemId()+"','"+listPrescribedMedicine.get(i).getItemName()+"','"+listPrescribedMedicine.get(i).getSchedule()+"','"+listPrescribedMedicine.get(i).getTime()+"','"+listPrescribedMedicine.get(i).getCourse()+"',now(),'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);
						}


						//insert tbDiagnosisHistory
						for(int i=0;i < listPrescribedDisease.size();i++) {
							sql = "insert into tbdiagnosishistory (patientId,visitNo,slNo,diagnosisId,diagnosisName,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listPrescribedDisease.get(i).getItemId()+"','"+listPrescribedDisease.get(i).getItemName()+"',now(),'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);
						}

						//insert tbTestHistory
						for(int i=0;i<listPrescribedInvestigation.size();i++) {
							sql = "insert into tbtesthistory (patientId,visitNo,slNo,TestId,testname,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listPrescribedInvestigation.get(i).getItemId()+"','"+listPrescribedInvestigation.get(i).getItemName()+"',now(),'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);
						}

						//insert tbadvisehistory
						for(int i=0;i < listPrescribedAdvise.size();i++) {
							sql = "insert into tbadvisehistory (patientId,visitNo,slNo,adviseId,advise,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listPrescribedAdvise.get(i).getItemId()+"','"+listPrescribedAdvise.get(i).getItemName()+"',now(),'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);
						}

						//insert tbcchistory
						for(int i=0;i < listPrescribedCheapComplain.size();i++) {
							sql = "insert into tbcchistory (patientId,visitNo,slNO,id,CheapComplain,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listPrescribedCheapComplain.get(i).getItemId()+"','"+listPrescribedCheapComplain.get(i).getItemName()+"',now(),'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);
						}

						//insert tbcinicalexaminationhistory
						for(int i=0;i<listClinicalExamination.size();i++) {
							sql = "insert into tbcinicalexaminationhistory (patientId,visitNo,slNo,id,result,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"','"+listClinicalExamination.get(i).getItemId()+"','"+listClinicalExamination.get(i).getResult()+"',now(),'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);
						}

						//insert tbadvisehistory
						/*sql = "insert into tbreporthistory (patientId,visitNo,History,entryTime,entryBy) values("+id+","+visitNo+",'"+txtReportHistory.getText().trim()+"',now(),'"+SessionBeam.getUserId()+"')";
					databaseHandler.execAction(sql);*/

						sql = "Delete from tbclinicalhistory where patientid="+id+" and visitno="+visitNo+" ;";
						databaseHandler.execAction(sql);

						for(int i=0;i<presentIllnessArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",2,'"+presentIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}

						for(int i=0;i<pastIllnessArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",3,'"+pastIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						for(int i=0;i<fatherIllnessArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",4,'"+fatherIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						for(int i=0;i<motherIllnessArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",5,'"+motherIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						for(int i=0;i<otherIllnessArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",6,'"+otherIllnessArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						for(int i=0;i<presentDrugArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",7,'"+presentDrugArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						for(int i=0;i<recentDrugArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",8,'"+recentDrugArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						for(int i=0;i<pastDrugArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",9,'"+pastDrugArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						for(int i=0;i<allergicDrugArray.size();i++) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",10,'"+allergicDrugArray.get(i)+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}

						for(int i=0;i<listSelectQuerySurvey.size();i++) {
							sql = "insert into tbsurveyhistory (PatientId,visitNo,slNo,id,entryTime,entryBy) values("+id+","+visitNo+",'"+i+"',"+listSelectQuerySurvey.get(i).getItemId()+",now(),'"+SessionBeam.getUserId()+"');" ;
							databaseHandler.execAction(sql);
						}

						if(!getCmbJobProffession().isEmpty()) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",11,'"+getCmbJobProffession()+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						if(!getCmbPosting().isEmpty()) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",12,'"+getCmbPosting()+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}
						if(!getCmbDesignation().isEmpty()) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",13,'"+getCmbDesignation()+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}

						if(getCheckSmokingYes()||getCheckSmokingNO()) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",14,'"+(getCheckSmokingYes()?"Yes":"NO")+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}

						if(getCheckBetelYes()||getCheckBetelNo()) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",15,'"+(getCheckBetelYes()?"Yes":"NO")+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}

						if(getCheckRegular()||getCheckEregular()) {
							sql= "insert into tbclinicalhistory (patientId,VisitNo,Id,Result,EntryTime,userId) values("+id+","+visitNo+",16,'"+(getCheckRegular()?"Regular":"Eregular")+"',now(),"+SessionBeam.getUserId()+")";
							databaseHandler.execAction(sql);
						}


						new Notification(Pos.TOP_CENTER, "Information graphic", "Prescription Edit Successfull..!","The Prescription of visit no:"+visitNo+" of Patientid:"+id+" has Edit Successfully");

						btnPrintAction(null);
						//btnRefreshAction(null);


					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Patient Id Not Exist..!","This Prescription Id is not Saved...\nPlease Select Valid Patient ID");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showInternalMessageDialog(null, e);
		}
	}

	@FXML
	private void btnPrintAction(ActionEvent event) {
		try {
			if(!getCmbId().isEmpty()) {
				if(isPatientIdExist(getCmbId())) {
					String report="";
					map=new HashMap<>();

					map.put("investigations", investigations(getCmbId(),getCmbVisitNo()));
					map.put("diagnosis", diagnosis(getCmbId(),getCmbVisitNo()));
					map.put("drugHistory", drugHistory(getCmbId(),getCmbVisitNo()));
					map.put("nextVisit",nextVisit(getSpinnerNextVisit()));
					map.put("refferTo",getCmbRefferTo());


					sql= "select * from tbpatientdetails as pd join tbvisithistory as vh on vh.PatientId = pd.patientID where pd.patientid="+getCmbId()+" and vh.visitNo="+getCmbVisitNo()+" ";

					report="src/resource/reportFile/finalPrescriptionInDetailsWithSubReport.jrxml";

					System.out.println(sql);

					JasperDesign jd=JRXmlLoader.load(report);
					JRDesignQuery jq=new JRDesignQuery();
					jq.setText(sql);
					jd.setQuery(jq);
					JasperReport jr=JasperCompileManager.compileReport(jd);
					JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
					JasperViewer.viewReport(jp, false);

					//JasperPrintManager.printPage(jp, 0, false);
					//JasperPrintManager.printReport(jp, true);

				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Patient Id!","Please Select Valid Patient Id...");
					cmbId.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Patient Id!","Please Enter Patient Id...");
				cmbId.requestFocus();
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub

		generalClearAndRefresh();

		tableSystem.setItems(LoadedInfo.getSystemList());
		tableDisease.setItems(LoadedInfo.getDiseaseList());
		tableAdvise.setItems(LoadedInfo.getAdviseList());
		tableMedicineGroup.setItems(LoadedInfo.getMedicineGroupList());
		tableMedicineBrand.setItems(LoadedInfo.getMedicineBrandList());
		tableGeneric.setItems(LoadedInfo.getGenericList());


		tableCheapComplain.setItems(LoadedInfo.getCheapComplainList());
		tableInvestigation.setItems(LoadedInfo.getInvestigationList());
		tableInvestigationGroup.setItems(LoadedInfo.getInvestigationGroupList());
		tableAdviseGroup.setItems(LoadedInfo.getAdviseGroupList());

		tableSystemHistory.setItems(LoadedInfo.getSystemList());
		tableDiseaseHistory.setItems(LoadedInfo.getDiseaseList());


		loadCheapComplain("");
		//drugComboBoxLoad(cmbSystemNameMed, 0);
		//drugComboBoxLoad(cmbDiseaseNameMed, 1);
		//drugComboBoxLoad(cmbGroupName, 2);
		//drugComboBoxLoad(cmbGenericName, 3);
		//drugComboBoxLoad(cmbBrandName, 4);
		brandCompanyNameLoad();


		/*diagnosisComboBoxLoad(cmbDiagnosisSystem, 0);
			diagnosisComboBoxLoad(cmbDiagnosisGroup, 1);*/
		//ccLoad(cmbCheapComplain);

		//drugComboBoxLoad(cmbSystem, 0);
		//drugComboBoxLoad(cmbDisease, 1);
		//drugComboBoxLoad(cmbDiseaseNameInd, 1);

		//investigationComboBoxLoad(cmbInvestigationName, 2);
		//adviseComboBoxLoad("", 2);
		companyNameComboLoad();
		scheduleLoad();
		timeLoad();
		courseLoad();
		allPatientIdLoad();
		setMaxPatientId();
		visitNoLoadByPatientId(getCmbId());
		cmbPatientNameLoad();
		cmbAddressLoad();
		cmbRefferNameLoad();
		cmbJobProffessionLoad();
		cmbDesignationLoad();
		cmbPostingLoad();


		//Clinical History Part Refresh
		drugComboBoxLoad(cmbSystemNameHistorySurvey, 0);
		drugComboBoxLoad(cmbSystemNameHistoryIllness, 0);
		drugComboBoxLoad(cmbDiseaseHistoryIllness, 1);
		drugComboBoxLoad(cmbMedicineHistoryDrug, 4);
		drugComboBoxLoad(cmbGenericHistoryDrug, 3);

		surbeyTableLoadBySystem(getDrugId(getCmbSystemNameHistorySurvey(),0));

		stageCcSuggest.clearList();
		//updateIdAction();
		//treeExaminationLoad();
	}


	/*private void updateIdAction() {
		// TODO Auto-generated method stub
		try{
			ArrayList<String> groupName= new ArrayList<>();
			ResultSet rs = databaseHandler.execQuery("SELECT COUNT(*),groupname FROM tbmedicinegroup GROUP BY groupname having COUNT(*)>1  ");
			while(rs.next()){
				groupName.add(rs.getString("groupName"));
			}

			for(int i=0;i<groupName.size();i++){
				String idList="";
				rs = databaseHandler.execQuery("select * from tbmedicinegroup where groupname='"+groupName.get(i)+"'");
				while(rs.next()){
					idList += rs.getString("id")+",";
				}
				idList = idList.substring(0,idList.lastIndexOf(","));
				String firstId = idList.substring(0, idList.indexOf(","));

				//System.out.println("update tbMedicinegroup set pId= '"+firstId+"' where pID in  ("+idList+")");
				//System.out.println("update tbMedicinegroup set id= '"+firstId+"' where id in  ("+idList+")");
				databaseHandler.execAction("update tbMedicinegroup set pId= '"+firstId+"' where pID in  ("+idList+")");

				databaseHandler.execAction("update tbMedicinegroup set id= '"+firstId+"' where id in  ("+idList+")");

				databaseHandler.execAction("UPDATE tbadvise SET PId = '"+firstId+"' WHERE pid IN ("+idList+")");

				databaseHandler.execAction("UPDATE tbtestgroup SET PId = '"+firstId+"' WHERE pid IN ("+idList+")");

				databaseHandler.execAction("UPDATE tbmedicinecompany SET id = '"+firstId+"' WHERE id IN ("+idList+")");
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}*/



	@FXML
	private void splitMenuLoadDataAction(ActionEvent event){
		new LoadedInfo.LoadData().start();
	}

	private void generalClearAndRefresh() {
		// TODO Auto-generated method stub
		setDate(new Date());
		//setCmbId("");
		setCmbPatientName("");
		setTxtAge("");
		setCmbAddress("");
		setTxtContactNo("");

		listClinicalExamination.clear();
		listPrescribedCheapComplain.clear();
		listPrescribedDisease.clear();
		listPrescribedInvestigation.clear();
		listPrescribedAdvise.clear();

		listPrescribedMedicine.clear();

		tableClinicalExamination.setItems(listClinicalExamination);
		tablePrescribedCheapComplain.setItems(listPrescribedCheapComplain);
		tablePrescribedDisease.setItems(listPrescribedDisease);
		tablePrescribedInvestigation.setItems(listPrescribedInvestigation);
		tablePrescribedAdvise.setItems(listPrescribedAdvise);

		tablePrescribedMedicine.setItems(listPrescribedMedicine);

		tableClinicalExamination.refresh();
		tablePrescribedCheapComplain.refresh();
		tablePrescribedDisease.refresh();
		tablePrescribedInvestigation.refresh();

		tablePrescribedMedicine.refresh();

		setTxtSystemName("");
		setCmbDisease("");
		setCmbCheapComplain("");
		/*setCmbDiseaseNameInd("");
		setCmbInvestigationName("");
		setCmbAdvise("");*/
		/*setCmbSystemNameMed("");
		setCmbDiseaseNameMed("");
		setCmbGroupName("");
		setCmbGenericName("");
		setCmbBrandName("");*/
		setCmbCompanyName("");
		setCmbSchedule("");
		setCmbTime("");
		setCmbCourse("");
		setSpinnerNextVisit("0");
		setCmbRefferTo("");

		listSelectQuerySurvey.clear();
		tableSelectQuerySurvey.setItems(listSelectQuerySurvey);

		setCmbSystemNameHistorySurvey("");
		setCmbSystemNameHistoryIllness("");
		setCmbDiseaseHistoryIllness("");
		setCmbGenericHistoryDrug("");
		setCmbMedicineHistoryDrug("");
		setCmbScheduleHistoryDrug("");

		setCmbJobProffession("");
		setCmbPosting("");
		setCmbDesignation("");

		setCheckBetelYes(false);
		setCheckBetelNo(false);
		setCheckRegular(false);
		setCheckEregular(false);
		setCheckSmokingYes(false);
		setCheckSmokingNO(false);

		presentIllnessArray.clear();
		pastIllnessArray.clear();
		fatherIllnessArray.clear();
		motherIllnessArray.clear();
		otherIllnessArray.clear();

		presentDrugArray.clear();
		pastDrugArray.clear();
		recentDrugArray.clear();
		allergicDrugArray.clear();

		loadTreeByHead(treeItemPresentIllness,presentIllnessArray);
		loadTreeByHead(treeItemPastIllness,pastIllnessArray);
		loadTreeByHead(treeItemFatherIllness,fatherIllnessArray);
		loadTreeByHead(treeItemMotherIllness,motherIllnessArray);
		loadTreeByHead(treeItemOtherIllness,otherIllnessArray);

		loadTreeByHead(treeItemPresentDrug,presentDrugArray);
		loadTreeByHead(treeItemRecentDrug,recentDrugArray);
		loadTreeByHead(treeItemPastDrug,pastDrugArray);
		loadTreeByHead(treeItemAllergicDrug,allergicDrugArray);

		treeDrugHistoryLoad();

		treeIllnessHistoryLoad();

		for(int i =0 ;i < examinationIdArray.size();i++) {
			examinationMap.get(examinationIdArray.get(i)).setResult("");
		}

		for(int i = 0 ;i< rootExamination.getChildren().size();i++) {

			expandFalse(rootExamination.getChildren().get(i));
		}
	}

	private void expandFalse(TreeItem<ExaminationItem> treeItem) {
		// TODO Auto-generated method stub
		for(int i = 0 ;i< treeItem.getChildren().size();i++) {

			expandFalse(treeItem.getChildren().get(i));
		}
		treeItem.setExpanded(false);
	}

	private void prescriptionFindByPatientId(String patientId) {
		try {
			sql="Select * from tbpatientDetails where patientid="+patientId+"";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {

				setCmbPatientName(rs.getString("Name"));
				setTxtContactNo(rs.getString("ContactNo"));
				setCmbSex(rs.getString("sex"));
				setCmbAddress(rs.getString("Address"));

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Patient Id Not Exist!","Please Select valid Patient Id...");
				cmbId.requestFocus();
			}

			visitNoLoadByPatientId(patientId);

			String visitNo = getCmbVisitNo();

			sql="Select date,age from tbvisithistory where PatientId="+patientId+" and visitNo = '"+visitNo+"'";
			rs = databaseHandler.execQuery(sql);
			if(rs.next()){
				setTxtAge(rs.getString("age"));
				setDate(rs.getDate("Date"));
			}

			//Load Drug Table
			sql="Select * from tbmedicinehistory where patientId="+patientId+" and visitNo="+visitNo+" order by slNo";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {

				listPrescribedMedicine.add(new MedicineItemInfo(rs.getInt("slNo"),rs.getString("MedicineId"),"0",rs.getString("medicineName"),"",rs.getString("Schedule"),rs.getString("time"),rs.getString("Course"),NodeType.MEDICINE_BRAND.getType()) );
			}


			//Load Diagnosis Table

			sql="Select *  from tbdiagnosishistory as dh where patientId="+patientId+" and visitNo="+visitNo+" order by slNo";
			rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listPrescribedDisease.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("diagnosisid"),"", rs.getString("diagnosisName"),NodeType.DISEASE.getType()));
			}



			//Load Investigation Table
			sql="Select *  from tbtesthistory as th where patientId="+patientId+" and visitNo="+visitNo+" order by slNo";
			rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listPrescribedInvestigation.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("testId"),"",rs.getString("TestName"),NodeType.INVESTIGATION.getType()));
			}



			//Load Advise Table
			sql="Select *  from tbadvisehistory as ah where patientId="+patientId+" and visitNo="+visitNo+" order by slNo";
			rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listPrescribedAdvise.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("adviseid"),"",rs.getString("advise"),NodeType.ADVISE.getType()));
			}


			//Load CC Table
			sql="Select * from tbcchistory as ch where patientId="+patientId+" and visitNo="+visitNo+" order by slNo";
			rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listPrescribedCheapComplain.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("id"),"",rs.getString("CheapComplain"),cheapComplainType));
			}


			//Load Clinical Examination Table
			sql="select ch.slNo,ch.id,ce.HeadNameList,ce.Name,ch.result from tbcinicalexaminationhistory ch join tbclinicalexamination ce on ch.id=ce.id where ch.patientId="+patientId+" and ch.visitNo="+visitNo+" order by ch.slNo,ch.id";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				examinationMap.get(rs.getString("id")).setResult(rs.getString("Result").replace("'", "''").trim());
				listClinicalExamination.add(new ClinicalExaminationItem(rs.getInt("slNo"),rs.getString("id"),rs.getString("HeadNameList"),rs.getString("Name"),rs.getString("Result").replace("'", "''").trim()));
			}


			sql = "select id,result from tbclinicalhistory where patientid="+patientId+" and visitno="+visitNo+" and id= 7;";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				String result = rs.getString("result");
				String drug = result.substring(0, result.indexOf('<'));
				/*if(result.startsWith("G")) {
					listPresentDrug.add(new PresentDrugItem("", "Generic",drug , result.substring(result.indexOf('<')+1, result.lastIndexOf('>'))));
				}else {
					listPresentDrug.add(new PresentDrugItem("", "Brand", drug, result.substring(result.indexOf('<')+1, result.lastIndexOf('>'))));
				}*/

			}

			tableClinicalExamination.setItems(listClinicalExamination);
			tablePrescribedCheapComplain.setItems(listPrescribedCheapComplain);
			tablePrescribedDisease.setItems(listPrescribedDisease);
			tablePrescribedInvestigation.setItems(listPrescribedInvestigation);
			tablePrescribedAdvise.setItems(listPrescribedAdvise);



			sql = "select id,result from tbclinicalhistory where patientid="+patientId+" and visitno="+visitNo+"";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				switch(rs.getInt("id")) {

				case 1:
					cheapComplaiArray.add(rs.getString("result"));
					break;
				case 2:
					presentIllnessArray.add(rs.getString("result"));
					break;
				case 3:
					pastIllnessArray.add(rs.getString("result"));
					break;
				case 4:
					fatherIllnessArray.add(rs.getString("result"));
					break;
				case 5:
					motherIllnessArray.add(rs.getString("result"));
					break;
				case 6:
					otherIllnessArray.add(rs.getString("result"));
					break;
				case 7:
					presentDrugArray.add(rs.getString("result"));
					break;
				case 8:
					recentDrugArray.add(rs.getString("result"));
					break;
				case 9:
					pastDrugArray.add(rs.getString("result"));
					break;
				case 10:
					allergicDrugArray.add(rs.getString("result"));
					break;
				case 11:
					setCmbJobProffession(rs.getString("result"));
					break;
				case 12:
					setCmbPosting(rs.getString("result"));
					break;
				case 13:
					setCmbDesignation(rs.getString("result"));
					break;
				case 14:
					if(rs.getString("result").equals("Yes")) {
						setCheckSmokingYes(true);
					}else {
						setCheckSmokingNO(true);
					}
					break;
				case 15:
					if(rs.getString("result").equals("Yes")) {
						setCheckBetelYes(true);
					}else {
						setCheckBetelNo(true);
					}
					break;
				case 16:
					if(rs.getString("result").equals("Regular")) {
						setCheckRegular(true);
					}else {
						setCheckEregular(true);
					}
					break;

				}
			}

			loadTreeByHead(treeItemPresentDrug,presentDrugArray);
			loadTreeByHead(treeItemRecentDrug,recentDrugArray);
			loadTreeByHead(treeItemPastDrug,pastDrugArray);
			loadTreeByHead(treeItemAllergicDrug,allergicDrugArray);
			//loadTreeByHead(cheapComplainTree,cheapComplaiArray);
			loadTreeByHead(treeItemPresentIllness,presentIllnessArray);
			loadTreeByHead(treeItemPastIllness,pastIllnessArray);
			loadTreeByHead(treeItemFatherIllness,fatherIllnessArray);
			loadTreeByHead(treeItemMotherIllness,motherIllnessArray);
			loadTreeByHead(treeItemOtherIllness,otherIllnessArray);
			treeDrugHistoryLoad();
			treeIllnessHistoryLoad();

			sql = "select slNo,id,(select question from tbsurveyquestion sq where sq.id = sh.id) as question from tbsurveyhistory sh where patientid="+patientId+" and visitNo = "+visitNo+" order by autoId;";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				listSelectQuerySurvey.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("id"),"", rs.getString("question"),surveyType));
			}



		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void prescriptionFindByPatientIdAndVisitNo(String patientId,String visitNo) {
		try {
			sql="Select * from tbpatientDetails where patientid="+patientId+"";
			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {

				setCmbPatientName(rs.getString("Name"));
				setTxtContactNo(rs.getString("ContactNo"));
				setCmbSex(rs.getString("sex"));
				setCmbAddress(rs.getString("Address"));

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Patient Id Not Exist!","Please Select valid Patient Id...");
				cmbId.requestFocus();
			}

			//visitNoLoadByPatientId(patientId);



			sql="Select date,age from tbvisithistory where PatientId="+patientId+" and visitNo = '"+visitNo+"'";
			rs = databaseHandler.execQuery(sql);
			if(rs.next()){
				setTxtAge(rs.getString("age"));
				setDate(rs.getDate("Date"));
			}

			//Load Drug Table
			sql="Select * from tbmedicinehistory where patientId="+patientId+" and visitNo="+visitNo+"";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				//listPrescribedMedicine.add(new Medicine(rs.getString("MedicineId"),rs.getString("medicineName"),rs.getString("Schedule"),rs.getString("time"),rs.getString("Course")) );
				listPrescribedMedicine.add(new MedicineItemInfo(rs.getInt("slNo"),rs.getString("MedicineId"),rs.getString("patientId"),rs.getString("medicineName"),"",rs.getString("Schedule"),rs.getString("time"),rs.getString("Course"),NodeType.MEDICINE_BRAND.getType()) );
			}


			//Load Diagnosis Table

			sql="Select *  from tbdiagnosishistory as dh where patientId="+patientId+" and visitNo="+visitNo+"";
			rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listPrescribedDisease.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("diagnosisid"),"", rs.getString("diagnosisName"),NodeType.DISEASE.getType()));
			}



			//Load Investigation Table
			sql="Select *  from tbtesthistory as th where patientId="+patientId+" and visitNo="+visitNo+"";
			rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listPrescribedInvestigation.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("testId"),"",rs.getString("TestName"),NodeType.INVESTIGATION.getType()));
			}



			//Load Advise Table
			sql="Select *  from tbadvisehistory as ah where patientId="+patientId+" and visitNo="+visitNo+"";
			rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listPrescribedAdvise.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("adviseid"),"",rs.getString("advise"),NodeType.ADVISE.getType()));
			}


			//Load CC Table
			sql="Select * from tbcchistory as ch where patientId="+patientId+" and visitNo="+visitNo+"";
			rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				listPrescribedCheapComplain.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("id"),"",rs.getString("CheapComplain"),cheapComplainType));
			}


			//Load Clinical Examination Table
			sql="select ch.slNo,ch.id,ce.HeadNameList,ce.Name,ch.result from tbcinicalexaminationhistory ch join tbclinicalexamination ce on ch.id=ce.id where ch.patientId="+patientId+" and ch.visitNo="+visitNo+" order by ch.slNo,ch.id";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				examinationMap.get(rs.getString("id")).setResult(rs.getString("Result").replace("'", "''").trim());
				listClinicalExamination.add(new ClinicalExaminationItem(rs.getInt("slNo"),rs.getString("id"),rs.getString("HeadNameList"),rs.getString("Name"),rs.getString("Result").replace("'", "''").trim()));
			}


			sql = "select id,result from tbclinicalhistory where patientid="+patientId+" and visitno="+visitNo+" and id= 7;";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				String result = rs.getString("result");
				String drug = result.substring(0, result.indexOf('<'));
				/*if(result.startsWith("G")) {
					listPresentDrug.add(new PresentDrugItem("", "Generic",drug , result.substring(result.indexOf('<')+1, result.lastIndexOf('>'))));
				}else {
					listPresentDrug.add(new PresentDrugItem("", "Brand", drug, result.substring(result.indexOf('<')+1, result.lastIndexOf('>'))));
				}*/

			}

			tableClinicalExamination.setItems(listClinicalExamination);
			tablePrescribedCheapComplain.setItems(listPrescribedCheapComplain);
			tablePrescribedDisease.setItems(listPrescribedDisease);
			tablePrescribedInvestigation.setItems(listPrescribedInvestigation);
			tablePrescribedAdvise.setItems(listPrescribedAdvise);
			//tablePresentDrug.setItems(listPresentDrug);


			sql = "select id,result from tbclinicalhistory where patientid="+patientId+" and visitno="+visitNo+"";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				switch(rs.getInt("id")) {

				case 1:
					cheapComplaiArray.add(rs.getString("result"));
					break;
				case 2:
					presentIllnessArray.add(rs.getString("result"));
					break;
				case 3:
					pastIllnessArray.add(rs.getString("result"));
					break;
				case 4:
					fatherIllnessArray.add(rs.getString("result"));
					break;
				case 5:
					motherIllnessArray.add(rs.getString("result"));
					break;
				case 6:
					otherIllnessArray.add(rs.getString("result"));
					break;
				case 7:
					presentDrugArray.add(rs.getString("result"));
					break;
				case 8:
					recentDrugArray.add(rs.getString("result"));
					break;
				case 9:
					pastDrugArray.add(rs.getString("result"));
					break;
				case 10:
					allergicDrugArray.add(rs.getString("result"));
					break;
				case 11:
					setCmbJobProffession(rs.getString("result"));
					break;
				case 12:
					setCmbPosting(rs.getString("result"));
					break;
				case 13:
					setCmbDesignation(rs.getString("result"));
					break;
				case 14:
					if(rs.getString("result").equals("Yes")) {
						setCheckSmokingYes(true);
					}else {
						setCheckSmokingNO(true);
					}
					break;
				case 15:
					if(rs.getString("result").equals("Yes")) {
						setCheckBetelYes(true);
					}else {
						setCheckBetelNo(true);
					}
					break;
				case 16:
					if(rs.getString("result").equals("Regular")) {
						setCheckRegular(true);
					}else {
						setCheckEregular(true);
					}
					break;

				}
			}

			loadTreeByHead(treeItemPresentDrug,presentDrugArray);
			loadTreeByHead(treeItemRecentDrug,recentDrugArray);
			loadTreeByHead(treeItemPastDrug,pastDrugArray);
			loadTreeByHead(treeItemAllergicDrug,allergicDrugArray);
			//loadTreeByHead(cheapComplainTree,cheapComplaiArray);
			loadTreeByHead(treeItemPresentIllness,presentIllnessArray);
			loadTreeByHead(treeItemPastIllness,pastIllnessArray);
			loadTreeByHead(treeItemFatherIllness,fatherIllnessArray);
			loadTreeByHead(treeItemMotherIllness,motherIllnessArray);
			loadTreeByHead(treeItemOtherIllness,otherIllnessArray);
			treeDrugHistoryLoad();
			treeIllnessHistoryLoad();

			sql = "select slNo,id,(select question from tbsurveyquestion sq where sq.id = sh.id) as question from tbsurveyhistory sh where patientid="+patientId+" and visitNo = "+visitNo+";";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				listSelectQuerySurvey.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("id"),"", rs.getString("question"),surveyType));
			}



		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private boolean validationCheck() {
		if(!getCmbId().isEmpty()) {
			if(!getCmbPatientName().isEmpty()) {
				if(listPrescribedMedicine.size()>0||listPrescribedInvestigation.size()>0|| listPrescribedDisease.size()>0) {
					return true;
				}else {

					new Notification(Pos.TOP_CENTER, "Information graphic", "Prescription is not ready yet....!","Please Enter Any Disease/Investigation/Drug for ready a prescription...");
					cmbDisease.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Patient Name!","Please Enter Patient Name...");
				cmbPatientName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Patient Id!","Please Enter Patient Id...");
			cmbId.requestFocus();
		}
		return false;
	}
	private String nextVisit(String nextVisitDay) {
		// TODO Auto-generated method stub
		int n = Integer.valueOf(nextVisitDay);


		if(n==0) {
			return "";
		}else if(n>0 && n<28 && n%7 == 0){
			return nextWeek(n);
		}else if(n>0 && n<30 && n%7 != 0) {
			return nextDay(n);
		}else if(n>0 && n>=28 && (n%7 == 0 || n%30 == 0 || ((n%30)/30.0)==.5)) {
			return nextMonth(n);
		}else {
			return nextDay(n);
		}
	}

	private String nextMonth(int days) {
		int temp,n=0;
		String next = "পরবর্তী সাক্ষাত ";
		n = days/30;
		temp = n/10;
		while(temp != 0) {
			switch(temp) {
			case 1:
				next += "১";
				break;
			case 2:
				next += "২";
				break;
			case 3:
				next += "৩";
				break;
			case 4:
				next += "৪";
				break;
			case 5:
				next += "৫";
				break;
			case 6:
				next += "৬";
				break;
			case 7:
				next += "৭";
				break;
			case 8:
				next += "৮";
				break;
			case 9:
				next += "৯";
				break;
			}
			n = n%10;
			temp = n/10;
		}
		temp = n;
		switch(temp) {
		case 0:
			next += "০";
			break;
		case 1:
			next += "১";
			break;
		case 2:
			next += "২";
			break;
		case 3:
			next += "৩";
			break;
		case 4:
			next += "৪";
			break;
		case 5:
			next += "৫";
			break;
		case 6:
			next += "৬";
			break;
		case 7:
			next += "৭";
			break;
		case 8:
			next += "৮";
			break;
		case 9:
			next += "৯";
			break;
		}
		n = days % 30;
		double mod = n / 30.0;

		if(mod > .33 && mod < .67) {
			next += ".৫";
		}else if(mod > .5) {
			next = "পরবর্তী সাক্ষাত ";
			n = (days/30)+1;

			temp = n/10;
			while(temp != 0) {
				switch(temp) {
				case 1:
					next += "১";
					break;
				case 2:
					next += "২";
					break;
				case 3:
					next += "৩";
					break;
				case 4:
					next += "৪";
					break;
				case 5:
					next += "৫";
					break;
				case 6:
					next += "৬";
					break;
				case 7:
					next += "৭";
					break;
				case 8:
					next += "৮";
					break;
				case 9:
					next += "৯";
					break;
				}
				n = n%10;
				temp = n/10;
			}
			temp = n;
			switch(temp) {
			case 0:
				next += "০";
				break;
			case 1:
				next += "১";
				break;
			case 2:
				next += "২";
				break;
			case 3:
				next += "৩";
				break;
			case 4:
				next += "৪";
				break;
			case 5:
				next += "৫";
				break;
			case 6:
				next += "৬";
				break;
			case 7:
				next += "৭";
				break;
			case 8:
				next += "৮";
				break;
			case 9:
				next += "৯";
				break;
			}
		}


		return next+" মাস  পর। ";
	}

	private String nextWeek(int n) {
		int temp;
		String next = "পরবর্তী সাক্ষাত ";

		temp = n/7;
		switch(temp) {
		case 0:
			next += "০";
			break;
		case 1:
			next += "১";
			break;
		case 2:
			next += "২";
			break;
		case 3:
			next += "৩";
			break;
		case 4:
			next += "৪";
			break;
		case 5:
			next += "৫";
			break;
		case 6:
			next += "৬";
			break;
		case 7:
			next += "৭";
			break;
		case 8:
			next += "৮";
			break;
		case 9:
			next += "৯";
			break;
		}

		return next+" সপ্তাহ পর। ";
	}
	private String nextDay(int n) {
		int temp;
		String next = "পরবর্তী সাক্ষাত ";
		temp = n/10;
		while(temp != 0) {
			switch(temp) {
			case 1:
				next += "১";
				break;
			case 2:
				next += "২";
				break;
			case 3:
				next += "৩";
				break;
			case 4:
				next += "৪";
				break;
			case 5:
				next += "৫";
				break;
			case 6:
				next += "৬";
				break;
			case 7:
				next += "৭";
				break;
			case 8:
				next += "৮";
				break;
			case 9:
				next += "৯";
				break;
			}
			n = n%10;
			temp = n/10;
		}
		temp = n;
		switch(temp) {
		case 0:
			next += "০";
			break;
		case 1:
			next += "১";
			break;
		case 2:
			next += "২";
			break;
		case 3:
			next += "৩";
			break;
		case 4:
			next += "৪";
			break;
		case 5:
			next += "৫";
			break;
		case 6:
			next += "৬";
			break;
		case 7:
			next += "৭";
			break;
		case 8:
			next += "৮";
			break;
		case 9:
			next += "৯";
			break;
		}

		return next+" দিন পর। ";
	}
	private String investigations(String patientid, String visitNo) {
		String investigation="";
		try {

			sql = "SELECT testName FROM tbtestHistory WHERE patientId = '"+patientid+"' and visitNo='"+visitNo+"' order by slNo";

			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) investigation += rs.getString("testName");
			while(rs.next()) {
				investigation += ", "+rs.getString("testName");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return investigation;
	}

	private String diagnosis(String patientid, String visitNo) {
		String diagnosis="";
		try {

			sql = "SELECT diagnosisName from tbdiagnosishistory WHERE patientId = '"+patientid+"' and visitNo='"+visitNo+"' order by slNo";

			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) diagnosis += rs.getString("diagnosisName");
			while(rs.next()) {
				diagnosis += ", "+rs.getString("diagnosisName");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return diagnosis;
	}

	private String drugHistory(String patientid, String visitNo) {
		String presentDrug="";
		String tempResult;
		try {

			sql = "SELECT id,result from tbclinicalhistory WHERE patientId = '"+patientid+"' and visitNo='"+visitNo+"' and id= 7   order by id";

			ResultSet rs = databaseHandler.execQuery(sql);

			if(rs.next()) {
				tempResult = rs.getString("result");
				presentDrug += "<b>Present Drugs:</b>"+tempResult.substring(tempResult.indexOf('>')+1, tempResult.indexOf('<'));
			}
			while(rs.next()) {
				tempResult = rs.getString("result");
				presentDrug += ", "+tempResult.substring(tempResult.indexOf('>')+1, tempResult.indexOf('<'));
			}

			sql = "SELECT id,result from tbclinicalhistory WHERE patientId = '"+patientid+"' and visitNo='"+visitNo+"' and id= 8   order by id";

			rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				tempResult = rs.getString("result");
				presentDrug += "<br> <b>Recent Drugs:</b>"+tempResult.substring(tempResult.indexOf('>')+1);
			}
			while(rs.next()) {
				tempResult = rs.getString("result");
				presentDrug += ", "+tempResult.substring(tempResult.indexOf('>')+1);
			}


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return presentDrug;
	}


	/*@FXML
	private void btnInstanceMedicineCreateAction(ActionEvent event) {
		if(!getCmbSystemNameMed().isEmpty()) {
			if(duplicateCheck(getCmbSystemNameMed(),0)) {
				if(confirmationCheck("Save This System Name?")) {
					insertToMedicineGroup(getMedicineMaxId(),getCmbSystemNameMed(),"0",0);
				}

			}
		}
		if(!getCmbDiseaseNameMed().isEmpty()) {
			if(duplicateCheck(getCmbDiseaseNameMed(),1,getCmbSystemNameMed())) { 
				if(!getCmbSystemNameMed().isEmpty()) {
					if(confirmationCheck("Save This Disease Name?"))
						insertToMedicineGroup(getMedicineMaxId(),getCmbDiseaseNameMed(),getDrugId(getCmbSystemNameMed(),0),1);
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select System Name!","Please Select Valid System Name...");
					cmbSystemNameMed.requestFocus();
				}
			}
		}
		if(!getCmbGroupName().isEmpty()) {
			if(duplicateCheck(getCmbGroupName(),2,getCmbDiseaseNameMed())) {
				if(!getCmbDiseaseNameMed().isEmpty()) {
					if(confirmationCheck("Save This Group Name?"))
						insertToMedicineGroup(getMedicineMaxId(),getCmbGroupName(),getDrugId(getCmbDiseaseNameMed(),1),2);
				}else {

					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Disease Name!","Please Select Valid Disease Name..");
					cmbDiseaseNameMed.requestFocus();
				}
			}
		}
		if(!getCmbGenericName().isEmpty()) {
			if(duplicateCheck(getCmbGenericName(),3,getCmbGroupName())) {
				if(!getCmbGroupName().isEmpty()) {
					if(confirmationCheck("Save This Generic Name?"))	
						insertToMedicineGroup(getMedicineMaxId(),getCmbGenericName(),getDrugId(getCmbGroupName(),2),3);
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Group!","Please Select Valid Group Name..");
					cmbGroupName.requestFocus();
				}

			}
		}
		if(!getCmbBrandName().isEmpty()) {
			if(duplicateCheck(getCmbBrandName(),4,getCmbGenericName())) {
				if(!getCmbGenericName().isEmpty()) {
					String medicineid = getMedicineMaxId();

					if(confirmationCheck("Save This Brand Name?")) {
						insertToMedicineGroup(medicineid,getCmbBrandName(),getDrugId(getCmbGenericName(),3),4);

						if (!getCmbCompanyName().isEmpty()) {
							String sql4="insert into tbmedicinecompany values('"+medicineid+"','"+getCmbCompanyName()+"')";
							try {
								databaseHandler.execAction(sql4);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Generic!","Please Select Valid Generic Name...");
					cmbGenericName.requestFocus();
				}

			}
		}
		companyNameComboLoad();
	}*/





	@FXML
	private void systemTableClickAction(MouseEvent event){
		if(event.getButton().equals(MouseButton.PRIMARY)){
			if(tableSystem.getSelectionModel().getSelectedItem() != null){

				TableItemInfo tempItem = tableSystem.getSelectionModel().getSelectedItem();

				setTxtSystemName(tempItem.getItemName());
				tableCheapComplain.setItems(LoadedInfo.getCheapComplainListBySystemId(tempItem.getItemId()));
				tableDisease.setItems(LoadedInfo.getDiseaseListBySystemId(tempItem.getItemId()));
				tableMedicineGroup.setItems(LoadedInfo.getMedicineGroupListBySystemId(tempItem.getItemId()));
				tableGeneric.setItems(LoadedInfo.getGenericListBySystemId(tempItem.getItemId()));
				tableInvestigation.setItems(LoadedInfo.getInvestigationListBySystemId(tempItem.getItemId()));
				tableAdvise.setItems(LoadedInfo.getAdviseListBySystemId(tempItem.getItemId()));
				tableCheapComplain.refresh();
				tableDisease.refresh();
				tableMedicineGroup.refresh();
				tableGeneric.refresh();
				tableInvestigation.refresh();
				tableAdvise.refresh();
			}	
		}
	}

	@FXML
	private void systemTableHistoryClickAction(MouseEvent event){
		if(event.getButton().equals(MouseButton.PRIMARY)){
			if(tableSystemHistory.getSelectionModel().getSelectedItem() != null){

				TableItemInfo tempItem = tableSystemHistory.getSelectionModel().getSelectedItem();

				setTxtSystemNameHistory(tempItem.getItemName());
				setCmbSystemNameHistorySurvey(tempItem.getItemName());
				tableDiseaseHistory.setItems(LoadedInfo.getDiseaseListBySystemId(tempItem.getItemId()));
				tableDiseaseHistory.refresh();

			}	
		}
	}

	@FXML
	private void diseaseTableClickAction(MouseEvent event) {
		if(tableDisease.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempItem = tableDisease.getSelectionModel().getSelectedItem();

			tableMedicineGroup.setItems(LoadedInfo.getMedicineGroupListByDiseaseId(tempItem.getItemId()));
			tableGeneric.setItems(LoadedInfo.getGenericListByDiseaseId(tempItem.getItemId()));
			tableInvestigation.setItems(LoadedInfo.getInvestigationListByDiseaseId(tempItem.getItemId()));
			tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(tempItem.getItemId()));
			tableMedicineGroup.refresh();
			tableGeneric.refresh();
			tableInvestigation.refresh();
			tableAdvise.refresh();
			if(event.getButton().equals(MouseButton.PRIMARY)){
				if(tableDisease.getSelectionModel().getSelectedItem() != null){
					if(event.getClickCount() == 2){
						addPrescribedDiseaseItems(tableDisease.getSelectionModel().getSelectedItem());
					}else if(event.getClickCount() == 1){
						setTxtDiseaseName(tableDisease.getSelectionModel().getSelectedItem().getItemName());
					}
				}
			}
			//investigationComboBoxLoad(cmbInvestigationName, 1, tempItem.getItemName());
			//adviseComboBoxLoad(tempItem.getItemName(), 1);
		}
	}


	@FXML
	private void diseaseTableHistoryClickAction(MouseEvent event) {
		if(tableDiseaseHistory.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempItem = tableDiseaseHistory.getSelectionModel().getSelectedItem();

			if(event.getButton().equals(MouseButton.PRIMARY)){
				if(tableDiseaseHistory.getSelectionModel().getSelectedItem() != null){
					if(event.getClickCount() == 2){
						//addPrescribedDiseaseItems(tableDisease.getSelectionModel().getSelectedItem());
						setCmbDiseaseHistoryIllness(tableDiseaseHistory.getSelectionModel().getSelectedItem().getItemName());
						illnessHistoryAddAction(null);
					}else if(event.getClickCount() == 1){
						setCmbDiseaseHistoryIllness(tableDiseaseHistory.getSelectionModel().getSelectedItem().getItemName());
						setTxtDiseaseNameHistory(tableDiseaseHistory.getSelectionModel().getSelectedItem().getItemName());
					}
				}
			}
			//investigationComboBoxLoad(cmbInvestigationName, 1, tempItem.getItemName());
			//adviseComboBoxLoad(tempItem.getItemName(), 1);
		}
	}


	@FXML
	private void medicineGroupTableClickAction(MouseEvent event) {
		if(tableMedicineGroup.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempItem = tableMedicineGroup.getSelectionModel().getSelectedItem();

			tableGeneric.setItems(LoadedInfo.getGenericListByMedicineGroupId(tempItem.getItemId()));

			tableGeneric.refresh();
			if(event.getButton().equals(MouseButton.PRIMARY)){

				if(event.getClickCount() == 1){
					setTxtMedicineGroup(tableMedicineGroup.getSelectionModel().getSelectedItem().getItemName());
				}

			}
		}
	}

	@FXML
	private void genericTableClickAction(MouseEvent event) {
		if(tableGeneric.getSelectionModel().getSelectedItem() != null) {


			tableMedicineBrand.setItems(LoadedInfo.getBrandListByGenericId(tableGeneric.getSelectionModel().getSelectedItem().getItemId()));

			tableMedicineBrand.refresh();

		}
	}

	@FXML
	private void cheapComplainTableClickAction(MouseEvent event) {
		if(tableCheapComplain.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempItem = tableCheapComplain.getSelectionModel().getSelectedItem();


			if(event.getButton().equals(MouseButton.PRIMARY)){
				if(tableCheapComplain.getSelectionModel().getSelectedItem() != null){
					if(event.getClickCount() == 2){
						addPrescribedCheapComplainItems(tableCheapComplain.getSelectionModel().getSelectedItem());
					}else if(event.getClickCount() == 1){
						setTxtCheapComplain(tableCheapComplain.getSelectionModel().getSelectedItem().getItemName());

						TableItemInfo temp = tableCheapComplain.getSelectionModel().getSelectedItem();
						stageCcSuggest.setCheapComplainId(temp.getItemId());
						stageCcSuggest.setCheapComplain(temp.getItemName());
						stageCcSuggest.setList(temp.getItemId());

					}




				}
			}
			//investigationComboBoxLoad(cmbInvestigationName, 1, tempItem.getItemName());
			//adviseComboBoxLoad(tempItem.getItemName(), 1);
		}
	}

	@FXML
	private void medicineBrandTableClickAction(MouseEvent event) {
		if(tableMedicineBrand.getSelectionModel().getSelectedItem() != null) {

			MedicineItemInfo tempMedicine = tableMedicineBrand.getSelectionModel().getSelectedItem();
			if(event.getButton().equals(MouseButton.PRIMARY)){
				if(tableMedicineBrand.getSelectionModel().getSelectedItem() != null){
					MedicineItemInfo temp = tableMedicineBrand.getSelectionModel().getSelectedItem();
					if(event.getClickCount() == 2){
						addPrescribedMedicine(temp);
					}else if(event.getClickCount() == 1){
						setTxtMedicineBrand(temp.getItemName());
						setCmbSchedule(temp.getSchedule());
						setCmbTime(temp.getTime());
						setCmbCourse(temp.getCourse());
						setCmbCompanyName(temp.getCompanyName());
					}
				}
			}

		}
	}

	@FXML
	private void prescribedCheapComplainTableClickAction(MouseEvent event) {
		if(tablePrescribedCheapComplain.getSelectionModel().getSelectedItem() != null) {
			TableItemInfo tempItem = tablePrescribedCheapComplain.getSelectionModel().getSelectedItem();

			if(event.getButton().equals(MouseButton.PRIMARY)){
				setTxtCheapComplain(tablePrescribedCheapComplain.getSelectionModel().getSelectedItem().getItemName());

				TableItemInfo temp = tablePrescribedCheapComplain.getSelectionModel().getSelectedItem();
				stageCcSuggest.setCheapComplainId(temp.getItemId());
				stageCcSuggest.setCheapComplain(temp.getItemName());
				stageCcSuggest.setList(temp.getItemId());

			}
			//investigationComboBoxLoad(cmbInvestigationName, 1, tempItem.getItemName());
			//adviseComboBoxLoad(tempItem.getItemName(), 1);
		}
	}

	@FXML
	private void prescribedMedicineTableClickAction(MouseEvent event) {
		if(tablePrescribedMedicine.getSelectionModel().getSelectedItem() != null) {

			MedicineItemInfo tempMedicine = tablePrescribedMedicine.getSelectionModel().getSelectedItem();

			setTxtMedicineBrand(tempMedicine.getItemName());
			setCmbSchedule(tempMedicine.getSchedule());
			setCmbTime(tempMedicine.getTime());
			setCmbCourse(tempMedicine.getCourse());
		}
	}

	@FXML
	private void prescribedDiseaseTableClickAction(MouseEvent event) {
		if(tablePrescribedDisease.getSelectionModel().getSelectedItem() != null) {

			TableItemInfo tempItem = tablePrescribedDisease.getSelectionModel().getSelectedItem();

			tableMedicineGroup.setItems(LoadedInfo.getMedicineGroupListByDiseaseId(tempItem.getItemId()));
			tableGeneric.setItems(LoadedInfo.getGenericListByDiseaseId(tempItem.getItemId()));
			tableInvestigation.setItems(LoadedInfo.getInvestigationListByDiseaseId(tempItem.getItemId()));
			tableAdvise.setItems(LoadedInfo.getAdviseListByDiseaseId(tempItem.getItemId()));
			tableMedicineGroup.refresh();
			tableGeneric.refresh();
			tableInvestigation.refresh();
			tableAdvise.refresh();
		}
	}

	@FXML
	private void investigationTableClickAction(MouseEvent event) {
		if(tableInvestigation.getSelectionModel().getSelectedItem() != null){
			TableItemInfo tempItem = tableInvestigation.getSelectionModel().getSelectedItem();


			if(event.getButton().equals(MouseButton.PRIMARY)){

				if(event.getClickCount() == 2){
					addPrescribedInvestigation(tempItem);
				}else if(event.getClickCount() == 1){
					setTxtInvestigation(tempItem.getItemName());
				}
			}
		}
	}
	@FXML
	private void investigationGroupTableClickAction(MouseEvent event) {
		if(tableInvestigationGroup.getSelectionModel().getSelectedItem() != null){
			TableItemInfo tempItem = tableInvestigationGroup.getSelectionModel().getSelectedItem();


			if(event.getButton().equals(MouseButton.PRIMARY)){

				if(event.getClickCount() == 2){
					ObservableList<TableItemInfo> tempList = LoadedInfo.getInvestigationListByGroupId(tempItem.getItemId());
					for(int i= 0;i < tempList.size();i++){
						addPrescribedInvestigation(tempList.get(i));
					}

				}else if(event.getClickCount() == 1){
					tableInvestigation.setItems(LoadedInfo.getInvestigationListByGroupId(tempItem.getItemId()));
				}
			}
		}
	}
	@FXML
	private void adviseTableClickAction(MouseEvent event) {
		if(tableAdvise.getSelectionModel().getSelectedItem() != null){
			TableItemInfo tempItem = tableAdvise.getSelectionModel().getSelectedItem();


			if(event.getButton().equals(MouseButton.PRIMARY)){

				if(event.getClickCount() == 2){
					addPrescribedAdvise(tempItem);
				}else if(event.getClickCount() == 1){
					setTxtAdvise(tempItem.getItemName());
				}
			}
		}
	}

	@FXML
	private void adviseGroupTableClickAction(MouseEvent event) {
		if(tableAdviseGroup.getSelectionModel().getSelectedItem() != null){
			TableItemInfo tempItem = tableAdviseGroup.getSelectionModel().getSelectedItem();


			if(event.getButton().equals(MouseButton.PRIMARY)){

				if(event.getClickCount() == 2){
					ObservableList<TableItemInfo> tempList = LoadedInfo.getAdviseListByGroupId(tempItem.getItemId());
					for(int i= 0;i < tempList.size();i++){
						addPrescribedAdvise(tempList.get(i));
					}

				}else if(event.getClickCount() == 1){
					tableAdvise.setItems(LoadedInfo.getAdviseListByGroupId(tempItem.getItemId()));
				}
			}
		}
	}

	/*@FXML
	private void presentDrugTableClickAction(MouseEvent event) {
		if(tablePresentDrug.getSelectionModel().getSelectedItem() != null) {
			PresentDrugItem tempPresentDrug = tablePresentDrug.getSelectionModel().getSelectedItem();
			if(tempPresentDrug.getMedicineType().equals("Generic")) {
				setCmbGenericName(tempPresentDrug.getMedicineName());
				scheduleSetByGenericId(getDrugId(getCmbGenericName(), 3));
			}else {
				setCmbBrandName(tempPresentDrug.getMedicineName());

				if(!scheduleSetByGenericId(getDrugId(getCmbBrandName(), 4))) {
					scheduleSetByGenericId(getDrugHeadId(getCmbBrandName(), 4));
				}
			}	
			setCmbSchedule(tempPresentDrug.getSchedule());
		}
	}*/

	@FXML
	private void systemHistoryTableClickAction(MouseEvent event){
		if(event.getButton().equals(MouseButton.PRIMARY)){
			if(tableSystemHistory.getSelectionModel().getSelectedItem() != null){

				TableItemInfo tempItem = tableSystemHistory.getSelectionModel().getSelectedItem();

				setCmbSystemNameHistorySurvey(tempItem.getItemName());

			}	
		}
	}

	@FXML
	private void diseaseHistoryTableClickAction(MouseEvent event){
		if(event.getButton().equals(MouseButton.PRIMARY)){
			if(tableDiseaseHistory.getSelectionModel().getSelectedItem() != null){

				if(event.getButton().equals(MouseButton.PRIMARY)){
					if(tableDiseaseHistory.getSelectionModel().getSelectedItem() != null){
						if(event.getClickCount() == 2){
							//addPrescribedDiseaseItems(tableDiseaseHistory.getSelectionModel().getSelectedItem());
						}else if(event.getClickCount() == 1){
							setTxtDiseaseName(tableDiseaseHistory.getSelectionModel().getSelectedItem().getItemName());
						}
					}
				}

			}	
		}
	}

	@FXML
	private void surveyQuestionTableClickAction(MouseEvent event) {

		if(tableSystemicSurvey.getSelectionModel().getSelectedItem() != null) {

			if(event.getButton().equals(MouseButton.PRIMARY)){
				if(event.getClickCount() == 2){
					TableItemInfo tempTableItem = tableSystemicSurvey.getSelectionModel().getSelectedItem();

					int i;
					for( i=0;i<listSelectQuerySurvey.size();i++) {
						if(listSelectQuerySurvey.get(i).getItemName().equals(tempTableItem.getItemName())) {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","This Systemic Survey Query All ready exist");
							break;
						}
					}
					if(i==listSelectQuerySurvey.size()) {
						listSelectQuerySurvey.add(new TableItemInfo(tempTableItem.getSlNo(), tempTableItem.getItemId(),tempTableItem.getHeadId(),tempTableItem.getItemName(),tempTableItem.getType()));
						tableSelectQuerySurvey.setItems(listSelectQuerySurvey);

					}
				}
			}

		}
	}



	private void brandCompanyNameLoad() {
		try {

			mapCompanyName.clear();
			sql= "SELECT mg.id,mg.groupname,mc.Name FROM tbmedicineGroup mg \r\n" + 
					"JOIN tbmedicinecompany mc\r\n" + 
					"ON mg.id = mc.id WHERE type='4' group by groupname order by sn,groupname";

			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				mapCompanyName.put(rs.getString("groupname"), rs.getString("Name"));
			}

		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void drugComboBoxLoad(FxComboBox tempSuggest,int type) {
		try {
			tempSuggest.data.clear();
			tempSuggest.data.add("");
			sql= "select * from tbmedicineGroup where type='"+type+"' group by groupname order by sn,groupname";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				tempSuggest.data.add(rs.getString("GroupName"));
			}

			if(tempSuggest.data.size()<=1) {
				tempSuggest.setValue("Empty");
			}else {
				tempSuggest.setValue("");
			}

			tempSuggest.setLoadItems();
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void drugComboBoxLoad(FxComboBox tempSuggest,int type,String name) {
		String id=getDrugId(name,type);
		tempSuggest.data.clear();
		tempSuggest.data.add("");

		String sql="";
		if(name.equals("")) {
			sql="select * from tbmedicineGroup where type='"+(type+1)+"' group by groupname order by sn,groupname";
		}else {
			sql="select * from tbmedicineGroup where type='"+(type+1)+"' and pid='"+id+"' group by groupname order by sn,groupname";
		}
		try {
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				tempSuggest.data.add(rs.getString("GroupName"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}

		if(tempSuggest.data.size()<=1) {
			tempSuggest.setValue("Empty");
		}else {
			tempSuggest.setValue("");
		}

		tempSuggest.setLoadItems();

	}

	/*private void genericComboBoxLoad(String name,int type) {
		String id=getDrugId(name,type);
		cmbGenericName.data.clear();
		cmbGenericName.data.add("");

		String sql="";
		if(name.equals("")) {
			sql="select * from tbmedicineGroup where type='3' group by groupname order by sn,groupname";
		}else {
			sql="select * from tbmedicineGroup where type='3' and pid='"+id+"' group by groupname order by sn,groupname";
		}
		try {
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbGenericName.data.add(rs.getString("GroupName"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}

		if(cmbGenericName.data.size()<=1) {
			cmbGenericName.setValue("Empty");
		}else {
			cmbGenericName.setValue("");
		}

		cmbGenericName.setLoadItems();

	}*/

	private void investigationComboBoxLoad(FxComboBox tempSuggest,int type) {
		try {
			tempSuggest.data.clear();
			tempSuggest.data.add("");
			String sql= "select * from tbtestgroup where type='"+type+"' group by groupname order by groupname";

			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				tempSuggest.data.add(rs.getString("GroupName"));
			}

			if(tempSuggest.data.size()<=1) {
				tempSuggest.setValue("Empty");
			}else {
				tempSuggest.setValue("");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void investigationComboBoxLoad(FxComboBox tempSuggest,int type,String name) {
		String id=getDrugId(name,type);
		tempSuggest.data.clear();
		tempSuggest.data.add("");

		String sql="";
		if(name.equals("")) {
			sql="select * from tbtestgroup where type='"+(type+1)+"' group by groupname order by groupname;";
		}else {
			sql="select * from tbtestgroup where type='"+(type+1)+"' and pId='"+id+"' group by groupname order by groupname;";
		}
		try {
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				tempSuggest.data.add(rs.getString("GroupName"));
			}

			if(tempSuggest.data.size()<=1) {
				tempSuggest.setValue("Empty");
			}else {
				tempSuggest.setValue("");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	/*private void adviseComboBoxLoad(String name,int type) {
		try {

			if(name.equals("")) {
				sql="select groupName from tbadviseGroup group by GroupName order by GroupName";
			}else{
				sql= "select groupName from tbadviseGroup where headid="+getDrugId(name, type)+"  group by GroupName order by GroupName";
			}
			cmbAdvise.data.clear();

			ResultSet rs= databaseHandler.execQuery(sql);
			while (rs.next()){
				cmbAdvise.data.add(rs.getString("groupName"));
			}

			if(name.equals("")) {
				sql="select * from tbadvise group by advise order by advise";
			}else{
				sql= "select * from tbadvise where pid="+getDrugId(name, type)+"  group by advise order by advise";
			}

			rs= databaseHandler.execQuery(sql);
			while (rs.next()){
				cmbAdvise.data.add(rs.getString("advise"));
			}
			if(cmbAdvise.data.size()<=1) {
				setCmbAdvise("Empty");
			}else {
				setCmbAdvise("");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}*/

	/*private void adviseLoadBySystemName(String name) {
		try {

			cmbAdvise.data.clear();
			cmbAdvise.data.add("");
			if(name.equals("")) {
				sql = "select tg.id,tg.advise from tbmedicinegroup mg join tbadvise tg on mg.id=tg.pId where mg.type=1 group by advise order by advise";
			}else
				sql = "select tg.id,tg.advise from tbmedicinegroup mg join tbadvise tg on mg.id=tg.pId where mg.type=1 and mg.pid=(select id from tbmedicinegroup where groupname='"+name+"') group by advise order by advise";
			ResultSet rs1= databaseHandler.execQuery(sql);
			while(rs1.next()) {
				//modelAdvise.addRow(new Object[] {rs1.getString("tg.id"),rs1.getString("tg.advise"),"Del"});
				cmbAdvise.data.add(rs1.getString("tg.advise"));
			}

			if(cmbAdvise.data.size()<=1) {
				setCmbAdvise("Empty");
			}else {
				setCmbAdvise("");
			}

			cmbAdvise.setLoadItems();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}*/





	/*private void investigationLoadBySystemName(String systemName) {
		try {
			cmbInvestigationName.data.clear();
			cmbInvestigationName.data.add("");
			if(systemName.equals("")) {
				sql = "select tg.id,tg.GroupName from tbmedicinegroup mg join tbtestgroup tg on mg.id=tg.pId where mg.type=1 group by groupname order by sn,groupname";
			}else
				sql = "select tg.id,tg.GroupName from tbmedicinegroup mg join tbtestgroup tg on mg.id=tg.pId where mg.type=1 and mg.pid=(select id from tbmedicinegroup where groupname='"+systemName+"') group by groupname order by sn,groupname";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				//modelTest.addRow(new Object[] {rs.getString("tg.id"),rs.getString("tg.groupname"),"Del"});
				cmbInvestigationName.data.add(rs.getString("tg.groupname"));
			}

			if(cmbInvestigationName.data.size()<=1) {
				setCmbInvestigationName("Empty");
			}else {
				setCmbInvestigationName("");
			}

			cmbInvestigationName.setLoadItems();


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}*/
	private void loadCheapComplain(String systemName) {
		// TODO Auto-generated method stub
		try {
			cmbCheapComplain.data.clear();
			cmbCheapComplain.data.add("");
			if(systemName.equals("")) {
				sql = "select cc.id,cc.name from tbcc cc group by name order by name";
			}else
				sql = "select cc.id,cc.name from tbcc cc join tbmedicinegroup mg on cc.id= mg.id where cc.headId=(select id from tbmedicinegroup where groupname='"+systemName+"' and type = 0) group by cc.name order by cc.name";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()) {
				//modelTest.addRow(new Object[] {rs.getString("tg.id"),rs.getString("tg.groupname"),"Del"});
				cmbCheapComplain.data.add(rs.getString("cc.name"));
			}

			if(cmbCheapComplain.data.size()<=1) {
				setCmbCheapComplain("Empty");
			}else {
				setCmbCheapComplain("");
			}

			cmbCheapComplain.setLoadItems();


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void companyNameComboLoad() {
		try {
			cmbCompanyName.data.clear();
			cmbCompanyName.data.add("");
			String sql= "select * from tbmedicinecompany group by name ";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbCompanyName.data.add(rs.getString("name"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void  scheduleLoad(){
		try {

			cmbSchedule.data.clear();
			cmbSchedule.data.add("");

			cmbScheduleHistoryDrug.data.clear();
			cmbScheduleHistoryDrug.data.add("");

			String sql="select * from tbsuggestions where type=1";
			ResultSet rs= databaseHandler.execQuery(sql);
			while(rs.next()){
				cmbSchedule.data.add(rs.getString("suggestion"));
				cmbScheduleHistoryDrug.data.add(rs.getString("suggestion"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	private void  timeLoad(){
		try {

			cmbTime.data.clear();
			cmbTime.data.add("");
			String sql="select * from tbsuggestions where type=2";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()){
				cmbTime.data.add(rs.getString("suggestion"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void  courseLoad(){
		try {

			cmbCourse.data.clear();
			cmbCourse.data.add("");
			String sql="select * from tbsuggestions where type=3";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()){
				cmbCourse.data.add(rs.getString("suggestion"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void allPatientIdLoad() {
		try {
			sql="select patientid from  tbpatientdetails order by patientid desc";
			ResultSet rs=databaseHandler.execQuery(sql);
			isSearch = false;
			cmbId.data.clear();
			while(rs.next()) {
				cmbId.data.add(rs.getString("patientid"));
			}
			isSearch = true;
			//txtId.txtMrNo.setText(txtId.v.get(txtId.v.size()-1));
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
	}

	private void loadRefferName() {
		try {
			sql = "select refferedto from tbpatientdetails group by refferedto order by refferedto";
			cmbRefferTo.data.clear();
			cmbRefferTo.data.add("");
			ResultSet rs1 = databaseHandler.execQuery(sql);
			while(rs1.next()) {
				cmbRefferTo.data.add(rs1.getString("refferedto"));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void cmbPatientNameLoad() {
		try {
			cmbPatientName.data.clear();
			cmbPatientName.data.add("");
			ResultSet rs= databaseHandler.execQuery("select name from tbpatientdetails group by name order by name");
			while(rs.next()) {
				cmbPatientName.data.add(rs.getString("name"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void cmbAddressLoad() {
		try {
			cmbAddress.data.clear();
			cmbAddress.data.add("");
			ResultSet rs= databaseHandler.execQuery("select address from tbpatientdetails group by address order by address");

			while(rs.next()) {
				cmbAddress.data.add(rs.getString("address"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private void cmbRefferNameLoad() {
		try {

			sql = "select refferedto from tbpatientdetails group by refferedto order by refferedto";
			cmbRefferTo.data.clear();
			cmbRefferTo.data.add("");
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbRefferTo.data.add(rs.getString("refferedto"));		
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void cmbJobProffessionLoad() {
		// TODO Auto-generated method stub
		try {
			cmbJobProffession.data.clear();
			cmbJobProffession.data.add("");
			sql = "select id,result from tbclinicalhistory where id = 11 group by result order by result";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbJobProffession.data.add(rs.getString("result"));	
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void cmbDesignationLoad() {
		// TODO Auto-generated method stub
		try {
			cmbDesignation.data.clear();
			cmbDesignation.data.add("");
			sql = "select id,result from tbclinicalhistory where id = 12 group by result order by result";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbDesignation.data.add(rs.getString("result"));	
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void cmbPostingLoad() {
		// TODO Auto-generated method stub
		try {
			cmbPosting.data.clear();
			cmbPosting.data.add("");
			sql = "select id,result from tbclinicalhistory where id = 13 group by result order by result";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbPosting.data.add(rs.getString("result"));	
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}
	private void setMaxPatientId() {
		try {

			String year = String.valueOf(calender.get(Calendar.YEAR)).substring(2);
			String month = dfId.format(calender.get(Calendar.MONTH)+1);
			sql="select (select IFNULL(MAX(SUBSTRING(patientID,5)),0)+1)as patientId from tbpatientDetails WHERE patientid LIKE '"+year+month+"%'";
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				setCmbId(year+month+dfId.format(rs.getInt("patientid")));
			}
			//txtId.txtMrNo.setText(txtId.v.get(txtId.v.size()-1));
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
	}

	private void visitNoLoadByPatientId(String patientId) {
		try {
			isSearch = false;
			cmbVisitNo.getItems().clear();
			sql="select visitNo from tbvisithistory where patientId = '"+patientId+"' order by visitNo desc";
			ResultSet rs=databaseHandler.execQuery(sql);

			while(rs.next()) {
				cmbVisitNo.getItems().add(rs.getString("visitNo"));
			}
			if(cmbVisitNo.getItems().size()==0) {
				cmbVisitNo.getItems().add("0");

			}
			cmbVisitNo.getSelectionModel().select(0);
			isSearch = true;
			//txtId.txtMrNo.setText(txtId.v.get(txtId.v.size()-1));
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
	}
	private void surbeyTableLoadBySystem(String headId) {
		try {

			listSystemicSurvey.clear();

			if(headId.equals("0")) {
				sql = "select * from tbsurveyquestion";
			}else {
				sql = "select * from tbsurveyquestion where headid="+headId+" ";
			}


			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				listSystemicSurvey.add(new TableItemInfo(rs.getInt("slNo"),rs.getString("id"),rs.getString("headId"), rs.getString("question"),surveyType));
			}

			tableSystemicSurvey.setItems(listSystemicSurvey);

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e );
			e.printStackTrace();
		}
	}

	private void cheapComplainAddAction(String cheapComplain) {
		// TODO Auto-generated method stub
		if(!cheapComplain.isEmpty()) {
			int i;
			for( i=0;i<listPrescribedCheapComplain.size();i++) {
				if(listPrescribedCheapComplain.get(i).getItemName().equals(cheapComplain)) {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","This Cheap Complain Allready Exist");
					cmbCheapComplain.getEditor().selectAll();
					cmbCheapComplain.requestFocus();
					break;
				}
			}
			if(i==listPrescribedCheapComplain.size()) {
				listPrescribedCheapComplain.add(new TableItemInfo(i,getCCId(cheapComplain),"", cheapComplain,cheapComplainType));
				tablePrescribedCheapComplain.setItems(listPrescribedCheapComplain);

				cmbCheapComplain.requestFocus();
				cmbCheapComplain.getEditor().selectAll();
			}


		}

	}

	private boolean scheduleSetByGenericId(String genericId) {
		try {

			sql = "select *,(select suggestion from tbsuggestions s where s.id=sc.suggestionId) as suggession   from tbschedule sc where sc.genericid="+genericId+"";

			ResultSet rs= databaseHandler.execQuery(sql);

			setCmbSchedule("");
			setCmbTime("");
			setCmbCourse("");
			boolean isFound = false;
			while(rs.next()) {
				if(rs.getInt("type")==1) {
					setCmbSchedule(rs.getString("suggession"));
				}else if(rs.getInt("type")==2) {
					setCmbTime(rs.getString("suggession"));
				}else if(rs.getInt("type")==3) {
					setCmbCourse(rs.getString("suggession"));
				}
				isFound = true;
			}
			if(isFound)
				return true;
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}


		return false;
	}

	/*private void diseaseAddAction(String diseaseName) {
		// TODO Auto-generated method stub
		if(!diseaseName.isEmpty()) {
			int i;
			for( i=0;i<listPrescribedDisease.size();i++) {
				if(listPrescribedDisease.get(i).getItemName().equals(diseaseName)) {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","This Disease Name Allready Exist");
					cmbDiseaseNameInd.getEditor().selectAll();
					cmbDiseaseNameInd.requestFocus();
					break;
				}
			}
			if(i==listPrescribedDisease.size()) {
				listPrescribedDisease.add(new TableItemInfo(i,getDrugId(diseaseName, 1),"", diseaseName,diseaseType));
				tablePrescribedDisease.setItems(listPrescribedDisease);

				cmbDiseaseNameInd.requestFocus();
				cmbDiseaseNameInd.getEditor().selectAll();
			}


		}

	}*/

	/*private void investigationAddAction(String invesgationName) {
		// TODO Auto-generated method stub
		if(!invesgationName.isEmpty()) {
			int i;
			for( i=0;i<listPrescribedInvestigation.size();i++) {
				if(listPrescribedInvestigation.get(i).getItemName().equals(invesgationName)) {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","This Investigation Name Allready Exist");
					cmbInvestigationName.getEditor().selectAll();
					cmbInvestigationName.requestFocus();
					break;
				}
			}
			if(i==listPrescribedInvestigation.size()) {
				listPrescribedInvestigation.add(new TableItemInfo(i,getTestId(invesgationName, 2),"", invesgationName,testType));
				tablePrescribedInvestigation.setItems(listPrescribedInvestigation);

				cmbInvestigationName.requestFocus();
				cmbInvestigationName.getEditor().selectAll();
			}


		}

	}*/

	/*private void adviseAddAction(String advise) {
		// TODO Auto-generated method stub
		try {
			if(!advise.isEmpty()) {
				int i;
				if(isAdviseGroupExist(advise)) {
					sql = "SELECT a.id,a.advise FROM tbadvisegroup ag\r\n" + 
							"JOIN tbadvise a\r\n" + 
							"ON a.id = ag.adviseId WHERE groupname = '"+advise+"' ";
					ResultSet rs = databaseHandler.execQuery(sql);

					while(rs.next()) {
						String adviseId = rs.getString("id");
						advise = rs.getString("advise");
						i = 0;
						for( i=0;i<listPrescribedAdvise.size();i++) {
							if(listPrescribedAdvise.get(i).getItemName().equals(advise)) {

								break;
							}
						}
						if(i==listPrescribedAdvise.size()) {
							listPrescribedAdvise.add(new TableItemInfo(i,adviseId,"", advise,adviseType));

						}
					}

					tablePrescribedAdvise.setItems(listPrescribedAdvise);
					cmbAdvise.requestFocus();
					cmbAdvise.getEditor().selectAll();

				}else {
					for( i=0;i<listPrescribedAdvise.size();i++) {
						if(listPrescribedAdvise.get(i).getItemName().equals(advise)) {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Allready Exist!","This Advise Allready Exist");
							cmbAdvise.getEditor().selectAll();
							cmbAdvise.requestFocus();
							break;
						}
					}
					if(i==listPrescribedAdvise.size()) {
						listPrescribedAdvise.add(new TableItemInfo(i,getAnyId("tbadvise","advise" ,advise, 2),"", advise,adviseType));
						tablePrescribedAdvise.setItems(listPrescribedAdvise);

						cmbAdvise.requestFocus();
						cmbAdvise.getEditor().selectAll();
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	 */


	/*@FXML
	private void medicineAddAction(ActionEvent event) {
		// TODO Auto-generated method stub
		if(!getCmbBrandName().isEmpty()) {
			if(!getCmbSchedule().isEmpty()) {
				if(!getCmbCourse().isEmpty()) {

					listPrescribedMedicine.add(new MedicineItemInfo(listPrescribedMedicine.size(),LoadedInfo.getMedicineBrandInfo(getCmbBrandName()).getItemId(),LoadedInfo.getMedicineBrandInfo(getCmbBrandName()).getHeadId(),LoadedInfo.getMedicineBrandInfo(getCmbBrandName()).getCompanyName(), getCmbBrandName(), getCmbSchedule(), getCmbTime(), getCmbCourse(),NodeType.MEDICINE_BRAND.getType()));
					tablePrescribedMedicine.setItems(listPrescribedMedicine);

					setCmbGenericName("");
					//setCmbBrandName("");
					cmbGenericName.requestFocus();
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Course!","Please Select any Course..");
					cmbCourse.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Schedule!","Please Select any Schedule..");
				cmbSchedule.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Select Brand!","Please Select Brand Name..");
			cmbBrandName.requestFocus();
		}

	}*/

	@FXML
	private void drugHistoryAddAction(ActionEvent event) {
		// TODO Auto-generated method stub
		if(getRadioPresentDrug()) {
			if(!getCmbMedicineHistoryDrug().isEmpty()) {
				presentDrugArray.add("B-> "+getCmbMedicineHistoryDrug()+" <"+getCmbScheduleHistoryDrug()+">");
				//addInPresenDrugTable(presentDrugArray.get(presentDrugArray.size()-1));
			}else if(!getCmbGenericHistoryDrug().isEmpty()){
				presentDrugArray.add("G-> "+getCmbGenericHistoryDrug()+" <"+getCmbScheduleHistoryDrug()+">");
				//addInPresenDrugTable(presentDrugArray.get(presentDrugArray.size()-1));
			}
			loadTreeByHead(treeItemPresentDrug,presentDrugArray);

		}else if(getRadioRecentDrug()) {
			if(!getCmbMedicineHistoryDrug().isEmpty()) {
				recentDrugArray.add("B-> "+getCmbMedicineHistoryDrug());
			}else if(!getCmbGenericHistoryDrug().isEmpty()){
				recentDrugArray.add("G-> "+getCmbGenericHistoryDrug());
			}
			loadTreeByHead(treeItemRecentDrug,recentDrugArray);
		}else if(getRadioPastDrug()) {
			if(!getCmbMedicineHistoryDrug().isEmpty()) {
				pastDrugArray.add("B-> "+getCmbMedicineHistoryDrug());
			}else if(!getCmbGenericHistoryDrug().isEmpty()){
				pastDrugArray.add("G-> "+getCmbGenericHistoryDrug());
			}
			loadTreeByHead(treeItemPastDrug,pastDrugArray);
		}else {
			if(!getCmbMedicineHistoryDrug().isEmpty()) {
				allergicDrugArray.add("B-> "+getCmbMedicineHistoryDrug());
			}else if(!getCmbGenericHistoryDrug().isEmpty()){
				allergicDrugArray.add("G-> "+getCmbGenericHistoryDrug());
			}	
			loadTreeByHead(treeItemAllergicDrug,allergicDrugArray);
		}

		treeDrugHistoryLoad();
		cmbMedicineHistoryDrug.requestFocus();
	}

	@FXML
	private void illnessHistoryAddAction(ActionEvent event) {
		// TODO Auto-generated method stub
		if(getRadioPresentIllness()) {
			if(!getCmbDiseaseHistoryIllness().isEmpty()) {
				presentIllnessArray.add(getCmbDiseaseHistoryIllness().trim());
				addInDiseaseTable(getCmbDiseaseHistoryIllness());
				loadTreeByHead(treeItemPresentIllness,presentIllnessArray);
			}
		}else if(getRadioPastIllness()) {
			if(!getCmbDiseaseHistoryIllness().isEmpty()) {
				pastIllnessArray.add(getCmbDiseaseHistoryIllness());
				loadTreeByHead(treeItemPastIllness,pastIllnessArray);
			}
		}else if(getRadioFatherIllness()) {
			if(!getCmbDiseaseHistoryIllness().isEmpty()) {
				fatherIllnessArray.add(getCmbDiseaseHistoryIllness());
				loadTreeByHead(treeItemFatherIllness,fatherIllnessArray);
			}
		}else if(getRadioMotherIllness()) {
			if(!getCmbDiseaseHistoryIllness().isEmpty()) {
				motherIllnessArray.add(getCmbDiseaseHistoryIllness());
				loadTreeByHead(treeItemMotherIllness,motherIllnessArray);
			}
		}else if(getRadioOtherIllness()) {
			if(!getCmbDiseaseHistoryIllness().isEmpty()) {
				otherIllnessArray.add(getCmbDiseaseHistoryIllness());
				loadTreeByHead(treeItemOtherIllness,otherIllnessArray);
			}
		}

		treeIllnessHistoryLoad();
		cmbDiseaseHistoryIllness.requestFocus();

	}

	private void deleteIllnessHistoryTreeItemAction() {
		// TODO Auto-generated method stub
		if(treeIllnessHistory.getSelectionModel().getSelectedItem() != null ) {
			TreeItem<String> tempItem = (TreeItem) treeIllnessHistory.getSelectionModel().getSelectedItem();

			String parent = tempItem.getParent().getValue();

			if(parent.equals("Present Illness")) {
				presentIllnessArray.remove(tempItem.getValue());
				treeItemPresentIllness.getChildren().remove(tempItem);
			}else if(parent.equals("Past Illness")) {
				pastIllnessArray.remove(tempItem.getValue());
				treeItemPastIllness.getChildren().remove(tempItem);
			}else if(parent.equals("Father Illness")) {
				fatherIllnessArray.remove(tempItem.getValue());
				treeItemFatherIllness.getChildren().remove(tempItem);
			}else if(parent.equals("Mother Illness")) {
				motherIllnessArray.remove(tempItem.getValue());
				treeItemMotherIllness.getChildren().remove(tempItem);
			}else if(parent.equals("Other Illness")) {
				otherIllnessArray.remove(tempItem.getValue());
				treeItemOtherIllness.getChildren().remove(tempItem);
			}
		}
	}
	private void deleteDrugTreeItemAction() {
		// TODO Auto-generated method stub
		if(treeDrugHistory.getSelectionModel().getSelectedItem() != null ) {
			TreeItem<String> tempItem = (TreeItem) treeDrugHistory.getSelectionModel().getSelectedItem();

			String parent = tempItem.getParent().getValue();

			if(parent.equals("Present Drug")) {
				presentDrugArray.remove(tempItem.getValue());
				treeItemPresentDrug.getChildren().remove(tempItem);
			}else if(parent.equals("Recent Drug")) {
				recentDrugArray.remove(tempItem.getValue());
				treeItemRecentDrug.getChildren().remove(tempItem);
			}else if(parent.equals("Past Drug")) {
				pastDrugArray.remove(tempItem.getValue());
				treeItemPastDrug.getChildren().remove(tempItem);
			}else if(parent.equals("Allergic Drug")) {
				allergicDrugArray.remove(tempItem.getValue());
				treeItemAllergicDrug.getChildren().remove(tempItem);
			}
		}
	}

	private void loadTreeByHead(TreeItem<String> treeNode, ArrayList<String> arrayList) {
		// TODO Auto-generated method stub
		treeNode.getChildren().clear();
		for(int i=0;i<arrayList.size();i++) {
			treeNode.getChildren().add((new TreeItem(arrayList.get(i))));
		}


	}

	/*protected void addInPresenDrugTable(String drugName) {
		try {

			String brand= drugName.substring(4, drugName.indexOf("<")).trim();
			String schedule= drugName.substring(drugName.indexOf("<")+1,drugName.length()-1);

			if(drugName.startsWith("G")) {
				listPresentDrug.add(new PresentDrugItem(getDrugId(brand, 3),"Generic", brand, schedule));
			}else {
				listPresentDrug.add(new PresentDrugItem(getDrugId(brand, 4),"Brand", brand, schedule));
			}
			tablePresentDrug.setItems(listPresentDrug);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}*/

	protected void addInPrescribedDrug(String drugName) {
		try {

			String brand= drugName.substring(4, drugName.indexOf("<")).trim();
			String schedule= drugName.substring(drugName.indexOf("<")+1,drugName.length()-1);


			listPrescribedMedicine.add(new MedicineItemInfo(listPrescribedMedicine.size(), "0", "", brand, "", schedule, "", "", NodeType.MEDICINE_BRAND.getType()));

			tablePrescribedMedicine.setItems(listPrescribedMedicine);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void addInDiseaseTable(String diseaseName) {
		try {

			//PSystem.modelDiagnosis.addRow(new Object[] {brand,schedule});


			if(!diseaseName.isEmpty()) {
				int i;
				for( i=0;i<listPrescribedDisease.size();i++) {
					if(diseaseName.equals(listPrescribedDisease.get(i).getItemName())){
						break;
					}
				}
				if(i==listPrescribedDisease.size()) {
					listPrescribedDisease.add(new TableItemInfo(i,getDrugId(diseaseName, 1),"", diseaseName,NodeType.DISEASE.getType()));
				}
				tablePrescribedDisease.setItems(listPrescribedDisease);
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void insertToMedicineGroup(String id,String name,String pId,int type) {
		try {
			String sql="insert into tbmedicinegroup (id,GroupName,pId,gpID,Type,sn) values("+id+",'"+name+"',"+pId+","+pId+","+type+","+id+");";

			if(databaseHandler.execAction(sql)) {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Successfull...!","Save Successfully...");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void addPrescribedDiseaseItems(TableItemInfo info){
		if(!tablePrescribedDisease.getItems().contains(info)){
			tablePrescribedDisease.getItems().add(info);
			tablePrescribedDisease.refresh();
		}

	}

	public void addPrescribedCheapComplainItems(TableItemInfo info){
		if(!tablePrescribedCheapComplain.getItems().contains(info)){
			tablePrescribedCheapComplain.getItems().add(info);
			tablePrescribedCheapComplain.refresh();
		}

	}

	public void addPrescribedMedicine(MedicineItemInfo info){
		if(!tablePrescribedMedicine.getItems().contains(info)){
			tablePrescribedMedicine.getItems().add(info);
			tablePrescribedMedicine.refresh();
		}

	}

	public void addPrescribedInvestigation(TableItemInfo info){
		if(!tablePrescribedInvestigation.getItems().contains(info)){
			tablePrescribedInvestigation.getItems().add(info);
			tablePrescribedInvestigation.refresh();
		}

	}

	public void addPrescribedAdvise(TableItemInfo info){
		if(!tablePrescribedAdvise.getItems().contains(info)){
			tablePrescribedAdvise.getItems().add(info);
			tablePrescribedAdvise.refresh();
		}

	}



	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message);
	}
	private boolean duplicateCheck(String name,int type) {
		try {
			String sql="select * from tbmedicinegroup where type="+type+" and groupname='"+name+"'";

			ResultSet rs= databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private boolean duplicateCheck(String name,int type,String parentName) {
		try {
			String sql="";
			if(parentName.equals("")) {
				sql="select * from tbmedicinegroup where type="+type+" and groupname='"+name+"'";
			}else
				sql="select * from tbmedicinegroup where type="+type+" and pid="+getDrugId(parentName, --type)+" and groupname='"+name+"'";

			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}

		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private String getCCId(String ccName ) {
		try {
			ccName = ccName.substring(0, ccName.indexOf('-')).trim();
			System.out.println("CC Name="+ccName);
			String sql="select id from tbCC where name like'%"+ccName+"%' and type = 1";
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

		return "0";
	}


	private String getAnyId(String tableName,String columnName,String Name ) {
		try {
			String sql="select id from "+tableName+" where "+columnName+"='"+Name+"'";
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

		return "0";
	}

	private String getAnyId(String tableName,String columnName,String Name, int type ) {
		try {
			String sql="select id from "+tableName+" where "+columnName+"='"+Name+"' and type="+type+"";
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

		return "0";
	}

	private String getDrugId(String name, int type) {
		try {
			ResultSet rs= databaseHandler.execQuery("select id from tbmedicineGroup where type='"+type+"' and Groupname='"+name+"'");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return "0";
	}

	private String getDrugHeadId(String name, int type) {
		try {
			ResultSet rs= databaseHandler.execQuery("select pid from tbmedicineGroup where type='"+type+"' and Groupname='"+name+"'");
			if(rs.next()) {
				return rs.getString("pid");
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return "0";
	}
	private String getTestId(String name, int type) {
		try {
			ResultSet rs= databaseHandler.execQuery("select id from tbtestgroup where type='"+type+"' and Groupname='"+name+"'");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return "0";
	}

	private String getMedicineMaxId() {
		try {
			ResultSet rs=databaseHandler.execQuery("select max(id)+1 as id from tbmedicinegroup");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();

		}
		return "0";
	}

	private boolean isPatientIdExist(String id) {

		try {
			ResultSet rs = databaseHandler.execQuery("select * from tbpatientdetails where patientid="+id+"");
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

	private boolean isAdviseGroupExist(String adviseGroup) {
		// TODO Auto-generated method stub
		try {
			ResultSet rs = databaseHandler.execQuery("select * from tbAdviseGroup where groupName='"+adviseGroup+"'");
			if(rs.next()) {
				return true;
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}


	private int getVisitNo() {
		try {
			ResultSet rs= databaseHandler.execQuery("select ifnull(max(visitNo),0) as visitno from tbvisithistory where patientId='"+getCmbId()+"'");
			if(rs.next()) {
				return rs.getInt("visitno");
			}
		}catch(Exception e) {
			e.printStackTrace();

		}
		return 0;
	}
	private void setCmpAction() {
		// TODO Auto-generated method stub

		cmbCheapComplain.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			cheapComplainAddAction(getCmbCheapComplain()); });

		/*cmbDiseaseNameInd.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			diseaseAddAction(getCmbDiseaseNameInd()); });

		cmbInvestigationName.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			investigationAddAction(getCmbInvestigationName()); });

		cmbAdvise.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			adviseAddAction(getCmbAdvise()); });*/

		/*btnAddMedicine.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			medicineAddAction(null); });*/

		btnAddDrugHis.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			drugHistoryAddAction(null); });

		btnAddIllnessHis.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			illnessHistoryAddAction(null); });

		cmbId.setOnKeyPressed(e ->{ 
			if(e.getCode() == KeyCode.ENTER) {
				generalClearAndRefresh();
				setCmbId(getCmbId());
				prescriptionFindByPatientId(getCmbId());
			}
		});

		txtDiseaseName.setOnKeyPressed(e ->{ 
			if(e.getCode() == KeyCode.ENTER) {
				if(!getTxtDiseaseName().isEmpty()){
					addPrescribedDiseaseItems(new TableItemInfo(0, "0", "0", getTxtDiseaseName(), NodeType.DISEASE.getType()));
				}
			}
		});

		txtCheapComplain.setOnKeyPressed(e ->{ 
			if(e.getCode() == KeyCode.ENTER) {
				if(!getTxtCheapComplain().isEmpty()){
					addPrescribedCheapComplainItems(new TableItemInfo(0, "0", "0", getTxtCheapComplain(), NodeType.CHEAP_COMPLAIN.getType()));
				}
			}
		});

		txtInvestigation.setOnKeyPressed(e ->{ 
			if(e.getCode() == KeyCode.ENTER) {
				if(!getTxtInvestigation().isEmpty()){
					addPrescribedInvestigation(new TableItemInfo(0, "0", "0", getTxtInvestigation(), NodeType.INVESTIGATION.getType()));
				}
			}
		});

		txtAdvise.setOnKeyPressed(e ->{ 
			if(e.getCode() == KeyCode.ENTER) {
				if(!getTxtAdvise().isEmpty()){
					addPrescribedAdvise(new TableItemInfo(0, "0", "0", getTxtAdvise(), NodeType.ADVISE.getType()));
				}
			}
		});

		txtMedicineBrand.setOnKeyPressed(e ->{ 
			if(e.getCode() == KeyCode.ENTER) {
				if(!getTxtMedicineBrand().isEmpty()){
					if(!getCmbSchedule().isEmpty()){
						if(!getCmbCourse().isEmpty()){
							addPrescribedMedicine(new MedicineItemInfo(0, "0", "0", getTxtMedicineBrand(), getCmbCompanyName(), getCmbSchedule(), getCmbTime(), getCmbCourse(), NodeType.MEDICINE_BRAND.getType()));
						}else{
							new Notification(Pos.TOP_CENTER, "Information graphic", "Select Course...!","Please Select Medicine Course...");
							cmbCourse.requestFocus();
						}
					}else{
						new Notification(Pos.TOP_CENTER, "Information graphic", "Select Schedule...!","Please Select Medicine Schedule...");
						cmbSchedule.requestFocus();
					}


				}
			}
		});
		/*cmbSystem.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					drugComboBoxLoad(cmbDisease, 0, newValue);
					drugComboBoxLoad(cmbDiseaseNameInd, 0, newValue);
					loadCheapComplain(newValue);
					investigationLoadBySystemName(newValue);
					adviseLoadBySystemName(newValue);

				}
			}    
		});*/

		/*cmbDisease.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					setCmbInvestigationName("");
					setCmbAdvise("");
					investigationComboBoxLoad(cmbInvestigationName, 1, newValue);
					adviseComboBoxLoad(newValue, 1);
				}
			}    
		});*/


		/*cmbSystemNameMed.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					setCmbDiseaseNameMed("");
					setCmbGenericName("");
					drugComboBoxLoad(cmbDiseaseNameMed, 0, newValue);
					genericComboBoxLoad(newValue,0);

				}
			}    
		});

		cmbDiseaseNameMed.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					setCmbGroupName("");
					setCmbGenericName("");
					drugComboBoxLoad(cmbGroupName, 1, newValue);
					genericComboBoxLoad(newValue,1);

				}
			}    
		});

		cmbGroupName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					setCmbGenericName("");			
					genericComboBoxLoad(newValue,2);

				}
			}    
		});


		cmbGenericName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					setCmbBrandName("");
					drugComboBoxLoad(cmbBrandName, 3, newValue);
					scheduleSetByGenericId(getDrugId(newValue, 3));

				}
			}    
		});

		cmbBrandName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {

					if(!scheduleSetByGenericId(getDrugId(newValue, 4))) {
						scheduleSetByGenericId(getDrugHeadId(newValue, 4));
					}

				}
			}    
		});*/


		cmbSystemNameHistorySurvey.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					surbeyTableLoadBySystem(getDrugId(newValue,0));
				}
			}    
		});

		cmbSystemNameHistoryIllness.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					drugComboBoxLoad(cmbDiseaseHistoryIllness, 0, newValue);
				}
			}    
		});

		cmbId.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null && isSearch) {
					generalClearAndRefresh();
					setCmbId(getCmbId());
					prescriptionFindByPatientId(getCmbId());
				}
			}    
		});

		cmbVisitNo.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null && isSearch) {
					String patientid = getCmbId();
					generalClearAndRefresh();
					setCmbId(patientid);
					prescriptionFindByPatientIdAndVisitNo(patientid, newValue);
				}
			}    
		});


		for(int i = 0;i < examinationIdArray.size(); i++) {
			final int j = i;
			examinationMap.get(examinationIdArray.get(i)).getInputField().setOnKeyPressed(e ->{
				if(j < examinationIdArray.size()-1) {
					if(e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.DOWN) {
						examinationMap.get(examinationIdArray.get(j+1)).getInputField().requestFocus();
					}
				}	
				if(j > 0) {
					if(e.getCode() == KeyCode.UP) {
						examinationMap.get(examinationIdArray.get(j)).getInputField().requestFocus();
					}
				}

			});

		}




		menuItemShowSugQuesCheapComplain.setOnAction(e ->{
			if(tablePrescribedCheapComplain.getSelectionModel().getSelectedItem() != null){
				TableItemInfo temp = tablePrescribedCheapComplain.getSelectionModel().getSelectedItem();
				stageCcSuggest.setCheapComplainId(temp.getItemId());
				stageCcSuggest.setCheapComplain(temp.getItemName());
				stageCcSuggest.setList(temp.getItemId());
				stageCcSuggest.setShow();
			}

		});

		menuItemAddCheapComplain.setOnAction(e ->{
			cheapComplainAddAction(getCmbCheapComplain());
		});

		menuItemLoadAllCheapComplain.setOnAction(e ->{
			loadCheapComplain("");
		});

		menuItemLoadBySystemCheapComplain.setOnAction(e ->{
			loadCheapComplain(getTxtSystemName());
		});

		/*menuItemAddInvestigation.setOnAction(e ->{
			investigationAddAction(getCmbInvestigationName());
		});

		menuItemLoadAllInvestigation.setOnAction(e ->{
			investigationComboBoxLoad(cmbInvestigationName, 2);
		});
		menuItemLoadByDiseaseInvestigation.setOnAction(e ->{
			investigationComboBoxLoad(cmbInvestigationName, 1, getCmbDisease());	
		});
		menuItemLoadBySystemInvestigation.setOnAction(e ->{
			investigationLoadBySystemName(getTxtSystemName());
		});

		menuItemAddAdvise.setOnAction(e ->{
			adviseAddAction(getCmbAdvise());
		});

		menuItemLoadAllAdvise.setOnAction(e ->{
			adviseComboBoxLoad("", 2);
		});
		menuItemLoadByDiseaseAdvise.setOnAction(e ->{
			adviseComboBoxLoad(getCmbDisease(), 1);
		});
		menuItemLoadBySystemAdvise.setOnAction(e ->{
			adviseLoadBySystemName(getTxtSystemName());
		});*/

		/*menuItemLoadAllDiseaseHistory.setOnAction(e ->{
			drugComboBoxLoad(cmbDiseaseHistoryIllness, 1);
		});
		menuItemLoadBySystemDiseaseHistory.setOnAction(e ->{
			drugComboBoxLoad(cmbDisease, 0, getCmbSystemNameHistoryIllness());
		});
		 */
		/*menuItemLoadAllDiseaseMed.setOnAction(e ->{
			drugComboBoxLoad(cmbDiseaseNameMed, 1);
		});
		menuItemLoadBySystemDiseaseMed.setOnAction(e ->{
			drugComboBoxLoad(cmbDiseaseNameMed, 0, getCmbSystemNameMed());
		});

		menuItemLoadAllGroup.setOnAction(e ->{
			drugComboBoxLoad(cmbDiseaseNameMed, 2);
		});
		menuItemLoadByDiseaseGroup.setOnAction(e ->{
			drugComboBoxLoad(cmbGroupName, 1, getCmbDiseaseNameMed());
		});

		menuItemLoadAllGeneric.setOnAction(e ->{
			drugComboBoxLoad(cmbDiseaseNameMed, 3);
		});
		menuItemLoadBySystemGeneric.setOnAction(e ->{
			genericComboBoxLoad(getCmbSystemNameMed(), 0);
		});
		menuItemLoadByDiseaseGeneric.setOnAction(e ->{
			genericComboBoxLoad(getCmbDiseaseNameMed(), 1);
		});
		menuItemLoadByGroupGeneric.setOnAction(e ->{
			genericComboBoxLoad(getCmbGroupName(), 2);
		});

		menuItemLoadAllBrand.setOnAction(e ->{
			drugComboBoxLoad(cmbBrandName,4);
		});
		menuItemLoadByGenericBrand.setOnAction(e ->{
			drugComboBoxLoad(cmbBrandName, 3, getCmbGenericName());
		});*/

		menuItemLoadAllMedicineHistory.setOnAction(e ->{
			drugComboBoxLoad(cmbMedicineHistoryDrug,4);
		});
		menuItemLoadByGenericMedicineHistory.setOnAction(e ->{
			drugComboBoxLoad(cmbMedicineHistoryDrug, 3, getCmbGenericHistoryDrug());
		});

		menuItemDeleteDrugHistory.setOnAction(e->{
			deleteDrugTreeItemAction();
		});

		menuItemAddToPrescribedDrug.setOnAction(e->{
			if(treeDrugHistory.getSelectionModel().getSelectedItem() != null){
				addInPrescribedDrug(((TreeItem)treeDrugHistory.getSelectionModel().getSelectedItem()).getValue().toString());
			}
		});

		menuItemDeleteIllnessHistory.setOnAction(e->{
			deleteIllnessHistoryTreeItemAction();
		});

		menuItemSaveScheduleToGenericSchedule.setOnAction(e->{

			scheduleSaveToGenericAction();

		});

		menuItemSaveScheduleToGenericCourse.setOnAction(e->{

			scheduleSaveToGenericAction();

		});

		menuItemSaveScheduleToMedicineBrandSchedule.setOnAction(e->{

			scheduleSaveToMedicineBrandAction();

		});

		menuItemSaveScheduleToMedicineBrandCourse.setOnAction(e->{

			scheduleSaveToMedicineBrandAction();

		});

		checkSmokingYes.setOnAction(e ->{
			if(getCheckSmokingYes()) {
				setCheckSmokingNO(false);
			}
		});

		checkSmokingNO.setOnAction(e ->{
			if(getCheckSmokingNO()) {
				setCheckSmokingYes(false);
			}
		});

		checkBetelYes.setOnAction(e ->{
			if(getCheckBetelYes()) {
				setCheckBetelNo(false);
			}
		});

		checkBetelNo.setOnAction(e ->{
			if(getCheckBetelNo()) {
				setCheckBetelYes(false);
			}
		});

		checkRegular.setOnAction(e ->{
			if(getCheckRegular()) {
				setCheckEregular(false);
			}
		});

		checkEregular.setOnAction(e ->{
			if(getCheckEregular()) {
				setCheckRegular(false);
			}
		});

	}

	private void scheduleSaveToGenericAction() {
		// TODO Auto-generated method stub
		try {
			if(scheduleSaveValidationCheck()){
				if(confirmationCheck("Save This Schedule?")) {
					MedicineItemInfo temp = tableMedicineBrand.getSelectionModel().getSelectedItem();
					if(isGenericIdExistInSchedule(temp.getHeadId())){
						scheduleEditAction(temp.getHeadId());
					}else{
						scheduleSaveAction(temp.getHeadId());
					}
				}
			}


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void scheduleSaveToMedicineBrandAction() {
		// TODO Auto-generated method stub
		try {
			if(scheduleSaveValidationCheck()){
				if(confirmationCheck("Save This Schedule?")) {
					MedicineItemInfo temp = tableMedicineBrand.getSelectionModel().getSelectedItem();
					if(isGenericIdExistInSchedule(temp.getItemId())){
						scheduleEditAction(temp.getItemId());
					}else{
						scheduleSaveAction(temp.getItemId());
					}
				}
			}


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}



	private boolean scheduleSaveValidationCheck() {
		if(tableMedicineBrand.getSelectionModel().getSelectedItem() != null){		
			if(!getCmbSchedule().isEmpty()) {
				if(!getCmbCourse().isEmpty()) {
					if(isScheduleExist(getCmbSchedule(),1)) {
						if(isScheduleExist(getCmbTime(),2)) {
							if(isScheduleExist(getCmbCourse(),3)) {
								return true;
							}
						}
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Select Course!","Please Select/Enter Any Course...");
					cmbCourse.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Schedule!","Please Select/Enter Any Schedule...");
				cmbSchedule.requestFocus();
			}	
		}else{
			new Notification(Pos.TOP_CENTER, "Information graphic", "Select Medicine Brand!","Please Select Any Medicine Brand...");
			tableMedicineBrand.requestFocus();
		}
		return false;
	}
	private void scheduleSaveAction(String pId) {
		try {
			sql= "insert INTO tbschedule (genericid,suggestionid,type,entryTime,entryBy) values("+pId+","+LoadedInfo.getScheduleInfo(getCmbSchedule()).getItemId()+",1,now(),'"+SessionBeam.getUserId()+"');";
			databaseHandler.execAction(sql);

			sql= "insert INTO tbschedule (genericid,suggestionid,type,entryTime,entryBy) values("+pId+","+(LoadedInfo.getTimeInfo(getCmbTime())!=null?LoadedInfo.getTimeInfo(getCmbTime()).getItemId():"0")+",2,now(),'"+SessionBeam.getUserId()+"');";	
			databaseHandler.execAction(sql);

			sql= "insert INTO tbschedule (genericid,suggestionid,type,entryTime,entryBy) values("+pId+","+LoadedInfo.getCourseInfo(getCmbCourse()).getItemId()+",3,now(),'"+SessionBeam.getUserId()+"');";		
			databaseHandler.execAction(sql);

			new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully!","This Schedule Save Successfully...");

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void scheduleEditAction(String pId) {
		try {
			sql= "update tbschedule set suggestionid="+LoadedInfo.getScheduleInfo(getCmbSchedule()).getItemId()+" where genericid="+pId+" and type=1;";		
			databaseHandler.execAction(sql);

			sql= "update tbschedule set suggestionid="+(LoadedInfo.getTimeInfo(getCmbTime())!=null?LoadedInfo.getTimeInfo(getCmbTime()).getItemId():"0")+" where genericid="+pId+" and type=2;";	
			databaseHandler.execAction(sql);

			sql= "update tbschedule set suggestionid="+LoadedInfo.getCourseInfo(getCmbCourse()).getItemId()+" where genericid="+pId+" and type=3;";	
			databaseHandler.execAction(sql);

			new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully!","This Schedule Edit Successfully...");
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private boolean isGenericIdExistInSchedule(String id) {
		try {
			sql="select * from tbschedule where genericid="+id+"";
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
					sql="insert into tbsuggestions (id,suggestion,type) values("+CommonMethod.getMaxSuggestionId()+",'"+name+"',"+type+")";		
					databaseHandler.execAction(sql);

					if(type==1){	
						scheduleLoad();
					}else if(type == 2){
						timeLoad();
					}else if(type == 3){
						courseLoad();
					}
					LoadedInfo.LoadAllTypeSchedule();
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
	private void setKeyAction() {
		// TODO Auto-generated method stub
		txtSystemName.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableSystem.setItems(searchEqualListItem(LoadedInfo.getSystemList(),getTxtSystemName()));
			}else{
				tableSystem.setItems(searchEqualListItem(tableSystem.getItems(),getTxtSystemName()));
			}
			tableSystem.refresh();
		});

		txtDiseaseName.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableDisease.setItems(searchEqualListItem(LoadedInfo.getDiseaseList(),getTxtDiseaseName()));
			}else{
				tableDisease.setItems(searchEqualListItem(tableDisease.getItems(),getTxtDiseaseName()));
			}
			tableDisease.refresh();
		});


		txtSystemNameHistory.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableSystemHistory.setItems(searchEqualListItem(LoadedInfo.getSystemList(),getTxtSystemName()));
			}else{
				tableSystemHistory.setItems(searchEqualListItem(tableSystemHistory.getItems(),getTxtSystemNameHistory()));
			}
			tableSystemHistory.refresh();
		});

		txtDiseaseNameHistory.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableDiseaseHistory.setItems(searchEqualListItem(LoadedInfo.getDiseaseList(),getTxtDiseaseNameHistory()));
			}else{
				tableDiseaseHistory.setItems(searchEqualListItem(tableDiseaseHistory.getItems(),getTxtDiseaseNameHistory()));
			}
			tableDiseaseHistory.refresh();
		});

		txtMedicineGroup.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableMedicineGroup.setItems(searchEqualListItem(LoadedInfo.getMedicineGroupList(),getTxtMedicineGroup()));
			}else{
				tableMedicineGroup.setItems(searchEqualListItem(tableMedicineGroup.getItems(),getTxtMedicineGroup()));
			}
			tableMedicineGroup.refresh();
		});

		txtGeneric.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableGeneric.setItems(searchEqualListItem(LoadedInfo.getGenericList(),getTxtGeneric()));
			}else{
				tableGeneric.setItems(searchEqualListItem(tableGeneric.getItems(),getTxtGeneric()));
			}
			tableGeneric.refresh();
		});

		txtMedicineBrand.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableMedicineBrand.setItems(searchMedicineEqualListItem(LoadedInfo.getMedicineBrandList(),getTxtMedicineBrand()));
			}else{
				tableMedicineBrand.setItems(searchMedicineEqualListItem(tableMedicineBrand.getItems(),getTxtMedicineBrand()));
			}
			tableMedicineBrand.refresh();
		});

		txtCheapComplain.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableCheapComplain.setItems(searchEqualListItem(LoadedInfo.getCheapComplainList(),getTxtCheapComplain()));
			}else{
				tableCheapComplain.setItems(searchEqualListItem(tableCheapComplain.getItems(),getTxtCheapComplain()));
			}
			tableCheapComplain.refresh();
		});

		txtInvestigation.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableInvestigation.setItems(searchEqualListItem(LoadedInfo.getInvestigationList(),getTxtInvestigation()));
			}else{
				tableInvestigation.setItems(searchEqualListItem(tableInvestigation.getItems(),getTxtInvestigation()));
			}
			tableInvestigation.refresh();
		});

		txtAdvise.setOnKeyReleased(e->{
			if(e.getCode()==KeyCode.BACK_SPACE || e.getCode()==KeyCode.DELETE){
				tableAdvise.setItems(searchEqualListItem(LoadedInfo.getAdviseList(),getTxtAdvise()));
			}else{
				tableAdvise.setItems(searchEqualListItem(tableAdvise.getItems(),getTxtAdvise()));
			}
			tableAdvise.refresh();
		});

	}

	private ObservableList<TableItemInfo> searchEqualListItem(ObservableList<TableItemInfo> items,String itemName) {
		// TODO Auto-generated method stub
		ObservableList<TableItemInfo> tempItems = FXCollections.observableArrayList();

		if(!itemName.isEmpty()){
			for(int i=0;i<items.size();i++){
				if(items.get(i).getItemName().toLowerCase().contains(itemName.toLowerCase())){
					tempItems.add(items.get(i));

				}

			}
		}else{
			return items;
		}

		return tempItems;
	}

	private ObservableList<MedicineItemInfo> searchMedicineEqualListItem(ObservableList<MedicineItemInfo> items,String itemName) {
		// TODO Auto-generated method stub
		ObservableList<MedicineItemInfo> tempItems = FXCollections.observableArrayList();

		if(!itemName.isEmpty()){
			for(int i=0;i<items.size();i++){
				if(items.get(i).getItemName().toLowerCase().contains(itemName.toLowerCase())){
					tempItems.add(items.get(i));

				}

			}
		}else{
			return items;
		}

		return tempItems;
	}



	private void focusMoveAction() {
		// TODO Auto-generated method stub

		Control[] control =  {cmbPatientName,txtAge,cmbSex,cmbAddress,txtContactNo,cmbCheapComplain};
		new FocusMoveByEnter(control);

		Control[] control2 =  {cmbSchedule,cmbCourse,cmbTime,txtMedicineBrand};
		new FocusMoveByEnter(control2);
		/*Control[] control2 =  {cmbSystemNameMed,cmbDiseaseNameMed,cmbGroupName,cmbGenericName,cmbBrandName,cmbSchedule,cmbTime,cmbCourse,btnAddMedicine};
		new FocusMoveByEnter(control2);*/

		Control[] control3 =  {cmbSystemNameHistoryIllness,cmbDiseaseHistoryIllness,btnAddIllnessHis};
		new FocusMoveByEnter(control3);

		Control[] control4 =  {cmbGenericHistoryDrug,cmbMedicineHistoryDrug,cmbScheduleHistoryDrug,btnAddDrugHis};
		new FocusMoveByEnter(control4);

	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbId.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbId);
		});
		cmbPatientName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbPatientName);
		});
		txtAge.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtAge);
		});

		txtSystemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtSystemName);
		});

		txtDiseaseName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtDiseaseName);
		});

		txtSystemNameHistory.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtSystemNameHistory);
		});

		txtDiseaseNameHistory.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtDiseaseNameHistory);
		});

		txtMedicineGroup.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtMedicineGroup);
		});

		txtGeneric.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtGeneric);
		});

		txtMedicineBrand.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtMedicineBrand);
		});

		txtCheapComplain.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtCheapComplain);
		});

		txtInvestigation.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtInvestigation);
		});

		txtAdvise.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectTextIfFocused(txtAdvise);
		});
		cmbSex.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSex);
		});
		cmbAddress.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbAddress);
		});
		txtContactNo.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtContactNo);
		});

		cmbDisease.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDisease);
		});
		cmbCheapComplain.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCheapComplain);
		});
		/*cmbDiseaseNameInd.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDiseaseNameInd);
		});
		cmbInvestigationName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbInvestigationName);
		});
		cmbAdvise.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbAdvise);
		});*/
		/*cmbSystemNameMed.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemNameMed);
		});
		cmbDiseaseNameMed.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDiseaseNameMed);
		});
		cmbGroupName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbGroupName);
		});
		cmbGenericName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbGenericName);
		});
		cmbBrandName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbBrandName);
		});*/
		cmbCompanyName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCompanyName);
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
		cmbRefferTo.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbRefferTo);
		});


		cmbSystemNameHistorySurvey.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemNameHistorySurvey);
		});
		cmbJobProffession.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbJobProffession);
		});
		cmbDesignation.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDesignation);
		});
		cmbPosting.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbPosting);
		});
		cmbSystemNameHistoryIllness.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSystemNameHistoryIllness);
		});
		cmbDiseaseHistoryIllness.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDiseaseHistoryIllness);
		});
		/*cmbCCHistoryIllness.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCCHistoryIllness);
		});*/
		cmbGenericHistoryDrug.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbGenericHistoryDrug);
		});
		cmbMedicineHistoryDrug.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbMedicineHistoryDrug);
		});
		cmbScheduleHistoryDrug.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbScheduleHistoryDrug);
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
	private void setCmpData() {
		// TODO Auto-generated method stub
		date.setConverter(converter);
		date.setValue(LocalDate.now());

		calender = Calendar.getInstance();

		cmbSex.data.addAll("Male","Female","Other..");

		stageCcSuggest = new StageCcSuggest();





		menuItemShowSugQuesCheapComplain = new MenuItem("Show Suggested Question");
		menuItemAddCheapComplain = new MenuItem("Add");
		menuItemLoadAllCheapComplain = new MenuItem("Load All");
		menuItemLoadBySystemCheapComplain = new MenuItem("Load By System Name");


		/*menuItemSystemTableRefresh = new MenuItem("Refresh");
		menuItemSystemTableAddDisease = new MenuItem("Add Disease");
		menuItemSystemTableAddCC = new MenuItem("Add Cheap Complain");
		menuItemSystemTableAddGeneric = new MenuItem("Add Generic");
		menuItemSystemTableEditSystemName = new MenuItem("Edit System Name");*/

		/*menuItemAddInvestigation = new MenuItem("Add");
		menuItemLoadAllInvestigation = new MenuItem("Load All");
		menuItemLoadByDiseaseInvestigation = new MenuItem("Load By Disease");
		menuItemLoadBySystemInvestigation = new MenuItem("Load By System Name");

		menuItemAddAdvise = new MenuItem("Add");
		menuItemLoadAllAdvise = new MenuItem("Load All");
		menuItemLoadByDiseaseAdvise = new MenuItem("Load By Disease Name");
		menuItemLoadBySystemAdvise = new MenuItem("Load By System Name");



		menuItemLoadAllDiseaseMed = new MenuItem("Load All");
		menuItemLoadBySystemDiseaseMed = new MenuItem("Load By System Name");

		menuItemLoadAllGroup = new MenuItem("Load All");
		menuItemLoadByDiseaseGroup = new MenuItem("Load By Disease Name");

		menuItemLoadAllGeneric = new MenuItem("Load All");
		menuItemLoadBySystemGeneric = new MenuItem("Load By System Name");
		menuItemLoadByDiseaseGeneric = new MenuItem("Load By Disease Name");
		menuItemLoadByGroupGeneric = new MenuItem("Load By Group Name");

		menuItemLoadAllBrand = new MenuItem("Load All");
		menuItemLoadByGenericBrand = new MenuItem("Load By Generic Name");*/
		menuItemLoadAllDiseaseHistory = new MenuItem("Load All");
		menuItemLoadBySystemDiseaseHistory = new MenuItem("Load By System Name");

		menuItemLoadAllMedicineHistory = new MenuItem("Load All");
		menuItemLoadByGenericMedicineHistory = new MenuItem("Load By Generic Name");

		menuItemAddToPrescribedDrug= new MenuItem("Add To Prescribed");
		menuItemDeleteDrugHistory = new MenuItem("Delete");
		menuItemDeleteIllnessHistory = new MenuItem("Delete");

		menuItemSaveScheduleToGenericSchedule = new MenuItem("Save/Edit Schedule To Generic");
		menuItemSaveScheduleToGenericCourse = new MenuItem("Save/Edit Schedule To Generic");

		menuItemSaveScheduleToMedicineBrandSchedule = new MenuItem("Save/Edit Schedule To Medicine Brand");
		menuItemSaveScheduleToMedicineBrandCourse = new MenuItem("Save/Edit Schedule To Medicine Brand");

		//contextMenuSystem = new CustomContextMenu(cmbSystem.getEditor());
		//contextMenuDisease = new CustomContextMenu(cmbDisease.getEditor());
		contextMenuCheapComplain = new CustomContextMenu(cmbCheapComplain.getEditor());
		/*	contextMenuDiseaseInd = new CustomContextMenu(cmbDiseaseNameInd.getEditor());
		contextMenuInvestigation = new CustomContextMenu(cmbInvestigationName.getEditor());
		contextMenuAdvise = new CustomContextMenu(cmbAdvise.getEditor());*/

		/*contextMenuSystemMed = new CustomContextMenu(cmbSystemNameMed.getEditor());
		contextMenuDiseaseMed = new CustomContextMenu(cmbDiseaseNameMed.getEditor());
		contextMenuGroup = new CustomContextMenu(cmbGroupName.getEditor());
		contextMenuGeneric = new CustomContextMenu(cmbGenericName.getEditor());
		contextMenuBrandName = new CustomContextMenu(cmbBrandName.getEditor());*/
		contextMenuSchedule = new CustomContextMenu(cmbSchedule.getEditor());
		contextMenuTime = new CustomContextMenu(cmbTime.getEditor());
		contextMenuCourse = new CustomContextMenu(cmbCourse.getEditor());

		contextMenuSchedule.getItems().addAll(new SeparatorMenuItem(), menuItemSaveScheduleToGenericSchedule,menuItemSaveScheduleToMedicineBrandSchedule);
		contextMenuCourse.getItems().addAll(new SeparatorMenuItem(),menuItemSaveScheduleToGenericCourse,menuItemSaveScheduleToMedicineBrandCourse);


		contextMenuDiseaseHistory = new CustomContextMenu(cmbDiseaseHistoryIllness.getEditor());
		contextMenuMedicineHistory = new CustomContextMenu(cmbMedicineHistoryDrug.getEditor());

		contextMenuTxtSystem = new NewCustomContextMenu(NodeType.SYSTEM, txtSystemName,this);
		contextMenuTxtSystem = new NewCustomContextMenu(NodeType.SYSTEM, txtSystemNameHistory,this);
		contextMenuTableSystem = new NewCustomContextMenu(NodeType.SYSTEM, tableSystem, this);
		contextMenuTableSystem = new NewCustomContextMenu(NodeType.SYSTEM, tableSystemHistory, this);
		contextMenuTxtDisease = new NewCustomContextMenu(NodeType.DISEASE, txtDiseaseName,this);
		contextMenuTxtDisease = new NewCustomContextMenu(NodeType.DISEASE, txtDiseaseNameHistory,this);
		contextMenuTableDisease = new NewCustomContextMenu(NodeType.DISEASE, tableDisease,this);
		contextMenuTableDisease = new NewCustomContextMenu(NodeType.DISEASE, tableDiseaseHistory,this);
		contextMenuTxtCheapComplain = new NewCustomContextMenu(NodeType.CHEAP_COMPLAIN, txtCheapComplain,this);
		contextMenuTableCheapComplain = new NewCustomContextMenu(NodeType.CHEAP_COMPLAIN, tableCheapComplain,this);
		contextMenuTxtMedicineGroup = new NewCustomContextMenu(NodeType.MEDICINE_GROUP, txtMedicineGroup,this);
		contextMenuTableMedicineGroup = new NewCustomContextMenu(NodeType.MEDICINE_GROUP, tableMedicineGroup,this);
		contextMenuTxtGeneric = new NewCustomContextMenu(NodeType.GENERIC, txtGeneric,this);
		contextMenuTableGeneric = new NewCustomContextMenu(NodeType.GENERIC, tableGeneric,this);
		contextMenuTxtMedicineBrand = new NewCustomContextMenu(NodeType.MEDICINE_BRAND, txtMedicineBrand,this);
		contextMenuTableMedicineBrand = new NewCustomContextMenu(NodeType.MEDICINE_BRAND, tableMedicineBrand,this);
		contextMenuTxtInvestigation = new NewCustomContextMenu(NodeType.INVESTIGATION, txtInvestigation,this);
		contextMenuTableInvestigation = new NewCustomContextMenu(NodeType.INVESTIGATION, tableInvestigation,this);
		contextMenuTxtAdvise = new NewCustomContextMenu(NodeType.ADVISE, txtAdvise,this);
		contextMenuTableAdvise = new NewCustomContextMenu(NodeType.ADVISE, tableAdvise,this);



		treeDrugHistory.setContextMenu(contextMenuDrugHistory);
		treeIllnessHistory.setContextMenu(contextMenuIllnessHistory);

		contextMenuCheapComplain.getItems().add(0,menuItemAddCheapComplain);
		contextMenuCheapComplain.getItems().add(1,menuItemLoadAllCheapComplain);
		contextMenuCheapComplain.getItems().add(2,menuItemLoadBySystemCheapComplain);
		contextMenuCheapComplain.getItems().add(3,new SeparatorMenuItem());

		//contextMenuSystemTable.getItems().addAll(menuItemSystemTableRefresh,new SeparatorMenuItem(),menuItemSystemTableAddDisease,menuItemSystemTableAddCC,menuItemSystemTableAddGeneric,new SeparatorMenuItem(),menuItemSystemTableEditSystemName);

		/*contextMenuInvestigation.getItems().add(0,menuItemAddInvestigation);
		contextMenuInvestigation.getItems().add(1,menuItemLoadAllInvestigation);
		contextMenuInvestigation.getItems().add(2,menuItemLoadBySystemInvestigation);
		contextMenuInvestigation.getItems().add(3,menuItemLoadByDiseaseInvestigation);
		contextMenuInvestigation.getItems().add(4,new SeparatorMenuItem());

		contextMenuAdvise.getItems().add(0,menuItemAddAdvise);
		contextMenuAdvise.getItems().add(1,menuItemLoadAllAdvise);
		contextMenuAdvise.getItems().add(2,menuItemLoadBySystemAdvise);
		contextMenuAdvise.getItems().add(3,menuItemLoadByDiseaseAdvise);
		contextMenuAdvise.getItems().add(4,new SeparatorMenuItem());

		contextMenuDiseaseMed.getItems().add(0,menuItemLoadAllDiseaseMed);
		contextMenuDiseaseMed.getItems().add(1,menuItemLoadBySystemDiseaseMed);
		contextMenuDiseaseMed.getItems().add(2,new SeparatorMenuItem());

		contextMenuGroup.getItems().add(0,menuItemLoadAllGroup);
		contextMenuGroup.getItems().add(1,menuItemLoadByDiseaseGroup);
		contextMenuGroup.getItems().add(2,new SeparatorMenuItem());

		contextMenuGeneric.getItems().add(0,menuItemLoadAllGeneric);
		contextMenuGeneric.getItems().add(1,menuItemLoadBySystemGeneric);
		contextMenuGeneric.getItems().add(2,menuItemLoadByDiseaseGeneric);
		contextMenuGeneric.getItems().add(3,menuItemLoadByGroupGeneric);
		contextMenuGeneric.getItems().add(4,new SeparatorMenuItem());

		contextMenuBrandName.getItems().add(0,menuItemLoadAllBrand);
		contextMenuBrandName.getItems().add(1,menuItemLoadByGenericBrand);
		contextMenuBrandName.getItems().add(2,new SeparatorMenuItem());*/

		contextMenuDiseaseHistory.getItems().add(0,menuItemLoadAllDiseaseHistory);
		contextMenuDiseaseHistory.getItems().add(1,menuItemLoadBySystemDiseaseHistory);
		contextMenuDiseaseHistory.getItems().add(2,new SeparatorMenuItem());

		contextMenuMedicineHistory.getItems().add(0,menuItemLoadAllMedicineHistory);
		contextMenuMedicineHistory.getItems().add(1,menuItemLoadByGenericMedicineHistory);
		contextMenuMedicineHistory.getItems().add(2,new SeparatorMenuItem());

		contextMenuIllnessHistory.getItems().add(menuItemDeleteIllnessHistory);
		contextMenuDrugHistory.getItems().addAll(menuItemDeleteDrugHistory,menuItemAddToPrescribedDrug);



		tableSystem.setColumnResizePolicy(tableSystem.CONSTRAINED_RESIZE_POLICY);
		systemCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		//tableSystem.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		systemCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(systemCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});
		//tableSystem.setContextMenu(contextMenuSystemTable);


		tableDisease.setColumnResizePolicy(tableDisease.CONSTRAINED_RESIZE_POLICY);
		diseaseCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableDisease.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		diseaseCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(diseaseCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});


		tableCheapComplain.setColumnResizePolicy(tableCheapComplain.CONSTRAINED_RESIZE_POLICY);
		cheapComplainCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableCheapComplain.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


		tablePrescribedCheapComplain.setColumnResizePolicy(tablePrescribedCheapComplain.CONSTRAINED_RESIZE_POLICY);
		prescribedCheapComplainCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tablePrescribedCheapComplain.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		prescribedCheapComplainCol.setCellFactory(tc -> {

			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(prescribedCheapComplainCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());

			return cell ;
		});
		prescribedCheapComplainCol.setCellFactory(
				TextFieldTableCell.forTableColumn());
		prescribedCheapComplainCol.setOnEditCommit(
				(TableColumn.CellEditEvent<TableItemInfo, String> t) ->
				( t.getTableView().getItems().get(
						t.getTablePosition().getRow())
						).setItemName(t.getNewValue())
				);

		tablePrescribedDisease.setColumnResizePolicy(tablePrescribedDisease.CONSTRAINED_RESIZE_POLICY);
		prscribedDiseaseCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tablePrescribedDisease.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);



		tableInvestigation.setColumnResizePolicy(tableInvestigation.CONSTRAINED_RESIZE_POLICY);
		investigationCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableInvestigation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		tableInvestigationGroup.setColumnResizePolicy(tableInvestigation.CONSTRAINED_RESIZE_POLICY);
		investigationGroupCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		investigationGroupCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(investigationGroupCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		tablePrescribedInvestigation.setColumnResizePolicy(tablePrescribedInvestigation.CONSTRAINED_RESIZE_POLICY);
		prescribedInvestigationCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tablePrescribedInvestigation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


		tableAdvise.setColumnResizePolicy(tableAdvise.CONSTRAINED_RESIZE_POLICY);
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

		tableAdviseGroup.setColumnResizePolicy(tableAdviseGroup.CONSTRAINED_RESIZE_POLICY);
		adviseGroupCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		adviseGroupCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(adviseCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		tablePrescribedAdvise.setColumnResizePolicy(tablePrescribedAdvise.CONSTRAINED_RESIZE_POLICY);
		prescribedAdviseCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tablePrescribedAdvise.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		prescribedAdviseCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(prescribedAdviseCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		tableSystemicSurvey.setColumnResizePolicy(tableSystemicSurvey.CONSTRAINED_RESIZE_POLICY);
		systemicSurveyCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		//tableSystemicSurvey.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		tableSelectQuerySurvey.setColumnResizePolicy(tableSelectQuerySurvey.CONSTRAINED_RESIZE_POLICY);
		selectQuerySurveyCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableSelectQuerySurvey.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


		tableClinicalExamination.setColumnResizePolicy(tableClinicalExamination.CONSTRAINED_RESIZE_POLICY);
		examinationNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		resultCol.setCellValueFactory(new PropertyValueFactory("result"));
		tableClinicalExamination.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		examinationNameCol.setCellFactory(tc -> {
			TableCell<ClinicalExaminationItem, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(examinationNameCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});
		resultCol.setCellFactory(tc -> {
			TableCell<ClinicalExaminationItem, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(resultCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});
		Bindings.bindContent(tableClinicalExamination.getItems(), listClinicalExamination);

		tableMedicineGroup.setColumnResizePolicy(tableMedicineGroup.CONSTRAINED_RESIZE_POLICY);
		medicineGroupCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableMedicineGroup.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		medicineGroupCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(medicineGroupCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		tableGeneric.setColumnResizePolicy(tableGeneric.CONSTRAINED_RESIZE_POLICY);
		genericCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableGeneric.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		genericCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(genericCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		tableMedicineBrand.setColumnResizePolicy(tableMedicineBrand.CONSTRAINED_RESIZE_POLICY);
		brandCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		companyCol.setCellValueFactory(new PropertyValueFactory("companyName"));
		tableMedicineBrand.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		brandCol.setCellFactory(tc -> {
			TableCell<MedicineItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(brandCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		/*tablePresentDrug.setColumnResizePolicy(tablePresentDrug.CONSTRAINED_RESIZE_POLICY);
		presentMedicineBrandCol.setCellValueFactory(new PropertyValueFactory("medicineName"));
		presentDrugScheduleCol.setCellValueFactory(new PropertyValueFactory("schedule"));
		tablePresentDrug.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);*/


		tablePrescribedMedicine.setColumnResizePolicy(tablePrescribedMedicine.CONSTRAINED_RESIZE_POLICY);
		medicineNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		scheduleCol.setCellValueFactory(new PropertyValueFactory("schedule"));
		timeCol.setCellValueFactory(new PropertyValueFactory("time"));
		courseCol.setCellValueFactory(new PropertyValueFactory("course"));
		tablePrescribedMedicine.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		medicineNameCol.setCellFactory(tc -> {
			TableCell<MedicineItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(medicineNameCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});
		scheduleCol.setCellFactory(tc -> {
			TableCell<MedicineItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(scheduleCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});


		tableSystemHistory.setColumnResizePolicy(tableSystemHistory.CONSTRAINED_RESIZE_POLICY);
		systemHistoryCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		//tableSystem.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		systemHistoryCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(systemHistoryCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});
		//tableSystem.setContextMenu(contextMenuSystemTable);


		tableDiseaseHistory.setColumnResizePolicy(tableDiseaseHistory.CONSTRAINED_RESIZE_POLICY);
		diseaseHistoryCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		tableDiseaseHistory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		diseaseHistoryCol.setCellFactory(tc -> {
			TableCell<TableItemInfo, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(diseaseHistoryCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});

		new TableItemRowFactory(tablePrescribedCheapComplain);
		new TableItemRowFactory(tablePrescribedDisease);
		new TableItemRowFactory(tablePrescribedInvestigation);
		new TableItemRowFactory(tablePrescribedAdvise);
		new TableItemRowFactory(tableSelectQuerySurvey);
		new MedicineRowFactory(tablePrescribedMedicine);

		tableContextMenuCheapComplain = new TableContextMenu(tablePrescribedCheapComplain, listPrescribedCheapComplain);
		new TableContextMenu(tablePrescribedDisease, listPrescribedDisease);
		new TableContextMenu(tablePrescribedInvestigation, listPrescribedInvestigation);
		new TableContextMenu(tablePrescribedAdvise, listPrescribedAdvise);
		new TableContextMenu(tablePrescribedMedicine, listPrescribedMedicine);
		new TableContextMenu(tableSelectQuerySurvey, listSelectQuerySurvey);


		tableContextMenuCheapComplain.menuAdd(0, menuItemShowSugQuesCheapComplain);
		tableContextMenuCheapComplain.menuAdd(1, new SeparatorMenuItem());


		groupIllness = new ToggleGroup();
		radioPresentIllness.setToggleGroup(groupIllness);
		radioPastIllness.setToggleGroup(groupIllness);
		radioFatherIllness.setToggleGroup(groupIllness);
		radioMotherIllness.setToggleGroup(groupIllness);
		radioOtherIllness.setToggleGroup(groupIllness);
		setRadioPresentIllness(true);

		groupDrug = new ToggleGroup();
		radioPresentDrug.setToggleGroup(groupDrug);
		radioPastDrug.setToggleGroup(groupDrug);
		radioRecentDrug.setToggleGroup(groupDrug);
		radioAllergicDrug.setToggleGroup(groupDrug);    
		setRadioPresentDrug(true);

	}
	private void addCmp() {
		// TODO Auto-generated method stub

		hBoxPatientInfo.getChildren().remove(1,3);
		hBoxPatientInfo.getChildren().remove(4,6);

		cmbId.setPrefWidth(107);
		cmbId.setPrefHeight(25);
		cmbId.setPromptText("Id");
		hBoxPatientInfo.getChildren().add(1,cmbId);

		cmbPatientName.setPrefWidth(350);
		cmbPatientName.setPrefHeight(25);
		cmbPatientName.setPromptText("Patient Name");
		hBoxPatientInfo.getChildren().add(2,cmbPatientName);

		cmbSex.setPrefWidth(86);
		cmbSex.setPrefHeight(25);
		cmbSex.setPromptText("Sex");
		hBoxPatientInfo.getChildren().add(6,cmbSex);

		cmbAddress.setPrefWidth(185);
		cmbAddress.setPrefHeight(25);
		cmbAddress.setPromptText("Address");
		hBoxPatientInfo.getChildren().add(7,cmbAddress);

		//cmbSystem.setPrefWidth(800);
		//cmbSystem.setPrefHeight(25);
		//cmbSystem.setPromptText("System Name");
		//cmbSystem.setTooltip(toolTipSystem);
		//vBoxSystem.getChildren().clear();
		//vBoxSystem.getChildren().add(cmbSystem);


		cmbCheapComplain.setPrefWidth(800);
		cmbCheapComplain.setPrefHeight(25);
		cmbCheapComplain.setPromptText("Cheap Complain");
		cmbCheapComplain.setTooltip(toolTipCheapComplain);


		cmbDisease.setPrefWidth(800);
		cmbDisease.setPrefHeight(25);
		cmbDisease.setPromptText("Disease Name");
		cmbDisease.setTooltip(toolTipDisease);



		/*cmbDiseaseNameInd.setPrefWidth(800);
		cmbDiseaseNameInd.setPrefHeight(25);
		cmbDiseaseNameInd.setPromptText("Disease Name");
		cmbDiseaseNameInd.setTooltip(toolTipDisease);


		cmbInvestigationName.setPrefWidth(800);
		cmbInvestigationName.setPrefHeight(25);
		cmbInvestigationName.setPromptText("Investigation Name");
		cmbInvestigationName.setTooltip(toolTipInvestigation);


		cmbAdvise.setPrefWidth(800);
		cmbAdvise.setPrefHeight(25);
		cmbAdvise.setPromptText("Advise");
		cmbAdvise.setTooltip(toolTipAdvis);*/



		cmbSystemNameHistorySurvey.setPrefWidth(800);
		cmbSystemNameHistorySurvey.setPrefHeight(25);
		cmbSystemNameHistorySurvey.setPromptText("System Name");
		cmbSystemNameHistorySurvey.setTooltip(toolTipSystem);
		vBoxSystemSurvey.getChildren().clear();
		vBoxSystemSurvey.getChildren().add(cmbSystemNameHistorySurvey);


		cmbJobProffession.setPrefWidth(800);
		cmbJobProffession.setPrefHeight(25);

		cmbDesignation.setPrefWidth(800);
		cmbDesignation.setPrefHeight(25);

		cmbPosting.setPrefWidth(800);
		cmbPosting.setPrefHeight(25);

		vBoxJobProffession.getChildren().remove(0,3);
		vBoxJobProffession.getChildren().add(0,cmbJobProffession);
		vBoxJobProffession.getChildren().add(1,cmbDesignation);
		vBoxJobProffession.getChildren().add(2,cmbPosting);


		cmbSystemNameHistoryIllness.setPrefWidth(800);
		cmbSystemNameHistoryIllness.setPrefHeight(25);
		cmbSystemNameHistoryIllness.setPromptText("System Name");
		cmbSystemNameHistoryIllness.setTooltip(toolTipSystem);

		/*cmbCCHistoryIllness.setPrefWidth(800);
		cmbCCHistoryIllness.setPrefHeight(25);
		cmbCCHistoryIllness.setPromptText("Cheap Complain");*/

		cmbDiseaseHistoryIllness.setPrefWidth(800);
		cmbDiseaseHistoryIllness.setPrefHeight(25);
		cmbDiseaseHistoryIllness.setPromptText("Disease Name");
		cmbDiseaseHistoryIllness.setTooltip(toolTipDisease);

		vBoxIllnessHistory.getChildren().remove(0,2);
		vBoxIllnessHistory.getChildren().add(0,cmbSystemNameHistoryIllness);
		//vBoxIllnessHistory.getChildren().add(1,cmbCCHistoryIllness);
		vBoxIllnessHistory.getChildren().add(1,cmbDiseaseHistoryIllness);


		cmbGenericHistoryDrug.setPrefWidth(800);
		cmbGenericHistoryDrug.setPrefHeight(25);
		cmbGenericHistoryDrug.setPromptText("Generic Name");
		cmbGenericHistoryDrug.setTooltip(toolTipGeneric);

		cmbMedicineHistoryDrug.setPrefWidth(800);
		cmbMedicineHistoryDrug.setPrefHeight(25);
		cmbMedicineHistoryDrug.setPromptText("Medicine Name");
		cmbMedicineHistoryDrug.setTooltip(toolTipMedicine);

		cmbScheduleHistoryDrug.setPrefWidth(800);
		cmbScheduleHistoryDrug.setPrefHeight(25);
		cmbScheduleHistoryDrug.setPromptText("Schedule");


		vBoxDrugHistory.getChildren().remove(0,3);
		vBoxDrugHistory.getChildren().add(0,cmbGenericHistoryDrug);
		vBoxDrugHistory.getChildren().add(1,cmbMedicineHistoryDrug);
		vBoxDrugHistory.getChildren().add(2,cmbScheduleHistoryDrug);


		//cmbSystemNameMed.setPrefWidth(560);
		//cmbSystemNameMed.setPrefHeight(25);
		//cmbSystemNameMed.setPromptText("System Name");
		//cmbSystemNameMed.setTooltip(toolTipSystem);

		/*cmbDiseaseNameMed.setPrefWidth(534);
		cmbDiseaseNameMed.setPrefHeight(25);
		cmbDiseaseNameMed.setPromptText("Disease Name");
		cmbDiseaseNameMed.setTooltip(toolTipDisease);*/

		/*cmbGroupName.setPrefWidth(517);
		cmbGroupName.setPrefHeight(25);
		cmbGroupName.setPromptText("Group Name");
		cmbGroupName.setTooltip(toolTipGroup);*/


		//hBoxMedicineL1.getChildren().addAll(cmbSystemNameMed,cmbDiseaseNameMed,cmbGroupName);



		/*cmbGenericName.setPrefWidth(543);
		cmbGenericName.setPrefHeight(25);
		cmbGenericName.setPromptText("Generic Name");
		cmbGenericName.setTooltip(toolTipGeneric);*/

		/*cmbBrandName.setPrefWidth(585);
		cmbBrandName.setPrefHeight(25);
		cmbBrandName.setPromptText("Brand Name");
		cmbBrandName.setTooltip(toolTipMedicine);*/
		cmbGenericHistoryDrug.setCellFactory(param -> {
			return new ListCell<String>() {

				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item != null) {
						setText(item);
						// Add the Tooltip with the image of the current item
						Tooltip tt = new Tooltip(mapCompanyName.get(item));  
						setTooltip(tt);
					} else {
						setText(null);
						setTooltip(null);
					}
				}
			};
		});




		/*hBoxMedicineL2.getChildren().add(0,cmbGenericName);
		hBoxMedicineL2.getChildren().add(1,cmbBrandName);*/



		cmbCompanyName.setPrefWidth(152);
		cmbCompanyName.setPrefHeight(25);
		cmbCompanyName.setPromptText("Company Name");




		cmbSchedule.setPrefWidth(290);
		cmbSchedule.setPrefHeight(25);
		cmbSchedule.setPromptText("Medicine Schedule");

		cmbTime.setPrefWidth(252);
		cmbTime.setPrefHeight(25);
		cmbTime.setPromptText("Time");

		cmbCourse.setPrefWidth(96);
		cmbCourse.setPrefHeight(25);
		cmbCourse.setPromptText("Course");



		hBoxMedicineBrand.getChildren().remove(1);
		hBoxMedicineBrand.getChildren().add(cmbTime);

		hBoxSchedule.getChildren().clear();
		hBoxSchedule.getChildren().addAll(cmbSchedule,cmbCourse,cmbCompanyName);


		hBoxNextVisit.getChildren().remove(1);
		spinnerNextVisit.setPrefHeight(25);
		spinnerNextVisit.setPrefWidth(70);
		hBoxNextVisit.getChildren().add(1,spinnerNextVisit);
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 365, 0,7);

		spinnerNextVisit.setValueFactory(valueFactory);

		vBoxRefferTo.getChildren().clear();
		cmbRefferTo.setPrefHeight(25);
		cmbRefferTo.setPrefWidth(320);
		cmbRefferTo.setPromptText("Reffer To");
		vBoxRefferTo.getChildren().add(cmbRefferTo);

		treeDrugHistoryLoad();

		treeIllnessHistoryLoad();

		examinationIdArray.clear();
		examinationMap.clear();
		treeExaminationLoad();
	}

	private void treeExaminationLoad() {
		// TODO Auto-generated method stub
		examinationSerial = 0;
		treeExamination.setRoot(treeLoad(rootExamination,"1"));
		treeExamination.setShowRoot(false);
	}


	private TreeItem<ExaminationItem> treeLoad(TreeItem<ExaminationItem> treeItem,String pHeadId) {
		// TODO Auto-generated method stub
		try {
			examinationSerial++;
			treeItem.getChildren().clear();
			ArrayList<String> treeName = new ArrayList<>();
			ArrayList<String> treeId = new ArrayList<>();

			sql = "select * FROM tbclinicalexamination WHERE headid = '"+pHeadId+"' and type != '2'";

			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				treeName.add(rs.getString("name"));
				treeId.add(rs.getString("id"));
			}

			for(int i =0 ;i<treeId.size();i++) {
				examinationIdArray.add(treeId.get(i));
				examinationMap.put(treeId.get(i),new ExaminationItem(examinationSerial,treeId.get(i), pHeadId, treeName.get(i)));
				treeItem.getChildren().add(treeLoad(new TreeItem<ExaminationItem>(examinationMap.get(treeId.get(i))),treeId.get(i)));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		treeItem.setExpanded(false);
		return treeItem;
	}


	private void treeIllnessHistoryLoad() {
		// TODO Auto-generated method stub
		rootIllnessHistory.getChildren().clear();
		rootIllnessHistory.getChildren().add(treeItemPresentIllness);
		rootIllnessHistory.getChildren().add(treeItemPastIllness);
		rootIllnessHistory.getChildren().add(treeItemFatherIllness);
		rootIllnessHistory.getChildren().add(treeItemMotherIllness);
		rootIllnessHistory.getChildren().add(treeItemOtherIllness);

		treeItemPresentIllness.setExpanded(true);
		treeItemPastIllness.setExpanded(true);
		treeItemFatherIllness.setExpanded(true);
		treeItemMotherIllness.setExpanded(true);
		treeItemOtherIllness.setExpanded(true);

		rootIllnessHistory.setExpanded(true);
		treeIllnessHistory.setRoot(rootIllnessHistory);
	}



	private void treeDrugHistoryLoad() {
		// TODO Auto-generated method stub
		rootDrugHistory.getChildren().clear();
		rootDrugHistory.getChildren().add(treeItemPresentDrug);
		rootDrugHistory.getChildren().add(treeItemRecentDrug);
		rootDrugHistory.getChildren().add(treeItemPastDrug);
		rootDrugHistory.getChildren().add(treeItemAllergicDrug);

		treeItemPresentDrug.setExpanded(true);
		treeItemRecentDrug.setExpanded(true);
		treeItemPastDrug.setExpanded(true);
		treeItemAllergicDrug.setExpanded(true);

		rootDrugHistory.setExpanded(true);
		treeDrugHistory.setRoot(rootDrugHistory);
	}



	private void manuItemAdd() {
		// TODO Auto-generated method stub
		//menuBar.getMenus().clear();


		//menuSetup.getItems().clear();
		//menuTrading.getItems().clear();
		//menuReport.getItems().clear();
		//menuAccounts.getItems().clear();
		//menuNotification.getItems().clear();
		//menuSettings.getItems().clear();



		//menuBar.getMenus().add(menuNotification);


		/*if(SessionBeam.getMapModule("1")) {
			menuBar.getMenus().add(menuSetup);
			accordion.getPanes().add(titlePaneSetup);


			if(!SessionBeam.getMapModuleItemBlock("1")) {
				menuSetup.getItems().add(menuItemSupplierCreate);
				vBoxSetup.getChildren().add(btnSupplierCreate);
			}
			if(!SessionBeam.getMapModuleItemBlock("2")) {
				menuSetup.getItems().add(menuItemCustomerCreate);
				vBoxSetup.getChildren().add(btnCusotmerCreate);
			}
			if(!SessionBeam.getMapModuleItemBlock("3")) {
				menuSetup.getItems().add(menuItemCategoryAndItem);
				vBoxSetup.getChildren().add(btnCategoryAndItemCreate);
			}
			if(!SessionBeam.getMapModuleItemBlock("4")) {
				menuSetup.getItems().add(menuItemItemStockAdjustment);
				vBoxSetup.getChildren().add(btnItemStockAdjustment);
			}
			if(!SessionBeam.getMapModuleItemBlock("5")) {
				menuSetup.getItems().add(menuItemBarcodeGenarator);
				vBoxSetup.getChildren().add(btnBarCodeGenerator);
			}

		}


		 */
		//menuNotification.getItems().add(menuItemStockNotification);




	}


	@FXML
	private void setCheapComplainCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/CustomerCreate.fxml"), parentPane,895);
		setTab("Cheap Complain Create", allTabs[0]);
	}
	@FXML
	private void setClinicalExaminationCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/CategoryAndItemCreate.fxml"), parentPane,1000);
		setTab("Clinical EXamination Create", allTabs[1]);
	}

	@FXML
	private void setMedicineCreaate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/SupplierCreate.fxml"), parentPane,895);		
		setTab("Medicine Create", allTabs[2]);
	}

	@FXML
	private void setMedicineScheduleSet(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Medicine Schedule Set", allTabs[3]);
	}

	@FXML
	private void setInvestigationTestCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Investigation/Test Create", allTabs[4]);
	}

	@FXML
	private void setAdviseCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Advise Create", allTabs[5]);
	}

	@FXML
	private void setSystemicSurveyQuestionCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Systemic Survey Question Create", allTabs[6]);
	}


	@FXML
	private void setPatientReport(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/accounts/HeadAndLedgerCreate.fxml"), parentPane,940);
		setTab("Patient Report", allTabs[7]);
	}



	@FXML
	private void setUserCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/settings/UserCreate.fxml"), parentPane,707);
		setTab("User Create", allTabs[8]);
	}

	@FXML
	private void setUserAuthentication(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/settings/UserAuthentication.fxml"), parentPane,805);
		setTab("User Authentication", allTabs[9]);
	}

	@FXML
	private void setMyProfile(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/settings/MyProfile.fxml"), parentPane,516);
		setTab("My Profile", allTabs[10]);
	}

	@FXML
	private void logOutAction(ActionEvent event) {
		try {
			if(AlertMaker.showConfirmationDialog("LogOut?", "Are You Sure to LogOut From Application?")) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/prescription/main/LogIn.fxml"));
				Parent parent = loader.load();
				Stage stage = ((Stage)tabPane.getScene().getWindow());
				Scene scene = new Scene(parent);

				stage.centerOnScreen();
				//stage.setResizable(true);
				//stage.setTitle("Creative Information Technology ");
				stage.setWidth(400);
				stage.setHeight(300);
				stage.centerOnScreen();
				stage.setScene(scene);

				stage.show();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex);
			// Logger.getLogger(MainFxmlController.class.getName());
		}
	}


	private void setTab(String tabName,Parent node) {
		try {
			if(tabPane != null) {
				int i;
				for(i = 0;i<tabPane.getTabs().size();i++) {
					if(tabName.equals(tabPane.getTabs().get(i).getText())) {
						tabPane.getSelectionModel().select(i);
						break;
					}
				}
				if(i==tabPane.getTabs().size()) {
					tabPane.getTabs().add(new Tab(tabName,node));
					tabPane.getSelectionModel().selectLast();
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}


	public void loadAllTabs() {

		allTabs=new Parent[15];


		try {
			allTabs[0] = FXMLLoader.load(getClass().getResource("/prescription/setup/CheapComplainCreate.fxml"));
			allTabs[1] = FXMLLoader.load(getClass().getResource("/prescription/setup/ClinicalExaminationCreate.fxml"));
			allTabs[2] = FXMLLoader.load(getClass().getResource("/prescription/setup/MedicineCreate.fxml"));
			allTabs[3] = FXMLLoader.load(getClass().getResource("/prescription/setup/MedicineScheduleSet.fxml"));
			allTabs[4] = FXMLLoader.load(getClass().getResource("/prescription/setup/InvestigationTestCreate.fxml"));
			allTabs[5] = FXMLLoader.load(getClass().getResource("/prescription/setup/AdviceCreate.fxml"));
			allTabs[6] = FXMLLoader.load(getClass().getResource("/prescription/setup/SystemicSurveyQuestionCreate.fxml"));

			allTabs[7] = FXMLLoader.load(getClass().getResource("/prescription/report/PatientReport.fxml"));

			allTabs[8] = FXMLLoader.load(getClass().getResource("/prescription/settings/UserCreate.fxml"));
			allTabs[9] = FXMLLoader.load(getClass().getResource("/prescription/settings/UserAuthentication.fxml"));
			allTabs[10] = FXMLLoader.load(getClass().getResource("/prescription/settings/MyProfile.fxml"));
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}





	}

	public class Medicine{
		private SimpleStringProperty id;
		private SimpleStringProperty medicineName;
		private SimpleStringProperty schedule;
		private SimpleStringProperty time;
		private SimpleStringProperty course;

		public Medicine(String id,String medicineName,String schedule,String time,String course) {
			this.id = new SimpleStringProperty(id);
			this.medicineName = new SimpleStringProperty(medicineName);
			this.schedule = new SimpleStringProperty(schedule);
			this.time = new SimpleStringProperty(time);
			this.course = new SimpleStringProperty(course);
		}

		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getMedicineName() {
			return medicineName.get();
		}

		public void setMedicineName(String medicineName) {
			this.medicineName = new SimpleStringProperty(medicineName);
		}

		public String getSchedule() {
			return schedule.get();
		}

		public void setSchedule(String schedule) {
			this.schedule = new SimpleStringProperty(schedule);
		}

		public String getTime() {
			return time.get();
		}

		public void setTime(String time) {
			this.time = new SimpleStringProperty(time);
		}

		public String getCourse() {
			return course.get();
		}

		public void setCourse(String course) {
			this.course = new SimpleStringProperty(course);
		}



	}

	public class PresentDrugItem{
		private SimpleStringProperty id;
		private SimpleStringProperty medicineType;
		private SimpleStringProperty medicineName;
		private SimpleStringProperty schedule;

		public PresentDrugItem(String id,String medicineType,String medicineName,String schedule) {
			this.id = new SimpleStringProperty(id);
			this.medicineType = new SimpleStringProperty(medicineType);
			this.medicineName = new SimpleStringProperty(medicineName);
			this.schedule = new SimpleStringProperty(schedule);
		}

		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getMedicineType() {
			return medicineType.get();
		}

		public void setMedicineType(String medicineType) {
			this.medicineType = new SimpleStringProperty(medicineType);
		}

		public String getMedicineName() {
			return medicineName.get();
		}

		public void setMedicineName(String medicineName) {
			this.medicineName = new SimpleStringProperty(medicineName);
		}

		public String getSchedule() {
			return schedule.get();
		}

		public void setSchedule(String schedule) {
			this.schedule = new SimpleStringProperty(schedule);
		}


	}

	public class ClinicalExaminationItem{
		private SimpleIntegerProperty slNo;
		private SimpleStringProperty itemId;
		private SimpleStringProperty headName;
		private SimpleStringProperty itemName;
		private SimpleStringProperty result;

		public ClinicalExaminationItem(int slNo,String id,String headName,String itemName,String result) {
			this.slNo = new SimpleIntegerProperty(slNo);
			this.itemId = new SimpleStringProperty(id);
			this.headName = new SimpleStringProperty(headName);
			this.itemName = new SimpleStringProperty(itemName);
			this.result = new SimpleStringProperty(result);
		}

		public int getSlNo() {
			return slNo.get();
		}

		public void setSlNo(int slNo) {
			this.slNo = new SimpleIntegerProperty(slNo);
		}

		public String getItemId() {
			return itemId.get();
		}

		public void setId(String id) {
			this.itemId = new SimpleStringProperty(id);
		}

		public String getHeadName() {
			return headName.get().equals("")?"":headName.get()+"\n";
		}

		public void seHeadName(String headName) {
			this.headName= new SimpleStringProperty(headName);
		}


		public String getItemName() {
			return getHeadName()+itemName.get();
		}

		public void setItemName(String itemName) {
			this.itemName = new SimpleStringProperty(itemName);
		}

		public String getResult() {
			return result.get();
		}

		public void setResult(String result) {
			this.result = new SimpleStringProperty(result);
		}


	}



	private class ExaminationItem extends HBox{
		private int serialNo = 0;
		private String itemId;
		private String headId;
		private String headName;
		private Text lblName;
		private FxComboBox cmbResult;
		private TextField txtResult;
		private boolean isComboBox;
		private boolean isMultipeResult;


		private CustomContextMenu contextMenu;

		private MenuItem menuItemRemoveResult;

		public ExaminationItem(String name) {
			this.lblName = new Text(name);

			this.getChildren().add(lblName);
		}

		public ExaminationItem(int slNo,String id,String headId,String name) {
			this.serialNo = slNo;
			this.itemId = id;
			this.headId = headId;

			this.lblName = new Text(name);
			this.lblName.prefHeight(25);
			this.lblName.prefWidth(300);
			this.lblName.setWrappingWidth(300);	

			this.txtResult = new TextField();
			this.txtResult.setPrefWidth(150);
			this.cmbResult = new FxComboBox<>();
			this.cmbResult.setPrefWidth(150);

			this.getChildren().add(this.lblName);

			try {
				ResultSet rs = databaseHandler.execQuery("select * FROM tbclinicalexamination WHERE headid = '"+id+"' and type != '2'");
				if(!rs.next()) {
					this.lblName.prefWidth(140);
					this.lblName.setWrappingWidth(140);
					rs= databaseHandler.execQuery("select headnameList,inputField,resultType,sn FROM tbclinicalexamination WHERE headid = '"+headId+"' and id='"+id+"' and type != '2'");
					if(rs.next()) {
						this.isComboBox = rs.getString("inputField").equals("Text Field")?false:true;
						this.isMultipeResult = rs.getString("resultType").equals("Single")?false:true;
						this.headName = rs.getString("headnameList");

					}

					if(getIsComboBox()) {	
						this.getChildren().add(cmbResult);
					}else {	
						this.getChildren().add(txtResult);
					}

					rs = databaseHandler.execQuery("select name FROM tbclinicalexamination WHERE headid = '"+id+"' and type = '2'");
					while(rs.next()) {
						cmbResult.data.add(rs.getString("name"));
					}
				}

			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}

			if(isComboBox) {
				cmbResult.focusedProperty().addListener((ov, oldV, newV) -> {
					if (!newV && !getCmbResult().isEmpty()) { // focus lost
						if(id.equals("4")||id.equals("5")) {
							StringTokenizer token = new StringTokenizer(examinationMap.get("4").getResult());
							double hFt = 0;
							double hInch = 0;
							if(token.hasMoreTokens()) hFt = Double.valueOf(token.nextToken().replaceAll("'", ""));
							if(token.hasMoreTokens()) hInch = Double.valueOf(token.nextToken().replaceAll("'", ""));

							double weight = examinationMap.get("5").getResult().isEmpty()?0:Double.valueOf(examinationMap.get("5").getResult());

							double height = ((hFt * 12) + hInch) / 39.3701;

							double bmi = weight/(height*height);


							examinationMap.get("6").setResult(df.format(bmi));
						}
						int i;
						for( i=0;i<listClinicalExamination.size();i++) {

							if(listClinicalExamination.get(i).getItemId().equals(this.itemId)) {
								if(isMultipeResult) {
									ClinicalExaminationItem tempClincicaExamination= listClinicalExamination.get(i);
									tempClincicaExamination.setResult(tempClincicaExamination.getResult()+", "+getCmbResult());
									listClinicalExamination.remove(i);
									listClinicalExamination.add(i, tempClincicaExamination);
								}else {
									ClinicalExaminationItem tempClincicaExamination= listClinicalExamination.get(i);
									tempClincicaExamination.setResult(getCmbResult());
									listClinicalExamination.remove(i);
									listClinicalExamination.add(i, tempClincicaExamination);
								}

								break;
							}else if(listClinicalExamination.get(i).getSlNo()>this.serialNo ){
								listClinicalExamination.add(i,new ClinicalExaminationItem(this.serialNo,this.itemId, headName, name, getCmbResult()));
								break;
							}
						}
						if(i == listClinicalExamination.size()) {
							listClinicalExamination.add(new ClinicalExaminationItem(this.serialNo,this.itemId, headName, name, getCmbResult()));
						}

						tableClinicalExamination.setItems(listClinicalExamination);
						tableClinicalExamination.refresh();
					}else if(getCmbResult().isEmpty()) {
						for(int i=0;i<listClinicalExamination.size();i++) {
							if(listClinicalExamination.get(i).getItemId().equals(this.itemId)) {
								listClinicalExamination.remove(i);
								tableClinicalExamination.setItems(listClinicalExamination);
								tableClinicalExamination.refresh();
								break;
							}
						}
					}
				});
				cmbResult.focusedProperty().addListener((observable, oldValue, newValue) -> {
					selectCombboxIfFocused(cmbResult);
				});
				contextMenu = new CustomContextMenu(cmbResult.getEditor());
			}else {
				txtResult.focusedProperty().addListener((ov, oldV, newV) -> {
					if (!newV && !getTxtResult().isEmpty()) { // focus lost
						if(id.equals("4")||id.equals("5")) {
							StringTokenizer token = new StringTokenizer(examinationMap.get("4").getResult());
							double hFt = 0;
							double hInch = 0;
							if(token.hasMoreTokens()) hFt = Double.valueOf(token.nextToken().replaceAll("'", ""));
							if(token.hasMoreTokens()) hInch = Double.valueOf(token.nextToken().replaceAll("'", ""));

							double weight = examinationMap.get("5").getResult().isEmpty()?0:Double.valueOf(examinationMap.get("5").getResult());

							double height = ((hFt * 12) + hInch) / 39.3701;

							double bmi = weight/(height*height);

							examinationMap.get("6").setResult(df.format(bmi));
						}
						int i;
						for( i=0;i<listClinicalExamination.size();i++) {

							if(listClinicalExamination.get(i).getItemId().equals(this.itemId)) {
								if(isMultipeResult) {
									listClinicalExamination.get(i).setResult(listClinicalExamination.get(i).getResult()+", "+getTxtResult());
								}else {
									listClinicalExamination.get(i).setResult(getTxtResult());
								}
								break;
							}else if(listClinicalExamination.get(i).getSlNo()>this.serialNo ){
								listClinicalExamination.add(i,new ClinicalExaminationItem(this.serialNo,this.itemId, headName, name, getTxtResult()));
								break;
							}
						}
						if(i == listClinicalExamination.size()) {
							listClinicalExamination.add(new ClinicalExaminationItem(this.serialNo,this.itemId, headName, name, getTxtResult()));
						}


						tableClinicalExamination.setItems(listClinicalExamination);
						tableClinicalExamination.refresh();
					}else if(getTxtResult().isEmpty()) {
						for(int i=0;i<listClinicalExamination.size();i++) {
							if(listClinicalExamination.get(i).getItemId().equals(this.itemId)) {
								listClinicalExamination.remove(i);
								tableClinicalExamination.setItems(listClinicalExamination);
								tableClinicalExamination.refresh();
								break;
							}
						}
					}
				});
				txtResult.focusedProperty().addListener((observable,oldValue,newValue) ->{
					selectTextIfFocused(txtResult);
				});
				contextMenu = new CustomContextMenu(txtResult);

			}

			this.setAlignment(Pos.CENTER_LEFT);
			menuItemRemoveResult = new MenuItem("Remove Result");
			contextMenu.getItems().add(0,menuItemRemoveResult);
			contextMenu.getItems().add(1,new SeparatorMenuItem());

			menuItemRemoveResult.setOnAction(e->{
				if(isComboBox) {
					setCmbResult("");
					for(int i=0;i<listClinicalExamination.size();i++) {
						if(listClinicalExamination.get(i).getItemId().equals(this.itemId)) {
							listClinicalExamination.remove(i);
							tableClinicalExamination.setItems(listClinicalExamination);
							tableClinicalExamination.refresh();
							break;
						}
					}

				}else {
					setTxtResult("");
					for(int i=0;i<listClinicalExamination.size();i++) {
						if(listClinicalExamination.get(i).getItemId().equals(this.itemId)) {
							listClinicalExamination.remove(i);
							tableClinicalExamination.setItems(listClinicalExamination);
							tableClinicalExamination.refresh();
							break;
						}
					}
				}
			});

		}


		public String getHeadId() {
			return headId;
		}

		public void setHeadId(String headId) {
			this.headId = headId;
		}

		public String getHeadName() {
			return headName;
		}

		public void setHeadName(String headName) {
			this.headName = headName;
		}

		public String getLblName() {
			return lblName.getText();
		}

		public void setLblName(String lblName) {
			this.lblName.setText(lblName);
		}

		public String getCmbResult() {
			if(cmbResult.getValue() != null)
				return cmbResult.getValue().toString().replace("'", "''").trim();
			return "";
		}

		public void setCmbResult(String cmbResult) {
			this.cmbResult.setValue(cmbResult);
		}

		public String getTxtResult() {
			return txtResult.getText().replace("'", "''").trim();
		}

		public void setTxtResult(String txtResult) {
			this.txtResult.setText(txtResult);
		}

		public String getResult() {
			if(isComboBox) {
				if(cmbResult.getValue() != null)
					return cmbResult.getValue().toString().replace("'", "''").trim();
				else
					return "";
			}else {
				return txtResult.getText().replace("'", "''").trim();
			}
		}

		public TextField getInputField() {
			if(isComboBox) {
				return cmbResult.getEditor();
			}else {
				return txtResult;
			}
		}

		public void setResult(String result) {
			if(isComboBox) {
				setCmbResult(result);
			}else {
				setTxtResult(result);
			}
		}

		public boolean getIsComboBox() {
			return isComboBox;
		}

		public void setIsComboBox(boolean isComboBox) {
			this.isComboBox = isComboBox;
		}

		public String getExaminationId() {
			return itemId;
		}

		public void setExaminationtId(String id) {
			this.itemId = id;
		}


		public int getSerialNo() {
			return serialNo;
		}

		public void setSerialNo(int serialNo) {
			this.serialNo = serialNo;
		}

		public boolean getResultType() {
			return isMultipeResult;
		}

		public void setResultType(boolean isMultipeResult) {
			this.isMultipeResult = isMultipeResult;
		}


	}


	public class StageCcSuggest extends Stage{

		SuggestedDialogController suggestedController;

		public StageCcSuggest() {
			try {

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shareClasses/SuggestedDialog.fxml"));
				Parent root = fxmlLoader.load();
				suggestedController = fxmlLoader.getController();

				//getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
				//Node closeButton = this.getDialogPane().lookupButton(ButtonType.CANCEL);
				//closeButton.managedProperty().bind(closeButton.visibleProperty());
				//closeButton.setVisible(false);
				this.setTitle("Suggested Question Of C/C");
				this.initModality(Modality.NONE);
				this.setX(780);
				this.setY(100);

				this.setAlwaysOnTop(true);

				this.setScene(new Scene(root));

				suggestedController.btnAdd.setOnAction(e->{
					String id = suggestedController.getCheapComplainId();
					ObservableList<TableItemInfo> temp = tablePrescribedCheapComplain.getItems();
					int i=0;
					for(i = 0;i<temp.size();i++){
						if(id.equals(temp.get(i).getItemId())){
							temp.get(i).setItemName(suggestedController.getTxtCheapComplain());
							break;
						}
					}
					if(temp.size()==i){
						tablePrescribedCheapComplain.getItems().add(new TableItemInfo(0, suggestedController.getCheapComplainId(), "0", suggestedController.getTxtCheapComplain(), NodeType.CHEAP_COMPLAIN.getType()));
					}

					tablePrescribedCheapComplain.refresh();
					//this.close();
				});

				suggestedController.btnCancel.setOnAction(e->{
					this.close();
				});

			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		public void setCheapComplain(String cheapComplain){
			suggestedController.setCheapComplain(cheapComplain);
			suggestedController.setTxtCheapComplain(cheapComplain);
		}


		public void setCheapComplainId(String cheapComplainId){
			suggestedController.setCheapComplainId(cheapComplainId);
		}

		public void setList(String pId) {
			suggestedController.setSuggestList(pId);
		}

		public void clearList() {
			suggestedController.clearMapList();
		}

		public void setShow() {
			if(!isShowing()) {
				this.show();
			}
		}

	}
	public String getTxtAge() {
		return txtAge.getText().trim();
	}
	public void setTxtAge(String txtAge) {
		this.txtAge.setText(txtAge);
	}
	public String getTxtContactNo() {
		return txtContactNo.getText().trim();
	}
	public void setTxtContactNo(String txtContactNo) {
		this.txtContactNo.setText(txtContactNo);
	}
	public String getDate() {
		if(date.getValue() != null)
			return date.getValue().toString();
		else 
			return "";

	}
	public void setDate(Date date) {
		if(date != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(date));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(date));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(date));
			this.date.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.date.getEditor().setText("");
		}
	}
	public String getCmbVisitNo() {
		if(cmbVisitNo.getValue() != null)
			return cmbVisitNo.getValue().toString().trim();
		else return "";
	}
	public void setCmbVisitNo(String cmbVisitNo) {
		this.cmbVisitNo.setValue(cmbVisitNo);
	}

	public String getCmbId() {
		if(cmbId.getValue() != null)
			return cmbId.getValue().toString().trim();
		else return "";
	}
	public void setCmbId(String cmbId) {
		this.cmbId.setValue(cmbId);
	}

	public String getCmbPatientName() {
		if(cmbPatientName.getValue() != null)
			return cmbPatientName.getValue().toString().trim();
		else return "";
	}
	public void setCmbPatientName(String cmbPatientName) {
		this.cmbPatientName.setValue(cmbPatientName);
	}

	public String getCmbSex() {
		if(cmbSex.getValue() != null)
			return cmbSex.getValue().toString().trim();
		else return "";
	}

	public void setCmbSex(String cmbSex) {
		this.cmbSex.setValue(cmbSex);
	}
	public String getCmbAddress() {
		if(cmbAddress.getValue() != null)
			return cmbAddress.getValue().toString().trim();
		else return "";
	}
	public void setCmbAddress(String cmbAddress) {
		this.cmbAddress.setValue(cmbAddress);
	}
	public String getTxtSystemName() {
		return txtSystemName.getText().trim();
	}
	public void setTxtSystemName(String cmbSystem) {
		this.txtSystemName.setText(cmbSystem);
	}


	public String getTxtDiseaseName() {
		return txtDiseaseName.getText().trim();
	}
	public void setTxtDiseaseName(String txtDiseaseName) {
		this.txtDiseaseName.setText(txtDiseaseName);
	}



	public String getTxtCheapComplain() {
		return txtCheapComplain.getText().trim();
	}

	public void setTxtCheapComplain(String txtCheapComplain) {
		this.txtCheapComplain.setText(txtCheapComplain);
	}

	public String getTxtMedicineGroup() {
		return txtMedicineGroup.getText().trim();
	}

	public void setTxtMedicineGroup(String txtMedicineGroup) {
		this.txtMedicineGroup.setText(txtMedicineGroup);
	}

	public String getTxtGeneric() {
		return txtGeneric.getText().trim();
	}

	public void setTxtGeneric(String txtGeneric) {
		this.txtGeneric.setText(txtGeneric);
	}

	public String getTxtMedicineBrand() {
		return txtMedicineBrand.getText().trim();
	}

	public void setTxtMedicineBrand(String txtMedicineBrand) {
		this.txtMedicineBrand.setText(txtMedicineBrand);
	}

	public String getTxtInvestigation() {
		return txtInvestigation.getText().trim();
	}

	public void setTxtInvestigation(String txtInvestigation) {
		this.txtInvestigation.setText(txtInvestigation);
	}

	public String getTxtAdvise() {
		return txtAdvise.getText().trim();
	}

	public void setTxtAdvise(String txtAdvise) {
		this.txtAdvise.setText(txtAdvise);
	}

	public String getCmbDisease() {
		if(cmbDisease.getValue() != null)
			return cmbDisease.getValue().toString().trim();
		else return "";
	}
	public void setCmbDisease(String cmbDisease) {
		this.cmbDisease.setValue(cmbDisease);
	}
	public String getCmbCheapComplain() {
		if(cmbCheapComplain.getValue() != null)
			return cmbCheapComplain.getValue().toString().trim();
		else return "";
	}
	public void setCmbCheapComplain(String cmbCheapComplain) {
		this.cmbCheapComplain.setValue(cmbCheapComplain);
	}
	/*public String getCmbSystemName() {
		if(cmbSystemName.getValue() != null)
			return cmbSystemName.getValue().toString().trim();
		else return "";
	}
	public void setCmbSystemName(String cmbSystemName) {
		this.cmbSystemName.setValue(cmbSystemName);
	}*/
	/*public String getCmbDiseaseNameInd() {
		if(cmbDiseaseNameInd.getValue() != null)
			return cmbDiseaseNameInd.getValue().toString().trim();
		else return "";
	}
	public void setCmbDiseaseNameInd(String cmbDiseaseNameInd) {
		this.cmbDiseaseNameInd.setValue(cmbDiseaseNameInd);
	}
	public String getCmbInvestigationName() {
		if(cmbInvestigationName.getValue() != null)
			return cmbInvestigationName.getValue().toString().trim();
		else return "";
	}
	public void setCmbInvestigationName(String cmbInvestigationName) {
		this.cmbInvestigationName.setValue(cmbInvestigationName);
	}
	public String getCmbAdvise() {
		if(cmbAdvise.getValue() != null)
			return cmbAdvise.getValue().toString().trim();
		else return "";
	}
	public void setCmbAdvise(String cmbAdvise) {
		this.cmbAdvise.setValue(cmbAdvise);
	}*/
	/*public String getCmbSystemNameMed() {
		if(cmbSystemNameMed.getValue() != null)
			return cmbSystemNameMed.getValue().toString().trim();
		else return "";
	}

	public void setCmbSystemNameMed(String cmbSystemNameMed) {
		this.cmbSystemNameMed.setValue(cmbSystemNameMed);
	}

	public String getCmbDiseaseNameMed() {
		if(cmbDiseaseNameMed.getValue() != null)
			return cmbDiseaseNameMed.getValue().toString().trim();
		else return "";
	}
	public void setCmbDiseaseNameMed(String cmbDiseaseNameMed) {
		this.cmbDiseaseNameMed.setValue(cmbDiseaseNameMed);
	}
	public String getCmbGroupName() {
		if(cmbGroupName.getValue() != null)
			return cmbGroupName.getValue().toString().trim();
		else return "";
	}
	public void setCmbGroupName(String cmbGroupName) {
		this.cmbGroupName.setValue(cmbGroupName);
	}
	public String getCmbGenericName() {
		if(cmbGenericName.getValue() != null)
			return cmbGenericName.getValue().toString().trim();
		else return "";
	}
	public void setCmbGenericName(String cmbGenericName) {
		this.cmbGenericName.setValue(cmbGenericName);
	}
	public String getCmbBrandName() {
		if(cmbBrandName.getValue() != null)
			return cmbBrandName.getValue().toString().trim();
		else return "";
	}
	public void setCmbBrandName(String cmbBrandName) {
		this.cmbBrandName.setValue(cmbBrandName);
	}*/
	public String getCmbCompanyName() {
		if(cmbCompanyName.getValue() != null)
			return cmbCompanyName.getValue().toString().trim();
		else return "";
	}
	public void setCmbCompanyName(String cmbCompanyName) {
		this.cmbCompanyName.setValue(cmbCompanyName);
	}
	public String getCmbSchedule() {
		if(cmbSchedule.getValue() != null)
			return cmbSchedule.getValue().toString().trim();
		else return "";
	}
	public void setCmbSchedule(String cmbSchedule) {
		this.cmbSchedule.setValue(cmbSchedule);
	}

	public String getCmbTime() {
		if(cmbTime.getValue() != null)
			return cmbTime.getValue().toString().trim();
		else return "";
	}
	public void setCmbTime(String cmbTime) {
		this.cmbTime.setValue(cmbTime);
	}
	public String getCmbCourse() {
		if(cmbCourse.getValue() != null)
			return cmbCourse.getValue().toString().trim();
		else return "";
	}
	public void setCmbCourse(String cmbCourse) {
		this.cmbCourse.setValue(cmbCourse);
	}

	public String getCmbRefferTo() {
		if(cmbRefferTo.getValue() != null)
			return cmbRefferTo.getValue().toString().trim();
		else return "";
	}
	public void setCmbRefferTo(String cmbRefferTo) {
		this.cmbRefferTo.setValue(cmbRefferTo);
	}
	public String getCmbSystemNameHistorySurvey() {
		if(cmbSystemNameHistorySurvey.getValue() != null)
			return cmbSystemNameHistorySurvey.getValue().toString().trim();
		else return "";
	}

	public void setSpinnerNextVisit(String spinnerNextVisit) {
		this.spinnerNextVisit.getEditor().setText(spinnerNextVisit);
	}
	public String getSpinnerNextVisit() {
		if(spinnerNextVisit.getValue() != null)
			return spinnerNextVisit.getValue().toString().trim();
		else return "";
	}

	public String getTxtSystemNameHistory() {
		return txtSystemNameHistory.getText().trim();
	}
	public void setTxtSystemNameHistory(String cmbSystemHistory) {
		this.txtSystemNameHistory.setText(cmbSystemHistory);
	}
	public String getTxtDiseaseNameHistory() {
		return txtDiseaseNameHistory.getText().trim();
	}
	public void setTxtDiseaseNameHistory(String txtDiseaseNameHistory) {
		this.txtDiseaseNameHistory.setText(txtDiseaseNameHistory);
	}

	public void setCmbSystemNameHistorySurvey(String cmbSystemNameHistorySurvey) {
		this.cmbSystemNameHistorySurvey.setValue(cmbSystemNameHistorySurvey);
	}

	public String getCmbSystemNameHistoryIllness() {
		if(cmbSystemNameHistoryIllness.getValue() != null)
			return cmbSystemNameHistoryIllness.getValue().toString().trim();
		else return "";
	}
	public void setCmbSystemNameHistoryIllness(String cmbSystemNameHistoryIllness) {
		this.cmbSystemNameHistoryIllness.setValue(cmbSystemNameHistoryIllness);
	}

	/*public String getCmbCCHistoryIllness() {
		if(cmbCCHistoryIllness.getValue() != null)
			return cmbCCHistoryIllness.getValue().toString().trim();
		else return "";
	}
	public void setCmbCCHistoryIllness(String cmbCCHistoryIllness) {
		this.cmbCCHistoryIllness.setValue(cmbCCHistoryIllness);
	}*/
	public String getCmbDiseaseHistoryIllness() {
		if(cmbDiseaseHistoryIllness.getValue() != null)
			return cmbDiseaseHistoryIllness.getValue().toString().trim();
		else return "";
	}
	public void setCmbDiseaseHistoryIllness(String cmbDiseaseHistoryIllness) {
		this.cmbDiseaseHistoryIllness.setValue(cmbDiseaseHistoryIllness);
	}

	public String getCmbGenericHistoryDrug() {
		if(cmbGenericHistoryDrug.getValue() != null)
			return cmbGenericHistoryDrug.getValue().toString().trim();
		else return "";
	}
	public void setCmbGenericHistoryDrug(String cmbGenericHistoryDrug) {
		this.cmbGenericHistoryDrug.setValue(cmbGenericHistoryDrug);
	}
	public String getCmbMedicineHistoryDrug() {
		if(cmbMedicineHistoryDrug.getValue() != null)
			return cmbMedicineHistoryDrug.getValue().toString().trim();
		else return "";
	}
	public void setCmbMedicineHistoryDrug(String cmbMedicineHistoryDrug) {
		this.cmbMedicineHistoryDrug.setValue(cmbMedicineHistoryDrug);
	}
	public String getCmbScheduleHistoryDrug() {
		if(cmbScheduleHistoryDrug.getValue() != null)
			return cmbScheduleHistoryDrug.getValue().toString().trim();
		else return "";
	}

	public void setCmbScheduleHistoryDrug(String cmbScheduleHistoryDrug) {
		this.cmbScheduleHistoryDrug.setValue(cmbScheduleHistoryDrug);
	}
	public String getCmbJobProffession() {
		if(cmbJobProffession.getValue() != null)
			return cmbJobProffession.getValue().toString().trim();
		else return "";
	}
	public void setCmbJobProffession(String cmbJobProffession) {
		this.cmbJobProffession.setValue(cmbJobProffession);
	}
	public String getCmbDesignation() {
		if(cmbDesignation.getValue() != null)
			return cmbDesignation.getValue().toString().trim();
		else return "";
	}
	public void setCmbDesignation(String cmbDesignation) {
		this.cmbDesignation.setValue(cmbDesignation);
	}

	public String getCmbPosting() {
		if(cmbPosting.getValue() != null)
			return cmbPosting.getValue().toString().trim();
		else return "";
	}
	public void setCmbPosting(String cmbPosting) {
		this.cmbPosting.setValue(cmbPosting);
	}

	public boolean getRadioPresentIllness() {
		return radioPresentIllness.isSelected();
	}
	public void setRadioPresentIllness(boolean checkPresentIllness) {
		this.radioPresentIllness.setSelected(checkPresentIllness);
	}
	public boolean getRadioPastIllness() {
		return radioPastIllness.isSelected();
	}
	public void setRadioPastIllness(boolean checkPastIllness) {
		this.radioPastIllness.setSelected(checkPastIllness);
	}

	public boolean getRadioFatherIllness() {
		return radioFatherIllness.isSelected();
	}
	public void setRadioFatherIllness(boolean checkFatherIllness) {
		this.radioFatherIllness.setSelected(checkFatherIllness);
	}
	public boolean getRadioMotherIllness() {
		return radioMotherIllness.isSelected();
	}
	public void setRadioMotherIllness(boolean checkMotherIllness) {
		this.radioMotherIllness.setSelected(checkMotherIllness);
	}
	public boolean getRadioOtherIllness() {
		return radioOtherIllness.isSelected();
	}
	public void setRadioOtherIllness(boolean checkOtherIllness) {
		this.radioOtherIllness.setSelected(checkOtherIllness);
	}

	public boolean getRadioPresentDrug() {
		return radioPresentDrug.isSelected();
	}
	public void setRadioPresentDrug(boolean checkPresentDrug) {
		this.radioPresentDrug.setSelected(checkPresentDrug);
	}
	public boolean getRadioRecentDrug() {
		return radioRecentDrug.isSelected();
	}
	public void setRadioRecentDrug(boolean checkRecentDrug) {
		this.radioRecentDrug.setSelected(checkRecentDrug);
	}

	public boolean getRadioPastDrug() {
		return radioPastDrug.isSelected();
	}
	public void setRadioPastDrug(boolean checkPastDrug) {
		this.radioPastDrug.setSelected(checkPastDrug);
	}

	public boolean getRadioAllergicDrug() {
		return radioAllergicDrug.isSelected();
	}
	public void setRadioAllergicDrug(boolean checkAllergicDrug) {
		this.radioAllergicDrug.setSelected(checkAllergicDrug);
	}
	public boolean getCheckSmokingYes() {
		return checkSmokingYes.isSelected();
	}
	public void setCheckSmokingYes(boolean checkSmokingYes) {
		this.checkSmokingYes.setSelected(checkSmokingYes);
	}
	public boolean getCheckSmokingNO() {
		return checkSmokingNO.isSelected();
	}
	public void setCheckSmokingNO(boolean checkSmokingNO) {
		this.checkSmokingNO.setSelected(checkSmokingNO);
	}
	public boolean getCheckBetelYes() {
		return checkBetelYes.isSelected();
	}
	public void setCheckBetelYes(boolean checkBetelYes) {
		this.checkBetelYes.setSelected(checkBetelYes);
	}
	public boolean getCheckBetelNo() {
		return checkBetelNo.isSelected();
	}

	public void setCheckBetelNo(boolean checkBetelNo) {
		this.checkBetelNo.setSelected(checkBetelNo);
	}
	public boolean getCheckRegular() {
		return checkRegular.isSelected();
	}
	public void setCheckRegular(boolean checkRegular) {
		this.checkRegular.setSelected(checkRegular);
	}

	public boolean getCheckEregular() {
		return checkEregular.isSelected();
	}
	public void setCheckEregular(boolean checkEregular) {
		this.checkEregular.setSelected(checkEregular);
	}



}
