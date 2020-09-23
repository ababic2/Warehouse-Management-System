BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "products" (
	"product_id"	INTEGER,
	"product_name"	TEXT,
	"price"	INTEGER,
	"stock"	INTEGER,
	"category_id"	INTEGER,
	"firm_id"	INTEGER,
	PRIMARY KEY("product_id")
);
CREATE TABLE IF NOT EXISTS "employees" (
	"employee_id"	INTEGER,
	"first_name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"username"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"access_level"	TEXT,
	"e_mail"	TEXT,
	"salary"	INTEGER NOT NULL,
	"hire_date"	TEXT NOT NULL,
	"department_id"	INTEGER,
	PRIMARY KEY("employee_id")
);
CREATE TABLE IF NOT EXISTS "firms" (
	"firm_id"	INTEGER,
	"firm_name"	TEXT,
	"firm_mail"	TEXT,
	"owner"	TEXT,
	"phone"	TEXT,
	"adress"	TEXT,
	"username"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"access_level"	TEXT,
	PRIMARY KEY("firm_id")
);
CREATE TABLE IF NOT EXISTS "categories" (
	"category_id"	INTEGER,
	"category_name"	TEXT,
	PRIMARY KEY("category_id")
);
CREATE TABLE IF NOT EXISTS "departments" (
	"department_id"	INTEGER,
	"department_name"	TEXT,
	PRIMARY KEY("department_id")
);
INSERT INTO "products" VALUES (2,'Twingo',5,10,1,1);
INSERT INTO "products" VALUES (4,'Sinalco',2,5,2,2);
INSERT INTO "products" VALUES (5,'Sofa',200,15,3,3);
INSERT INTO "products" VALUES (6,'Rug',100,2,3,4);
INSERT INTO "employees" VALUES (1,'Sarah','Rodrigues','sarah','hello12345','admin','sarah@gmail.com',500,'22-05-2007',1);
INSERT INTO "employees" VALUES (2,'Terri','Cabrera','terri','hello12345','employee','terrisia@gmail.com',500,'19-04-2007',1);
INSERT INTO "employees" VALUES (3,'Flynn','Rosas','flynn','hello12345','employee','flynn@gmail.com',600,'10-02-2006',2);
INSERT INTO "employees" VALUES (4,'River','Proctor','river','hello12345','employee','river@gmail.com',800,'10-01-2000',3);
INSERT INTO "employees" VALUES (5,'Aurelia','Kane','aurelia','hello12345','employee','aurelia@gmail.com',500,'10-05-2008',2);
INSERT INTO "employees" VALUES (6,'Agatha','Buck','agatha','hello12345','employee','agatha@gmail.com',400,'01-02-2010',4);
INSERT INTO "employees" VALUES (7,'Lester','Munro','lester','hello12345','employee','munro@gmail.com',400,'02-02-2009',4);
INSERT INTO "employees" VALUES (8,'Drake','Lopez','drake','hello12345','employee','drake@gmail.com',750,'01-01-2010',3);
INSERT INTO "employees" VALUES (9,'Chantelle','Hawes','chantelle','hello12345','employee','cahntelle@gmail.com',800,'05-09-2012',3);
INSERT INTO "employees" VALUES (10,'Shawn','Mendes','shawn','hello12345','employee','shawn@gmail.com',550,'05-05-2015',1);
INSERT INTO "firms" VALUES (1,'Viva','viva@gmail.com','Justin Hawk','030335669','Carson City, GB','viva','hello12345','firm');
INSERT INTO "firms" VALUES (2,'LaVidia','laVidia@gmail.com','Sandra Baldwin','032556887','Matthews, US','lavidia','hello12345','firm');
INSERT INTO "firms" VALUES (3,'BMarkets','bb@gmail.com','Sarah Gery','032555412','Brownsville, US','bmarkets','hello12345','firm');
INSERT INTO "firms" VALUES (4,'Saphire','saphire@gmail.com','Hamza Oratok','554896321','Vezneciler, TURKEY','saphire','hello12345','firm');
INSERT INTO "firms" VALUES (5,'Mahala','mahala123@gmail.com','Himzo Husic','065548975','Sarajevo, BIH','mahala','hello12345','firm');
INSERT INTO "categories" VALUES (1,'Food');
INSERT INTO "categories" VALUES (2,'Drink');
INSERT INTO "categories" VALUES (3,'Furniture');
INSERT INTO "categories" VALUES (4,'Clothes');
INSERT INTO "departments" VALUES (1,'Receiver');
INSERT INTO "departments" VALUES (2,'Control');
INSERT INTO "departments" VALUES (3,'Safety');
INSERT INTO "departments" VALUES (4,'Information Technology');
COMMIT;