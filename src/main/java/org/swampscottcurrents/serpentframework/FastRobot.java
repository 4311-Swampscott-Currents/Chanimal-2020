package org.swampscottcurrents.serpentframework;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/** A robot class which calls periodic update functions as fast as possible. */
public class FastRobot extends RobotBase {

    private Timer matchTimer;
    private Timer robotTimer;
    private double timeDelta;
    private double lastUpdateTime;
    private boolean competitionInProgress = true;

    /** Called once when the robot initally turns on. */
    public void robotStart() {}
    /** Called every robot update cycle, no matter the mode. */
    public void robotUpdate() {}
    /** Called once at the start of autonomous mode. */
    public void autonomousStart() {}
    /** Called every robot update cycle during autonomous mode. */
    public void autonomousUpdate() {}
    /** Called once at the end of autonomous mode. */
    public void autonomousEnd() {}
    /** Called once at the start of teleoperated mode. */
    public void teleopStart() {}
    /** Called every robot update cycle during teleoperated mode. */
    public void teleopUpdate() {}
    /** Called once at the end of teleoperated mode. */
    public void teleopEnd() {}
    /** Called once at the start of test mode. */
    public void testStart() {}
    /** Called every robot update cycle during test mode. */
    public void testUpdate() {}
    /** Called once at the end of test mode. */
    public void testEnd() {}
    /** Called once when the robot is disabled. */
    public void disabledStart() {}
    /** Called every robot update cycle while the robot is disabled. */
    public void disabledUpdate() {}
    /** Called once when the robot is enabled. */
    public void disabledEnd() {}

    @Override
    public final void startCompetition() {
        robotTimer = new Timer();
        matchTimer = new Timer();
        robotTimer.start();
        HAL.observeUserProgramStarting();
        robotStart();
        updateAllianceInformation();
        while (!Thread.currentThread().isInterrupted() && competitionInProgress) {
            if(isDisabled()) {
                disabled();
            }
            else if (isAutonomous()) {
                autonomous();
            }
            else if(isOperatorControl()) {
                operatorControl();
            }
            else if(isTest()) {
                test();
            }
        }
    }

    @Override
    public final void endCompetition() {
        competitionInProgress = false;
    }

    public final void autonomous() {
        HAL.observeUserProgramAutonomous();
        matchTimer.reset();
        matchTimer.start();
        autonomousStart();
        updateTimeDelta();
        while(isAutonomous() && !isDisabled() && competitionInProgress) {
            robotUpdate();
            CommandScheduler.getInstance().run();
            autonomousUpdate();
            updateTimeDelta();
        }
        autonomousEnd();
        updateTimeDelta();
    }

    public final void operatorControl() {
        HAL.observeUserProgramTeleop();
        teleopStart();
        updateTimeDelta();
        while(isOperatorControl() && !isDisabled() && competitionInProgress) {
            robotUpdate();
            CommandScheduler.getInstance().run();
            teleopUpdate();
            updateTimeDelta();
        }
        autonomousEnd();
        updateTimeDelta();
    }

    public final void test() {
        HAL.observeUserProgramTest();
        testStart();
        updateTimeDelta();
        while(isTest() && !isDisabled() && competitionInProgress) {
            robotUpdate();
            CommandScheduler.getInstance().run();
            testUpdate();
            updateTimeDelta();
        }
        testEnd();
        updateTimeDelta();
    }

    public final void disabled() {
        updateAllianceInformation();
        HAL.observeUserProgramDisabled();
        disabledStart();
        updateTimeDelta();
        while(isDisabled() && competitionInProgress) {
            updateAllianceInformation();
            robotUpdate();
            CommandScheduler.getInstance().run();
            disabledUpdate();
            updateTimeDelta();
        }
        disabledEnd();
        updateTimeDelta();
    }

    /** Returns the time in seconds since the robot was turned on. */
    public final double getRobotTime() {
        return robotTimer.get();
    }

    /** Returns the time in seconds since the beginning of the match. */
    public final double getMatchTime() {
        return matchTimer.get();
    }

    /** Returns the time the last robot update cycle took. */
    public final double getTimeDelta() {
        return timeDelta;
    }

    private void updateTimeDelta() {
        timeDelta = robotTimer.get() - lastUpdateTime;
        lastUpdateTime = robotTimer.get();
    }
    
    private void updateAllianceInformation() {
        Alliance alliance = DriverStation.getInstance().getAlliance();
        if(alliance == Alliance.Red) {
            NetworkTableInstance.getDefault().getEntry("sf.alliance").setString("RED");
        }
        else if(alliance == Alliance.Blue) {
            NetworkTableInstance.getDefault().getEntry("sf.alliance").setString("BLUE");
        }
        else {
            NetworkTableInstance.getDefault().getEntry("sf.alliance").setString("UNKNOWN");
        }
    }
}