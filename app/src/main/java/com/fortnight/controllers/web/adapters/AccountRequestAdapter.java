package com.fortnight.controllers.web.adapters;

import com.fortnight.controllers.web.requests.AccountRequest;
import com.fortnight.domains.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountRequestAdapter {

    Account from(AccountRequest request);
}
