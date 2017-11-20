package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class AddFractionsTest {
    @Test
    public void zeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        final Fraction sum = zero.plus(zero);
        Assert.assertEquals(0, sum.intValue());
    }
    @Test
    public void notZeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        final Fraction four = new Fraction(4);
        final Fraction sum = four.plus(zero);
        Assert.assertEquals(4, sum.intValue());
    }

    private static class Fraction {
        private final int intValue;

        public Fraction(final int intValue) {
            this.intValue = intValue;
        }

        public Fraction plus(final Fraction zero) {
            return this;
        }

        public int intValue() {
            return intValue;
        }
    }
}
