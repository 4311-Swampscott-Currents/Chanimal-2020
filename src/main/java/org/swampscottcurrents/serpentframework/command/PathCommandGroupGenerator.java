package org.swampscottcurrents.serpentframework.command;

import org.swampscottcurrents.serpentframework.*;

public class PathCommandGroupGenerator {

    /** Returns a CommandGroup containing all the necessary Commands to drive from a starting WaypointNode to an ending WaypointNode. */
    public CommandGroup GeneratePathFromNodes(WaypointNode[] nodes) {
        CommandGroup toReturn = new CommandGroup();
        int max = nodes.length - 1;
        for(int x = 0; x < max; x++) {
            toReturn.queueCommand(getTurningCommand(Waypoint.getAngle(nodes[x + 1].point, nodes[x].point), nodes[x + 1]));
            toReturn.queueCommand(getDrivingCommand(Waypoint.getDistance(nodes[x + 1].point, nodes[x].point), nodes[x + 1]));
        }
        return toReturn;
    }

    /** Returns a new command that turns the robot to the specified absolute angle. */
    public Command getTurningCommand(Quaternion2D angleToTurnTo, WaypointNode targetNode) { return null; }

    /** Returns a new command that drives the robot straight a specified distance. */
    public Command getDrivingCommand(double distance, WaypointNode targetNode) { return null; }
}