package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotMode;
import frc.robot.subsystems.Climber;

/** This command allows the driver to move the climbing hook and winch.  It also slows the drivetrain down for more precise movement, and turns off any other robot subsystems. */
public class OperatorClimbCommand extends CommandBase {

    public OperatorClimbCommand(Climber system) {
        addRequirements(system);
    }
    
    @Override
    public void initialize() {
        Feedback.setStatus("Climber", "Idle");
    }

    @Override
    public void execute() {
        if(RobotMap.joystick.getButton("Climbing Hook Up") && RobotMap.climber.hookLimitSwitch.get()) {
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
            Feedback.setStatus("Climber", "Idle");
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.climber.setClimberSpeed(0);
        RobotMap.climber.setHookMotorSpeed(0);
        RobotMap.drivetrain.stopMotors();
    }
}