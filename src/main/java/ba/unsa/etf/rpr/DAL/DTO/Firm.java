package ba.unsa.etf.rpr.DAL.DTO;

import javafx.beans.property.SimpleStringProperty;

public class Firm extends User implements Comparable<Firm>{
    private int firmId;
    private SimpleStringProperty firmName;
    private SimpleStringProperty firmEMail;
    private SimpleStringProperty owner;
    private SimpleStringProperty firmPhone;
    private SimpleStringProperty firmAdress;

    public Firm() {
    }

    public Firm(int firmId, String firmName, String firmEMail,
                String owner, String firmPhone, String firmAdress,
                String username, String password) {
        this.firmId = firmId;
        this.firmName = new SimpleStringProperty(firmName);
        this.firmEMail = new SimpleStringProperty(firmEMail);
        this.owner = new SimpleStringProperty(owner);
        this.firmPhone = new SimpleStringProperty(firmPhone);
        this.firmAdress = new SimpleStringProperty(firmAdress);
        setInConstructor(username, password, "Firm");
    }

    public int getFirmId() {
        return firmId;
    }

    public void setFirmId(int firmId) {
        this.firmId = firmId;
    }

    public String getFirmName() {
        return firmName.get();
    }

    public SimpleStringProperty firmNameProperty() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName.set(firmName);
    }

    public String getFirmEMail() {
        return firmEMail.get();
    }

    public SimpleStringProperty firmEMailProperty() {
        return firmEMail;
    }

    public void setFirmEMail(String firmEMail) {
        this.firmEMail.set(firmEMail);
    }

    public String getOwner() {
        return owner.get();
    }

    public SimpleStringProperty ownerProperty() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public String getFirmPhone() {
        return firmPhone.get();
    }

    public SimpleStringProperty firmPhoneProperty() {
        return firmPhone;
    }

    public void setFirmPhone(String firmPhone) {
        this.firmPhone.set(firmPhone);
    }

    public String getFirmAdress() {
        return firmAdress.get();
    }

    public SimpleStringProperty firmAdressProperty() {
        return firmAdress;
    }

    public void setFirmAdress(String firmAdress) {
        this.firmAdress.set(firmAdress);
    }

    @Override
    public String toString() {
        return getFirmId() + ", " + getFirmName();
    }

    @Override
    public int compareTo(Firm o) {
        Firm firm = (Firm) o;
        if(this.getFirmName().equals(firm.getFirmName()) &&
                this.getOwner().equals(firm.getOwner()) &&
                this.getFirmId() == firm.getFirmId())return  0;
        return -1;
    }
}
