package com.fortnight.gateways.database.mysql.adapters;

import com.fortnight.domains.Account;
import com.fortnight.gateways.database.mysql.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountEntityAdapter {

    @Mapping(target = "document", source = "document")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "creation", source = "creation")
    @Mapping(target = "balance.amount", source = "balance.amount")
    AccountEntity to(Account account);
}
