package nghiadhse140362.dtos;

import java.io.Serializable;

public class CategoriesDTO implements Serializable {
    private int categoryID;
    private String categoryName;

    public CategoriesDTO() {
    }

    public CategoriesDTO(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
