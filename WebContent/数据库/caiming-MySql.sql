/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50539
Source Host           : localhost:3306
Source Database       : caiming

Target Server Type    : MYSQL
Target Server Version : 50539
File Encoding         : 65001

Date: 2016-08-28 14:25:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `customers`
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(10) NOT NULL,
  `last_name` varchar(10) NOT NULL,
  `dob` date DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customers
-- ----------------------------
INSERT INTO `customers` VALUES ('1', 'John', 'Brown', '1965-01-01', '800-555-1211');
INSERT INTO `customers` VALUES ('2', 'Cynthia', 'Green', '1968-02-05', '800-555-1212');
INSERT INTO `customers` VALUES ('3', 'Steve', 'White', '1971-03-16', '800-555-1213');
INSERT INTO `customers` VALUES ('4', 'Gail', 'Black', null, '800-555-1214');
INSERT INTO `customers` VALUES ('5', 'Doreen', 'Blue', '1970-05-20', null);

-- ----------------------------
-- Table structure for `employees`
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `first_name` varchar(10) NOT NULL,
  `last_name` varchar(10) NOT NULL,
  `title` varchar(20) DEFAULT NULL,
  `salary` decimal(6,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_manage_relation` (`manager_id`),
  CONSTRAINT `FK_manage_relation` FOREIGN KEY (`manager_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='employees';

-- ----------------------------
-- Records of employees
-- ----------------------------
INSERT INTO `employees` VALUES ('1', null, 'James', 'Smith', 'CEO', '800000');
INSERT INTO `employees` VALUES ('2', '1', 'Ron', 'Johnson', 'Sales Manager', '600000');
INSERT INTO `employees` VALUES ('3', '2', 'Fred', 'Hobbs', 'Salesperson', '150000');
INSERT INTO `employees` VALUES ('4', '2', 'Susan', 'Jones', 'Salesperson', '500000');

-- ----------------------------
-- Table structure for `products`
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `product_type_id` int(11) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_product_type` (`product_type_id`),
  CONSTRAINT `FK_product_type` FOREIGN KEY (`product_type_id`) REFERENCES `product_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='products';

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES ('1', '1', 'Modern Science', 'A description of modern science', '19.95');
INSERT INTO `products` VALUES ('2', '1', 'Chemistry', 'Introduction to Chemistry', '30.00');
INSERT INTO `products` VALUES ('3', '2', 'Supernova', 'A star explodes', '25.99');
INSERT INTO `products` VALUES ('4', '2', 'Tank War', 'Action movie about a future war', '13.95');
INSERT INTO `products` VALUES ('5', '2', 'Z Files', 'Series on mysterious activities', '49.99');
INSERT INTO `products` VALUES ('6', '2', '2412: The Return', 'Aliens return', '14.95');
INSERT INTO `products` VALUES ('7', '3', 'Space Force 9', 'Adventures of heroes', '13.49');
INSERT INTO `products` VALUES ('8', '3', 'From Another Planet', 'Alien from another planet lands on Earth', '12.99');
INSERT INTO `products` VALUES ('9', '4', 'Classical Music', 'The best classical music', '10.99');
INSERT INTO `products` VALUES ('10', '4', 'Pop 3', 'The best popular music', '15.99');
INSERT INTO `products` VALUES ('11', '4', 'Creative Yell', 'Debut album', '14.99');
INSERT INTO `products` VALUES ('12', null, 'My Front Line', 'Their greatest hits', '13.49');

-- ----------------------------
-- Table structure for `product_types`
-- ----------------------------
DROP TABLE IF EXISTS `product_types`;
CREATE TABLE `product_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='��Ʒ���ͱ�';

-- ----------------------------
-- Records of product_types
-- ----------------------------
INSERT INTO `product_types` VALUES ('1', 'Book');
INSERT INTO `product_types` VALUES ('2', 'Video');
INSERT INTO `product_types` VALUES ('3', 'DVD');
INSERT INTO `product_types` VALUES ('4', 'CD');
INSERT INTO `product_types` VALUES ('5', 'Magazine');

-- ----------------------------
-- Table structure for `promotions`
-- ----------------------------
DROP TABLE IF EXISTS `promotions`;
CREATE TABLE `promotions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `duration` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='promotions';

-- ----------------------------
-- Records of promotions
-- ----------------------------
INSERT INTO `promotions` VALUES ('1', '10% off Z Files', null);
INSERT INTO `promotions` VALUES ('2', '20% off Pop 3', null);
INSERT INTO `promotions` VALUES ('3', '30% off Modern Science', null);
INSERT INTO `promotions` VALUES ('4', '20% off Tank War', null);
INSERT INTO `promotions` VALUES ('5', '10% off Chemistry', null);
INSERT INTO `promotions` VALUES ('6', '20% off Creative Yell', null);
INSERT INTO `promotions` VALUES ('7', '15% off My Front Line', null);

-- ----------------------------
-- Table structure for `purchases`
-- ----------------------------
DROP TABLE IF EXISTS `purchases`;
CREATE TABLE `purchases` (
  `customer_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`customer_id`,`product_id`),
  KEY `FK_PRODUCT_ID` (`product_id`),
  CONSTRAINT `FK_CUSTOMER_ID` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of purchases
-- ----------------------------
INSERT INTO `purchases` VALUES ('1', '1', '1');
INSERT INTO `purchases` VALUES ('1', '2', '3');
INSERT INTO `purchases` VALUES ('2', '1', '2');
INSERT INTO `purchases` VALUES ('2', '2', '1');
INSERT INTO `purchases` VALUES ('3', '1', '1');
INSERT INTO `purchases` VALUES ('3', '2', '1');
INSERT INTO `purchases` VALUES ('3', '3', '1');
INSERT INTO `purchases` VALUES ('4', '1', '1');
INSERT INTO `purchases` VALUES ('4', '2', '1');

-- ----------------------------
-- Table structure for `salary_grades`
-- ----------------------------
DROP TABLE IF EXISTS `salary_grades`;
CREATE TABLE `salary_grades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `low_salary` decimal(6,0) DEFAULT NULL,
  `high_salary` decimal(6,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='salary_grades';

-- ----------------------------
-- Records of salary_grades
-- ----------------------------
INSERT INTO `salary_grades` VALUES ('1', '1', '250000');
INSERT INTO `salary_grades` VALUES ('2', '250001', '500000');
INSERT INTO `salary_grades` VALUES ('3', '500001', '750000');
INSERT INTO `salary_grades` VALUES ('4', '750001', '999999');
