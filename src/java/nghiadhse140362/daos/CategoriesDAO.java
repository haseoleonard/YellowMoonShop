package nghiadhse140362.daos;

import nghiadhse140362.dtos.CategoriesDTO;
import nghiadhse140362.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAO implements Serializable {
    private Connection con;
    private PreparedStatement psm;
    private ResultSet rs;

    public CategoriesDAO() {
        this.con=null;
        this.psm=null;
        this.rs=null;
    }
    private void closeConnection() throws SQLException {
        if(this.rs!=null)this.rs.close();
        if(this.psm!=null)this.psm.close();
        if(this.con!=null)this.con.close();
    }

    private List<CategoriesDTO> categoriesDTOList;

    public List<CategoriesDTO> getCategoriesDTOList() {
        return categoriesDTOList;
    }
    
    public boolean createCategory(String categoryName) throws SQLException, NamingException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "insert into Categories(categoryName) values (?)";
                psm = con.prepareStatement(sql);
                psm.setString(1, categoryName);
                int result = psm.executeUpdate();
                if(result>0)return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }

    public int getCategoriesList() throws SQLException, NamingException {
        int result = 0;
        try {
            this.con = DBHelpers.makeConnection();
            if (this.con != null) {
                String sql = "select categoryID,categoryName " +
                        "from Categories";
                this.psm=con.prepareStatement(sql);
                this.rs=this.psm.executeQuery();
                while(this.rs.next()){
                    if(this.categoriesDTOList==null)this.categoriesDTOList=new ArrayList<>();
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getNString("categoryName");
                    this.categoriesDTOList.add(new CategoriesDTO(categoryID,categoryName));
                    result++;
                }
            }
        }finally {
            closeConnection();
        }
        return result;
    }

    public int getCategoryIDFromName(String categoryName) throws NamingException, SQLException{
        try{
            con = DBHelpers.makeConnection();
            if(con!=null){
                String sql = "select categoryID from Categories where categoryName=?";
                psm = con.prepareStatement(sql);
                psm.setString(1, categoryName);
                rs = psm.executeQuery();
                if(rs.next()){
                    return rs.getInt("categoryID");
                }
            }
        }finally{
            closeConnection();
        }
        return -1;
    }
}
