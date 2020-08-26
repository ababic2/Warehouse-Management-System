package ba.unsa.etf.rpr.DAL.DTO;

public class Request {
    private int requestId;
    private Firm firm;
    private Product product;

    public Request() {
    }

    public Request(int requestId, Firm firm, Product product) {
        this.requestId = requestId;
        this.firm = firm;
        this.product = product;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
