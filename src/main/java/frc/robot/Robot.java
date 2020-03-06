/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.swampscottcurrents.serpentframework.*;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.ExecuteGamePlanStrategyCommand;
import frc.robot.commands.ManualControlCommand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends FastRobot {

    public static Robot instance;
    
    private RobotMode currentMode;
    
    @Override
    public void robotStart() {
        instance = this;
        Preferences.getInstance().removeAll();
        RobotMap.initialize();
        RobotMap.drivetrain.navXGyroscope.setAngleAdjustment(180);
        Feedback.log("Robot started.");
        CameraServer.getInstance().startAutomaticCapture(0);
    }

    @Override
    public void robotUpdate() {
        Feedback.setStatus("Match Time", "" + getMatchTime());
        RobotMap.limelight.update();
        NetworkTableInstance.getDefault().getEntry("robotOrientationY").setDouble(RobotMap.drivetrain.navXGyroscope.getAngle());
    }

    @Override
    public void autonomousStart() {
        Feedback.log("Beginning autonomous.");
        CommandScheduler.getInstance().schedule(new ExecuteGamePlanStrategyCommand());
    }

    @Override
    public void autonomousEnd() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void teleopStart() {
        Feedback.log("Beginning teleop.");
    }

    @Override
    public void disabledStart() {
        setRobotMode(RobotMode.Disabled);
    }

    @Override
    public void teleopUpdate() {
        if(RobotMap.joystick.getButtonReleased("Toggle Manual Control")) {
            CommandScheduler.getInstance().schedule(false, new ManualControlCommand());
        }
    }

    public void setRobotMode(RobotMode mode) {
        currentMode = mode;
        Feedback.setStatus("Robot", mode.toString());
    }

    public RobotMode getRobotMode() {
        return currentMode;
    }
}
