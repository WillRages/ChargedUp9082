package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.ConfigReader
import frc.robot.RobotContainer

class EndEffectorCommand : CommandBase() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(RobotContainer.endEffector)
    }

    override fun initialize() {}
    private val config = ConfigReader("Operator.lift.")
    fun getACButton(path: String): Boolean {
        return RobotContainer.armController.getRawButton(config.getInt(path))
    }

    override fun execute() {
        RobotContainer.endEffector.liftyBoi(
            RobotContainer.armController.getRawAxis(config.getNestedInt("claw_axis")),
            getACButton("cone.consume") xor getACButton("cube.barfing"),
            getACButton("cube.consume") xor getACButton("cone.barfing")
        )
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end(interrupted: Boolean) {}
}
