// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.RobotContainer
import frc.robot.commands.autos.AutoBalanceCommand
import frc.robot.commands.detection.ZeroHeadingCommand
import frc.robot.commands.movements.DriveWithEncoders

val driveBalanceAuto = SequentialCommandGroup(
    DriveWithEncoders(64.0 /*4'10"*/, 0.5, RobotContainer.drivetrain),
    ZeroHeadingCommand(RobotContainer.gyroSub),
    AutoBalanceCommand(RobotContainer.drivetrain, RobotContainer.gyroSub)
)

val driveForwardAuto = SequentialCommandGroup(
    DriveWithEncoders(10.0, -0.5, RobotContainer.drivetrain),
    DriveWithEncoders(132.81, 0.5, RobotContainer.drivetrain),
    DriveWithEncoders(36.06, -0.5, RobotContainer.drivetrain)
)

val dropCubeAuto = SequentialCommandGroup(

)
