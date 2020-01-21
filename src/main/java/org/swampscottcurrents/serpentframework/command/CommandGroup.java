package org.swampscottcurrents.serpentframework.command;

import java.util.*;

public class CommandGroup extends Command {

    private Command currentCommand;
    private LinkedList<Command> queuedCommands = new LinkedList<Command>();

    public CommandGroup() {}
    
    public CommandGroup(Command[] toExecute) {
        for(Command command : toExecute) {
            queueCommand(command);
        }
    }

    /** Adds a Command to the list of Commands to be executed. */
    public final void queueCommand(Command command) {
        queuedCommands.addLast(command);
    }

    public final void start() {
        if(!queuedCommands.isEmpty()) {
            currentCommand = queuedCommands.getFirst();
            queuedCommands.removeFirst();
            currentCommand.start();
        }
    }

    public final void update() {
        if(currentCommand != null) {
            currentCommand.update();
        }
    }

    public final void end() {

    }

    public final void interrupted() {
        if(currentCommand != null) {
            currentCommand.interrupted();
        }
    }

    public final void pauseStart() {
        if(currentCommand != null) {
            currentCommand.pauseStart();
        }
    }

    public final void pauseUpdate() {
        if(currentCommand != null) {
            currentCommand.pauseUpdate();
        }
    }

    public final void pauseEnd() {
        if(currentCommand != null) {
            currentCommand.pauseEnd();
        }
    }

    public final boolean isComplete() {
        if(currentCommand == null) {
            if(queuedCommands.isEmpty()) {
                return true;
            }
            else {
                currentCommand = queuedCommands.getFirst();
                queuedCommands.removeFirst();
                currentCommand.start();
                return false;
            }
        }
        if(currentCommand.isComplete()) {
            currentCommand.end();
            if(queuedCommands.isEmpty()) {
                return true;
            }
            else {
                currentCommand = queuedCommands.getFirst();
                queuedCommands.removeFirst();
                currentCommand.start();
                return false;
            }
        }
        return false;
    }
}