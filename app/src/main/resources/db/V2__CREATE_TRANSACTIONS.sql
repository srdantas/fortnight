CREATE TABLE `transactions`
(
    `id`          bigint auto_increment,
    `correlation` varchar(100)                             NOT NULL,
    `document`    varchar(13)                              NOT NULL,
    `amount`      float                                    NOT NULL,
    `date`        datetime                                 NOT NULL,
    `type`        enum ('deposit', 'withdraw', 'transfer') NOT NULL,

    PRIMARY KEY (`id`),
    KEY `fkIdx_73` (`document`),
    CONSTRAINT `FK_73` FOREIGN KEY `fkIdx_73` (`document`) REFERENCES `customers` (`document`),
    UNIQUE (`correlation`, `document`)

);
