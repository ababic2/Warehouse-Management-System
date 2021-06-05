# Warehouse-Management-System

OPIS APLIKACIJE

## YouTube Presentation

Presentation with the main functions of the user interface.
LINK HERE

## Implementation

MVC pattern was followed during implementation

### Database 

- Used SQLite database.
- There are four tables.
SLIKA
- All activities with database(queries) are in DAO classes.
- 
### Reports

- Reports are implemented using the Jaspersoft Studio
- If product is deleted, a report is created as a confirmation
- The admin can choose one of the following options on his panel:
 - Report for current employees, companies and products in the system / warehouse
 - Report for companies
 - Report for products in system
 SLIKA
 
 ### Localization
 
Resources include bundle-s, which enables translation of the application into two languages:
Bosnian and English.
English is selected as the default language.
Clicking on the button bs / en translates the application into the selected language.
