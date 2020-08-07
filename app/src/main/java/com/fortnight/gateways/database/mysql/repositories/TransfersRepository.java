package com.fortnight.gateways.database.mysql.repositories;

import com.fortnight.gateways.database.mysql.entities.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransfersRepository extends JpaRepository<TransferEntity, Long> {
}
