package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

public class ManualControlCommand extends CommandBase {

    public double shooterSpeed = 1;

    public ManualControlCommand() {
        addRequirements(RobotMap.conveyorBelt, RobotMap.launcher);
    }

    @Override
    public void execute() {
        if(RobotMap.joystick.getButton("Fire")) {
            RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, shooterSpeed);
        }
        else {
            RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, 0);
        }
        if(RobotMap.joystick.getButton("Conveyor Belt Up")) {
            RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.defaultConveyorSpeed);
        }
        else if(RobotMap.joystick.getButton("Conveyor Belt Down")) {
            RobotMap.conveyorBelt.setConveyorSpeed(-RobotMap.defaultConveyorSpeed);
        }
        else {
            RobotMap.conveyorBelt.setConveyorSpeed(0);
        }
    }

    @Override
    public boolean isFinished() {
        return RobotMap.joystick.getButtonReleased("Toggle Manual Control");
    }
}