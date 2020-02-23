package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Feedback;
import frc.robot.RobotMap;
import frc.robot.commands.IntakeBallsCommand;

/** Represents the subsystem used for picking up and delivering balls to the shooter on the robot. */
public class ConveyorBelt implements Subsystem {

    /** This is the sensor attached to the base of the robot itake, used for detecting balls. */
    public Ultrasonic intakeSensor = new Ultrasonic(1, 0);
    /** This is the sensor attached to the top of the conveyor belt, used for detecting when the conveyor is full. */
    public Ultrasonic indexSensor = new Ultrasonic(3, 2);
    /** This motor drives the conveyor belt. */
    public WPI_VictorSPX conveyorBeltMotor = new WPI_VictorSPX(21);
    /** This motor drives the hex-bar intake. */
    public WPI_VictorSPX intakeMotor = new WPI_VictorSPX(22);

    public double conveyorMotorSpeed = 0;

    public ConveyorBelt() {
        conveyorBeltMotor.setNeutralMode(NeutralMode.Brake);
        intakeMotor.setInverted(InvertType.InvertMotorOutput);
        intakeMotor.setNeutralMode(NeutralMode.Brake);

        intakeSensor.setEnabled(true);
        indexSensor.setEnabled(true);
        intakeSensor.setAutomaticMode(true);
        indexSensor.setAutomaticMode(true);

        Feedback.setStatus("Intake", "Disabled");
        Feedback.setStatus("Conveyor", "Idle");

        setDefaultCommand(new IntakeBallsCommand(this));
    }

    /** Toggles the intake on or off, using the preset speed. */
    public void setIntakeOn(boolean isItOn) {
        if(isItOn) {
            Feedback.setStatus("Intake", "Enabled");
            intakeMotor.set(ControlMode.PercentOutput, RobotMap.defaultIntakeSpeed);
        }
        else {
            Feedback.setStatus("Intake", "Disabled");
            intakeMotor.set(ControlMode.Disabled, 0);
        }
    }

    /** Turns the conveyor belt on or off to a specified speed. */
    public void setConveyorSpeed(double speed) {
        conveyorBeltMotor.set(ControlMode.PercentOutput, speed);
        conveyorMotorSpeed = speed;
        if(speed > 0) {
            Feedback.setStatus("Conveyor", "Running"); 
        }
        else if(speed < 0) {
            Feedback.setStatus("Conveyor", "Running backwards"); 
        }
        else {
            Feedback.setStatus("Conveyor", "Idle"); 
        }
    }

    @Override
    public String toString() {
        return "Conveyor Belt";
    }
}