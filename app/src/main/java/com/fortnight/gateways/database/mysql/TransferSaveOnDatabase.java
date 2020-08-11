package com.fortnight.gateways.database.mysql;

import com.fortnight.domains.Transfer;
import com.fortnight.domains.exceptions.TransactionAlreadyExistsException;
import com.fortnight.gateways.CustomerUpdateGateway;
import com.fortnight.gateways.TransferCreateGateway;
import com.fortnight.gateways.database.mysql.adapters.TransferEntityAdapter;
import com.fortnight.gateways.database.mysql.repositories.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class TransferSaveOnDatabase implements TransferCreateGateway {

    private final TransferEntityAdapter adapter;
    private final TransferRepository repository;
    private final CustomerUpdateGateway customerUpdateGateway;

    @Override
    public Mono<Void> execute(final Transfer transfer) {
        return Mono.just(transfer)
                .map(adapter::from)
                .log("TransferSaveOnDatabase.adapter.from", INFO, ON_NEXT)
                .map(repository::save)
                .log("TransferSaveOnDatabase.repository.save", INFO, ON_NEXT, ON_ERROR)
                .map(adapter::to)
                .flatMap(this::updateBalances)
                .log("CreateTransferUseCase.updateBalances", INFO, ON_NEXT, ON_ERROR)
                .then()
                .onErrorMap(DataIntegrityViolationException.class, (ex) -> new TransactionAlreadyExistsException());
    }

    private Mono<Transfer> updateBalances(final Transfer transfer) {
        return this.customerUpdateGateway.execute(transfer.getCreditor())
                .and(this.customerUpdateGateway.execute(transfer.getDebtor()))
                .thenReturn(transfer);
    }
}
