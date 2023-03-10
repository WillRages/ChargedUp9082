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

    private var oldHeadingY = gyroSubsystem.headingY
    private var stopOvershoot = 0

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(drivetrain, gyroSubsystem)

    }

    override fun initialize() {
    }

    private fun signSqrt(double: Double): Double {
        return double.sign * sqrt(double.absoluteValue)
    }


    override fun execute() {
        if (oldHeadingY.sign != gyroSubsystem.headingY) {
            // 20 ms loop time, 500 ms target pause
            stopOvershoot = 25
        }

        if (stopOvershoot > 0) {
            stopOvershoot -= 1
            drivetrain.arcadeDrive(0.0, 0.0)
        } else {
            drivetrain.arcadeDrive(
                clamp(gyroSubsystem.headingY / -5.0, -0.5, 0.5),
                clamp(signSqrt(gyroSubsystem.headingZ) / 7.0, -0.5, 0.5)
            )
        }

        oldHeadingY = gyroSubsystem.headingY
    }

    override fun isFinished(): Boolean {
        return !driverController.button(4).asBoolean
    }

    override fun end(interrupted: Boolean) {
        drivetrain.arcadeDrive(0.0, 0.0)
    }
}
