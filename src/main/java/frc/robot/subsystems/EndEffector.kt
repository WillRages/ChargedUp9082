package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.ConfigReader
import frc.robot.RobotContainer
import kotlin.math.abs
import kotlin.math.absoluteValue

class EndEffector : SubsystemBase() {
    private val config = ConfigReader("Robot.motors.")
    private val armRotor: CANSparkMax =
        CANSparkMax(config.getInt("rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val clawMotor: CANSparkMax =
        CANSparkMax(config.getInt("claw_motor"), CANSparkMaxLowLevel.MotorType.kBrushed)

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

        // -7 minimum, 116 max


        // CASES


        // Arm motor Input Detection
        // negative axisInput retracts arm, positive extends it
        config.currentConfigPath = ""
        if (((axisInput.absoluteValue > config.getDouble("Operator.lift.stick_deadzone"))
                    and (((armRotor.encoder.position > -7) or (axisInput < 0.0))
                    and ((armRotor.encoder.position < 116) or (axisInput > 0.0)))
                    or RobotContainer.armController.getRawButton(1))
        ) {
            armRotor.set(axisInput)
        } else {
            armRotor.set(0.0)
        }

        if (RobotContainer.armController.getRawButton(1)) {
            armRotor.encoder.position = 0.0
        }

        // Boolean detection to control motor direction (Movement is same for Cone
        // consumption and Cube Spewing)

        if (consumption && !barfing) {
            clawMotor.set(-0.7)
        } else if (barfing && !consumption) {
            clawMotor.set(0.7)
        } else {
            clawMotor.set(0.0)
        }
    }

    fun targetArmEncoder(target: Double, speed: Double): Boolean {
        val encoder = armRotor.encoder.position

        if (abs(encoder - target) < 10) return true

        if (encoder < target) {
            armRotor.set(speed.absoluteValue)
        } else {
            armRotor.set(-speed.absoluteValue)
        }

        return false
    }

    fun setArmRotor(power: Double) {
        armRotor.set(power)
    }

    override fun periodic() {
        SmartDashboard.putNumber("Arm Encoder", armRotor.encoder.position)
        SmartDashboard.putNumber("Claw Speed", clawMotor.get())
    }
}