package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Feedback;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotMode;
import frc.robot.subsystems.Launcher;

/** This command allows the driver complete manual control over the robot, without any automation or restrictions. */
public class ManualControlCommand extends CommandBase {

    private boolean intakeEnabled = false;
    private static double lastNetworkTablesRobotPositionChange = -1;

    public ManualControlCommand() {
        addRequirements(RobotMap.drivetrain, RobotMap.conveyorBelt, RobotMap.launcher, RobotMap.climber, RobotMap.limelight);
    }

    @Override
    public void initialize() {
        Robot.instance.setRobotMode(RobotMode.Manual);
        NetworkTableInstance.getDefault().getEntry("isManualMode").setBoolean(true);
    }

    @Override
    public void execute() {
        RobotMap.drivetrain.driveDifferential(-RobotMap.joystick.getXAxis(), RobotMap.joystick.getYAxis());
        if(RobotMap.joystick.getButtonReleased("Increment Launcher Speed")) {
            Launcher.launcherSpeed = clamp(Launcher.launcherSpeed + RobotMap.shooterManualControlSpeedIncrements, 0, RobotMap.shooterMaxSpeed);
        }
        if(RobotMap.joystick.getButtonReleased("Decrement Launcher Speed")) {
            Launcher.launcherSpeed = clamp(Launcher.launcherSpeed - RobotMap.shooterManualControlSpeedIncrements, 0, RobotMap.shooterMaxSpeed);
        }
        if(NetworkTableInstance.getDefault().getEntry("guiRobotPositionY").exists() && NetworkTableInstance.getDefault().getEntry("guiRobotPositionY").getLastChange() != lastNetworkTablesRobotPositionChange) {
            //do some fun math to figure out speed from distance
            Launcher.launcherSpeed = Math.sqrt(Math.pow(NetworkTableInstance.getDefault().getEntry("guiRobotPositionX").getDouble(0) - ExecuteGamePlanStrategyCommand.targetLocationX, 2) + Math.pow(NetworkTableInstance.getDefault().getEntry("guiRobotPositionY").getDouble(0) - ExecuteGamePlanStrategyCommand.targetLocationY, 2));
            Launcher.launcherSpeed = RobotMap.distanceToLauncherSpeed(Launcher.launcherSpeed);
            lastNetworkTablesRobotPositionChange = NetworkTableInstance.getDefault().getEntry("guiRobotPositionY").getLastChange();
        }
        if(RobotMap.joystick.getButton("Fire")) {
            RobotMap.launcher.setShooterSpeed(Launcher.launcherSpeed);
            Feedback.setStatus("Launcher", "Firing (" + Launcher.launcherSpeed + " rots/sec)");
        }
        else {
            RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, 0);
            Feedback.setStatus("Launcher", "Idle (" + Launcher.launcherSpeed + " rots/sec)");
        }
        if(RobotMap.joystick.getButtonReleased("Toggle Intake")) {
            //intakeEnabled = !intakeEnabled;
            //RobotMap.conveyorBelt.setIntakeOn(intakeEnabled);
            Launcher.launcherSpeed = RobotMap.angledLimelightTargetHeightToLauncherSpeed(RobotMap.limelight.getTargetHeight()) + 0.4;
        }
        if(RobotMap.joystick.getButton("Conveyor Belt Up")) {
            RobotMap.conveyorBelt.setConveyorSpeed(RobotMap.defaultConveyorSpeed);
        }
        else if(RobotMap.joystick.getButton("Conveyor Belt Down")) {
            RobotMap.conveyorBelt.setConveyorSpeed(-RobotMap.defaultConveyorSpeed);
            RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, -0.3);
        }
        else {
            if(!RobotMap.joystick.getButton("Fire")) {
                RobotMap.launcher.shooterMotor.set(ControlMode.PercentOutput, 0);
            }
            RobotMap.conveyorBelt.setConveyorSpeed(0);
        }
        if(RobotMap.joystick.getButton("Run Climber")) {
            Feedback.setStatus("Climber", "Climbing");
            RobotMap.climber.setClimberSpeed(RobotMap.climberMotorSpeed);
        }
        else {
            Feedback.setStatus("Climber", "Idle");
            RobotMap.climber.setClimberSpeed(0);
        }
        if(RobotMap.joystick.getButton("Climbing Hook Up")) {
            RobotMap.climber.setHookMotorSpeed(RobotMap.climberHookMotorSpeed);
        }
        else if(RobotMap.joystick.getButton("Climbing Hook Down")) {
            RobotMap.climber.setHookMotorSpeed(-RobotMap.climberHookMotorSpeed);
        }
        else {
            RobotMap.climber.setHookMotorSpeed(0);
        }
    }

    @Override
    public boolean isFinished() {
        return RobotMap.joystick.getButtonReleased("Toggle Manual Control");
    }

    @Override
    public void end(boolean interrupted) {
        NetworkTableInstance.getDefault().getEntry("isManualMode").delete();
        RobotMap.drivetrain.stopMotors();
        RobotMap.conveyorBelt.setIntakeOn(false);
        RobotMap.conveyorBelt.setConveyorSpeed(0);
        RobotMap.launcher.setShooterSpeed(0);
        Feedback.setStatus("Launcher", "Idle");
        RobotMap.climber.setClimberSpeed(0);
        Feedback.setStatus("Climber", "Idle");
        RobotMap.climber.setHookMotorSpeed(0);
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}