/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DB.ConnectDB;
import Model.Bill;
import Model.Category;
import Model.Product;
import Model.User;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author tien
 */
public class BillDao {
    private ConnectDB dB ;
    private Category category;
    private Connection connection;
    private Bill bill;
    public BillDao() {
        dB=new ConnectDB();
        this.connection=dB.GetConnect();
    }
    public boolean insertBill(Bill bill) throws FileNotFoundException {
       // PreparedStatement statement = null;
        try {
            //InputStream is = new FileInputStream(new File(String.valueOf(product.getImage()) ));
            String sql = "insert into bill(time,date,total) values(?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bill.getTime());
            statement.setString(2, bill.getDate());
            statement.setFloat(3, bill.getTotal());
            statement.execute();
            return  true;
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return false;
    }
     public int getBillMax() throws SQLException {
        String sql="select MAX(id) as maxId from bill";
        ResultSet rs=connection.createStatement().executeQuery(sql);
       // user lh=null;
        if(rs.next()){
           return rs.getInt("maxId");
        }
     return 0;
 }
       public ArrayList<Bill>getAllRows()throws Exception{
        ArrayList<Bill> lst=new ArrayList<Bill>();
        String sql="select * from bill";
        ResultSet rs=connection.createStatement().executeQuery(sql);
         
    // user lh=null;
        while(rs.next()){
          
            bill=new Bill(rs.getInt("id"),
                    rs.getString("time"),
                    rs.getString("date"),
                    rs.getFloat("total")
                    
                   );
            lst.add(bill);
        }
        //Blob imageBlob = list.get(i).getImage();
        return lst;
 }
       public ArrayList<Bill>getAllRowsDate(String date)throws Exception{
        ArrayList<Bill> lst=new ArrayList<Bill>();
        String sql="select * from bill WHERE  date=?";
         PreparedStatement stmt=connection.prepareStatement(sql);
           stmt.setString(1,date);
    // user lh=null;
        try(ResultSet rs=stmt.executeQuery();) {
            while (rs.next()) {
                bill=new Bill(rs.getInt("id"),rs.getString("time"),rs.getString("date"),rs.getFloat("total"));
                 lst.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
     return lst;
 }
}
