package frc.robot.commands;

import org.swampscottcurrents.serpentframework.Quaternion2D;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.Launcher;
import frc.robot.*;

/** This is the default command that runs on the Launcher subsystem.  It searches for the upper target and awaits driver input to begin the firing process. */
public class SearchForTargetCommand extends SequentialCommandGroup {
    
    public double speed = 1;

    public SearchForTargetCommand(Launcher system) {
        addRequirements(system, RobotMap.limelight);
        Feedback.setStatus("Launcher", "Idle");
    }

    @Override
    public void initialize() {
        RobotMap.joystick.debounceAllButtons();
        Feedback.setStatus("Launcher", "Idle");
    }

    @Override
    public void execute() {
        if(Math.abs(Quaternion2D.fromEuler(RobotMap.drivetrain.navXGyroscope.getAngle()).toEuler()) > 90) {
            RobotMap.limelight.setLEDsOn(false);
        }
        else {
            RobotMap.limelight.setLEDsOn(true);
            boolean av = RobotMap.limelight.hasTarget();
            if(av) {
                Feedback.setStatus("Launcher", "Target acquired");
            }
            else {
                Feedback.setStatus("Launcher", "Idle");
            }
            if(RobotMap.joystick.getButtonReleased("Fire")) {
                if(av) {
                    CommandScheduler.getInstance().schedule(new AimAndFireCommand());
                }
                else {
                    CommandScheduler.getInstance().schedule(new SweepForTargetCommand());
                    Feedback.log(RobotMap.launcher, "Valid vision target not found; sweeping...");
                }
            }
        }
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}