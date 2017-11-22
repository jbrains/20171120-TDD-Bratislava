package ca.jbrains.pos.ui.test;

import ca.jbrains.pos.ui.ConsumeTextAsLines;
import ca.jbrains.pos.ui.NormalizeStreamsOfText;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ConsumeAndInterpretCommandsTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private final InterpretCommand interpretCommand = context.mock(InterpretCommand.class);

    private static String unlines(final List<CharSequence> linesOfText) {
        return String.join(System.lineSeparator(), linesOfText);
    }

    @Test
    public void noCommands() throws Exception {
        context.checking(new Expectations() {{
            never(interpretCommand);
        }});

        final Stream<String> lines = Collections.<String> emptyList().stream();
        interpretLinesAsCommands(lines);
    }

    @Test
    public void oneCommand() throws Exception {
        context.checking(new Expectations() {{
            oneOf(interpretCommand).interpretCommand("127638");
        }});

        final Stream<String> lines = Collections.singletonList("127638").stream();
        interpretLinesAsCommands(lines);
    }

    @Test
    public void manyCommands() throws Exception {
        context.checking(new Expectations() {{
            oneOf(interpretCommand).interpretCommand("::barcode 1::");
            oneOf(interpretCommand).interpretCommand("::barcode 2::");
            oneOf(interpretCommand).interpretCommand("::barcode 3::");
        }});

        final Stream<String> lines = Arrays.asList(
                "::barcode 1::",
                "::barcode 2::",
                "::barcode 3::").stream();
        interpretLinesAsCommands(lines);
    }

    @Test
    public void someCommandsAreEmpty() throws Exception {
        context.checking(new Expectations() {{
            oneOf(interpretCommand).interpretCommand("::barcode 1::");
            oneOf(interpretCommand).interpretCommand("::barcode 2::");
            oneOf(interpretCommand).interpretCommand("::barcode 3::");
        }});

        final Stream<String> lines = Arrays.asList(
                "",
                "::barcode 1::",
                "",
                "",
                "::barcode 2::",
                "",
                "",
                "",
                "::barcode 3::",
                "").stream();
        interpretLinesAsCommands(lines);
    }

    @Test
    public void manyCommandsWithSomeWhitespaceAllOverThePlace() throws Exception {
        context.checking(new Expectations() {{
            oneOf(interpretCommand).interpretCommand("::barcode 1::");
            oneOf(interpretCommand).interpretCommand("::barcode 2::");
            oneOf(interpretCommand).interpretCommand("::barcode 3::");
        }});

        final Stream<String> lines = Arrays.asList(
                "   \r    \n   ",
                "     ::barcode 1::",
                "",
                "::barcode 2::    ",
                "",
                "\t     \t ::barcode 3::\t\t",
                "    \t\t      \t\t     ").stream();

        interpretLinesAsCommands(lines);
    }

    private void interpretLinesAsCommands(final Stream<String> lines) {
        normalize(lines).forEach(interpretCommand::interpretCommand);
    }

    private Stream<String> normalize(final Stream<String> lines) {
        return NormalizeStreamsOfText.removeWhitespace(lines);
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
