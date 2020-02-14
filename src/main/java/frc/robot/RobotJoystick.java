package frc.robot;

import java.util.HashMap;

import org.swampscottcurrents.serpentframework.ConfigurableJoystick;

public final class RobotJoystick extends ConfigurableJoystick {

    public RobotJoystick(int port) {
        super(port);
    }

    @Override
    public HashMap<String, Integer> getDefaultButtonBindings() {
        HashMap<String, Integer> toRet = super.getDefaultButtonBindings();
        toRet.put("Fire", 1);
        toRet.put("Toggle Intake", 3);
        toRet.put("Toggle Manual Control", 9);
        return toRet;
    }

    @Override
    public HashMap<String, Double> getDefaultControllerParameters() {
        HashMap<String, Double> toReturn = new HashMap<String, Double>();
        toReturn.put("Deadzone", 0.1);
        return toReturn;
    }
}