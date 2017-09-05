package org.grpcvsrest.content;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class ContentProducer {
    private final List<String> content;
    private int position = 0;
    private final Random random = new Random();

    public ContentProducer(String resourcePath) throws IOException {
        content = Collections.unmodifiableList(
                Resources.readLines(Resources.getResource(resourcePath), Charsets.UTF_8));
    }

    public void setCallback(Consumer<String> func) {
        for(String s: content) {
            try {
                Thread.sleep(random.nextInt(200));
                func.accept(s);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized String next() {
        if (position > content.size() - 1) {
            return null;
        }

        return content.get(position++);
    }

    public List<String> content() {
        return content;
    }
}
