package com.fortnight;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
public abstract class IntegrationTest {

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeAll
    public static void before() {
        FixtureFactoryLoader.loadTemplates("com.fortnight.templates");
    }
}
