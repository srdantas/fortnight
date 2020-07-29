CREATE TABLE `transactions`
(
 `correlation` varchar(100) NOT NULL ,
 `document`    varchar(13) NOT NULL ,
 `amount`      bigint NOT NULL ,
 `date`        datetime NOT NULL ,
 `type`        enum('deposit', 'withdraw', 'transfer_debit', 'transfer_credit') NOT NULL ,

PRIMARY KEY (`correlation`, `document`),
KEY `fkIdx_73` (`document`),
CONSTRAINT `FK_73` FOREIGN KEY `fkIdx_73` (`document`) REFERENCES `customers` (`document`)
);
