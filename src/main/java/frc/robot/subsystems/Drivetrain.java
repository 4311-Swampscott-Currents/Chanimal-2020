package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.OperatorControlCommand;

/** Represents the robot's drivetrain, namely, the 4 motors which control robot movement. */
public class Drivetrain implements Subsystem {

    public WPI_TalonFX frontLeftFalcon = new WPI_TalonFX(0);
    public WPI_TalonFX backLeftFalcon = new WPI_TalonFX(0);
    public WPI_TalonFX frontRightFalcon = new WPI_TalonFX(0);
    public WPI_TalonFX backRightFalcon = new WPI_TalonFX(0);
    public AHRS navXGyroscope = new AHRS();

    public DifferentialDrive driveController = new DifferentialDrive(frontLeftFalcon, frontRightFalcon);

    public Drivetrain() {
        TalonFXConfiguration mainConfiguration = new TalonFXConfiguration();
        mainConfiguration.velocityMeasurementPeriod = VelocityMeasPeriod.Period_1Ms;
        mainConfiguration.closedloopRamp = 1;
        mainConfiguration.feedbackNotContinuous = false;
        mainConfiguration.initializationStrategy = SensorInitializationStrategy.BootToZero;
        mainConfiguration.neutralDeadband = 0.001;
        mainConfiguration.motionAcceleration = (int)(3 * RobotMap.encoderUnitsPerFoot);
        mainConfiguration.motionCruiseVelocity = (int)(0.0004 * RobotMap.encoderUnitsPerFoot); //divide 4ft / 1000ms because units are encoder units/ms

        frontLeftFalcon.setSensorPhase(false);
        frontLeftFalcon.setInverted(InvertType.InvertMotorOutput);
        frontLeftFalcon.configAllSettings(mainConfiguration);
        frontLeftFalcon.setNeutralMode(NeutralMode.Brake);

        frontRightFalcon.setSensorPhase(false);
        frontRightFalcon.setInverted(InvertType.InvertMotorOutput);
        frontRightFalcon.configAllSettings(mainConfiguration);
        frontRightFalcon.setNeutralMode(NeutralMode.Brake);
        
        backLeftFalcon.follow(frontLeftFalcon);
        backLeftFalcon.setInverted(InvertType.FollowMaster);
        backLeftFalcon.configAllSettings(mainConfiguration);
        backLeftFalcon.setNeutralMode(NeutralMode.Brake);

        backRightFalcon.follow(frontRightFalcon);
        backRightFalcon.setInverted(InvertType.FollowMaster);
        backRightFalcon.configAllSettings(mainConfiguration);
        backRightFalcon.setNeutralMode(NeutralMode.Brake);

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