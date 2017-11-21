package ca.jbrains.pos.controller.test;

import org.junit.Assert;
import org.junit.Test;

public class SellOneItemControllerTest {
    private boolean displayPriceInvoked;

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
        };

        new Sale(catalog, display).onBarcode("::any barcode::");

        Assert.assertTrue(displayPriceInvoked);
    }

    public interface Catalog {
        Price findPrice(String barcode);
    }

    public interface Display {
        void displayPrice(Price price);
    }

    public static class Sale {
        private final Catalog catalog;
        private final Display display;

        public Sale(final Catalog catalog, final Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(final String barcode) {
            display.displayPrice(catalog.findPrice(barcode));
        }
    }

    public static class Price {
        public static Price euroCents(final int euroCents) {
            return new Price();
        }
    }
}
