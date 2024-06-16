-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2024 at 05:15 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `steel_shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `auth`
--

CREATE TABLE `auth` (
  `username` varchar(20) NOT NULL,
  `password` char(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `product_type` varchar(50) NOT NULL,
  `size` varchar(20) DEFAULT NULL,
  `weight` decimal(10,2) DEFAULT NULL,
  `weight_unit` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `brand`, `product_type`, `size`, `weight`, `weight_unit`) VALUES
(1, 'Brand A', 'Type X', '10 cm', 0.50, 'kg'),
(2, 'Brand B', 'Type Y', '20 cm', 1.20, 'gram'),
(3, 'Brand C', 'Type Z', '15 cm', 0.80, 'ons'),
(4, 'Brand D', 'Type X', '18 cm', 1.00, 'kg'),
(5, 'Brand E', 'Type Y', '12 cm', 0.60, 'gram');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `contact` varchar(50) NOT NULL,
  `address` text NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `customer_name`, `contact`, `address`, `email`) VALUES
(1, 'John Doe', '1234567890', '456 Elm St, Townsville, USA', 'john.doe@example.com'),
(2, 'Jane Smith', '9876543210', '789 Oak Ave, Villageburg, USA', 'jane.smith@example.com'),
(3, 'Bob Johnson', '5551234567', '321 Pine Rd, Oakville, USA', 'bob.johnson@example.com'),
(4, 'Alice Williams', '7778889990', '159 Maple Ln, Cedartown, USA', 'alice.williams@example.com'),
(5, 'Tom Davis', '2223334444', '753 Cedar Dr, Pineville, USA', 'tom.davis@example.com');

-- --------------------------------------------------------

--
-- Table structure for table `purchasing`
--

CREATE TABLE `purchasing` (
  `purchase_date` date NOT NULL,
  `invoice_number` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `product_type` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `supplier_name` varchar(50) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `purchasing`
--

INSERT INTO `purchasing` (`purchase_date`, `invoice_number`, `stock_id`, `brand`, `product_type`, `price`, `supplier_id`, `supplier_name`, `quantity`) VALUES
('2023-06-02', 1002, 2, 'Brand B', 'Type Y', 20000.00, 2, 'Supplier B', 100),
('2023-06-03', 1003, 3, 'Brand C', 'Type Z', 15000.00, 3, 'Supplier C', 75),
('2023-06-04', 1004, 4, 'Brand D', 'Type X', 18000.00, 4, 'Supplier D', 90);

-- --------------------------------------------------------

--
-- Table structure for table `returns`
--

CREATE TABLE `returns` (
  `return_date` date NOT NULL,
  `return_id` int(11) NOT NULL,
  `return_type` varchar(15) NOT NULL,
  `invoice_number` int(11) NOT NULL,
  `reason` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `sale_date` date NOT NULL,
  `invoice_number` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `product_type` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`sale_date`, `invoice_number`, `stock_id`, `brand`, `product_type`, `price`, `customer_id`, `customer_name`, `quantity`) VALUES
('2023-06-07', 2002, 2, 'Brand B', 'Type Y', 25000.00, 2, 'Jane Smith', 50),
('2023-06-08', 2003, 3, 'Brand C', 'Type Z', 20000.00, 3, 'Bob Johnson', 30),
('2023-06-09', 2004, 4, 'Brand D', 'Type X', 23000.00, 4, 'Alice Williams', 40);

-- --------------------------------------------------------

--
-- Table structure for table `stocks`
--

CREATE TABLE `stocks` (
  `stock_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `purchase_price` decimal(10,2) NOT NULL,
  `selling_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stocks`
--

INSERT INTO `stocks` (`stock_id`, `category_id`, `quantity`, `purchase_price`, `selling_price`) VALUES
(2, 2, 200, 20000.00, 25000.00),
(3, 3, 150, 15000.00, 20000.00),
(4, 4, 180, 18000.00, 23000.00);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `supplier_id` int(11) NOT NULL,
  `supplier_name` varchar(50) NOT NULL,
  `contact` varchar(20) NOT NULL,
  `address` text NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`supplier_id`, `supplier_name`, `contact`, `address`, `email`) VALUES
(1, 'Supplier A', 'John Doe', '123 Main St, Cityville, USA', 'john@doe.com'),
(2, 'Supplier B', 'Jane Smith', '456 Elm St, Townsville, USA', 'jane@smith.com'),
(3, 'Supplier C', 'Alex Johnson', '789 Oak Ave, Villageburg, USA', 'alex@johnson.com'),
(4, 'Supplier Bm', 'Jane Smith', '456 Elm St, Townsville, USA', 'jane@smith.com'),
(5, 'Supplier E', 'Ava Thompson', '543 Cherry Blvd, Hillside, USA', 'ava@thompson.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auth`
--
ALTER TABLE `auth`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `purchasing`
--
ALTER TABLE `purchasing`
  ADD PRIMARY KEY (`invoice_number`),
  ADD KEY `stock_id` (`stock_id`),
  ADD KEY `supplier_id` (`supplier_id`);

--
-- Indexes for table `returns`
--
ALTER TABLE `returns`
  ADD PRIMARY KEY (`return_id`),
  ADD KEY `invoice_number` (`invoice_number`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`invoice_number`),
  ADD KEY `stock_id` (`stock_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `stocks`
--
ALTER TABLE `stocks`
  ADD PRIMARY KEY (`stock_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`supplier_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `returns`
--
ALTER TABLE `returns`
  MODIFY `return_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `stocks`
--
ALTER TABLE `stocks`
  MODIFY `stock_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=261;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `supplier_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `purchasing`
--
ALTER TABLE `purchasing`
  ADD CONSTRAINT `purchasing_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`),
  ADD CONSTRAINT `purchasing_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`);

--
-- Constraints for table `returns`
--
ALTER TABLE `returns`
  ADD CONSTRAINT `returns_ibfk_1` FOREIGN KEY (`invoice_number`) REFERENCES `purchasing` (`invoice_number`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `returns_ibfk_2` FOREIGN KEY (`invoice_number`) REFERENCES `sales` (`invoice_number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`),
  ADD CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`);

--
-- Constraints for table `stocks`
--
ALTER TABLE `stocks`
  ADD CONSTRAINT `stocks_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
