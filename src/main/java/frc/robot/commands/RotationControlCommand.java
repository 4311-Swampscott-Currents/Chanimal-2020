package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;
import frc.robot.SpinnerColor;

public class RotationControlCommand extends CommandBase {
    
    public int rotationsToMake;
    private SpinnerColor previousColor = SpinnerColor.Unknown;

    public RotationControlCommand(int rotations) {
        rotationsToMake = rotations;
        addRequirements(RobotMap.spinner);
    }

    @Override
    public void initialize() {
        previousColor = RobotMap.spinner.getSensorColor();
        setSpinnerSpeed();
    }

    @Override
    public void execute() {
        SpinnerColor color = RobotMap.spinner.getSensorColor();
        if(color != SpinnerColor.Unknown && color != previousColor) {
            previousColor = color;
            rotationsToMake--;
        }
        setSpinnerSpeed();
    }

    @Override
    public boolean isFinished() {
        return rotationsToMake <= 0;
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.spinner.runSpinnerMotor(0);
    }

    private void setSpinnerSpeed() {
        if(rotationsToMake > 10) {
            RobotMap.spinner.runSpinnerMotor(RobotMap.spinnerMaxSpeed);
        }
        else if(rotationsToMake > 5) {
            RobotMap.spinner.runSpinnerMotor(RobotMap.spinnerMaxSpeed * 0.75);
        }
        else {
            RobotMap.spinner.runSpinnerMotor(RobotMap.spinnerMaxSpeed * 0.5);
        }
    }
}