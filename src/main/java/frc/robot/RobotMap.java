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
    public static CameraManager cameraManager;
    public static Spinner spinner;

    public final static double sweepSpeed = 0.5;
    public final static double sweepSpeedSlow = 0.25;
    public final static double wheelDiameter = 1.57079632679; // ft
    public final static double motorRotationsPerWheel = 10.75; // unitless
    public final static double encoderUnitsPerRotation = 4096;
    public final static double motorRotationsPerFoot = motorRotationsPerWheel / wheelDiameter; // rots/ft
    public final static double encoderUnitsPerFoot = encoderUnitsPerRotation * motorRotationsPerFoot; // sensor units/ft
    public final static double robotRadius = 0.90625;
    public final static double intakeThresholdLength = 0.667; //ft
    public final static double intakeWaitTime = 0.1; //seconds
    public final static double intakeSuckTime = 0.4; //seconds
    public final static double intakeDisableTime = 0.125; //seconds
    public final static double conveyorIndexTime = 2.5; //seconds
    public final static double conveyorIndexWaitTime = 0.125; //seconds
    public final static double defaultConveyorSpeed = 0.575; //pwr
    public final static double defaultIntakeSpeed = 0.5; //pwr
    public final static double indexThresholdLength = 0.55; //ft
    public final static double shootBallTime = 0.3; //seconds
    public final static double limelightWaitTime = 0.1; //seconds
    public final static double shooterConveyorActuationTime = 1; //seconds
    public final static double shooterConveyorActuationSpeed = 0.5  ; //pwr
    public final static double shooterConveyorOffActuationTime = 1; //seconds
    public final static double climberMotorSpeed = 1; //pwr
    public final static double climberHookMotorSpeed = 1; //pwr
    public final static double climberReleaseDebounceButtonTime = 1; //s
    public final static double climberReleaseTime = 0.2; //s
    public final static double climberReleaseSpeed = 0.75; //pwr
    public final static double climberDrivetrainSlowdownPercentage = 0.707;
    public final static double shooterManualControlSpeedIncrements = 0.5; //rots/sec
    public final static double shooterMaxSpeed = 95; //rots/sec
    public final static double gamePlanIdleTime = 0.25; //s
    public final static double limelightPerspectiveConstantP = 0.2379283874; //unitless
    public final static double limelightThreshold = 4; //pixel
    public final static double conveyorIndexSpeed = 0.45; //pwr
    public final static double shooterConveyorInitialOffTime = 2;
    public final static double spinnerColorThreshold = 0.25;
    public final static double spinnerMaxSpeed = 0.8; //pwr

    /** Creates all of the robot subsystem objects. */
    public static void initialize() {
        joystick = new RobotJoystick(0);
        cameraManager = new CameraManager();
        limelight = new Limelight();
        drivetrain = new Drivetrain();
        conveyorBelt = new ConveyorBelt();
        launcher = new Launcher();
        climber = new Climber();
        spinner = new Spinner();
    }

    /** Converts a target in Limelight pixels to launcher speed in r/s. */
    public static double limelightTargetHeightToLauncherSpeed(double x) {
        // edited return 0.0000071194192770191 * Math.pow(x, 4) - 0.00148151580351 * Math.pow(x, 3) + 0.1194240947187 * Math.pow(x, 2) - 4.495256009821 * x + 98.381876157753;
        return 0.0000071194192770191 * Math.pow(x, 4) - 0.00148151580351 * Math.pow(x, 3) + 0.1194240947187 * Math.pow(x, 2) - 4.4525256009821 * x + 98.381876157753;
    }

    /** Converts a target in Limelight pixels to launcher speed in r/s, accounting for the robot's angle. */
    public static double angledLimelightTargetHeightToLauncherSpeed(double x) {
        return limelightTargetHeightToLauncherSpeed((1 - limelightPerspectiveConstantP * Math.abs(Math.sin(drivetrain.navXGyroscope.getAngle() * (Math.PI / 180)))) * x);
    }

    /** Converts a distance from the target to launcher speed in r/s. */
    public static double distanceToLauncherSpeed(double x) {
        if(x <= 9) {
            return 90; //return arbitrarily large constant
        }
        else if(x >= 41.5) {
            return x - 2; //return fairly large constant that might still work
        }
        else {
            return (-0.00064545060726777 * Math.pow(x, 4) + 0.06072661518602 * Math.pow(x, 3) - 1.7150612524702 * Math.pow(x, 2) + 52.457560556833 * x - 372.72509759758) / (x - 9);
        }
    }
}