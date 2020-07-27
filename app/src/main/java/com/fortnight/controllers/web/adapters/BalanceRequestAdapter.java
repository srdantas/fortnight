package com.fortnight.controllers.web.adapters;

import com.fortnight.controllers.web.responses.BalanceResponse;
import com.fortnight.domains.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceRequestAdapter {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "amount", source = "balance.amount")
    BalanceResponse from(Account account);
}
