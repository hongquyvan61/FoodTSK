/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;


import Dao.ProductDao;
import Model.Product;
import foodtsk.Home;
import java.awt.Container;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tien
 */
public class ClickAddCart implements ActionListener{

    private Product product;
    private JTable jTable;
    private JLabel lbtotal;
    public ClickAddCart(Product product,JTable jTable,JLabel lbtotal,int slton) {
        this.product = product;
        this.jTable=jTable;
        this.lbtotal= lbtotal;
    }
    
         //tùy event
        public void actionPerformed(ActionEvent e) {
            ProductDao pd = new ProductDao();
           int rows = jTable.getRowCount();
            DefaultTableModel model=new DefaultTableModel();
            model=(DefaultTableModel) this.jTable.getModel();
          //  model.getColumnName("id");
         
          Integer id = product.getId();
          
          
          if(rows==0) {
               model.addRow(new Object[]{
                  this.product.getId(),
                  this.product.getName(),   
                  this.product.getQuantity(),
                  this.product.getPrice(),
              });
          }
          else  {
              int t = 0;
              for (int row = 0; row < rows; row++) {
                 
                 if (jTable.getValueAt(row, 0)==id) {
                     if(pd.getsoluong(id) == (int) jTable.getValueAt(row, 2)){
                        JOptionPane.showMessageDialog(null, "Đã hết hàng!");
                        t++;
                        break;
                     }
                     if(pd.getsoluong(id) > (int) jTable.getValueAt(row, 2) ){
                        int quantity=(int) jTable.getValueAt(row, 2)+1;
                        
                        jTable.setValueAt(quantity, row, 2);
                        int price=(int) this.product.getPrice()*quantity;

                        jTable.setValueAt(price, row, 3);
                        t++;
                        break;
                     }
                     
                }
                 
            }
              if(t == 0) 
               {
                     model.addRow(new Object[]{
                        this.product.getId(),
                        this.product.getName(),
                        this.product.getQuantity(),
                        this.product.getPrice(),
                    });
                 }
          }
          
          
             int rowsNew = jTable.getRowCount();
            double total=0;
            for (int row = 0; row < rowsNew; row++) {
                total+=Double.parseDouble(String.valueOf(jTable.getValueAt(row, 3)) ) ;
                
            }
            DecimalFormat formatter = new DecimalFormat("###,###,###");


              lbtotal.setText(formatter.format(total)+" VNĐ");
            
              
             // lbtotal.setText(formatDecimal(Float.parseFloat(lbtotal.getText()) ));
            
          
        }
   
}
