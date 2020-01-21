package org.swampscottcurrents.serpentframework;

import java.util.ArrayList;
import java.util.Stack;

public class WaypointNetwork {

    public WaypointNode[] nodes;
    
    private static WaypointNetwork instance;

    /** Sets the in-use WaypointNetwork. */
    public static void setInstance(WaypointNetwork network) {
        instance = network;
        Waypoint[] pts = new Waypoint[network.nodes.length];
        for(int x = 0; x < pts.length; x++) {
            pts[x] = network.nodes[x].point;
        }
        NetworkBinding.setWaypoints(pts);
    }

    /** Sets the in-use WaypointNetwork. */
    public static void setInstance(WaypointNode[] nodes) {
        WaypointNetwork network = new WaypointNetwork();
        network.nodes = nodes;
        setInstance(network);
    }

    /** Gets the currently in-use WaypointNetwork. */
    public static WaypointNetwork getInstance() {
        return instance;
    }

    /** Constructs a series of Waypoints representing the path with least nodes from the start Waypoint to the end Waypoint. */
    public WaypointNode[] getPathToPoint(Waypoint start, Waypoint end) {
        return getPathToPoint(getNodeFromWaypoint(start), getNodeFromWaypoint(end));
    }

    /** Constructs a series of Waypoints representing the path with least nodes from the start Waypoint to the end Waypoint. */
    public WaypointNode[] getPathToPoint(WaypointNode start, WaypointNode end) {
        Stack<WaypointNode> points = new Stack<WaypointNode>();
        ArrayList<WaypointNode> checks = new ArrayList<WaypointNode>();
        points.push(start);
        checks.add(start);
        int x = 0;
        while(!checkNodesAtDepth(x, points, checks, start, end)) {
            x++;
        }
        return points.toArray(new WaypointNode[points.size()]);
    }

    private boolean checkNodesAtDepth(int depthIndex, Stack<WaypointNode> nodeStack, ArrayList<WaypointNode> checkedNodes, WaypointNode current, WaypointNode end) {
        if(depthIndex == 0) {
            for(WaypointNode node : current.connectedPoints) {
                if(node == end) {
                    nodeStack.push(node);
                    return true;
                }
            }
        }
        else {
            for(WaypointNode node : current.connectedPoints) {
                if(!checkedNodes.contains(node)) {
                    nodeStack.push(node);
                    if(checkNodesAtDepth(depthIndex - 1, nodeStack, checkedNodes, node, end)) {
                        return true;
                    }
                    nodeStack.pop();
                }
            }
        }
        return false;
    }

    /** Converts a Waypoint to a WaypointNode.  Null if the node is not found. */
    public WaypointNode getNodeFromWaypoint(Waypoint point) {
        for(WaypointNode node : nodes) {
            if(node.point == point) {
                return node;
            }
        }
        return null;
    }

    /** Retrieves the WaypointNode closest to a certain position. */
    public WaypointNode getNodeClosestToPosition(double x, double y) {
        WaypointNode closestPoint = null;
        double distSq = Double.POSITIVE_INFINITY;
        for(WaypointNode node : nodes) {
            double curDistance = square(node.point.xPosition - x) + square(node.point.yPosition - y);
            if(curDistance < distSq) {
                closestPoint = node;
                distSq = curDistance;
            }
        }
        return closestPoint;
    }

    /** Creates a temporary WaypointNode connected to the physically closest WaypointNode.  This allows the robot to enter the Waypoint system from an abitrary location. */
    public WaypointNode getTemporaryNodeFromPosition(double x, double y) {
        return new WaypointNode(new Waypoint(WaypointType.NAVIGATION, x, y), new WaypointNode[] { getNodeClosestToPosition(x, y) });
    }

    private double square(double a) {
        return a * a;
    }
}