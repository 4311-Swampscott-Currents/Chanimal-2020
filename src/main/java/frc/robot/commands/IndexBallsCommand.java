package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;

/** Brings all balls to the top of the conveyor. */
public class IndexBallsCommand extends CommandBase {
    
    private double finalIndexTime = 0;
    private double objectFoundTime = 0;

    public IndexBallsCommand() {
        addRequirements(RobotMap.conveyorBelt);
    }

    @Override
    public void initialize() {
        finalIndexTime = Robot.instance.getRobotTime() + RobotMap.conveyorIndexTime;
        if(RobotMap.conveyorBelt.indexSensor.getRangeInches() > RobotMap.indexThresholdLength * 12) {
            RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.conveyorIndexSpeed);
        }
        Feedback.setStatus("Conveyor", "Indexing");
    }

    @Override
    public void execute() {
        /*if(objectFoundTime == 0) {
            if(RobotMap.conveyorBelt.indexSensor.getRangeInches() > RobotMap.indexThresholdLength * 12) {
                objectFoundTime = Robot.instance.getRobotTime() + RobotMap.conveyorIndexWaitTime;
                RobotMap.conveyorBelt.setConveyorSpeed(0);
            }
        }
        else if(objectFoundTime < Robot.instance.getRobotTime() && RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
            objectFoundTime = 0;
            RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.defaultConveyorSpeed);
        }*/
    }

    @Override
    public boolean isFinished() {
        return finalIndexTime < Robot.instance.getRobotTime() || RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12;
    }

    @Override
    public void end(boolean interrupted) {
        
        RobotMap.conveyorBelt.setConveyorSpeed(0);
    }
}