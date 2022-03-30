package com.sportsdirect.lv.helpers;

import com.google.common.base.CharMatcher;

import java.math.BigInteger;

public class PriceHelper {

    private PriceHelper() {
        throw new IllegalStateException();
    }

    public static BigInteger stringToBigInt(final String textInput) {
        final String digits = CharMatcher.inRange('0', '9').retainFrom(textInput);
        return BigInteger.valueOf(Integer.parseInt(digits));
    }
}
