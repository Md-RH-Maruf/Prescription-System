package prescription.main;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.businessobjects.samples.SessionBeam;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import shareClasses.AlertMaker;
import shareClasses.CommonMethod;
import shareClasses.LoadedInfo;
import ui.util.MainUtil;

public class LoadingSplashScreenController implements Initializable{
	Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
	@FXML
	StackPane stackPane;

	@FXML
	ImageView cursorImage;
	
	@FXML
	ProgressBar progress;
	
	private Stage stageMain = new Stage();
	private double progressValue=0;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cursorImage.setImage(new Image(getClass().getResourceAsStream("/image/icon/cursor.png")));
		new CommonMethod();
		

		//splashController.setProgressValue(1.0/2);


		//splashController.setProgressValue(1.0/3);
		new LoadedInfo(this);
		
		new Thread(new Runnable() {

			public void run() {
				
				while(progressValue<1){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/prescription/main/MainFrame.fxml"));
						Parent parentMain = null;
						try {
							parentMain = loader2.load();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						MainFrameController mainController = loader2.getController();
						mainController.loadAllTabs();
						
						Scene sceneMain = new Scene(parentMain);

						//scene.setRoot(parent);

						MainUtil.setStageIcon(stageMain);
						stageMain.setTitle("Prescription System (Dr.Mohammad Moinddin Chowdhury)");
						stageMain.setScene(sceneMain);

						stageMain.centerOnScreen();
						stageMain.setResizable(true);

						//stageMain.setX(bounds.getMinX());
						//stageMain.setY(bounds.getMinY());
						//stageMain.setWidth(bounds.getWidth());
						//stageMain.setHeight(bounds.getHeight());
						stageMain.setMaximized(true);
						stageMain.setOnCloseRequest(e->{
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
								stageMain.close();
							}else {
								e.consume();
							}
						});

						setProgressValue(1.0/1.5);
						mainController.stageCcSuggest.initOwner(stageMain);
						setProgressValue(1.0/1.0);
						stageMain.show();
						progress.getScene().getWindow().hide();
						
				    }
				    
				});
				
				
			}
			
		}).start();
		
		
	}
	
	public double getProgressValue() {
		return progressValue;
	}
	public void setProgressValue(double progressValue) {
		if(this.progressValue<1){
			this.progressValue = progressValue;
			this.progress.setProgress(progressValue);
			
		}
		
	}
	
	public ProgressBar getProgressBar(){
		return progress;
	}
	
}
