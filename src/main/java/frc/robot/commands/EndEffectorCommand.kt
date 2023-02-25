package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants.getInt
import frc.robot.Constants.getNestedInt
import frc.robot.RobotContainer

class EndEffectorCommand : CommandBase() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(RobotContainer.endEffector)
    }

    override fun initialize() {}

    override fun execute() {
        RobotContainer.endEffector.liftyBoi(
            RobotContainer.armController.getRawAxis(getNestedInt("Operator.lift.claw_axis")),
            RobotContainer.armController.getRawButton(getInt("Operator.lift.cone_button")),
            RobotContainer.armController.getRawButton(getInt("Operator.lift.cube_button"))
        )
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end(interrupted: Boolean) {}
}
