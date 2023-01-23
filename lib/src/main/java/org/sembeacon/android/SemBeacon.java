package org.sembeacon.android;

import android.os.Parcel;
import android.os.Parcelable;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SemBeacon extends Beacon {
    private static final String TAG = "SemBeacon";
    private String mUri = "";

    /**
     * Required for making object Parcelable.  If you override this class, you must provide an
     * equivalent version of this method.
     */
    public static final Parcelable.Creator<SemBeacon> CREATOR
            = new Parcelable.Creator<SemBeacon>() {
        public SemBeacon createFromParcel(Parcel in) {
            return new SemBeacon(in);
        }

        public SemBeacon[] newArray(int size) {
            return new SemBeacon[size];
        }
    };

    /**
     * Copy constructor from base class
     * @param beacon
     */
    protected SemBeacon(Beacon beacon) {
        super(beacon);
    }

    /**
     * @see SemBeacon.Builder to make SemBeacon instances
     */
    protected SemBeacon() {
        super();
    }

    /**
     * Required for making object Parcelable
     **/
    protected SemBeacon(Parcel in) {
        super(in);
        this.mUri = in.readString();
    }

    public byte getFlags() {
        return mExtraDataFields.get(0).byteValue();
    }

    public String getUri() {
        return this.mUri;
    }

    protected void setUri(String uri) {
        this.mUri = uri;
    }

    /**
     * Required for making object Parcelable
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Required for making object Parcelable
     **/
    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(this.mUri);
    }


    /**
     * Builder class for AltBeacon objects. Provides a convenient way to set the various fields of a
     * Beacon
     *
     * <p>Example:
     *
     * <pre>
     * Beacon beacon = new Beacon.Builder()
     *         .setId1(&quot;2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6&quot;)
     *         .setId2("1")
     *         .setId3("2")
     *         .build();
     * </pre>
     */
    public static class Builder extends Beacon.Builder {
        protected String mUri;

        public Builder(Beacon beacon) {
            super();
            this.setBeaconTypeCode(beacon.getBeaconTypeCode());
            this.setDataFields(beacon.getDataFields());
            this.setBluetoothAddress(beacon.getBluetoothAddress());
            this.setBluetoothName(beacon.getBluetoothName());
            this.setExtraDataFields(beacon.getExtraDataFields());
            this.setManufacturer(beacon.getManufacturer());
            this.setTxPower(beacon.getTxPower());
            this.setRssi(beacon.getRssi());
            this.setServiceUuid(beacon.getServiceUuid());
            this.setMultiFrameBeacon(true);
            List<Identifier> identifiers = beacon.getIdentifiers();
            if (identifiers.size() >= 3) {
                setId3(identifiers.get(2).toHexString());
            }
            if (identifiers.size() >= 2) {
                setId2(identifiers.get(1).toHexString());
            }
            if (identifiers.size() >= 1) {
                setId1(identifiers.get(0).toHexString());
            }
        }

        @Override
        public SemBeacon build() {
            SemBeacon beacon = new SemBeacon(super.build());
            if (this.mUri != null) {
                beacon.setUri(mUri);
            }
            return beacon;
        }

        public SemBeacon.Builder setNamespaceId(String id) {
            return (SemBeacon.Builder) super.setId1(id);
        }

        public SemBeacon.Builder setInstanceId(String id) {
            return (SemBeacon.Builder) super.setId2(id);
        }

        public SemBeacon.Builder setFlags(byte flags) {
            if (mBeacon.getDataFields().size() != 0) {
                super.setDataFields(new ArrayList<Long>());;
            }
            mBeacon.getDataFields().add((long) flags);
            return this;
        }

        public SemBeacon.Builder setUri(String uri) {
            this.mUri = uri;
            return this;
        }

    }

}