package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.Launcher;
import frc.robot.*;

public class SearchForTargetCommand extends SequentialCommandGroup {
    
    public double speed = 1;

    public SearchForTargetCommand(Launcher system) {
        addRequirements(system);
    }

    @Override
    public void execute() {
        NetworkTableInstance.getDefault().getEntry("yeet").setDouble(speed);
        if(RobotMap.joystick.getButton("Fire")) {
            RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, speed);
        }
        else {
            RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, 0);
        }
        if(RobotMap.joystick.getRawButtonPressed(11)) {
            speed += 0.05;
        }
        if(RobotMap.joystick.getRawButtonPressed(10)) {
            speed -= 0.05;
        }
        /*if(RobotMap.joystick.getButtonReleased("Fire") && TurnTowardsGoalCommand.isAvailable()) {
            CommandScheduler.getInstance().schedule(new AimAndFireCommand());
        }*/
    }
}