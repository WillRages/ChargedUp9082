package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getDouble
import frc.robot.Constants.getInt
import kotlin.math.absoluteValue

class EndEffector : SubsystemBase() {
    private val armRotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val clawMotor: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.claw_motor"), CANSparkMaxLowLevel.MotorType.kBrushed)

    init {
        armRotor.idleMode = CANSparkMax.IdleMode.kBrake
        clawMotor.idleMode = CANSparkMax.IdleMode.kBrake
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
        } else {
            armRotor.set(0.0)
        }

        // Boolean detection to control motor direction (Movement is same for Cone
        // consumption and Cube Spewing)

        if (consumption && !barfing) {
            clawMotor.set(getDouble("Robot.arm.arm_limit.lower"))
        } else if (barfing && !consumption) {
            clawMotor.set(-getDouble("Robot.arm.arm_limit.upper"))
        } else {
            clawMotor.set(0.0)
        }
    }
}