/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.27 : Database - es_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`es_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `es_db`;

/*Table structure for table `tb_item` */

DROP TABLE IF EXISTS `tb_item`;

CREATE TABLE `tb_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id，同时也是商品编号',
  `item_name` varchar(100) NOT NULL COMMENT '商品名称',
  `sell_point` varchar(500) DEFAULT NULL COMMENT '商品卖点',
  `price` bigint(20) NOT NULL COMMENT '商品价格，单位为：分',
  `num` int(11) NOT NULL COMMENT '库存数量',
  `brand` varchar(30) DEFAULT NULL COMMENT '品牌',
  `image` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `cid` bigint(10) NOT NULL COMMENT '所属类目，叶子类目',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '商品状态，1-正常，2-下架，3-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='商品表';

/*Data for the table `tb_item` */

insert  into `tb_item`(`id`,`item_name`,`sell_point`,`price`,`num`,`brand`,`image`,`cid`,`status`,`create_time`,`modify_time`) values (1,'华为p40','高清像素',50000,100,'huawei','https://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1',1,1,'2019-04-03 20:27:10','2020-05-05 11:29:26'),(2,'华为p30','高清语音',4000,50,'huawei','https://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-2',1,1,'2020-05-03 20:27:47','2020-05-05 11:29:12'),(3,'小米10','便宜/充电快',3000,30,'MI','https://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-55',2,1,'2020-05-03 20:28:14','2020-05-05 11:29:05'),(4,'苹果手机','土豪金',60000,99,'apple','https://image.baidu.com/search/index?99',3,1,'2020-05-05 11:28:23','2020-05-05 11:31:45'),(5,'三星s10','曲面屏',80000,100,'samsung','https://image.baidu.com/search/index?5115',4,1,'2020-05-05 11:31:35','2020-05-05 11:31:48'),(6,'OPPO手机','好用',4000,66,'OPPO','https://image.baidu.com/search/index?5115',1,1,'2020-05-05 11:32:41','2020-05-05 11:32:47'),(7,'奔驰s400','双排气',666666666,5,'奔驰','https://www.mercedes-benz.com.cn/?smtid=570962679z32cmz2dt02z1pdz0z',5,1,'2020-05-05 12:12:36','2020-05-05 12:12:36');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
