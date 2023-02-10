package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class EndEffector extends SubsystemBase {
    private final CANSparkMax armRotor;
    private final CANSparkMax clawMotor;

    public EndEffector() {
        armRotor = new CANSparkMax(Constants.END_EFFECTOR_ARM_ROTATION_CANSPARKMAX, MotorType.kBrushless);
        clawMotor = new CANSparkMax(Constants.END_EFFECTOR_CLAW_MOVE_CANSPARKMAX, MotorType.kBrushless);
    }

    public void LiftyBoi(float Axis_Input, boolean Consumption, boolean Barfing) {
        /*
         * Replace the variable Axis_Input with Controller x Axis
         * Consumption is the Controller's trigger
         * Barfing is the Controller's Side panel button
         */
        // Arm motor Input Detection
        if (Axis_Input > 0.2) {
            armRotor.set(Axis_Input);
        } else if (Axis_Input < 0.2) {
            armRotor.set(Axis_Input);
        }

        // Boolean detection to control motor direction (Movement is same for Cone
        // consumption and Cube Spewing)
        if (Consumption && !Barfing) {
            clawMotor.set(1);
        } else if (Barfing && Consumption) {
            clawMotor.set(-1);
        }

    }
}
