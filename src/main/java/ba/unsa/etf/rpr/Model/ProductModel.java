package ba.unsa.etf.rpr.Model;

import ba.unsa.etf.rpr.DAL.DAO.CategoryDAO;
import ba.unsa.etf.rpr.DAL.DAO.FirmDAO;
import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DTO.Category;
import ba.unsa.etf.rpr.DAL.DTO.Firm;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class ProductModel {

    private final ProductDAO productDAO = ProductDAO.getInstance();
    private final FirmDAO firmDAO = FirmDAO.getInstance();
    private final CategoryDAO categoryDAO = CategoryDAO.getInstance();
    private Product addedProduct;
    private ObservableList<Product> products = productDAO.getInfoList();
    private ObservableList<Firm> firms = firmDAO.getInfoList();
    private ObservableList<Category> categories = categoryDAO.getInfoList();

    private final ObjectProperty<Product> currentProduct = new SimpleObjectProperty<>();

    public void refreshListOfProducts(){ productDAO.getInfoList();}

    public ObservableList<Firm> getFirms() { return firms; }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public int getSizeOfList() { return products.size();}

    // When searching, listing...change current user/item
    public void changeCurrent(int page) {
        currentProduct.set(products.get(page));
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public void addProductToList(Product product) {
        products.add(product);
    }

    public Product getCurrentProduct() {
        return currentProduct.get();
    }

    public ObjectProperty<Product> currentProductProperty() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct.set(currentProduct);
    }

    public void deleteProductWithId(int id) {
        productDAO.deleteProductWithId(id);
    }

    public void removeProductOnPage(int page) {
        products.remove(page);
    }

    public void updateProductInBase() {
        productDAO.updateProduct(currentProduct.getValue().getProductId(), currentProduct.getValue().getName(),
                currentProduct.getValue().getPrice(), currentProduct.getValue().getStock());
    }

    public void changeStock(int id, int newStock) {
        productDAO.changeStock(id, newStock);
    }

    public int getMaxId() {
        return productDAO.getMaxId();
    }

    public void addProductToBase(Product product) {
        productDAO.addProduct(product);
    }

    public Product getAddedProduct() {
        return addedProduct;
    }

    public void setAddedProduct(Product addedProduct) {
        this.addedProduct = addedProduct;
    }

    public boolean checkIfProductAlreadyExist(Product product) {
        return productDAO.checkIfAlreadyExist(product);
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }
}
