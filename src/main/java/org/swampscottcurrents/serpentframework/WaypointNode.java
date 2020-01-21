package org.swampscottcurrents.serpentframework;

public class WaypointNode {
    public Waypoint point;
    public WaypointNode[] connectedPoints;

    public WaypointNode() {}
    public WaypointNode(Waypoint a) {
        point = a;
    }
    public WaypointNode(Waypoint a, WaypointNode[] nodes) {
        point = a;
        connectedPoints = nodes;
    }
}