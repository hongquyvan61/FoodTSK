/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;
import DB.ConnectDB;
import Model.NhapKho;
import Model.Product;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Admin
 */
public class NhapKhoDao {
    private ConnectDB dB ;

    private Connection connection;
    public NhapKhoDao() {
        dB=new ConnectDB();
        this.connection=dB.GetConnect();
    }
    public ArrayList<NhapKho> getAllRows(){
        String sql = "select * from nhapkho";
        ArrayList<NhapKho> result = new ArrayList<NhapKho>();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhapKho nk = new NhapKho();
                nk.setProduct(rs.getInt(1));
                nk.setQuantity(rs.getInt(2));
                nk.setPrice(rs.getInt(3));
                nk.setDate(rs.getString(4));
                result.add(nk);
               
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
