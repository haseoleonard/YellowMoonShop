/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.daos;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nghiadhse140362.dtos.ProductsDTO;
import nghiadhse140362.utils.Constants;
import nghiadhse140362.utils.DBHelpers;

import javax.naming.NamingException;
import nghiadhse140362.cart.CartItemObject;
import nghiadhse140362.dtos.OrderDetailsDTO;

/**
 *
 * @author haseo
 */
public class ProductsDAO implements Serializable {

    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public ProductsDAO() {
        this.con = null;
        this.psm = null;
        this.rs = null;
    }

    private void closeConnection() throws SQLException {
        if (this.rs != null) {
            this.rs.close();
        }
        if (this.psm != null) {
            this.psm.close();
        }
        if (this.con != null) {
            this.con.close();
        }
    }

    private ProductsDTO selectedProduct;
    
    public int getLatestProductID() throws SQLException, NamingException{
        try{
            con=DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select MAX(productID) as latestProductID from Products";
                psm = con.prepareStatement(sql);
                rs = psm.executeQuery();
                if(rs.next())return rs.getInt("latestProductID");
            }
        }finally{
            closeConnection();
        }
        return -1;
    }

    public ProductsDTO getSelectedProduct() {
        return selectedProduct;
    }

    public boolean loadSelectedProduct(int productID, boolean getAll) throws NamingException, SQLException {
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "select productName,description,image,price,quantity,createDate,expirationDate,categoryID,statusID "
                        + "from Products "
                        + "where productID=?";
                psm = con.prepareStatement(sql);
                psm.setInt(1, productID);
                rs = psm.executeQuery();
                if (rs.next()) {
                    String productName = rs.getNString("productName");
                    String image = rs.getString("image");
                    String description = rs.getNString("description");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    this.selectedProduct = new ProductsDTO(productID, productName, image, description, price, quantity, categoryID, createDate, expirationDate, statusID);
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public int getPriceByID(int productID) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select price from Products where productID=? and statusID=1 and quantity>0 and expirationDate>GETDATE()";
                psm = con.prepareStatement(sql);
                psm.setInt(1, productID);
                rs = psm.executeQuery();
                if(rs.next()){
                    return rs.getInt("price");
                }
            }
        }finally{
            closeConnection();
        }
        return -1;
    }
    
    public String getNameByID(int productID) throws NamingException, SQLException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select productName from Products where productID=?";
                psm = con.prepareStatement(sql);
                psm.setInt(1, productID);
                rs = psm.executeQuery();
                if(rs.next()){
                    return rs.getNString("productName");
                }
            }
        }finally{
            closeConnection();
        }
        return null;
    }
    
    public int getQuantityByID(int productID) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select quantity from Products where productID=? and statusID=1";
                psm = con.prepareStatement(sql);
                psm.setInt(1, productID);
                rs = psm.executeQuery();
                if(rs.next()){
                    return rs.getInt("quantity");
                }
            }
        }finally{
            closeConnection();
        }
        return -1;
    }

    public int getNumberOfProductPage(String searchValue, int minPrice, int maxPrice, String categoryID, boolean getAll) throws NamingException, SQLException {
        int total = 0;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql;
                if (!getAll) {
                    sql = "select count(productID) as numOfProducts "
                            + "from Products "
                            + "where quantity>0 and statusID=1 "
                            + "and expirationDate>GETDATE()"
                            + "and (productName like ? or description like ?) "
                            + "and price>=? and price<=? and categoryID like ? ";
                    psm = con.prepareStatement(sql);
                    psm.setNString(1, "%" + searchValue + "%");
                    psm.setNString(2, "%" + searchValue + "%");
                    psm.setInt(3, minPrice);
                    psm.setInt(4, maxPrice);
                    psm.setString(5, categoryID);
                } else {
                    sql = "select count(productID) as numOfProducts "
                            + "from Products "
                            + "where productName like ? or description like ?";
                    psm = con.prepareStatement(sql);
                    psm.setNString(1, "%" + searchValue + "%");
                    psm.setNString(2, "%" + searchValue + "%");
                }
                rs = psm.executeQuery();
                if (rs.next()) {
                    total = rs.getInt("numOfProducts");
                }
            }
        } finally {
            closeConnection();
        }
        return (int) Math.ceil(total / (float)Constants.PRODUCT_PER_PAGE);
    }

    private int minPrice;

    public int getMinPrice() {
        return minPrice;
    }

    private int maxPrice;

    public int getMaxPrice() {
        return maxPrice;
    }

    public boolean getMinMaxPrice() throws NamingException, SQLException {
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "select MIN(price) as minPrice,MAX(price) as maxPrice from Products";
                psm = con.prepareStatement(sql);
                rs = psm.executeQuery();
                if (rs.next()) {
                    minPrice = rs.getInt("minPrice");
                    maxPrice = rs.getInt("maxPrice");
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    List<ProductsDTO> productList;

    public List<ProductsDTO> getProductList() {
        return productList;
    }

    public int loadProduct(int page, boolean getAll) throws SQLException, NamingException {
        int total = 0;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql;
                if (!getAll) {
                    sql = "select productID,productName,image,description,price,quantity,createDate,expirationDate,categoryID "
                            + "from Products "
                            + "where statusID=1 and quantity>0 "
                            + "and expirationDate>GETDATE()"
                            + "Order by createDate desc "
                            + "OFFSET ? rows "
                            + "FETCH NEXT ? ROWS ONLY";
                } else {
                    sql = "select productID,productName,image,description,price,quantity,createDate,expirationDate,categoryID "
                            + "from Products "
                            + "Order by createDate desc "
                            + "OFFSET ? rows "
                            + "FETCH NEXT ? ROWS ONLY";
                }
                psm = con.prepareStatement(sql);
                psm.setInt(1, page - 1);
                psm.setInt(2, Constants.PRODUCT_PER_PAGE);
                rs = psm.executeQuery();
                while (rs.next()) {
                    if (this.productList == null) {
                        this.productList = new ArrayList<>();
                    }
                    int productID = rs.getInt("productID");
                    String productName = rs.getNString("productName");
                    String image = rs.getString("image");
                    String description = rs.getNString("description");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    this.productList.add(new ProductsDTO(productID, productName, image, description, price, quantity, categoryID, createDate, expirationDate, statusID));
                    total++;
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public int searchProduct(String searchValue, int minPrice, int maxPrice, String incategoryID, int page, boolean getAll) throws SQLException, NamingException {
        int total = 0;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                if (!getAll) {                    
                    String sql = "select productID,productName,image,description,price,quantity,createDate,expirationDate,categoryID,statusID "
                            + "from Products "
                            + "where (productName like ? or description like ?) "
                            + "and price>=? and price<=? and categoryID like ? "
                            + "and statusID=1 and quantity>0 "
                            + "and expirationDate>GETDATE()"
                            + "Order By createDate desc "
                            + "OFFSET ? rows "
                            + "FETCH NEXT ? ROWS ONLY";
                    psm = con.prepareStatement(sql);
                    psm.setNString(1, "%" + searchValue + "%");
                    psm.setNString(2, "%" + searchValue + "%");
                    psm.setInt(3, minPrice);
                    psm.setInt(4, maxPrice);
                    psm.setString(5, incategoryID);
                    psm.setInt(6, (page - 1)*Constants.PRODUCT_PER_PAGE);
                    psm.setInt(7, Constants.PRODUCT_PER_PAGE);
                } else {
                    String sql = "select productID,productName,image,description,price,quantity,createDate,expirationDate,categoryID,statusID "
                            + "from Products "
                            + "where productName like ? or description like ? "
                            + "Order By createDate desc "
                            + "OFFSET ? rows "
                            + "FETCH NEXT ? ROWS ONLY";
                    psm = con.prepareStatement(sql);
                    psm.setNString(1, "%" + searchValue + "%");
                    psm.setNString(2, "%" + searchValue + "%");
                    psm.setInt(3, page - 1);
                    psm.setInt(4, Constants.PRODUCT_PER_PAGE);
                }
                rs = psm.executeQuery();
                while (rs.next()) {
                    if (this.productList == null) {
                        this.productList = new ArrayList<>();
                    }
                    int productID = rs.getInt("productID");
                    String productName = rs.getNString("productName");
                    String image = rs.getString("image");
                    String description = rs.getNString("description");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    this.productList.add(new ProductsDTO(productID, productName, image, description, price, quantity, categoryID, createDate, expirationDate, statusID));
                    total++;
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public boolean createProduct(String productName, String image, String description, int price,Timestamp createDate ,Date expirationDate, int categoryID) throws SQLException, NamingException {
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "insert into Products(productName,image,description,price,createDate,expirationDate,categoryID) "
                        + "values(?,?,?,?,?,?,?)";
                psm = con.prepareStatement(sql);
                psm.setNString(1, productName);
                psm.setString(2, image);
                psm.setNString(3, description);
                psm.setInt(4, price);
                psm.setTimestamp(5, createDate);
                psm.setDate(6, expirationDate);
                psm.setInt(7, categoryID);
                int result = psm.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean updateProduct(int productID, String productName, String image, String description, int price, int quantity,Timestamp createDate, Date expirationDate, int categoryID, int statusID)
            throws SQLException, NamingException {
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "update Products "
                        + "set productName=?,image=?,description=?,price=?,"
                        + "quantity=?,createDate=?,expirationDate=?,categoryID=?,statusID=? "
                        + "where productID=?";
                psm = con.prepareStatement(sql);
                psm.setNString(1, productName);
                psm.setString(2, image);
                psm.setNString(3, description);
                psm.setInt(4, price);
                psm.setInt(5, quantity);
                psm.setTimestamp(6, createDate);
                psm.setDate(7, expirationDate);
                psm.setInt(8, categoryID);
                psm.setInt(9, statusID);
                psm.setInt(10, productID);
                int result = psm.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public boolean decreaseQuantityByID(Map<Integer,CartItemObject> items) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                con.setAutoCommit(false);
                String sql = "update Products set quantity=quantity-? where productID=?";
                psm = con.prepareStatement(sql);
                for (Map.Entry<Integer, CartItemObject> entry : items.entrySet()) {
                    psm.setInt(1, entry.getValue().getQuantity());
                    psm.setInt(2, entry.getKey());
                    psm.addBatch();
                    psm.clearParameters();
                }
                int[] executeBatch = psm.executeBatch();
                for(int i : executeBatch){
                    if(i==PreparedStatement.EXECUTE_FAILED)return false;
                }
                con.commit();
                return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }
    
    public boolean decreaseQuantityByID(List<OrderDetailsDTO> items) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                con.setAutoCommit(false);
                String sql = "update Products set quantity=quantity-? where productID=?";
                psm = con.prepareStatement(sql);
                for(OrderDetailsDTO detail: items){
                    psm.setInt(1, detail.getQuantity());
                    psm.setInt(2, detail.getProductID());
                    psm.addBatch();
                    psm.clearParameters();
                }
                int[] executeBatch = psm.executeBatch();
                for(int i : executeBatch){
                    if(i==PreparedStatement.EXECUTE_FAILED)return false;
                }
                con.commit();
                return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }
}
