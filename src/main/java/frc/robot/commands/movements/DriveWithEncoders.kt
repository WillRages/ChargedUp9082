// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.movements

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.ConfigReader
import frc.robot.subsystems.Drivetrain
import kotlin.math.withSign

class DriveWithEncoders(inches: Double, speed: Double, private val drive: Drivetrain) : CommandBase() {
    private val distance: Double
    private val speed: Double

    private val config = ConfigReader("Robot.wheels.")

    init {
        addRequirements(drive)
        distance = (inches * config.getDouble("inch_to_encoder")).withSign(speed)
        this.speed = -speed
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        drive.setZeroEncoders()
        drive.arcadeDrive(speed, 0.0)
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        drive.arcadeDrive(speed, 0.0)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        drive.arcadeDrive(0.0, 0.0)
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return drive.averageEncoder >= distance
    }
}