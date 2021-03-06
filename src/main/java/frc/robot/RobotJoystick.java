package frc.robot;

import java.util.HashMap;

import org.swampscottcurrents.serpentframework.ConfigurableJoystick;

/** Manages all of the button bindings used to control the robot. */
public final class RobotJoystick extends ConfigurableJoystick {

    public RobotJoystick(int port) {
        super(port);
    }

    @Override
    public HashMap<String, Integer> getDefaultButtonBindings() {
        HashMap<String, Integer> toRet = super.getDefaultButtonBindings();
        toRet.put("Fire", 1);
        toRet.put("Find Target", 3);
        toRet.put("Toggle Intake", 2);
        toRet.put("Toggle Manual Control", 9);
        toRet.put("Conveyor Belt Up", 6);
        toRet.put("Conveyor Belt Down", 7);
        toRet.put("Climbing Hook Up", 11);
        toRet.put("Climbing Hook Down", 10);
        toRet.put("Run Climber", 8);
        toRet.put("Increment Launcher Speed", 5);
        toRet.put("Decrement Launcher Speed", 4);
        toRet.put("Swap Cameras", 8);
        toRet.put("Run Position Control", 16);
        toRet.put("Run Rotation Control", 16);
        toRet.put("Run Spinner", 17);
        toRet.put("Reset Gyro", 17);
        return toRet;
    }

    @Override
    public HashMap<String, Double> getDefaultControllerParameters() {
        HashMap<String, Double> toReturn = new HashMap<String, Double>();
        toReturn.put("Deadzone", 0.025);
        return toReturn;
    }
}