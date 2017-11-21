package ca.jbrains.pos.controller.test;

import org.junit.Assert;
import org.junit.Test;

public class SellOneItemControllerTest {
    private boolean displayPriceInvoked;
    private boolean displayProductNotFoundMessageInvoked;
    private boolean displayEmptyBarcodeMessageInvoked;

    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.euroCents(795);

        Catalog catalog = new Catalog() {
            public Price findPrice(String barcode) {
                return matchingPrice;
            }
        };

        Display display = new Display() {
            public void displayPrice(Price price) {
                Assert.assertEquals(matchingPrice, price);
                SellOneItemControllerTest.this.displayPriceInvoked = true;
            }

            @Override
            public void displayProductNotFoundMessage(final String barcode) {
                // I DON'T CARE
            }

            @Override
            public void displayEmptyBarcodeMessage() {
                // I DON'T CARE
            }
        };

        new Sale(catalog, display).onBarcode("::any barcode::");

        Assert.assertTrue(displayPriceInvoked);
    }

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = new Catalog() {
            public Price findPrice(String barcode) {
                return null;
            }
        };

        final String barcodeNotFound = "::any barcode without a matching price::";

        Display display = new Display() {
            @Override
            public void displayPrice(final Price price) {
                // I DON'T CARE
            }

            public void displayProductNotFoundMessage(String barcode) {
                Assert.assertEquals(barcodeNotFound, barcode);
                SellOneItemControllerTest.this.displayProductNotFoundMessageInvoked = true;
            }

            @Override
            public void displayEmptyBarcodeMessage() {
                // I DON'T CARE
            }
        };

        new Sale(catalog, display).onBarcode(barcodeNotFound);

        Assert.assertTrue(displayProductNotFoundMessageInvoked);
    }

    @Test
    public void emptyBarcode() throws Exception {
        Display display = new Display() {
            @Override
            public void displayPrice(final Price price) {
                // I DON'T CARE
            }

            public void displayProductNotFoundMessage(String barcode) {
            }

            public void displayEmptyBarcodeMessage() {
                SellOneItemControllerTest.this.displayEmptyBarcodeMessageInvoked = true;
            }
        };

        new Sale(null, display).onBarcode("");

        Assert.assertTrue(displayEmptyBarcodeMessageInvoked);
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
