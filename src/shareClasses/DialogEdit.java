package shareClasses;




import databaseHandler.DatabaseHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogEdit extends Dialog{


	private String id ;
	private String headId;



	private Button btnEdit = new Button("Edit");
	private Button btnCancel = new Button("Cancel");


	private Label lblNewItemName;


	private TextField txtNewItemName;
	private TextArea txtAreaNewItemName;


	private VBox vBox = new VBox();
	private HBox hBoxButton = new HBox();

	NodeType nodeType;
	DatabaseHandler databaseHandler;
	String sql;
	public DialogEdit(NodeType nodeType,String id,String headId){
		databaseHandler = DatabaseHandler.getInstance();
		this.id = id;
		this.headId = headId;
		this.nodeType = nodeType;

		txtNewItemName = new TextField();
		txtAreaNewItemName = new TextArea();

		lblNewItemName = new Label();





		txtNewItemName.setPrefWidth(300);
		txtNewItemName.setPrefHeight(30);


		lblNewItemName.setPrefHeight(28);
		lblNewItemName.setPrefWidth(150);

		txtAreaNewItemName.setWrapText(true);
		txtAreaNewItemName.setPrefWidth(430);
		txtAreaNewItemName.setPrefHeight(90);

		btnEdit.setPrefWidth(80);
		btnCancel.setPrefWidth(80);


		hBoxButton.setSpacing(10);


		hBoxButton.getChildren().addAll(btnEdit,btnCancel);


		hBoxButton.setAlignment(Pos.CENTER);
		vBox.setAlignment(Pos.CENTER_LEFT);
		
		vBox.setMargin(hBoxButton, new Insets(10,0,0,0));
		vBox.setMargin(lblNewItemName, new Insets(10,0,0,0));
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.getChildren().addAll(lblNewItemName,hBoxButton);

		switch (nodeType) {
		case SYSTEM:
			this.setTitle("Edit System Name....");

			lblNewItemName.setText("New System Name");
			vBox.getChildren().add(1,txtNewItemName);
			
			btnEdit.setOnAction(e->{
				setSystemEditAction();
			});
			break;
		case DISEASE:
			this.setTitle("Edit Disease Name....");

			lblNewItemName.setText("New Disease Name");
			vBox.getChildren().add(1,txtNewItemName);
			
			btnEdit.setOnAction(e->{
				setDiseaseEditAction();
			});
			break;
		case MEDICINE_GROUP:
			this.setTitle("Edit Medicine Group Name....");

			lblNewItemName.setText("New Medicine Group Name");
			vBox.getChildren().add(1,txtNewItemName);
			
			btnEdit.setOnAction(e->{
				setMedicineGroupEditAction();
			});
			break;
		case GENERIC:
			this.setTitle("Edit Generic Name....");

			lblNewItemName.setText("New Generic Name");
			vBox.getChildren().add(1,txtNewItemName);
			
			btnEdit.setOnAction(e->{
				setGenericEditAction();
			});
			break;
		case MEDICINE_BRAND:

			this.setTitle("Edit Medicine....");

			lblNewItemName.setText("New Medicine Name");
			vBox.getChildren().add(1,txtNewItemName);
			btnEdit.setOnAction(e->{
				setMedicineBrandEditAction();
			});
			break;
		case INVESTIGATION:
			this.setTitle("Edit Investigation....");

			lblNewItemName.setText("New Investigation Name");
			vBox.getChildren().add(1,txtNewItemName);
			btnEdit.setOnAction(e->{
				setInvestigationEditAction();
			});
			
			break;
		case CHEAP_COMPLAIN:
			this.setTitle("Edit Cheap Complain....");

			lblNewItemName.setText("New Cheap-Complain");
			vBox.getChildren().add(1,txtNewItemName);
			
			btnEdit.setOnAction(e->{
				setCheapComplainEditAction();
			});
			break;
		case ADVISE:
			this.setTitle("Edit Advise....");

			lblNewItemName.setText("New Advise");
			vBox.getChildren().add(1,txtAreaNewItemName);
			btnEdit.setOnAction(e->{
				setAdviseEditAction();
			});
			break;

		}


		btnCancel.setOnAction(e->{		
			this.close();
		});
		
		

		getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Node closeButton = this.getDialogPane().lookupButton(ButtonType.CANCEL);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);

		this.initModality(Modality.APPLICATION_MODAL);
		this.getDialogPane().setContent(vBox);


	}

	
	private void setSystemEditAction() {
		// TODO Auto-generated method stub

		if(!getTxtNewItemName().isEmpty()){
			if(!LoadedInfo.isSystemExist(headId+getTxtNewItemName())){


				sql = "update tbmedicinegroup set groupName='"+getTxtNewItemName()+"' where id = '"+id+"';";					

				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "System Edit Successfully...!","This System Edit successfully.....");
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadSystemInfo();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Child Name..!","This Disease Name already exist... \nPlease Enter New Child Name..");
				txtNewItemName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty System Name..!","Please Enter New System Name..");
			txtNewItemName.requestFocus();
		}



	}
	
	private void setDiseaseEditAction() {
		// TODO Auto-generated method stub

		if(!getTxtNewItemName().isEmpty()){
			if(!LoadedInfo.isDiseaseExist(headId+getTxtNewItemName())){
				sql = "update tbmedicinegroup set groupName='"+getTxtNewItemName()+"' where id = '"+id+"';";					

				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Disease Edit Successfully...!","This Disease Edit successfully.....");
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadDiseaseInfo();
							LoadedInfo.loadMapDiseaseListBySystemId();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Disease Name..!","This Disease Name already exist... \nPlease Enter New Disease Name..");
				txtNewItemName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Disease Name..!","Please Enter New Disease Name..");
			txtNewItemName.requestFocus();
		}

	}
	
	private void setMedicineGroupEditAction() {
		// TODO Auto-generated method stub

		if(!getTxtNewItemName().isEmpty()){
			if(!LoadedInfo.isMedicineGroupExist(headId+getTxtNewItemName())){
				sql = "update tbmedicinegroup set groupName='"+getTxtNewItemName()+"' where id = '"+id+"';";					

				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Medicine Group Edit Successfully...!","This Medicine Group Edit successfully.....");
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadMedicineGroupInfo();
							LoadedInfo.loadMapMedicineGroupListByDiseaseId();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Medicine Group Name..!","This Medicine Group Name already exist... \nPlease Enter New Disease Name..");
				txtNewItemName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Medicine Group Name..!","Please Enter New Medicine Group Name..");
			txtNewItemName.requestFocus();
		}

	}
	
	private void setGenericEditAction() {
		// TODO Auto-generated method stub

		if(!getTxtNewItemName().isEmpty()){
			if(!LoadedInfo.isGenericExist(headId+getTxtNewItemName())){
				sql = "update tbmedicinegroup set groupName='"+getTxtNewItemName()+"' where id = '"+id+"';";					

				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Generic Edit Successfully...!","This Generic Edit successfully.....");
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadGenericInfo();
							LoadedInfo.loadMapGenericListBySystemId();
							LoadedInfo.loadMapGenericListByDiseaseId();
							LoadedInfo.loadMapGenericListByMedicineGroupId();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name..!","This Generic Name already exist... \nPlease Enter New Disease Name..");
				txtNewItemName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Generic Name..!","Please Enter New Generic Name..");
			txtNewItemName.requestFocus();
		}

	}
	
	private void setMedicineBrandEditAction() {
		// TODO Auto-generated method stub

		if(!getTxtNewItemName().isEmpty()){
			if(!LoadedInfo.isMedicineBrandExist(headId+getTxtNewItemName())){
				sql = "update tbmedicinegroup set groupName='"+getTxtNewItemName()+"' where id = '"+id+"';";					

				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Medcine Brand Edit Successfully...!","This Medcine Brand Edit successfully.....");		
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadMedicineBrandInfo();
							LoadedInfo.loadMapMedicineBrandListByGenericId();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Medcine Brand Name..!","This Medcine Brand Name already exist... \nPlease Enter New Medcine Brand Name..");
				txtNewItemName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Medcine Brand Name..!","Please Enter New Medcine Brand Name..");
			txtNewItemName.requestFocus();
		}

	}
	
	private void setCheapComplainEditAction() {
		// TODO Auto-generated method stub

		if(!getTxtNewItemName().isEmpty()){
			if(!LoadedInfo.isCheapComplainExist(headId+getTxtNewItemName())){
				sql = "update tbcc set Name='"+getTxtNewItemName()+"' where id = '"+id+"' and type = '"+nodeType.getType()+"';";					

				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Cheap Complain Edit Successfully...!","This Cheap Complain Edit successfully.....");				
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadCheapComplainInfo();
							LoadedInfo.loadMapCheapComplainListBySystemId();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Cheap Complain Name..!","This Cheap Complain Name already exist... \nPlease Enter New Disease Name..");
				txtNewItemName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Cheap Complain Name..!","Please Enter New Cheap Complain Name..");
			txtNewItemName.requestFocus();
		}

	}
	
	private void setInvestigationEditAction() {
		// TODO Auto-generated method stub

		if(!getTxtNewItemName().isEmpty()){
			if(!LoadedInfo.isInvestigationExist(headId+getTxtNewItemName())){
				sql = "update tbTestGroup set groupname='"+getTxtNewItemName()+"' where id = '"+id+"' and type = '"+nodeType.getType()+"';";					

				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Investigation Edit Successfully...!","This Investigation Edit successfully.....");		
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadInvestigationInfo();
							LoadedInfo.loadMapInvestigationListByDiseaseId();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Investigation Name..!","This Investigation Name already exist... \nPlease Enter New Investigation Name..");
				txtNewItemName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Investigation Name..!","Please Enter New Investigation Name..");
			txtNewItemName.requestFocus();
		}

	}
	/*private void setMedicineAddAction() {
		// TODO Auto-generated method stub

		if(!getTxtAreaNewItemName().isEmpty()){
			if(!LoadedInfo.isMedicineBrandExist(headId+getTxtAreaNewItemName())){


				sql = "update tbmedicineinfo set medicineName='"+getTxtAreaNewItemName()+"' where id = '"+id+"';";					

				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Medicine Save Successfully...!","This Medicine Save successfully.....");
					this.close();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Child Name..!","This Disease Name already exist... \nPlease Enter New Child Name..");
				txtAreaNewItemName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Child Name..!","Please Enter New Child Name..");
			txtAreaNewItemName.requestFocus();
		}



	}*/

	private void setAdviseEditAction() {
		// TODO Auto-generated method stub
		
			if(!getTxtAreaNewItemName().isEmpty()){
				if(!LoadedInfo.isAdviseExist(headId+getTxtAreaNewItemName())){
					sql = "update tbadvise set advise='"+getTxtAreaNewItemName()+"',pId ='"+headId+"' where id="+id+"";
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Advise Edit Successfully...!","This Advise Edit successfully.....");
						
						this.close();
						new Thread(new Runnable() {		
							@Override
							public void run() {
								// TODO Auto-generated method stub
								LoadedInfo.loadAdviseInfo();
								LoadedInfo.loadMapAdviseListByDiseaseId();
								LoadedInfo.loadMapAdviseListBySystemId();
							}
						}).start();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Advise..!","This Advise already exist... \nPlease Enter New Advise..");
					txtAreaNewItemName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Advise Name..!","Please Enter New Advise..");
				txtAreaNewItemName.requestFocus();
			}
		

		
	}

	private void addCmp(int type) {
		// TODO Auto-generated method stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}


	public String getTxtNewItemName() {
		return txtNewItemName.getText().trim();
	}

	public void setTxtNewItemName(String txtNewItemName) {
		this.txtNewItemName.setText( txtNewItemName);
	}


	public String getTxtAreaNewItemName() {
		return txtAreaNewItemName.getText().trim();
	}

	public void setTxtAreaNewItemName(String txtAreaNewItemName) {
		this.txtAreaNewItemName.setText( txtAreaNewItemName);
	}

}
