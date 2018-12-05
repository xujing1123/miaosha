#创建数据库
create database miaosha;
USE miaosha;

SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;

#创建表
DROP table if exists user;
CREATE TABLE `user`(
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT'用户ID(主键)',
  `NAME` VARCHAR(30) DEFAULT NULL COMMENT'用户名称',
  PRIMARY KEY (`ID`)
)ENGINE = InnoDB AUTO_INCREMENT = 59 DEFAULT  CHARSET=utf8 COMMENT  = '用户表';

INSERT  INTO  user (NAME)  VALUE ('徐晶');


-----------------------------------------------------秒杀---------------------------------------
#用户表
DROP table if exists miaosha_user;
CREATE TABLE `miaosha_user`(
  `ID` bigint(20) NOT NULL COMMENT'用户ID(主键),手机号码',
  `NICK_NAME` VARCHAR(225) DEFAULT NULL COMMENT'用户名称',
  `PASSWORD` VARCHAR(225) DEFAULT NULL COMMENT'用户密码',
  `SALT` VARCHAR(10) DEFAULT NULL COMMENT'用户密码（盐）',
  `HEAD` VARCHAR(100) DEFAULT NULL COMMENT'图像，云存储的id',
  `REGISTER_DATE` DATETIME DEFAULT NULL COMMENT'注册时间',
  `LAST_LOGIN_DATE` DATETIME DEFAULT NULL COMMENT'最后登陆',
  `LOGIN_COUNT` int(11) DEFAULT '0' COMMENT '登陆次数',
  PRIMARY KEY (`ID`)
)ENGINE = InnoDB DEFAULT  CHARSET=utf8 COMMENT  = '秒杀用户表';

INSERT INTO `miaosha_user` VALUES ('13677194063', '徐晶', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', null, '2018-09-03 18:10:19', '2018-09-03 18:10:23', '1');


#商品表
DROP table if exists goods;
CREATE TABLE `goods`(
  `ID` bigint(20) NOT NULL COMMENT'商品Id',
  `GOODS_NAME` VARCHAR(16) DEFAULT NULL COMMENT'商品名称',
  `GOODS_TITLE` VARCHAR(64) DEFAULT NULL COMMENT'商品标题',
  `GOODS_IMAGES` VARCHAR(64) DEFAULT NULL COMMENT'商品图片',
  `GOODS_DETAILS` TEXT DEFAULT NULL COMMENT'商品详情',
  `GOODS_PRICE` DECIMAL(10,2)   COMMENT'商品价格',
  `GOODS_STOCK`  bigint(11)   COMMENT'商品库存，1表示没有限制',
  PRIMARY KEY (`ID`)
)ENGINE = InnoDB DEFAULT  CHARSET=utf8 COMMENT  = '商品表';
INSERT INTO goods VALUES (1,'iphoneX','APPle iphone X  64G 银色 移动联通电信4G手机','/img/iphonex.png','APPle iphone X  64G 银色 移动联通电信4G手机',3212,-1);

#秒杀商品表
DROP table if exists miaosha_goods;
CREATE TABLE `miaosha_goods`(
  `ID` bigint(20) NOT NULL COMMENT'秒杀商品Id',
  `GOODS_ID` bigint(20) NOT NULL COMMENT'商品ID',
  `MIAOSHA_PRICE` DECIMAL(10,2) NOT NULl COMMENT'商品秒杀价格',
  `STOCK_COUNT` int(11)   COMMENT'库存数量',
  `START_DATE` DATETIME DEFAULT NULL COMMENT'秒杀开始时间',
  `END_DATE` DATETIME DEFAULT NULL COMMENT'秒杀结束时间',
  PRIMARY KEY (`ID`)
)ENGINE = InnoDB DEFAULT  CHARSET=utf8 COMMENT  = '商品秒杀表';

INSERT INTO miaosha_goods VALUES(1,1,0.01,4,'2018-09-08 18:10:19','2018-09-15 18:10:19');


#订单表
DROP table if exists order_info;
CREATE TABLE `order_info`(
  `ID` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT'主键Id',
  `USER_ID` bigint(20) NOT NULL COMMENT'用户ID',
  `GOODS_ID` bigint(20) NOT NULL COMMENT'商品ID',
  `ADDRESS_ID` bigint(20) NOT NULL COMMENT'收货地址ID',
  `GOODS_NAME` VARCHAR(16) DEFAULT NULL COMMENT'冗余过来的商品名称',
  `GOODS_COUNT` int(11) COMMENT '商品数量（下单数量）',
  `GOODS_PRICE` DECIMAL(10,2)   COMMENT'商品下单价格',
  `ORDER_CHANNEL` int(11) COMMENT '1.PC；2,android;3.ios',
  `STATUS` int(11) COMMENT '订单状态:0 未支付；1已支付；2已发货；3已收货；4.已退款；5已完成',
  `CREATE_DATE` DATETIME DEFAULT NULL COMMENT'下单时间',
  `PAY_DATE` DATETIME DEFAULT NULL COMMENT'支付时间'
)ENGINE = InnoDB AUTO_INCREMENT=59  CHARSET=utf8 COMMENT  = '订单表';

#秒杀订单表
DROP table if exists miaosha_order;
CREATE TABLE `miaosha_order`(
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT'主键Id',
  `USER_ID` bigint(20) NOT NULL COMMENT'用户ID',
  `ORDER_ID` bigint(20) NOT NULL COMMENT'用户ID',
  `GOODS_ID` bigint(20) NOT NULL COMMENT'商品ID',
  PRIMARY KEY (`ID`)
)ENGINE = InnoDB DEFAULT  CHARSET=utf8 COMMENT  = '秒杀订单表';


/** 避免买超*/
alter table miaosha_order add unique index save_unique_index(USER_ID,GOODS_ID);