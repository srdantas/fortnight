package com.fortnight.gateway.database.mysql.repository;

import com.fortnight.gateway.database.mysql.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<AccountEntity, String> {
}
