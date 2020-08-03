package com.fortnight.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fortnight.domains.TransactionType;
import com.fortnight.gateways.database.mysql.entities.CustomerEntity;
import com.fortnight.gateways.database.mysql.entities.TransactionEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class TransactionEntityTemplate implements TemplateLoader {

    public enum Templates {
        VALID_NEW,
        VALID_FROM_DATABASE
    }

    @Override
    public void load() {
        Fixture.of(TransactionEntity.class).addTemplate(Templates.VALID_NEW.name(), new Rule() {{
            add("correlation", UUID.randomUUID().toString());
            add("amount", random(BigDecimal.class, range(0.0, 1000.0)));
            add("type", random(TransactionType.WITHDRAW, TransactionType.DEPOSIT));
            add("date", Instant.now());
            add("customer", one(CustomerEntity.class, CustomerEntityTemplate.Templates.VALID.name()));
        }});

        Fixture.of(TransactionEntity.class)
                .addTemplate(Templates.VALID_FROM_DATABASE.name())
                .inherits(Templates.VALID_NEW.name(), new Rule() {{
                    add("id", random(Long.class, range(0, 99999)));
                }});
    }
}
