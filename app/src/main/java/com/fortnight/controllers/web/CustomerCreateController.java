package com.fortnight.controllers.web;

import com.fortnight.controllers.web.adapters.CustomerRequestAdapter;
import com.fortnight.controllers.web.requests.CustomerRequest;
import com.fortnight.usecases.CustomerCreateUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_NEXT;

@RestController
@Tag(name = "customers")
@RequiredArgsConstructor
@RequestMapping("customers")
public class CustomerCreateController {

    private final CustomerRequestAdapter adapter;
    private final CustomerCreateUseCase customerCreateUseCase;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> execute(@RequestBody @Valid final CustomerRequest request) {
        return Mono.just(request)
                .map(adapter::from)
                .log("CustomerCreateController.adapter", INFO, ON_NEXT)
                .flatMap(customerCreateUseCase::execute);
    }
}
