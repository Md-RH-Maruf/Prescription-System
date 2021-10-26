package shareClasses;

import impl.org.controlsfx.tools.rectangle.change.NewChangeStrategy;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class CustomContextMenu extends ContextMenu{

	MenuItem menuClear = new MenuItem("Clear");
	MenuItem menuCut = new MenuItem("Cut");
	MenuItem menuCopy = new MenuItem("Copy");
	MenuItem menuPaste = new MenuItem("Paste");
	MenuItem menuDelete = new MenuItem("Delete");
	MenuItem menuSelectAll = new MenuItem("SelectAll");
	Clipboard clipboard = Clipboard.getSystemClipboard();
	ClipboardContent cbContent = new ClipboardContent();

	TextField txt;

	public CustomContextMenu(TextField txt) {
		this.txt = txt;

		this.getItems().addAll(menuClear,menuCut,menuCopy,menuPaste,menuDelete,new SeparatorMenuItem(),menuSelectAll);

		this.txt.setContextMenu(this);
		
		menuClear.setOnAction(e ->{
			this.txt.setText("");
		});


		menuCut.setOnAction(e ->{
			if(!getSelectedTxt().isEmpty()) {
				cbContent.putString(getSelectedTxt());
				clipboard.setContent(cbContent);
				setTxt("");
			}
		});

		menuCopy.setOnAction(e ->{
			if(!getSelectedTxt().isEmpty()) {
				cbContent.putString(getSelectedTxt());
				clipboard.setContent(cbContent);

			}
		});

		menuPaste.setOnAction(e ->{

			setTxt(clipboard.getString());

		});

		menuDelete.setOnAction(e ->{
			setTxt("");

		});
		
		menuSelectAll.setOnAction(e ->{
			this.txt.selectAll();

		});
		
		
	}

	public String getTxt() {
		return txt.getText().trim();
	}

	public String getSelectedTxt() {
		return txt.getSelectedText();
	}

	public void setTxt(String txt) {
		if(getSelectedTxt().isEmpty())
			if(getTxt().isEmpty())
				this.txt.setText(txt);
			else
				this.txt.setText(getTxt()+txt);
		else
			this.txt.replaceSelection(txt);
	}



}
