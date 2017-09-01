package org.grpcvsrest.content;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ContentProducer {
    private final List<String> content;
    private int position = 0;
    public ContentProducer(String resourcePath) throws IOException {
        content = Collections.unmodifiableList(
                Resources.readLines(Resources.getResource(resourcePath), Charsets.UTF_8));
    }

    public synchronized String next() {
        if (position > content.size() - 1) {
            return null;
        }

        return content.get(position++);
    }
}
