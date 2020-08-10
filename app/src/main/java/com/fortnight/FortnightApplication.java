package com.fortnight;

import com.fortnight.domains.Customer;
import com.fortnight.domains.Transaction;
import com.fortnight.domains.TransactionType;
import com.fortnight.domains.Transfer;
import com.fortnight.gateways.TransactionCreateGateway;
import com.fortnight.usecases.customers.CustomerCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class FortnightApplication implements CommandLineRunner {

    private final CustomerCreateUseCase customerCreateUseCase;
    private final TransactionCreateGateway transactionCreateGateway;

    public static void main(String[] args) {
        SpringApplication.run(FortnightApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        final var creditor = new Customer();
        creditor.setDocument("45001452813");
        creditor.setName("Renato Dantas");
        creditor.setCreation(Instant.now());
        creditor.setBalance(BigDecimal.TEN);

        final var debtor = new Customer();
        debtor.setDocument("45001452812");
        debtor.setName("Renato Vieira");
        debtor.setCreation(Instant.now());
        debtor.setBalance(BigDecimal.ZERO);
//
//        customerCreateUseCase.execute(creditor)
//                .log()
//                .subscribe();
//
//        customerCreateUseCase.execute(debtor)
//                .log()
//                .subscribe();

        final var transaction = new Transaction();
        transaction.setAmount(BigDecimal.TEN);
        transaction.setCorrelation(UUID.randomUUID().toString());
        transaction.setCustomer(creditor);
        transaction.setDate(Instant.now());
        transaction.setType(TransactionType.TRANSFER);

        final var transfer = new Transfer();
        transfer.setCreditor(creditor.getDocument());
        transfer.setDebtor(debtor.getDocument());
        transfer.setTransaction(transaction);
    }
}

