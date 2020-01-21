package org.swampscottcurrents.serpentframework;

public class Waypoint {
    
    private static byte pointCount;

    public final byte id;
    public double xPosition;
    public double yPosition;
    public WaypointType actionType;

    public Waypoint() {
        id = pointCount;
        actionType = WaypointType.NAVIGATION;
        pointCount++;
    }

    public Waypoint(WaypointType action, double x, double y) {
        id = pointCount;
        xPosition = x;
        yPosition = y;
        actionType = action;
        pointCount++;
    }

    /** Gets the absolute angle between two Waypoints, where Waypoint a is the origin. */
    public static Quaternion2D getAngle(Waypoint a, Waypoint b) {
        return Quaternion2D.fromAxis(b.xPosition - a.xPosition, b.yPosition - a.yPosition);
    }

    /** Gets the distance between two Waypoints. */
    public static double getDistance(Waypoint a, Waypoint b) {
        return Math.sqrt(square(a.xPosition - b.xPosition) + square(a.yPosition - b.yPosition));
    }

    private static double square(double a) {
        return a * a;
    }
}