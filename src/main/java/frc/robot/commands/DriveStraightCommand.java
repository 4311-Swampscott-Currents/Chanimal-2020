package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.RobotMap;

/** This command causes the robot to drive straight forward for a specified number of feet. */
public class DriveStraightCommand extends CommandBase {

    private double feetToMove;
    private int leftSensorGoal, rightSensorGoal;
    private boolean goingBackwards = false;

    public DriveStraightCommand(double feet) {
        feetToMove = feet / 2;
        addRequirements(RobotMap.drivetrain);
    }

    public DriveStraightCommand(double feet, boolean driveBackwards) {
        feetToMove = feet / 2;
        goingBackwards = driveBackwards;
        addRequirements(RobotMap.drivetrain);
    }

    @Override
    public void initialize() {
        int back = goingBackwards ? -1 : 1;
        leftSensorGoal = RobotMap.drivetrain.frontLeftFalcon.getSelectedSensorPosition() + back * ( - (int)(feetToMove * RobotMap.encoderUnitsPerFoot) - 10); //add some units since it seems to undershoot
        rightSensorGoal = RobotMap.drivetrain.frontRightFalcon.getSelectedSensorPosition() + back * ((int)(feetToMove * RobotMap.encoderUnitsPerFoot) + 10);
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