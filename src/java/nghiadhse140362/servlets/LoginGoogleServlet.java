/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nghiadhse140362.daos.UsersDAO;
import nghiadhse140362.dtos.UsersDTO;
import nghiadhse140362.utils.Constants;
import nghiadhse140362.utils.GoogleTokenVerifierAndParser;
import org.apache.log4j.Logger;

/**
 *
 * @author haseo
 */
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/LoginGoogleServlet"})
public class LoginGoogleServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginGoogleServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Constants.LOGIN_PAGE;
        try {
            /* TODO output your page here. You may use following sample code. */
            String tokenIdString = request.getParameter("id_token");
            GoogleIdToken.Payload payload = GoogleTokenVerifierAndParser.getPayload(tokenIdString);
            if (payload != null) {
                String name = (String) payload.get("name");
                String gid = payload.getSubject();
                UsersDAO dao = new UsersDAO();
                boolean rs = dao.checkLogin(gid);
                HttpSession session = request.getSession();
                if (!rs) {
                    session.setAttribute("GOOGLE_ID", gid);
                    session.setAttribute("NAME", name);
                    url = Constants.CREATE_ACCOUNT_PAGE;
                } else {
                    UsersDTO loginUser = dao.getLoginUser();
                    session.setAttribute("LOGIN_USER", loginUser);
                    url = Constants.PRODUCT_LIST_PAGE;
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(Constants.LOGIN_PAGE);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
