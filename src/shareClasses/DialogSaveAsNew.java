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

public class DialogSaveAsNew extends Dialog{
	
	
	
	private String headId ;
	private String companyName ;
	
	
	private Button btnSave = new Button("Save");
	private Button btnCancel = new Button("Cancel");
	
	private Label lblParentsName;
	private Label lblNewItemName;
	
	private TextField txtParentsName;
	private TextArea txtAreaNewName;
	private TextField txtNewName;
	
	private VBox vBox = new VBox();
	private HBox hBoxButton = new HBox();
	
	DatabaseHandler databaseHandler;
	String sql;
	NodeType nodeType;
	public DialogSaveAsNew(NodeType nodeType,String headId){
		databaseHandler = DatabaseHandler.getInstance();
		this.headId = headId;
		this.nodeType = nodeType;
		
		txtParentsName = new TextField();
		txtNewName = new TextField();
		txtAreaNewName = new TextArea();
		lblParentsName = new Label("Parents Disease Name");
		lblNewItemName = new Label();
		
		
		
		lblParentsName.setPrefHeight(28);
		lblParentsName.setPrefWidth(200);
		
		txtParentsName.setEditable(false);
		txtParentsName.setPrefWidth(200);
		txtParentsName.setPrefHeight(30);
		
		
		lblNewItemName.setPrefHeight(28);
		lblNewItemName.setPrefWidth(200);
		
		txtNewName.setPrefWidth(430);
		txtNewName.setPrefHeight(30);
		
		txtAreaNewName.setPrefWidth(430);
		txtAreaNewName.setPrefHeight(90);
		
		btnSave.setPrefWidth(80);
		btnCancel.setPrefWidth(80);

		
		hBoxButton.setSpacing(10);
		
		
		hBoxButton.getChildren().addAll(btnSave,btnCancel);
		
		
		hBoxButton.setAlignment(Pos.CENTER);
		vBox.setAlignment(Pos.CENTER_LEFT);
		
		vBox.setMargin(hBoxButton, new Insets(10,0,0,0));
		vBox.setMargin(lblNewItemName, new Insets(10,0,0,0));
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.getChildren().addAll(lblParentsName,txtParentsName,lblNewItemName,hBoxButton);
		
		
		
		switch(nodeType){
		case SYSTEM:
			this.setTitle("Create New System....");
			lblNewItemName.setText("New System Name");
			vBox.getChildren().add(3,txtNewName);
			
			setSystemSaveCmpAction();
			break;
		case DISEASE:
			this.setTitle("Create New Disease....");
			lblNewItemName.setText("New Disease Name");
			vBox.getChildren().add(3,txtNewName);
			setDiseaseSaveCmpAction();
			break;
		case MEDICINE_GROUP:
			this.setTitle("Create New Medicine Group....");
			lblNewItemName.setText("New Medicine Group Name");
			vBox.getChildren().add(3,txtNewName);
			setMedicineGroupSaveCmpAction();
			
			break;
		case GENERIC:
			this.setTitle("Create New Generic....");
			lblNewItemName.setText("New Generic Name");
			vBox.getChildren().add(3,txtNewName);
			setGenericSaveCmpAction();
			break;
		case MEDICINE_BRAND:
			this.setTitle("Create New Medicine Brand....");
			lblNewItemName.setText("New Medicine Brand");
			vBox.getChildren().add(3,txtNewName);
			setMedicineBrandSaveCmpAction();
			break;
		case CHEAP_COMPLAIN:
			this.setTitle("Create New Cheap-Complain....");
			lblNewItemName.setText("New Cheap-Complain Name");
			vBox.getChildren().add(3,txtNewName);
			setCheapComplainSaveCmpAction();
			break;
		case INVESTIGATION:
			this.setTitle("Create New Investigation....");
			lblNewItemName.setText("New Investigation Name");
			vBox.getChildren().add(3,txtNewName);
			setInvestigationSaveCmpAction();
			break;
		case ADVISE:
			this.setTitle("Create New Advise....");
			lblNewItemName.setText("New Advise");
			vBox.getChildren().add(3,txtAreaNewName);
			setAdviseAddCmpAction();
			break;
		}
		
		getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Node closeButton = this.getDialogPane().lookupButton(ButtonType.CANCEL);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		
		this.initModality(Modality.APPLICATION_MODAL);
		this.getDialogPane().setContent(vBox);
		
	}

	private void setSystemSaveCmpAction() {
		// TODO Auto-generated method stub
		btnSave.setOnAction(e->{
			if(!getTxtNewName().isEmpty()){
				if(!LoadedInfo.isSystemExist(headId+getTxtNewName())){
					int maxId = CommonMethod.getMaxMedicineId();
					
					sql = "insert into tbmedicinegroup (id,GroupName,pId,gpID,Type,sn,entryTime,userId) values('"+maxId+"','"+getTxtNewName()+"','"+headId+"','0','"+nodeType.getType()+"','"+maxId+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						
						new Notification(Pos.TOP_CENTER, "Warning graphic", "System Save Successfully...!","This System Save successfully.....");
						
						this.close();
						new Thread(new Runnable() {		
							@Override
							public void run() {
								// TODO Auto-generated method stub
								LoadedInfo.loadSystemInfo();
								NewCustomContextMenu.mainController.tableSystem.setItems(LoadedInfo.getSystemList());
							}
						}).start();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate System Name..!","This System Name already exist... \nPlease Enter New System Name..");
					txtNewName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty System Name..!","Please Enter New System Name..");
				txtNewName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}

	private void setDiseaseSaveCmpAction() {
		// TODO Auto-generated method stub
		btnSave.setOnAction(e->{
			if(!getTxtNewName().isEmpty()){
				if(!LoadedInfo.isDiseaseExist(headId+getTxtNewName())){
					int maxId = CommonMethod.getMaxMedicineId();
					
					sql = "insert into tbmedicinegroup (id,GroupName,pId,gpID,Type,sn,entryTime,userId) values('"+maxId+"','"+getTxtNewName()+"','"+headId+"','0','"+nodeType.getType()+"','"+maxId+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Disease Save Successfully...!","This Disease Save successfully.....");
						
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
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Child Name..!","This Disease Name already exist... \nPlease Enter New Child Name..");
					txtAreaNewName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Child Name..!","Please Enter New Child Name..");
				txtAreaNewName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}
	
	private void setInvestigationSaveCmpAction() {
		// TODO Auto-generated method stub
		btnSave.setOnAction(e->{
			if(!getTxtNewName().isEmpty()){
				if(!LoadedInfo.isInvestigationExist(headId+getTxtNewName())){
					int maxId = CommonMethod.getMaxInvestigationId();
					
					sql = "insert into tbtestgroup (Slno,id,GroupName,pid,TYPE,entryTime,userId) values('"+maxId+"','"+maxId+"','"+getTxtNewName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"')";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Investigation Save Successfully...!","This Investigation Save successfully.....");
						
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
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Investigation Name..!","This Investigation already exist... \nPlease Enter New Investigation Name..");
					txtNewName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Investigation Name..!","Please Enter New Investigation Name..");
				txtNewName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}
	
	private void setMedicineGroupSaveCmpAction() {
		// TODO Auto-generated method stub
		btnSave.setOnAction(e->{
			if(!getTxtNewName().isEmpty()){
				if(!LoadedInfo.isMedicineGroupExist(headId+getTxtNewName())){
					int maxId = CommonMethod.getMaxMedicineId();
					
					sql = "insert into tbmedicinegroup (id,GroupName,pId,gpID,Type,sn,entryTime,userId) values('"+maxId+"','"+getTxtNewName()+"','"+headId+"','0','"+nodeType.getType()+"','"+maxId+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Medicine Group Save Successfully...!","This Medicine Group Save successfully.....");
						
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
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Medicine Group Name..!","This Disease Name already exist... \nPlease Enter New Child Name..");
					txtNewName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Medicine Group Name..!","Please Enter New Medicine Group Name..");
				txtNewName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}
	
	private void setGenericSaveCmpAction() {
		// TODO Auto-generated method stub
		btnSave.setOnAction(e->{
			if(!getTxtNewName().isEmpty()){
				if(!LoadedInfo.isGenericExist(headId+getTxtNewName())){
					int maxId = CommonMethod.getMaxMedicineId();
					
					sql = "insert into tbmedicinegroup (id,GroupName,pId,gpID,Type,sn,entryTime,userId) values('"+maxId+"','"+getTxtNewName()+"','"+headId+"','0','"+nodeType.getType()+"','"+maxId+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Generic Save Successfully...!","This Generic Save successfully.....");
						
						this.close();
						new Thread(new Runnable() {		
							@Override
							public void run() {
								// TODO Auto-generated method stub
								LoadedInfo.loadGenericInfo();
								LoadedInfo.loadMapGenericListByMedicineGroupId();
							}
						}).start();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name..!","This Generic already exist... \nPlease Enter New Generic Name..");
					txtAreaNewName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Generic Name..!","Please Enter New Generic Name..");
				txtNewName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}
	
	private void setMedicineBrandSaveCmpAction() {
		// TODO Auto-generated method stub
		btnSave.setOnAction(e->{
			if(!getTxtNewName().isEmpty()){
				if(!LoadedInfo.isMedicineBrandExist(headId+getTxtNewName())){
					int maxId = CommonMethod.getMaxMedicineId();
					
					sql = "insert into tbmedicinegroup (id,GroupName,pId,gpID,Type,sn,entryTime,userId) values('"+maxId+"','"+getTxtNewName()+"','"+headId+"','0','"+nodeType.getType()+"','"+maxId+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						sql="insert into tbmedicinecompany (id,name) values('"+maxId+"','"+getCompanyName()+"')";
						
						if(databaseHandler.execAction(sql)){
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Medicine Brand Successfully...!","This Medicine Brand Save successfully.....");
							
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
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Medicine Brand Name..!","This Medicine Brand already exist... \nPlease Enter New Medicine Brand Name..");
					txtAreaNewName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Medicine Brand Name..!","Please Enter New Medicine Brand Name..");
				txtNewName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}
	
	private void setCheapComplainSaveCmpAction() {
		// TODO Auto-generated method stub
		btnSave.setOnAction(e->{
			if(!getTxtNewName().isEmpty()){
				if(!LoadedInfo.isCheapComplainExist(headId+getTxtNewName())){
					int maxId = CommonMethod.getMaxCheapComplainId();
					
					sql = "insert into tbcc (slno,id,NAME,headId,type,entryTime,entryBy) values('"+maxId+"','"+maxId+"','"+getTxtNewName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Cheap-Complain Save Successfully...!","This Cheap-Complain Save successfully.....");
						
						this.close();
						new Thread(new Runnable() {		
							@Override
							public void run() {
								// TODO Auto-generated method stub
								LoadedInfo.loadCheapComplainList();
								LoadedInfo.loadMapCheapComplainListBySystemId();
							}
						}).start();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Child Name..!","This Disease Name already exist... \nPlease Enter New Child Name..");
					txtNewName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Child Name..!","Please Enter New Child Name..");
				txtNewName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}
	
	private void setAdviseAddCmpAction() {
		// TODO Auto-generated method stub
		btnSave.setOnAction(e->{
			if(!getTxtAreaNewName().isEmpty()){
				if(!LoadedInfo.isAdviseExist(headId+getTxtAreaNewName())){
					int maxId = CommonMethod.getMaxAdviseId();
					
					
					sql="insert into tbadvise (slno,id,advise,pid,type,entryTime,entryBy) values("+maxId+",'"+maxId+"','"+getTxtAreaNewName()+"','"+headId+"','"+nodeType.getType()+"',NOW(),'"+SessionBeam.getUserId()+"');";
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Advise Save Successfully...!","This Advise save successfully.....");
						
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
					txtAreaNewName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Advise..!","Please Enter New Advise..");
				txtAreaNewName.requestFocus();
			}
		});
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}



	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTxtParentsName() {
		return txtParentsName.getText().trim();
	}

	public void setTxtParentsName(String txtParentsName) {
		this.txtParentsName.setText( txtParentsName);
	}

	public String getTxtNewName() {
		return txtNewName.getText().trim();
	}

	public void setTxtNewName(String txtNewName) {
		this.txtNewName.setText( txtNewName);
	}
	
	public String getTxtAreaNewName() {
		return txtAreaNewName.getText().trim();
	}

	public void setTxtAreaNewName(String txtNewName) {
		this.txtAreaNewName.setText( txtNewName);
	}
	
}
