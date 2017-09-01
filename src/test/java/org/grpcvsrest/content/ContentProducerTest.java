package org.grpcvsrest.content;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentProducerTest {

    @Test
    public void testProduce() throws IOException {
        ContentProducer producer = new ContentProducer("resource.txt");
        assertThat(producer.next()).isEqualTo("foo");
        assertThat(producer.next()).isEqualTo("bar");
        assertThat(producer.next()).isNull();
    }
}
