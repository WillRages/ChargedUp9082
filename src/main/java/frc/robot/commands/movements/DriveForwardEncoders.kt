// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.movements

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants.getDouble
import frc.robot.subsystems.Drivetrain

class DriveForwardEncoders(inches: Double, speed: Double, private val drive: Drivetrain) : CommandBase() {
    private val distance: Double
    private val speed: Double

    init {
        addRequirements(drive)
        distance = inches * getDouble("Robot.wheels.inch_to_encoder")
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
        SmartDashboard.putNumber("Encoder Value Left 1", drive.encoderLeft1.position)
        SmartDashboard.putNumber("Encoder Value Left 2", drive.encoderLeft2.position)
        SmartDashboard.putNumber("Encoder Value Right 1", drive.encoderRight1.position)
        SmartDashboard.putNumber("Encoder Value Right 2", drive.encoderRight2.position)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        drive.arcadeDrive(0.0, 0.0)
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        SmartDashboard.putNumber("Average Encoder", drive.averageEncoder)
        SmartDashboard.putNumber("Distance To Move", distance)
        return (Math.abs(drive.encoderLeft1.position)
                + Math.abs(drive.encoderLeft2.position)
                + Math.abs(drive.encoderRight1.position)
                + Math.abs(drive.encoderRight2.position)) / 4 >= distance
    }
}