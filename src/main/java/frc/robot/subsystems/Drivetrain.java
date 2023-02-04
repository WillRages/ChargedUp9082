// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

	// Declare Variables

	// Declare Motors
	public CANSparkMax motor_left_front;
	public CANSparkMax motor_left_back;
	public CANSparkMax motor_right_front;
	public CANSparkMax motor_right_back;

	// Declare Encoders
	public RelativeEncoder encoder_left_1;
	public RelativeEncoder encoder_left_2;
	public RelativeEncoder encoder_right_1;
	public RelativeEncoder encoder_right_2;

	// Speed Controls
	MotorControllerGroup leftMotors;
	MotorControllerGroup rightMotors;

	// Differential Drive
	DifferentialDrive differentialDrive;

	/** Creates a new Drivetrain. */
	public Drivetrain() {
		// CANSparkMax Controllers
		motor_left_front =
				new CANSparkMax(Constants.DRIVETRAIN_LEFT_FRONT_CANSPARKMAX, MotorType.kBrushless);
		motor_left_back =
				new CANSparkMax(Constants.DRIVETRAIN_LEFT_BACK_CANSPARKMAX, MotorType.kBrushless);
		motor_right_front =
				new CANSparkMax(Constants.DRIVETRAIN_RIGHT_FRONT_CANSPARKMAX, MotorType.kBrushless);
		motor_right_back =
				new CANSparkMax(Constants.DRIVETRAIN_RIGHT_BACK_CANSPARKMAX, MotorType.kBrushless);

		leftMotors = new MotorControllerGroup(motor_left_front, motor_left_back);
		rightMotors = new MotorControllerGroup(motor_right_front, motor_right_back);

		encoder_left_1 = motor_left_back.getEncoder();
		encoder_left_2 = motor_left_front.getEncoder();
		encoder_right_1 = motor_right_back.getEncoder();
		encoder_right_2 = motor_right_front.getEncoder();

		// Need to invert one side
		rightMotors.setInverted(true);

		differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
	}

	public double getAverageEncoder() {
		return (Math.abs(encoder_left_1.getPosition()) + Math.abs(encoder_left_2.getPosition())
				+ Math.abs(encoder_right_1.getPosition()) + Math.abs(encoder_right_2.getPosition()))
				/ 4;
	}

	public void setZeroEncoders() {
		encoder_left_1.setPosition(0.0);
		encoder_left_2.setPosition(0.0);
		encoder_right_1.setPosition(0.0);
		encoder_right_2.setPosition(0.0);
	}

	public void arcadeDrive(double moveSpeed, double rotateSpeed) {
		differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
	}

	public void tankDrive(double moveSpeed, double rotateSpeed) {
		differentialDrive.tankDrive(moveSpeed, rotateSpeed);
	}

	/**
	 * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
	 *
	 * @param maxOutput the maximum output to which the drive will be constrained
	 */
	public void setMaxOutput(double maxOutput) {
		differentialDrive.setMaxOutput(maxOutput);
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}
