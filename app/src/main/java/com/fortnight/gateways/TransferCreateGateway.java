package com.fortnight.gateways;

import com.fortnight.domains.Transfer;
import reactor.core.publisher.Mono;

public interface TransferCreateGateway {

    Mono<Void> execute(Transfer transfer);
}
