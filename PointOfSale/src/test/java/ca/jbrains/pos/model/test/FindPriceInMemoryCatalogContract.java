package ca.jbrains.pos.model.test;

import ca.jbrains.pos.controller.test.Catalog;
import ca.jbrains.pos.controller.test.Price;
import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInMemoryCatalogContract {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.euroCents(795);
        Assert.assertEquals(
                matchingPrice,
                catalogWith("12345", matchingPrice).findPrice("12345"));
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    public void productNotFound() throws Exception {
        final Catalog catalog = catalogWithout("12345");
        Assert.assertEquals(null, catalog.findPrice("12345"));
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
