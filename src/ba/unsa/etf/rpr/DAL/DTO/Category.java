package ba.unsa.etf.rpr.DAL.DTO;

import javafx.beans.property.SimpleStringProperty;

public class Category {

    private int categoryId;
    private SimpleStringProperty categoryName;

    public Category() {
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = new SimpleStringProperty(categoryName);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public SimpleStringProperty categoryNameProperty() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    @Override
    public String toString() {
        return "ID : " + categoryId +
                "  NAME: " + categoryName;
    }
}
