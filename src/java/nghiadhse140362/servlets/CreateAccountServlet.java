/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.servlets;

import nghiadhse140362.errors.UsersError;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nghiadhse140362.daos.UsersDAO;
import nghiadhse140362.utils.Constants;
import org.apache.log4j.Logger;

/**
 *
 * @author haseo
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(CreateAccountServlet.class);

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
        boolean rs = false;
        try {
            String txtUsername = request.getParameter("txtUsername");
            String txtPassword = request.getParameter("txtPassword");
            String txtName = request.getParameter("txtName");
            String txtAddress = request.getParameter("txtAddress");
            String txtPhone = request.getParameter("txtPhone");
            String facebookID = null;
            String googleID = null;
            HttpSession session = request.getSession(false);
            if (session != null) {
                googleID = (String) session.getAttribute("GOOGLE_ID");
                facebookID = (String) session.getAttribute("LOGINED_FACEBOOKID");
            }
            UsersDAO dao = new UsersDAO();
            rs = dao.createNewAccount(txtUsername, txtPassword, txtName, txtAddress, txtPhone, googleID, facebookID);
        } catch (SQLException | NamingException ex) {
            if (ex.getMessage().contains("duplicated")) {
                UsersError error = new UsersError();
                error.setUsernameExistedErr("Username existed");
                request.setAttribute("ERROR", error);
            }
            LOGGER.error(ex.getMessage());
        } finally {
            if (rs) {
                request.getRequestDispatcher(Constants.LOGIN_PAGE).forward(request, response);
            } else {                
                request.getRequestDispatcher(Constants.CREATE_ACCOUNT_PAGE).forward(request, response);
            }
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
        processRequest(request, response);
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
