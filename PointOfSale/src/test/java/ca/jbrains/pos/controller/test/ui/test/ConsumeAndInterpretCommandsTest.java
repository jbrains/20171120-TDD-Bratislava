package ca.jbrains.pos.controller.test.ui.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

public class ConsumeAndInterpretCommandsTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private final BarcodeScannedListener barcodeScannedListener = context.mock(BarcodeScannedListener.class);

    @Test
    public void noCommands() throws Exception {
        context.checking(new Expectations() {{
            never(barcodeScannedListener);
        }});

        consumeAndInterpretCommands(new StringReader(unlines(Collections.emptyList())));
    }

    private static String unlines(final List<CharSequence> linesOfText) {
        return String.join(System.lineSeparator(), linesOfText);
    }

    private void consumeAndInterpretCommands(final Reader reader) {
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
