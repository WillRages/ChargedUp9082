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

    /*
    def grab(x: float, y: float, A: float, B: float) -> tuple:
    try:
        α = acos(((A*A + B*B - x*x - y*y) / (2*A*B)))
    except ValueError:
        ta2 = atan2(y, x)
        if (A+B)/2-abs(x) < 0 or (A+B)/2-abs(y) < 0:
            return ta2, 0
        else:
            if A > B:
                return ta2+pi, pi
            else:
                return ta2, pi
    q1 = pi-α
    β = atan2((B*sin(q1)), ((B*cos(q1)) + A))
    q2 = atan2(y, x) - β
    return q2, q1
    */
    fun grab(x: Double, y: Double, A: Double, B: Double): Pair<Double, Double> {
        val a = acos((A * A + B * B - x * x - y * y) / (2.0 * A * B))
        val q1 = PI - a
        val b = atan2(B * sin(q1), B * cos(q1) + A)
        val q2 = atan2(y, x) - b
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