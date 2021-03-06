package com.fortnight.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fortnight.controllers.web.requests.CustomerRequest;

import java.util.UUID;

public class CustomerRequestTemplate implements TemplateLoader {

    public enum Templates {
        VALID,
        WITHOUT_NAME,
        WITHOUT_DOCUMENT
    }

    @Override
    public void load() {
        Fixture.of(CustomerRequest.class).addTemplate(Templates.VALID.name(), new Rule() {{
            add("name", UUID.randomUUID().toString());
            add("document", regex("\\d{3}\\d{3}\\d{3}\\d{2}"));
        }});

        Fixture.of(CustomerRequest.class).addTemplate(Templates.WITHOUT_DOCUMENT.name(), new Rule() {{
            add("name", UUID.randomUUID().toString());
        }});

        Fixture.of(CustomerRequest.class).addTemplate(Templates.WITHOUT_NAME.name(), new Rule() {{
            add("document", regex("\\d{3}\\d{3}\\d{3}\\d{2}"));
        }});
    }
}
