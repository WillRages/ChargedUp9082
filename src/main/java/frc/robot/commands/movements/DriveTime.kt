// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.movements

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Drivetrain

class DriveTime(seconds: Double, speed: Double, private val drive: Drivetrain) : CommandBase() {
    private val timer: Timer
    private val time: Double
    private val speed: Double

    init {
        addRequirements(drive)
        timer = Timer()
        time = seconds
        this.speed = speed
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        timer.reset()
        timer.start()
        drive.setZeroEncoders()
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
        return timer.get() >= time
    }
}