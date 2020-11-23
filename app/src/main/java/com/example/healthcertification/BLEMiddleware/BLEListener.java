package com.example.healthcertification.BLEMiddleware;

import org.altbeacon.beacon.Beacon;

public interface BLEListener {
    public void onBLEDataAvailable(Beacon beacon, String data);
}
