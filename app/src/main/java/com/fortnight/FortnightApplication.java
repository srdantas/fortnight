package com.fortnight;

import com.fortnight.gateways.database.mysql.entities.AccountEntity;
import com.fortnight.gateways.database.mysql.entities.BalanceEntity;
import com.fortnight.gateways.database.mysql.repositories.AccountRepository;
import com.fortnight.gateways.database.mysql.repositories.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;

@SpringBootApplication
@RequiredArgsConstructor
public class FortnightApplication implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    public static void main(String[] args) {
        SpringApplication.run(FortnightApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        val accountEntity = new AccountEntity();
        accountEntity.setName("Vieira Dantas");
        accountEntity.setDocument("1234567891078");
        accountEntity.setCreation(Instant.now());

        val balanceEntity = new BalanceEntity();
        balanceEntity.setAmount(BigDecimal.ONE);
        balanceEntity.setDocument(accountEntity.getDocument());

        accountEntity.setBalance(balanceEntity);

//        accountRepository.save(accountEntity);

//        balanceRepository.save(balanceEntity);

    }
}
