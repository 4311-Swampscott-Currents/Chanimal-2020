package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Climber;

/** This is the default command that runs on the climbing subsystem.  It waits for the driver to release the climbing apparatus. */
public class AwaitClimbCommand extends CommandBase {
    
    public double releaseClimbTime = 0;

    public AwaitClimbCommand(Climber system) {
        addRequirements(system);
        Feedback.setStatus("Climber", "Idle"); 
    }

    @Override
    public void initialize() {
        Feedback.setStatus("Climber", "Idle"); 
    }

    @Override
    public void execute() {
        if(RobotMap.joystick.getButtonReleased("Deploy Climbing Apparatus")) {
            if(releaseClimbTime > Robot.instance.getRobotTime()) {
                CommandScheduler.getInstance().schedule(
                    new SequentialCommandGroup(
                        new ReleaseClimberApparatus(),
                        new OperatorClimbCommand()
                    )
                );
            }
            else {
                releaseClimbTime = Robot.instance.getRobotTime() + RobotMap.climberReleaseDebounceButtonTime;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}