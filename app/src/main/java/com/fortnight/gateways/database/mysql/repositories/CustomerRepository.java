package com.fortnight.gateways.database.mysql.repositories;

import com.fortnight.gateways.database.mysql.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository
        extends JpaRepository<CustomerEntity, String> {
}
