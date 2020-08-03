package com.fortnight.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fortnight.domains.Customer;
import com.fortnight.domains.Transaction;
import com.fortnight.domains.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class TransactionTemplate implements TemplateLoader {

    public enum Templates {
        VALID
    }

    @Override
    public void load() {
        Fixture.of(Transaction.class).addTemplate(Templates.VALID.name(), new Rule() {{
            add("correlation", UUID.randomUUID().toString());
            add("amount", random(BigDecimal.class, range(0.0, 1000.0)));
            add("type", random(TransactionType.WITHDRAW, TransactionType.DEPOSIT));
            add("date", Instant.now());
            add("customer", one(Customer.class, CustomerTemplate.Templates.VALID.name()));
        }});
    }
}
