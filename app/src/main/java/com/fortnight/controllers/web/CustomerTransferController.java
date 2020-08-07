package com.fortnight.controllers.web;

import com.fortnight.controllers.web.requests.TransferRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("customers")
public class CustomerTransferController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{document}/transfers")
    public Mono<Void> execute(@PathVariable final String document,
                              @RequestBody @Valid final TransferRequest request) {
        return Mono.empty();
    }
}
