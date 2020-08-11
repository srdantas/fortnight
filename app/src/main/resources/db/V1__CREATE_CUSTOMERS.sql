CREATE TABLE `customers`
(
 `document` varchar(13) NOT NULL ,
 `name`     varchar(100) NOT NULL ,
 `creation` datetime NOT NULL ,
 `balance`  DECIMAL(10, 2) NOT NULL ,

PRIMARY KEY (`document`)
);
