/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nghiadhse140362.daos.ProductsDAO;
import nghiadhse140362.utils.Constants;
import org.apache.log4j.Logger;

/**
 *
 * @author haseo
 */
@WebServlet(name = "SearchProductAdminServlet", urlPatterns = {"/SearchProductAdminServlet"})
public class SearchProductAdminServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchProductAdminServlet.class);

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
            String txtSearchValue = request.getParameter("txtSearchValue");
            if (txtSearchValue != null && !txtSearchValue.trim().isEmpty()) {
                ProductsDAO dao = new ProductsDAO();
                int maxPage = dao.getNumberOfProductPage(txtSearchValue, 0, 0, "%", true);
                int page = Constants.DEFAULT_PAGE;
                String txtPage = request.getParameter("page");
                if (txtPage != null && !txtPage.trim().isEmpty() && txtPage.matches("\\d+")) {
                    page = Integer.parseInt(txtPage);
                    if (page > maxPage) {
                        page = maxPage;
                    }
                    if (page < Constants.DEFAULT_PAGE) {
                        page = Constants.DEFAULT_PAGE;
                    }
                }
                int totalProduct = dao.searchProduct(txtSearchValue, 0, 0, null, page, true);
                if (totalProduct > 0) {
                    request.setAttribute("MAX_PAGE", maxPage);
                    request.setAttribute("PRODUCT_LIST", dao.getProductList());
                }
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            request.getRequestDispatcher(Constants.ADMIN_PAGE).forward(request, response);
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
