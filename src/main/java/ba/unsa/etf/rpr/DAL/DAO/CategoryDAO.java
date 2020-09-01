package ba.unsa.etf.rpr.DAL.DAO;

import ba.unsa.etf.rpr.DAL.DTO.Category;
import ba.unsa.etf.rpr.HelpModel.Reference;
import ba.unsa.etf.rpr.Interface.DAOInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO implements DAOInterface {

    private static CategoryDAO instance;

    private PreparedStatement countFirmsStatement;
    private PreparedStatement firmsStatement;

    private Connection conn = null;
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private Reference<Connection> connReference = new Reference<>(null);

    private PreparedStatement categoriesStatement;

    private CategoryDAO() {
        connectToBase(connReference);
        conn = connReference.get();
    }

    public static CategoryDAO getInstance() {
        if(instance == null) instance = new CategoryDAO();
        return instance;
    }

    public ObservableList<Category> getCategories() { return categories; }

    @Override
    public void addToList(PreparedStatement statement) {
        if(categories.size() > 0) {
            categories.clear();
        }
        try {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Category category = new Category(rs.getInt(1), rs.getString(2));
                categories.add(category);
            }
        } catch (SQLException exception) {}
    }

    @Override
    public void prepareStatements() throws SQLException {
        conn = connReference.get();
        categoriesStatement = conn.prepareStatement("select * from categories");
    }

    public ObservableList<Category> getInfoList() {
        addToList(categoriesStatement);
        return categories;
    }
}
