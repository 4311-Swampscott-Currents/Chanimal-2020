package frc.robot.subsystems;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.ForceLimelightOff;

public class Limelight implements Subsystem {

    private static NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    public Limelight() {
        setDefaultCommand(new ForceLimelightOff(this));
    }

    public void setLEDsOn(boolean on) {
        if(on) {
            limelightTable.getEntry("ledMode").setNumber(3);
        }
        else {
            limelightTable.getEntry("ledMode").setNumber(1);
        }
    }

    /** Returns whether the Limelight has any valid vision targets. */
    public boolean hasTarget() {
        return limelightTable.getEntry("tv").getDouble(0) == 1;
    }

    /** Returns the yaw angle in degrees that the target is from the robot. */
    public double getTargetDegreesX() {
        return limelightTable.getEntry("tx").getDouble(0);
    }

    /** Returns the pitch angle in degrees that the target is from the robot. */
    public double getTargetDegreesY() {
        return limelightTable.getEntry("ty").getDouble(0);
    }
}