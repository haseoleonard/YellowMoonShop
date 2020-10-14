/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nghiadhse140362.daos.OrderDetailsDAO;
import nghiadhse140362.daos.OrdersDAO;
import nghiadhse140362.daos.PaymentMethodsDAO;
import nghiadhse140362.daos.PaymentStatusDAO;
import nghiadhse140362.daos.ProductsDAO;
import nghiadhse140362.dtos.OrderDetailsDTO;
import nghiadhse140362.dtos.OrdersDTOExtend;
import nghiadhse140362.dtos.UsersDTO;
import nghiadhse140362.utils.Constants;
import org.apache.log4j.Logger;

/**
 *
 * @author haseo
 */
@WebServlet(name = "TrackOrderServlet", urlPatterns = {"/TrackOrderServlet"})
public class TrackOrderServlet extends HttpServlet {
    private static final Logger LOGGER =Logger.getLogger(TrackOrderServlet.class);
    
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
        try{
            HttpSession session = request.getSession(false);
            if(session!=null){
                UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                if(loginUser!=null){
                String txtOrderID = request.getParameter("txtOrderID");
                if(txtOrderID!=null&&!txtOrderID.trim().isEmpty()){
                    OrdersDAO ordersDAO = new OrdersDAO();
                    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                    ProductsDAO productsDAO = new ProductsDAO();
                    PaymentMethodsDAO paymentMethodsDAO = new PaymentMethodsDAO();
                    PaymentStatusDAO paymentStatusDAO = new PaymentStatusDAO();
                    OrdersDTOExtend order = ordersDAO.getOrderByID(txtOrderID, loginUser.getUsername());
                    if(order!=null){
                        order.setPaymentStatusName(paymentStatusDAO.getNameFromID(order.getPaymentStatus()));
                        List<OrderDetailsDTO> detailList = orderDetailsDAO.getOrderDetailsList(txtOrderID);
                        for(OrderDetailsDTO detail:detailList){
                            detail.setProductName(productsDAO.getNameByID(detail.getProductID()));
                        }
                        order.setDetailList(detailList);
                        order.setPaymentMethodName(paymentMethodsDAO.getMethodNameByType(order.getPaymentMethod()));
                        request.setAttribute("ORDER", order);
                    }
                }
            }
            }
        } catch (NamingException | SQLException ex) {
            LOGGER.error(ex.getMessage());
        }finally{
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
