// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.movements

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants.getDouble
import frc.robot.subsystems.Drivetrain

class TurnToAngleEncoders(private val drivetrain: Drivetrain, degrees: Double, private val speed: Double) :
    CommandBase() {
    private val degrees: Double

    init {
        this.degrees = degrees * 0.9 // adjust for overshoot
        addRequirements(drivetrain)
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        drivetrain.setZeroEncoders()
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
        return drivetrain.averageEncoder >= degrees * getDouble("Robot.wheels.ticks_per_degree")
    }
}