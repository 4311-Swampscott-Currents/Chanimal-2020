package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.AwaitClimbCommand;

/** This class contains functions for controlling the climbing hook and climbing winch. */
public class Climber implements Subsystem {
    
    public WPI_VictorSPX climberMotorA = new WPI_VictorSPX(31);
    public WPI_VictorSPX climberMotorB = new WPI_VictorSPX(32);
    public WPI_TalonSRX hookMotor = new WPI_TalonSRX(33);

    public SpeedControllerGroup climberMotors = new SpeedControllerGroup(climberMotorA, climberMotorB);

    public Climber() {
        hookMotor.setNeutralMode(NeutralMode.Brake);
        climberMotorA.setNeutralMode(NeutralMode.Coast);
        climberMotorB.setNeutralMode(NeutralMode.Coast);

        setDefaultCommand(new AwaitClimbCommand(this));
    }

    /** Sets the climbing winch speed. */
    public void setClimberSpeed(double speed) {
        if(speed >= 0) {
            climberMotors.set(speed);
        }
    }

    /** Moves the climbing hook's telescoping arm up/down. */
    public void setHookMotorSpeed(double speed) {
        hookMotor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public String toString() {
        return "Climber";
    }
}