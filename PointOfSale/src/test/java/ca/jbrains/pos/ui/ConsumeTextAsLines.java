package ca.jbrains.pos.ui;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

public class ConsumeTextAsLines {
    public static Stream<String> consumeTextAsLines(final Reader reader) {
        return new BufferedReader(reader).lines();
    }
}
