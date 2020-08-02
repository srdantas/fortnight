package com.fortnight.gateways.database.mysql.repositories;

import com.fortnight.gateways.database.mysql.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<CustomerEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM customers c WHERE c.document = ?1")
    Optional<CustomerEntity> findByIdAndLock(String document);

    @Modifying
    @Query(value = "INSERT INTO customers(document, name, creation, balance) " +
            "VALUES (:document, :name, :creation, :balance)",
            nativeQuery = true)
    Integer insert(String document, String name, Instant creation, BigDecimal balance);
}
