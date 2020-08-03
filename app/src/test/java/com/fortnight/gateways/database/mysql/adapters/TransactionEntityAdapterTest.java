package com.fortnight.gateways.database.mysql.adapters;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.UnitTest;
import com.fortnight.domains.Transaction;
import com.fortnight.gateways.database.mysql.entities.TransactionEntity;
import com.fortnight.templates.TransactionEntityTemplate;
import com.fortnight.templates.TransactionTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class TransactionEntityAdapterTest extends UnitTest {

    @InjectMocks
    private TransactionEntityAdapterImpl adapter;

    @Test
    public void testAdapterTransactionFromEntity() {
        final Transaction transaction = Fixture.from(Transaction.class).gimme(TransactionTemplate.Templates.VALID.name());

        final var result = adapter.from(transaction);

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getId());

        Assertions.assertEquals(transaction.getCustomer().getDocument(), result.getCustomer().getDocument());
        Assertions.assertEquals(transaction.getAmount(), result.getAmount());
        Assertions.assertEquals(transaction.getCorrelation(), result.getCorrelation());
        Assertions.assertEquals(transaction.getType(), result.getType());
        Assertions.assertEquals(transaction.getDate(), result.getDate());
    }

    @Test
    public void testAdapterTransactionToEntity() {
        final TransactionEntity entity = Fixture.from(TransactionEntity.class)
                .gimme(TransactionEntityTemplate.Templates.VALID_FROM_DATABASE.name());

        final var result = adapter.to(entity);

        Assertions.assertNotNull(result);

        Assertions.assertEquals(entity.getCustomer().getDocument(), result.getCustomer().getDocument());
        Assertions.assertEquals(entity.getAmount(), result.getAmount());
        Assertions.assertEquals(entity.getCorrelation(), result.getCorrelation());
        Assertions.assertEquals(entity.getType(), result.getType());
        Assertions.assertEquals(entity.getDate(), result.getDate());
    }

}
