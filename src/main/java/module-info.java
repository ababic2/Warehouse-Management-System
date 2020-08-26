module ba.unsa.etf.rpr {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    exports ba.unsa.etf.rpr;
    exports ba.unsa.etf.rpr.Controllers;
    exports ba.unsa.etf.rpr.Interface;
    exports ba.unsa.etf.rpr.DAL.DAO;
    exports ba.unsa.etf.rpr.DAL.DTO;
    exports ba.unsa.etf.rpr.HelpModel;
    exports ba.unsa.etf.rpr.Exceptions;
    opens ba.unsa.etf.rpr.Controllers;
    opens ba.unsa.etf.rpr.Interface;
    opens ba.unsa.etf.rpr.DAL.DAO;
    opens ba.unsa.etf.rpr.DAL.DTO;
    opens ba.unsa.etf.rpr.HelpModel;
    opens ba.unsa.etf.rpr.Exceptions;
}