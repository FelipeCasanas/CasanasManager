-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 27, 2025 at 03:09 AM
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
(1, 2, 'casanas store', 'felipe casañas', '3026029365', 'casanasstore@casanasgroup.com', 'carrera 29 #12a-03', 'buga', 'valle', 'co', '2024-12-29 02:13:56'),
(2, 1, 'parqueadero exito', 'administracion exito', '3003725935', 'administracion_exito@exito.com', 'carrera 13 #8-54', 'buga', 'valle', 'co', '2025-01-14 20:28:15');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `business_type` varchar(32) NOT NULL,
  `retail` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `business_type`, `retail`) VALUES
(1, 'parqueadero', 0),
(2, 'comercial', 1),
(3, 'comidas', 1);

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
(6, 'verde'),
(7, 'rosado'),
(8, 'morado'),
(9, 'amarillo'),
(10, 'otro');

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
  `buy_price` double NOT NULL,
  `sell_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `income`
--

INSERT INTO `income` (`id`, `business_id`, `item_id`, `buy_price`, `sell_price`) VALUES
(1, 1, 1, 8002, 0),
(2, 1, 2, 8002, 0),
(3, 1, 3, 8002, 0),
(4, 1, 4, 8002, 0),
(5, 1, 5, 9779, 0),
(6, 1, 6, 9779, 0),
(7, 1, 7, 9779, 0),
(8, 1, 8, 9779, 0),
(9, 1, 9, 8515, 0),
(10, 1, 10, 8515, 0),
(11, 1, 11, 8515, 0),
(12, 1, 12, 8515, 0),
(13, 1, 13, 10118, 25000),
(14, 1, 14, 10118, 0),
(15, 1, 15, 10118, 0),
(16, 1, 16, 10118, 0),
(17, 1, 17, 8002, 0),
(18, 1, 18, 8002, 0),
(19, 1, 19, 8002, 0),
(20, 1, 20, 8002, 0),
(21, 1, 21, 7931, 15500),
(22, 1, 22, 7931, 0),
(23, 1, 23, 7931, 0),
(24, 1, 24, 7931, 0),
(25, 1, 25, 8002, 15000),
(26, 1, 26, 8002, 0),
(27, 1, 27, 8002, 0),
(28, 1, 28, 8002, 0),
(29, 2, 29, 0, 0),
(30, 2, 30, 0, 0);

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
(1, 'rsb001', 4, 1, 1, '1112388257', 5, 0, '2024-12-28 19:23:53', NULL, 1, 0),
(2, 'rsb002', 4, 1, 1, '1112388257', 5, 0, '2024-12-28 19:26:13', NULL, 1, 0),
(3, 'rsb003', 4, 1, 1, '1112388257', 5, 0, '2024-12-28 19:26:53', NULL, 1, 0),
(4, 'rsb004', 4, 1, 1, '1112388257', 5, 0, '2024-12-28 19:27:03', NULL, 1, 0),
(5, 'rdn001', 5, 1, 3, '1112388257', 5, 5, '2024-12-28 19:28:49', '2025-02-17 01:39:59', 1, 1),
(6, 'rdn002', 5, 1, 3, '1112388257', 5, 0, '2024-12-28 19:29:05', NULL, 1, 0),
(7, 'rdn003', 5, 1, 3, '1112388257', 5, 0, '2024-12-28 19:29:44', NULL, 1, 0),
(8, 'rdn004', 5, 1, 3, '1112388257', 5, 0, '2024-12-28 19:29:50', NULL, 1, 0),
(9, 'rsn001', 4, 1, 3, '1112388257', 8, 0, '2024-12-28 19:31:08', NULL, 1, 0),
(10, 'rsn002', 4, 1, 3, '1112388257', 5, 0, '2024-12-28 19:31:18', NULL, 1, 0),
(11, 'rsn003', 4, 1, 3, '1112388257', 5, 0, '2024-12-28 19:31:24', NULL, 1, 0),
(12, 'rsn004', 4, 1, 3, '1112388257', 5, 0, '2024-12-28 19:31:32', NULL, 1, 0),
(13, 'rdna001', 5, 1, 5, '1112388257', 5, 5, '2024-12-31 19:56:52', '2025-01-03 21:30:22', 1, 1),
(14, 'rdna002', 5, 1, 5, '1112388257', 5, 0, '2024-12-31 19:56:59', NULL, 1, 0),
(15, 'rdna003', 5, 1, 5, '1112388257', 5, 0, '2024-12-31 19:57:06', NULL, 1, 0),
(16, 'rdna004', 5, 1, 5, '1112388257', 5, 0, '2024-12-31 19:57:13', NULL, 1, 0),
(17, 'rsv001', 4, 1, 6, '1112388257', 5, 0, '2025-01-02 19:11:40', NULL, 1, 0),
(18, 'rsv002', 4, 1, 6, '1112388257', 8, 0, '2025-01-02 19:11:48', NULL, 1, 0),
(19, 'rsv003', 4, 1, 6, '1112388257', 5, 0, '2025-01-02 19:11:56', NULL, 1, 0),
(20, 'rsv004', 4, 1, 6, '1112388257', 5, 0, '2025-01-02 19:12:16', NULL, 1, 0),
(21, 'rsrd001', 4, 1, 7, '1112388257', 5, 5, '2025-01-03 00:38:45', '2025-01-08 02:45:18', 1, 1),
(22, 'rsrd002', 4, 1, 7, '1112388257', 5, 0, '2025-01-03 00:52:12', NULL, 1, 0),
(23, 'rsrd003', 4, 1, 7, '1112388257', 5, 0, '2025-01-03 00:52:21', NULL, 1, 0),
(24, 'rsrd004', 4, 1, 7, '1112388257', 5, 0, '2025-01-03 00:52:36', NULL, 1, 0),
(25, 'rsr001', 4, 1, 4, '1112388257', 5, 0, '2025-01-03 00:59:07', NULL, 1, 0),
(26, 'rsr002', 4, 1, 4, '1112388257', 5, 0, '2025-01-03 00:59:13', NULL, 1, 0),
(27, 'rsr003', 4, 1, 4, '1112388257', 5, 0, '2025-01-03 00:59:24', NULL, 1, 0),
(28, 'rsr004', 4, 1, 4, '1112388257', 5, 0, '2025-01-03 00:59:36', NULL, 1, 0),
(29, 'wjk95c', 1, 2, 5, '2222222222', 1, 0, '2025-01-14 20:36:52', NULL, 3, 0),
(30, 'rru442', 2, 2, 1, '2222222222', 2, 0, '2025-01-14 20:38:22', NULL, 3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `item_update`
--

CREATE TABLE `item_update` (
  `id` int(11) NOT NULL,
  `business_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `update_note` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(1, '1112455342', 'felipe', 'casañas', '2004-03-17', 1, 'casanascastrofelipe@gmail.com', '1234', 1, 1),
(3, '1111270234', 'julio', 'rodriguez', '2001-02-18', 2, 'julio_rodriguez@gmail.com', '12345', 0, 1);

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
(1, 'carro', 2, 3300),
(2, 'moto', 2, 1500),
(3, 'bicicleta', 2, 500),
(4, 'reloj silicona', 1, 9500),
(5, 'reloj deportivo', 1, 12000);

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
(7, 'reacondicionado', 2),
(8, 'defectuoso', 2),
(9, 'dañado', 2);

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `id` int(11) NOT NULL,
  `type_name` varchar(64) NOT NULL,
  `business_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`id`, `type_name`, `business_id`) VALUES
(1, 'moto', 2),
(2, 'carro', 2),
(3, 'bicicleta', 2),
(4, 'reloj silicona', 1),
(5, 'reloj deportivo', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_preference`
--

CREATE TABLE `user_preference` (
  `id` int(11) NOT NULL,
  `dark_mode` int(11) NOT NULL,
  `recomendations` int(11) NOT NULL
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
-- Indexes for table `item_update`
--
ALTER TABLE `item_update`
  ADD PRIMARY KEY (`id`);

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `business`
--
ALTER TABLE `business`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `color`
--
ALTER TABLE `color`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `income`
--
ALTER TABLE `income`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `item_update`
--
ALTER TABLE `item_update`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `my_user`
--
ALTER TABLE `my_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `price`
--
ALTER TABLE `price`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `state`
--
ALTER TABLE `state`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `type`
--
ALTER TABLE `type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user_preference`
--
ALTER TABLE `user_preference`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
