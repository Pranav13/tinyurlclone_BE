package com.example.tinyurlclone.util;

import java.math.BigInteger;

public class Base62 {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = BASE62.length();
    private static final int MIN_URL_LENGTH = 8; // Minimum length of the encoded part of the URL

    public static String encode(byte[] hashBytes) {
        // Convert the byte array to a BigInteger
        BigInteger bigInt = new BigInteger(1, hashBytes);
        StringBuilder encoded = new StringBuilder();

        // Encode the BigInteger value in Base62
        while (bigInt.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divideAndRemainder = bigInt.divideAndRemainder(BigInteger.valueOf(BASE));
            encoded.append(BASE62.charAt(divideAndRemainder[1].intValue()));
            bigInt = divideAndRemainder[0];
        }

        // Return the encoded string in reverse order
        return encoded.reverse().toString();
    }
}
