package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Test;

public class AddFractionsTest {
    @Test
    public void zeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        final Fraction sum = zero.plus(zero);
        Assert.assertEquals(new Fraction(0), sum);
    }

    @Test
    public void notZeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        final Fraction four = new Fraction(4);
        final Fraction sum = four.plus(zero);
        Assert.assertEquals(four, sum);
    }

    @Test
    public void zeroPlusNotZero() throws Exception {
        final Fraction sum = new Fraction(0).plus(new Fraction(7));
        Assert.assertEquals(new Fraction(7), sum);
    }

    @Test
    public void notZeroPlusNotZeroAllIntegers() throws Exception {
        final Fraction sum = new Fraction(3).plus(new Fraction(9));
        Assert.assertEquals(new Fraction(12), sum);
    }

    @Test
    public void notIntegersWithSameDenominator() throws Exception {
        final Fraction sum = new Fraction(1, 5).plus(new Fraction(2, 5));
        Assert.assertEquals(new Fraction(3, 5), sum);
    }

    @Test
    public void denominatorsWithNoCommonFactors() throws Exception {
        final Fraction sum = new Fraction(3, 2).plus(new Fraction(4, 7));
        Assert.assertEquals(new Fraction(29, 14), sum);
    }

    @Test
    public void unequalDenominatorsWithCommonFactors() throws Exception {
        Assert.assertEquals(
                new Fraction(19, 24),
                new Fraction(3, 8).plus(new Fraction(5, 12)));
    }

    private static class Fraction {
        private int numerator;
        private int denominator;

        public Fraction(final int intValue) {
            this(intValue, 1);
        }

        public Fraction(final int numerator, final int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction plus(final Fraction that) {
            return new Fraction(
                    this.numerator * that.denominator + that.numerator * this.denominator,
                    this.denominator * that.denominator);
        }

        public boolean equals(final Object other) {
            if (other instanceof Fraction) {
                Fraction that = (Fraction) other;
                return this.numerator * that.denominator == that.numerator * this.denominator;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            // SMELL This makes it suck to use Fractions as keys in a dictionary.
            return 762;
        }

        @Override
        public String toString() {
            return String.format("%d/%d", numerator, denominator);
        }
    }
}
