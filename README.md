# Warehouse-Management-System

This is simple and pretty much limited  application that allows easy organization, product tracking and reporting.
The user of the application can have one of three access levels which separates the user's jobs.
* Companies that use the application can only monitor the status of their products, and modify their profile.
* Employees are those who are responsible for the condition of the product.
* The administrator is the one who is in charge of managing the accounts and organizing the warehouse. The ability to add a new employee, firm, category or department has only the admin and also he can get an insight into the list of application users and current products in the system.

![ScreenShot](/imagesOfApp/dashboard.png)

![ScreenShot](/imagesOfApp/dashboard2.png)

## Access data

![ScreenShot](/imagesOfApp/login.png)


## Demo

Presentation with the main functions of the user interface.
LINK HERE

## Implementation

MVC pattern was followed during implementation

### Database 

- Used SQLite database.
- There are four tables.

![ScreenShot](/imagesOfApp/dashboard.png)

- All activities with database(queries) are in DAO classes.

### Reports

- Reports are implemented using the Jaspersoft Studio
- If product is deleted, a report is created as a confirmation
- The admin can choose one of the following options on his panel:
 - Report for current employees, companies and products in the system / warehouse
 - Report for companies
 - Report for products in system
 
 ![ScreenShot](/imagesOfApp/adminaPanel.png)
 
 ![ScreenShot](/imagesOfApp/reportItems.png)
 
 ### Localization
 
Resources include bundle-s, which enables translation of the application into two languages:
Bosnian and English.
English is selected as the default language.
Clicking on the button bs / en translates the application into the selected language.

![ScreenShot](/imagesOfApp/lokalizacija.png)
