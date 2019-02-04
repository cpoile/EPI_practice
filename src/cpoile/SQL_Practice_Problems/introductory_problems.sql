-- 1
USE northwind;

SELECT *
  FROM shippers;

-- 2
SELECT CategoryName, Description
  FROM Categories;

-- 3
SELECT FirstName, LastName, HireDate
  FROM Employees
  WHERE Title = 'Sales Representative';

-- 4
SELECT FirstName, LastName, HireDate
  FROM Employees
  WHERE Title = 'Sales Representative'
    AND Country = 'USA';

-- 5
SELECT *
  FROM Orders
  WHERE EmployeeID = 5;

-- 6
SELECT SupplierID, ContactName, ContactTitle
  FROM Suppliers
  WHERE ContactTitle != 'Marketing Manager';

-- 7
SELECT ProductID, ProductName
  FROM Products
  WHERE ProductName LIKE '%queso%';

-- 8
SELECT OrderID, CustomerID, ShipCountry
  FROM Orders
  WHERE ShipCountry = 'France'
     OR ShipCountry = 'Belgium';

-- 9
SELECT OrderID, CustomerID, ShipCountry
  FROM Orders
  WHERE ShipCountry IN ('Brazil', 'Mexico', 'Argentina', 'Venezuela');

-- 10
SELECT FirstName, LastName, Title, BirthDate
  FROM EMPLOYEES
  ORDER BY BirthDate;

-- 11
SELECT FirstName, LastName, Title, date(BirthDate) as DateOnlyBirthDate
  FROM EMPLOYEES
  ORDER BY BirthDate;

-- 12
SELECT FirstName, LastName, CONCAT(FirstName, ' ', LastName) as Fullname
  FROM Employees;

-- 13
SELECT OrderID,
       ProductID,
       ROUND(UnitPrice, 2)            as UnitPrice,
       Quantity,
       Round(UnitPrice * Quantity, 2) as TotalPrice
  FROM `Order Details`;

-- 14
SELECT COUNT(*) as TotalCustomers
  FROM Customers;

-- 15
SELECT MIN(OrderDate) as FirstOrder
  FROM Orders;

-- 16
SELECT DISTINCT Country
  FROM Customers
  WHERE Country IS NOT NULL;

SELECT Country
  FROM Customers
  WHERE Country IS NOT NULL
  GROUP BY Country;

-- 17
SELECT ContactTitle, COUNT(*) as TotalContactTitle
  FROM Customers
  GROUP BY ContactTitle
  ORDER BY TotalContactTitle DESC;

-- 18
SELECT ProductID, ProductName, CompanyName
  FROM Products as p
         INNER JOIN Suppliers as s
                    ON p.SupplierID = s.SupplierID
  ORDER BY ProductID;

-- 19
SELECT OrderID, DATE(OrderDate) as OrderDate, CompanyName as Shipper
  FROM Orders as o
         JOIN Shippers as s ON o.ShipVia = s.ShipperID
  WHERE OrderID < 10300
  ORDER BY OrderID;

-- 20
SELECT CategoryName, COUNT(*) as TotalProducts
  FROM Products as p
         JOIN Categories as c ON p.CategoryID = c.CategoryID
  GROUP BY CategoryName
  ORDER BY TotalProducts DESC;