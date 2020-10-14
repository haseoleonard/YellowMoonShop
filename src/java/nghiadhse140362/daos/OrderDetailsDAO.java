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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import nghiadhse140362.cart.CartItemObject;
import nghiadhse140362.dtos.OrderDetailsDTO;
import nghiadhse140362.utils.DBHelpers;

/**
 *
 * @author haseo
 */
public class OrderDetailsDAO implements Serializable{
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public OrderDetailsDAO() {
        con=null;
        psm=null;
        rs=null;
    }
    
    private void closeConnection() throws SQLException{
        if(rs!=null)rs.close();
        if(psm!=null)psm.close();
        if(con!=null)con.close();
    }
    
    public List<OrderDetailsDTO> getOrderDetailsList(String orderID) throws SQLException, NamingException{
        List<OrderDetailsDTO> orderDetailsList = null;
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select productID,quantity,total "
                        + "from OrderDetails "
                        + "where orderID=?";
                psm = con.prepareStatement(sql);
                psm.setString(1, orderID);
                rs = psm.executeQuery();
                while(rs.next()){
                    if(orderDetailsList==null)orderDetailsList=new ArrayList<>();
                    int productID = rs.getInt("productID");
                    int quantity = rs.getInt("quantity");
                    long total = rs.getLong("total");
                    orderDetailsList.add(new OrderDetailsDTO(orderID, productID, quantity,total));
                }
            }
        }finally{
            closeConnection();
        }
        return orderDetailsList;
    }
    
    public boolean addOrderDetails(String orderID,Map<Integer,CartItemObject> items) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "insert into OrderDetails(orderID,productID,quantity,total) values(?,?,?,?)";
                con.setAutoCommit(false);
                psm = con.prepareStatement(sql);
                for (Map.Entry<Integer, CartItemObject> entry : items.entrySet()) {
                    psm.setString(1, orderID);
                    psm.setInt(2, entry.getKey());
                    psm.setInt(3, entry.getValue().getQuantity());
                    psm.setLong(4, entry.getValue().total());
                    psm.addBatch();psm.clearParameters();
                }
                int[] executeBatch = psm.executeBatch();
                for(int i:executeBatch){
                    if(i==PreparedStatement.EXECUTE_FAILED)return false;
                }
                con.commit();
                return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }
}
