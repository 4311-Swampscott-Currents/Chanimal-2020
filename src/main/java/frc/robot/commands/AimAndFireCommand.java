package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotMap;
// Hey sweetheart
/** If the Limelight has sight of the target, this command causes the robot to turn toward it, index all balls, and then launch them. */
public class AimAndFireCommand extends SequentialCommandGroup {
    public AimAndFireCommand() {
        if(RobotMap.limelight.hasTarget()) {
            addRequirements(RobotMap.limelight, RobotMap.drivetrain, RobotMap.conveyorBelt, RobotMap.launcher);
            addCommands(
                new ParallelCommandGroup(
                    new TurnToRelativeAngleCommand(RobotMap.limelight.getTargetDegreesX()),
                    new IndexBallsCommand()
                )/*,
                new LaunchAllBallsCommand()*/
            );
        }
    }
}