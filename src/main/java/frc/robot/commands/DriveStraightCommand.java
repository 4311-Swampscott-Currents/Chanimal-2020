package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

/** This command causes the robot to drive straight forward for a specified number of feet. */
public class DriveStraightCommand extends CommandBase {

    private double feetToMove;
    private int leftSensorGoal, rightSensorGoal;

    public DriveStraightCommand(double feet) {
        feetToMove = feet / 2;
        addRequirements(RobotMap.drivetrain);
    }

    @Override
    public void initialize() {
        leftSensorGoal = RobotMap.drivetrain.frontLeftFalcon.getSelectedSensorPosition() - (int)(feetToMove * RobotMap.encoderUnitsPerFoot) - 50; //add some units since it seems to undershoot
        rightSensorGoal = RobotMap.drivetrain.frontRightFalcon.getSelectedSensorPosition() + (int)(feetToMove * RobotMap.encoderUnitsPerFoot) + 50;
        RobotMap.drivetrain.frontLeftFalcon.set(ControlMode.MotionMagic, leftSensorGoal);
        RobotMap.drivetrain.frontRightFalcon.set(ControlMode.MotionMagic, rightSensorGoal);
    }

    @Override
    public boolean isFinished() {
        //get drivetrain wheels within 0.1 ft of target before stopping
        return Math.abs(RobotMap.drivetrain.frontLeftFalcon.getSelectedSensorPosition() - leftSensorGoal) < 0.1 * RobotMap.encoderUnitsPerFoot && Math.abs(RobotMap.drivetrain.frontRightFalcon.getSelectedSensorPosition() - rightSensorGoal) < 0.1 * RobotMap.encoderUnitsPerFoot;
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.drivetrain.stopMotors();
    }
}