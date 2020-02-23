package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.AwaitClimbCommand;

/** This class contains functions for controlling the climbing hook and climbing winch. */
public class Climber implements Subsystem {
    
    public WPI_VictorSPX climberMotorA = new WPI_VictorSPX(-1);
    public WPI_VictorSPX climberMotorB = new WPI_VictorSPX(-1);
    public WPI_VictorSPX hookMotor = new WPI_VictorSPX(-1);

    public SpeedControllerGroup climberMotors = new SpeedControllerGroup(climberMotorA, climberMotorB);

    public Climber() {
        hookMotor.setNeutralMode(NeutralMode.Brake);
        climberMotorA.setNeutralMode(NeutralMode.Brake);
        climberMotorB.setNeutralMode(NeutralMode.Brake);

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
        hookMotor.set(speed);
    }

    @Override
    public String toString() {
        return "Climber";
    }
}