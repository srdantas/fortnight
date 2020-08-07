package com.fortnight.gateways.database.mysql.adapters;

import com.fortnight.domains.Transaction;
import com.fortnight.gateways.database.mysql.entities.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionEntityAdapter {

    @Mapping(target = "transfer.debtor.document", source = "transfer.debtor")
    @Mapping(target = "transfer.creditor.document", source = "transfer.creditor")
    TransactionEntity from(Transaction transaction);

    @Mapping(source = "transfer.debtor.document", target = "transfer.debtor")
    @Mapping(source = "transfer.creditor.document", target = "transfer.creditor")
    Transaction to(TransactionEntity entity);
}
