package prescription.main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import shareClasses.AlertMaker;
import shareClasses.CommonMethod;
import shareClasses.FocusMoveByEnter;
import shareClasses.LoadedInfo;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import ui.util.MainUtil;

public class LoginController implements Initializable{

	Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

	@FXML
	TextField txtUserName;
	@FXML
	TextField txtPassword;

	@FXML
	Button btnLogIn;
	@FXML
	Button btnCancel;

	String sql ;
	DatabaseHandler databaseHandler;
	Stage stageMain = new Stage();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		focusMoveAction();
		setCmpAction();
		setCmpFocusAction();
		setCmpData();
	}



	private void setCmpAction() {
		// TODO Auto-generated method stub
		btnLogIn.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			loginAction(null); });
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		txtUserName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtUserName);
		});
		txtPassword.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPassword);
		});

	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		setTxtUserName("Nasir");
		setTxtPassword("123");
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
		Control[] control =  {txtUserName,txtPassword,btnLogIn};
		new FocusMoveByEnter(control);
	}

	@FXML
	private void loginAction(ActionEvent event) {

		if(!getTxtUserName().isEmpty()) {
			if(!getTxtPassword().isEmpty()) {
				if(isUserNameExist(getTxtUserName())) {
					if(isPasswordValid(getTxtUserName(),getTxtPassword())) {			
						loadMain();
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic","Invalid Password..","Please Enter Valid Password...");
						txtPassword.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic","Invalid User Name..","Please Enter valid user name...");
					txtUserName.requestFocus();
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic","Enter Password..","Please Enter your password...");
				txtPassword.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic","Enter User Name..","Please Enter your user name...");
			txtUserName.requestFocus();
		}


		//}else
		{ 
			//txtUserName.getStyleClass().add("wrong-credentials");
			//txtPassword.getStyleClass().add("wrong-credentials");
		}

	}

	private boolean isUserNameExist(String userName) {
		// TODO Auto-generated method stub
		try {
			sql = "select user_id from tblogin where username = '"+userName+"' and activeStatus = 'Active'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean isPasswordValid(String userName, String password) {
		// TODO Auto-generated method stub
		try {
			sql = "select user_id from tblogin where username = '"+userName+"' and password = '"+password+"' and activeStatus = 'Active'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@FXML
	private void cancelAction(ActionEvent event) {
		System.exit(0);
	}

	/*void loadMain() {
		try {

			new SessionBeam(getTxtUserName());

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/prescription/main/MainFrame.fxml"));
			Parent parent = loader.load();
			MainFrameController mainContoller = loader.getController();
			Stage stage = ((Stage)txtUserName.getScene().getWindow());
			Scene scene = new Scene(parent);

			//scene.setRoot(parent);


			stage.setTitle("Prescription System (Dr.Mohammad Moinddin Chowdhury)");
			stage.setScene(scene);

			stage.centerOnScreen();
			stage.setResizable(true);

			stage.setX(bounds.getMinX());
			stage.setY(bounds.getMinY());
			stage.setWidth(bounds.getWidth());
			stage.setHeight(bounds.getHeight());
			stage.setMaximized(true);
			stage.setOnCloseRequest(e->{
				if(AlertMaker.showConfirmationDialog("Close Application?", "Are You Want to Close Application?")) {
					try {
						String username = "root";
						String password = "Cursor777";
						String dbFile = "prescriptionsystemfinal";
						String date=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
						System.out.println("date "+date);
						String executeCmd = "mysqldump -u"+username+" -p"+password+" "+dbFile+">"+dbFile+date+".sql";
						Process runtimeProcess;
						runtimeProcess = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", executeCmd });
						System.out.println(executeCmd);
						runtimeProcess = Runtime.getRuntime().exec(executeCmd);
						int processComplete = runtimeProcess.waitFor();
						System.out.println("processComplete"+processComplete);
						if (processComplete == 0) {
							System.out.println("Backup created successfully");

						} else {
							System.out.println("Could not create the backup");
						}
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error!!,"+e2);
					}
					stage.close();
				}else {
					e.consume();
				}
			});
			stage.show();
			new LoadedInfo();
			new CommonMethod();
			//stage.setWidth(value);
			MainFrameController mainController = loader.getController();

			new Thread(new Runnable() {

				public void run() {
					mainController.loadAllTabs();
				}
			}).start();
			mainContoller.stageCcSuggest.initOwner(stage);

			// LibraryAssistantUtil.setStageIcon(stage);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex);
			// Logger.getLogger(MainFxmlController.class.getName());
		}
	}
	 */

	void loadMain() {
		try {
			new SessionBeam(getTxtUserName());
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/prescription/main/LoadingSplashScreen.fxml"));

			Parent parent = loader.load();
			LoadingSplashScreenController splashController = loader.getController();
			
			Stage stage = new Stage();
			MainUtil.setStageIcon(stage);
			Scene scene = new Scene(parent);
			stage.centerOnScreen();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			txtUserName.getScene().getWindow().hide();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex);
			// Logger.getLogger(MainFxmlController.class.getName());
		}
	}

	public String getTxtUserName() {
		return txtUserName.getText().trim();
	}

	public void setTxtUserName(String txtUserName) {
		this.txtUserName.setText(txtUserName);
	}

	public String getTxtPassword() {
		return txtPassword.getText().trim();
	}

	public void setTxtPassword(String txtPassword) {
		this.txtPassword.setText(txtPassword);
	}


}
