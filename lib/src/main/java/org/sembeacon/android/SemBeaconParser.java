package org.sembeacon.android;

import android.bluetooth.BluetoothDevice;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

public class SemBeaconParser extends BeaconParser {
    public static final String TAG = "SemBeaconParser";
    // SemBeacon layout extends AltBeacon with an additional flag to indicate it includes a different scan response
    public static final String SEMBEACON_LAYOUT = "m:2-3=beac,i:4-19,i:20-23,p:24-24,d:25-25";
    // Optional scan response layout compatible with Eddystone-URL
    public static final String SEMBEACON_SCAN_RESPONSE_LAYOUT = "x,s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-21v";

    /**
     * Constructs an SemBeacon Parser and sets its layout
     */
    public SemBeaconParser() {
        super();
        this.setBeaconLayout(SemBeaconParser.SEMBEACON_LAYOUT);
        this.addExtraDataParser(new BeaconParser().setBeaconLayout(SEMBEACON_SCAN_RESPONSE_LAYOUT));
        this.mIdentifier = "sembeacon";
    }

    /**
     * Construct an SemBeacon from a Bluetooth LE packet collected by Android's Bluetooth APIs,
     * including the raw Bluetooth device info and scan response data
     *
     * @param scanData The actual packet bytes
     * @param rssi The measured signal strength of the packet
     * @param device The Bluetooth device that was detected
     * @param timestampMs The timestamp in milliseconds of the scan execution
     * @return An instance of an <code>SemBeacon</code>
     */
    @Override
    public SemBeacon fromScanData(byte[] scanData, int rssi, BluetoothDevice device, long timestampMs) {
        SemBeacon beacon = (SemBeacon) super.fromScanData(scanData, rssi, device, timestampMs, new SemBeacon());
        if (beacon != null && this.extraParsers.size() > 0) {
            BeaconParser extraParser = this.getExtraDataParsers().get(0);
            Beacon extraBeaconData = extraParser.fromScanData(scanData, rssi, device, timestampMs);
            if (extraBeaconData != null) {
                String url = UrlBeaconUrlCompressor.uncompress(extraBeaconData.getId1().toByteArray());
                return new SemBeacon.Builder(beacon)
                        .setUri(url)
                        .build();
            }
        }
        return beacon;
    }
}
