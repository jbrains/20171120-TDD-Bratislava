package ca.jbrains.pos.model.test;

import ca.jbrains.pos.controller.test.Catalog;
import ca.jbrains.pos.controller.test.Price;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class FindPriceInMemoryCatalogTest extends FindPriceInMemoryCatalogContract {

    @Override
    protected Catalog catalogWith(final String barcode, final Price matchingPrice) {
        return new InMemoryCatalog(
                Collections.singletonMap(barcode, matchingPrice));
    }

    @Override
    protected Catalog catalogWithout(final String barcodeToAvoid) {
        return new InMemoryCatalog(Collections.emptyMap());
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(final Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(final String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
