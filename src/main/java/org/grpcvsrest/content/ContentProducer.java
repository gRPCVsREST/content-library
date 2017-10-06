package org.grpcvsrest.content;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContentProducer implements Iterator<String> {
    private final List<String> content;
    private final AtomicInteger position = new AtomicInteger(0);

    public ContentProducer(String resourcePath) throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource(resourcePath), Charsets.UTF_8);
        ArrayList<String> list = Lists.newArrayList(lines);
        Collections.shuffle(list);
        content = Collections.unmodifiableList(list);
    }

    @Override
    public boolean hasNext() {
        int index = position.get();
        return hasNext(index);
    }

    @Nullable
    public String next() {
        int index = position.getAndIncrement();
        return hasNext(index) ? content.get(index) : null;
    }

    public List<String> content() {
        return content;
    }

    private boolean hasNext(int index) {
        return index < content.size();
    }
}
