package com.fortnight.gateways.database.mysql.adapters;

import com.fortnight.domains.Transaction;
import com.fortnight.gateways.database.mysql.entities.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionEntityAdapter {

    TransactionEntity from(Transaction transaction);

    Transaction to(TransactionEntity entity);
}
