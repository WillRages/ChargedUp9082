package frc.robot.commands.movements

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.EndEffector

class ArmEncoderCommand(
    private val endEffector: EndEffector,
    private val targetEncoder: Double,
    private val speed: Double
) : CommandBase() {
    private var atEncoder = false

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(endEffector)
    }

    override fun initialize() {}

    override fun execute() {
        atEncoder = endEffector.targetArmEncoder(targetEncoder, speed)
    }

    override fun isFinished(): Boolean {
        return atEncoder
    }

    override fun end(interrupted: Boolean) {
        endEffector.setArmRotor(0.0)
    }
}
