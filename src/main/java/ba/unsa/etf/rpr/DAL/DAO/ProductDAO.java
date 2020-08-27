package ba.unsa.etf.rpr.DAL.DAO;

import ba.unsa.etf.rpr.DAL.DTO.Category;
import ba.unsa.etf.rpr.DAL.DTO.Firm;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.HelpModel.Reference;
import ba.unsa.etf.rpr.Interface.DAOInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO implements DAOInterface {

    private static ProductDAO instance;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private Reference<Connection> connReference = new Reference<>(null);

    private PreparedStatement countProductsStatement;
    private PreparedStatement productsStatement;
    private PreparedStatement categoryStatement;
    private PreparedStatement countLowStockItemsStatement;
    private PreparedStatement lowStockItemsStatement;
    private PreparedStatement itemWithIDStatement;
    private PreparedStatement deleteItemWithIdStatement;
    private PreparedStatement firmStatement;
    private PreparedStatement increaseStockOfProductStatement;

    private Connection conn = null;

    public ProductDAO() {
        connectToBase(connReference);
        conn = connReference.get();
    }

    public static ProductDAO getInstance() {
        if(instance == null) instance = new ProductDAO();
        return instance;
    }

    public Integer getCount() {
        return count(countProductsStatement);
    }

    public ObservableList<Product> getInfoList() {
        addToList(productsStatement);
        return products;
    }

    private Category getCategory(int id) {
        ResultSet rs = null;
        try {
            categoryStatement.setInt(1,id);
            rs = categoryStatement.executeQuery();
            Category category = new Category(rs.getInt(1), rs.getString(2));
            return category;
        } catch (SQLException throwables) {
            return null;
        }
    }

    private Firm getFirm(int id) {
        ResultSet rs = null;
        try {

            firmStatement.setInt(1,id);
            rs = firmStatement.executeQuery();
            Firm firm = new Firm(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4),rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8));
            return firm;
        } catch (SQLException throwables) {
            return null;
        }
    }

    public Integer getCountLowStock() {
        return count(countLowStockItemsStatement);
    }

    public ObservableList<Product> getInfoLowStockList() {
        addToList(lowStockItemsStatement);
        return products;
    }

    public Product getItemWithId(int id) {
        ResultSet rs = null;
        try {
            itemWithIDStatement.setInt(1,id);
            rs = itemWithIDStatement.executeQuery();
            Product product = new Product(rs.getInt(1), rs.getString(2),
                        rs.getInt(3), rs.getInt(4), null, null);
                product.setCategory(getCategory(rs.getInt(5)));
                product.setFirm(getFirm(rs.getInt(6)));
                return product;
            } catch (SQLException e) {
                return  null;
        }
    }

    @Override
    public void prepareStatements() throws SQLException {
        conn = connReference.get();
        countProductsStatement = conn.prepareStatement("select count(product_id) from products");
        productsStatement = conn.prepareStatement("select * from products");
        categoryStatement = conn.prepareStatement("select * from categories where category_id = ?");
        countLowStockItemsStatement = conn.prepareStatement("select count(product_id) from products where stock <= 5");
        lowStockItemsStatement = conn.prepareStatement("select * from products where stock <= 5");
        itemWithIDStatement = conn.prepareStatement("select * from products where product_id = ?");
        deleteItemWithIdStatement = conn.prepareStatement("delete from products where product_id = ?");
        firmStatement = conn.prepareStatement("select * from firms where firm_id = ?");
        increaseStockOfProductStatement = conn.prepareStatement("update products set stock = ? where product_id = ?");
    }

    @Override
    public void addToList(PreparedStatement statement) {
        if(products.size() > 0) {
            products.clear();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery();
            while(rs.next()) {
                Product product = new Product(rs.getInt(1), rs.getString(2),
                        rs.getInt(3), rs.getInt(4), null, null);
                product.setCategory(getCategory(rs.getInt(5)));
                product.setFirm(getFirm(rs.getInt(6)));
                products.add(product);
            }

        } catch (SQLException throwables) {
        }

    }

    public void deleteProductWithId(int id) {
        try {
            deleteItemWithIdStatement.setInt(1,id);
            deleteItemWithIdStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void increaseStockOfProduct(int id, int newStock) {
        try {
            increaseStockOfProductStatement.setInt(1,newStock);
            increaseStockOfProductStatement.setInt(2, id);
            increaseStockOfProductStatement.executeUpdate();
        } catch (SQLException exception) {
        }
    }

    public int maxID() {
        try {
            PreparedStatement ps = conn.prepareStatement("select max(product_id) from products");
            ResultSet rs = ps.executeQuery();
            return rs.getInt(1);
        } catch (SQLException exception) {
            return -1;
        }
    }
}
