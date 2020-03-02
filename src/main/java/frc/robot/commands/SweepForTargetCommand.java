package frc.robot.commands;

import org.swampscottcurrents.serpentframework.Quaternion2D;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotMap;

public class SweepForTargetCommand extends CommandBase {
    
    public double motorDirection = 1;
    public boolean foundSlowSpeed = false;

    public SweepForTargetCommand() {
        addRequirements(RobotMap.drivetrain, RobotMap.launcher, RobotMap.limelight);
    }

    @Override
    public void initialize() {
        if(Quaternion2D.fromEuler(RobotMap.drivetrain.navXGyroscope.getPitch()).toEuler() > 0) {
            motorDirection = 1;
        }
        else {
            motorDirection = -1;
        }
    }

    @Override
    public void execute() {
        if(Math.abs(Quaternion2D.fromEuler(RobotMap.drivetrain.navXGyroscope.getAngle()).toEuler()) > 90) {
            RobotMap.drivetrain.driveDifferential(motorDirection * RobotMap.sweepSpeed, 0);
        }
        else {
            foundSlowSpeed = true;
            RobotMap.drivetrain.driveDifferential(motorDirection * RobotMap.sweepSpeedSlow, 0);
        }
        if(RobotMap.limelight.hasTarget()) {
            CommandScheduler.getInstance().schedule(new AimAndFireCommand());
        }
    }

    @Override
    public boolean isFinished() {
        return foundSlowSpeed && Math.abs(Quaternion2D.fromEuler(RobotMap.drivetrain.navXGyroscope.getPitch()).toEuler()) > 90;
    }
}