CREATE TABLE `customers`
(
 `document` varchar(13) NOT NULL ,
 `name`     varchar(100) NOT NULL ,
 `creation` datetime NOT NULL ,
 `balance`  float NOT NULL ,

PRIMARY KEY (`document`)
);
