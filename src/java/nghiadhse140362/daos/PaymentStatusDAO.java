/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.daos;

import nghiadhse140362.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author haseo
 */
public class PaymentStatusDAO implements Serializable {
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public PaymentStatusDAO() {
        this.con=null;
        this.psm=null;
        this.rs=null;
    }

    private void closeConnection() throws SQLException {
        if(rs!=null)rs.close();
        if(psm!=null)psm.close();
        if(con!=null)con.close();
    }

    public String getNameFromID(int statusID) throws NamingException, SQLException{
        try{
            con =DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select statusName from PaymentStatus where statusID=?";
                psm=con.prepareStatement(sql);
                psm.setInt(1,statusID);
                rs = psm.executeQuery();
                if(rs.next()) return rs.getString("statusName");
            }
        }finally{
            closeConnection();
        }
        return null;
    }
}
