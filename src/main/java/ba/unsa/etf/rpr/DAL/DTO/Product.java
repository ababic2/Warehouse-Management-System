package ba.unsa.etf.rpr.DAL.DTO;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Product implements Comparable{

    private SimpleIntegerProperty productId = new SimpleIntegerProperty(0);
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleIntegerProperty price = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty stock = new SimpleIntegerProperty(0);
    private Category category;
    private Firm firm;


    public Product() {
    }

    public Product(int productId, String name, int price, int stock, Category category, Firm firm) {
        this.productId = new SimpleIntegerProperty(productId);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.category = category;
        this.firm = firm;
    }

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public int getProductId() {
        return productId.get();
    }

    public SimpleIntegerProperty productIdProperty() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getStock() {
        return stock.get();
    }

    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return  getName() + " " + getPrice() +  " " + LocalDate.now().toString() + "\n";
    }

    @Override
    public int compareTo(Object o) {
        Product product = (Product) o;
        if(this.getProductId()==product.getProductId() &&
                this.getName().equals(product.getName()) &&
                this.getPrice() == product.getPrice() &&
                this.getStock() == product.getStock()) return 0;
        return 1;
    }
}
