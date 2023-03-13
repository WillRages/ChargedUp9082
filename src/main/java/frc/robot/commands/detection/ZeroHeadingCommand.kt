package frc.robot.commands.detection

import frc.robot.RobotContainer
import frc.robot.commands.autos.RunCodeCommand

val ZeroHeadingCommand = RunCodeCommand {
    RobotContainer.gyroSub.zeroYaw()
    RobotContainer.gyroSub.zeroPitch()
    RobotContainer.gyroSub.zeroRoll()
}
