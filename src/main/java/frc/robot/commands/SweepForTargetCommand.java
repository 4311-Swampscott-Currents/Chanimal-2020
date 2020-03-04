package frc.robot.commands;

import org.swampscottcurrents.serpentframework.Quaternion2D;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotMap;
import frc.robot.subsystems.Launcher;

public class SweepForTargetCommand extends CommandBase {
    
    public double motorDirection = 1;
    public boolean foundSlowSpeed = false;

    public SweepForTargetCommand() {
        addRequirements(RobotMap.drivetrain, RobotMap.launcher, RobotMap.limelight);
    }

    @Override
    public void initialize() {
        if(Quaternion2D.fromEuler(RobotMap.drivetrain.navXGyroscope.getAngle()).toEuler() > 0) {
            motorDirection = -1;
        }
        else {
            motorDirection = 1;
        }
    }

    @Override
    public void execute() {
        if(Math.abs(Quaternion2D.fromEuler(RobotMap.drivetrain.navXGyroscope.getAngle()).toEuler()) < 90) {
            RobotMap.limelight.setLEDsOn(false);
            RobotMap.drivetrain.driveDifferential(motorDirection * RobotMap.sweepSpeed, 0);
        }
        else {
            RobotMap.limelight.setLEDsOn(true);
            foundSlowSpeed = true;
            RobotMap.drivetrain.driveDifferential(motorDirection * RobotMap.sweepSpeedSlow, 0);
        }
    }

    @Override
    public boolean isFinished() {
        return (foundSlowSpeed && Math.abs(Quaternion2D.fromEuler(RobotMap.drivetrain.navXGyroscope.getAngle()).toEuler()) < 90) || (RobotMap.limelight.hasTarget() && Math.abs(RobotMap.limelight.getTargetDegreesX()) < RobotMap.limelightThreshold);
    }

    @Override
    public void end(boolean interrupted) {
        if(RobotMap.limelight.hasTarget()) {
            Launcher.launcherSpeed = RobotMap.angledLimelightTargetHeightToLauncherSpeed(RobotMap.limelight.getTargetHeight());
        }
        RobotMap.limelight.setLEDsOn(true);
    }
}