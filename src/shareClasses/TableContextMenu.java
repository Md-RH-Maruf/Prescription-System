package shareClasses;

import impl.org.controlsfx.tools.rectangle.change.NewChangeStrategy;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;


public class TableContextMenu extends ContextMenu{

	MenuItem menuDelete = new MenuItem("Delete");
	MenuItem menuDeSelectAll = new MenuItem("Deselect all");
	MenuItem menuSelectAll = new MenuItem("SelectAll");
	Clipboard clipboard = Clipboard.getSystemClipboard();
	ClipboardContent cbContent = new ClipboardContent();

	TableView table;

	public TableContextMenu(TableView table,ObservableList list) {
		this.table = table;

		this.getItems().addAll(menuDelete,new SeparatorMenuItem(),menuSelectAll,menuDeSelectAll);

		this.table.setContextMenu(this);

		

		menuDelete.setOnAction(e ->{
			ObservableList selectItem = table.getSelectionModel().getSelectedItems();
			//for(int i=0;i<selectItem.size();i++) {
				list.removeAll(selectItem);
			//}
			table.getSelectionModel().clearSelection();
			table.setItems(list);
		});
		
		menuSelectAll.setOnAction(e ->{
			table.getSelectionModel().selectAll();

		});
		
		menuDeSelectAll.setOnAction(e ->{
			table.getSelectionModel().clearSelection();

		});
		
		
	}
	
	public void menuAdd(int index,MenuItem menuItem) {
		this.getItems().add(index,menuItem);
	}

}
