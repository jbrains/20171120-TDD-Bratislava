package ca.jbrains.pos.controller.test.ui.test;

import ca.jbrains.pos.ui.NormalizeStreamsOfText;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ConsumeAndInterpretCommandsTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private final BarcodeScannedListener barcodeScannedListener = context.mock(BarcodeScannedListener.class);

    private static String unlines(final List<CharSequence> linesOfText) {
        return String.join(System.lineSeparator(), linesOfText);
    }

    @Test
    public void noCommands() throws Exception {
        context.checking(new Expectations() {{
            never(barcodeScannedListener);
        }});

        consumeAndInterpretCommands(new StringReader(unlines(Collections.emptyList())));
    }

    @Test
    public void oneCommand() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("127638");
        }});

        consumeAndInterpretCommands(new StringReader(unlines(Collections.singletonList("127638"))));
    }

    @Test
    public void manyCommands() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
        }});

        consumeAndInterpretCommands(new StringReader(unlines(Arrays.asList(
                "::barcode 1::",
                "::barcode 2::",
                "::barcode 3::"))));
    }

    @Test
    public void someCommandsAreEmpty() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
        }});

        consumeAndInterpretCommands(new StringReader(unlines(Arrays.asList(
                "",
                "::barcode 1::",
                "",
                "",
                "::barcode 2::",
                "",
                "",
                "",
                "::barcode 3::",
                ""))));
    }

    @Test
    public void manyCommandsWithSomeWhitespaceAllOverThePlace() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
        }});

        consumeAndInterpretCommands(new StringReader(unlines(Arrays.asList(
                "   \r    \n   ",
                "     ::barcode 1::",
                "",
                "::barcode 2::    ",
                "",
                "\t     \t ::barcode 3::\t\t",
                "    \t\t      \t\t     "))));
    }

    private void consumeAndInterpretCommands(final Reader reader) throws IOException {
        normalize(consumeTextAsLines(reader)).forEach(this::interpretCommand);
    }

    // CONTRACT: the command text is "normalized"
    private void interpretCommand(String text) {
        barcodeScannedListener.onBarcode(text);
    }

    private Stream<String> consumeTextAsLines(final Reader reader) {
        return new BufferedReader(reader).lines();
    }

    private Stream<String> normalize(final Stream<String> lines) {
        return NormalizeStreamsOfText.removeWhitespace(lines);
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
