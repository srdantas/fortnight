package com.fortnight.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fortnight.domains.Customer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class CustomerTemplate implements TemplateLoader {

    public enum Templates {
        VALID
    }

    @Override
    public void load() {
        Fixture.of(Customer.class).addTemplate(Templates.VALID.name(), new Rule() {{
            add("name", UUID.randomUUID().toString());
            add("document", regex("\\d{3}\\d{3}\\d{3}\\d{2}"));
            add("creation", Instant.now());
            add("balance", random(BigDecimal.class, range(0, 10000)));
        }});
    }
}
