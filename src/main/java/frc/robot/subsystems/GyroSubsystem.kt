// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
@file:Suppress("MemberVisibilityCanBePrivate")

package frc.robot.subsystems

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.ADXRS450_Gyro
import edu.wpi.first.wpilibj.SerialPort
import edu.wpi.first.wpilibj.interfaces.Gyro
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getDouble
import kotlin.math.IEEErem

class GyroSubsystem : SubsystemBase() {
    var targetAngle = 0.0
    var pitchOffset = 0.0

    private val navx: AHRS = AHRS(SerialPort.Port.kMXP)
    val headingX: Double
        get() = navx.pitch.toDouble().IEEErem(360.0) * getDouble("Robot.gyro.multiplier") - pitchOffset
    val headingY: Double
        get() = navx.roll.toDouble().IEEErem(360.0) * getDouble("Robot.gyro.multiplier")
    val headingZ: Double
        get() = navx.angle.IEEErem(360.0) * getDouble("Robot.gyro.multiplier")

    fun zeroNavX() {
        pitchOffset += headingX
    }


    fun zeroNavZ() {
        navx.reset()
        targetAngle = 0.0
    }

    // Gyro Sensor
    private val gyro: Gyro = ADXRS450_Gyro()
    val heading: Double
        get() = (gyro.angle.IEEErem(360.0) * getDouble("Robot.gyro.multiplier"))
    val turnRate: Double
        get() = gyro.rate * getDouble("Robot.gyro.multiplier")

    fun zeroHeading() {
        gyro.reset()
    }

    override fun periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Heading", heading)
        SmartDashboard.putNumber("Heading X", headingX)
        SmartDashboard.putNumber("Heading Y", headingY)
        SmartDashboard.putNumber("Heading Z", headingZ)
    }
}