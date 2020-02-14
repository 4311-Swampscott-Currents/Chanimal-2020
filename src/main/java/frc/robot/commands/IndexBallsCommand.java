package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class IndexBallsCommand extends CommandBase {
    
    private double finalIndexTime = 0;
    private double objectFoundTime = 0;

    public IndexBallsCommand() {
        addRequirements(RobotMap.conveyorBelt);
    }

    @Override
    public void initialize() {
        finalIndexTime = Robot.instance.getRobotTime() + RobotMap.conveyorIndexTime;
        RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.defaultConveyorSpeed);
    }

    @Override
    public void execute() {
        if(objectFoundTime == 0) {
            if(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12) {
                objectFoundTime = Robot.instance.getRobotTime() + RobotMap.conveyorIndexTime;
            }
        }
        else if(objectFoundTime < Robot.instance.getRobotTime() && !(RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12)) {
            objectFoundTime = 0;
        }
    }

    @Override
    public boolean isFinished() {
        return finalIndexTime < Robot.instance.getRobotTime() || (objectFoundTime != 0 && objectFoundTime < Robot.instance.getRobotTime() && RobotMap.conveyorBelt.indexSensor.getRangeInches() < RobotMap.indexThresholdLength * 12);
    }
}