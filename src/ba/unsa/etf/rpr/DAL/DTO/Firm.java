package ba.unsa.etf.rpr.DAL.DTO;

public class Firm {
    private int firmId;
    private String firmName;
    private String firmEMail;
    private String owner;
    private String firmPhone;
    private String firmAdress;

    private String username;
    private String password;

    public Firm() {
    }

    public Firm(int firmId, String firmName, String firmEMail,
                String owner, String firmPhone, String firmAdress,
                String username, String password) {
        this.firmId = firmId;
        this.firmName = firmName;
        this.firmEMail = firmEMail;
        this.owner = owner;
        this.firmPhone = firmPhone;
        this.firmAdress = firmAdress;
        this.username = username;
        this.password = password;
    }

    public int getFirmId() {
        return firmId;
    }

    public void setFirmId(int firmId) {
        this.firmId = firmId;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getFirmEMail() {
        return firmEMail;
    }

    public void setFirmEMail(String firmEMail) {
        this.firmEMail = firmEMail;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFirmPhone() {
        return firmPhone;
    }

    public void setFirmPhone(String firmPhone) {
        this.firmPhone = firmPhone;
    }

    public String getFirmAdress() {
        return firmAdress;
    }

    public void setFirmAdress(String firmAdress) {
        this.firmAdress = firmAdress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
