// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Packages
package frc.robot.commands

import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.ConfigReader
import frc.robot.RobotContainer

class DriveArcade : CommandBase() {
    init {
        addRequirements(RobotContainer.drivetrain)
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        RobotContainer.drivetrain.setZeroEncoders()
    }

    private val config = ConfigReader("Operator.drive.")

    private val moveSpeedLimiter = SlewRateLimiter(0.2)

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        val moveSpeed = RobotContainer.driverController.getRawAxis(
            config.getNestedInt("move_axis")
        )

        val rotateSpeed = -RobotContainer.driverController.getRawAxis(
            config.getNestedInt("rotate_axis")
        )
        val damping = 1 - RobotContainer.driverController.getRawAxis(
            config.getNestedInt("damping_axis")
        )
        RobotContainer.drivetrain.arcadeDrive(moveSpeedLimiter.calculate(moveSpeed) * damping, rotateSpeed * damping)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        RobotContainer.drivetrain.arcadeDrive(0.0, 0.0)
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }
}