-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 11, 2024 at 03:52 AM
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
-- Database: `parking_managment`
--

-- --------------------------------------------------------

--
-- Table structure for table `business`
--

CREATE TABLE `business` (
  `id` int(11) NOT NULL,
  `category` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `owner_name` varchar(32) DEFAULT NULL,
  `phone_number` varchar(12) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `address` varchar(64) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `country` varchar(4) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `business`
--

INSERT INTO `business` (`id`, `category`, `name`, `owner_name`, `phone_number`, `email`, `address`, `city`, `state`, `country`, `created_at`) VALUES
(1, 1, 'parking1', 'carlos', '3016124366', 'parking1@gmail.com', 'cl 11 #8-22', 'buga', 'valle', 'co', '2024-09-03 02:13:56');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `business_type` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `business_type`) VALUES
(1, 'parqueadero'),
(2, 'comercial'),
(3, 'comidas');

-- --------------------------------------------------------

--
-- Table structure for table `color`
--

CREATE TABLE `color` (
  `id` int(11) NOT NULL,
  `color_name` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `color`
--

INSERT INTO `color` (`id`, `color_name`) VALUES
(1, 'blanco'),
(2, 'gris'),
(3, 'negro'),
(4, 'rojo'),
(5, 'azul'),
(6, 'otro');

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE `devices` (
  `id` int(11) NOT NULL,
  `disk_serial_number` varchar(255) NOT NULL,
  `bios_uuid` varchar(255) NOT NULL,
  `os_info` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `devices`
--

INSERT INTO `devices` (`id`, `disk_serial_number`, `bios_uuid`, `os_info`, `created_at`, `updated_at`, `active`) VALUES
(1, 'WD-WCC6Y1TN6L61', '958D5380-B05D-11E5-8F1B-107B44146C29', 'Windows 10 10.0 amd64', '2024-12-11 02:32:07', '2024-12-11 02:51:13', 1);

-- --------------------------------------------------------

--
-- Table structure for table `income`
--

CREATE TABLE `income` (
  `id` int(11) NOT NULL,
  `business_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `rate_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `income`
--

INSERT INTO `income` (`id`, `business_id`, `item_id`, `rate_amount`) VALUES
(1, 1, 1, 1900),
(2, 1, 4, 2100),
(3, 1, 5, 5900),
(4, 1, 7, 2200),
(5, 1, 8, 2000),
(6, 1, 9, 1700),
(7, 1, 10, 0),
(8, 1, 11, 3000),
(9, 1, 6, 7000);

-- --------------------------------------------------------

--
-- Table structure for table `invoice_model`
--

CREATE TABLE `invoice_model` (
  `id` int(11) NOT NULL,
  `business_id` int(11) NOT NULL,
  `icon` varchar(256) NOT NULL,
  `jrxml_file` varchar(256) NOT NULL,
  `jasper_model` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoice_model`
--

INSERT INTO `invoice_model` (`id`, `business_id`, `icon`, `jrxml_file`, `jasper_model`) VALUES
(1, 1, 'C:/Users/Felipe/Desktop/icono.jpg', 'C:/Users/Felipe/Desktop/InvoiceModel.jrxml', 'C:/Users/Felipe/Desktop/InvoiceModel.jasper');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `item_identifiquer` varchar(32) NOT NULL,
  `item_type` int(11) NOT NULL,
  `business_id` int(11) NOT NULL,
  `color` int(11) NOT NULL,
  `client` varchar(12) NOT NULL,
  `checkin_state` int(11) NOT NULL,
  `checkout_state` int(11) NOT NULL,
  `checkin_hour` timestamp NULL DEFAULT NULL,
  `checkout_hour` timestamp NULL DEFAULT NULL,
  `checkin_by` int(11) NOT NULL,
  `checkout_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `item_identifiquer`, `item_type`, `business_id`, `color`, `client`, `checkin_state`, `checkout_state`, `checkin_hour`, `checkout_hour`, `checkin_by`, `checkout_by`) VALUES
(1, 'hhh10g', 1, 1, 1, '1111435456', 1, 1, '2024-11-25 18:42:25', '2024-12-07 20:21:21', 1, 1),
(2, 'hhh56f', 1, 1, 5, '2222222222', 1, 0, '2024-12-06 20:50:56', NULL, 1, 0),
(3, 'frt123', 2, 1, 3, '1111122222', 2, 0, '2024-12-07 20:05:44', NULL, 1, 0),
(4, 'yyy123', 1, 1, 1, '2222211111', 1, 1, '2024-12-07 20:29:22', '2024-12-07 20:42:09', 1, 1),
(5, 'rrr123', 2, 1, 4, '1111133333', 1, 1, '2024-12-07 22:35:00', '2024-12-07 22:35:52', 1, 1),
(6, 'rrr123', 2, 1, 4, '1111122222', 1, 1, '2024-12-07 22:36:30', '2024-12-08 17:16:25', 1, 1),
(7, 'wjk95c', 1, 1, 5, '1112388256', 1, 2, '2024-12-07 22:58:52', '2024-12-07 22:59:41', 1, 1),
(8, 'wer34d', 1, 1, 1, '1234567890', 1, 1, '2024-12-08 00:21:02', '2024-12-08 00:21:32', 1, 1),
(9, 'ttt234', 1, 1, 2, '1234567890', 1, 1, '2024-12-08 00:36:52', '2024-12-08 00:37:41', 1, 1),
(10, 'qqq123', 2, 1, 5, '1234554321', 2, 1, '2024-12-08 03:24:00', '2024-12-08 03:24:42', 1, 1),
(11, 'fff10f', 1, 1, 1, '1234554321', 1, 1, '2024-12-08 03:26:43', '2024-12-08 03:27:16', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `my_user`
--

CREATE TABLE `my_user` (
  `id` int(11) NOT NULL,
  `card_id` varchar(16) NOT NULL,
  `name` varchar(32) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `birth_day` date NOT NULL,
  `business_id` int(11) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `my_user`
--

INSERT INTO `my_user` (`id`, `card_id`, `name`, `last_name`, `birth_day`, `business_id`, `email`, `password`, `admin`, `active`) VALUES
(0, '0000000000', 'indefinido', 'indefinido', '2000-01-01', 0, '', '', 0, 1),
(1, '1112455342', 'felipe', 'casanas', '2004-03-17', 1, 'casanascastrofelipe@gmail.com', '1234', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `price`
--

CREATE TABLE `price` (
  `id` int(11) NOT NULL,
  `rate_name` varchar(16) NOT NULL,
  `business_id` int(11) NOT NULL,
  `rate_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `price`
--

INSERT INTO `price` (`id`, `rate_name`, `business_id`, `rate_amount`) VALUES
(1, 'carro', 1, 3300),
(2, 'moto', 1, 2500),
(3, 'bicicleta', 1, 500);

-- --------------------------------------------------------

--
-- Table structure for table `state`
--

CREATE TABLE `state` (
  `id` int(11) NOT NULL,
  `state_name` varchar(16) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `state`
--

INSERT INTO `state` (`id`, `state_name`, `category_id`) VALUES
(0, 'indefinido', 0),
(1, 'buen estado', 1),
(2, 'rayon(es)', 1),
(3, 'golpe(s)', 1),
(4, 'desconocido', 1),
(5, 'nuevo', 2),
(6, 'usado', 2),
(7, 'reacondicionado', 2);

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `id` int(11) NOT NULL,
  `type_name` varchar(12) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`id`, `type_name`, `category_id`) VALUES
(1, 'moto', 1),
(2, 'carro', 1),
(3, 'bicicleta', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_preference`
--

CREATE TABLE `user_preference` (
  `id` int(11) NOT NULL,
  `dark_mode` int(11) NOT NULL,
  `recomendations` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `workers`
--

CREATE TABLE `workers` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `business_id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `business`
--
ALTER TABLE `business`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `disk_serial_number` (`disk_serial_number`,`bios_uuid`,`os_info`);

--
-- Indexes for table `income`
--
ALTER TABLE `income`
  ADD PRIMARY KEY (`id`),
  ADD KEY `business_id` (`business_id`);

--
-- Indexes for table `invoice_model`
--
ALTER TABLE `invoice_model`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_item_type` (`item_type`),
  ADD KEY `fk_item_business_name` (`business_id`),
  ADD KEY `fk_item_color_name` (`color`),
  ADD KEY `fk_item_checkin_state` (`checkin_state`),
  ADD KEY `fk_item_checkout_state` (`checkout_state`),
  ADD KEY `fk_item_checkin_by` (`checkin_by`),
  ADD KEY `fk_item_checkout_by` (`checkout_by`);

--
-- Indexes for table `my_user`
--
ALTER TABLE `my_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_business` (`business_id`);

--
-- Indexes for table `price`
--
ALTER TABLE `price`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_price_business` (`business_id`);

--
-- Indexes for table `state`
--
ALTER TABLE `state`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_preference`
--
ALTER TABLE `user_preference`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `workers`
--
ALTER TABLE `workers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`,`business_id`),
  ADD KEY `business_id` (`business_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `business`
--
ALTER TABLE `business`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `color`
--
ALTER TABLE `color`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `income`
--
ALTER TABLE `income`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `invoice_model`
--
ALTER TABLE `invoice_model`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `my_user`
--
ALTER TABLE `my_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `price`
--
ALTER TABLE `price`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `state`
--
ALTER TABLE `state`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `type`
--
ALTER TABLE `type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user_preference`
--
ALTER TABLE `user_preference`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `workers`
--
ALTER TABLE `workers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `workers`
--
ALTER TABLE `workers`
  ADD CONSTRAINT `workers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `my_user` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `workers_ibfk_2` FOREIGN KEY (`business_id`) REFERENCES `business` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
