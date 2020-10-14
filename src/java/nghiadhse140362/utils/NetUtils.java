/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.utils;

import nghiadhse140362.momo.MoMoRequestObject;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import nghiadhse140362.momo.MoMoResponseObject;

/**
 *
 * @author haseo
 */
public class NetUtils {

    public static MoMoResponseObject sendMomo(String orderID, String requestID, long totalAmmount) throws MalformedURLException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        HttpURLConnection con = null;
        DataOutputStream dos = null;
        Gson gson = new Gson();
        InputStream is = null;
        MoMoResponseObject mmro = null;
        try {
            String signature = EncodeUtils.getMomoSignature(requestID, orderID, totalAmmount);
            MoMoRequestObject moMoObject = new MoMoRequestObject(requestID, String.valueOf(totalAmmount), orderID, signature);
            String json = gson.toJson(moMoObject);
            byte[] postData = json.getBytes(StandardCharsets.UTF_8);
            //
            URL url = new URL(Constants.MOMO_PORTAL);
            con = (HttpURLConnection) url.openConnection();
            //
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestProperty("Content-Length", "" + postData.length);
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.connect();
            //
            dos = new DataOutputStream(con.getOutputStream());
            dos.write(postData);
            is = con.getInputStream();
            mmro = parseMoMoResponse(is);
        } finally {
            if (dos != null) {
                dos.close();
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return mmro;
    }

    private static MoMoResponseObject parseMoMoResponse(InputStream is) throws IOException {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            return gson.fromJson(isr, MoMoResponseObject.class);
        } finally {
            if (isr != null) {
                isr.close();
            }
        }
    }
}
