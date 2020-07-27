package com.fortnight.controllers.web;

import com.fortnight.controllers.web.adapters.AccountRequestAdapter;
import com.fortnight.controllers.web.requests.AccountRequest;
import com.fortnight.usecases.AccountCreateUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_NEXT;

@RestController
@Tag(name = "accounts")
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountsCreateController {

    private final AccountRequestAdapter adapter;
    private final AccountCreateUseCase accountCreateUseCase;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> execute(@RequestBody final Mono<AccountRequest> request) {
        return request
                .map(adapter::from)
                .log("AccountsCreateController.execute.adapter", INFO, ON_NEXT)
                .flatMap(accountCreateUseCase::execute);
    }
}
