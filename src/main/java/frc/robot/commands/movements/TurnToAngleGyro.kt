// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.movements

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.ConfigReader
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.NavigationSubsystem
import kotlin.math.IEEErem
import kotlin.math.abs

class TurnToAngleGyro(
    /** Creates a new AngleGyroTurn.  */
    private val drivetrain: Drivetrain, private val gyro: NavigationSubsystem,
    private val targetHead: Double,
    private val speed: Double
) : CommandBase() {
    private val config = ConfigReader("Robot.gyro.")

    init {
        addRequirements(drivetrain, gyro)
    }

    override fun initialize() {
        gyro.targetYaw += targetHead
    }

    override fun execute() {
        drivetrain.arcadeDrive(0.0, if (gyro.targetYaw < 0) speed else -speed)
    }

    override fun end(interrupted: Boolean) {
        drivetrain.arcadeDrive(0.0, 0.0)
    }


    override fun isFinished(): Boolean {
        // check if we are within <epsilon> degrees of the target
        return abs(gyro.yaw - gyro.targetYaw).IEEErem(360.0) < config.getDouble("Robot.gyro.turn_epsilon")
    }
}