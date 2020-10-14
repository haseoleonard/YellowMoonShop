/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.daos;

import nghiadhse140362.dtos.UsersDTO;
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
public class UsersDAO implements Serializable {
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public UsersDAO() {
        this.con=null;
        this.psm=null;
        this.rs=null;
    }

    private void closeConnection() throws SQLException {
        if(this.rs!=null)this.rs.close();
        if(this.psm!=null)this.psm.close();
        if(this.con!=null)this.con.close();
    }

    private UsersDTO loginUser;

    public UsersDTO getLoginUser() {
        return loginUser;
    }

    public boolean checkLogin(String username, String password) throws SQLException, NamingException {
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select username,googleID,name,address,phone,facebookID,roleID " +
                        "from Users " +
                        "where username=? and password=?";
                psm=con.prepareStatement(sql);
                psm.setString(1,username);
                psm.setString(2,password);
                rs=psm.executeQuery();
                if(rs.next()){
                    String googleID = rs.getString("googleID");
                    String name = rs.getNString("name");
                    String address = rs.getNString("address");
                    String phone = rs.getString("phone");
                    String facebookID = rs.getString("facebookID");
                    int roleID = rs.getInt("roleID");
                    this.loginUser = new UsersDTO(username,name,address,phone,googleID,facebookID,roleID);
                    return true;
                }
            }
        }finally {
            closeConnection();
        }
        return false;
    }

    public boolean checkLogin(String googleID) throws SQLException, NamingException {
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select username,googleID,name,address,phone,facebookID,roleID " +
                        "from Users " +
                        "where googleID=?";
                psm = con.prepareStatement(sql);
                psm.setString(1,googleID);
                rs = psm.executeQuery();
                if(rs.next()){
                    String username = rs.getString("username");
                    String name = rs.getNString("name");
                    String address = rs.getNString("address");
                    String phone = rs.getString("phone");
                    String facebookID = rs.getString("facebookID");
                    int roleID = rs.getInt("roleID");
                    this.loginUser = new UsersDTO(username,name, address, phone, googleID, facebookID, roleID);
                    return true;
                }
            }
        }finally{
            closeConnection();
        }
        return false;
    }
    
    public boolean createNewAccount(String username,String password,String name,String address,String phone,String googleID,String facebookID) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "insert into Users(username,password,name,address,phone,googleID,facebookID) "
                        + "values(?,?,?,?,?,?,?)";
                psm=con.prepareStatement(sql);
                psm.setString(1,username);
                psm.setString(2, password);
                psm.setNString(3, name);
                psm.setNString(4, address);
                psm.setString(5, phone);
                psm.setString(6, googleID);
                psm.setString(7, facebookID);
                int result = psm.executeUpdate();
                if(result>0)return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }
}
