package com.fortnight.controllers.web;

import br.com.six2six.fixturefactory.Fixture;
import com.fortnight.IntegrationTest;
import com.fortnight.controllers.web.requests.CustomerRequest;
import com.fortnight.templates.CustomerRequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

class CustomerCreateControllerTest extends IntegrationTest {

    private static final String CREATE_CUSTOMER_URI = "/customers";

    @Test
    public void testCreateCustomerWithSuccess() {
        final CustomerRequest request = Fixture.from(CustomerRequest.class).gimme(CustomerRequestTemplate.Templates.VALID.name());

        this.webTestClient
                .post()
                .uri(CREATE_CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();
    }

    @ParameterizedTest
    @EnumSource(value = CustomerRequestTemplate.Templates.class, names = {"WITHOUT_NAME", "WITHOUT_DOCUMENT"})
    public void testCreateCustomerWithInvalidRequest(final CustomerRequestTemplate.Templates template) {
        final CustomerRequest request = Fixture.from(CustomerRequest.class).gimme(template.name());

        this.webTestClient
                .post()
                .uri(CREATE_CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testCreateCustomerWhenDocumentAlreadyExists() {
        final CustomerRequest request = Fixture.from(CustomerRequest.class).gimme(CustomerRequestTemplate.Templates.VALID.name());

        // create customer...
        this.webTestClient
                .post()
                .uri(CREATE_CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        // retry create...
        this.webTestClient
                .post()
                .uri(CREATE_CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }
}