package com.fortnight.controllers.web;

import com.fortnight.controllers.web.adapters.BalanceRequestAdapter;
import com.fortnight.controllers.web.responses.BalanceResponse;
import com.fortnight.usecases.BalanceSearchUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@RestController
@Tag(name = "balances")
@RequiredArgsConstructor
@RequestMapping("accounts")
public class BalanceSearchController {

    private final BalanceRequestAdapter adapter;
    private final BalanceSearchUseCase balanceSearchUseCase;

    @GetMapping("{document}/balances")
    public Mono<BalanceResponse> execute(@PathVariable final Mono<String> document) {
        return document
                .flatMap(balanceSearchUseCase::execute)
                .log("BalanceSearchController.execute.balanceSearchUseCase", INFO, ON_NEXT, ON_ERROR)
                .map(adapter::from)
                .log("BalanceSearchController.execute.adapter", INFO, ON_NEXT);
    }

}
