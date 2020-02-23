package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

/** This class is used to send diagnostic messages and signals to Shuffleboard. */
public final class Feedback {

    private static String feedbackString = "";

    /** Generates a robot message in the Shuffleboard logs. */
    public static void log(String message) {
        feedbackString = "[Robot] " + message + System.lineSeparator() + feedbackString;
        SmartDashboard.putString("Feedback", feedbackString);
    }

    /** Generates a message specific to a certain subsystem in the Shuffleboard logs. */
    public static void log(Subsystem system, String message) {
        feedbackString = "[" + system.toString() + "] " + message + System.lineSeparator() + feedbackString;
        SmartDashboard.putString("Feedback", feedbackString);
    }

    /** Sets the status of a certain subsystem on Shuffleboard. */
    public static void setStatus(String statusName, String status) {
        NetworkTableInstance.getDefault().getTable("Status").getEntry(statusName).setString(status);
    }
}