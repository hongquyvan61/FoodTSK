/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Admin
 */
public class XuatKho {
    private int productid;
    private String name;
    private int quantity;
    public XuatKho(){
        productid=0;
        name=null;
        quantity = 0;
    }
    public XuatKho(int pdid,String n,int q){
        productid= pdid;
        name = n;
        quantity = q;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
