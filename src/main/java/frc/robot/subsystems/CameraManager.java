package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.OperatorSwapCamerasCommand;

public class CameraManager implements Subsystem {

    public UsbCamera[] cameras;
    public int currentCameraIndex = 0;

    public CameraManager() {
        cameras = new UsbCamera[2];
        cameras[0] = CameraServer.getInstance().startAutomaticCapture(0);
        cameras[1] = CameraServer.getInstance().startAutomaticCapture(1);
        
        for(UsbCamera cam : cameras) {
            cam.setResolution(320, 240);
            cam.setFPS(20);
        }
        
        setDefaultCommand(new OperatorSwapCamerasCommand(this));
    }

    public void setCameraIndex(int index) {
        currentCameraIndex = index;
        CameraServer.getInstance().getServer().setSource(cameras[index]);
    }
}