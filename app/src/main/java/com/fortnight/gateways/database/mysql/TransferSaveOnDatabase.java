package com.fortnight.gateways.database.mysql;

import com.fortnight.domains.Transfer;
import com.fortnight.gateways.TransferCreateGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransferSaveOnDatabase implements TransferCreateGateway {

    @Override
    public Mono<Void> execute(Transfer transfer) {
        return null;
    }
}
