package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.RobotMap;

public class TurnTowardsGoalCommand extends CommandBase {
    public TurnTowardsGoalCommand() {
        addRequirements(RobotMap.drivetrain);
    }

    @Override
    public void initialize() {
        CommandScheduler.getInstance().schedule(new TurnToRelativeAngleCommand(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0)));
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    public static boolean isAvailable() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1;
    }
}