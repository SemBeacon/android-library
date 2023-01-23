package org.sembeacon.android;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;

import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.logging.LogManager;

public class SemBeaconTransmitter extends BeaconTransmitter {
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private BluetoothAdapter mBluetoothAdapter;

    public SemBeaconTransmitter(Context context) {
        super(context, new SemBeaconParser());
        android.bluetooth.BluetoothManager bluetoothManager = (BluetoothManager)context.getSystemService("bluetooth");
        if (bluetoothManager != null) {
            this.mBluetoothAdapter = bluetoothManager.getAdapter();
            this.mBluetoothLeAdvertiser = this.mBluetoothAdapter.getBluetoothLeAdvertiser();
            LogManager.d("BeaconTransmitter", "new BeaconTransmitter constructed.  mbluetoothLeAdvertiser is %s", new Object[]{this.mBluetoothLeAdvertiser});
        } else {
            LogManager.e("BeaconTransmitter", "Failed to get BluetoothManager", new Object[0]);
        }
    }

    @Override
    public void startAdvertising() {
    }
}
