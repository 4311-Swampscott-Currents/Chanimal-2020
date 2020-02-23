package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotMode;

/** This command allows the driver to move the climbing hook and winch.  It also slows the drivetrain down for more precise movement, and turns off any other robot subsystems. */
public class OperatorClimbCommand extends CommandBase {

    public OperatorClimbCommand() {
        addRequirements(RobotMap.drivetrain, RobotMap.conveyorBelt, RobotMap.launcher, RobotMap.climber);
    }
    
    @Override
    public void initialize() {
        Feedback.setStatus("Climber", "Climb ready");
        Robot.instance.setRobotMode(RobotMode.Climb);
    }

    @Override
    public void execute() {
        RobotMap.drivetrain.driveDifferential(-RobotMap.joystick.getXAxis() * RobotMap.climberDrivetrainSlowdownPercentage, RobotMap.joystick.getYAxis() * RobotMap.climberDrivetrainSlowdownPercentage);
        if(RobotMap.joystick.getButton("Climbing Hook Up")) {
            RobotMap.climber.setHookMotorSpeed(RobotMap.climberHookMotorSpeed);
        }
        else if(RobotMap.joystick.getButton("Climbing Hook Down")) {
            RobotMap.climber.setHookMotorSpeed(-RobotMap.climberHookMotorSpeed);
        }
        else {
            RobotMap.climber.setHookMotorSpeed(0);
        }

        if(RobotMap.joystick.getButton("Run Climber")) {
            RobotMap.climber.setClimberSpeed(RobotMap.climberMotorSpeed);
            Feedback.setStatus("Climber", "Climbing");
        }
        else {
            RobotMap.climber.setClimberSpeed(0);
            Feedback.setStatus("Climber", "Climb ready");
        }
    }

    @Override
    public boolean isFinished() {
        return RobotMap.joystick.getButtonReleased("Exit Climb");
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.climber.setClimberSpeed(0);
        RobotMap.climber.setHookMotorSpeed(0);
        RobotMap.drivetrain.stopMotors();
    }
}