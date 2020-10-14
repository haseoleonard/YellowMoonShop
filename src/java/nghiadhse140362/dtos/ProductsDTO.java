/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.dtos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author haseo
 */
public class ProductsDTO implements Serializable {
    private int productID;
    private String productName;
    private String description;
    private String image;
    private int price;
    private int quantity;
    private int categoryID;
    private Timestamp createDate;
    private Date expirationDate;
    private int statusID;

    public ProductsDTO() {
    }

    public ProductsDTO(int productID, String productName,String image,String description, int price, int quantity, int categoryID, Timestamp createDate, Date expirationDate,int statusID) {
        this.productID = productID;
        this.productName = productName;
        this.image=image;
        this.description=description;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.statusID=statusID;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCreateDate() {
        return createDate.toLocalDateTime().minusNanos(createDate.getNanos()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getExpirationDate() {
        LocalDate dateTime = expirationDate.toLocalDate();
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
}
