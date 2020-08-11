package com.fortnight.gateways.database.mysql.adapters;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Transfer;
import com.fortnight.templates.TransferTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class TransferEntityAdapterTest extends UnitTest {

    @InjectMocks
    private TransferEntityAdapterImpl adapter;

    @Test
    public void testTransferAdapter() {
        final Transfer transfer = Fixture.from(Transfer.class).gimme(TransferTemplate.Templates.VALID.name());

        final var result = adapter.from(transfer);

        Assertions.assertNotNull(result.getCreditor());
        Assertions.assertNotNull(result.getCreditor());
        Assertions.assertNotNull(result.getTransaction());

        Assertions.assertEquals(result.getTransaction().getCorrelation(), transfer.getTransaction().getCorrelation());
        Assertions.assertEquals(result.getTransaction().getAmount(), transfer.getTransaction().getAmount());
        Assertions.assertEquals(result.getTransaction().getType(), transfer.getTransaction().getType());
        Assertions.assertEquals(result.getTransaction().getDate(), transfer.getTransaction().getDate());

        Assertions.assertEquals(result.getCreditor().getDocument(), transfer.getCreditor().getDocument());
        Assertions.assertEquals(result.getDebtor().getDocument(), transfer.getDebtor().getDocument());

        Assertions.assertEquals(result.getDebtor().getBalance(), transfer.getDebtor().getBalance());
        Assertions.assertEquals(result.getDebtor().getBalance(), transfer.getDebtor().getBalance());

        Assertions.assertEquals(result.getDebtor().getName(), transfer.getDebtor().getName());
        Assertions.assertEquals(result.getDebtor().getName(), transfer.getDebtor().getName());
    }
}