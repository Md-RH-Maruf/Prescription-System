package shareClasses;





import databaseHandler.DatabaseHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogRename extends Dialog{
	
	private String headId ;
	private String itemId ;
	
	private Button btnRename = new Button("Rename");
	private Button btnCancel = new Button("Cancel");
	
	private Label lblChildName = new Label("Child Name:");
	
	
	private TextField txtChildName = new TextField();
	
	private VBox vBox = new VBox();

	private HBox hBoxChild = new HBox();
	private HBox hBoxButton = new HBox();
	
	DatabaseHandler databaseHandler;
	String sql;
	public DialogRename(String headId,String itemId){
		databaseHandler = DatabaseHandler.getInstance();
		this.headId = headId;
		this.itemId = itemId;
		addCmp();
		setCmpAction();
	}

	private void setCmpAction() {
		// TODO Auto-generated method stub
		btnRename.setOnAction(e->{
			if(!getTxtChildName().isEmpty()){
				if(!LoadedInfo.isDiseaseExist(headId+getTxtChildName())){
					
					sql = "update  tbDiseaseInfo set DiseaseName='"+getTxtChildName()+"' where id = '"+itemId+"';";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Edit Successfully...!","This Disease Edit successfully.....");
						this.close();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Child Name..!","This Disease Name already exist... \nPlease Enter New Child Name..");
					txtChildName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Child Name..!","Please Enter New Child Name..");
				txtChildName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}

	private void addCmp() {
		// TODO Auto-generated method stub
		
		
		lblChildName.setPrefHeight(28);
		lblChildName.setPrefWidth(80);
		
		txtChildName.setPrefWidth(200);
		txtChildName.setPrefHeight(30);
		
		btnRename.setPrefWidth(80);
		btnCancel.setPrefWidth(80);

		vBox.setSpacing(5);
		hBoxChild.setSpacing(5);
		hBoxButton.setSpacing(5);
		
		
		hBoxChild.getChildren().addAll(lblChildName,txtChildName);
		hBoxButton.getChildren().addAll(btnRename,btnCancel);
		
		
		hBoxChild.setAlignment(Pos.CENTER);
		hBoxButton.setAlignment(Pos.CENTER);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(hBoxChild,hBoxButton);
		
		getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Node closeButton = this.getDialogPane().lookupButton(ButtonType.CANCEL);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		this.setTitle("Create Tree Child (Disease Name)....");
		this.initModality(Modality.APPLICATION_MODAL);
		this.getDialogPane().setContent(vBox);
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getAncestorId() {
		return itemId;
	}

	public void setAncestorId(String ancestorId) {
		this.itemId = ancestorId;
	}

	public String getTxtChildName() {
		return txtChildName.getText().trim();
	}

	public void setTxtChildName(String txtChildName) {
		this.txtChildName.setText( txtChildName);
	}
	
	
	
}
