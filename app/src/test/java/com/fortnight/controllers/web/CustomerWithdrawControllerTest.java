package com.fortnight.controllers.web;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.IntegrationTest;
import com.fortnight.controllers.web.requests.CustomerRequest;
import com.fortnight.controllers.web.requests.DepositRequest;
import com.fortnight.controllers.web.requests.WithdrawRequest;
import com.fortnight.templates.CustomerRequestTemplate;
import com.fortnight.templates.DepositRequestTemplate;
import com.fortnight.templates.WithdrawRequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import static java.lang.String.format;

class CustomerWithdrawControllerTest extends IntegrationTest {

    private static final String CREATE_CUSTOMER_URI = "/customers";
    private static final String CREATE_DEPOSIT_URI = "/customers/%s/deposits";
    private static final String CREATE_WITHDRAW_URI = "/customers/%s/withdraws";

    @Test
    public void testCreateWithdrawWithSuccess() {
        final WithdrawRequest request = Fixture.from(WithdrawRequest.class)
                .gimme(WithdrawRequestTemplate.Templates.VALID.name());

        final var customer = this.createCustomer();
        final var deposit = this.createDeposit(customer.getDocument());

        // update deposit withdraw
        request.setAmount(deposit.getAmount());

        this.webTestClient
                .post()
                .uri(format(CREATE_WITHDRAW_URI, customer.getDocument()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testWithdrawWhenNotEnough() {
        final WithdrawRequest request = Fixture.from(WithdrawRequest.class)
                .gimme(WithdrawRequestTemplate.Templates.VALID.name());

        final var customer = this.createCustomer();

        this.webTestClient
                .post()
                .uri(format(CREATE_WITHDRAW_URI, customer.getDocument()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isForbidden();
    }

    @ParameterizedTest
    @EnumSource(value = WithdrawRequestTemplate.Templates.class, names = {"INVALID_AMOUNT", "INVALID_CORRELATION"})
    public void testCreatWithdrawWitInvalidRequest(WithdrawRequestTemplate.Templates template) {
        final WithdrawRequest request = Fixture.from(WithdrawRequest.class).gimme(template.name());

        final var customer = this.createCustomer();
        final var deposit = this.createDeposit(customer.getDocument());

        this.webTestClient
                .post()
                .uri(format(CREATE_WITHDRAW_URI, customer.getDocument()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest();
    }

    private DepositRequest createDeposit(final String document) {
        final DepositRequest request = Fixture.from(DepositRequest.class)
                .gimme(DepositRequestTemplate.Templates.VALID.name());

        this.webTestClient
                .post()
                .uri(format(CREATE_DEPOSIT_URI, document))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        return request;
    }

    private CustomerRequest createCustomer() {
        final CustomerRequest request = Fixture.from(CustomerRequest.class)
                .gimme(CustomerRequestTemplate.Templates.VALID.name());

        this.webTestClient
                .post()
                .uri(CREATE_CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        return request;
    }
}
