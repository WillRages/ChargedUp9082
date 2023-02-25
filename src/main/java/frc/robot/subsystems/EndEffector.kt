package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getInt

class EndEffector : SubsystemBase() {
    private val armRotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val clawMotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.claw_move"), CANSparkMaxLowLevel.MotorType.kBrushless)

    @Suppress("SpellCheckingInspection")
    fun liftyBoi(axisInput: Double, consumption: Boolean, barfing: Boolean) {
        /*
         * Replace the variable Axis_Input with Controller x Axis
         * Consumption is the Controller's trigger
         * Barfing is the Controller's Side panel button
         */
        // Arm motor Input Detection
        if (axisInput > 0.2) {
            armRotor.set(axisInput)
        } else if (axisInput < 0.2) {
            armRotor.set(axisInput)
        }

        // Boolean detection to control motor direction (Movement is same for Cone
        // consumption and Cube Spewing)
        if (consumption && !barfing) {
            clawMotor.set(1.0)
        } else if (barfing && consumption) {
            clawMotor.set(-1.0)
        }
    }
}