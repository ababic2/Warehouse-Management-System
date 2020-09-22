package ba.unsa.etf.rpr.Model;

import java.time.LocalDate;

public class Shipment {
    private String name;
    private int price;
    private int quantity;
    private String orderDate = LocalDate.now().toString();
    private String currentUser2 = "AMINA";//currentUser.getUsername();

    public Shipment() {
    }

    public Shipment(String name, int price, int quantity, String orderDate, String currentUser2) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.currentUser2 = currentUser2;
    }

    public Shipment(String name, int price, int quantity, String orderDate) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public String getCurrentUser2() {
        return currentUser2;
    }

    public void setCurrentUser2(String currentUser2) {
        this.currentUser2 = currentUser2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
