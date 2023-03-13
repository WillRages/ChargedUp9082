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

    private val armRotor = CANSparkMax(config.getInt("rotation_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val clawMotor = CANSparkMax(config.getInt("claw_motor"), CANSparkMaxLowLevel.MotorType.kBrushed)

    init {
        armRotor.idleMode = CANSparkMax.IdleMode.kBrake
        clawMotor.idleMode = CANSparkMax.IdleMode.kBrake
    }

    @Suppress("SpellCheckingInspection")
    fun liftyBoi(axisInput: Double, consumption: Boolean, barfing: Boolean) {/* TODO:
         * Replace the variable Axis_Input with Controller x Axis
         * Consumption is the Controller's trigger
         * Barfing is the Controller's Side panel button
         * Yo mama
         * Hi future Will :) - Jaren
         */

        val trigger = RobotContainer.armController.getRawButton(1)
        config.currentConfigPath = ""

        // negative axisInput retracts arm, positive extends it
        if (axisInput.absoluteValue > config.getDouble("Operator.lift.stick_deadzone") && (//@formatter:off
                    ((armRotor.encoder.position > -7)  || (axisInput < 0.0)) &&
                    ((armRotor.encoder.position < 116) || (axisInput > 0.0))
                    || trigger)
        ) {//@formatter:on
            armRotor.set(axisInput)
        } else {
            armRotor.set(0.0)
        }

        if (trigger) {
            armRotor.encoder.position = 0.0
        }

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

        armRotor.set(speed * if (encoder < target) 1 else -1)

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