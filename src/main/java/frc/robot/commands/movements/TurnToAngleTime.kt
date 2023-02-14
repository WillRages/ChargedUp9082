// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.movements

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Drivetrain

class TurnToAngleTime(
    /** Creates a new TurnToAngleTime.  */
    private val drivetrain: Drivetrain, private val time: Double, private val speed: Double
) : CommandBase() {
    private val autoTimer: Timer = Timer()

    init {
        addRequirements(drivetrain)
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        autoTimer.reset()
        autoTimer.start()
        drivetrain.arcadeDrive(0.0, speed)
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        drivetrain.arcadeDrive(0.0, speed)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        drivetrain.arcadeDrive(0.0, 0.0)
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        SmartDashboard.putNumber("Auto Running", (autoTimer.get() * 10).toInt().toDouble() / 10)
        return autoTimer.get() >= time
    }
}