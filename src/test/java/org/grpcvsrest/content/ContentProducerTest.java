package org.grpcvsrest.content;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentProducerTest {

    @Test
    public void testProduce() throws IOException {
        ContentProducer producer = new ContentProducer("resource.txt");
        assertThat(producer.next()).isEqualTo("foo");
        assertThat(producer.next()).isEqualTo("bar");
        assertThat(producer.next()).isNull();
    }

    @Test
    public void testContent() throws IOException {
        ContentProducer producer = new ContentProducer("resource.txt");
        assertThat(producer.content()).containsExactly("foo", "bar");
    }

    @Test
    public void testSetCallback() throws IOException, InterruptedException {
        ContentProducer producer = new ContentProducer("resource.txt");
        CountDownLatch latch = new CountDownLatch(2);
        List<String> result = new LinkedList<>();
        producer.setCallback(s -> {
            result.add(s);
            latch.countDown();
        });

        latch.await(1000, TimeUnit.MILLISECONDS);
        assertThat(result).containsOnly("foo", "bar");
    }
}
