package com.fortnight.controllers.web;

import com.fortnight.controllers.web.requests.DepositRequest;
import com.fortnight.usecases.deposits.CustomerDepositUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@RestController
@Tag(name = "operations")
@RequiredArgsConstructor
@RequestMapping("customers")
public class CustomerDepositController {

    private final CustomerDepositUseCase depositUseCase;

    @PostMapping("{document}/deposits")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> execute(@PathVariable final String document,
                              @RequestBody @Valid final DepositRequest request) {
        return depositUseCase.execute(document, request.getCorrelation(), request.getAmount())
                .log("CustomerDepositController.depositUseCase", INFO, ON_NEXT, ON_ERROR);
    }
}
