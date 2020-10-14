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
import javax.naming.NamingException;
import nghiadhse140362.dtos.ProductStatusDTO;
import nghiadhse140362.utils.DBHelpers;

/**
 *
 * @author haseo
 */
public class ProductStatusDAO implements Serializable{
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public ProductStatusDAO() {
        this.con=null;
        this.psm=null;
        this.rs=null;
    }

    private void closeConnection() throws SQLException {
        if(this.rs!=null)this.rs.close();
        if(this.psm!=null)this.psm.close();
        if(this.con!=null)this.con.close();
    }
    
    private List<ProductStatusDTO> productStatusList;

    public List<ProductStatusDTO> getProductStatusList() {
        return productStatusList;
    }
    public int getProductStatus() throws NamingException, SQLException{
       int total = 0;
       try{
           con=DBHelpers.makeConnection();
           if(con!=null){
               String sql = "select statusID,statusName from ProductStatus";
               psm = con.prepareStatement(sql);
               rs =psm.executeQuery();
               while(rs.next()){
                   if(this.productStatusList==null)this.productStatusList=new ArrayList<>();
                   int statusID = rs.getInt("statusID");
                   String statusName =rs.getString("statusName");
                   this.productStatusList.add(new ProductStatusDTO(statusID, statusName));
                   total++;
               }
           }
       }finally{
           closeConnection();
       }
       return total;
    }
    
    public int getStatusIDFromName(String statusName) throws SQLException, NamingException{
        try{
           con=DBHelpers.makeConnection();
           if(con!=null){
               String sql = "select statusID from ProductStatus where statusName=?";
               psm = con.prepareStatement(sql);
               psm.setString(1, statusName);
               rs =psm.executeQuery();
               if(rs.next()){
                   return rs.getInt("statusID");
               }
           }
       }finally{
           closeConnection();
       }
        return -1;
    }
}
