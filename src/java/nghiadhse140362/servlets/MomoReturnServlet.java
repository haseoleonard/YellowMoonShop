/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nghiadhse140362.daos.OrderDetailsDAO;
import nghiadhse140362.daos.OrdersDAO;
import nghiadhse140362.daos.PaymentTempDAO;
import nghiadhse140362.daos.ProductsDAO;
import nghiadhse140362.dtos.OrderDetailsDTO;
import nghiadhse140362.utils.Constants;
import nghiadhse140362.utils.EncodeUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author haseo
 */
@WebServlet(name = "MomoReturnServlet", urlPatterns = {"/MomoReturnServlet"})
public class MomoReturnServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MomoReturnServlet.class);

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
        try {
            String requestId = request.getParameter("requestId");
            String orderID = request.getParameter("orderId");
            String errorCode = request.getParameter("errorCode");
            String transId = request.getParameter("transId");
            String amount = request.getParameter("amount");
            String message = request.getParameter("message");
            String localMessage = request.getParameter("localMessage");
            String responseTime = request.getParameter("responseTime");
            String payType = request.getParameter("payType");
            String extraData = request.getParameter("extraData");
            String signature = request.getParameter("signature");
            //
            if (signature != null && !signature.trim().isEmpty()) {
                boolean rs = EncodeUtils.momoReturnSignatureVerify(requestId, orderID, errorCode, transId,
                        amount, message, localMessage, responseTime, payType, extraData, signature);
                if (rs) {
                    ProductsDAO productsDAO = new ProductsDAO();
                    OrdersDAO ordersDAO = new OrdersDAO();
                    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                    PaymentTempDAO paymentTempDAO = new PaymentTempDAO();
                    //
                    String serverOrderID = paymentTempDAO.getOrderIDFromPaymentID(orderID);
                    if (serverOrderID != null && !serverOrderID.trim().isEmpty()) {
                        int errCode = Integer.parseInt(errorCode);
                        if (errCode == 0) {
                            List<OrderDetailsDTO> details = orderDetailsDAO.getOrderDetailsList(serverOrderID);
                            boolean decrease = productsDAO.decreaseQuantityByID(details);
                            if (decrease) {
                                boolean update = ordersDAO.updatePaymentStatus(serverOrderID, 2);
                                if (update) {
                                    request.setAttribute("CHECKOUT_SUCCESS", serverOrderID);
                                }
                            }
                        } else {
                            ordersDAO.updatePaymentStatus(serverOrderID, 0);
                            request.setAttribute("CHECKOUT_FAILED", serverOrderID);
                        }
                    }
                }
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | NamingException | SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            request.getRequestDispatcher(Constants.TRACE_ORDER_PAGE).forward(request, response);
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
