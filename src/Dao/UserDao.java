/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DB.ConnectDB;

import Model.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author tien
 */
public class UserDao {
    private ConnectDB dB ;
    private User user;

    private Connection connection;
    public UserDao() {
         dB=new ConnectDB();
        this.connection=dB.GetConnect();
        
    }
    
    public boolean login(String username, String password){    //SỬA HÀM NÀY
        String sql="select * from user";
       
        try {
            ResultSet rs=connection.createStatement().executeQuery(sql);
           
            while(rs.next()){
                if((username.equals(rs.getString(3))) && (password.equals(rs.getString(4)))){ 
                    return true;
                }
            }
           
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return  false;
    }
    public User getUserLogined(String username, String password) throws SQLException{ 
        
         //SỬA HÀM NÀY
        String sql="select * from user where username=? and password=?";
     
         PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setString(1,username);
            stmt.setString(2,password);
        try(ResultSet rs=stmt.executeQuery();) {
            while (rs.next()) {
                user=new User(rs.getInt("id"),rs.getString("name"),rs.getString("username"),rs.getString("password"),rs.getString("sex"),rs.getInt("role"));
            }
        } catch (Exception e) {
        }
        
     return user;
    }
    public boolean insert(User user) {
       // PreparedStatement statement = null;
        try {
            //lay tat ca danh sach sinh vien
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodtsk", "root", "");
            
            //query
            String sql = "insert into user(name, username, password, sex, role) values(?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareCall(sql);
            
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getSex());
            statement.setInt(5, user.getRole());
            
            statement.execute();
            return  true;
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return false;
    }
     public ArrayList<User>getAllRows()throws Exception{
        ArrayList<User> lst=new ArrayList<User>();
        String sql="select * from user";
        ResultSet rs=connection.createStatement().executeQuery(sql);
       // user lh=null;
        while(rs.next()){
            user=new User(rs.getInt("id"),rs.getString("name"),rs.getString("username"),rs.getString("password"),rs.getString("sex"),rs.getInt("role"));
            lst.add(user);
        }
     return lst;
 }
      public int deleteUser(int id){
        String sql = "DELETE FROM user WHERE id="+id+";";
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
      public User getbyId(int id){
        String sql="select * from user where id="+id+"";
        try {
             ResultSet rs=connection.createStatement().executeQuery(sql); while(rs.next()){
            user=new User(rs.getInt("id"),rs.getString("name"),rs.getString("username"),rs.getString("password"),rs.getString("sex"),rs.getInt("role"));
        }
        } catch (Exception e) {
             e.printStackTrace();
        }
    
    // user lh=null;
    
     return user;
 }
        public int updateUser(User user){
             //SỬA HÀM NÀY
             String password=user.getPassword();
     String sql = "UPDATE user SET name='"+user.getName()+"', username='"+user.getUsername()+"',"
             + "password='" + password + "'"
             + ",sex='"+user.getSex()+"'"
             + ", role='"+user.getRole()+"'"
             + " WHERE id='"+user.getId()+"'";
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
}
