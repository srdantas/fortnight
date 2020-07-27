package com.fortnight.gateways;

import com.fortnight.domains.Account;
import reactor.core.publisher.Mono;

public interface AccountSaveGateway {

    Mono<Void> execute(Account account);
}
