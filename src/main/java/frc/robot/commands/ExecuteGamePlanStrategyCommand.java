package frc.robot.commands;

import java.util.stream.Stream;

import org.swampscottcurrents.serpentframework.Quaternion2D;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotMode;

/** Represents a series of actions created in ShuffleBoard using the GamePlan widget.  The actions are synthesized when the command object is created, so storing/reusing it will result in out-of-sync data. */
public class ExecuteGamePlanStrategyCommand extends SequentialCommandGroup {

    public final static double targetLocationX = 0, targetLocationY = 7.6667; //ft

    public ExecuteGamePlanStrategyCommand() {
        addRequirements(RobotMap.drivetrain, RobotMap.conveyorBelt, RobotMap.launcher);
        Robot.instance.setRobotMode(RobotMode.GamePlan);
        try {
            Command[] toRun = parseGamePlanStrategy(NetworkTableInstance.getDefault().getEntry("gamePlanStrategy").getString(""));
            for(Command command : toRun) {
                addCommands(command);
            }
        }
        catch(Exception e) {
            Feedback.log("Unable to execute GamePlan strategy:\n" + e.getMessage());
        }
    }

    /** Converts a GamePlan strategy to a set of Commands that the robot can execute. */
    private Command[] parseGamePlanStrategy(String strategy) throws Exception {
        if(strategy.equals("")) {
            throw new Exception("No GamePlan strategy provided!");
        }
        String[] commandSubsets = Stream.of(strategy.split("\\|")).filter(w -> !w.isEmpty()).toArray(String[]::new);
        Command[] toReturn = new Command[commandSubsets.length - 1];
        String[] parseInfo = commandSubsets[0].split(",");
        double lastPositionX, lastPositionY;
        if(parseInfo[0].equals("RobotPosition")) {
            lastPositionX = Double.parseDouble(parseInfo[1]);
            lastPositionY = Double.parseDouble(parseInfo[2]);
        }
        else {
            throw new Exception("Expected robot position at beginning of strategy information, but got: " + parseInfo[0] + "... out of: " + commandSubsets[0]);
        }
        for(int x = 1; x < commandSubsets.length; x++) {
            parseInfo = commandSubsets[x].split(",");
            if(parseInfo[0].equals("FireBalls")) {
                toReturn[x - 1] = new SequentialCommandGroup(
                    new ParallelCommandGroup(
                        new IndexBallsCommand(),
                        new TurnToAbsoluteAngleCommand(Quaternion2D.fromAxis(targetLocationX - lastPositionX, targetLocationY - lastPositionY))
                    ),
                    new AimAndFireCommand()
                );
            }
            else if(parseInfo[0].equals("Wait")) {
                toReturn[x - 1] = new WaitForTimeCommand(Double.parseDouble(parseInfo[1]));
            }
            else if(parseInfo[0].equals("DriveRobotForwards")) {
                double xPos = Double.parseDouble(parseInfo[1]);
                double yPos = Double.parseDouble(parseInfo[2]);
                toReturn[x - 1] = new ParallelRaceGroup(
                    new ParallelCommandGroup(
                        new TurnToAbsoluteAngleCommand(Quaternion2D.fromAxis(xPos - lastPositionX, yPos - lastPositionY)),
                        new DriveStraightCommand(Math.sqrt(Math.pow(xPos - lastPositionX, 2) + Math.pow(yPos - lastPositionY, 2)))
                    ),
                    new IntakeBallsCommand(RobotMap.conveyorBelt)
                );
                lastPositionX = xPos;
                lastPositionY = yPos;
            }
            else if(parseInfo[0].equals("DriveRobotBackwards")) {
                double xPos = Double.parseDouble(parseInfo[1]);
                double yPos = Double.parseDouble(parseInfo[2]);
                toReturn[x - 1] = new ParallelRaceGroup(
                    new ParallelCommandGroup(
                        new TurnToAbsoluteAngleCommand(Quaternion2D.add(Quaternion2D.fromAxis(xPos - lastPositionX, yPos - lastPositionY), Quaternion2D.fromEuler(180))),
                        new DriveStraightCommand(Math.sqrt(Math.pow(xPos - lastPositionX, 2) + Math.pow(yPos - lastPositionY, 2)), true)
                    ),
                    new IntakeBallsCommand(RobotMap.conveyorBelt)
                );
                lastPositionX = xPos;
                lastPositionY = yPos;
            }
            else {
                throw new Exception("Unexpected input at command number " + x + "!");
            }
        }
        return toReturn;
    }

    /*@Override
    public void execute() {
        super.execute();
        try {
            String toPut = "";
            Field privates = SequentialCommandGroup.class.getDeclaredField("m_commands");
            Field privatesC = SequentialCommandGroup.class.getDeclaredField("m_currentCommandIndex");
            privates.setAccessible(true);
            privatesC.setAccessible(true);
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("command check").setString(((List<Command>)privates.get(this)).get(privatesC.getInt(this)).getClass().getSimpleName());
            }
            catch (Exception e) {
                Feedback.log(e.getMessage());
            }
    }*/
}