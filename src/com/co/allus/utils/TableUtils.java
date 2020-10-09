/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.utils;

import com.co.allus.modelo.consultamovimientos.infoTablaConsMov;
import java.util.Iterator;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class TableUtils {

    /**
     * Install the keyboard handler: + CTRL + C = copy to clipboard
     *
     * @param table
     */
    public static void installCopyPasteHandler(TableView<?> table) {

        // install copy/paste keyboard handler
        table.setOnKeyPressed(new TableKeyEventHandler());

    }

    /**
     * Copy/Paste keyboard event handler. The handler uses the keyEvent's source
     * for the clipboard data. The source must be of type TableView.
     */
    public static class TableKeyEventHandler implements EventHandler<KeyEvent> {

        KeyCodeCombination copyKeyCodeCompination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);

        @Override
        public void handle(final KeyEvent keyEvent) {

            if (copyKeyCodeCompination.match(keyEvent)) {

                if (keyEvent.getSource() instanceof TableView) {

                    // copy to clipboard
                    copySelectionToClipboardBk((TableView<?>) keyEvent.getSource());


                    // event is handled, consume it
                    keyEvent.consume();

                }

            }

        }
    }

    /**
     * Get table selection and copy it to the clipboard.
     *
     * @param table
     */
    public static void copySelectionToClipboardBk(TableView<?> table) {

        StringBuilder clipboardString = new StringBuilder();

        ObservableList<TablePosition> positionList = table.getSelectionModel().getSelectedCells();

        int prevRow = -1;

        for (TablePosition position : positionList) {

            int row = position.getRow();
            int col = position.getColumn();

            Object cell = (Object) table.getColumns().get(col).getCellData(row);

            // null-check: provide empty string for nulls
            if (cell == null) {
                cell = "";
            }

            // determine whether we advance in a row (tab) or a column
            // (newline).
            if (prevRow == row) {

                clipboardString.append('\t');

            } else if (prevRow != -1) {

                clipboardString.append('\n');

            }

            // create string from cell
            String text = cell.toString();

            // add new item to clipboard
            clipboardString.append(text);

            // remember previous
            prevRow = row;
        }

        // create clipboard content
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(clipboardString.toString());

        // set clipboard content
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    /**
     * Get table selection and copy it to the clipboard.
     *
     * @param table
     */
    public static void copySelectionToClipboard(final List<infoTablaConsMov> dataTabla) {

        StringBuilder clipboardString = new StringBuilder();


        clipboardString.append("Origen Trx");
        clipboardString.append('\t');
        clipboardString.append("Cod Trx");
        clipboardString.append('\t');
        clipboardString.append("Descripcion");
        clipboardString.append('\t');
        clipboardString.append("Fecha Trx  ");
        clipboardString.append('\t');
        clipboardString.append("Valor Transaccion");
        clipboardString.append('\t');
        clipboardString.append("Oficina Origen");
        clipboardString.append('\t');
        clipboardString.append("Numero Cheque");
        clipboardString.append('\n');

        for (Iterator<infoTablaConsMov> it = dataTabla.iterator(); it.hasNext();) {
            infoTablaConsMov movimiento = it.next();
            // add new item to clipboard
            clipboardString.append(movimiento.getOrigen_trx());
            clipboardString.append('\t');
            clipboardString.append(movimiento.getCod_trx());
            clipboardString.append('\t');
            clipboardString.append(movimiento.getDescripcion());
            clipboardString.append('\t');
            clipboardString.append(movimiento.getFecha_trx());
            clipboardString.append('\t');
            clipboardString.append(movimiento.getValor_transaccion());
            clipboardString.append('\t');
            clipboardString.append(movimiento.getOfi_origen());
            clipboardString.append('\t');
            clipboardString.append(movimiento.getNum_cheque());
            clipboardString.append('\n');


        }

        // create clipboard content
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(clipboardString.toString());

        // set clipboard content
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }
}