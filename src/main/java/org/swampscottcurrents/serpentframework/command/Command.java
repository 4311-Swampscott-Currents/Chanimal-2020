package org.swampscottcurrents.serpentframework.command;

/** Provides a custom implementation of the command-based programming model included in WPILib.  This implementation is meant to provide a slightly simpler and more flexible interface. */
public class Command {
    /** Called when the command begins execution. */
    public void start() {}
    /** Called every update cycle during command lifetime. */
    public void update() {}
    /** Called when the command completes. */
    public void end() {}
    /** Called if the command ends early. */
    public void interrupted() { }
    /** Called when the command is paused. */
    public void pauseStart() {}
    /** Called every update cycle while the command is paused. */
    public void pauseUpdate() {}
    /** Called when the command is unpaused. */
    public void pauseEnd() {}
    /** Called every update cycle, before end(), to determine whether the command is finished. */
    public boolean isComplete() { return false; }
}