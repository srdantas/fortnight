package com.fortnight.gateways.database.mysql.repositories;

import com.fortnight.gateways.database.mysql.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<AccountEntity, String> {
}
