/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.dtos;

import java.io.Serializable;

/**
 *
 * @author haseo
 */
public class UsersDTO implements Serializable {
    private String username;
    private String name;
    private String address;
    private String phone;
    private String googleID;
    private String facebookID;
    private int roleID;

    public UsersDTO() {
    }

    public UsersDTO(String username, String name, String address, String phone,String googleID,String facebookID, int roleID) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.googleID=googleID;
        this.facebookID=facebookID;
        this.roleID = roleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
