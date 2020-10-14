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
public class ProductErrors implements Serializable{
    private String emptyProductName;
    private String emptyDescription;
    private String emptyCategory;
    private String emptyStatus;
    private String invalidPrice;
    private String invalidQuantity;
    private String invalidFileType;
    private String invalidExpirationDate;
    private String invalidCreateDate;

    public String getEmptyProductName() {
        return emptyProductName;
    }

    public void setEmptyProductName(String emptyProductName) {
        this.emptyProductName = emptyProductName;
    }
    
    public String getEmptyDescription() {
        return emptyDescription;
    }

    public void setEmptyDescription(String emptyDescription) {
        this.emptyDescription = emptyDescription;
    }

    public String getInvalidPrice() {
        return invalidPrice;
    }

    public void setInvalidPrice(String invalidPrice) {
        this.invalidPrice = invalidPrice;
    }

    public String getInvalidQuantity() {
        return invalidQuantity;
    }

    public void setInvalidQuantity(String invalidQuantity) {
        this.invalidQuantity = invalidQuantity;
    }

    public String getInvalidFileType() {
        return invalidFileType;
    }

    public void setInvalidFileType(String invalidFileType) {
        this.invalidFileType = invalidFileType;
    }

    public String getEmptyCategory() {
        return emptyCategory;
    }

    public void setEmptyCategory(String emptyCategory) {
        this.emptyCategory = emptyCategory;
    }

    public String getInvalidExpirationDate() {
        return invalidExpirationDate;
    }

    public void setInvalidExpirationDate(String invalidExpirationDate) {
        this.invalidExpirationDate = invalidExpirationDate;
    }

    public String getInvalidCreateDate() {
        return invalidCreateDate;
    }

    public void setInvalidCreateDate(String invalidCreateDate) {
        this.invalidCreateDate = invalidCreateDate;
    }

    public String getEmptyStatus() {
        return emptyStatus;
    }

    public void setEmptyStatus(String emptyStatus) {
        this.emptyStatus = emptyStatus;
    }
    
    
}
