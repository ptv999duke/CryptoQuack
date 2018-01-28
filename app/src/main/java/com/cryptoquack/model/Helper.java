package com.cryptoquack.model;

/**
 * Created by Duke on 1/27/2018.
 */

public class Helper {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = Helper.hexArray[v >>> 4];
            hexChars[j * 2 + 1] = Helper.hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }
}
