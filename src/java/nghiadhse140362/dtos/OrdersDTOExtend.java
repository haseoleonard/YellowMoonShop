/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.dtos;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author haseo
 */
public class OrdersDTOExtend extends OrdersDTO {

    private String paymentMethodName;
    private String paymentStatusName;
    private List<OrderDetailsDTO> detailList;

    public OrdersDTOExtend() {
    }

    public OrdersDTOExtend(String paymentMethodName, List<OrderDetailsDTO> detailList, String orderID,
            String username, String customerName, String customerAddress, String customerPhone, Timestamp orderDate,
            int paymentMethod, int paymentStatus,long total, String paymentStatusName) {
        super(orderID, username, customerName, customerAddress, customerPhone, orderDate, paymentMethod, paymentStatus,total);
        this.paymentMethodName = paymentMethodName;
        this.detailList = detailList;
        this.paymentStatusName=paymentStatusName;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public List<OrderDetailsDTO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderDetailsDTO> detailList) {
        this.detailList = detailList;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }

    public void setPaymentStatusName(String paymentStatusName) {
        this.paymentStatusName = paymentStatusName;
    }
}
