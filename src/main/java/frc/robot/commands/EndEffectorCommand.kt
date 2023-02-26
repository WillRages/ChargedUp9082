package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants.getInt
import frc.robot.Constants.getNestedInt
import frc.robot.RobotContainer

class EndEffectorCommand : CommandBase() {
    private var currentX = 0.0
    private var currentY = 0.0

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(RobotContainer.endEffector)
    }

    override fun initialize() {}

    override fun execute() {
        currentX += RobotContainer.armController.getRawAxis(
            getNestedInt("Operator.lift.arm_x"),
        )
        currentY += RobotContainer.armController.getRawAxis(
            getNestedInt("Operator.lift.arm_y"),
        )
        RobotContainer.endEffector.moveClaw(
            RobotContainer.armController.getRawButton(getInt("Operator.lift.cone_button")),
            RobotContainer.armController.getRawButton(getInt("Operator.lift.cube_button"))
        )
        RobotContainer.endEffector.targetArm(currentX, currentY)
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end(interrupted: Boolean) {}
}
