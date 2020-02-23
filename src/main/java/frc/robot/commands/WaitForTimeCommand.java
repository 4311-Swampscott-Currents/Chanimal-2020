package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

/** This command causes the robot to pause for a certain amount of time. */
public class WaitForTimeCommand extends CommandBase {

    private double timeToWait;
    private double timeEnding;

    public WaitForTimeCommand(double time) {
        timeToWait = time;
    }

    @Override
    public void initialize() {
        timeEnding = Robot.instance.getRobotTime() + timeToWait;
    }

    @Override
    public boolean isFinished() {
        return timeEnding < Robot.instance.getRobotTime();
    }
}