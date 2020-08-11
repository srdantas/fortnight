package com.fortnight.controllers.web;

import com.fortnight.controllers.web.requests.TransferRequest;
import com.fortnight.usecases.transfers.CreateTransferUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigDecimal;

import static java.util.logging.Level.INFO;
import static reactor.core.publisher.SignalType.ON_ERROR;
import static reactor.core.publisher.SignalType.ON_NEXT;

@RestController
@RequiredArgsConstructor
@Tag(name = "operations")
@RequestMapping("customers")
public class CustomerTransferController {

    private final CreateTransferUseCase createTransferUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{document}/transfers")
    public Mono<Void> execute(@PathVariable final String document,
                              @RequestBody @Valid final TransferRequest request) {
        return createTransferUseCase.execute(request.getCorrelation(), BigDecimal.valueOf(request.getAmount()),
                document, request.getCreditor().getDocument())
                .log("CustomerTransferController.createTransferUseCase", INFO, ON_NEXT, ON_ERROR);
    }
}
