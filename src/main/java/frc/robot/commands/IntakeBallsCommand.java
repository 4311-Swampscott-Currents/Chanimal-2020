package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.*;
import frc.robot.subsystems.*;

/** This is the default command that runs on the ConveyorBelt subsystem.  It manages the intake and conveyor belt motion whilst picking up balls. */
public class IntakeBallsCommand extends CommandBase {

    private boolean isIntakeRunning = true;
    private boolean conveyorAutoDisabled = false;
    private double checkTime = 0;
    private double disableTime = 0;

    public IntakeBallsCommand(ConveyorBelt system) {
        addRequirements(system);
    }

    @Override
    public void initialize() {
        RobotMap.conveyorBelt.setIntakeOn(isIntakeRunning);
        RobotMap.conveyorBelt.setConveyorSpeed(0);
        setIntakeRunning(true);
        conveyorAutoDisabled = false;
        checkTime = 0;
        disableTime = 0;
        RobotMap.joystick.debounceAllButtons();
    }

    @Override
    public void execute() {
        if(RobotMap.joystick.getButtonReleased("Toggle Intake")) {
            if(isIntakeRunning) {
                RobotMap.conveyorBelt.setConveyorSpeed(0);
            }
            setIntakeRunning(!isIntakeRunning);
        }
        if(isIntakeRunning) {
            if(checkTime == 0) {
                if(RobotMap.conveyorBelt.intakeSensor.getRangeInches() < RobotMap.intakeThresholdLength * 12) {
                    checkTime = Robot.instance.getRobotTime() + RobotMap.intakeWaitTime;
                }
            }
            else if(Math.abs(checkTime) < Robot.instance.getRobotTime()) {
                if(checkTime > 0) {
                    if(RobotMap.conveyorBelt.intakeSensor.getRangeInches() < RobotMap.intakeThresholdLength * 12) {    
                        RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.defaultConveyorSpeed);
                        checkTime = -Robot.instance.getRobotTime() - RobotMap.intakeSuckTime;
                    }
                    else {
                        checkTime = 0;
                    }
                }
                else {
                    RobotMap.conveyorBelt.setConveyorSpeed(0);
                    checkTime = 0;
                }
            }
            if(!conveyorAutoDisabled) {
                if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                    conveyorAutoDisabled = true;
                    setIntakeRunning(false);
                    RobotMap.conveyorBelt.setConveyorSpeed(0);
                    Feedback.setStatus("Conveyor", "Full");
                    Feedback.log(RobotMap.conveyorBelt, "Conveyor has reached maximum capacity.  Intake auto-disabled.");
                }
            }
        }
        if(RobotMap.joystick.getButtonReleased("Increment Launcher Speed")) {
            CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                new IndexBallsCommand(),
                new LaunchAllBallsCommand()
            ));
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.conveyorBelt.setIntakeOn(false);
        RobotMap.conveyorBelt.setConveyorSpeed(0);
    }

    public void setIntakeRunning(boolean running) {
        isIntakeRunning = running;
        RobotMap.conveyorBelt.setIntakeOn(running);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}