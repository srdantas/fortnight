package com.fortnight.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fortnight.domains.Customer;
import com.fortnight.domains.Transaction;
import com.fortnight.domains.Transfer;

public class TransferTemplate implements TemplateLoader {

    public enum Templates {
        VALID
    }

    @Override
    public void load() {
        Fixture.of(Transfer.class).addTemplate(Templates.VALID.name(), new Rule() {{
            add("transaction", one(Transaction.class, TransactionTemplate.Templates.VALID.name()));
            add("debtor", one(Customer.class, CustomerTemplate.Templates.VALID.name()));
            add("creditor", one(Customer.class, CustomerTemplate.Templates.VALID.name()));
        }});
    }
}
