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
public class CheckOutErrors implements Serializable{
    private String emptyNameErr;
    private String emptyAddressErr;
    private String invalidPhoneNumErr;

    public String getEmptyNameErr() {
        return emptyNameErr;
    }

    public void setEmptyNameErr(String emptyNameErr) {
        this.emptyNameErr = emptyNameErr;
    }

    public String getEmptyAddressErr() {
        return emptyAddressErr;
    }

    public void setEmptyAddressErr(String emptyAddressErr) {
        this.emptyAddressErr = emptyAddressErr;
    }

    public String getInvalidPhoneNumErr() {
        return invalidPhoneNumErr;
    }

    public void setInvalidPhoneNumErr(String invalidPhoneNumErr) {
        this.invalidPhoneNumErr = invalidPhoneNumErr;
    }
    
    
}
