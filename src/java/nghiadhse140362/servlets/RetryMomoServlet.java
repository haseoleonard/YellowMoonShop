/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.servlets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nghiadhse140362.daos.OrdersDAO;
import nghiadhse140362.daos.PaymentTempDAO;
import nghiadhse140362.dtos.OrdersDTO;
import nghiadhse140362.dtos.UsersDTO;
import nghiadhse140362.momo.MoMoResponseObject;
import nghiadhse140362.utils.Constants;
import nghiadhse140362.utils.GeneratingHelpers;
import nghiadhse140362.utils.NetUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author haseo
 */
@WebServlet(name = "RetryMomoServlet", urlPatterns = {"/RetryMomoServlet"})
public class RetryMomoServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RetryMomoServlet.class);

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
        String txtOrderID = request.getParameter("txtOrderID");
        String url = Constants.TRACE_ORDER_PAGE;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                if (loginUser != null) {
                    if (txtOrderID != null) {
                        OrdersDAO ordersDAO = new OrdersDAO();
                        OrdersDTO order = ordersDAO.getOrderByID(txtOrderID, loginUser.getUsername());
                        if (order != null) {
                            if (order.getPaymentMethod() == 1) {
                                String momoOrderID = GeneratingHelpers.OrderIDGenerate();
                                MoMoResponseObject mmro = NetUtils.sendMomo(momoOrderID, momoOrderID, order.getTotal());
                                if (mmro != null) {
                                    if (mmro.getPayUrl() != null && !mmro.getPayUrl().trim().isEmpty()) {
                                        PaymentTempDAO paymentTempDAO = new PaymentTempDAO();
                                        paymentTempDAO.addPaymentTempPair(momoOrderID, txtOrderID);
                                        url = mmro.getPayUrl();
                                    }
                                }
                            }else{
                                url=Constants.TRACK_ORDER_CONTROLLER+"?txtOrderID="+txtOrderID;
                            }
                        }
                    }
                }
            }
        } catch (NamingException | SQLException | MalformedURLException | NoSuchAlgorithmException | InvalidKeyException ex) {
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
