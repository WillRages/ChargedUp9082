// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class GyroSubsystem extends SubsystemBase {
	// Gyro Sensor
	public final Gyro gyro = new ADXRS450_Gyro();

	/** Creates a new Gyro. */
	public GyroSubsystem() {}

	public void zeroHeading() {
		gyro.reset();
	}

	/**
	 * Returns the heading of the robot.
	 *
	 * @return the robot's heading in degrees, from -180 to 180
	 */
	public double getHeading() {
		return Math.IEEEremainder(gyro.getAngle(), 360)
				* (Constants.getDouble("Robot.gyro.multiplier"));
	}

	/**
	 * Returns the turn rate of the robot.
	 *
	 * @return The turn rate of the robot, in degrees per second
	 */
	public double getTurnRate() {
		return gyro.getRate() * (Constants.getDouble("Robot.gyro.multiplier"));
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}
