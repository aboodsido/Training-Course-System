-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2022 at 10:08 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aam_training_center_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_user` varchar(15) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_user`, `name`, `age`, `phone_number`, `address`) VALUES
('Hamada', 'Hamada Abu Zaid', 20, 597902438, 'KhanYounis');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `course_id` varchar(15) NOT NULL,
  `course_name` varchar(50) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`course_id`, `course_name`, `description`) VALUES
('CAP 2134', 'Database Security', 'IT Colleaege'),
('CSCI 2308', 'Programming 3', 'IT Colleaege'),
('GEB 1011', 'Introduction to Business', 'Business Colleaege'),
('GEB 2350', 'Survey of International Business', 'Business Colleaege'),
('MAC 1105', 'College Algebra', 'Mathmatics Colleaege'),
('MAC 1114', 'Trigonometry ', 'Mathmatics Colleaege'),
('REA 0007', 'Developmental Reading I', 'Reading Colleage'),
('REA 0009', 'HS Reading', 'Reading Colleage');

-- --------------------------------------------------------

--
-- Table structure for table `course_room`
--

CREATE TABLE `course_room` (
  `course_id` varchar(15) NOT NULL,
  `room_id` varchar(15) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course_room`
--

INSERT INTO `course_room` (`course_id`, `room_id`, `time`) VALUES
('CAP 2134', 'I116', '2022-05-14 09:16:29'),
('CSCI 2308', 'I214', '2022-05-14 09:16:53'),
('GEB 1011', 'B004', '2022-05-14 09:15:31'),
('GEB 2350', 'B300', '2022-05-14 09:16:03'),
('MAC 1105', 'K512', '2022-05-14 09:17:09'),
('MAC 1114', 'K517', '2022-05-14 09:17:21'),
('REA 0007', 'B004', '2022-05-14 09:19:30'),
('REA 0009', 'I214', '2022-05-14 09:18:13');

-- --------------------------------------------------------

--
-- Table structure for table `login_info`
--

CREATE TABLE `login_info` (
  `username` varchar(15) NOT NULL,
  `password` text NOT NULL,
  `userCategory` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login_info`
--

INSERT INTO `login_info` (`username`, `password`, `userCategory`) VALUES
('Assel', 'e10adc3949ba59abbe56e057f20f883e', 'student'),
('hamada', 'e10adc3949ba59abbe56e057f20f883e', 'admin'),
('Haytham', 'e10adc3949ba59abbe56e057f20f883e', 'student'),
('Heba', 'e10adc3949ba59abbe56e057f20f883e', 'trainer'),
('Hisham', 'e10adc3949ba59abbe56e057f20f883e', 'student'),
('Mohammed', 'e10adc3949ba59abbe56e057f20f883e', 'trainer'),
('Zaina', 'e10adc3949ba59abbe56e057f20f883e', 'trainer');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `room_id` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`room_id`) VALUES
('B004'),
('B300'),
('I116'),
('I214'),
('K512'),
('K517'),
('M401');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `student_user` varchar(15) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_user`, `name`, `age`, `phone_number`, `address`) VALUES
('Assel', 'Assel Ghassan', 21, 32155, 'Gaza'),
('Haytham', 'Haytham Mohammed', 22, 1864851, 'KhanYounis'),
('Hisham', 'Hisham Bassam', 17, 565485, 'Rafah');

-- --------------------------------------------------------

--
-- Table structure for table `student_course`
--

CREATE TABLE `student_course` (
  `student_user` varchar(15) NOT NULL,
  `course_id` varchar(15) NOT NULL,
  `room_id` varchar(15) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_course`
--

INSERT INTO `student_course` (`student_user`, `course_id`, `room_id`, `time`) VALUES
('Assel', 'CAP 2134', 'I116', '2022-05-14 09:16:29'),
('Assel', 'MAC 1114', 'K517', '2022-05-14 09:17:21'),
('haytham', 'CSCI 2308', 'I214', '2022-05-14 09:16:53'),
('haytham', 'GEB 1011', 'B004', '2022-05-14 09:15:31'),
('haytham', 'GEB 2350', 'B300', '2022-05-14 09:16:03');

-- --------------------------------------------------------

--
-- Table structure for table `trainer`
--

CREATE TABLE `trainer` (
  `trainer_user` varchar(15) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `address` text NOT NULL,
  `major` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trainer`
--

INSERT INTO `trainer` (`trainer_user`, `name`, `age`, `phone_number`, `address`, `major`) VALUES
('Heba', 'Heba Raed', 23, 516553, 'KhanYounis', 'Decor'),
('Mohammed', 'Mohammed Adnan', 27, 23165512, 'Rafah', 'Sport'),
('Zaina', 'Zaina Mohammed', 15, 3215655, 'Gaza', 'IT');

-- --------------------------------------------------------

--
-- Table structure for table `trainer_course`
--

CREATE TABLE `trainer_course` (
  `trainer_user` varchar(15) NOT NULL,
  `course_id` varchar(15) NOT NULL,
  `room_id` varchar(15) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trainer_course`
--

INSERT INTO `trainer_course` (`trainer_user`, `course_id`, `room_id`, `time`) VALUES
('Heba', 'CSCI 2308', 'I214', '2022-05-14 09:16:53'),
('Heba', 'MAC 1105', 'K512', '2022-05-14 09:17:09'),
('Heba', 'MAC 1114', 'K517', '2022-05-14 09:17:21'),
('Heba', 'REA 0007', 'B004', '2022-05-14 09:19:30'),
('Heba', 'REA 0009', 'I214', '2022-05-14 09:18:13'),
('zaina', 'CAP 2134', 'I116', '2022-05-14 09:16:29'),
('zaina', 'CSCI 2308', 'I214', '2022-05-14 09:16:53'),
('zaina', 'GEB 1011', 'B004', '2022-05-14 09:15:31');

-- --------------------------------------------------------

--
-- Table structure for table `user_category`
--

CREATE TABLE `user_category` (
  `category_id` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_category`
--

INSERT INTO `user_category` (`category_id`) VALUES
('admin'),
('student'),
('trainer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_user`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`course_id`),
  ADD UNIQUE KEY `course_name` (`course_name`);

--
-- Indexes for table `course_room`
--
ALTER TABLE `course_room`
  ADD PRIMARY KEY (`course_id`,`room_id`,`time`),
  ADD KEY `room_id_fk` (`room_id`);

--
-- Indexes for table `login_info`
--
ALTER TABLE `login_info`
  ADD PRIMARY KEY (`username`,`userCategory`),
  ADD KEY `user_category_fk` (`userCategory`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_user`);

--
-- Indexes for table `student_course`
--
ALTER TABLE `student_course`
  ADD PRIMARY KEY (`student_user`,`course_id`,`room_id`,`time`),
  ADD KEY `course_id_fk1` (`course_id`),
  ADD KEY `room_id_fk1` (`room_id`);

--
-- Indexes for table `trainer`
--
ALTER TABLE `trainer`
  ADD PRIMARY KEY (`trainer_user`);

--
-- Indexes for table `trainer_course`
--
ALTER TABLE `trainer_course`
  ADD PRIMARY KEY (`trainer_user`,`course_id`,`room_id`,`time`),
  ADD KEY `course_id_fk2` (`course_id`),
  ADD KEY `room_id_fk2` (`room_id`);

--
-- Indexes for table `user_category`
--
ALTER TABLE `user_category`
  ADD PRIMARY KEY (`category_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course_room`
--
ALTER TABLE `course_room`
  ADD CONSTRAINT `course_id_fk` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `room_id_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `login_info`
--
ALTER TABLE `login_info`
  ADD CONSTRAINT `user_category_fk` FOREIGN KEY (`userCategory`) REFERENCES `user_category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `student_course`
--
ALTER TABLE `student_course`
  ADD CONSTRAINT `course_id_fk1` FOREIGN KEY (`course_id`) REFERENCES `course_room` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `room_id_fk1` FOREIGN KEY (`room_id`) REFERENCES `course_room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `student_user_fk` FOREIGN KEY (`student_user`) REFERENCES `student` (`student_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `trainer_course`
--
ALTER TABLE `trainer_course`
  ADD CONSTRAINT `course_id_fk2` FOREIGN KEY (`course_id`) REFERENCES `course_room` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `room_id_fk2` FOREIGN KEY (`room_id`) REFERENCES `course_room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `trainer_user_fk` FOREIGN KEY (`trainer_user`) REFERENCES `trainer` (`trainer_user`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
