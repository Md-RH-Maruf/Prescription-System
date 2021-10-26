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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditGroupNameController implements Initializable{

	@FXML
	TextField txtNewGroupName;

	@FXML
	Label lbl;
	String currentGroupName;

	@FXML
	Button btnEdit;
	@FXML
	Button btnCancel;
	DatabaseHandler databaseHandler;
	String sql;
	
	NodeType nodeType;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		databaseHandler = DatabaseHandler.getInstance();
		// TODO Auto-generated method stub

	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		try {
			if(!getTxtNewGroupName().isEmpty()) {
				
				if(nodeType == NodeType.ADVISE_GROUP){
					if(!LoadedInfo.isAdviseGroupExist(getTxtNewGroupName())) {
						if(confirmationCheck("Edit This Group Name?")) {
							sql = "Update tbadvisegroup set groupname = '"+getTxtNewGroupName()+"' where groupName = '"+currentGroupName+"'";
							if(databaseHandler.execAction(sql)) {
								new Notification(Pos.TOP_CENTER, "Information graphic", "Group Name Edit Successfully..","Group Name Edit Successfully...");
								Stage dialog=(Stage)txtNewGroupName.getScene().getWindow();
								
								dialog.close();
								setTxtNewGroupName("");
								new Thread(new Runnable() {		
									@Override
									public void run() {
										// TODO Auto-generated method stub
										LoadedInfo.loadAdviseGroupInfo();
									}
								}).start();
							}
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Group Name All Ready Exist..","Please Enter New Group Name");
						txtNewGroupName.requestFocus();
					}
				}else if(nodeType == NodeType.INVESTIGATION_GROUP){
					if(!LoadedInfo.isInvestigationGroupExist(getTxtNewGroupName())) {
						if(confirmationCheck("Edit This Group Name?")) {
							sql = "Update tbInvestigationGroup set groupname = '"+getTxtNewGroupName()+"' where groupName = '"+currentGroupName+"'";
							if(databaseHandler.execAction(sql)) {
								new Notification(Pos.TOP_CENTER, "Information graphic", "Group Name Edit Successfully..","Group Name Edit Successfully...");
								Stage dialog=(Stage)txtNewGroupName.getScene().getWindow();				
								dialog.close();
								setTxtNewGroupName("");
								new Thread(new Runnable() {		
									@Override
									public void run() {
										// TODO Auto-generated method stub
										LoadedInfo.loadInvestigationGroupInfo();
									}
								}).start();
							}
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


	private String getMaxGroupId() {
		try {
			ResultSet rs=databaseHandler.execQuery("select ifnull(max(id),0)+1 as id from tbAdviseGroup");
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();

		}
		return "0";
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

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public String getTxtNewGroupName() {
		return txtNewGroupName.getText().toString().replace("'", "''").trim();
	}

	public void setTxtNewGroupName(String txtNewGroupName) {
		this.txtNewGroupName.setText(txtNewGroupName);
	}


	public String getLbl() {
		return lbl.getText();
	}

	public void setLbl(String lbl) {
		this.lbl.setText("Current Group Name : "+lbl);
	}

	public String getCurrentGroupName() {
		return currentGroupName;
	}

	public void setCurrentGroupName(String currentGroupName) {
		this.currentGroupName = currentGroupName;
	}

}
