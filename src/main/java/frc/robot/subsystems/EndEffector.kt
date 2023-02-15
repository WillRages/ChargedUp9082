package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getInt

class EndEffector : SubsystemBase() {
    private val armRotor: CANSparkMax
    private val clawMotor: CANSparkMax

    init {
        armRotor = CANSparkMax(getInt("Robot.motors.rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
        clawMotor = CANSparkMax(getInt("Robot.motors.claw_move"), CANSparkMaxLowLevel.MotorType.kBrushless)
    }

    fun LiftyBoi(Axis_Input: Float, Consumption: Boolean, Barfing: Boolean) {
        /*
         * Replace the variable Axis_Input with Controller x Axis
         * Consumption is the Controller's trigger
         * Barfing is the Controller's Side panel button
         */
        // Arm motor Input Detection
        if (Axis_Input > 0.2) {
            armRotor.set(Axis_Input.toDouble())
        } else if (Axis_Input < 0.2) {
            armRotor.set(Axis_Input.toDouble())
        }

        // Boolean detection to control motor direction (Movement is same for Cone
        // consumption and Cube Spewing)
        if (Consumption && !Barfing) {
            clawMotor.set(1.0)
        } else if (Barfing && Consumption) {
            clawMotor.set(-1.0)
        }
    }
}