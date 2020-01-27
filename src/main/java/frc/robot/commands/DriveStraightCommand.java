package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

/** This command causes the robot to drive straight forward for a specified number of feet. */
public class DriveStraightCommand extends CommandBase {

    private double feetToMove;

    public DriveStraightCommand(double feet) {
        feetToMove = feet;
        addRequirements(RobotMap.drivetrain);
    }

    @Override
    public void initialize() {
        RobotMap.drivetrain.frontLeftFalcon.set(ControlMode.MotionMagic, feetToMove * RobotMap.motorRotationsPerFoot);
        RobotMap.drivetrain.frontRightFalcon.set(ControlMode.MotionMagic, feetToMove * RobotMap.motorRotationsPerFoot);
    }

    @Override
    public boolean isFinished() {
        //get drivetrain wheels within 0.1 ft of target before stopping
        return RobotMap.drivetrain.frontLeftFalcon.getClosedLoopError() / RobotMap.motorRotationsPerFoot < 0.1 && RobotMap.drivetrain.frontRightFalcon.getClosedLoopError() / RobotMap.motorRotationsPerFoot < 0.1;
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.drivetrain.stopMotors();
    }
}