/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.dtos;

import java.io.Serializable;

/**
 *
 * @author haseo
 */
public class OrderDetailsDTO implements Serializable{
    private String orderID;
    private int productID;
    private String productName;
    private int quantity;
    private long total;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String orderID, int productID, int quantity,long total) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.total=total;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
