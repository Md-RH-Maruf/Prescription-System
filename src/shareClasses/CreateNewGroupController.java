package shareClasses;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import shareClasses.LoadedInfo.TableItemInfo;

public class CreateNewGroupController implements Initializable{

	@FXML
	TextField txtNewGroupName;
	
	@FXML
	Button btnOk;
	@FXML
	Button btnCancel;
	DatabaseHandler databaseHandler;
	String sql;

	ObservableList<TableItemInfo> itemList;
	
	NodeType nodeType;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		databaseHandler = DatabaseHandler.getInstance();
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void btnOkAction(ActionEvent event) {
		try {
			if(!getTxtNewGroupName().isEmpty()) {
				if(nodeType == NodeType.ADVISE_GROUP){
					if(!LoadedInfo.isAdviseGroupExist(getTxtNewGroupName())) {
						if(confirmationCheck("Save as new Advise Group?")) {
							int groupId = CommonMethod.getMaxAdviseGroupId();
							String groupName = getTxtNewGroupName();
							for(int i= 0;i< itemList.size();i++) {
								sql = "INSERT INTO tbadvisegroup (slno,id,groupname,adviseId,TYPE,headId,entryTime,userId) VALUES ('"+i+"','"+groupId+"','"+groupName+"','"+itemList.get(i).getItemId()+"','1','0',NOW(),'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);
							}
							new Notification(Pos.TOP_CENTER, "Information graphic", "Group Create Successfully..","New Group Create Successfully...");
							Stage dialog=(Stage)txtNewGroupName.getScene().getWindow();
							LoadedInfo.loadAdviseGroupInfo();
							LoadedInfo.loadMapAdviseListByGroupId();
							dialog.close();
							setTxtNewGroupName("");
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Group Name All Ready Exist..","Please Enter New Group Name");
						txtNewGroupName.requestFocus();
					}
				}else if(nodeType == NodeType.INVESTIGATION_GROUP){
					if(!LoadedInfo.isInvestigationGroupExist(getTxtNewGroupName())) {
						if(confirmationCheck("Save as new Investigation Group?")) {
							int groupId = CommonMethod.getMaxInvestigationGroupId();
							String groupName = getTxtNewGroupName();
							for(int i= 0;i< itemList.size();i++) {
								sql = "INSERT INTO tbInvestigationgroup (slno,id,groupname,investigationId,TYPE,headId,entryTime,userId) VALUES ('"+i+"','"+groupId+"','"+groupName+"','"+itemList.get(i).getItemId()+"','1','0',NOW(),'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);
							}
							new Notification(Pos.TOP_CENTER, "Information graphic", "Group Create Successfully..","New Group Create Successfully...");
							Stage dialog=(Stage)txtNewGroupName.getScene().getWindow();
							LoadedInfo.loadInvestigationGroupInfo();
							LoadedInfo.loadMapInvestigationListByGroupId();
							dialog.close();
							setTxtNewGroupName("");
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Group Name All Ready Exist..","Please Enter New Group Name");
						txtNewGroupName.requestFocus();
					}
				}
				
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Enter New Group Name..","Please Enter New Group Name");
				txtNewGroupName.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	
	/*private String getMaxGroupId() {
		try {
			ResultSet rs=databaseHandler.execQuery("select ifnull(max(id),0)+1 as id from tbAdviseGroup");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();

		}
		return "0";
	}*/

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	@FXML
	private void btnCancelAction(ActionEvent event) {
		setTxtNewGroupName("");
		Stage dialog=(Stage)txtNewGroupName.getScene().getWindow();
		dialog.close();
	}
	
	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub
		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message);
	}

	/*private boolean isGroupNameExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select groupName from tbAdviseGroup where groupname = '"+getTxtNewGroupName()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}*/

	public String getTxtNewGroupName() {
		return txtNewGroupName.getText().toString().replace("'", "''").trim();
	}

	public void setTxtNewGroupName(String txtNewGroupName) {
		this.txtNewGroupName.setText(txtNewGroupName);
	}

	public ObservableList<TableItemInfo> getItemList() {
		return itemList;
	}

	public void setItemList(ObservableList<TableItemInfo> itemList) {
		this.itemList = itemList;
	}
	
	

}
