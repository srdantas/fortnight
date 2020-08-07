CREATE TABLE `transactions`
(
    `id`          bigint auto_increment,
    `correlation` varchar(100)                             NOT NULL,
    `document`    varchar(13)                              NOT NULL,
    `amount`      float                                    NOT NULL,
    `date`        datetime                                 NOT NULL,
    `transfer`    bigint                                   NOT NULL,
    `type`        enum ('DEPOSIT', 'WITHDRAW', 'TRANSFER') NOT NULL,

    PRIMARY KEY (`id`),
    KEY `fkIdx_73` (`document`),
    CONSTRAINT `FK_73` FOREIGN KEY `fkIdx_73` (`document`) REFERENCES `customers` (`document`),
    CONSTRAINT `FK_76` FOREIGN KEY `fkIdx_76` (`transfer`) REFERENCES `transfers` (`id`),
    UNIQUE (`correlation`, `document`)

);
