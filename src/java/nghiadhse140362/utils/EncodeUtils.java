/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author haseo
 */
public class EncodeUtils {
    public static String HMACSHA256(String params) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException{
        Mac sha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(Constants.getMOMO_SECRET_KEY().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256.init(secret_key);
        return HexBin.encode(sha256.doFinal(params.getBytes(StandardCharsets.UTF_8))).toLowerCase();
    }
    
    public static String getMomoSignature(String requestID,String orderID,long totalAmmount) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException{
        String parameters = "partnerCode=" + Constants.MOMO_PARTNER_CODE
                + "&accessKey=" + Constants.MOMO_ACCESS_KEY
                + "&requestId=" + requestID
                + "&amount=" + totalAmmount
                + "&orderId=" + orderID
                + "&orderInfo=" + Constants.MOMO_ORDER_INFO
                + "&returnUrl=" + Constants.MOMO_RETURN_URL
                + "&notifyUrl=" + Constants.MOMO_NOTIFY_URL
                + "&extraData=";
        return HMACSHA256(parameters);
    }
    
    public static boolean momoReturnSignatureVerify(String requestID,String orderID,String errorCode,
            String transID,String ammount,String message,String localMessage,String responseTime,
            String payType,String extraData,String signature) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException{
        
        String parameters = "partnerCode=" + Constants.MOMO_PARTNER_CODE
                + "&accessKey=" + Constants.MOMO_ACCESS_KEY
                + "&requestId=" + requestID
                + "&amount=" + ammount
                + "&orderId=" + orderID
                + "&orderInfo=" + Constants.MOMO_ORDER_INFO
                + "&orderType=momo_wallet"
                + "&transId=" + transID                
                + "&message=" + message
                + "&localMessage="+localMessage
                + "&responseTime="+responseTime
                + "&errorCode=" + errorCode
                + "&payType=" + payType
                + "&extraData=";
        String HMAC = HMACSHA256(parameters);
        return signature.equals(HMAC);
    }
}
