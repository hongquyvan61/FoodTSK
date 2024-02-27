/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

/**
 *
 * @author tien
 */
public class ConnectDB {
    public static Connection connect ;
	public static Connection  GetConnect()  {
		try {
		
		 Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		  return DriverManager.getConnection("jdbc:mysql://localhost:3306/foodtsk","root","");
		  
       
       
		}catch(Exception e){
			e.printStackTrace();
		}
		 return null;
	}
}
