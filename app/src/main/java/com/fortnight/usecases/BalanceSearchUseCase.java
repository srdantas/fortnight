package com.fortnight.usecases;

import com.fortnight.domains.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BalanceSearchUseCase {

    public Mono<Account> execute(final String document) {
        return Mono.error(new IllegalArgumentException());
    }
}
