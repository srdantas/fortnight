package com.fortnight;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public abstract class UnitTest {

    @BeforeAll
    public static void before() {
        FixtureFactoryLoader.loadTemplates("com.fortnight.templates");
    }
}
