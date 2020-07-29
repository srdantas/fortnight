package com.fortnight.controllers.web.adapters;

import com.fortnight.controllers.web.responses.CustomerResponse;
import com.fortnight.domains.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerResponseAdapter {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "balance", source = "balance")
    CustomerResponse from(Customer customer);
}
