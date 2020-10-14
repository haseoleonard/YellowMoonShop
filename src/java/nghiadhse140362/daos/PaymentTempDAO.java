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
import javax.naming.NamingException;
import nghiadhse140362.utils.DBHelpers;

/**
 *
 * @author haseo
 */
public class PaymentTempDAO implements Serializable{
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public PaymentTempDAO() {
        con=null;
        psm=null;
        rs=null;
    }

    private void closeConnection() throws SQLException{
        if(rs!=null)rs.close();
        if(psm!=null)psm.close();
        if(con!=null)con.close();
    }
    
    public boolean addPaymentTempPair(String paymentID,String orderID) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "insert into PaymentTemp(paymentID,orderID) values(?,?)";
                psm = con.prepareStatement(sql);
                psm.setString(1, paymentID);
                psm.setString(2, orderID);
                int result = psm.executeUpdate();
                if(result>0)return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }
    
    public String getOrderIDFromPaymentID(String paymentID) throws SQLException, NamingException{
        try{
            con=DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select orderID from PaymentTemp where paymentID=?";
                psm = con.prepareStatement(sql);
                psm.setString(1, paymentID);
                rs=psm.executeQuery();
                if(rs.next())return rs.getString("orderID");
            }
        }finally{
            closeConnection();
        }
        return null;
    }
}
