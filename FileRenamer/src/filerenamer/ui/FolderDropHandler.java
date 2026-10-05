/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.ui;

/**
 *
 * @author oleksandr.dan
 */

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JComponent;

/**
 * Handles drag-and-drop operations for selecting a folder.
 *
 * The handler accepts file-list drops and passes the first dropped
 * directory to the supplied callback.
 */

public class FolderDropHandler extends DropTargetAdapter {

    private final Consumer<File> onFolderDropped;
    
/**
 * Creates a drop handler for the specified component.
 *
 * @param targetComponent component that accepts folder drops
 * @param onFolderDropped callback invoked when a folder is dropped
 */

    public FolderDropHandler(JComponent targetComponent, Consumer<File> onFolderDropped) {
        this.onFolderDropped = onFolderDropped;
        new DropTarget(targetComponent, DnDConstants.ACTION_COPY, this, true);
    }

/**
 * Processes a dropped file list and notifies the callback when the first
 * dropped item is a directory.
 *
 * The drop is rejected when the data format is unsupported,
 * the list is empty, or the first item is not a directory.
 */
    
    @Override
    @SuppressWarnings("unchecked")
    public void drop(DropTargetDropEvent event) {
        event.acceptDrop(DnDConstants.ACTION_COPY);

        try {
            List<File> droppedFiles = (List<File>) event.getTransferable()
                    .getTransferData(DataFlavor.javaFileListFlavor);

            if (!droppedFiles.isEmpty() && droppedFiles.get(0).isDirectory()) {
                onFolderDropped.accept(droppedFiles.get(0));
                event.dropComplete(true);
                return;
            }
        } catch (UnsupportedFlavorException | IOException ex) {
        }

        event.dropComplete(false);
    }
}