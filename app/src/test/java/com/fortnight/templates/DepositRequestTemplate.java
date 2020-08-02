package com.fortnight.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fortnight.controllers.web.requests.DepositRequest;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositRequestTemplate implements TemplateLoader {

    public enum Templates {
        VALID,
        INVALID_AMOUNT,
        INVALID_CORRELATION
    }

    @Override
    public void load() {
        Fixture.of(DepositRequest.class)
                .addTemplate(Templates.VALID.name(), new Rule() {{
                    add("correlation", UUID.randomUUID().toString());
                    add("amount", random(BigDecimal.class, range(0.0, 10000.0)));
                }});

        Fixture.of(DepositRequest.class)
                .addTemplate(Templates.INVALID_AMOUNT.name())
                .inherits(Templates.VALID.name(), new Rule() {{
                    add("amount", random(BigDecimal.class, range(-10000.0, -0.1)));
                }});

        Fixture.of(DepositRequest.class)
                .addTemplate(Templates.INVALID_CORRELATION.name())
                .inherits(Templates.VALID.name(), new Rule() {{
                    add("correlation", null);
                }});
    }
}
