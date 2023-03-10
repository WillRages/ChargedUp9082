// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.movements

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.ConfigReader
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.GyroSubsystem
import kotlin.math.IEEErem
import kotlin.math.abs

class TurnToAngleGyro(
    /** Creates a new AngleGyroTurn.  */
    private val drivetrain: Drivetrain, private val gyro: GyroSubsystem, angle: Double, speed: Double
) : CommandBase() {
    private val speed: Double
    private val targetHead: Double
    private var calibrate = true

    // TurnToAngleGyro x = new TurnToAngleGyro();
    // x.initialize();
    init {
        addRequirements(drivetrain, gyro)
        this.speed = speed
        targetHead = angle
    }

    // Called when the command is initially scheduled.
    override fun initialize() {
        gyro.targetAngle += targetHead
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        drivetrain.arcadeDrive(0.0, if (gyro.targetAngle < 0) speed else -speed)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        calibrate = true
        drivetrain.arcadeDrive(0.0, 0.0)
    }

    private val config = ConfigReader("Robot.gyro.")

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
//        if (calibrate) return false
        // check if we are within <epsilon> degrees of the target
        return abs(gyro.headingZ - gyro.targetAngle).IEEErem(360.0) < config.getDouble("Robot.gyro.turn_epsilon")
    }
}