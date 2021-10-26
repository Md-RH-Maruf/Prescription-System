package shareClasses;





import databaseHandler.DatabaseHandler;
import javafx.collections.ObservableList;
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
import shareClasses.LoadedInfo.TableItemInfo;

public class DialogAddChild extends Dialog{
	
	
	private String headId ;
	
	
	private Button btnAdd = new Button("Add");
	private Button btnCancel = new Button("Cancel");
	private Label lblParentsName ;
	private Label lblChildName ;
	
	private TextField txtParentsName = new TextField();
	private FxComboBox cmbChildName = new FxComboBox<>();
	private TextArea txtAreaChildName = new TextArea();
	
	private VBox vBox = new VBox();
	private HBox hBoxParent = new HBox();
	private HBox hBoxChild = new HBox();
	private HBox hBoxButton = new HBox();
	
	
	DatabaseHandler databaseHandler;
	NodeType nodeType;
	String sql;
	public DialogAddChild(NodeType nodeType,String headId){
		
		databaseHandler = DatabaseHandler.getInstance();
		this.headId = headId;
		this.nodeType = nodeType;
		
		
		
		switch(nodeType){
		case SYSTEM:
			break;
		case DISEASE:
			
			lblParentsName = new Label("System Name");
			lblParentsName.setPrefHeight(28);
			lblParentsName.setPrefWidth(80);
			
			txtParentsName.setEditable(false);
			txtParentsName.setPrefWidth(200);
			txtParentsName.setPrefHeight(30);
			
			
			lblChildName = new Label("Disease Name");
			lblChildName.setPrefHeight(28);
			lblChildName.setPrefWidth(80);
			
			cmbChildName.setPrefWidth(200);
			cmbChildName.setPrefHeight(30);
			
			this.setTitle("Add Disease....");
			hBoxChild.getChildren().addAll(lblChildName,cmbChildName);
			btnAdd.setOnAction(e->{
				addDiseaseAction();
			});
			break;
		case MEDICINE_GROUP:
			lblParentsName = new Label("Disease Name");
			lblParentsName.setPrefHeight(28);
			lblParentsName.setPrefWidth(80);
			
			txtParentsName.setEditable(false);
			txtParentsName.setPrefWidth(200);
			txtParentsName.setPrefHeight(30);
			
			
			lblChildName = new Label("Medicine-Group Name");
			lblChildName.setPrefHeight(28);
			lblChildName.setPrefWidth(80);
			
			cmbChildName.setPrefWidth(200);
			cmbChildName.setPrefHeight(30);
			
			this.setTitle("Add Medicine-Groip....");
			hBoxChild.getChildren().addAll(lblChildName,cmbChildName);
			btnAdd.setOnAction(e->{
				addMedicineGroupAction();
			});
			
			break;
		case GENERIC:
			
			lblParentsName = new Label("System Name");
			lblParentsName.setPrefHeight(28);
			lblParentsName.setPrefWidth(80);
			
			txtParentsName.setEditable(false);
			txtParentsName.setPrefWidth(200);
			txtParentsName.setPrefHeight(30);
			
			
			lblChildName = new Label("Generic Name");
			lblChildName.setPrefHeight(28);
			lblChildName.setPrefWidth(80);
			
			cmbChildName.setPrefWidth(200);
			cmbChildName.setPrefHeight(30);
			hBoxChild.getChildren().addAll(lblChildName,cmbChildName);
			this.setTitle("Add Generic....");
			
			btnAdd.setOnAction(e->{
				addGenericAction();
			});
			break;
		case MEDICINE_BRAND:
			break;
		case CHEAP_COMPLAIN:
			
			lblParentsName = new Label("System Name");
			lblParentsName.setPrefHeight(28);
			lblParentsName.setPrefWidth(100);
			
			txtParentsName.setEditable(false);
			txtParentsName.setPrefWidth(200);
			txtParentsName.setPrefHeight(30);
			
			
			lblChildName = new Label("Cheap Complain");
			lblChildName.setPrefHeight(28);
			lblChildName.setPrefWidth(100);
			
			cmbChildName.setPrefWidth(200);
			cmbChildName.setPrefHeight(30);
			
			this.setTitle("Add Cheap Complain....");
			hBoxChild.getChildren().addAll(lblChildName,cmbChildName);
			
			btnAdd.setOnAction(e->{
				addCheapComplainAction();
			});
			break;
		case INVESTIGATION:
			
			lblParentsName = new Label("Disease Name");
			lblParentsName.setPrefHeight(28);
			lblParentsName.setPrefWidth(100);
			
			txtParentsName.setEditable(false);
			txtParentsName.setPrefWidth(200);
			txtParentsName.setPrefHeight(30);
			
			
			lblChildName = new Label("Investigation");
			lblChildName.setPrefHeight(28);
			lblChildName.setPrefWidth(100);
			
			cmbChildName.setPrefWidth(200);
			cmbChildName.setPrefHeight(30);
			
			this.setTitle("Add Investigation....");
			hBoxChild.getChildren().addAll(lblChildName,cmbChildName);
			btnAdd.setOnAction(e->{
				addInvestigationAction();
			});
			break;
		case ADVISE:
			
			lblParentsName = new Label("Disease Name");
			lblParentsName.setPrefHeight(28);
			lblParentsName.setPrefWidth(80);
			
			txtParentsName.setEditable(false);
			txtParentsName.setPrefWidth(200);
			txtParentsName.setPrefHeight(30);
			
			
			lblChildName = new Label("Advise");
			lblChildName.setPrefHeight(28);
			lblChildName.setPrefWidth(80);
			
			txtAreaChildName.setPrefWidth(400);
			txtAreaChildName.setPrefHeight(60);
			
			this.setTitle("Add Advise....");
			
			hBoxChild.getChildren().addAll(lblChildName,txtAreaChildName);
			
			btnAdd.setOnAction(e->{
				addAdviseAction();
			});
			break;
		
		}
		btnAdd.setPrefWidth(80);
		btnCancel.setPrefWidth(80);

		vBox.setSpacing(5);
		hBoxParent.setSpacing(5);
		hBoxChild.setSpacing(5);
		hBoxButton.setSpacing(5);
		
		hBoxParent.getChildren().addAll(lblParentsName,txtParentsName);
		
		hBoxButton.getChildren().addAll(btnAdd,btnCancel);
		
		hBoxParent.setAlignment(Pos.CENTER_LEFT);
		hBoxChild.setAlignment(Pos.CENTER_LEFT);
		hBoxButton.setAlignment(Pos.CENTER);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(hBoxParent,hBoxChild,hBoxButton);
		
		getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Node closeButton = this.getDialogPane().lookupButton(ButtonType.CANCEL);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		this.initModality(Modality.APPLICATION_MODAL);
		this.getDialogPane().setContent(vBox);
		
		cmbChildName.requestFocus();
		
		
		btnCancel.setOnAction(e->{		
			this.close();
		});
	}

	

	private void addDiseaseAction() {
		// TODO Auto-generated method stub
		if(!getCmbChildName().isEmpty()){
			if(!LoadedInfo.isDiseaseExist(headId+getCmbChildName())){
				if(LoadedInfo.isDiseaseExist(getCmbChildName())){
					TableItemInfo temp = LoadedInfo.getDiseaseInfo(getCmbChildName());
					sql = "insert into tbMedicineGroup (sn,id,groupName,pId,type,entryTime,userId) values('"+temp.getSlNo()+"','"+temp.getItemId()+"','"+getCmbChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Disease Add Successfully...!","This Disease add successfully.....");
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
					int maxId = CommonMethod.getMaxMedicineGroupId();
					sql = "insert into tbMedicineGroup (sn,id,groupName,pId,type,entryTime,userId) values('"+maxId+"','"+maxId+"','"+getCmbChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Disease Add Successfully...!","This Disease add successfully.....");
						
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
				}
				
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Disease Name..!","This Disease Name already exist... \nPlease Enter New Disease Name..");
				cmbChildName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Disease Name..!","Please Enter New Diesease Name..");
			cmbChildName.requestFocus();
		}
	}
	
	private void addMedicineGroupAction() {
		// TODO Auto-generated method stub
		if(!getCmbChildName().isEmpty()){
			if(!LoadedInfo.isMedicineGroupExist(headId+getCmbChildName())){
				
				if(LoadedInfo.isMedicineGroupExist(getCmbChildName())){
					TableItemInfo temp = LoadedInfo.getMedicineGroupInfo(getCmbChildName());
					sql = "insert into tbMedicineGroup (sn,id,groupName,pId,type,entryTime,userId) values('"+temp.getSlNo()+"','"+temp.getItemId()+"','"+getCmbChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Disease Add Successfully...!","This Disease add successfully.....");
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
					int maxId = CommonMethod.getMaxMedicineGroupId();
					sql = "insert into tbMedicineGroup (sn,id,groupName,pId,type,entryTime,userId) values('"+maxId+"','"+maxId+"','"+getCmbChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Disease Add Successfully...!","This Disease add successfully.....");
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
				}
				
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Medicine-Group Name..!","This Medicine-Group Name already exist... \nPlease Enter New Medicine-Group Name..");
				cmbChildName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Medicine-Group Name..!","Please Enter New Medicine-Group Name..");
			cmbChildName.requestFocus();
		}
	}
	
	private void addCheapComplainAction() {
		// TODO Auto-generated method stub
		if(!getCmbChildName().isEmpty()){
			if(!LoadedInfo.isCheapComplainExist(headId+getCmbChildName())){
				int maxId = CommonMethod.getMaxCCId();
				sql = "insert into tbcc (slno,id,NAME,headId,type,entryTime,entryBy) values('"+maxId+"','"+maxId+"','"+getCmbChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
				
				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Chap Complain Add Successfully...!","This Cheap Compalin add successfully.....");
					//LoadedInfo.loadMapDiseaseListBySystemId();
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
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Cheap Complain Name..!","This Chap Complain Name already exist... \nPlease Enter New Chap Complain Name..");
				cmbChildName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Cheap Complain Name..!","Please Enter New Cheap Complain Name..");
			cmbChildName.requestFocus();
		}
	}
	
	private void addInvestigationAction() {
		// TODO Auto-generated method stub
		if(!getCmbChildName().isEmpty()){
			if(!LoadedInfo.isCheapComplainExist(headId+getCmbChildName())){
				int maxId = CommonMethod.getMaxInvestigationId();
				sql = "insert into tbtestgroup (Slno,id,GroupName,pid,type,entryTime,userId) values('"+maxId+"','"+maxId+"','"+getCmbChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"')";
				
				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Investigation Add Successfully...!","This Cheap Compalin add successfully.....");
					
					//LoadedInfo.loadMapDiseaseListBySystemId();
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadInvestigationList();
							LoadedInfo.loadMapInvestigationListByDiseaseId();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Cheap Complain Name..!","This Chap Complain Name already exist... \nPlease Enter New Chap Complain Name..");
				cmbChildName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Cheap Complain Name..!","Please Enter New Cheap Complain Name..");
			cmbChildName.requestFocus();
		}
	}
	
	private void addAdviseAction() {
		// TODO Auto-generated method stub
		if(!getTxtAreaChildName().isEmpty()){
			if(!LoadedInfo.isAdviseExist(headId+getTxtAreaChildName())){
				int maxId = CommonMethod.getMaxAdviseId();
				sql = "insert into tbadvise (id,slNo,advise,pid,type,entryTime,entryBy) values('"+maxId+"','"+maxId+"','"+getTxtAreaChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"')";
				
				if(databaseHandler.execAction(sql)){
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Advise Add Successfully...!","This Advise add successfully.....");				
					//LoadedInfo.loadMapDiseaseListBySystemId();
					this.close();
					new Thread(new Runnable() {		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoadedInfo.loadAdviseInfo();;
							LoadedInfo.loadMapAdviseListByDiseaseId();
							LoadedInfo.loadMapAdviseListBySystemId();
						}
					}).start();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Advise Name..!","This Advise Name already exist... \nPlease Enter New Advise Name..");
				txtAreaChildName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Advise Name..!","Please Enter New Advise Name..");
			txtAreaChildName.requestFocus();
		}
	}
	
	private void addGenericAction() {
		// TODO Auto-generated method stub
		if(!getCmbChildName().isEmpty()){
			if(!LoadedInfo.isGenericExist(headId+getCmbChildName())){
				if(LoadedInfo.isGenericExist(getCmbChildName())){
					TableItemInfo temp = LoadedInfo.getGenericInfo(getCmbChildName());
					sql = "insert into tbMedicineGroup (sn,id,groupName,pId,type,entryTime,userId) values('"+temp.getSlNo()+"','"+temp.getItemId()+"','"+getCmbChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Generic Add Successfully...!","This Generic add successfully.....");	
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
					int maxId = CommonMethod.getMaxMedicineGroupId();
					sql = "insert into tbMedicineGroup (sn,id,groupName,pId,type,entryTime,userId) values('"+maxId+"','"+maxId+"','"+getCmbChildName()+"','"+headId+"','"+nodeType.getType()+"',CURRENT_TIMESTAMP(),'"+SessionBeam.getUserId()+"');";
					
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Generic Add Successfully...!","This Generic add successfully.....");
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
				}
				
			}else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Duplicate Generic Name..!","This Generic Name already exist... \nPlease Enter New Generic Name..");
				cmbChildName.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty Generic Name..!","Please Enter New Generic Name..");
			cmbChildName.requestFocus();
		}
	}

	

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	

	public String getTxtParentsName() {
		return txtParentsName.getText().trim();
	}

	public void setTxtParentsName(String txtParentsName) {
		this.txtParentsName.setText( txtParentsName);
	}

	public String getCmbChildName() {
		if(cmbChildName.getValue() != null){
			return cmbChildName.getValue().toString();
		}else{
			return "";
		}
		
	}

	public void setCmbChildName(String cmbChildName) {
		this.cmbChildName.setValue(cmbChildName);;
	}

	public void setCmbChildNameData(ObservableList data){
		this.cmbChildName.data = data;
	}
	public String getTxtAreaChildName() {
		return txtAreaChildName.getText().trim();
	}

	public void settxtAreaChildName(String txtAreaChildName) {
		this.txtAreaChildName.setText( txtAreaChildName);
	}
	
	
	
}
