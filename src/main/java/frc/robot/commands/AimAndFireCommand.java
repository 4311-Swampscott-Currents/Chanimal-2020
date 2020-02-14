package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotMap;
// Hey sweetheart
public class AimAndFireCommand extends SequentialCommandGroup {
    public AimAndFireCommand() {
        if(TurnTowardsGoalCommand.isAvailable()) {
            addCommands(
                new ParallelCommandGroup(
                    new TurnTowardsGoalCommand(),
                    new IndexBallsCommand()
                ),
                new LaunchAllBallsCommand()
            );
        }
    }
}