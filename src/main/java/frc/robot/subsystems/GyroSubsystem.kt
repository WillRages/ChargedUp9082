// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems

import edu.wpi.first.wpilibj.ADXRS450_Gyro
import edu.wpi.first.wpilibj.interfaces.Gyro
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getDouble

class GyroSubsystem
/** Creates a new Gyro.  */
    : SubsystemBase() {
    // Gyro Sensor
    val gyro: Gyro = ADXRS450_Gyro()
    fun zeroHeading() {
        gyro.reset()
    }

    val heading: Double
        /**
         * Returns the heading of the robot.
         *
         * @return the robot's heading in degrees, from -180 to 180
         */
        get() = (Math.IEEEremainder(gyro.angle, 360.0)
                * getDouble("Robot.gyro.multiplier"))
    val turnRate: Double
        /**
         * Returns the turn rate of the robot.
         *
         * @return The turn rate of the robot, in degrees per second
         */
        get() = gyro.rate * getDouble("Robot.gyro.multiplier")

    override fun periodic() {
        // This method will be called once per scheduler run
    }
}