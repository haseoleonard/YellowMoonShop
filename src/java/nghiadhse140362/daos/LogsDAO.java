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
public class LogsDAO implements Serializable{
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public LogsDAO() {
        con=null;
        psm=null;
        rs=null;
    }
    
    private void closeConnection() throws SQLException{
        if(rs!=null)rs.close();
        if(psm!=null)psm.close();
        if(con!=null)con.close();
    }
    
    public boolean addLogs(int productID,String username) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "insert into Logs(productID,username) values(?,?)";
                psm = con.prepareStatement(sql);
                psm.setInt(1, productID);
                psm.setString(2, username);
                int result = psm.executeUpdate();
                if(result>0)return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }
}
