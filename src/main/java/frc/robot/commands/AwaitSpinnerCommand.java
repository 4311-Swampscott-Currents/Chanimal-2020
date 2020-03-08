package frc.robot.commands;

import java.util.Dictionary;
import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Feedback;
import frc.robot.RobotMap;
import frc.robot.SpinnerColor;
import frc.robot.subsystems.Spinner;

public class AwaitSpinnerCommand extends CommandBase {

    public SpinnerColor selectedColor = SpinnerColor.Unknown;

    private HashMap<SpinnerColor, SpinnerColor> colorTranspose = new HashMap<SpinnerColor, SpinnerColor>();

    public AwaitSpinnerCommand(Spinner system) {

        //for moving the spinner counterclockwise, reading colors from the very front
        colorTranspose.put(SpinnerColor.Blue, SpinnerColor.Red);
        colorTranspose.put(SpinnerColor.Green, SpinnerColor.Yellow);
        colorTranspose.put(SpinnerColor.Red, SpinnerColor.Blue);
        colorTranspose.put(SpinnerColor.Yellow, SpinnerColor.Green);
        colorTranspose.put(SpinnerColor.Unknown, SpinnerColor.Unknown);

        addRequirements(system);
    }

    @Override
    public void execute() {
        if(RobotMap.joystick.getButtonReleased("Run Rotation Control")) {
            CommandScheduler.getInstance().schedule(new RotationControlCommand(32));
        }
        if(RobotMap.joystick.getButtonReleased("Run Position Control")) {
            if(selectedColor == SpinnerColor.Unknown) {
                Feedback.log("Position control information is not available yet.");
            }
            else {
                CommandScheduler.getInstance().schedule(new PositionControlCommand(colorTranspose.get(selectedColor))); //transpose the color so the robot knows which color should be on the other side of the wheel
            }
        }
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(selectedColor == SpinnerColor.Unknown && gameData.length() > 0)
        {
            switch (gameData.charAt(0))
            {
                case 'B' :
                    selectedColor = SpinnerColor.Blue;
                break;
                case 'G' :
                    selectedColor = SpinnerColor.Green;
                break;
                case 'R' :
                    selectedColor = SpinnerColor.Red;
                break;
                case 'Y' :
                    selectedColor = SpinnerColor.Yellow;
                break;
                default :
                    Feedback.log("Received bad data for color wheel!");
                break;
            }
            Feedback.log("Position control data is now available; the control panel should be turned toward " + selectedColor.toString());
        }
    }
}