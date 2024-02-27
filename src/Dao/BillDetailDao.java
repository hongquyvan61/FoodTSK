    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DB.ConnectDB;
import Model.Bill;
import Model.BillDetail;
import Model.Category;
import Model.ProductPayment;
import java.awt.List;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author tien
 */
public class BillDetailDao {
     private ConnectDB dB ;
    private Category category;
    private BillDetail billDetail;
    private Connection connection;
    public BillDetailDao() {        //dành cho table detailbill
        dB=new ConnectDB();
        this.connection=dB.GetConnect();
    }
    public boolean insertBillD(BillDetail billDetail) throws FileNotFoundException {
       // PreparedStatement statement = null;
        try {
            //InputStream is = new FileInputStream(new File(String.valueOf(product.getImage()) ));
            String sql = "insert into detailsbill(product,bill,quantity,price) values(?,?, ?,?)";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, billDetail.getProduct());
            statement.setInt(2, billDetail.getBill());
            statement.setInt(3, billDetail.getQuantity());
            statement.setFloat(4, billDetail.getPrice());
            statement.execute();
            return  true;
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return false;
    }
   public ArrayList<ProductPayment>getBillDetails(int idBill)throws Exception{
        ArrayList<ProductPayment> lst=new ArrayList<ProductPayment>();
        String sql="select product.name,detailsbill.quantity,detailsbill.price from detailsbill "
                + "INNER JOIN product ON detailsbill.product = product.id where bill="+idBill+"";
        ResultSet rs=connection.createStatement().executeQuery(sql);
           
    // user lh=null;
        while(rs.next()){
          
            ProductPayment productPayment=new ProductPayment(
                   rs.getString("name"),
                   rs.getInt("quantity"),
                   rs.getFloat("price")/ rs.getInt("quantity"), //price ở dòng này là đơn giá đã
                                                                //nhân số lượng, nên kết quả ở đây sẽ 
                                                                //là đơn giá của món ăn
                   rs.getFloat("price")         //price ở dòng này là đơn giá đã
                                                                //nhân số lượng
           );
           lst.add(productPayment);
        }
        //Blob imageBlob = list.get(i).getImage();
        return lst;
 }
   public ArrayList<String> getDate()throws Exception{
        ArrayList<String> list=new ArrayList<String>();
        String sql="select * from bill GROUP BY date";
        ResultSet rs=connection.createStatement().executeQuery(sql);
        String[] date;
        
    // user lh=null;
        while(rs.next()){
         
           list.add(rs.getString("date"));
        }
        //Blob imageBlob = list.get(i).getImage();
        return list;
 }
}
