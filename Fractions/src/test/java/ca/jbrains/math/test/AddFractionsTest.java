package ca.jbrains.math.test;

import org.junit.Assert;
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

    @Test
    public void zeroPlusNotZero() throws Exception {
        final Fraction sum = new Fraction(0).plus(new Fraction(7));
        Assert.assertEquals(7, sum.intValue());
    }

    @Test
    public void notZeroPlusNotZero() throws Exception {
        final Fraction sum = new Fraction(3).plus(new Fraction(9));
        Assert.assertEquals(12, sum.intValue());
    }

    private static class Fraction {
        private final int intValue;

        public Fraction(final int intValue) {
            this.intValue = intValue;
        }

        public Fraction plus(final Fraction that) {
            if (this.intValue != 0 && that.intValue != 0) {
                return new Fraction(this.intValue + that.intValue);
            }
            else {
                if (that.intValue == 0)
                    return this;
                else
                    return that;
            }
        }

        public int intValue() {
            return intValue;
        }
    }
}
