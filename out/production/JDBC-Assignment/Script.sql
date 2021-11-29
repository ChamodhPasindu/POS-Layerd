DROP DATABASE IF EXISTS Kade;
CREATE DATABASE IF NOT EXISTS Kade;
SHOW DATABASES;
USE Kade;

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    CustId VARCHAR (6),
    CustTitle VARCHAR (5),
    Custname VARCHAR (30) NOT NULL DEFAULT 'Unknown',
    CustAddress VARCHAR (30),
    City VARCHAR (20),
    province VARCHAR (20),
    postalCOde VARCHAR (9),
    CONSTRAINT PRIMARY KEY (CustId)
    );
SHOW TABLES;
DESCRIBE Customer;

//----------------

DROP TABLE IF EXISTS Orders;
CREATE TABLE IF NOT EXISTS Orders(
    OrderId VARCHAR (6),
    OrderDate DATE,
    CustId VARCHAR (6),
    CONSTRAINT PRIMARY KEY (OrderId),
    CONSTRAINT FOREIGN KEY (CustId) REFERENCES Customer (CustId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Orders;

//---------------

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    ItemCode VARCHAR (6),
    Description VARCHAR (50),
    PackSize VARCHAR (20),
    UnitPrice DECIMAL (6,2) DEFAULT 0.00,
    QtyOnHand INT (5) DEFAULT 0,
    CONSTRAINT PRIMARY KEY (ItemCode)
    );
SHOW TABLES;
DESCRIBE Item;

//----------------

DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
    OrderId VARCHAR (6),
    ItemCode VARCHAR (6),
    OrderQty INT (11) DEFAULT 0,
    Discount DECIMAL (6,2) DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (OrderId,ItemCode),
    CONSTRAINT FOREIGN KEY (OrderId) REFERENCES Orders (OrderId) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (ItemCode) REFERENCES Item (ItemCode) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE OrderDetail;
)

//------------------------------------------------------
ALTER TABLE Customer RENAME COLUMN postalCOde TO postalCode;
ALTER TABLE Item ADD Discount DECIMAL (6,2) DEFAULT 0.00;

ALTER TABLE OrderDetail ADD Cost DECIMAL (6,2) DEFAULT 0.00;
SELECT * FROM Customer;

ALTER TABLE Item
DROP COLUMN Discount;

ALTER TABLE Orders
ADD COLUMN Cost DECIMAL (6,2);