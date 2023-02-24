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
    private val secStageArm: CANSparkMax =
        CANSparkMax(getInt("Robot.motors.second_Stage_arm"), CANSparkMaxLowLevel.MotorType.kBrushless)


    @Suppress("SpellCheckingInspection")
    fun liftyBoiDoubleTime(axisInput: Double, secondStage: Double, consumption: Boolean, barfing: Boolean) {
        



        

        // Arm motor Input Detection
        if (axisInput > 0.2) {
            armRotor.set(axisInput.toDouble())
        } else if (axisInput < 0.2) {
            armRotor.set(axisInput.toDouble())
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