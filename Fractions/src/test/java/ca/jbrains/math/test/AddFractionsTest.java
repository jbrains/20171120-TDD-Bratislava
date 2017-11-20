package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Test;

public class AddFractionsTest {
    @Test
    public void zeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        final Fraction sum = zero.plus(zero);
        Assert.assertEquals(0, sum.getNumerator());
        Assert.assertEquals(1, sum.getDenominator());
    }

    @Test
    public void notZeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        final Fraction four = new Fraction(4);
        final Fraction sum = four.plus(zero);
        Assert.assertEquals(4, sum.getNumerator());
        Assert.assertEquals(1, sum.getDenominator());
    }

    @Test
    public void zeroPlusNotZero() throws Exception {
        final Fraction sum = new Fraction(0).plus(new Fraction(7));
        Assert.assertEquals(7, sum.getNumerator());
        Assert.assertEquals(1, sum.getDenominator());
    }

    @Test
    public void notZeroPlusNotZeroAllIntegers() throws Exception {
        final Fraction sum = new Fraction(3).plus(new Fraction(9));
        Assert.assertEquals(12, sum.getNumerator());
        Assert.assertEquals(1, sum.getDenominator());
    }

    @Test
    public void notIntegersWithSameDenominator() throws Exception {
        final Fraction sum = new Fraction(1, 5).plus(new Fraction(2, 5));
        Assert.assertEquals(3, sum.getNumerator());
        Assert.assertEquals(5, sum.getDenominator());
    }

    @Test
    public void denominatorsWithNoCommonFactors() throws Exception {
        final Fraction sum = new Fraction(3, 2).plus(new Fraction(4, 7));
        Assert.assertEquals(29, sum.getNumerator());
        Assert.assertEquals(14, sum.getDenominator());
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
            if (this.denominator == that.denominator)
                return new Fraction(this.numerator + that.numerator, this.denominator);
            else
                return new Fraction(
                        this.numerator * that.denominator + that.numerator * this.denominator,
                        this.denominator * that.denominator);
        }

        public int getNumerator() {
            return numerator;
        }

        public int getDenominator() {
            return denominator;
        }
    }
}
