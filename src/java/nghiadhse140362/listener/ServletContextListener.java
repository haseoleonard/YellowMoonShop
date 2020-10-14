/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.listener;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import nghiadhse140362.utils.Constants;
import nghiadhse140362.utils.FileHelpers;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Web application lifecycle listener.
 *
 * @author haseo
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ServletContext context = sce.getServletContext();
        PropertyConfigurator.configure(context.getRealPath("/WEB-INF/log4j.properties"));
        //
        try {
            String resourceMapRealPath = context.getRealPath("/WEB-INF/resource_map.properties");
            Map<String,String> resourceMap = FileHelpers.loadResourceMap(resourceMapRealPath);
            if(resourceMap==null)
                throw new Exception("RESOURCE MAP IS NULL");
            else
                context.setAttribute("RESOURCE_MAP", resourceMap);
            //
            String allowExtensionMapPath = context.getRealPath("/WEB-INF/allowing_extension.properties");
            List<String> allowExtension = FileHelpers.loadAllowingExtensionList(allowExtensionMapPath);
            if(allowExtension!=null){
                context.setAttribute("ALLOW_EXTENSIONS", allowExtension);
            }
            //
            String uploadDir = context.getInitParameter("IMAGE_UPLOAD_DIR");
            if (uploadDir == null || uploadDir.trim().isEmpty()) {
                throw new Exception("IMAGE UPLOAD DIRECTORY CANNOT BE EMPTY");
            }
            FileHelpers.setIMAGE_SAVING_FOLDER(uploadDir);
            //
            String momoAccessKey = context.getInitParameter("MOMO_ACCESS_KEY");
            if(momoAccessKey==null||momoAccessKey.trim().isEmpty()){
                throw new Exception("MOMO ACCESS KEY MUST NOT BE EMPTY");
            }
            Constants.MOMO_ACCESS_KEY=momoAccessKey;
            //
            String momoPartnerCode = context.getInitParameter("MOMO_PARTNER_CODE");
            if(momoPartnerCode==null||momoPartnerCode.trim().isEmpty()){
                throw new Exception("MOMO PARTNER CODE MUST NOT BE EMPTY");
            }
            Constants.MOMO_PARTNER_CODE=momoPartnerCode;
            //
            String momoSecretKey = context.getInitParameter("MOMO_SECRET_KEY");
            if(momoSecretKey==null||momoSecretKey.trim().isEmpty()){
                throw new Exception("MOMO SECRET KEY MUST NOT BE EMPTY");
            }
            Constants.setMOMO_SECRET_KEY(momoSecretKey);
            //
            String momoPortal = context.getInitParameter("MOMO_PORTAL");
            if(momoPortal==null||momoPortal.trim().isEmpty()){
                throw new Exception("MOMO PAYMENT PORTAL MUST NOT BE EMPTY");
            }
            Constants.MOMO_PORTAL=momoPortal;
            //
            String momoReturnURL = context.getInitParameter("MOMO_RETURN_URL");
            if(momoReturnURL==null||momoReturnURL.trim().isEmpty()){
                throw new Exception("MOMO RETURN URL MUST NOT BE EMPTY");
            }
            Constants.MOMO_RETURN_URL=momoReturnURL;
            //
            String momoNotifyURL = context.getInitParameter("MOMO_NOTIFY_URL");
            if(momoNotifyURL==null||momoNotifyURL.trim().isEmpty()){
                throw new Exception("MOMO NOTIFY URL MUST NOT BE EMPTY");
            }
            Constants.MOMO_NOTIFY_URL=momoNotifyURL;
            //
            String googleClientID = context.getInitParameter("GOOGLE_CLIENT_ID");
            if(googleClientID==null||googleClientID.trim().isEmpty()){
                throw new Exception("GOOGLE CLIENT ID MUST NOT BE EMPTY");
            }
            Constants.GOOGLE_CLIENT_ID=googleClientID;
            context.setAttribute("GOOGLE_CLIENT_ID", googleClientID);
            //            
        } catch (Exception ex) {
            LOGGER.fatal(ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
