package com.fortnight.controllers.web;

import com.fortnight.controllers.web.adapters.CustomerResponseAdapter;
import com.fortnight.controllers.web.responses.CustomerResponse;
import com.fortnight.usecases.CustomerSearchUseCase;
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
@Tag(name = "customers")
@RequiredArgsConstructor
@RequestMapping("customers")
public class CustomerSearchController {

    private final CustomerResponseAdapter adapter;
    private final CustomerSearchUseCase customerSearchUseCase;

    @GetMapping("{document}")
    public Mono<CustomerResponse> execute(@PathVariable final String document) {
        return Mono.just(document)
                .flatMap(customerSearchUseCase::execute)
                .log("CustomerSearchController.customerSearchUseCase", INFO, ON_NEXT, ON_ERROR)
                .map(adapter::from)
                .log("CustomerSearchController.adapter", INFO, ON_NEXT);
    }

}
