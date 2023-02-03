// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
	public static class OperatorConstants {
		// Constants for driver controller port (likely unnecessary)
		public static final int DRIVER_CONTROLLER_PORT = 0;
	}

	public static class DriveConstants {
		public static final int TICKS_PER_ROTATION = 1024;
		public static final double WHEEL_DIAMETER_INCH = 6;
		public static final double INCHES_PER_TICK =
				// Assumes the encoders are directly mounted on the wheel shafts
				(WHEEL_DIAMETER_INCH * Math.PI) / (double) TICKS_PER_ROTATION;

		public static final boolean GYRO_REVERSED = false;

		public static final double STABILIZATION_P = 1;
		public static final double STABILIZATION_I = 0.5;
		public static final double STABILIZATION_D = 0;

		public static final double TURN_P = 1;
		public static final double TURN_I = 0;
		public static final double TURN_D = 0;

		public static final double MAX_TURN_RATE_DPS = 10;
		public static final double MAX_TURN_ACCEL_DPS2 = 30;

		public static final double TURN_TOLERANCE_DEG = 10;
		public static final double TURN_RATE_TOLERANCE_DPS = 10; // degrees per second
	}

	public static class AutoConstants {
		// These are constants for autonomous
		public static final double AUTO_DRIVE_DISTANCE_INCH = 200;
		// 72 inch per 43 ticks
		public static final double INCH_TO_ENCODER = 43 / 72;
		public static final double AUTO_DRIVE_SPEED = .3;
		public static final double TICKS_PER_DEGREE = 0.15555555555555555555555555555555555555555555555555555555555;
		public static final int GYRO_TURN_EPSILON = 10;
	}

	// Motor Controllers for Drive Train
	public static final int DRIVETRAIN_LEFT_FRONT_CANSPARKMAX = 3;
	public static final int DRIVETRAIN_LEFT_BACK_CANSPARKMAX = 4;
	public static final int DRIVETRAIN_RIGHT_FRONT_CANSPARKMAX = 1;
	public static final int DRIVETRAIN_RIGHT_BACK_CANSPARKMAX = 2;

	// Joysticks
	public static final int DRIVER_CONTROLLER = 0;

	public static final int STICK_X_INDEX = 0;
	public static final int STICK_Y_INDEX = 1;
	public static final int STICK_TWIST_INDEX = 2;
	public static final int STICK_SLIDE_INDEX = 3;

	public static final int DRIVER_CONTROLLER_MOVE_AXIS = STICK_Y_INDEX;
	public static final int DRIVER_CONTROLLER_ROTATE_AXIS = STICK_TWIST_INDEX;
	public static final int DRIVER_DAMPING_AXIS = STICK_SLIDE_INDEX;

	// divide voltage by 9.77mV to get cm
	// https://www.maxbotix.com/firstrobotics
	// 0.0248158
	public static final double VOLTAGE_TO_INCH = 40.29691;

	public static final int DISTANCE_PORT = 0;

}
