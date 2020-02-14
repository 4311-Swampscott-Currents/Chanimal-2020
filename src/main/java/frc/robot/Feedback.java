package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Subsystem;

public final class Feedback {

    private static NetworkTableEntry infoLogEntry = NetworkTableInstance.getDefault().getEntry("driverInfoLog");

    public static void log(String message) {
        infoLogEntry.setString(infoLogEntry.getString("") + "[Robot] " + message + "\n");
    }

    public static void log(Subsystem system, String message) {
        infoLogEntry.setString(infoLogEntry.getString("") + "[" + system.getClass().getName() + "] " + message + "\n");
    }
}