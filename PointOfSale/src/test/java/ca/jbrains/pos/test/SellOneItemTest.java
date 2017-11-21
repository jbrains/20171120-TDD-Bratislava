package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("12345", "€ 7,95");
        }});

        sale.onBarcode("12345");

        Assert.assertEquals("€ 7,95", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("12345", "€ 7,95");
            put("23456", "€ 12,50");
        }});

        sale.onBarcode("23456");

        Assert.assertEquals("€ 12,50", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("12345", "€ 7,95");
            put("23456", "€ 12,50");
        }});

        sale.onBarcode("99999");

        Assert.assertEquals("Product not found for 99999", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, Collections.emptyMap());

        sale.onBarcode("");

        Assert.assertEquals("Scanning error: empty barcode", display.getText());
    }

    private static class Sale {
        private final Display display;
        private final Map<String, String> pricesByBarcode;

        private Sale(final Display display, final Map<String, String> pricesByBarcode) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(final String barcode) {
            if ("".equals(barcode))
                display.setText("Scanning error: empty barcode");
            else if (pricesByBarcode.containsKey(barcode))
                display.setText(pricesByBarcode.get(barcode));
            else
                display.setText(String.format("Product not found for %s", barcode));
        }
    }

    private static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(final String text) {
            this.text = text;
        }
    }
}
