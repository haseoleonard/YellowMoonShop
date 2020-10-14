/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.dtos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author haseo
 */
public class OrdersDTO implements Serializable {

    protected String orderID;
    protected String username;
    protected String customerName;
    protected String customerAddress;
    protected String customerPhone;
    protected Timestamp orderDate;
    protected int paymentMethod;
    protected int paymentStatus;
    protected long total;

    public OrdersDTO() {
    }

    public OrdersDTO(String orderID, String username, String customerName, String customerAddress, String customerPhone, Timestamp orderDate, int paymentMethod, int paymentStatus,long total) {
        this.orderID = orderID;
        this.username = username;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.total = total;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getOrderDate() {
        return orderDate.toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
    
}
