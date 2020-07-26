CREATE TABLE `transactions`
(
    `correlation` varchar(100)                                                      NOT NULL,
    `amount`      bigint                                                            NOT NULL,
    `date`        datetime                                                          NOT NULL,
    `type`        enum ('deposit', 'withdraw', 'transfer_debit', 'transfer_credit') NOT NULL,
    `document`    varchar(13)                                                       NOT NULL,

    PRIMARY KEY (`correlation`),
    KEY `fkIdx_70` (`document`),
    CONSTRAINT `FK_70` FOREIGN KEY `fkIdx_70` (`document`) REFERENCES `balances` (`document`)
);

