-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 15, 2024 lúc 10:20 AM
-- Phiên bản máy phục vụ: 10.4.14-MariaDB
-- Phiên bản PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `foodtsk`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bill`
--

CREATE TABLE `bill` (
  `id` int(11) NOT NULL,
  `time` varchar(100) NOT NULL,
  `date` varchar(20) NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `bill`
--

INSERT INTO `bill` (`id`, `time`, `date`, `total`) VALUES
(38, '14:31', '2021-05-06', 45000),
(39, '14:23', '2021-05-13', 30000),
(40, '21:06', '2021-05-18', 55000),
(41, '21:40', '2021-05-18', 30000),
(42, '17:35', '2021-05-19', 215000),
(43, '17:36', '2021-05-19', 215000),
(44, '17:40', '2021-05-19', 170000),
(45, '17:42', '2021-05-19', 155000),
(46, '18:04', '2021-05-19', 15000),
(47, '13:36', '2021-09-12', 85000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Food'),
(2, 'Drink');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `detailsbill`
--

CREATE TABLE `detailsbill` (
  `id` int(11) NOT NULL,
  `product` int(11) NOT NULL,
  `bill` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `detailsbill`
--

INSERT INTO `detailsbill` (`id`, `product`, `bill`, `quantity`, `price`) VALUES
(64, 2, 38, 1, 30000),
(65, 3, 38, 1, 15000),
(66, 2, 39, 9, 30000),
(67, 2, 40, 2, 30000),
(68, 4, 40, 3, 25000),
(69, 9, 41, 2, 10000),
(70, 10, 41, 5, 20000),
(71, 2, 42, 1, 30000),
(72, 3, 42, 1, 15000),
(73, 4, 42, 5, 125000),
(74, 5, 42, 1, 30000),
(75, 6, 42, 1, 10000),
(76, 7, 42, 1, 5000),
(77, 2, 43, 1, 30000),
(78, 3, 43, 1, 15000),
(79, 4, 43, 5, 125000),
(80, 5, 43, 1, 30000),
(81, 6, 43, 1, 10000),
(82, 7, 43, 1, 5000),
(83, 4, 44, 4, 100000),
(84, 5, 44, 1, 30000),
(85, 3, 44, 2, 30000),
(86, 6, 44, 1, 10000),
(87, 3, 45, 2, 30000),
(88, 4, 45, 5, 125000),
(89, 3, 46, 1, 15000),
(90, 2, 47, 1, 20000),
(91, 5, 47, 1, 30000),
(92, 7, 47, 3, 15000),
(93, 8, 47, 1, 20000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhapkho`
--

CREATE TABLE `nhapkho` (
  `product` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `date` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `nhapkho`
--

INSERT INTO `nhapkho` (`product`, `quantity`, `price`, `date`) VALUES
(2, 3, 20000, '2021-09-03'),
(2, 2, 20000, '2021-09-03');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `CategoryID` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `name`, `CategoryID`, `quantity`, `price`) VALUES
(2, 'Chân Gà', 1, 4, '20000'),
(3, 'Da Heo Giòn', 1, 1, '15000'),
(4, 'Bánh Tráng Trộn', 1, 77, '25000'),
(5, 'Bánh Tráng Cuốn', 1, 45, '30000'),
(6, 'Xoài Lắc', 1, 197, '10000'),
(7, 'Kẹo Muối Ớt', 1, 495, '5000'),
(8, 'Lưỡi vịt', 1, 19, '20000'),
(9, 'Trà Chanh', 2, 10, '10000'),
(10, 'Trà Đào', 2, 45, '20000'),
(11, 'Milo Đá Dằm', 2, 40, '15000'),
(16, 'Lẩu Cay', 1, 50, '69000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `sex` varchar(10) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `password`, `sex`, `role`) VALUES
(3, 'test', 'admin', '1', 'Nam', 2),
(21, 'test2', 'employee', '2', 'Nam', 1),
(30, 'nv1', 'nv1', 'e10adc3949ba59abbe56e057f20f883e', 'Nữ', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `detailsbill`
--
ALTER TABLE `detailsbill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product` (`product`),
  ADD KEY `dbb_bill_fk` (`bill`);

--
-- Chỉ mục cho bảng `nhapkho`
--
ALTER TABLE `nhapkho`
  ADD KEY `nk_pd_fk` (`product`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cat` (`CategoryID`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `bill`
--
ALTER TABLE `bill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `detailsbill`
--
ALTER TABLE `detailsbill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=94;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `detailsbill`
--
ALTER TABLE `detailsbill`
  ADD CONSTRAINT `db_bill_fk` FOREIGN KEY (`bill`) REFERENCES `bill` (`id`),
  ADD CONSTRAINT `db_pd_fk` FOREIGN KEY (`product`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `dbb_bill_fk` FOREIGN KEY (`bill`) REFERENCES `bill` (`id`),
  ADD CONSTRAINT `detailsbill_ibfk_1` FOREIGN KEY (`bill`) REFERENCES `bill` (`id`);

--
-- Các ràng buộc cho bảng `nhapkho`
--
ALTER TABLE `nhapkho`
  ADD CONSTRAINT `nk_pd_fk` FOREIGN KEY (`product`) REFERENCES `product` (`id`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_cat` FOREIGN KEY (`CategoryID`) REFERENCES `category` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
