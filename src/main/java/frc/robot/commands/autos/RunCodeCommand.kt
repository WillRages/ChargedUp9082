package frc.robot.commands.autos

import edu.wpi.first.wpilibj2.command.CommandBase

class RunCodeCommand(val code: Function0<Unit>) : CommandBase() {

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
        code()
    }

    //MASTERFUL CODING <3333
    override fun execute() {}

    override fun isFinished(): Boolean {
        return true
    }

    override fun end(interrupted: Boolean) {}
}
