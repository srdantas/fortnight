package com.fortnight.controllers.web;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.IntegrationTest;
import com.fortnight.controllers.web.requests.CustomerRequest;
import com.fortnight.templates.CustomerRequestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import static org.hamcrest.core.Is.is;

class CustomerSearchControllerTest extends IntegrationTest {

    private static final String CREATE_CUSTOMER_URI = "/customers";

    @Test
    public void testSearchCustomerThatExistsItIsReturnWithSuccess() {
        final CustomerRequest request = Fixture.from(CustomerRequest.class).gimme(CustomerRequestTemplate.Templates.VALID.name());

        // create customer
        this.webTestClient
                .post()
                .uri(CREATE_CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        // search customer create
        this.webTestClient
                .get()
                .uri(CREATE_CUSTOMER_URI + "/" + request.getDocument())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").value(is(request.getName()))
                .jsonPath("balance").value(is(0.0));
    }

    @Test
    public void testSearchCustomerThatNotExistsReturnNotFound() {
        final CustomerRequest request = Fixture.from(CustomerRequest.class).gimme(CustomerRequestTemplate.Templates.VALID.name());

        this.webTestClient
                .get()
                .uri(CREATE_CUSTOMER_URI + "/" + request.getDocument())
                .exchange()
                .expectStatus().isNotFound();
    }

}