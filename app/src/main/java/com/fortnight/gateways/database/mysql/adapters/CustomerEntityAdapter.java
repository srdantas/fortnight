package com.fortnight.gateways.database.mysql.adapters;

import com.fortnight.domains.Customer;
import com.fortnight.gateways.database.mysql.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerEntityAdapter {

    @Mapping(target = "document", source = "document")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "creation", source = "creation")
    @Mapping(target = "balance", source = "balance")
    CustomerEntity to(Customer customer);

    @Mapping(target = "document", source = "document")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "creation", source = "creation")
    @Mapping(target = "balance", source = "balance")
    Customer from(CustomerEntity entity);
}
