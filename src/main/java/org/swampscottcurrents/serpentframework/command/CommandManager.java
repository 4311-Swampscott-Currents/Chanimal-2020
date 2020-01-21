package org.swampscottcurrents.serpentframework.command;

public final class CommandManager {

    private static Command currentCommand;
    private static boolean isPaused;

    /** Returns whether the CommandManager is current executing a Command. */
    public static boolean isExecutingCommand() {
        return currentCommand == null;
    }

    /** Returns whether the CommandManager is paused. */
    public static boolean getPaused() {
        return isPaused;
    }

    /** Pauses/unpauses the CommandManager, and any currently running Command. */
    public static void setPaused(boolean paused) {
        if(currentCommand != null) {
            if(paused) {
                if(!isPaused) {
                    currentCommand.pauseStart();
                }
            }
            else {
                if(isPaused) {
                    currentCommand.pauseEnd();
                }
            }
        }
        isPaused = paused;
    }

    /** Begins the execution of a new command, interrupting the currently running command if necessary. */
    public static void executeCommand(Command toExecute) {
        if(currentCommand != null) {
            interruptCurrentCommand();
        }
        currentCommand = toExecute;
        toExecute.start();
        if(isPaused) {
            toExecute.pauseStart();
        }
    }

    /** This method should be called every robot update cycle. */
    public static void update() {
        if(currentCommand != null) {
            if(isPaused) {
                currentCommand.pauseUpdate();
            }
            else {
                currentCommand.update();
            }
            if(currentCommand.isComplete()) {
                currentCommand.end();
                currentCommand = null;
            }
        }
    }

    /** This method will stop the currently running command. */
    public static void interruptCurrentCommand() {
        currentCommand.interrupted();
        currentCommand = null;
    }
}