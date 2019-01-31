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

