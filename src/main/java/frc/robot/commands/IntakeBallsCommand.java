package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

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
    private double automaticConveyorSpeed = 0;

    public IntakeBallsCommand(ConveyorBelt system) {
        addRequirements(system);
    }

    @Override
    public void initialize() {
        RobotMap.conveyorBelt.setIntakeOn(isIntakeRunning);
        automaticConveyorSpeed = 0;
        setIntakeRunning(true);
        conveyorAutoDisabled = false;
        checkTime = 0;
        RobotMap.joystick.debounceAllButtons();
    }

    @Override
    public void execute() {
        if(RobotMap.joystick.getButtonReleased("Toggle Intake")) {
            if(isIntakeRunning) {
                automaticConveyorSpeed = 0;
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
                        automaticConveyorSpeed = RobotMap.defaultConveyorSpeed;
                        checkTime = -Robot.instance.getRobotTime() - RobotMap.intakeSuckTime;
                    }
                    else {
                        checkTime = 0;
                    }
                }
                else {
                    automaticConveyorSpeed = 0;
                    checkTime = 0;
                }
            }
            if(!conveyorAutoDisabled) {
                if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                    conveyorAutoDisabled = true;
                    setIntakeRunning(false);
                    automaticConveyorSpeed = 0;
                    Feedback.setStatus("Conveyor", "Full");
                    Feedback.log(RobotMap.conveyorBelt, "Conveyor has reached maximum capacity.  Intake auto-disabled.");
                }
            }
        }
        
        if(RobotMap.joystick.getButton("Conveyor Belt Up")) {
            RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.defaultConveyorSpeed);
        }
        else if(RobotMap.joystick.getButton("Conveyor Belt Down")) {
            RobotMap.conveyorBelt.setConveyorSpeed(-RobotMap.defaultConveyorSpeed);
            RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, -0.3);
        }
        else {
            if(!RobotMap.joystick.getButton("Fire")) {
                RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, 0);
            }
            RobotMap.conveyorBelt.setConveyorSpeed(automaticConveyorSpeed);
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