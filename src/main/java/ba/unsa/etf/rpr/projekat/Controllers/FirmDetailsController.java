package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAL.DTO.Firm;
import ba.unsa.etf.rpr.projekat.Interface.DetailsInterface;
import ba.unsa.etf.rpr.projekat.Model.FirmModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FirmDetailsController implements Initializable, DetailsInterface {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty owner;
    private SimpleStringProperty mail;
    private SimpleStringProperty phone;
    private SimpleStringProperty address;

    public CheckBox checkBox;

    public Button btnNext;
    public Button btnPrevious;
    public ToggleButton btnEdit;

    public TextField nameField;
    public TextField ownerField;
    public TextField mailField;
    public TextField phoneField;
    public TextField addressField;
    public TextField searchField;

    public Label idLabel;

    private int page = 0;

    private FirmModel model;

    public int editClick = 0;
    private ChangeListener nameListener;
    private ChangeListener ownerListener;
    private ChangeListener mailListener;
    private ChangeListener phoneListener;
    private ChangeListener addressListener;

    public FirmDetailsController(FirmModel model) {
        this();
        this.model = model;
    }

    public FirmDetailsController() {
        id = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty("");
        owner = new SimpleStringProperty("");
        mail = new SimpleStringProperty("");
        phone = new SimpleStringProperty("");
        address = new SimpleStringProperty("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindingFieldsWithProperties();
        model.changeCurrent(page);

        setInitialFirm();
        setBinding();
        setButtonsDisableTo();
        DetailsInterface.setButtonsNextPrev(model.getFirms().size(), btnNext, btnPrevious, page);
        btnPrevious.setDisable(true);
    }

    private void setButtonsDisableTo() {///lateeer

    }

    private void setFieldsDisableTo(boolean var) {
        nameField.setDisable(var);
        mailField.setDisable(var);
        ownerField.setDisable(var);
        phoneField.setDisable(var);
        addressField.setDisable(var);
    }

    private void setBinding() {
        model.currentFirmProperty().addListener((obs, oldValue, newValue) -> {
            id.setValue(model.getCurrentFirm().getFirmId());
            if(oldValue != null) {
                nameField.textProperty().unbindBidirectional(oldValue.firmNameProperty());
                mailField.textProperty().unbindBidirectional(oldValue.firmEMailProperty());
                ownerField.textProperty().unbindBidirectional(oldValue.ownerProperty());
                phoneField.textProperty().unbindBidirectional(oldValue.firmPhoneProperty());
                addressField.textProperty().unbindBidirectional(oldValue.firmAdressProperty());
            }
            nameField.textProperty().bindBidirectional(newValue.firmNameProperty());
            mailField.textProperty().bindBidirectional(newValue.firmEMailProperty());
            ownerField.textProperty().bindBidirectional(newValue.ownerProperty());
            phoneField.textProperty().bindBidirectional(newValue.firmPhoneProperty());
            addressField.textProperty().bindBidirectional(newValue.firmAdressProperty());
        });
    }

    private void setInitialFirm() {
        id.setValue(model.getCurrentFirm().getFirmId());
        owner.setValue(model.getCurrentFirm().getOwner());
        name.setValue(model.getCurrentFirm().getFirmName());
        mail.setValue(model.getCurrentFirm().getFirmEMail());
        phone.setValue(model.getCurrentFirm().getFirmPhone());
        address.setValue(model.getCurrentFirm().getFirmAdress());
    }

    private void bindingFieldsWithProperties() {
        nameField.textProperty().bindBidirectional(name);
        mailField.textProperty().bindBidirectional(mail);
        phoneField.textProperty().bindBidirectional(phone);
        addressField.textProperty().bindBidirectional(address);
        ownerField.textProperty().bindBidirectional(owner);
    }

    public void btnPreviousClicked(ActionEvent actionEvent) {
        goToPreviousPage();
    }

    private void goToPreviousPage() {
        page--;
        DetailsInterface.setButtonsNextPrev(model.getFirms().size(), btnNext, btnPrevious, page);
        model.changeCurrent(page);
    }

    public void btnNextClicked(ActionEvent actionEvent) {
        goToNextPage();
    }

    private void goToNextPage() {
        page++;
        DetailsInterface.setButtonsNextPrev(model.getFirms().size(), btnNext, btnPrevious, page);
        model.changeCurrent(page);
    }

    public void btnSearchClicked(ActionEvent actionEvent) {
        if (checkBox.isSelected()) {
            checkBoxSelected();
        } else {
            if (searchField.getText() != null) {
                List<Firm> list = model.getFirms().stream().filter(s -> s.getFirmName().equals(searchField.getText())).collect(Collectors.toList());
                if (list.size() == 0) {
                    setAlertWindow("No result !");
                } else {
                    model.setCurrentFirm(list.get(0));
                    findPageAfterSearch();
                }
            }
        }
    }

    private void checkBoxSelected() {
        if (searchField.getText() != null) {
            if (isNumeric(searchField.getText())) {
                int id = Integer.parseInt(searchField.getText());
                List<Firm> list = model.getFirms().stream().filter((emp) -> emp.getFirmId() == id).collect(Collectors.toList());
                if (list.size() == 0) {
                    setAlertWindow("No result !");
                } else {
                    model.setCurrentFirm(list.get(0));
                    findPageAfterSearch();
                }
            } else {
                setAlertWindow("ID must be numeric !");
            }
        }
    }

    private void findPageAfterSearch() {
        for(int i = 0; i < model.getFirms().size(); i++) {
            if(model.getCurrentFirm().compareTo(model.getFirms().get(i)) == 0) {
                page = i;
                break;
            }
        }
        DetailsInterface.setButtonsNextPrev(model.getFirms().size(), btnNext, btnPrevious, page);
    }

    public void btnEditClicked(ActionEvent actionEvent) {
        editClick++;

        if (editClick == 1) {
            setFieldsDisableTo(false);
            nameListener = (obs, oldValue, newValue) -> {
                name.setValue((String) newValue);
                model.getFirms().get(page).setFirmName(name.getValue());
            };
            nameField.textProperty().addListener(nameListener);

            mailListener = (obs, oldValue, newValue) -> {
                mail.setValue((String) newValue);
                model.getFirms().get(page).setFirmEMail(mail.getValue());
            };
            mailField.textProperty().addListener(mailListener);

            ownerListener = (obs, oldValue, newValue) -> {
                owner.setValue((String) newValue);
                model.getFirms().get(page).setOwner(owner.getValue());
            };
            ownerField.textProperty().addListener(ownerListener);


            phoneListener = (obs, oldValue, newValue) -> {
                phone.setValue((String) newValue);
                model.getFirms().get(page).setFirmPhone(phone.getValue());
            };
            phoneField.textProperty().addListener(phoneListener);

            addressListener = (obs, oldValue, newValue) -> {
                address.setValue((String) newValue);
                model.getFirms().get(page).setFirmAdress(address.getValue());
            };
            phoneField.textProperty().addListener(phoneListener);

        } else if (editClick == 2) {
            setFieldsDisableTo(true);
            model.changeCurrent(page);
            model.updateFirm();
            nameField.textProperty().removeListener(nameListener);
            mailField.textProperty().removeListener(mailListener);
            phoneField.textProperty().removeListener(phoneListener);
            addressField.textProperty().removeListener(addressListener);
            editClick = 0;
        }
    }

    public void btnDeleteClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(idLabel.getText());

        model.deleteUserWithId(id);
        model.getFirms().remove(page); //check heree!!!!!!!!!!!!!!!!!!!!!!

        if (page == 0) goToNextPage();
        else goToPreviousPage();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public String getOwner() {
        return owner.get();
    }

    public SimpleStringProperty ownerProperty() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public String getMail() {
        return mail.get();
    }

    public SimpleStringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}
