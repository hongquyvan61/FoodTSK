/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.mysql.cj.jdbc.Blob;

/**
 *
 * @author tien
 */
public class Product {
    private Integer id;
    private String name;
    private Integer CategoryID;
    private Integer quantity;
    private Integer price;

    public Product(Integer id, String name, Integer CategoryID, Integer quantity, Integer price) {
        this.id = id;
        this.name = name;
        this.CategoryID = CategoryID;
        this.quantity = quantity;
        this.price = price;
    }

    public Product() {
        this.id=null;
        this.name = null;
        this.CategoryID = null;
        this.quantity = null;
        this.price = null;//To change body of generated methods, choose Tools | Templates.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(Integer CategoryID) {
        this.CategoryID = CategoryID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    
}
