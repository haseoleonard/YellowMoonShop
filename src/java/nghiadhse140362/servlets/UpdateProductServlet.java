/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nghiadhse140362.daos.CategoriesDAO;
import nghiadhse140362.daos.LogsDAO;
import nghiadhse140362.daos.ProductStatusDAO;
import nghiadhse140362.daos.ProductsDAO;
import nghiadhse140362.dtos.ProductsDTO;
import nghiadhse140362.dtos.UsersDTO;
import nghiadhse140362.errors.ProductErrors;
import nghiadhse140362.utils.Constants;
import nghiadhse140362.utils.FileHelpers;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 *
 * @author haseo
 */
@MultipartConfig
@WebServlet(name = "UpdateProductServlet", urlPatterns = {"/UpdateProductServlet"})
public class UpdateProductServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateProductServlet.class);

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
        ServletContext context = request.getServletContext();
        List<String> allowExtensions = (List<String>) context.getAttribute("ALLOW_EXTENSIONS");
        //
        String txtProductID = null;
        boolean valid = true;
        boolean redirect = false;
        ProductErrors error = new ProductErrors();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                if (loginUser != null) {
                    InputStream is = null;
                    List items;
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    //
                    items = upload.parseRequest(request);
                    //
                    Iterator iter = items.iterator();
                    Hashtable params = new Hashtable();
                    String imageName = null;
                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                            params.put(item.getFieldName(), item.getString("UTF-8"));
                        } else {
                            String itemName = item.getName();
                            if (itemName != null && !itemName.trim().isEmpty()) {
                                imageName = System.currentTimeMillis() + itemName.substring(itemName.lastIndexOf("."));
                                is = item.getInputStream();
                            }
                        }
                    }
                    txtProductID = (String) params.get("txtProductID");
                    if (txtProductID == null || txtProductID.trim().isEmpty() || !txtProductID.matches("\\d+")) {
                        redirect = true;
                    }
                    //
                    if (!redirect) {
                        if (imageName != null
                                && !allowExtensions.contains(
                                        imageName.substring(imageName.lastIndexOf(".")).toLowerCase())) {
                            error.setInvalidFileType("File Type Incorrect");
                            valid = false;
                        }
                        //
                        String name = (String) params.get("txtProductName");
                        if (name == null || name.trim().isEmpty()) {
                            error.setEmptyProductName("Product Name Cannot be Empty");
                            valid = false;
                        }
                        //
                        String description = (String) params.get("txtDescription");
                        if (description == null || description.trim().isEmpty()) {
                            error.setEmptyDescription("Product Description cannot be empty");
                            valid = false;
                        }
                        //
                        String categoryName = (String) params.get("txtCategory");
                        if (categoryName == null || categoryName.trim().isEmpty()) {
                            error.setEmptyCategory("You Must Select A Category");
                            valid = false;
                        }
                        //
                        String txtPrice = (String) params.get("txtPrice");
                        if (txtPrice == null
                                || txtPrice.trim().isEmpty()
                                || !txtPrice.matches("\\d+")
                                || Integer.parseInt(txtPrice) < 1000
                                || Integer.parseInt(txtPrice) > Integer.MAX_VALUE) {
                            error.setInvalidPrice("Invalid Price!!");
                            valid = false;
                        }
                        //
                        String txtQuantity = (String) params.get("txtQuantity");
                        if (txtQuantity == null || txtQuantity.trim().isEmpty()
                                || !txtQuantity.matches("\\d++") || Integer.parseInt(txtQuantity) < 0
                                || Integer.parseInt(txtQuantity) > Integer.MAX_VALUE) {
                            error.setInvalidQuantity("Invalid Quantity!");
                            valid = false;
                        }
                        //
                        String txtExprirationDate = (String) params.get("txtExpirationDate");
                        LocalDate localDate = null;
                        try {
                            localDate = LocalDate.parse(txtExprirationDate);
                            if (localDate.isEqual(LocalDate.now()) || localDate.isBefore(LocalDate.now())) {
                                error.setInvalidExpirationDate("Invalid Expriation Date");
                                valid = false;
                            }
                        } catch (DateTimeParseException ex) {
                            error.setInvalidExpirationDate("Invalid Expriation Date");
                            valid = false;
                        }
                        //
                        String txtCreateDate = (String) params.get("txtCreateDate");
                        LocalDateTime localDateTime = null;
                        try {
                            localDateTime = LocalDateTime.parse(txtCreateDate);
                            if (localDateTime.isAfter(localDate.atStartOfDay())) {
                                error.setInvalidCreateDate("Create Date Must be before Expiration Date");
                                valid = false;
                            }
                        } catch (DateTimeParseException ex) {
                            error.setInvalidCreateDate("Invalid Create Date");
                            valid = false;
                        }
                        //
                        if (valid) {
                            if(imageName!=null&&is!=null){
                                FileHelpers.writeImgToServerFile(imageName, is);
                            }
                            int productID = Integer.parseInt(txtProductID);
                            CategoriesDAO categoriesDAO = new CategoriesDAO();
                            ProductStatusDAO productStatusDAO = new ProductStatusDAO();
                            ProductsDAO productsDAO = new ProductsDAO();
                            boolean prvProduct = productsDAO.loadSelectedProduct(productID, true);
                            if (prvProduct) {
                                ProductsDTO loadedProduct = productsDAO.getSelectedProduct();
                                //
                                int categoryID = categoriesDAO.getCategoryIDFromName(categoryName);
                                //
                                int price = Integer.parseInt(txtPrice);
                                //
                                String statusName = (String) params.get("txtStatus");
                                int statusID = productStatusDAO.getStatusIDFromName(statusName);
                                //
                                int quantity = Integer.parseInt(txtQuantity);
                                //
                                if (imageName == null || imageName.trim().isEmpty()) {
                                    imageName = loadedProduct.getImage();
                                }
                                Timestamp createDate = Timestamp.valueOf(localDateTime);
                                Date expirationDate = Date.valueOf(localDate);
                                //
                                boolean rs = productsDAO.updateProduct(productID, name, imageName, description,
                                        price, quantity,createDate, expirationDate, categoryID, statusID);
                                if (rs) {
                                    FileHelpers.copyImgToContextFolder(context.getRealPath("/resources/imgs"), imageName);
                                    LogsDAO logsDAO = new LogsDAO();
                                    logsDAO.addLogs(productID, loginUser.getUsername());
                                    request.setAttribute("UPDATE_SUCCESS", "SUCCESS");
                                } else {
                                    request.setAttribute("UPDATE_FAILED", "FAILED");
                                }
                            }
                        }else{
                            request.setAttribute("ERROR", error);
                        }
                    }
                }
            }
        } catch (NamingException | FileUploadException | NumberFormatException | SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (!redirect) {
                request.getRequestDispatcher(Constants.LOAD_PRODUCT_CONTROLLER + "?txtProductID=" + txtProductID).forward(request, response);
            } else {
                response.sendRedirect(Constants.ADMIN_PAGE);
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
