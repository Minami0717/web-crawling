package com.minami.webcrawling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(CrawlingExample.class)
public class CrawlingTest {
    @Autowired
    private CrawlingExample e;

    @Test
    void test() {
        e.crawling();
    }
}
