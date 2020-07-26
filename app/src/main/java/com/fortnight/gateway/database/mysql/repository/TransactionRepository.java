package com.fortnight.gateway.database.mysql.repository;

import com.fortnight.gateway.database.mysql.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
}
