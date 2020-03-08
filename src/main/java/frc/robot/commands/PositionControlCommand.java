package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;
import frc.robot.SpinnerColor;

public class PositionControlCommand extends CommandBase {

    private SpinnerColor colorGoal = SpinnerColor.Unknown;

    public PositionControlCommand(SpinnerColor color) {
        colorGoal = color;
        addRequirements(RobotMap.spinner);
    }

    @Override
    public void initialize() {
        RobotMap.spinner.runSpinnerMotor(RobotMap.spinnerMaxSpeed * 0.5);
    }

    @Override
    public boolean isFinished() {
        return RobotMap.spinner.getSensorColor() == colorGoal;
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.spinner.runSpinnerMotor(0);
    }
}