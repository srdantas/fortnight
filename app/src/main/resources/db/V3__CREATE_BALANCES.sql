CREATE TABLE `balances`
(
    `document` varchar(13) NOT NULL,
    `amount`   bigint      NOT NULL,

    PRIMARY KEY (`document`),
    KEY `fkIdx_21` (`document`),
    CONSTRAINT `fk_balances` FOREIGN KEY `fkIdx_21` (`document`) REFERENCES `accounts` (`document`)
);
