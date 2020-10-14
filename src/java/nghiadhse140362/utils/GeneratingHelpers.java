/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.utils;

import java.util.Random;

/**
 *
 * @author haseo
 */
public final class GeneratingHelpers {
    public static String OrderIDGenerate(){
        StringBuilder builder = new StringBuilder();
        Random rand = new Random(); 
        while(builder.length()<Constants.ORDERID_LENGTH){
            int ASCII = rand.nextInt(90);
            if((ASCII>47&&ASCII<58)||(ASCII>64&&ASCII<91)){
                builder.append((char) ASCII);
            }
        }
        return builder.toString();
    }
}
