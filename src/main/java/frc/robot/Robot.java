/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.swampscottcurrents.serpentframework.FastRobot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends FastRobot {
    @Override
    public void robotStart() {
        Preferences.getInstance().removeAll();
        RobotMap.initialize();
    }

    @Override
    public void robotUpdate() {
        NetworkTableInstance.getDefault().getEntry("robotOrientationY").setDouble(RobotMap.drivetrain.navXGyroscope.getAngle());
    }
}
