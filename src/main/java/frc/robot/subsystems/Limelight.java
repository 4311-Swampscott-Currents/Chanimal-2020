package frc.robot.subsystems;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.ForceLimelightOff;

public class Limelight implements Subsystem {

    private static NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    private static boolean gotTarget;
    private static double targetDegreesX, targetDegreesY, targetWidth, targetHeight;

    public Limelight() {
        setDefaultCommand(new ForceLimelightOff(this));
    }

    /** This method is called to update all NetworkTables Limelight values at once.  This prevents them from changing mid-use during a cycle. */
    public void update() {
        gotTarget = limelightTable.getEntry("tv").getDouble(0) == 1;
        targetDegreesX = limelightTable.getEntry("tx").getDouble(0);
        targetDegreesY = limelightTable.getEntry("ty").getDouble(0);
        targetWidth = limelightTable.getEntry("thor").getDouble(0);
        targetHeight = limelightTable.getEntry("tvert").getDouble(0);
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
        return gotTarget;
    }

    /** Returns the yaw angle in degrees that the target is from the robot. */
    public double getTargetDegreesX() {
        return targetDegreesX;
    }

    /** Returns the pitch angle in degrees that the target is from the robot. */
    public double getTargetDegreesY() {
        return targetDegreesY;
    }

    public double getTargetWidth() {
        return targetWidth;
    }

    public double getTargetHeight() {
        return targetHeight;
    }
}