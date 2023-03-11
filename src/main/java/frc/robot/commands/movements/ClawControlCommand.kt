package frc.robot.commands.movements

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.EndEffector

class ClawControlCommand(
    private val endEffector: EndEffector,
    private val direction: Boolean,
    seconds: Double
) : CommandBase() {

    private var waitTicks: Int

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(endEffector)
        waitTicks = (seconds * 50).toInt()
    }

    override fun initialize() {

    }

    override fun execute() {
        endEffector.liftyBoi(0.0, direction, !direction)
        waitTicks -= 1
    }

    override fun isFinished(): Boolean {
        return waitTicks <= 0
    }

    override fun end(interrupted: Boolean) {}
}
