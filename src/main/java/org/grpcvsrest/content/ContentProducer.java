package org.grpcvsrest.content;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ContentProducer {
    private final List<String> content;
    private final AtomicInteger position = new AtomicInteger(-1);
    private final Random random = new Random();

    public ContentProducer(String resourcePath) throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource(resourcePath), Charsets.UTF_8);
        ArrayList<String> list = Lists.newArrayList(lines);
        Collections.shuffle(list);
        content = Collections.unmodifiableList(list);
    }

    public void setCallback(Consumer<String> func) {
        for (String s : content) {
            try {
                Thread.sleep(random.nextInt(200));
                func.accept(s);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nullable
    public String next() {
        int index = position.accumulateAndGet(0, (x,y) -> (x+1) % content.size());
        return content.get(index);
    }

    List<String> content() {
        return content;
    }
}
