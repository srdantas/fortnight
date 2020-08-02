package com.fortnight.gateways.database.mysql.entities;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@DynamicUpdate
@Entity(name = "customers")
public class CustomerEntity {

    @Id
    private String document;
    private String name;
    private BigDecimal balance = BigDecimal.ZERO;
    private Instant creation;
}
