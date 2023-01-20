// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Drivetrain extends SubsystemBase {

	// Declare Variables

	// Declare Motors
	public CANSparkMax m_motor_left_front;
	public CANSparkMax m_motor_left_back;
	public CANSparkMax m_motor_right_front;
	public CANSparkMax m_motor_right_back;

	// Declare Encoders
	public RelativeEncoder m_encoder_left_1;
	public RelativeEncoder m_encoder_left_2;
	public RelativeEncoder m_encoder_right_1;
	public RelativeEncoder m_encoder_right_2;

	// Speed Controls
	MotorControllerGroup leftMotors = null;
	MotorControllerGroup rightMotors = null;

	// Differential Drive
	DifferentialDrive differentialDrive = null;

	// Gyro Sensor
	private final Gyro m_gyro = new ADXRS450_Gyro();

	/** Creates a new Drivetrain. */
	public Drivetrain() {
		// CANSparkMax Controllers
		m_motor_left_front = new CANSparkMax(Constants.DRIVETRAIN_LEFT_FRONT_CANSPARKMAX, MotorType.kBrushless);
		m_motor_left_back = new CANSparkMax(Constants.DRIVETRAIN_LEFT_BACK_CANSPARKMAX, MotorType.kBrushless);
		m_motor_right_front = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_FRONT_CANSPARKMAX, MotorType.kBrushless);
		m_motor_right_back = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_BACK_CANSPARKMAX, MotorType.kBrushless);

		leftMotors = new MotorControllerGroup(m_motor_left_front, m_motor_left_back);
		rightMotors = new MotorControllerGroup(m_motor_right_front, m_motor_right_back);

		m_encoder_left_1 = m_motor_left_back.getEncoder();
		m_encoder_left_2 = m_motor_left_front.getEncoder();
		m_encoder_right_1 = m_motor_right_back.getEncoder();
		m_encoder_right_2 = m_motor_right_front.getEncoder();

		// One of the sides needs to be inverted so positive voltage means forwards,
		// rather than turning
		rightMotors.setInverted(true);

		differentialDrive = new DifferentialDrive(leftMotors, rightMotors);

	}

	public void arcadeDrive(double moveSpeed, double rotateSpeed) {
		differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
	}

	public void tankDrive(double moveSpeed, double rotateSpeed) {
		differentialDrive.tankDrive(moveSpeed, rotateSpeed);
	}

	/**
	 * Sets the max output of the drive. Useful for scaling the drive to drive more
	 * slowly.
	 *
	 * @param maxOutput the maximum output to which the drive will be constrained
	 */
	public void setMaxOutput(double maxOutput) {
		differentialDrive.setMaxOutput(maxOutput);
	}

	public void zeroHeading() {
		m_gyro.reset();
	}

	/**
	 * Returns the heading of the robot.
	 *
	 * @return the robot's heading in degrees, from 180 to 180
	 */
	public double getHeading() {
		return Math.IEEEremainder(m_gyro.getAngle(), 360) * (Constants.DriveConstants.kGyroReversed ? -1.0 : 1.0);
	}

	/**
	 * Returns the turn rate of the robot.
	 *
	 * @return The turn rate of the robot, in degrees per second
	 */
	public double getTurnRate() {
		return m_gyro.getRate() * (Constants.DriveConstants.kGyroReversed ? -1.0 : 1.0);
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}
