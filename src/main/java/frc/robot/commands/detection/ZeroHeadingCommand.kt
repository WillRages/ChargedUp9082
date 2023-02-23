package frc.robot.commands.detection

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.GyroSubsystem

class ZeroHeadingCommand(private val gyroSubsystem: GyroSubsystem) : CommandBase() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(gyroSubsystem)
    }

    override fun initialize() {
        gyroSubsystem.zeroNavX()
    }

    override fun execute() {}

    override fun isFinished(): Boolean {
        return true
    }

    override fun end(interrupted: Boolean) {}
}
