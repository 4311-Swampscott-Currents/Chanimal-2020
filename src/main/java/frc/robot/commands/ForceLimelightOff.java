package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;
import frc.robot.subsystems.Limelight;

/** Forces the Limelight's LEDs off while running. */
public class ForceLimelightOff extends CommandBase {
    public ForceLimelightOff(Limelight system) {
        addRequirements(system);
    }

    @Override
    public void initialize() {
        RobotMap.limelight.setLEDsOn(false);
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.limelight.setLEDsOn(true);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}