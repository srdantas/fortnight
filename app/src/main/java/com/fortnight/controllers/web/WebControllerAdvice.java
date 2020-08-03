package com.fortnight.controllers.web;

import com.fortnight.domains.exceptions.CustomerAlreadyExistsException;
import com.fortnight.domains.exceptions.CustomerBalanceNotEnoughException;
import com.fortnight.domains.exceptions.CustomerNotFoundException;
import com.fortnight.domains.exceptions.TransactionAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class WebControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<Void> badRequest() {
        return Mono.empty();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public Mono<Void> conflict() {
        return Mono.empty();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public Mono<Void> notFound() {
        return Mono.empty();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(CustomerBalanceNotEnoughException.class)
    public Mono<Void> forbidden() {
        return Mono.empty();
    }

    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(TransactionAlreadyExistsException.class)
    public Mono<Void> notModified() {
        return Mono.empty();
    }
}
