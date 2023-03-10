// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.autos

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.commands.movements.DriveWithEncoders
import frc.robot.commands.movements.TurnToAngleGyro
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.GyroSubsystem

class RobotLoop(
    drivetrain: Drivetrain?, gyro: GyroSubsystem?, distance: Double, speed: Double,
    loops: Int
) : CommandBase() {
    // private final Drivetrain drivetrain;
    private val turnCommand: TurnToAngleGyro
    private val driveCommand: DriveWithEncoders
    private var loops: Int

    // is_turn is false when we are driving, testing with off
    // private boolean is_turn = false;
    init {
        addRequirements(drivetrain)
        this.loops = loops
        //		this.turn_command = null;
//		this.drive_command = null;
        turnCommand = TurnToAngleGyro(drivetrain!!, gyro!!, 90.0, speed)
        driveCommand = DriveWithEncoders(distance, speed, drivetrain)
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        driveCommand.schedule()
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        if (driveCommand.isFinished) {
            loops -= 1
            turnCommand.schedule()
        }
        if (turnCommand.isFinished) {
            driveCommand.schedule()
        }
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        driveCommand.cancel()
        turnCommand.cancel()
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return loops == 0
    }
}