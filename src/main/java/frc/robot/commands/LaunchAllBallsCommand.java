package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LaunchAllBallsCommand extends CommandBase {

    private double limelightSpeed = 0;
    private double limelightWaitTime = 0;
    private double shooterTime = 0;

    public LaunchAllBallsCommand() {
        addRequirements(RobotMap.conveyorBelt, RobotMap.launcher);
    }

    @Override
    public void initialize() {
        limelightWaitTime = Robot.instance.getRobotTime() + RobotMap.limelightWaitTime;
    }

    @Override
    public void execute() {
        if(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1) {
            limelightSpeed = 1; //do some fun calculations here to get speed from distance
            limelightWaitTime = 0;
            shooterTime = Robot.instance.getRobotTime() + RobotMap.shootBallTime;
            RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.shooterConveyorActuationSpeed);
            RobotMap.launcher.setShooterSpeed(limelightSpeed);
        }
        if(shooterTime != 0 && Math.abs(shooterTime) < Robot.instance.getRobotTime()) {
            if(shooterTime > 0) {
                setShootingSystem(false);
                if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                    shooterTime = -Robot.instance.getRobotTime() - RobotMap.shooterConveyorOffActuationTime;
                }
                else {
                    shooterTime = 0;
                }
            }
            if(shooterTime < 0) {
                if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                    shooterTime = Robot.instance.getRobotTime() + RobotMap.shooterConveyorOffActuationTime;
                    setShootingSystem(true);
                }
                else {
                    shooterTime = 0;
                }
            }
        }
    }

    private void setShootingSystem(boolean isOn) {
        if(isOn) {
            RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.shooterConveyorActuationSpeed);
            RobotMap.launcher.setShooterSpeed(limelightSpeed);
        }
        else {
            RobotMap.conveyorBelt.setConveyorSpeed(0);
            RobotMap.launcher.setShooterSpeed(0);
        }
    }

    @Override
    public boolean isFinished() {
        return limelightWaitTime < Robot.instance.getRobotTime() && shooterTime == 0;
    }
}