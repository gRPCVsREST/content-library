package org.grpcvsrest.content;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentProducerTest {

    @Test
    public void testProduce() throws IOException {
        ContentProducer producer = new ContentProducer("https://raw.githubusercontent.com/gRPCVsREST/content-library/master/src/main/resources/crypto.txt");
        assertThat(producer.next()).isEqualTo(producer.content().get(0));
        assertThat(producer.next()).isEqualTo(producer.content().get(1));
    }

    @Test
    public void testContent() throws IOException {
        ContentProducer producer = new ContentProducer("resource.txt");
        assertThat(producer.content()).containsOnly("foo", "bar");
    }

}
