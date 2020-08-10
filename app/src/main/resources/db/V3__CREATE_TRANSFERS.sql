CREATE TABLE `transfers`
(
    `id`          bigint auto_increment,
    `debtor`      varchar(13)  NOT NULL,
    `creditor`    varchar(13)  NOT NULL,
    `transaction` varchar(100) NOT NULL,

    PRIMARY KEY (`id`),
    CONSTRAINT `FK_74` FOREIGN KEY `fkIdx_74` (`debtor`) REFERENCES `customers` (`document`),
    CONSTRAINT `FK_75` FOREIGN KEY `fkIdx_75` (`creditor`) REFERENCES `customers` (`document`),
    CONSTRAINT `FK_76` FOREIGN KEY `fkIdx_76` (`transaction`) REFERENCES `transactions` (`correlation`)
);
