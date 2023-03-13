package frc.robot.commands.autos

import edu.wpi.first.math.MathUtil.clamp
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.RobotContainer.Companion.driverController
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.NavigationSubsystem
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.math.sqrt

class AutoBalanceCommand(private val drivetrain: Drivetrain, private val navSub: NavigationSubsystem) : CommandBase() {

    private var oldPitch = navSub.pitch!!
    private var stopOvershoot = 0

    init {
        addRequirements(drivetrain, navSub)
    }

    override fun initialize() {}

    private fun Float.signSqrt() = this.sign * sqrt(this.absoluteValue)

    override fun execute() {
        if (!NavigationSubsystem.connected) return

        if (oldPitch.sign != navSub.pitch!!.sign) {
            // 20 ms loop time, 500 ms target pause
            stopOvershoot = 25
        }

        if (stopOvershoot > 0) {
            stopOvershoot -= 1
            drivetrain.arcadeDrive(0.0, 0.0)
        } else {
            drivetrain.arcadeDrive(
                clamp(navSub.pitch!! / -5.0, -0.5, 0.5),
                clamp(navSub.yaw.signSqrt() / 7.0, -0.5, 0.5)
            )
        }

        oldPitch = navSub.pitch!!
    }

    override fun isFinished() = !driverController.button(4).asBoolean or !NavigationSubsystem.connected

    override fun end(interrupted: Boolean) {
        drivetrain.arcadeDrive(0.0, 0.0)
    }
}
