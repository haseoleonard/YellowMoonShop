/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import nghiadhse140362.dtos.OrdersDTOExtend;
import nghiadhse140362.utils.DBHelpers;

/**
 *
 * @author haseo
 */
public class OrdersDAO implements Serializable{
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public OrdersDAO() {
        con=null;
        psm=null;
        rs=null;
    }
    
    private void closeConnection() throws SQLException{
        if(rs!=null)rs.close();
        if(psm!=null)psm.close();
        if(con!=null)con.close();
    }
    
    public boolean createOrder(String orderID,String username,String customerName,String customerAddress,String phone,long total,int paymentMethod) throws SQLException, NamingException{
        try{
            con= DBHelpers.makeConnection();
            if(con!=null){
                String sql = "insert into Orders(orderID,username,customerName,customerAddress,customerPhone,total,paymentMethod) "
                        + "values(?,?,?,?,?,?,?)";
                psm = con.prepareStatement(sql);
                psm.setString(1, orderID);
                psm.setString(2, username);
                psm.setNString(3, customerName);
                psm.setNString(4, customerAddress);
                psm.setString(5, phone);
                psm.setLong(6, total);
                psm.setInt(7, paymentMethod);
                int result = psm.executeUpdate();
                if(result>0)return true;
            }   
        }finally{
            closeConnection();
        }
        return false;
    }
    
    public OrdersDTOExtend getOrderByID(String orderID, String username) throws NamingException, SQLException{
        OrdersDTOExtend order = null;
        try{
            con= DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select customerName,customerAddress,customerPhone,orderDate,paymentMethod,paymentStatus,total "
                        + "from Orders where orderID=? and username=?";
                psm = con.prepareStatement(sql);
                psm.setString(1, orderID);
                psm.setString(2, username);
                rs = psm.executeQuery();
                if(rs.next()){
                    String customerName = rs.getNString("customerName");
                    String customerAddress = rs.getNString("customerAddress");
                    String customerPhone = rs.getString("customerPhone");
                    Timestamp orderDate =rs.getTimestamp("orderDate");
                    int paymentMethod = rs.getInt("paymentMethod");
                    int paymentStatus = rs.getInt("paymentStatus");
                    long total = rs.getLong("total");
                    order=new OrdersDTOExtend(null,null,orderID, username, customerName, customerAddress, customerPhone, orderDate, paymentMethod, paymentStatus,total,null);
                }
            }   
        }finally{
            closeConnection();
        }
        return order;
    }
    
    public boolean updatePaymentStatus(String orderID,int paymentStatus) throws NamingException, SQLException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "update Orders set paymentStatus=? where orderID=?";
                psm = con.prepareStatement(sql);
                psm.setInt(1, paymentStatus);
                psm.setString(2, orderID);                
                int result = psm.executeUpdate();
                if(result>0)return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }
}
