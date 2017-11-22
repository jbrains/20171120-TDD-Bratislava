package ca.jbrains.pos.controller.test.ui.test;

import ca.jbrains.pos.ui.NormalizeStreamsOfText;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveWhitespaceTest {
    @Test
    public void oneBigScaryTest() throws Exception {
        final List<String> dirtyLines = Arrays.asList(
                "   \r    \n   ",
                "     ::barcode 1::",
                "",
                "::barcode 2::    ",
                "",
                "\t     \t ::barcode 3::\t\t",
                "    \t\t      \t\t     ");

        final List<String> cleanLines = Arrays.asList(
                "::barcode 1::",
                "::barcode 2::",
                "::barcode 3::");

        Assert.assertEquals(
                cleanLines,
                NormalizeStreamsOfText.removeWhitespace(dirtyLines.stream())
                        .collect(Collectors.toList()));
    }
}
