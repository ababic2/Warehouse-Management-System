package ba.unsa.etf.rpr.Model;

import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.HelpModel.Account;

public class LogInModel {
    private Account account;
    private Employee currentEmployee;
    private UserDAO userDAO = UserDAO.getInstance();

    public boolean is_Valid_Password(String password) {

        if (password.length() > 0 && password.length() < 8 ) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }
        return (charCount >= 2 && numCount >= 2);
    }

    private boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    private boolean is_Numeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    public boolean lookForUsernameInBase(String username) {
        if(userDAO.getPasswordForUsername(username) == null) return false;
        return true;
    }

    public boolean lookForPassInBaseForUsername(String username, String password) {
        String pass = userDAO.getPasswordForUsername(username).getPassword();
        if (!pass.equals(password)) {
            return false;
        }
        return true;
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account setUpAccountForUser(String username) {
        account = userDAO.getPasswordForUsername(username);
        return account;
    }
}
