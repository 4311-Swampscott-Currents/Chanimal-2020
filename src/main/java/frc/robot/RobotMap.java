package frc.robot;

import frc.robot.subsystems.Drivetrain;

public final class RobotMap {

    public static RobotJoystick joystick;
    public static Drivetrain drivetrain;

    public final static double wheelDiameter = 1.57079632679; // ft
    public final static double motorRotationsPerWheel = -1; // unitless
    public final static double motorRotationsPerFoot = motorRotationsPerWheel / wheelDiameter; // rots/ft
    public final static double encoderUnitsPerFoot = 4096 * motorRotationsPerFoot; // sensor units/ft

    public static void initialize() {
        joystick = new RobotJoystick(0);
        drivetrain = new Drivetrain();
    }
}