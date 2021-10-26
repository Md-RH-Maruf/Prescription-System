package shareClasses;

import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import prescription.main.MainFrameController.Medicine;
import shareClasses.LoadedInfo.MedicineItemInfo;



public class MedicineRowFactory {
	
	private final DataFormat SERIALIZED_MIME_TYPE = TableItemRowFactory.SERIALIZED_MIME_TYPE;
	public MedicineRowFactory(TableView<MedicineItemInfo> table) {
		
		
		table.setRowFactory(tv -> {
				 
	            TableRow<MedicineItemInfo> row = new TableRow<>();

	            row.setOnDragDetected(event -> {
	                if (! row.isEmpty()) {
	                    Integer index = row.getIndex();
	                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
	                    db.setDragView(row.snapshot(null, null));
	                    ClipboardContent cc = new ClipboardContent();
	                    cc.put(SERIALIZED_MIME_TYPE, index);
	                    db.setContent(cc);
	                    event.consume();
	                }
	            });

	           row.setOnDragOver(event -> {
	                Dragboard db = event.getDragboard();
	                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
	                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
	                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	                        event.consume();
	                    }
	                }
	            });

	            row.setOnDragDropped(event -> {
	                Dragboard db = event.getDragboard();
	                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
	                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
	                    MedicineItemInfo draggedPerson = table.getItems().remove(draggedIndex);

	                    int dropIndex ; 

	                    if (row.isEmpty()) {
	                        dropIndex = table.getItems().size() ;
	                    } else {
	                        dropIndex = row.getIndex();
	                    }

	                    table.getItems().add(dropIndex, draggedPerson);

	                    event.setDropCompleted(true);
	                    table.getSelectionModel().clearSelection();
	                    table.getSelectionModel().select(dropIndex);
	                    event.consume();
	                }
	            });

	            return row ;
	        });
	}
}
