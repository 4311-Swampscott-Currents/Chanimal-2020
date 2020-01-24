package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.OperatorControlCommand;

/** Represents the robot's drivetrain, namely, the 4 motors which control robot movement. */
public class Drivetrain implements Subsystem {

    public WPI_TalonFX frontLeftFalcon = new WPI_TalonFX(11);
    public WPI_TalonFX backLeftFalcon = new WPI_TalonFX(12);
    public WPI_TalonFX frontRightFalcon = new WPI_TalonFX(13);
    public WPI_TalonFX backRightFalcon = new WPI_TalonFX(14);

    public DifferentialDrive driveController = new DifferentialDrive(frontLeftFalcon, frontRightFalcon);

    public Drivetrain() {
        driveController.setSafetyEnabled(false);
        backLeftFalcon.follow(frontLeftFalcon);
        backRightFalcon.follow(frontRightFalcon);
        backLeftFalcon.setInverted(InvertType.FollowMaster);
        backRightFalcon.setInverted(InvertType.FollowMaster);
        setDefaultCommand(new OperatorControlCommand(this));
    }

    /** Drives using a differential (arcade) algorithm. */
    public void driveDifferential(double x, double y) {
        driveController.arcadeDrive(y, x, false);
    }

    /** Shuts off all of the drivetrain motors. */
    public void stopMotors() {
        frontLeftFalcon.set(ControlMode.Disabled, 0);
        frontRightFalcon.set(ControlMode.Disabled, 0);
    }
}