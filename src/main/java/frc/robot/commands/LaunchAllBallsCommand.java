package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;

/** If the Limelight has an available target, this command causes the robot to fire all balls from the top of the conveyor, using a speed computed from the Limelight target's bounding-box height. */
public class LaunchAllBallsCommand extends CommandBase {

    private double limelightSpeed = 0;
    private double limelightWaitTime = 0;
    private double shooterTime = 0;

    public LaunchAllBallsCommand() {
        addRequirements(RobotMap.conveyorBelt, RobotMap.launcher, RobotMap.limelight);
    }

    @Override
    public void initialize() {
        limelightWaitTime = Robot.instance.getRobotTime() + RobotMap.limelightWaitTime;
        Feedback.setStatus("Launcher", "Awaiting Limelight");
    }

    @Override
    public void execute() {
        if(limelightWaitTime != 0 && RobotMap.limelight.hasTarget()) {
            limelightSpeed = RobotMap.angledLimelightTargetHeightToLauncherSpeed(RobotMap.limelight.getTargetHeight()); //do some fun calculations here to get speed from distance
            limelightWaitTime = 0;
            shooterTime = -Robot.instance.getRobotTime() - RobotMap.shooterWaitToSpinUpTime;
            RobotMap.launcher.setShooterSpeed(limelightSpeed);
            Feedback.setStatus("Launcher", "Preparing to fire");
        }
        if(shooterTime != 0 && Math.abs(shooterTime) < Robot.instance.getRobotTime()) {
            if(shooterTime > 0) {
                RobotMap.conveyorBelt.setConveyorSpeed(0);
                Feedback.setStatus("Launcher", "Preparing to fire");
                if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                    shooterTime = -Robot.instance.getRobotTime() - RobotMap.shooterConveyorOffActuationTime;
                }
                else {
                    shooterTime = 0;
                }
            }
            else if(shooterTime < 0) {
                if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                    shooterTime = Robot.instance.getRobotTime() + RobotMap.shooterConveyorActuationTime;
                    RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.shooterConveyorActuationSpeed);
                    Feedback.setStatus("Launcher", "Firing");
                }
                else {
                    shooterTime = 0;
                }
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        Feedback.setStatus("Launcher", "Idle");
        RobotMap.conveyorBelt.setConveyorSpeed(0);
        RobotMap.launcher.setShooterSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return limelightWaitTime < Robot.instance.getRobotTime() && shooterTime == 0;
    }
}