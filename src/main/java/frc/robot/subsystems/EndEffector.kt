package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getDouble
import frc.robot.Constants.getInt
import kotlin.math.*

class EndEffector : SubsystemBase() {
    private val armRotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val clawMotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.claw_move"), CANSparkMaxLowLevel.MotorType.kBrushless)

    /** all units in inches, return value in radians */
    fun grab(target_x: Double, target_y: Double, arm_a: Double, arm_b: Double): Pair<Double, Double> {
        val a = acos(
            (target_x.pow(2) + target_y.pow(2) - arm_a.pow(2) - arm_b.pow(2))
                    / (2.0 * arm_a * arm_b)
        )
        val q1 = PI - a
        val b = atan2(arm_b * sin(a), arm_b * -cos(a) + arm_a)
        val q2 = atan2(target_y, target_x) - b
        return Pair(q2, q1)
    }


    @Suppress("SpellCheckingInspection")
    fun liftyBoi(axisInput: Double, consumption: Boolean, barfing: Boolean) {
        /* TODO:
         * Replace the variable Axis_Input with Controller x Axis
         * Consumption is the Controller's trigger
         * Barfing is the Controller's Side panel button
         */
        // Arm motor Input Detection

        if (axisInput.absoluteValue > getDouble("Operator.lift.stick_deadzone")) {
            armRotor.set(axisInput)
        }

        // Boolean detection to control motor direction (Movement is same for Cone
        // consumption and Cube Spewing)
        if (consumption && !barfing) {
            clawMotor.set(1.0)
        } else if (barfing && !consumption) {
            clawMotor.set(-1.0)
        } else {
            clawMotor.set(0.0)
        }
    }
}