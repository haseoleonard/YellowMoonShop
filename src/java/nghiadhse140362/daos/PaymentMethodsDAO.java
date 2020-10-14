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
import nghiadhse140362.dtos.PaymentMethodsDTO;
import nghiadhse140362.utils.DBHelpers;

/**
 *
 * @author haseo
 */
public class PaymentMethodsDAO implements Serializable{
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public PaymentMethodsDAO() {
        this.con=null;
        this.psm=null;
        this.rs=null;
    }
    
    private void closeConnection() throws SQLException{
        if(rs!=null)rs.close();
        if(psm!=null)psm.close();
        if(con!=null)con.close();
    }
    
    private List<PaymentMethodsDTO> methodList;

    public List<PaymentMethodsDTO> getMethodList() {
        return methodList;
    }
    
    public int getPaymentMethodsList() throws SQLException, NamingException{
        int total = 0;
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select methodType,methodName from PaymentMethods";
                psm =con.prepareStatement(sql);
                rs = psm.executeQuery();
                while(rs.next()){
                    if(this.methodList==null)this.methodList=new ArrayList<>();
                    int methodType = rs.getInt("methodType");
                    String methodName = rs.getString("methodName");
                    this.methodList.add(new PaymentMethodsDTO(methodType, methodName));
                    total++;
                }
            }
        }finally{
            closeConnection();
        }
        return total;
    }
    
    public String getMethodNameByType(int methodType) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select methodName from PaymentMethods where methodType=?";
                psm = con.prepareStatement(sql);
                psm.setInt(1, methodType);
                rs = psm.executeQuery();
                if(rs.next()){
                    return rs.getString("methodName");
                }
            }
        }finally{
            closeConnection();
        }
        return null;
    }
}
