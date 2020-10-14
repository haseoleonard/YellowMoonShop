/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.servlets;

import nghiadhse140362.utils.Constants;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nghiadhse140362.daos.CategoriesDAO;
import nghiadhse140362.daos.ProductsDAO;
import nghiadhse140362.dtos.ProductsDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author haseo
 */
@WebServlet(name = "SearchProductServlet", urlPatterns = {"/SearchProductServlet"})
public class SearchProductServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchProductServlet.class);

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
            String searchValue = request.getParameter("txtSearchValue");
            String txtMin = request.getParameter("minPrice");
            String txtMax = request.getParameter("maxPrice");
            String txtCategory = request.getParameter("txtCategory");
            String txtPage = request.getParameter("page");
            int page = Constants.DEFAULT_PAGE;
            if (txtPage != null && !txtPage.trim().isEmpty() && txtPage.matches("\\d+")) {
                page = Integer.parseInt(txtPage);
            }
            if (searchValue != null) {
                CategoriesDAO categoriesDAO = new CategoriesDAO();
                ProductsDAO dao = new ProductsDAO();
                boolean loadedMinMax = dao.getMinMaxPrice();
                if (loadedMinMax) {
                    int categoryID = categoriesDAO.getCategoryIDFromName(txtCategory);
                    String category;
                    if (txtCategory!=null&&txtCategory.trim().isEmpty()) {
                        category = "%";
                    } else {
                        category = String.valueOf(categoryID);
                    }
                    int minPrice = dao.getMinPrice();
                    int maxPrice = dao.getMaxPrice();
                    if (txtMin != null && !txtMin.trim().isEmpty()) {
                        minPrice = Integer.parseInt(txtMin);
                        if (minPrice < dao.getMinPrice()) {
                            minPrice = dao.getMinPrice();
                        }
                    }
                    if (txtMax != null && !txtMax.trim().isEmpty()) {
                        maxPrice = Integer.parseInt(txtMax);
                        if (maxPrice > dao.getMaxPrice()) {
                            maxPrice = dao.getMaxPrice();
                        }
                    }
                    int maxPage = dao.getNumberOfProductPage(searchValue, minPrice, maxPrice, category, false);
                    if (page > maxPage) {
                        page = maxPage;
                    }
                    if (page < Constants.DEFAULT_PAGE) {
                        page = Constants.DEFAULT_PAGE;
                    }
                    int result = dao.searchProduct(searchValue, minPrice, maxPrice, category, page, false);
                    if (result > 0) {
                        request.setAttribute("CURR_PAGE", page);
                        request.setAttribute("MAX_PAGE", maxPage);
                        List<ProductsDTO> productList = dao.getProductList();
                        request.setAttribute("PRODUCT_LIST", productList);
                    }
                }
            }
        } catch (SQLException | NamingException | NumberFormatException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            request.getRequestDispatcher(Constants.PRODUCT_LIST_PAGE).forward(request, response);
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
