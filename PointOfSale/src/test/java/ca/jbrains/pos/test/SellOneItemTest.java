package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        final Sale sale = new Sale();
        final Display display = new Display();

        sale.onBarcode("12345");

        Assert.assertEquals("€ 7,95", display.getText());
    }

    private static class Sale {
        public void onBarcode(final String barcode) {

        }
    }

    private static class Display {
        public String getText() {
            return "€ 7,95";
        }
    }
}
