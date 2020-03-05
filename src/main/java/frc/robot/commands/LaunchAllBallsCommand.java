package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Launcher;

/** If the Limelight has an available target, this command causes the robot to fire all balls from the top of the conveyor, using a speed computed from the Limelight target's bounding-box height. */
public class LaunchAllBallsCommand extends CommandBase {

    private double timeBalless = Double.POSITIVE_INFINITY;
    private double shooterTime = 0;
    private double maxTime = 0;

    public LaunchAllBallsCommand() {
        addRequirements(RobotMap.conveyorBelt, RobotMap.launcher, RobotMap.limelight);
    }

    @Override
    public void initialize() {
        RobotMap.launcher.setShooterSpeed(Launcher.launcherSpeed);
        Feedback.setStatus("Launcher", "Preparing to fire");
        shooterTime = Robot.instance.getRobotTime() + RobotMap.shooterConveyorInitialOffTime;
        maxTime = Robot.instance.getRobotTime() + (RobotMap.shooterConveyorActuationTime + RobotMap.shooterConveyorOffActuationTime) * 7;
    }

    @Override
    public void execute() {
        if(shooterTime > 0 && shooterTime < Robot.instance.getRobotTime()) {
            RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.shooterConveyorActuationSpeed);
            if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                timeBalless = Robot.instance.getRobotTime() + RobotMap.shooterConveyorActuationTime;
            }
        }
        /*if(Math.abs(shooterTime) < Robot.instance.getRobotTime()) {
            if(shooterTime > 0) {
                //RobotMap.conveyorBelt.setConveyorSpeed(0);
                Feedback.setStatus("Launcher", "Preparing to fire");
                if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                    shooterTime = -Robot.instance.getRobotTime() - RobotMap.shooterConveyorOffActuationTime;
                }
                else {
                    finished = true;
                }
            }
            else if(shooterTime < 0) {
                shooterTime = Robot.instance.getRobotTime() + RobotMap.shooterConveyorActuationTime;
                RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.shooterConveyorActuationSpeed);
                Feedback.setStatus("Launcher", "Firing");
            }
        }*/
    }

    @Override
    public void end(boolean interrupted) {
        Feedback.setStatus("Launcher", "Idle");
        RobotMap.conveyorBelt.setConveyorSpeed(0);
        RobotMap.launcher.setShooterSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return maxTime < Robot.instance.getRobotTime() || timeBalless < Robot.instance.getRobotTime();
    }
}