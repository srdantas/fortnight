package com.fortnight.controllers.web;

import com.fortnight.controllers.web.requests.WithdrawRequest;
import com.fortnight.usecases.withdraws.CustomerWithdrawUseCase;
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
public class CustomerWithdrawController {

    private final CustomerWithdrawUseCase withdrawUseCase;

    @PostMapping("{document}/withdraws")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> execute(@PathVariable final String document,
                              @RequestBody @Valid final WithdrawRequest request) {
        return withdrawUseCase.execute(document, request.getCorrelation(), request.getAmount())
                .log("CustomerWithdrawController.withdrawUseCase", INFO, ON_NEXT, ON_ERROR);
    }

}
