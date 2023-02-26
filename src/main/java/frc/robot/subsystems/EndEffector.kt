package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getDouble
import frc.robot.Constants.getInt
import kotlin.math.*

class EndEffector : SubsystemBase() {
    private val TAU = 2.0 * PI
    private val armRotorA: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val armRotorB: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val clawMotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.claw_move"), CANSparkMaxLowLevel.MotorType.kBrushless)

    /** all units in inches, return value in radians */
    fun pointToJoint(targetX: Double, targetY: Double, armA: Double, armB: Double): Pair<Double, Double> {
        val a = acos(
            (targetX.pow(2) + targetY.pow(2) - armA.pow(2) - armB.pow(2))
                    / (2.0 * armA * armB)
        )
        val q1 = PI - a
        val b = atan2(armB * sin(a), armB * -cos(a) + armA)
        val q2 = atan2(targetY, targetX) - b
        return Pair(q2, q1)
    }

    fun targetArm(targetX: Double, targetY: Double) {
        val (angleA, angleB) = pointToJoint(
            targetX,
            targetY,
            getDouble("Robot.arm.a_length"),
            getDouble("Robot.arm.b_length")
        )
        val robotDiffA = angleA - armRotorA.encoder.position / getInt("Robot.arm.ticks_per_rev") * TAU
        val robotDiffB = angleB - armRotorB.encoder.position / getInt("Robot.arm.ticks_per_rev") * TAU

        armRotorA.set(robotDiffA / 50.0)
        armRotorB.set(robotDiffB / 50.0)
    }
}