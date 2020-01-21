package org.swampscottcurrents.serpentframework;

public enum WaypointType {
    NAVIGATION((byte)0),
    GRAB_PANEL((byte)1),
    PLACE_PANEL((byte)2);

    private final byte value;

    WaypointType(final byte newValue) {
        value = newValue;
    }

    public byte getValue() { return value; }
    
    public static WaypointType fromValue(byte val) {
        WaypointType[] possibles = WaypointType.values();
            for(int i = 0; i < possibles.length; i++)
            {
                if(possibles[i].getValue() == val) {
                    return possibles[i];
                }
            }
        throw new IllegalArgumentException("val");
    }
}