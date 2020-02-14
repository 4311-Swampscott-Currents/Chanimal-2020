package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.*;
import frc.robot.subsystems.*;

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
    }

    @Override
    public void execute() {
        if(RobotMap.joystick.getButtonPressed("Toggle Intake")) {
            setIntakeRunning(!isIntakeRunning);
        }
        if(isIntakeRunning) {
            if(checkTime == 0) {
                if(RobotMap.conveyorBelt.intakeSensor.getRangeInches() < RobotMap.intakeThresholdLength * 12) {
                    checkTime = Robot.instance.getRobotTime() + RobotMap.intakeWaitTime;
                }
                RobotMap.conveyorBelt.setConveyorSpeed(0);
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
            else {
                RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.defaultConveyorSpeed);
            }            
        }
    }

    public void setIntakeRunning(boolean running) {
        isIntakeRunning = running;
        RobotMap.conveyorBelt.setIntakeOn(running);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}