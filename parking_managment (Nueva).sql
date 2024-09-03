-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 03, 2024 at 05:00 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

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
(2, 'tienda'),
(3, 'supermercado');

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
(1, 'rojo'),
(2, 'azul'),
(3, 'verde'),
(4, 'amarillo'),
(5, 'naranja'),
(6, 'morado'),
(7, 'rosa'),
(8, 'negro'),
(9, 'blanco'),
(10, 'gris');

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
(1, 1, 2, 4500);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `item_identifiquer` varchar(32) NOT NULL,
  `business_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `color` int(11) NOT NULL,
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

INSERT INTO `item` (`id`, `item_identifiquer`, `business_id`, `type`, `color`, `checkin_state`, `checkout_state`, `checkin_hour`, `checkout_hour`, `checkin_by`, `checkout_by`) VALUES
(1, 'fff456', 1, 0, 2, 1, 3, '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 2),
(2, 'wjk95c', 1, 1, 1, 1, 1, '2024-09-03 01:27:29', '2024-09-03 02:58:31', 1, 1),
(3, 'hhy546', 1, 1, 5, 3, 0, '2024-09-03 02:28:58', NULL, 1, 0);

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
(1, 'carro', 1, 3400),
(2, 'moto', 1, 2500),
(3, 'bicicleta', 1, 700);

-- --------------------------------------------------------

--
-- Table structure for table `state`
--

CREATE TABLE `state` (
  `id` int(11) NOT NULL,
  `state_name` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `state`
--

INSERT INTO `state` (`id`, `state_name`) VALUES
(1, 'nuevo'),
(2, 'usado'),
(3, 'bueno'),
(4, 'regular'),
(5, 'malo'),
(6, 'reacondicionado'),
(7, 'dañado'),
(8, 'como nuevo'),
(9, 'roto'),
(10, 'caja abierta');

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
-- Indexes for table `income`
--
ALTER TABLE `income`
  ADD PRIMARY KEY (`id`),
  ADD KEY `business_id` (`business_id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `my_user`
--
ALTER TABLE `my_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `price`
--
ALTER TABLE `price`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `state`
--
ALTER TABLE `state`
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `income`
--
ALTER TABLE `income`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `my_user`
--
ALTER TABLE `my_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `price`
--
ALTER TABLE `price`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `state`
--
ALTER TABLE `state`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `user_preference`
--
ALTER TABLE `user_preference`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `income`
--
ALTER TABLE `income`
  ADD CONSTRAINT `income_ibfk_1` FOREIGN KEY (`business_id`) REFERENCES `business` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
