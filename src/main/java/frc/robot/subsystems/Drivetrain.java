package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.OperatorControlCommand;

/** Represents the robot's drivetrain, namely, the 4 motors which control robot movement. */
public class Drivetrain implements Subsystem {

    public WPI_TalonFX frontLeftFalcon = new WPI_TalonFX(11);
    public WPI_TalonFX backLeftFalcon = new WPI_TalonFX(12);
    public WPI_TalonFX frontRightFalcon = new WPI_TalonFX(13);
    public WPI_TalonFX backRightFalcon = new WPI_TalonFX(14);

    public AHRS navXGyroscope = new AHRS();

    public DifferentialDrive driveController = new DifferentialDrive(frontLeftFalcon, frontRightFalcon);

    public Drivetrain() {
        driveController.setSafetyEnabled(false);

        TalonFXConfiguration mainConfig = new TalonFXConfiguration();
        mainConfig.neutralDeadband = 0.0400782;
        mainConfig.motionAcceleration = 1320;
        mainConfig.motionCruiseVelocity = 3500;
        mainConfig.motionCurveStrength = 2;
        mainConfig.closedloopRamp = 2.045;
        mainConfig.slot0.allowableClosedloopError = 45;
        mainConfig.slot0.closedLoopPeriod = 1;
        mainConfig.slot0.closedLoopPeakOutput = 0.45;
        mainConfig.slot0.kD = 52;
        mainConfig.slot0.kI = 0;
        mainConfig.slot0.kP = 0.16;
        
        frontLeftFalcon.configAllSettings(mainConfig);
        frontLeftFalcon.setNeutralMode(NeutralMode.Brake);
        frontLeftFalcon.setInverted(InvertType.InvertMotorOutput);
        frontLeftFalcon.setSensorPhase(false);

        frontRightFalcon.configAllSettings(mainConfig);
        frontRightFalcon.setNeutralMode(NeutralMode.Brake);
        frontRightFalcon.setInverted(InvertType.InvertMotorOutput);
        frontRightFalcon.setSensorPhase(false);

        backLeftFalcon.configAllSettings(mainConfig);
        backLeftFalcon.setNeutralMode(NeutralMode.Brake);
        backLeftFalcon.follow(frontLeftFalcon);
        backLeftFalcon.setInverted(InvertType.FollowMaster);
        backLeftFalcon.setSensorPhase(false);

        backRightFalcon.configAllSettings(mainConfig);
        backRightFalcon.setNeutralMode(NeutralMode.Brake);
        backRightFalcon.follow(frontRightFalcon);
        backRightFalcon.setInverted(InvertType.FollowMaster);
        backRightFalcon.setSensorPhase(false);

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

    @Override
    public String toString() {
        return "Drivetrain";
    }
}