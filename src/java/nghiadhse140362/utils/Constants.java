/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.utils;

/**
 *
 * @author haseo
 */
public class Constants {
    public static String GOOGLE_CLIENT_ID = "671719685472-humdjuu5rjbsirkqtfv9s11jkdvqo6vu.apps.googleusercontent.com";
    //
    private static String MOMO_SECRET_KEY="";
    public static String MOMO_PARTNER_CODE="MOMO5QRL20201010";    
    public static String MOMO_ACCESS_KEY="OpuKqLO5pvLEz1gy";
    public static String MOMO_PORTAL = "https://test-payment.momo.vn/gw_payment/transactionProcessor";    
    public static String MOMO_RETURN_URL="http://localhost:8084/YellowMoon/momoReturn";
    public static String MOMO_NOTIFY_URL="http://localhost:8084/YellowMoon/momoReturn";
    public static final String MOMO_ORDER_INFO = "YellowMoonShop";
    public static final String MOMO_REQUEST_TYPE ="captureMoMoWallet";
    
    public static String getMOMO_SECRET_KEY() {
        return MOMO_SECRET_KEY;
    }
    public static void setMOMO_SECRET_KEY(String MOMO_SECRET_KEY) {
        Constants.MOMO_SECRET_KEY = MOMO_SECRET_KEY;
    }    
    //properties
    public static final int PRODUCT_PER_PAGE = 20;
    public static final int DEFAULT_PAGE = 1;
    public static final int ORDERID_LENGTH = 10;
    //Resources
    public static final String PRODUCT_LIST_PAGE = "productlist.jsp";
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String ADMIN_PAGE = "adminpage.jsp";
    public static final String CREATE_PRODUCT_PAGE = "createproduct.jsp";
    public static final String UPDATE_PRODUCT_PAGE = "updateproduct.jsp";
    public static final String VIEW_CART_PAGE = "viewcart.jsp";
    public static final String LOAD_PRODUCT_CONTROLLER = "loadProduct";
    public static final String SEARCH_PRODUCT_CONTROLLER = "search";
    public static final String CHECK_OUT_PAGE="checkout.jsp";
    public static final String TRACE_ORDER_PAGE = "traceorder.jsp";
    public static final String CREATE_ACCOUNT_PAGE = "createaccount.jsp";
    public static final String TRACK_ORDER_CONTROLLER = "traceOrder";
    
}
