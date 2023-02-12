package org.sembeacon.android;

import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.net.MalformedURLException;

public class SemBeaconURICompressor extends UrlBeaconUrlCompressor {
    private static final String TAG = "SemBeaconURICompressor";

    public static byte[] compress(String urlString) throws MalformedURLException {
        byte[] data = UrlBeaconUrlCompressor.compress(urlString);
        // Currently the dependency does not support extended advertisements
        return data;
    }

    public static String uncompress(byte[] compressedURL) {
        String uri = UrlBeaconUrlCompressor.uncompress(compressedURL);
        // Currently the dependency does not support extended advertisements
        return uri;
    }
}
