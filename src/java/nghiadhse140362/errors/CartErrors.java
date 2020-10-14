/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.errors;

import java.io.Serializable;

/**
 *
 * @author haseo
 */
public class CartErrors implements Serializable{
    private int productID;
    private String outOfStockErr;
    private int quantityLeft;
    private String statusChangedErr;

    public String getOutOfStockErr() {
        return outOfStockErr;
    }

    public void setOutOfStockErr(String outOfStockErr) {
        this.outOfStockErr = outOfStockErr;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    
    
    public String getStatusChangedErr() {
        return statusChangedErr;
    }

    public void setStatusChangedErr(String statusChangedErr) {
        this.statusChangedErr = statusChangedErr;
    }
    
}
