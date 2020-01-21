package org.swampscottcurrents.serpentframework;

import java.nio.ByteBuffer;

import edu.wpi.first.networktables.NetworkTableInstance;

public final class NetworkBinding {
    /** Pushes a set of Waypoints to NetworkTables/Shuffleboard. */
    public static void setWaypoints(Waypoint[] points) {
        ByteBuffer byteList = ByteBuffer.allocate(18 * points.length);
        for (int x = 0; x < points.length; x++) {
            byteList.put(points[x].id);
            byteList.put(points[x].actionType.getValue());
            byteList.putDouble(points[x].xPosition);
            byteList.putDouble(points[x].yPosition);
        }
        NetworkTableInstance.getDefault().getEntry("waypoints").setRaw(byteList.array());
    }
}