package com.fortnight.gateways.database.mysql.entities;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity(name = "customers")
public class CustomerEntity implements Persistable<String> {

    @Id
    private String document;
    private String name;
    private BigDecimal balance = BigDecimal.ZERO;
    private Instant creation;

    @Override
    public String getId() {
        return this.document;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
