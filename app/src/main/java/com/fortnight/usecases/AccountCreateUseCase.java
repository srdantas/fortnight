package com.fortnight.usecases;

import com.fortnight.domains.Account;
import com.fortnight.domains.Balance;
import com.fortnight.gateways.AccountSaveGateway;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_NEXT;

@Component
@RequiredArgsConstructor
public class AccountCreateUseCase {

    private final AccountSaveGateway accountSaveGateway;

    public Mono<Void> execute(@NonNull final Account account) {
        return Mono.just(account)
                .map(this::createBalance)
                .log("AccountCreateUseCase.execute.createBalance", INFO, ON_NEXT)
                .flatMap(accountSaveGateway::execute);
    }

    private Account createBalance(final Account account) {
        if (Objects.isNull(account.getBalance())) {
            account.setBalance(new Balance());
        }

        return account;
    }
}
