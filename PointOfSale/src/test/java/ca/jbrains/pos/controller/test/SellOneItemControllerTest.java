package ca.jbrains.pos.controller.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SellOneItemControllerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    
    private Display display;

    @Before
    public void setUp() throws Exception {
        display = context.mock(Display.class);
    }

    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.euroCents(795);

        final Catalog catalog = (ignoredBarcode) -> matchingPrice;
        context.checking(new Expectations() {{
            oneOf(display).displayPrice(with(matchingPrice));
        }});

        new Sale(catalog, display).onBarcode("::any barcode::");
    }

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = (ignoredBarcode) -> null;
        context.checking(new Expectations() {{
            oneOf(display).displayProductNotFoundMessage(with("::any barcode without a matching price::"));
        }});

        new Sale(catalog, display).onBarcode("::any barcode without a matching price::");
    }

    @Test
    public void emptyBarcode() throws Exception {
        context.checking(new Expectations() {{
            oneOf(display).displayEmptyBarcodeMessage();
        }});

        new Sale(null, display).onBarcode("");
    }

    public interface Catalog {
        Price findPrice(String barcode);
    }

    public interface Display {
        void displayPrice(Price price);

        void displayProductNotFoundMessage(String barcode);

        void displayEmptyBarcodeMessage();
    }

    public static class Sale {
        private final Catalog catalog;
        private final Display display;

        public Sale(final Catalog catalog, final Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(final String barcode) {
            if ("".equals(barcode)) {
                display.displayEmptyBarcodeMessage();
                return;
            }

            final Price price = catalog.findPrice(barcode);
            if (price == null)
                display.displayProductNotFoundMessage(barcode);
            else
                display.displayPrice(price);
        }
    }

    public static class Price {
        public static Price euroCents(final int euroCents) {
            return new Price();
        }
    }
}
