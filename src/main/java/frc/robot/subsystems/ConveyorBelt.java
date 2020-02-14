package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.IntakeBallsCommand;

public class ConveyorBelt implements Subsystem {

    public Ultrasonic intakeSensor = new Ultrasonic(1, 0);
    public Ultrasonic indexSensor = new Ultrasonic(3, 2);
    public WPI_VictorSPX conveyorBeltMotor = new WPI_VictorSPX(21);
    public WPI_VictorSPX intakeMotor = new WPI_VictorSPX(22);

    public ConveyorBelt() {
        conveyorBeltMotor.setNeutralMode(NeutralMode.Brake);
        intakeMotor.setInverted(InvertType.InvertMotorOutput);
        intakeMotor.setNeutralMode(NeutralMode.Brake);

        intakeSensor.setEnabled(true);
        indexSensor.setEnabled(true);
        intakeSensor.setAutomaticMode(true);
        indexSensor.setAutomaticMode(true);

        setDefaultCommand(new IntakeBallsCommand(this));
    }

    public void setIntakeOn(boolean isItOn) {
        if(isItOn) {
            intakeMotor.set(ControlMode.PercentOutput, RobotMap.defaultIntakeSpeed);
        }
        else {
            intakeMotor.set(ControlMode.Disabled, 0);
        }
    }

    public void setConveyorSpeed(double speed) {
        conveyorBeltMotor.set(ControlMode.PercentOutput, speed);
    }
}