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
public class PaymentMethodsDTO implements Serializable{
    private int methodType;
    private String methodName;

    public PaymentMethodsDTO() {
    }

    public PaymentMethodsDTO(int methodType, String methodName) {
        this.methodType = methodType;
        this.methodName = methodName;
    }

    public int getMethodType() {
        return methodType;
    }

    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
}
