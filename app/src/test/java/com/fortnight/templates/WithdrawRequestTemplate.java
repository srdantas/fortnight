package com.fortnight.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fortnight.controllers.web.requests.WithdrawRequest;

import java.math.BigDecimal;
import java.util.UUID;

public class WithdrawRequestTemplate implements TemplateLoader {

    public enum Templates {
        VALID,
        INVALID_AMOUNT,
        INVALID_CORRELATION
    }
    
    @Override
    public void load() {
        Fixture.of(WithdrawRequest.class)
                .addTemplate(WithdrawRequestTemplate.Templates.VALID.name(), new Rule() {{
                    add("correlation", regex("\\d{5}-\\d{5}-\\d{5}-\\d{5}"));
                    add("amount", random(BigDecimal.class, range(0.0, 10000.0)));
                }});

        Fixture.of(WithdrawRequest.class)
                .addTemplate(WithdrawRequestTemplate.Templates.INVALID_AMOUNT.name())
                .inherits(WithdrawRequestTemplate.Templates.VALID.name(), new Rule() {{
                    add("amount", random(BigDecimal.class, range(-10000.0, -0.1)));
                }});

        Fixture.of(WithdrawRequest.class)
                .addTemplate(WithdrawRequestTemplate.Templates.INVALID_CORRELATION.name())
                .inherits(WithdrawRequestTemplate.Templates.VALID.name(), new Rule() {{
                    add("correlation", null);
                }});
    }
}
