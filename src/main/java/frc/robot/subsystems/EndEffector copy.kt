package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getInt
import kotlin.math.*

class EndEffectorCopy : SubsystemBase() {
    private val armRotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val clawMotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.claw_move"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val secStageArm: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.second_Stage_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)

    @Deprecated("not needed")
    fun grab(targetX: Double, targetY: Double, armLenA: Double, armLenB: Double): Pair<Double, Double> {
        val a = acos(
            (armLenA.pow(2) + armLenB.pow(2) - targetX.pow(2) - targetY.pow(2))
                    / (2.0 * armLenA * armLenB)
        )
        val q1 = PI - a
        val q2 = atan2(targetY, targetX) - atan2(armLenB * sin(a), armLenB * -cos(a) + armLenA)
        return Pair(q2, q1)
    }

    @Suppress("SpellCheckingInspection")
    fun liftyBoiDoubleTime(axisInput: Double, consumption: Boolean, barfing: Boolean) {
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