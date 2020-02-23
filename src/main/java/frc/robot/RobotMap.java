package frc.robot;

import frc.robot.subsystems.*;

/** Contains the robot subsystems and configurable constants specific to the robot. */
public final class RobotMap {

    public static RobotJoystick joystick;
    public static Drivetrain drivetrain;
    public static ConveyorBelt conveyorBelt;
    public static Launcher launcher;
    public static Climber climber;
    public static Limelight limelight;

    public final static double wheelDiameter = 1.57079632679; // ft
    public final static double motorRotationsPerWheel = 10.75; // unitless
    public final static double encoderUnitsPerRotation = 4096;
    public final static double motorRotationsPerFoot = motorRotationsPerWheel / wheelDiameter; // rots/ft
    public final static double encoderUnitsPerFoot = encoderUnitsPerRotation * motorRotationsPerFoot; // sensor units/ft
    public final static double robotRadius = 0.90625;
    public final static double intakeThresholdLength = 0.667; //ft
    public final static double intakeWaitTime = 0.02; //seconds
    public final static double intakeSuckTime = 0.25; //seconds
    public final static double intakeDisableTime = 0.1; //seconds
    public final static double conveyorIndexTime = 2; //seconds
    public final static double conveyorIndexWaitTime = 0.1; //seconds
    public final static double defaultConveyorSpeed = 0.5; //pwr
    public final static double defaultIntakeSpeed = 0.5; //pwr
    public final static double indexThresholdLength = 0.833; //ft
    public final static double shootBallTime = 0.3; //seconds
    public final static double limelightWaitTime = 0.1; //seconds
    public final static double shooterConveyorActuationTime = 0.7; //seconds
    public final static double shooterConveyorActuationSpeed = 0.7; //pwr
    public final static double shooterConveyorOffActuationTime = 0.2; //seconds
    public final static double climberMotorSpeed = 0.5; //pwr
    public final static double climberHookMotorSpeed = 0.75; //pwr
    public final static double climberReleaseDebounceButtonTime = 1; //s
    public final static double climberReleaseTime = 0.2; //s
    public final static double climberReleaseSpeed = 0.75; //pwr
    public final static double climberDrivetrainSlowdownPercentage = 0.707;
    public final static double shooterWaitToSpinUpTime = 0.5; //s
    public final static double shooterManualControlSpeedIncrements = 5; //rots/sec
    public final static double shooterMaxSpeed = 95; //rots/sec

    /** Creates all of the robot subsystem objects. */
    public static void initialize() {
        joystick = new RobotJoystick(0);
        limelight = new Limelight();
        drivetrain = new Drivetrain();
        conveyorBelt = new ConveyorBelt();
        launcher = new Launcher();
        climber = new Climber();
    }
}