package prescription.report;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.security.auth.callback.ChoiceCallback;
import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import shareClasses.FxComboBox;
import shareClasses.Notification;
import shareClasses.NumberField;

public class PatientReportController implements Initializable{


	@FXML
	RadioButton radioPurchaseStatement;
	@FXML
	RadioButton radioPurchaseStatementSupplierWise;
	@FXML
	RadioButton radioSalesStatement;
	@FXML
	RadioButton radioSalesStatementCustomerWise;
	@FXML
	RadioButton radioServicingStatement;
	@FXML
	RadioButton radioServicingStatementCustomerWise;
	@FXML
	RadioButton radioAllItemStockPosition;
	@FXML
	RadioButton radioAllItemStockPositionWithValue;
	@FXML
	RadioButton radioReorderItemList;
	@FXML
	RadioButton radioImeiSerialInformation;

	@FXML
	RadioButton radioSummery;	
	@FXML
	RadioButton radiokDetails;

	@FXML
	Button btnPreview;


	@FXML
	DatePicker dateFromDate;
	@FXML
	DatePicker dateToDate;

	@FXML
	TextField txtReorderQty;
	


	@FXML
	VBox vBoxCustomerName;
	@FXML
	VBox vBoxSupplierName;
	@FXML
	VBox vBoxCategoryName;
	@FXML
	VBox vBoxItemName;
	@FXML
	VBox vBoxImei;

	FxComboBox cmbCustomerName = new FxComboBox<>();
	FxComboBox cmbSupplierName = new FxComboBox<>();
	FxComboBox cmbCategoryName = new FxComboBox<>();
	FxComboBox cmbItemName = new FxComboBox<>();
	FxComboBox cmbImei = new FxComboBox<>();

	@FXML
	CheckBox checkCustomerAll;
	@FXML
	CheckBox checkSupplierAll;
	@FXML
	CheckBox checkCategoryAll;
	@FXML
	CheckBox checkItemNameAll;
	@FXML
	CheckBox checkIMEISerial;

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

	//CheckBox checkSalesStatement;

	private DatabaseHandler databaseHandler;
	private String sql;
	private HashMap map;

	ToggleGroup groupSummeryDetails ;
	ToggleGroup groupReportSelect;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		CustomerLoad();
		SupplierLoad();
		CategoryLoad();
		ItemNameLoad();
		ImeiLoad();

		checkCategoryAll.setDisable(true);
		checkItemNameAll.setDisable(true);
		checkSupplierAll.setDisable(true);
		checkCustomerAll.setDisable(true);
		checkIMEISerial.setDisable(true);

		cmbCategoryName.setDisable(true);
		cmbItemName.setDisable(true);
		cmbSupplierName.setDisable(true);
		cmbCustomerName.setDisable(true);
		cmbImei.setDisable(true);

		setCheckCustomerAll(false);
		setCheckSupplierAll(false);
		setCheckCategoryAll(false);
		setCheckItemNameAll(false);
		setCheckIMEISerial(false);
		
		setCmbCustomerName("");
		setCmbSupplierName("");
		setCmbCategoryName("");
		setCmbItemName("");
		setCmbImei("");

		dateFromDate.setDisable(false);
		dateToDate.setDisable(false);
		
		radioSummery.setDisable(false);
		radiokDetails.setDisable(false);

	}

	@FXML
	private void btnPreviewAction(ActionEvent event) {

		map.put("fromDate", getDateFromDate());
		map.put("toDate", getDateToDate());
		
		if(getRadioPurchaseStatement()) {
			openPurchaseStatement();;
		}else if(getRadioPurchaseStatementSupplierWise()) {
			openPurchaseStatementSupplierWise();;
		}else if(getRadioSalesStatement()) {
			openSaleStatement();
		}else if(getRadioSalesStatementCustomerWise()) {
			openSaleStatementCustomerWise();
		}else if(getRadioServicingStatement()) {
			openServicingStatement();
		}else if(getRadioServicingStatementCustomerWise()) {
			openServicingStatementCustomerWise();
		}else if(getRadioAllItemStockPosition()) {
			openAllItemStockPosition();
		}else if(getRadioAllItemStockPositionWithValue()) {
			openAllItemStockPositionWithValue();
		}else if(getRadioReorderItemList()) {
			openReorderItemList();
		}else if(getRadioImeiSerialInformation()) {
			openImeiSerialInformation();
		}

	}

	
	
	private void openPurchaseStatement() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/PurchaseStatement.jrxml";
				sql = "select date,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbPurchase where type = 1 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by date order by date ";
			}else {
				report = "src/resource/reports/tradingReportFile/PurchaseStatementDetails.jrxml";
				sql = "select date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbPurchase where type = 1 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by date,Invoice order by date";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void openPurchaseStatementSupplierWise() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/PurchaseStatementSupplierWise.jrxml";
				if(getCmbSupplierName().equals(""))
					sql = "select suppliername,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbPurchase where type = 1 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by suppliername order by suppliername";
				else
					sql = "select suppliername,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbPurchase where type = 1 and suppliername = '"+getCmbSupplierName()+"' and supplierId = '"+getSupplierId(getCmbSupplierName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by suppliername order by suppliername";
			}else {
				report = "src/resource/reports/tradingReportFile/PurchaseStatementSupplierWiseDetails.jrxml";

				if(getCmbSupplierName().equals(""))
					sql = "select suppliername,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbPurchase where type = 1 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by suppliername,date,Invoice order by suppliername";
				else
					sql = "select suppliername,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbPurchase where type = 1 and suppliername = '"+getCmbSupplierName()+"' and supplierID = '"+getSupplierId(getCmbSupplierName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by suppliername,date,Invoice order by suppliername";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	private void openSaleStatement() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/SaleStatement.jrxml";
				sql = "select date,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by date order by date ";
			}else {
				report = "src/resource/reports/tradingReportFile/SaleStatementDetails.jrxml";
				sql = "select date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by date,Invoice order by date";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void openSaleStatementCustomerWise() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWise.jrxml";
				if(getCmbCustomerName().equals(""))
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
				else
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbCustomerName()+"' and customerID = '"+getCustomerId(getCmbCustomerName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
			}else {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWiseDetails.jrxml";

				if(getCmbCustomerName().equals(""))
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
				else
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbCustomerName()+"' and customerID = '"+getCustomerId(getCmbCustomerName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private void openServicingStatementCustomerWise() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/ServicingStatementCustomerWise.jrxml";
				if(getCmbCustomerName().equals(""))
					sql = "  select si.customerName,(SUM(isnull(si.totalAmount,0))+SUM(isnull(s.totalAmount,0)))as totalAmount,(SUM(isnull(si.vatAmount,0))+SUM(isnull(s.vatAmount,0)))as vatAmount,(SUM(isnull(si.totalDiscount,0))+SUM(isnull(s.totalDiscount,0)))as totalDiscount,(SUM(isnull(si.netAmount,0))+SUM(isnull(s.netAmount,0)))as netAmount,(SUM(isnull(si.paid,0))+SUM(isnull(s.paid,0)))as paid,(SUM(isnull(si.netAmount,0)) - SUM(isnull(si.paid,0))+SUM(isnull(s.netAmount,0)) - SUM(isnull(s.paid,0)))as due \r\n" + 
							"   from tbServicingInvoice si\r\n" + 
							" left join tbSales s\r\n" + 
							" on s.Invoice = si.Invoice and s.type = 5\r\n" + 
							"  where si.type = 6 and si.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by si.customerName order by si.customerName";
				else
					sql = "  select si.customerName,(SUM(isnull(si.totalAmount,0))+SUM(isnull(s.totalAmount,0)))as totalAmount,(SUM(isnull(si.vatAmount,0))+SUM(isnull(s.vatAmount,0)))as vatAmount,(SUM(isnull(si.totalDiscount,0))+SUM(isnull(s.totalDiscount,0)))as totalDiscount,(SUM(isnull(si.netAmount,0))+SUM(isnull(s.netAmount,0)))as netAmount,(SUM(isnull(si.paid,0))+SUM(isnull(s.paid,0)))as paid,(SUM(isnull(si.netAmount,0)) - SUM(isnull(si.paid,0))+SUM(isnull(s.netAmount,0)) - SUM(isnull(s.paid,0)))as due \r\n" + 
							"   from tbServicingInvoice si\r\n" + 
							" left join tbSales s\r\n" + 
							" on s.Invoice = si.Invoice and s.type = 5\r\n" + 
							"  where si.type = 6 and si.customerName = '"+getCmbCustomerName()+"' and si.customerID = '"+getCustomerId(getCmbCustomerName())+"' and si.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by si.customerName order by si.customerName";					
			}else {
				report = "src/resource/reports/tradingReportFile/ServicingStatementCustomerWiseDetails.jrxml";

				if(getCmbCustomerName().equals(""))
					sql = "  select si.customerName,si.date,si.Invoice,(SUM(isnull(si.totalAmount,0))+SUM(isnull(s.totalAmount,0)))as totalAmount,(SUM(isnull(si.vatAmount,0))+SUM(isnull(s.vatAmount,0)))as vatAmount,(SUM(isnull(si.totalDiscount,0))+SUM(isnull(s.totalDiscount,0)))as totalDiscount,(SUM(isnull(si.netAmount,0))+SUM(isnull(s.netAmount,0)))as netAmount,(SUM(isnull(si.paid,0))+SUM(isnull(s.paid,0)))as paid,(SUM(isnull(si.netAmount,0)) - SUM(isnull(si.paid,0))+SUM(isnull(s.netAmount,0)) - SUM(isnull(s.paid,0)))as due \r\n" + 
							"   from tbServicingInvoice si\r\n" + 
							" left join tbSales s\r\n" + 
							" on s.Invoice = si.Invoice and s.type = 5\r\n" + 
							"   where si.type = 6 and si.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by si.customerName,si.date,si.Invoice order by customerName";
				else
					sql = "  select si.customerName,si.date,si.Invoice,(SUM(isnull(si.totalAmount,0))+SUM(isnull(s.totalAmount,0)))as totalAmount,(SUM(isnull(si.vatAmount,0))+SUM(isnull(s.vatAmount,0)))as vatAmount,(SUM(isnull(si.totalDiscount,0))+SUM(isnull(s.totalDiscount,0)))as totalDiscount,(SUM(isnull(si.netAmount,0))+SUM(isnull(s.netAmount,0)))as netAmount,(SUM(isnull(si.paid,0))+SUM(isnull(s.paid,0)))as paid,(SUM(isnull(si.netAmount,0)) - SUM(isnull(si.paid,0))+SUM(isnull(s.netAmount,0)) - SUM(isnull(s.paid,0)))as due \r\n" + 
							"   from tbServicingInvoice si\r\n" + 
							" left join tbSales s\r\n" + 
							" on s.Invoice = si.Invoice and s.type = 5\r\n" + 
							"   where si.type = 6 and si.customerName = '"+getCmbCustomerName()+"' and si.customerID = '"+getCustomerId(getCmbCustomerName())+"' and si.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by si.customerName,si.date,si.Invoice order by customerName";

			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void openServicingStatement() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/ServicingStatement.jrxml";
				sql = "select si.date,(SUM(isnull(si.totalAmount,0))+SUM(isnull(s.totalAmount,0)))as totalAmount,(SUM(isnull(si.vatAmount,0))+SUM(isnull(s.vatAmount,0)))as vatAmount,(SUM(isnull(si.totalDiscount,0))+SUM(isnull(s.totalDiscount,0)))as totalDiscount,(SUM(isnull(si.netAmount,0))+SUM(isnull(s.netAmount,0)))as netAmount,(SUM(isnull(si.paid,0))+SUM(isnull(s.paid,0)))as paid,(SUM(isnull(si.netAmount,0)) - SUM(isnull(si.paid,0))+SUM(isnull(s.netAmount,0)) - SUM(isnull(s.paid,0)))as due \r\n" + 
						"from tbServicingInvoice si\r\n" + 
						"left join tbSales s\r\n" + 
						"on s.Invoice = si.Invoice and s.type= 5 \r\n" + 
						"where si.type = 6 and si.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by si.date";
			}else {
				report = "src/resource/reports/tradingReportFile/ServicingStatementDetails.jrxml";
				sql = "select si.date,si.Invoice,(SUM(isnull(si.totalAmount,0))+SUM(isnull(s.totalAmount,0)))as totalAmount,(SUM(isnull(si.vatAmount,0))+SUM(isnull(s.vatAmount,0)))as vatAmount,(SUM(isnull(si.totalDiscount,0))+SUM(isnull(s.totalDiscount,0)))as totalDiscount,(SUM(isnull(si.netAmount,0))+SUM(isnull(s.netAmount,0)))as netAmount,(SUM(isnull(si.paid,0))+SUM(isnull(s.paid,0)))as paid,(SUM(isnull(si.netAmount,0)) - SUM(isnull(si.paid,0))+SUM(isnull(s.netAmount,0)) - SUM(isnull(s.paid,0)))as due \r\n" + 
						" from tbServicingInvoice si\r\n" + 
						" left join tbSales s\r\n" + 
						" on s.Invoice = si.Invoice and s.type = 5\r\n" + 
						"  where si.type = 6 and si.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by si.date,si.Invoice order by date";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private void openAllItemStockPosition() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/AllItemStockReportSummery.jrxml";

			}else {
				report = "src/resource/reports/tradingReportFile/AllItemStockReportDetails.jrxml";		
			}

			if(getCheckItemNameAll()) {
				sql = "select *from FnCurrentStockReport() order by catagoryName,itemname";
			}else {
				if(!getCmbItemName().isEmpty()) {
					sql = "select *from FnCurrentStockReport()  where itemname = '"+getCmbItemName()+"' order by catagoryName,itemname";
				}else if(getCheckCategoryAll()) {
					sql = "select *from FnCurrentStockReport()  order by catagoryName,itemname";
				}else {
					if(!getCmbCategoryName().isEmpty()) {
						sql = "select *from FnCurrentStockReport()  where catagoryName = '"+getCmbCategoryName()+"' order by catagoryName,itemname";
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Any Category/Item Name...","Please Select a valid Category/Item Name");
						cmbCategoryName.requestFocus();
					}
				}
			}



			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void openAllItemStockPositionWithValue() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/AllItemReportWithValueSummery.jrxml";

			}else {
				report = "src/resource/reports/tradingReportFile/AllItemReportWithValue.jrxml";		
			}

			if(getCheckItemNameAll()) {
				sql = "select * from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') order by categroyName,productname";
			}else {
				if(!getCmbItemName().isEmpty()) {
					sql = "select *from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where productName = '"+getCmbItemName()+"' order by categroyName,productname";
				}else if(getCheckCategoryAll()) {
					sql = "select * from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') order by categroyName,productname";
				}else {
					if(!getCmbCategoryName().isEmpty()) {
						sql = "select *from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where categroyName = '"+getCmbCategoryName()+"' order by categroyName,productname";
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Any Category/Item Name...","Please Select a valid Category/Item Name");
						cmbCategoryName.requestFocus();
					}
				}
			}



			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void openReorderItemList() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";


			report = "src/resource/reports/tradingReportFile/ReorderItemList.jrxml";


			if(getCheckCategoryAll() || getCmbCategoryName().isEmpty()) {

				if(!getTxtReorderQty().isEmpty()) {
					sql = "select c.categoryName,i.projectedItemName,i.unit,dbo.presentStock(i.id) as presentStock,"+getTxtReorderQty()+" as ReorderQty from tbItem i\r\n" + 
							"join tbcategory c \r\n" + 
							"on i.CategoryId = c.id where dbo.presentStock(i.id)<"+getTxtReorderQty()+" order by c.categoryName,i.projectedItemName ";
				}else {
					sql = "select c.categoryName,i.projectedItemName,i.unit,dbo.presentStock(i.id) as presentStock,i.ReorderQty from tbItem i\r\n" + 
							"join tbcategory c \r\n" + 
							"on i.CategoryId = c.id where dbo.presentStock(i.id)<i.ReorderQty order by c.categoryName,i.projectedItemName ";
				}

			}else {	
				
					if(!getTxtReorderQty().isEmpty()) {
						sql = "select c.categoryName,i.projectedItemName,i.unit,dbo.presentStock(i.id) as presentStock,"+getTxtReorderQty()+" as ReorderQty from tbItem i\r\n" + 
								"join tbcategory c \r\n" + 
								"on i.CategoryId = c.id where dbo.presentStock(i.id)<"+getTxtReorderQty()+" and c.categoryName = '"+getCmbCategoryName()+"' order by c.categoryName,i.projectedItemName ";
					}else {
						sql = "select c.categoryName,i.projectedItemName,i.unit,dbo.presentStock(i.id) as presentStock,i.ReorderQty from tbItem i\r\n" + 
								"join tbcategory c \r\n" + 
								"on i.CategoryId = c.id where dbo.presentStock(i.id)<i.ReorderQty and c.categoryName = '"+getCmbCategoryName()+"' order by c.categoryName,i.projectedItemName ";
					}
				
				
			}



			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void openImeiSerialInformation() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";


			report = "src/resource/reports/tradingReportFile/ImeiSerialInformaion.jrxml";


			if(getCheckCategoryAll() || (getCmbImei().isEmpty() && getCmbItemName().isEmpty())) {

				sql = "select i.projectedItemName,im.imei,isnull(im.purchaseInvoiceNo,'')as purchaseInvoiceNo,\r\n" + 
						"isnull(im.purchaseReturnInvoiceNo,'') as purchaseReturnInvoiceNo,\r\n" + 
						"isnull(im.SalesInvoiceNo,'') as SalesInvoiceNo,\r\n" + 
						"isnull(im.SalesReturnInvoiceNo,'') as SalesReturnInvoiceNo,\r\n" + 
						"isnull(im.inType,'') as inType,\r\n" + 
						"isnull(im.outType,'') as outType,im.isAvailable from tbIMEI im\r\n" + 
						"join tbItem i\r\n" + 
						"on im.itemID = i.id order by i.projectedItemName";

			}else {	
				
				if(!getCmbImei().isEmpty()) {
					sql = "select i.projectedItemName,im.imei,isnull(im.purchaseInvoiceNo,'')as purchaseInvoiceNo,\r\n" + 
							"isnull(im.purchaseReturnInvoiceNo,'') as purchaseReturnInvoiceNo,\r\n" + 
							"isnull(im.SalesInvoiceNo,'') as SalesInvoiceNo,\r\n" + 
							"isnull(im.SalesReturnInvoiceNo,'') as SalesReturnInvoiceNo,\r\n" + 
							"isnull(im.inType,'') as inType,\r\n" + 
							"isnull(im.outType,'') as outType,im.isAvailable from tbIMEI im\r\n" + 
							"join tbItem i\r\n" + 
							"on im.itemID = i.id  where im.imei = '"+getCmbImei()+"' order by i.projectedItemName";
				}else if(!getCmbItemName().isEmpty()) {
					sql = "select i.projectedItemName,im.imei,isnull(im.purchaseInvoiceNo,'')as purchaseInvoiceNo,\r\n" + 
							"isnull(im.purchaseReturnInvoiceNo,'') as purchaseReturnInvoiceNo,\r\n" + 
							"isnull(im.SalesInvoiceNo,'') as SalesInvoiceNo,\r\n" + 
							"isnull(im.SalesReturnInvoiceNo,'') as SalesReturnInvoiceNo,\r\n" + 
							"isnull(im.inType,'') as inType,\r\n" + 
							"isnull(im.outType,'') as outType,im.isAvailable from tbIMEI im\r\n" + 
							"join tbItem i\r\n" + 
							"on im.itemID = i.id  where i.projectedItemName = '"+getCmbItemName()+"' order by i.projectedItemName";
				}
				
				
				
			}



			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private boolean isCustomerExist(String customerName) {
		try {
			sql = "select * from tbCustomer where CustomerName = '"+customerName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return false;
	}

	private String getCustomerId(String customerName) {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbCustomer where CustomerName = '"+customerName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	private String getSupplierId(String supplierName) {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbSupplier where SupplierName = '"+supplierName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	public void CustomerLoad() {
		/*try {
			sql = "select CustomerName from tbCustomer";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbCustomerName.data.clear();
			while (rs.next()) {
				cmbCustomerName.data.add(rs.getString("CustomerName"));
			}

			sql = "select CustomerName from tbSales where customerID = 0 group by customerName order by customerName";
			rs = databaseHandler.execQuery(sql);
			while (rs.next()) {
				cmbCustomerName.data.add(rs.getString("CustomerName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	public void SupplierLoad() {
		/*try {
			sql = "select supplierName from tbSupplier";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbSupplierName.data.clear();
			while (rs.next()) {
				cmbSupplierName.data.add(rs.getString("supplierName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	public void CategoryLoad() {
		/*try {
			sql = "select categoryName from tbCategory order by categoryName";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbCategoryName.data.clear();
			while (rs.next()) {
				cmbCategoryName.data.add(rs.getString("categoryName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	public void ItemNameLoad() {
		/*try {
			sql = "select projectedItemName from tbItem order by projectedItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbItemName.data.clear();
			while (rs.next()) {
				cmbItemName.data.add(rs.getString("projectedItemName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}
	public void ImeiLoad() {
		/*try {
			sql = "select imei from tbIMEI";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbImei.data.clear();
			while (rs.next()) {
				cmbImei.data.add(rs.getString("imei"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbCustomerName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCustomerName);
		});

		cmbSupplierName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSupplierName);
		});

		cmbCategoryName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCategoryName);
		});

		cmbItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemName);
		});
		
		cmbImei.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbImei);
		});

		txtReorderQty.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtReorderQty);
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

		checkCustomerAll.setOnAction(e->{
			if(checkCustomerAll.isSelected()) {

				setCmbCustomerName("");		
				cmbCustomerName.setDisable(true);

			}else {

				//setCmbCustomerName("");
				cmbCustomerName.setDisable(false);

			}
		});

		checkSupplierAll.setOnAction(e->{
			if(checkSupplierAll.isSelected()) {

				setCmbSupplierName("");			
				cmbSupplierName.setDisable(true);

			}else {

				//setCmbSupplierName("");
				cmbSupplierName.setDisable(false);

			}
		});

		checkCategoryAll.setOnAction(e->{
			if(checkCategoryAll.isSelected()) {

				setCmbItemName("");
				setCmbCategoryName("");
				cmbItemName.setDisable(true);
				cmbCategoryName.setDisable(true);
			}else {

				//setCmbItemName("");
				//setCmbCategoryName("");
				cmbItemName.setDisable(false);
				cmbCategoryName.setDisable(false);
			}
		});

		checkItemNameAll.setOnAction(e->{
			if(checkItemNameAll.isSelected()) {

				setCmbItemName("");			
				cmbItemName.setDisable(true);

			}else {

				//setCmbItemName("");
				cmbItemName.setDisable(false);

			}
		});
		
		checkIMEISerial.setOnAction(e->{
			if(checkIMEISerial.isSelected()) {

				setCmbImei("");			
				cmbImei.setDisable(true);
				cmbItemName.setDisable(true);

			}else {

				//setCmbImei("");
				cmbImei.setDisable(false);
				cmbItemName.setDisable(false);

			}
		});
		
		radioPurchaseStatement.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);
			checkIMEISerial.setDisable(true);
			

			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);
			cmbImei.setDisable(true);

			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
			
			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioPurchaseStatementSupplierWise.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(false);
			checkCustomerAll.setDisable(true);
			checkIMEISerial.setDisable(true);

			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(false);
			cmbCustomerName.setDisable(true);
			cmbImei.setDisable(true);

			setCheckCustomerAll(false);
			setCheckSupplierAll(true);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
			
			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioSalesStatement.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);
			checkIMEISerial.setDisable(true);
			

			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);
			cmbImei.setDisable(true);

			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
			
			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioSalesStatementCustomerWise.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(false);
			checkIMEISerial.setDisable(true);

			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(false);
			cmbImei.setDisable(true);

			setCheckCustomerAll(true);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
			
			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioServicingStatement.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);
			checkIMEISerial.setDisable(true);

			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);
			cmbImei.setDisable(true);

			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
			
			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioServicingStatementCustomerWise.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(false);
			checkIMEISerial.setDisable(true);

			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(false);
			cmbImei.setDisable(true);

			setCheckCustomerAll(true);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
			
			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioAllItemStockPosition.setOnAction(e->{
			checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);
			checkIMEISerial.setDisable(true);

			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);
			cmbImei.setDisable(true);

			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(true);
			setCheckItemNameAll(true);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(true);
			dateToDate.setDisable(true);
			
			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioAllItemStockPositionWithValue.setOnAction(e->{
			checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);
			checkIMEISerial.setDisable(true);

			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);
			cmbImei.setDisable(true);

			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(true);
			setCheckItemNameAll(true);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
			
			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});
		
		radioReorderItemList.setOnAction(e->{
			checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);
			checkIMEISerial.setDisable(true);

			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);
			cmbImei.setDisable(true);

			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(true);
			setCheckItemNameAll(true);
			setCheckIMEISerial(false);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(false);

			dateFromDate.setDisable(true);
			dateToDate.setDisable(true);
			
			radioSummery.setDisable(true);
			radiokDetails.setDisable(true);
		});
		
		
		radioImeiSerialInformation.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);
			checkIMEISerial.setDisable(false);

			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);
			cmbImei.setDisable(false);

			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);
			setCheckIMEISerial(true);
			
			setTxtReorderQty("");
			txtReorderQty.setDisable(true);

			dateFromDate.setDisable(true);
			dateToDate.setDisable(true);
			
			radioSummery.setDisable(true);
			radiokDetails.setDisable(true);
		});
	}

	private void setCmpData() {
		// TODO Auto-generated method stub

		dateFromDate.setConverter(converter);
		dateFromDate.setValue(LocalDate.now());

		dateToDate.setConverter(converter);
		dateToDate.setValue(LocalDate.now());

		radioSummery.setToggleGroup(groupSummeryDetails);
		radiokDetails.setToggleGroup(groupSummeryDetails);
		radioSummery.setSelected(true);


		//groupReportSelect.getChildren().addAll(checkSalesStatement,checkSalesStatementSupplierWise);
		radioPurchaseStatement.setToggleGroup(groupReportSelect);
		radioPurchaseStatementSupplierWise.setToggleGroup(groupReportSelect);
		radioSalesStatement.setToggleGroup(groupReportSelect);
		radioSalesStatementCustomerWise.setToggleGroup(groupReportSelect);
		radioServicingStatement.setToggleGroup(groupReportSelect);
		radioServicingStatementCustomerWise.setToggleGroup(groupReportSelect);
		radioAllItemStockPosition.setToggleGroup(groupReportSelect);
		radioAllItemStockPositionWithValue.setToggleGroup(groupReportSelect);
		radioReorderItemList.setToggleGroup(groupReportSelect);
		radioImeiSerialInformation.setToggleGroup(groupReportSelect);
		
		radioSalesStatement.setSelected(true);

		txtReorderQty.setTextFormatter(NumberField.getIntegerFormate());
	}

	private void focusMoveAction() {
		// TODO Auto-generated method stub

	}

	private void addCmp() {
		// TODO Auto-generated method stub

		map = new HashMap<>();
		groupSummeryDetails = new ToggleGroup();
		groupReportSelect = new ToggleGroup();

		vBoxCustomerName.getChildren().clear();
		cmbCustomerName.setPrefHeight(28);
		cmbCustomerName.setPrefWidth(295);
		vBoxCustomerName.getChildren().add(cmbCustomerName);

		vBoxSupplierName.getChildren().clear();
		cmbSupplierName.setPrefHeight(28);
		cmbSupplierName.setPrefWidth(295);
		vBoxSupplierName.getChildren().add(cmbSupplierName);

		vBoxCategoryName.getChildren().clear();
		cmbCategoryName.setPrefHeight(28);
		cmbCategoryName.setPrefWidth(295);
		vBoxCategoryName.getChildren().add(cmbCategoryName);

		vBoxItemName.getChildren().clear();
		cmbItemName.setPrefHeight(28);
		cmbItemName.setPrefWidth(295);
		vBoxItemName.getChildren().add(cmbItemName);
		
		vBoxImei.getChildren().clear();
		cmbImei.setPrefHeight(28);
		cmbImei.setPrefWidth(295);
		vBoxImei.getChildren().add(cmbImei);

	}
	
	public boolean getRadioPurchaseStatement() {
		return radioPurchaseStatement.isSelected();
	}

	public void setRadioPurchaseStatement(boolean radioPurchaseStatement) {
		this.radioPurchaseStatement.setSelected(radioPurchaseStatement);
	}

	public boolean getRadioPurchaseStatementSupplierWise() {
		return radioPurchaseStatementSupplierWise.isSelected();
	}

	public void setRadioPurchaseStatementSupplierWise(boolean radioPurchaseStatementSupplierWise) {
		this.radioPurchaseStatementSupplierWise.setSelected(radioPurchaseStatementSupplierWise);
	}

	public boolean getRadioSalesStatement() {
		return radioSalesStatement.isSelected();
	}

	public void setRadioSalesStatement(boolean radioSalesStatement) {
		this.radioSalesStatement.setSelected(radioSalesStatement);
	}

	public boolean getRadioSalesStatementCustomerWise() {
		return radioSalesStatementCustomerWise.isSelected();
	}

	public void setRadioSalesStatementCustomerWise(boolean radioSalesStatementCustomerWise) {
		this.radioSalesStatementCustomerWise.setSelected(radioSalesStatementCustomerWise);
	}

	public boolean getRadioSummery() {
		return radioSummery.isSelected();
	}

	public void setRadioSummery(boolean radioSummery) {
		this.radioSummery.setSelected( radioSummery);
	}

	public boolean getRadiokDetails() {
		return radiokDetails.isSelected();
	}

	public void setRadiokDetails(boolean radiokDetails) {
		this.radiokDetails.setSelected( radiokDetails);
	}



	public boolean getRadioServicingStatement() {
		return radioServicingStatement.isSelected();
	}

	public void setRadioServicingStatement(boolean radioServicingStatement) {
		this.radioServicingStatement.setSelected(radioServicingStatement);
	}

	public boolean getRadioServicingStatementCustomerWise() {
		return radioServicingStatementCustomerWise.isSelected();
	}

	public void setRadioServicingStatementCustomerWise(boolean radioServicingStatementCustomerWise) {
		this.radioServicingStatementCustomerWise.setSelected(radioServicingStatementCustomerWise);
	}

	public boolean getRadioAllItemStockPosition() {
		return radioAllItemStockPosition.isSelected();
	}

	public void setRadioAllItemStockPosition(boolean RadioAllItemStockPosition) {
		this.radioAllItemStockPosition.setSelected(RadioAllItemStockPosition);
	}

	public boolean getRadioAllItemStockPositionWithValue() {
		return radioAllItemStockPositionWithValue.isSelected();
	}

	public void setRadioAllItemStockPositionWithValue(boolean RadioAllItemStockPositionWithValue) {
		this.radioAllItemStockPositionWithValue.setSelected(RadioAllItemStockPositionWithValue);
	}

	public boolean getRadioReorderItemList() {
		return radioReorderItemList.isSelected();
	}

	public void setRadioReorderItemList(boolean radioReorderItemList) {
		this.radioReorderItemList.setSelected(radioReorderItemList);
	}
	
	public boolean getRadioImeiSerialInformation() {
		return radioImeiSerialInformation.isSelected();
	}

	public void setRadioImeiSerialInformation(boolean radioImeiSerialInformation) {
		this.radioImeiSerialInformation.setSelected(radioImeiSerialInformation);
	}

	public String getDateFromDate() {
		if(dateFromDate.getValue() != null)
			return dateFromDate.getValue().toString();
		else 
			return "";
	}

	public void setDateFromDate(DatePicker dateFromDate) {
		if(dateFromDate != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(dateFromDate));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(dateFromDate));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(dateFromDate));
			this.dateFromDate.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.dateFromDate.getEditor().setText("");
			this.dateFromDate.setValue(null);
		}
	}

	public String getDateToDate() {
		if(dateToDate.getValue() != null)
			return dateToDate.getValue().toString();
		else 
			return "";
	}


	public boolean getCheckCustomerAll() {
		return checkCustomerAll.isSelected();
	}

	public void setCheckCustomerAll(boolean checkCustomerAll) {
		this.checkCustomerAll.setSelected(checkCustomerAll);
	}

	public boolean getCheckSupplierAll() {
		return checkSupplierAll.isSelected();
	}

	public void setCheckSupplierAll(boolean checkSupplierAll) {
		this.checkSupplierAll.setSelected(checkSupplierAll);
	}

	public boolean getCheckCategoryAll() {
		return checkCategoryAll.isSelected();
	}

	public void setCheckCategoryAll(boolean checkCategoryAll) {
		this.checkCategoryAll.setSelected(checkCategoryAll);
	}

	public boolean getCheckItemNameAll() {
		return checkItemNameAll.isSelected();
	}

	public void setCheckItemNameAll(boolean checkItemNameAll) {
		this.checkItemNameAll.setSelected(checkItemNameAll);
	}
	
	public boolean getCheckIMEISerial() {
		return checkIMEISerial.isSelected();
	}

	public void setCheckIMEISerial(boolean checkIMEISerial) {
		this.checkIMEISerial.setSelected(checkIMEISerial);
	}

	public String getCmbCustomerName() {
		if(cmbCustomerName.getValue() != null)
			return cmbCustomerName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbCustomerName(String cmbCustomerName) {
		if(cmbCustomerName.equals("")) 
			this.cmbCustomerName.setValue(null);
		this.cmbCustomerName.getEditor().setText(cmbCustomerName);
	}


	public String getCmbSupplierName() {
		if(cmbSupplierName.getValue() != null)
			return cmbSupplierName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbSupplierName(String cmbSupplierName) {
		if(cmbSupplierName.equals("")) 
			this.cmbSupplierName.setValue(null);
		this.cmbSupplierName.getEditor().setText(cmbSupplierName);
	}

	public String getCmbCategoryName() {
		if(cmbCategoryName.getValue() != null)
			return cmbCategoryName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbCategoryName(String cmbCategory) {
		if(cmbCategory.equals("")) 
			this.cmbCategoryName.setValue(null);
		this.cmbCategoryName.getEditor().setText(cmbCategory);
	}

	public String getCmbItemName() {
		if(cmbItemName.getValue() != null)
			return cmbItemName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbItemName(String cmbItemName) {
		if(cmbItemName.equals("")) 
			this.cmbItemName.setValue(null);
		this.cmbItemName.getEditor().setText(cmbItemName);
	}


	public String getCmbImei() {
		if(cmbImei.getValue() != null)
			return cmbImei.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbImei(String cmbImei) {
		if(cmbImei.equals("")) 
			this.cmbImei.setValue(null);
		this.cmbImei.getEditor().setText(cmbImei);
	}
	public void setDateToDate(Date dateToDate) {
		if(dateToDate != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(dateToDate));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(dateToDate));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(dateToDate));
			this.dateToDate.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.dateToDate.getEditor().setText("");
			this.dateToDate.setValue(null);
		}
	}

	public String getTxtReorderQty() {
		return txtReorderQty.getText().trim();
	}


	public void setTxtReorderQty(String txtReorderQty) {
		this.txtReorderQty.setText(txtReorderQty);
	}






}
