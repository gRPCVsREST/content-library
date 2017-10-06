package org.grpcvsrest.content;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Periodically (with a pseudo-random rate) streams values returned by {@link ContentProducer#next()}}
 * to a provided instance if {@link Consumer<String>}.
 */
public class ContentStreamer {

    private final ContentProducer contentProducer;
    private final Consumer<String> consumer;

    public ContentStreamer(ContentProducer contentProducer, Consumer<String> consumer, long randomDelayInMillis) {
        this.contentProducer = contentProducer;
        this.consumer = consumer;
        Timer timer = new Timer();
        timer.schedule(new ContentStreamer.GenerateContentTask(), randomDelayInMillis, randomDelayInMillis);
    }

    public ContentStreamer(ContentProducer contentProducer, Consumer<String> consumer) {
        this(contentProducer, consumer, TimeUnit.SECONDS.toMillis(1 + new Random().nextInt(4)));
    }

    private class GenerateContentTask extends TimerTask {
        @Override
        public void run() {
            String value = contentProducer.next();
            if (value != null) {
                consumer.accept(value);
            }
        }
    }
}
