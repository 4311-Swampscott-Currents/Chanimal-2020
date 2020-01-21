package frc.robot;

import frc.robot.subsystems.Drivetrain;

public final class RobotMap {

    public static RobotJoystick joystick;
    public static Drivetrain drivetrain;

    public static double wheelDiameter = 1.57079632679; // ft
    public static double motorRotationsPerWheel = -1; // unitless
    public static double motorRotationsPerFoot = motorRotationsPerWheel / wheelDiameter; // deg/ft

    public static void initialize() {
        joystick = new RobotJoystick(0);
        drivetrain = new Drivetrain();
    }
}