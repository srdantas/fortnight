package com.fortnight.gateways.database.mysql.repositories;

import com.fortnight.gateways.database.mysql.entities.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<BalanceEntity, String> {
}
