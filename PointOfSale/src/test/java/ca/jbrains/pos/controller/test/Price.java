package ca.jbrains.pos.controller.test;

public class Price {
    public static Price euroCents(final int euroCents) {
        return new Price();
    }

    @Override
    public String toString() {
        return "a Price";
    }
}
