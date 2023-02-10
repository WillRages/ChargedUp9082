package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class EndEffector extends SubsystemBase {
    public final CANSparkMax armRotor;
    public final CANSparkMax clawMotor;

    public EndEffector() {
        armRotor = new CANSparkMax(Constants.getInt("Robot.motors.rotation_arm"), MotorType.kBrushless);
        clawMotor = new CANSparkMax(Constants.getInt("Robot.motors.claw_move"), MotorType.kBrushless);
    }
}

