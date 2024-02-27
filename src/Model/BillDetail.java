/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author tien
 */
public class BillDetail {
    private Integer id;
    private Integer product;
    private Integer bill;
    private Integer quantity;
    private Float price;

    public BillDetail(Integer id, Integer product, Integer bill, Integer quantity, Float price) {
        this.id = id;                     //bill ở đây là mã hoá đơn   //product ở đây là mã sp
        this.product = product;
        this.bill = bill;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
    
}
