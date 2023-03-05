// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Packages
package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants.getNestedInt
import frc.robot.RobotContainer

// Library imports
// Functioning Code Starts Here
class DriveArcade : CommandBase() {
    /** Creates a new DriveArcade.  */
    init {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.drivetrain)
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        RobotContainer.drivetrain.encoderLeft1.position = 0.0
        RobotContainer.drivetrain.encoderLeft2.position = 0.0
        RobotContainer.drivetrain.encoderRight1.position = 0.0
        RobotContainer.drivetrain.encoderRight2.position = 0.0
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        val moveSpeed = RobotContainer.driverController.getRawAxis(
            getNestedInt("Operator.drive.move_axis")
        )
        val rotateSpeed = -RobotContainer.driverController.getRawAxis(
            getNestedInt("Operator.drive.rotate_axis")
        )
        val damping = 1 - ((RobotContainer.driverController.getRawAxis(
            getNestedInt("Operator.drive.damping_axis")
        ) + 1) / 2)
        RobotContainer.drivetrain.arcadeDrive(moveSpeed * damping, rotateSpeed * damping)
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