/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodtsk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class clearlistcart implements ActionListener{
    private JTable table;
    public clearlistcart(JTable tableinput){
        table = tableinput;
    }
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
    }
}
