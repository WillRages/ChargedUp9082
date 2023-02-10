package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class EndEffector extends SubsystemBase {
    public final CANSparkMax armRotor;
    public final CANSparkMax clawMotor;

    public EndEffector() {
        armRotor = new CANSparkMax(Constants.END_EFFECTOR_ARM_ROTATION_CANSPARKMAX, MotorType.kBrushless);
        clawMotor = new CANSparkMax(Constants.END_EFFECTOR_CLAW_MOVE_CANSPARKMAX, MotorType.kBrushless);
    }
}

