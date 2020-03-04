package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.swampscottcurrents.serpentframework.Quaternion2D;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.RobotMap;

/** This command causes the robot to drive straight forward for a specified number of feet. */
public class TurnToAbsoluteAngleCommand extends CommandBase {

    private double angleToHit;
    private double leftSensorGoal, rightSensorGoal;

    public TurnToAbsoluteAngleCommand(double angle) {
        angleToHit = angle;
        addRequirements(RobotMap.drivetrain);
    }

    public TurnToAbsoluteAngleCommand(Quaternion2D angle) {
        angleToHit = angle.toEuler();
    }

    @Override
    public void initialize() {
        double ftToTurn = RobotMap.robotRadius * Quaternion2D.subtract(Quaternion2D.fromEuler(RobotMap.drivetrain.navXGyroscope.getAngle()), Quaternion2D.fromEuler(angleToHit)).toRadian();
        ftToTurn /= 2;
        leftSensorGoal = RobotMap.drivetrain.frontLeftFalcon.getSelectedSensorPosition() + ftToTurn * RobotMap.encoderUnitsPerFoot;
        rightSensorGoal = RobotMap.drivetrain.frontRightFalcon.getSelectedSensorPosition() + ftToTurn * RobotMap.encoderUnitsPerFoot;
        RobotMap.drivetrain.frontLeftFalcon.set(ControlMode.MotionMagic, leftSensorGoal); //counterclockwise so right turns forward and left back
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