/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filerenamer.ui;

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
 *
 * @author oleksandr.dan
 */

public class FolderDropHandler extends DropTargetAdapter {

    private final Consumer<File> onFolderDropped;

    public FolderDropHandler(JComponent targetComponent, Consumer<File> onFolderDropped) {
        this.onFolderDropped = onFolderDropped;
        new DropTarget(targetComponent, DnDConstants.ACTION_COPY, this, true);
    }

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
            
            // We simply don't accept drag and drop if the format is unexpected.
            
        }

        event.dropComplete(false);
    }
}