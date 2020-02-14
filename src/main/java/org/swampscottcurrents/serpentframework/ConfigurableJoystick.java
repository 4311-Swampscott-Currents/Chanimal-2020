package org.swampscottcurrents.serpentframework;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;

import java.util.HashMap;

/** Represents a joystick with buttons/parameters that are easily configurable using Shuffleboard. */
public class ConfigurableJoystick extends Joystick {

    public int joystickNumber;

    public ConfigurableJoystick(int port) {
        super(port);
        joystickNumber = port;
        if(!Preferences.getInstance().getBoolean("sf.joystick" + joystickNumber, false)) {
            resetJoystick();
        }
        NetworkTableInstance.getDefault().addEntryListener(NetworkTableInstance.getDefault().getTable("Preferences").getEntry("sf.joystick" + joystickNumber), notification -> {
            if(!Preferences.getInstance().getBoolean("sf.joystick" + joystickNumber, false)) {
                resetJoystick();
            }
         }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate | EntryListenerFlags.kDelete);
    }

    private void resetJoystick() {
        Preferences.getInstance().putBoolean("sf.joystick" + joystickNumber, true);
            HashMap<String, Integer> defBindings = getDefaultButtonBindings();
            for(String key : defBindings.keySet()) {
                Preferences.getInstance().putInt("sf.joystick" + joystickNumber + "/button/" + key, defBindings.get(key));
            }
            HashMap<String, Double> defParams = getDefaultControllerParameters();
            for(String key : defParams.keySet()) {
                Preferences.getInstance().putDouble("sf.joystick" + joystickNumber + "/parameter/" + key, defParams.get(key));
            }
    }

    /** Returns the current button binding for a certain action. */
    public final int getJoystickButtonNumber(String buttonName) {
        return Preferences.getInstance().getInt(buttonName, 0);
    }

    /** Returns the current setting for a joystick parameter. */
    public final double getJoystickParameter(String parameter) {
        return Preferences.getInstance().getDouble(parameter, 0);
    }

    /** This function returns a map of all default button bindings.  It should be overridden when defining new behaviors. */
    public HashMap<String, Integer> getDefaultButtonBindings() {
        return new HashMap<String, Integer>();
    }

    /** This function returns a map of all default controller parameters.  It should be overridden when defining new behaviors. */
    public HashMap<String, Double> getDefaultControllerParameters() {
        HashMap<String, Double> toReturn = new HashMap<String, Double>();
        toReturn.put("Deadzone", 0.1);
        return toReturn;
    }

    /** Returns the x-position of the controller's joystick. */
    public double getXAxis() {
        return processAxisInput(super.getX());
    }

    /** Returns the y-position of the controller's joystick. */
    public double getYAxis() {
        return processAxisInput(super.getY());
    }

    /** Returns the z-position of the controller's joystick. */
    public double getZAxis() {
        return processAxisInput(super.getZ());
    }

    /** Returns whether a button bound to the specified name has just been pressed down. */
    public final boolean getButtonPressed(String buttonName) {
        return super.getRawButtonPressed(Preferences.getInstance().getInt("sf.joystick" + joystickNumber + "/button/" + buttonName, 0));
    }

    /** Returns whether a button bound to the specified name is being held down. */
    public final boolean getButton(String buttonName) {
        return super.getRawButton(Preferences.getInstance().getInt("sf.joystick" + joystickNumber + "/button/" + buttonName, 0));
    }

    /** Returns whether a button bound to the specified name has just been released. */
    public final boolean getButtonReleased(String buttonName) {
        return super.getRawButtonReleased(Preferences.getInstance().getInt("sf.joystick" + joystickNumber + "/button/" + buttonName, 0));
    }

    /** Applies mathematical operations to controller input such as deadband, squaring, et cetera. */
    public double processAxisInput(double input) {
        if(Math.abs(input) < Preferences.getInstance().getDouble("sf.joystick" + joystickNumber + "/parameter/Deadzone", 0)) {
            return 0;
        }
        return Math.signum(input) * (input * input);
    }
}