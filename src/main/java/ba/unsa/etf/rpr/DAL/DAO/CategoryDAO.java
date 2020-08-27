package ba.unsa.etf.rpr.DAL.DAO;

import ba.unsa.etf.rpr.DAL.DTO.Category;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.Interface.DAOInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO implements DAOInterface {

    private PreparedStatement ps;
    private Connection connection = null;
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public ObservableList<Category>getCategories{
            addToList(ps);
        ObservableList<Category> categories1 = categories;
        return categories1;
    }

    @Override
    public void addToList(PreparedStatement statement) {
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
            while(rs.next()) {
                Category category = new Category(rs.getInt(1), rs.getString(2));
                categories.add(category);
            }
        } catch (SQLException exception) {
        }
    }

    @Override
    public void prepareStatements() throws SQLException {
        ps = connection.prepareStatement("select * from categories");
    }
}
