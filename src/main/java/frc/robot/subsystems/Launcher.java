package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.*;
import frc.robot.commands.SearchForTargetCommand;

/** Represents the subsystem used for launching balls on the robot. */
public class Launcher implements Subsystem {
    
    /** The Falcon attached to the shooter. */
    public WPI_TalonFX shooterMotor = new WPI_TalonFX(15);

    public Launcher() {
        TalonFXConfiguration shooterConfiguration = new TalonFXConfiguration();
        shooterConfiguration.neutralDeadband = 0.0400782;
        shooterConfiguration.closedloopRamp = 2.045;
        shooterConfiguration.velocityMeasurementPeriod = VelocityMeasPeriod.Period_1Ms;
        shooterConfiguration.slot0.allowableClosedloopError = 45;
        shooterConfiguration.slot0.closedLoopPeriod = 1;
        shooterConfiguration.slot0.closedLoopPeakOutput = 0.45;
        shooterConfiguration.slot0.kD = 52;
        shooterConfiguration.slot0.kI = 0;
        shooterConfiguration.slot0.kP = 0.16;

        shooterMotor.configAllSettings(shooterConfiguration);
        shooterMotor.setInverted(InvertType.InvertMotorOutput);
        shooterMotor.setNeutralMode(NeutralMode.Coast);

        setDefaultCommand(new SearchForTargetCommand(this));
    }

    /** Turns on the shooter, using PID control to maintain a constant rotational velocity. */
    public void setShooterSpeed(double rotationsPerSecond) {
        if(rotationsPerSecond == 0) {
            shooterMotor.set(ControlMode.Disabled, 0);
        }
        else {
            //enc/ms
            shooterMotor.set(ControlMode.Velocity, RobotMap.encoderUnitsPerRotation * rotationsPerSecond / 1000);
        }
    }

    @Override
    public String toString() {
        return "Launcher";
    }
}