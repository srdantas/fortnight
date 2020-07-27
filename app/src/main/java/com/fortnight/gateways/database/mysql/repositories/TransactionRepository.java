package com.fortnight.gateways.database.mysql.repositories;

import com.fortnight.gateways.database.mysql.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
}
