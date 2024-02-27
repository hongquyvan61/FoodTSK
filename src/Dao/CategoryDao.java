/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DB.ConnectDB;
import Model.Category;
import Model.Product;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author tien
 */
public class CategoryDao {
    private ConnectDB dB ;
    private Category category;
    private Connection connection;
    public CategoryDao() {
        dB=new ConnectDB();
        this.connection=dB.GetConnect();
    }
     public ArrayList<Category>getAllRows()throws Exception{
        ArrayList<Category> lst=new ArrayList<Category>();
        String sql="select * from category";
        ResultSet rs=connection.createStatement().executeQuery(sql);
    // user lh=null;
        while(rs.next()){
            category=new Category(rs.getInt("id"),
                    rs.getString("name"));
            lst.add(category);
        }
        return lst;
 }
    
}
