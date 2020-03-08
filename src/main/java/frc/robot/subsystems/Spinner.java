package frc.robot.subsystems;

import com.revrobotics.*;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.SpinnerColor;
import frc.robot.commands.AwaitSpinnerCommand;

public class Spinner extends SubsystemBase {
    
    public ColorSensorV3 sensor = new ColorSensorV3(Port.kOnboard);
    public PWMVictorSPX spinnerMotor = new PWMVictorSPX(0);

    public Spinner() {
        setDefaultCommand(new AwaitSpinnerCommand(this));
    }

    public SpinnerColor getSensorColor() {
        Color sensorColor = sensor.getColor();
        if(isColorCloseToBlack(subtractColors(sensorColor, Color.kRed))) {
            return SpinnerColor.Red;
        }
        else if(isColorCloseToBlack(subtractColors(sensorColor, Color.kCyan))) {
            return SpinnerColor.Blue;
        }
        else if(isColorCloseToBlack(subtractColors(sensorColor, Color.kLime))) {
            return SpinnerColor.Green;
        }
        else if(isColorCloseToBlack(subtractColors(sensorColor, Color.kYellow))) {
            return SpinnerColor.Yellow;
        }
        else {
            return SpinnerColor.Unknown;
        }
    }

    /** Subtracts two colors linearly.  If the values of b are greater than a, the values "wrap around" - that is, the absolute value of the difference is taken. */
    private Color subtractColors(Color a, Color b) {
        return new Color(Math.abs(a.red - b.red), Math.abs(a.green - b.green), Math.abs(a.blue - b.blue));
    }

    private boolean isColorCloseToBlack(Color a) {
        return a.red < RobotMap.spinnerColorThreshold && a.green < RobotMap.spinnerColorThreshold && a.blue < RobotMap.spinnerColorThreshold;
    }

    /** Runs the spinner motor at a specified speed. */
    public void runSpinnerMotor(double speed) {
        spinnerMotor.set(speed);
    }
}