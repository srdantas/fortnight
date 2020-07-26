package com.fortnight.gateway.database.mysql.repository;

import com.fortnight.gateway.database.mysql.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<BalanceEntity, String> {
}
