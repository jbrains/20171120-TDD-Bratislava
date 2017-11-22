package ca.jbrains.pos.ui;

import java.util.stream.Stream;

public class NormalizeStreamsOfText {
    public static Stream<String> removeWhitespace(final Stream<String> dirtyLines) {
        return dirtyLines.map(
                line -> line.trim()
        ).filter(
                line -> !line.isEmpty()
        );
    }
}
