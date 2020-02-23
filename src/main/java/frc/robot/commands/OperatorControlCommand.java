package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotMode;
import frc.robot.subsystems.Drivetrain;

/** This is the default command that the Drivetrain subsystem runs.  It allows an operator to control it using joystick input. */
public class OperatorControlCommand extends CommandBase {

    public OperatorControlCommand(Drivetrain system) {
        addRequirements(system);
    }

    @Override
    public void initialize() {
        RobotMap.joystick.debounceAllButtons();
        Robot.instance.setRobotMode(RobotMode.Offense);        
    }

    @Override
    public void execute() {
        RobotMap.drivetrain.driveDifferential(-RobotMap.joystick.getXAxis(), RobotMap.joystick.getYAxis());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.drivetrain.stopMotors();
    }
}