package frc.robot.commands.autos

import edu.wpi.first.math.MathUtil.clamp
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.RobotContainer.Companion.driverController
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.GyroSubsystem
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.math.sqrt

class AutoBalanceCommand(private val drivetrain: Drivetrain, private val gyroSubsystem: GyroSubsystem) : CommandBase() {

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(drivetrain, gyroSubsystem)

    }

    override fun initialize() {
        gyroSubsystem.zeroNavX()
        gyroSubsystem.zeroNavZ()
    }

    private fun signSqrt(double: Double): Double {
        return double.sign * sqrt(double.absoluteValue)
    }


    override fun execute() {
        drivetrain.arcadeDrive(
            clamp(gyroSubsystem.headingX / 50.0, -0.3, 0.3),
            clamp(signSqrt(gyroSubsystem.headingZ) / 7.0, -0.5, 0.5)
        )
    }

    override fun isFinished(): Boolean {
        return driverController.button(2).asBoolean
    }

    override fun end(interrupted: Boolean) {
        drivetrain.arcadeDrive(0.0, 0.0)
    }
}
