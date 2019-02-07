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

-- 21
SELECT Country, City, COUNT(*) as TotalCustomer
  FROM Customers
  GROUP BY Country, City
  ORDER BY TOtalCustomer DESC;

-- 22
SELECT ProductID, ProductName, UnitsInStock, ReorderLevel
  FROM Products
  WHERE UnitsInStock < ReorderLevel
  ORDER BY ProductID;

-- 23
SELECT ProductID, ProductName, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued
  FROM Products
  WHERE UnitsInStock + UnitsOnOrder <= ReorderLevel
    AND Discontinued = false
  ORDER BY ProductID;

-- 24
SELECT CustomerID, CompanyName, Region
  FROM Customers
  ORDER BY ISNULL(Region), Region, CustomerID;

-- 25
SELECT ShipCountry, ROUND(AVG(Freight), 2) as AverageFreight
  FROM Orders
  GROUP BY ShipCountry
  ORDER BY AverageFreight DESC
  LIMIT 3;

-- 26
-- To do this, we need to add 18 years to each date:
UPDATE Orders
SET OrderDate = DATE_ADD(OrderDate, INTERVAL 18 YEAR);

SELECT OrderID, OrderDate, CompanyName
  FROM Orders as o
         JOIN Shippers as s ON o.ShipVia = s.ShipperID
  WHERE OrderID = 10249;

SELECT ShipCountry, ROUND(AVG(Freight), 2) as AvgFreight
  FROM Orders
  WHERE YEAR(OrderDate) = 2015
  GROUP BY ShipCountry
  ORDER BY AvgFreight DESC
  LIMIT 3;

-- 27
SELECT ShipCountry, ROUND(AVG(Freight), 2) as AvgFreight
  FROM Orders
  WHERE Date(OrderDate) BETWEEN '2015/1/1' AND '2015/12/31'
  GROUP BY ShipCountry
  ORDER BY AvgFreight DESC
  LIMIT 3;

SELECT OrderID, OrderDate, ShipCountry, ROUND((Freight), 2)
  FROM Orders
  WHERE DATE(OrderDate) = '2015/12/31'
  ORDER BY Freight DESC
  LIMIT 3;

select *
  from orders
  order by OrderDate
  LIMIT 700;

-- 28
SELECT ShipCountry, ROUND(AVG(Freight), 2) as AvgFreight
  FROM Orders
  WHERE OrderDate >= DATE_SUB((SELECT MAX(OrderDate) FROM Orders), INTERVAL 1 YEAR)
  GROUP BY ShipCountry
  ORDER BY AvgFreight DESC
  LIMIT 3;

-- 29
SELECT e.EmployeeID, LastName, o.OrderID, ProductName, Quantity
  FROM Employees as e
         JOIN Orders as o ON e.EmployeeID = o.EmployeeID
         JOIN `Order Details` as od ON od.OrderID = o.OrderID
         JOIN Products as p ON p.ProductID = od.ProductID
  ORDER BY OrderID, od.ProductID;

-- 30
SELECT c.CustomerID as Customers_CustomerID, o.CustomerID as Orders_CustomerID
  FROM Customers as c
         LEFT JOIN Orders as o ON c.CustomerID = o.CustomerID
  WHERE o.CustomerID IS NULL;

-- 31

SELECT c.CustomerID
  FROM Customers as c
         LEFT JOIN Orders as o ON c.CustomerID = o.CustomerID AND o.EmployeeID = 4
  WHERE o.CustomerID IS NULL;

SELECT c.CustomerID
  FROM Customers as c
  WHERE c.CustomerID NOT IN (SELECT o.CustomerID FROM Orders as o WHERE EmployeeID = 4);
