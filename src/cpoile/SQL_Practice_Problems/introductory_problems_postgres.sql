-- 1  not used in postgres
-- USE northwind;

-- need this function:
create function end_of_month(date)
  returns date as
$$
select (date_trunc('month', $1) + interval '1 month' - interval '1 day')::date;
$$ language 'sql'
   immutable strict;

-- To do some of the questions, we need to add 18 years to each date:
UPDATE Orders
SET order_date = order_date + interval '18 year';

UPDATE Orders
SET required_date = required_date + interval '18 year';

UPDATE Orders
SET shipped_date = shipped_date + interval '18 year';

SHOW TABLES;

SELECT *
  FROM Orders;

-- 2
SELECT category_name, Description
  FROM Categories;

-- 3
SELECT first_name, last_name, hire_date
  FROM Employees
  WHERE Title = 'Sales Representative';

-- 4
SELECT first_name, last_name, hire_date
  FROM Employees
  WHERE Title = 'Sales Representative'
    AND Country = 'USA';

-- 5
SELECT *
  FROM Orders
  WHERE employee_id = 5;

-- 6
SELECT supplier_id, contact_name, contact_title
  FROM Suppliers
  WHERE contact_title != 'Marketing Manager';

-- 7
SELECT product_id, product_name
  FROM Products
  WHERE product_name LIKE '%queso%';

-- 8
SELECT order_id, customer_id, ship_country
  FROM Orders
  WHERE ship_country = 'France'
     OR ship_country = 'Belgium';

-- 9
SELECT order_id, customer_id, ship_country
  FROM Orders
  WHERE ship_country IN ('Brazil', 'Mexico', 'Argentina', 'Venezuela');

-- 10
SELECT first_name, last_name, Title, birth_date
  FROM EMPLOYEES
  ORDER BY birth_date;

-- 11
SELECT first_name, last_name, Title, birth_date::date as DateOnlybirth_date
  FROM EMPLOYEES
  ORDER BY birth_date;

-- 12
SELECT first_name, last_name, CONCAT(first_name, ' ', last_name) as Fullname
  FROM Employees;

-- 13
SELECT order_id,
       product_id,
       ROUND(unit_price::DECIMAL, 2)::text              as unit_price,
       Quantity,
       Round((unit_price * Quantity)::DECIMAL, 2)::text as TotalPrice
  FROM order_details;

-- 14
SELECT COUNT(*) as TotalCustomers
  FROM Customers;

-- 15
SELECT MIN(order_date) as FirstOrder
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
SELECT contact_title, COUNT(*) as Totalcontact_title
  FROM Customers
  GROUP BY contact_title
  ORDER BY Totalcontact_title DESC;

-- 18
SELECT product_id, product_name, company_name
  FROM Products as p
         INNER JOIN Suppliers as s
                    ON p.supplier_id = s.supplier_id
  ORDER BY product_id;

-- 19
SELECT order_id, order_date::date as order_date, company_name as Shipper
  FROM Orders as o
         JOIN Shippers as s ON o.ship_via = s.shipper_id
  WHERE order_id < 10300
  ORDER BY order_id;

-- 20
SELECT category_name, COUNT(*) as TotalProducts
  FROM Products as p
         JOIN Categories as c ON p.category_id = c.category_id
  GROUP BY category_name
  ORDER BY TotalProducts DESC;

-- 21
SELECT Country, City, COUNT(*) as TotalCustomer
  FROM Customers
  GROUP BY Country, City
  ORDER BY TOtalCustomer DESC;

-- 22
SELECT product_id, product_name, units_in_stock, reorder_level
  FROM Products
  WHERE units_in_stock < reorder_level
  ORDER BY product_id;

-- 23
SELECT product_id, product_name, units_in_stock, units_on_order, reorder_level, Discontinued
  FROM Products
  WHERE units_in_stock + units_on_order <= reorder_level
    AND Discontinued = false
  ORDER BY product_id;

-- 24
SELECT customer_id, company_name, Region
  FROM Customers
  ORDER BY Region, customer_id;

-- 25
SELECT ship_country, ROUND(AVG(Freight)::decimal, 2)::text as AverageFreight
  FROM Orders
  GROUP BY ship_country
  ORDER BY AverageFreight DESC
  LIMIT 3;

-- 26
SELECT order_id, order_date, company_name
  FROM Orders as o
         JOIN Shippers as s ON o.ship_via = s.shipper_id
  WHERE order_id = 10249;

SELECT ship_country, ROUND(AVG(Freight)::decimal, 2)::text as AvgFreight
  FROM Orders
  WHERE date_part('year', order_date) = 2015
  GROUP BY ship_country
  ORDER BY AvgFreight DESC
  LIMIT 3;

-- 27
SELECT ship_country, ROUND(AVG(Freight)::decimal, 2)::text as AvgFreight
  FROM Orders
  WHERE order_date BETWEEN '2015/1/1' AND '2015/12/31'
  GROUP BY ship_country
  ORDER BY AvgFreight DESC
  LIMIT 3;

SELECT order_id, order_date, ship_country, ROUND((Freight)::decimal, 2)::text
  FROM Orders
  WHERE order_date = '2015/12/31'
  ORDER BY Freight DESC
  LIMIT 3;

select *
  from orders
  order by order_date
  LIMIT 700;

-- 28
SELECT ship_country, ROUND(AVG(Freight)::decimal, 2)::text as AvgFreight
  FROM Orders
  WHERE order_date >= (SELECT MAX(order_date) FROM Orders) - interval '1 year'
  GROUP BY ship_country
  ORDER BY AvgFreight DESC
  LIMIT 3;

-- 29
SELECT e.employee_id, last_name, o.order_id, product_name, Quantity
  FROM Employees as e
         JOIN Orders as o ON e.employee_id = o.employee_id
         JOIN order_details as od ON od.order_id = o.order_id
         JOIN Products as p ON p.product_id = od.product_id
  ORDER BY order_id, od.product_id;

-- 30
SELECT c.customer_id as Customers_customer_id, o.customer_id as Orders_customer_id
  FROM Customers as c
         LEFT JOIN Orders as o ON c.customer_id = o.customer_id
  WHERE o.customer_id IS NULL;

-- 31

SELECT c.customer_id
  FROM Customers as c
         LEFT JOIN Orders as o ON c.customer_id = o.customer_id AND o.employee_id = 4
  WHERE o.customer_id IS NULL;

SELECT c.customer_id
  FROM Customers as c
  WHERE c.customer_id NOT IN (SELECT o.customer_id FROM Orders as o WHERE employee_id = 4);

-- 32

SELECT c.customer_id,
       company_name,
       o.order_id,
       ROUND(SUM(unit_price * Quantity)::decimal, 2)::text as TotalOrderAmount
  FROM Orders as o
         JOIN order_details as od ON o.order_id = od.order_id
         JOIN Customers as c ON o.customer_id = c.customer_id
  WHERE date_part('year', order_date) = 2016
  GROUP BY c.customer_id, o.order_id
  HAVING SUM(unit_price * Quantity) > 10000
  ORDER BY TotalOrderAmount DESC;

-- 33

SELECT c.customer_id, company_name, ROUND(SUM(unit_price * Quantity)::decimal, 2)::text as TotalOrderAmount
  FROM Orders as o
         JOIN order_details as od ON o.order_id = od.order_id
         JOIN Customers as c ON o.customer_id = c.customer_id
  WHERE date_part('year', order_date) = 2016
  GROUP BY c.customer_id
  HAVING SUM(unit_price * quantity) >= 15000
  ORDER BY TotalOrderAmount DESC;

-- 34
SELECT c.customer_id,
       company_name,
       ROUND(SUM(unit_price * Quantity * (1 - Discount))::decimal, 2)::text as TotalOrderAmount
  FROM Orders as o
         JOIN order_details as od ON o.order_id = od.order_id
         JOIN Customers as c ON o.customer_id = c.customer_id
  WHERE date_part('year', order_date) = 2016
  GROUP BY c.customer_id
  HAVING SUM(unit_price * Quantity * (1 - Discount)) >= 15000
  ORDER BY TotalOrderAmount DESC;

-- 35
SELECT order_id, employee_id, order_date
  FROM Orders
  WHERE order_date = end_of_month(order_date)
  ORDER BY employee_id, order_id;

-- 36
SELECT o.order_id, COUNT(*) as TotalOrderDetails
  FROM Orders as o
         JOIN order_details as od ON o.order_id = od.order_id
  GROUP BY o.order_id
  ORDER BY TotalOrderDetails DESC
  LIMIT 10;

SELECT order_id, COUNT(*) as TotalOrderDetails
  FROM order_details
  GROUP BY order_id
  ORDER BY TotalOrderDetails DESC
  LIMIT 10;

-- 37
SELECT order_id
  FROM order_details
  WHERE random() < .02;

-- 38
SELECT order_id
  FROM order_details
  WHERE Quantity >= 60
  GROUP BY order_id, Quantity
  HAVING COUNT(*) > 1;


SELECT name, setting FROM pg_settings WHERE category = 'File Locations';

