-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 21, 2022 at 01:43 PM
-- Server version: 10.5.16-MariaDB
-- PHP Version: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id19985165_crop_price_db`
--
CREATE DATABASE IF NOT EXISTS `id19985165_crop_price_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id19985165_crop_price_db`;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(50) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `email`, `password`) VALUES
(1, 'Admin', 'admin@gmail.com', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `auction`
--

CREATE TABLE `auction` (
  `id` int(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `image` text NOT NULL,
  `qty` int(11) NOT NULL,
  `starting_time` text NOT NULL,
  `ending_time` text NOT NULL,
  `status` tinyint(4) NOT NULL,
  `fk_seller_id` int(11) NOT NULL,
  `fk_category_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auction`
--

INSERT INTO `auction` (`id`, `name`, `price`, `description`, `image`, `qty`, `starting_time`, `ending_time`, `status`, `fk_seller_id`, `fk_category_id`) VALUES
(1, 'Vegetable', '1500', 'Vegetables are parts of plants that are consumed by humans or other animals as food. The original meaning is still commonly used and is applied to plants collectively to refer to all edible plant matter, including the flowers, fruits, stems, leaves, roots, and seeds.', 'https://ichef.bbci.co.uk/news/304/mcs/media/images/73334000/jpg/_73334530_vegindiabbc.jpg', 10, '19-12-2022 10:05:02 am', '25-12-2022 10:05:02 am', 1, 12, 1),
(2, 'Maize', '1800', 'Maize, also known as corn, is a cereal grain first domesticated by indigenous peoples in southern Mexico about 10,000 years ago. The leafy stalk of the plant produces pollen inflorescences and separate ovuliferous inflorescences called ears that when fertilized yield kernels or seeds, which are fruits.', 'https://cdn.britannica.com/36/167236-050-BF90337E/Ears-corn.jpg', 10, '19-12-2022 10:05:02 am', '25-12-2022 10:05:02 am', 1, 12, 1),
(3, 'Bajra', '600', 'Pearl millet is the most widely grown type of millet. It has been grown in Africa and the Indian subcontinent since prehistoric times. The center of diversity, and suggested area of domestication, for the crop is in the Sahel zone of West Africa.', 'https://www.apnikheti.com/upload/crops/4073idea99822149277ef0e90f99c9550bd730200a.jpg', 10, '19-12-2022 10:05:02 am', '25-12-2022 10:05:02 am', 1, 12, 1),
(4, 'Grain', '800', 'A grain is a small, hard, dry fruit – with or without an attached hull layer – harvested for human or animal consumption. A grain crop is a grain-producing plant. The two main types of commercial grain crops are cereals and legumes', 'https://www.treehugger.com/thmb/OxbtE6DZs2Ieq0KggcrxuBoHZ-Y=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/__opt__aboutcom__coeus__resources__content_migration__mnn__images__2016__02__WheatFieldWithBlueCloudySky-cab45aaa81564ba99e2bd9a40932fd08.jpg', 10, '19-12-2022 10:05:02 am', '25-12-2022 10:05:02 am', 1, 12, 1),
(5, 'Rice', '1200', 'rice, (Oryza sativa), edible starchy cereal grain and the grass plant (family Poaceae) by which it is produced. Roughly one-half of the world population, including virtually all of East and Southeast Asia, is wholly dependent upon rice as a staple food; 95 percent of the world\'s rice crop is eaten by humans.', 'https://news.uchicago.edu/sites/default/files/styles/full_width/public/images/2021-07/rice_plant_and_rice_grains.jpg?itok=RpdGHlYJ', 10, '19-12-2022 10:05:02 am', '20-12-2022 02:01:02 pm', 0, 12, 1);

-- --------------------------------------------------------

--
-- Table structure for table `bid`
--

CREATE TABLE `bid` (
  `id` int(50) NOT NULL,
  `fk_buyer_id` int(50) NOT NULL,
  `fk_auction_id` int(50) NOT NULL,
  `amount` double NOT NULL,
  `added_on` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bid`
--

INSERT INTO `bid` (`id`, `fk_buyer_id`, `fk_auction_id`, `amount`, `added_on`) VALUES
(1, 18, 5, 200, '19-12-2022 10:06:37 am'),
(2, 18, 6, 1500, '20-12-2022 12:06:59 pm'),
(3, 18, 6, 1600, '20-12-2022 12:08:02 pm'),
(4, 19, 6, 20000, '20-12-2022 12:14:30 pm'),
(5, 20, 5, 3000, '20-12-2022 01:55:20 pm');

-- --------------------------------------------------------

--
-- Table structure for table `buyer`
--

CREATE TABLE `buyer` (
  `id` int(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `image` text NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buyer`
--

INSERT INTO `buyer` (`id`, `name`, `contact`, `image`, `email`, `password`) VALUES
(18, 'Syed Mubeen Hussain ', '03000000000', 'content://media/external/images/media/273906', 'mubeen@gmail.com', '123'),
(19, 'testbuyer', '03232323232', 'content://media/external/images/media/273906', 'testbuyer@gmail.com', '123'),
(20, 'ali', '03243410738', 'content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F273906/ORIGINAL/NONE/image%2Fjpeg/1223169890', 'mujtaba@gmail.com', '234');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `image` text NOT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `image`, `status`) VALUES
(1, 'Food Crop', 'https://www.aces.edu/wp-content/uploads/2018/07/iStock-964095076.jpg', 1),
(2, 'Feed Crop', 'https://modernfarmer.com/wp-content/uploads/2020/04/image1-2-2-1200x853.jpg', 1),
(3, 'Fiber Crop', 'https://media-exp1.licdn.com/dms/image/C5612AQFhje3HZ3IwmA/article-cover_image-shrink_600_2000/0/1520184339256?e=2147483647&v=beta&t=aeDcE9DyV9wkuAGOXckbwavwkWcPsEhFy4zQLFXKhf8', 1),
(4, 'Oil Crop', 'https://agrihunt.com/wp-content/uploads/2015/08/Oilseed-crops-in-Pakistan-8.jpg', 1),
(5, 'Ornamental Crop', 'https://i.pinimg.com/736x/fc/6d/52/fc6d5294904585c410295173a4bb532e.jpg', 1),
(6, 'Industrial Crop', 'https://world-crops.com/wp-content/uploads/Industrial-crops-Tobacco.jpg', 1);

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(50) NOT NULL,
  `fk_buyer_id` int(50) NOT NULL,
  `fk_auction_id` int(50) NOT NULL,
  `message` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `fk_buyer_id`, `fk_auction_id`, `message`) VALUES
(4, 19, 6, 'nice '),
(5, 20, 5, 'good ');

-- --------------------------------------------------------

--
-- Table structure for table `contact`
--

CREATE TABLE `contact` (
  `id` int(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `subject` text NOT NULL,
  `message` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contact`
--

INSERT INTO `contact` (`id`, `name`, `subject`, `message`) VALUES
(8, 'test', 'subject ', 'message from the SAMI ');

-- --------------------------------------------------------

--
-- Table structure for table `seller`
--

CREATE TABLE `seller` (
  `id` int(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `image` text NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `seller`
--

INSERT INTO `seller` (`id`, `name`, `contact`, `image`, `email`, `password`) VALUES
(12, 'Farmer', '03000000000', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.com%2Ffree-icon%2Ffarmer_353778&psig=AOvVaw32c4J3DZlPCigvim3sHXW8&ust=1671510038488000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCIjB2aLqhPwCFQAAAAAdAAAAABAF', 'farmer@gmail.com', '123'),
(13, 'farmer', '03112222222', 'content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F273906/ORIGINAL/NONE/image%2Fjpeg/1661845107', 'farmer123@gmail.com', '123'),
(14, 'test', '03000000000', 'content://media/external/images/media/273906', 'test@gmail.com', '123');

-- --------------------------------------------------------

--
-- Table structure for table `sellerWiningAuction`
--

CREATE TABLE `sellerWiningAuction` (
  `id` int(50) NOT NULL,
  `fk_auction_id` int(50) NOT NULL,
  `fk_seller_id` int(50) NOT NULL,
  `amount` double NOT NULL,
  `added_on` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `winingAuction`
--

CREATE TABLE `winingAuction` (
  `id` int(50) NOT NULL,
  `fk_auction_id` int(50) NOT NULL,
  `fk_buyer_id` int(50) NOT NULL,
  `amount` double NOT NULL,
  `added_on` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `winingAuction`
--

INSERT INTO `winingAuction` (`id`, `fk_auction_id`, `fk_buyer_id`, `amount`, `added_on`) VALUES
(61, 6, 18, 1600, '20-12-2022 12:12:42 pm'),
(62, 6, 19, 20000, '20-12-2022 12:15:29 pm'),
(63, 6, 19, 20000, '20-12-2022 12:15:42 pm'),
(64, 5, 20, 3000, '20-12-2022 01:57:45 pm'),
(65, 5, 20, 3000, '20-12-2022 01:59:20 pm');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `auction`
--
ALTER TABLE `auction`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bid`
--
ALTER TABLE `bid`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `buyer`
--
ALTER TABLE `buyer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `seller`
--
ALTER TABLE `seller`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sellerWiningAuction`
--
ALTER TABLE `sellerWiningAuction`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `winingAuction`
--
ALTER TABLE `winingAuction`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `auction`
--
ALTER TABLE `auction`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `bid`
--
ALTER TABLE `bid`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `buyer`
--
ALTER TABLE `buyer`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `contact`
--
ALTER TABLE `contact`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `seller`
--
ALTER TABLE `seller`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `sellerWiningAuction`
--
ALTER TABLE `sellerWiningAuction`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `winingAuction`
--
ALTER TABLE `winingAuction`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
