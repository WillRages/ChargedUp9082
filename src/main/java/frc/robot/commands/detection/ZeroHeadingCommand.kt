package frc.robot.commands.detection

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.NavigationSubsystem

class ZeroHeadingCommand(private val gyroSubsystem: NavigationSubsystem) : CommandBase() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(gyroSubsystem)
    }

    override fun initialize() {
        gyroSubsystem.zeroYaw()
        gyroSubsystem.zeroPitch()
        gyroSubsystem.zeroRoll()
    }

    override fun execute() {}

    override fun isFinished() = true

    override fun end(interrupted: Boolean) {}
}
