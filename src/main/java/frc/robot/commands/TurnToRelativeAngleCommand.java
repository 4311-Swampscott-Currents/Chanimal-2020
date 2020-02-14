package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.swampscottcurrents.serpentframework.Quaternion2D;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

/** This command causes the robot to drive straight forward for a specified number of feet. */
public class TurnToRelativeAngleCommand extends CommandBase {

    private double angleToHit;

    public TurnToRelativeAngleCommand(double angle) {
        angleToHit = angle;
        addRequirements(RobotMap.drivetrain);
    }

    @Override
    public void initialize() {
        double ftToTurn = Math.PI * RobotMap.robotRadius * angleToHit / 180;
        RobotMap.drivetrain.frontLeftFalcon.set(ControlMode.MotionMagic, RobotMap.drivetrain.frontLeftFalcon.getSelectedSensorPosition() - ftToTurn * RobotMap.encoderUnitsPerFoot); //counterclockwise so right turns forward and left back
        RobotMap.drivetrain.frontRightFalcon.set(ControlMode.MotionMagic, RobotMap.drivetrain.frontRightFalcon.getSelectedSensorPosition() + ftToTurn * RobotMap.encoderUnitsPerFoot);
    }

    @Override
    public boolean isFinished() {
        //get drivetrain wheels within 0.1 ft of target before stopping
        return Math.abs(RobotMap.drivetrain.frontLeftFalcon.getClosedLoopError()) < 0.1 * RobotMap.encoderUnitsPerFoot && Math.abs(RobotMap.drivetrain.frontRightFalcon.getClosedLoopError()) < 0.1 * RobotMap.encoderUnitsPerFoot;
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.drivetrain.stopMotors();
    }
}