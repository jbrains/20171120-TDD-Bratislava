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
        final Sale sale = new Sale(display, new Catalog(new HashMap<String, String>() {{
            put("12345", "€ 7,95");
        }}));

        sale.onBarcode("12345");

        Assert.assertEquals("€ 7,95", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(new HashMap<String, String>() {{
            put("12345", "€ 7,95");
            put("23456", "€ 12,50");
        }}));

        sale.onBarcode("23456");

        Assert.assertEquals("€ 12,50", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(new HashMap<String, String>() {{
            put("12345", "€ 7,95");
            put("23456", "€ 12,50");
        }}));

        sale.onBarcode("99999");

        Assert.assertEquals("Product not found for 99999", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(Collections.emptyMap()));

        sale.onBarcode("");

        Assert.assertEquals("Scanning error: empty barcode", display.getText());
    }

    private static class Sale {
        private final Display display;
        private final Catalog catalog;

        private Sale(final Display display, final Catalog catalog) {
            this.display = display;
            this.catalog = catalog;
        }

        public void onBarcode(final String barcode) {
            if ("".equals(barcode))
                display.displayEmptyBarcodeMessage();
            else if (catalog.hasBarcode(barcode))
                display.displayPrice(catalog.findPrice(barcode));
            else
                display.displayProductNotFoundMessage(barcode);
        }
    }

    private static class Display {
        private String text;

        public String getText() {
            return text;
        }

        private void displayEmptyBarcodeMessage() {
            this.text = "Scanning error: empty barcode";
        }

        private void displayPrice(final String price) {
            this.text = price;
        }

        private void displayProductNotFoundMessage(final String barcode) {
            this.text = String.format("Product not found for %s", barcode);
        }
    }

    private static class Catalog {
        private final Map<String, String> pricesByBarcode;

        public Catalog(final Map<String, String> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public boolean hasBarcode(final String barcode) {
            return this.pricesByBarcode.containsKey(barcode);
        }

        public String findPrice(final String barcode) {
            return this.pricesByBarcode.get(barcode);
        }
    }
}
