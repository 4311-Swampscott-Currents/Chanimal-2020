package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;

/** This command quickly actuates the climbing winch, releasing extra belt so that the climbing hook may extend. */
public class ReleaseClimberApparatus extends CommandBase {
    
    public double releaseClimbTime = 0;

    public ReleaseClimberApparatus() {
        addRequirements(RobotMap.climber);
    }

    @Override
    public void initialize() {
        Feedback.setStatus("Climber", "Deploying belt");
        releaseClimbTime = Robot.instance.getRobotTime() + RobotMap.climberReleaseTime;
        RobotMap.climber.setClimberSpeed(RobotMap.climberReleaseSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.climber.setClimberSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return releaseClimbTime < Robot.instance.getRobotTime();
    }
}