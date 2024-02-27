/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DB.ConnectDB;
import EndCode.Md5;
import Model.Product;
import Model.User;
import com.mysql.cj.jdbc.Blob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author tien
 */
public class ProductDao {
   private ConnectDB dB ;
    private Product product;
    private Connection connection;
    public ProductDao() {
        dB=new ConnectDB();
        this.connection=dB.GetConnect();
    }
    
     public ArrayList<Product>getAllRows(int id)throws Exception{
        ArrayList<Product> lst=new ArrayList<Product>();
        String sql="select * from product where CategoryID="+id+"";
        ResultSet rs=connection.createStatement().executeQuery(sql);
           
    // user lh=null;
        while(rs.next()){
          
            product=new Product(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("CategoryID"),
                    rs.getInt("quantity"),
                    rs.getInt("price")
                   );
            
            lst.add(product);
        }
        //Blob imageBlob = list.get(i).getImage();
        return lst;
 }
 public boolean insertProduct(Product product) throws FileNotFoundException {
       // PreparedStatement statement = null;
        try {
            //InputStream is = new FileInputStream(new File(String.valueOf(product.getImage()) ));
            String sql = "insert into product(name, CategoryID, quantity, price) values(?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getCategoryID());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, product.getPrice());
            statement.execute();
            return  true;
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return false;
    }
 public ArrayList<Product>getAllRows()throws Exception{
        ArrayList<Product> lst=new ArrayList<Product>();
        String sql="select * from product";
        ResultSet rs=connection.createStatement().executeQuery(sql);
       // user lh=null;
        while(rs.next()){
            Product  product=new Product(rs.getInt("id"),rs.getString("name"),rs.getInt("CategoryID"),rs.getInt("quantity"),rs.getInt("price"));
            lst.add(product);
        }
     return lst;
 }
   public ArrayList<Product> searchProduct(String key) throws SQLException{
         ArrayList<Product> lst=new ArrayList<Product>();
        //String nameString="%"+key+"%";
        String sql="select * from product WHERE name LIKE ? or id like ? or quantity like ? or price like ?";
         PreparedStatement stmt=connection.prepareStatement(sql);
           stmt.setString(1,"%"+key+"%");
           stmt.setString(2,key);
           stmt.setString(3,key);
           stmt.setString(4,key);
              
        try(ResultSet rs=stmt.executeQuery();) {
            while (rs.next()) {
                product=new Product(rs.getInt("id"),rs.getString("name"),rs.getInt("CategoryID"),rs.getInt("quantity"),rs.getInt("price"));
                 lst.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
     return lst;
    }
   public Product getbyId(int id){
        String sql="select * from product where id="+id+"";
        try {
             ResultSet rs=connection.createStatement().executeQuery(sql); 
             while(rs.next()){
                product=new Product(rs.getInt("id"),rs.getString("name"),rs.getInt("CategoryID"),rs.getInt("quantity"),rs.getInt("price"));
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
    
    // user lh=null;
    
     return product;
 }
      public int updateProduct(Product product){
            
         
     String sql = "UPDATE product SET name='"+product.getName()+"', CategoryID="+product.getCategoryID()+""
             + ", quantity="+product.getQuantity()+", price='"+product.getPrice()+"' "
             + "WHERE id="+product.getId()+"";
     try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            //thực thi
             int rowAffected = pstmt.executeUpdate();
             if (rowAffected>0) {
                 return 1;
             
         }
     } catch (Exception ex) {
         System.out.println(ex.getMessage());
     }
     return 0;
 }
      public int deleteProduct(int id){
        String sql = "DELETE FROM product WHERE id="+id+";";
        try(PreparedStatement pstmt = connection.prepareStatement(sql,
                          Statement.RETURN_GENERATED_KEYS);) {
            //thực thi
             int rowAffected = pstmt.executeUpdate();
             if (rowAffected>0) {
                 return 1;
             
         }
     } catch (Exception ex) {
         System.out.println(ex.getMessage());
     }
     return 0;
 }
    public boolean checkbyId(int id){
        String sql="select * from product where id="+id+"";
        try {
             ResultSet rs=connection.createStatement().executeQuery(sql);
             if(rs.next()){
                 return true;
             }
             return false;
        } catch (Exception e) {
            
             e.printStackTrace();
             
        }
    
    // user lh=null;
    
     return false;
 }
    public int getsoluong(int id){
        int soluong = 0 ;
        String sql = "select quantity from product where id=?";
        try{
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                soluong = rs.getInt(1);
               
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return soluong;
    }
     public int nhapkho(int sl,int id,int price){
         String sql = "update product set quantity=?, price='"+ price +"' where id=?";
         try {
             PreparedStatement stmt=connection.prepareStatement(sql);
             stmt.setInt(1, sl + getsoluong(id));
            
             stmt.setInt(2, id);
             if(stmt.executeUpdate() > 0){
                 return 1;
             }
             return 0;
        } catch (Exception e) {
            
             e.printStackTrace();
             
        }
         return 0;
     }
     public int ghinhapkho(int slthem, int id,int price){
         String sql = "insert into nhapkho(product,quantity,price,date)"
                 + " values(?,?,?,?)";
         try {
             PreparedStatement stmt=connection.prepareStatement(sql);
             stmt.setInt(1, id);
            
             stmt.setInt(2, slthem);
             stmt.setInt(3, price);
             stmt.setString(4, String.valueOf(java.time.LocalDate.now()));
             if(stmt.executeUpdate() > 0){
                 return 1;
             }
             return 0;
        } catch (Exception e) {
            
             e.printStackTrace();
           
        }
         return 0;
     }
     public void giamsoluong(int sl,int id){
         String sql = "update product set quantity=? where id=?"; 
         try {
             PreparedStatement stmt=connection.prepareStatement(sql);
             stmt.setInt(1, getsoluong(id) - sl);
            
             stmt.setInt(2, id);
             stmt.executeUpdate();
             
        } catch (Exception e) {
            
             e.printStackTrace();
           
        }
     }
     
     public ArrayList<Product> getlastproduct(){
         ArrayList<Product> result = new ArrayList<Product>();
         String sql = "select * from product order by id desc";
         try{
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();
             while(rs.next()){
                 Product p = new Product();
                 p.setId(rs.getInt(1));
                 p.setName(rs.getString(2));
                 p.setCategoryID(rs.getInt(3));
                 p.setQuantity(rs.getInt(4));
                 p.setPrice(Integer.parseInt(rs.getString(5)));
                 result.add(p);
                 break;
             }
             
         }
         catch(Exception e){
             e.printStackTrace();
         }
         return result;
     }
     public int insertnhapkho(){
         
         ArrayList<Product> lastpd = new ArrayList<Product>();
         lastpd = getlastproduct();
         Product p = lastpd.get(0);
         String sql = "insert into nhapkho(product,quantity,price,date)"
                 + " values(?,?,?,?)";
         try {
             PreparedStatement stmt=connection.prepareStatement(sql);
             stmt.setInt(1, p.getId());
            
             stmt.setInt(2, p.getQuantity());
             stmt.setInt(3, p.getPrice());
             stmt.setString(4, String.valueOf(java.time.LocalDate.now()));
             stmt.executeUpdate();
             return 1;
        } catch (Exception e) {
            
             e.printStackTrace();
           
        }
        return 0;
     }
     
}
//