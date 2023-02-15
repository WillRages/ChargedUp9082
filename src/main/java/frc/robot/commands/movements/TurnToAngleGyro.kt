// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.movements

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants.getDouble
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.GyroSubsystem

class TurnToAngleGyro(
    /** Creates a new AngleGyroTurn.  */
    private val drivetrain: Drivetrain, private val gyro: GyroSubsystem, angle: Double, speed: Double
) : CommandBase() {
    private val speed: Double
    private val targetHead: Double

    // TurnToAngleGyro x = new TurnToAngleGyro();
    // x.initialize();
    init {
        addRequirements(drivetrain, gyro)
        this.speed = if (angle < 0) speed else -speed
        targetHead = angle
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        gyro.zeroHeading()
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        drivetrain.arcadeDrive(0.0, speed)
        SmartDashboard.putNumber("Heading", gyro.heading)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        drivetrain.arcadeDrive(0.0, 0.0)
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        // check if we are within <epsilon> degrees of the target
        return Math.abs(gyro.heading - targetHead) < getDouble("Robot.gyro.turn_epsilon")
    }
}