BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "accounts" (
	"account_id"	INTEGER,
	"user_id"	INTEGER,
	"username"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"access_level"	TEXT NOT NULL,
	PRIMARY KEY("account_id"),
	FOREIGN KEY("user_id") REFERENCES "users"("employee_id")
);
CREATE TABLE IF NOT EXISTS "request" (
	"request_id"	INTEGER,
	"firm_id"	INTEGER,
	"product_id"	INTEGER,
	PRIMARY KEY("request_id")
);
CREATE TABLE IF NOT EXISTS "categories" (
	"category_id"	INTEGER,
	"category_name"	TEXT,
	PRIMARY KEY("category_id")
);
CREATE TABLE IF NOT EXISTS "products" (
	"product_id"	INTEGER,
	"product_name"	TEXT,
	"price"	INTEGER,
	"stock"	INTEGER,
	"category_id"	INTEGER,
	PRIMARY KEY("product_id")
);
CREATE TABLE IF NOT EXISTS "firms" (
	"firm_id"	INTEGER,
	"firm_name"	TEXT,
	"firm_mail"	TEXT,
	"owner"	TEXT,
	"phone"	TEXT,
	"adress"	TEXT,
	PRIMARY KEY("firm_id")
);
CREATE TABLE IF NOT EXISTS "departments" (
	"department_id"	INTEGER,
	"department_name"	TEXT,
	PRIMARY KEY("department_id")
);
CREATE TABLE IF NOT EXISTS "users" (
	"employee_id"	INTEGER,
	"first_name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"e-mail"	TEXT,
	"salary"	INTEGER NOT NULL,
	"hire_date"	TEXT NOT NULL,
	"department_id"	INTEGER,
	PRIMARY KEY("employee_id")
);
INSERT INTO "categories" VALUES (1,'Food');
INSERT INTO "categories" VALUES (2,'Drink');
INSERT INTO "categories" VALUES (3,'Furniture');
INSERT INTO "categories" VALUES (4,'Clothes');
INSERT INTO "products" VALUES (2,'Twingo',5,10,1);
INSERT INTO "products" VALUES (3,'Snickers',2,4,1);
INSERT INTO "products" VALUES (4,'Sinalco',2,5,2);
INSERT INTO "products" VALUES (5,'Sofa',200,15,3);
INSERT INTO "products" VALUES (6,'Rug',100,2,3);
INSERT INTO "products" VALUES (7,'DeShirt',25,12,4);
INSERT INTO "firms" VALUES (1,'Viva','viva@gmail.com','Justin Hawk','030335669','Carson City, GB');
INSERT INTO "firms" VALUES (2,'LaVidia','laVidia@gmail.com','Sandra Baldwin','032556887','Matthews, US');
INSERT INTO "firms" VALUES (3,'BMarkets','bb@gmail.com','Sarah Gery','032555412','Brownsville, US');
INSERT INTO "firms" VALUES (4,'Saphire','saphire@gmail.com','Hamza Oratok','554896321','Vezneciler, TURKEY');
INSERT INTO "firms" VALUES (5,'Mahala','mahala123@gmail.com','Himzo Husic','065548975','Sarajevo, BIH');
INSERT INTO "departments" VALUES (1,'Receiver');
INSERT INTO "departments" VALUES (2,'Control');
INSERT INTO "departments" VALUES (3,'Safety');
INSERT INTO "departments" VALUES (4,'Information Technology');
INSERT INTO "users" VALUES (1,'Sarah','Rodrigues','sarah@gmail.com',500,'22-05-2007',1);
INSERT INTO "users" VALUES (2,'Terri','Cabrera','terrisia@gmail.com',500,'19-04-2007',1);
INSERT INTO "users" VALUES (3,'Flynn','Rosas','flynn@gmail.com',600,'10-02-2006',2);
INSERT INTO "users" VALUES (4,'River','Proctor','river@gmail.com',800,'10-01-2000',3);
INSERT INTO "users" VALUES (5,'Aurelia','Kane','aurelia@gmail.com',500,'10-05-2008',2);
INSERT INTO "users" VALUES (6,'Agatha','Buck','agatha@gmail.com',400,'01-02-2010',4);
INSERT INTO "users" VALUES (7,'Lester','Munro','munro@gmail.com',400,'02-02-2009',4);
INSERT INTO "users" VALUES (8,'Drake','Lopez','drake@gmail.com',750,'01-01-2010',3);
INSERT INTO "users" VALUES (9,'Chantelle','Hawes','cahntelle@gmail.com',800,'05-09-2012',3);
INSERT INTO "users" VALUES (10,'Shawn','Mendes','shawn@gmail.com',550,'05-05-2015',1);
COMMIT;
