package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.*;
import frc.robot.subsystems.*;

public class OperatorSwapCamerasCommand extends CommandBase {
    public OperatorSwapCamerasCommand(CameraManager system) {
        addRequirements(system);
    }

    @Override
    public void execute() {
        if(RobotMap.joystick.getButtonReleased("Swap Cameras")) {
            //RobotMap.cameraManager.setCameraIndex((RobotMap.cameraManager.currentCameraIndex + 1) % RobotMap.cameraManager.cameras.length);
        }
    }
}