/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.errors;

import java.io.Serializable;

/**
 *
 * @author haseo
 */
public class UsersError implements Serializable{
    private String usernameExistedErr;
    private String usernameLengthErr;
    private String passwordLengthErr;
    private String nameLengthErr;
    private String addressLengthErr;
    private String invalidPhoneErr;
    private String confirmNotMatchedErr;

    public String getUsernameExistedErr() {
        return usernameExistedErr;
    }

    public void setUsernameExistedErr(String usernameExistedErr) {
        this.usernameExistedErr = usernameExistedErr;
    }

    public String getConfirmNotMatchedErr() {
        return confirmNotMatchedErr;
    }

    public void setConfirmNotMatchedErr(String confirmNotMatchedErr) {
        this.confirmNotMatchedErr = confirmNotMatchedErr;
    }

    public String getUsernameLengthErr() {
        return usernameLengthErr;
    }

    public void setUsernameLengthErr(String usernameLengthErr) {
        this.usernameLengthErr = usernameLengthErr;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getNameLengthErr() {
        return nameLengthErr;
    }

    public void setNameLengthErr(String nameLengthErr) {
        this.nameLengthErr = nameLengthErr;
    }

    public String getAddressLengthErr() {
        return addressLengthErr;
    }

    public void setAddressLengthErr(String addressLengthErr) {
        this.addressLengthErr = addressLengthErr;
    }

    public String getInvalidPhoneErr() {
        return invalidPhoneErr;
    }

    public void setInvalidPhoneErr(String invalidPhoneErr) {
        this.invalidPhoneErr = invalidPhoneErr;
    }
       
}
