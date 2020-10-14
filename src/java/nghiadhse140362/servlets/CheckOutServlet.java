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
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nghiadhse140362.cart.CartItemObject;
import nghiadhse140362.cart.CartObject;
import nghiadhse140362.daos.OrderDetailsDAO;
import nghiadhse140362.daos.OrdersDAO;
import nghiadhse140362.daos.PaymentTempDAO;
import nghiadhse140362.daos.ProductsDAO;
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
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CheckOutServlet.class);

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
        String url = Constants.CHECK_OUT_PAGE;
        boolean redirect = false;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                String txtName = request.getParameter("txtName");
                String txtAddress = request.getParameter("txtAddress");
                String txtPhone = request.getParameter("txtPhone");
                String txtPaymentMethod = request.getParameter("txtPaymentMethod");
                if (txtPaymentMethod != null && !txtPaymentMethod.trim().isEmpty() && txtPaymentMethod.matches("\\d+")) {
                    int paymentMethod = Integer.parseInt(txtPaymentMethod);
                    String newOrderID = GeneratingHelpers.OrderIDGenerate();
                    if (cart != null) {
                        UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                        String loginUsername = null;
                        if (loginUser != null) {
                            loginUsername = loginUser.getUsername();
                        }
                        OrdersDAO ordersDAO = new OrdersDAO();
                        ProductsDAO productsDAO = new ProductsDAO();
                        boolean newOrder = ordersDAO.createOrder(newOrderID, loginUsername, txtName, txtAddress, txtPhone, cart.total(), paymentMethod);
                        if (newOrder) {
                            OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                            Map<Integer, CartItemObject> items = cart.getItems();
                            boolean rs = orderDetailsDAO.addOrderDetails(newOrderID, items);
                            if (!rs) {
                                request.setAttribute("CHECKOUT_FAILED", "FAILED");
                                //delete order
                            } else {
                                if(paymentMethod==0){
                                    boolean decreaseStock = productsDAO.decreaseQuantityByID(items);
                                    if (decreaseStock) {
                                        request.setAttribute("CHECKOUT_SUCCESS", newOrderID);
                                        url = Constants.TRACE_ORDER_PAGE;
                                        session.removeAttribute("CART");
                                    } else {
                                        //delete orderdetail
                                        //delete order
                                    }
                                }
                                if (paymentMethod == 1) {
                                    String momoOrderID = GeneratingHelpers.OrderIDGenerate();
                                    MoMoResponseObject moMoResponseObject = NetUtils.sendMomo(momoOrderID, momoOrderID, cart.total());
                                    if (moMoResponseObject != null) {
                                        if (moMoResponseObject.getPayUrl() != null && !moMoResponseObject.getPayUrl().trim().isEmpty()) {
                                            PaymentTempDAO paymentTempDAO = new PaymentTempDAO();
                                            paymentTempDAO.addPaymentTempPair(momoOrderID, newOrderID);
                                            url = moMoResponseObject.getPayUrl();
                                            redirect = true;
                                            session.removeAttribute("CART");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException | NamingException | MalformedURLException | NoSuchAlgorithmException | InvalidKeyException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (!redirect) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect(url);
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
