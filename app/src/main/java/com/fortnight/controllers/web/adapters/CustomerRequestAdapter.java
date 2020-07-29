package com.fortnight.controllers.web.adapters;

import com.fortnight.controllers.web.requests.CustomerRequest;
import com.fortnight.domains.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRequestAdapter {

    Customer from(CustomerRequest request);
}
