package frc.robot.commands.movements

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.NavigationSubsystem

class DriveUntilGyroCommand(
    private val drivetrain: Drivetrain,
    private val gyroSubsystem: NavigationSubsystem,
    private val speed: Double,
    private val isStableGyro: Function1<Float, Boolean>
) : CommandBase() {

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(drivetrain, gyroSubsystem)
    }

    override fun initialize() {}

    override fun execute() {
        drivetrain.arcadeDrive(speed, 0.0)
    }

    override fun isFinished(): Boolean {
        return isStableGyro(gyroSubsystem.yaw)
    }

    override fun end(interrupted: Boolean) {
        drivetrain.arcadeDrive(0.0, 0.0)
    }
}
