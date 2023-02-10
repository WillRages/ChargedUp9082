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
	public CANSparkMax motorLeftFront;
	public CANSparkMax motorLeftBack;
	public CANSparkMax motorRightFront;
	public CANSparkMax motorRightBack;

	// Declare Encoders
	public RelativeEncoder encoderLeft1;
	public RelativeEncoder encoderLeft2;
	public RelativeEncoder encoderRight1;
	public RelativeEncoder encoderRight2;

	// Speed Controls
	MotorControllerGroup leftMotors;
	MotorControllerGroup rightMotors;

	// Differential Drive
	DifferentialDrive differentialDrive;

	/** Creates a new Drivetrain. */
	public Drivetrain() {
		// CANSparkMax Controllers
		motorLeftFront =
				new CANSparkMax(Constants.DRIVETRAIN_LEFT_FRONT_CANSPARKMAX, MotorType.kBrushless);
		motorLeftBack =
				new CANSparkMax(Constants.DRIVETRAIN_LEFT_BACK_CANSPARKMAX, MotorType.kBrushless);
		motorRightFront =
				new CANSparkMax(Constants.DRIVETRAIN_RIGHT_FRONT_CANSPARKMAX, MotorType.kBrushless);
		motorRightBack =
				new CANSparkMax(Constants.DRIVETRAIN_RIGHT_BACK_CANSPARKMAX, MotorType.kBrushless);

		leftMotors = new MotorControllerGroup(motorLeftFront, motorLeftBack);
		rightMotors = new MotorControllerGroup(motorRightFront, motorRightBack);

		encoderLeft1 = motorLeftBack.getEncoder();
		encoderLeft2 = motorLeftFront.getEncoder();
		encoderRight1 = motorRightBack.getEncoder();
		encoderRight2 = motorRightFront.getEncoder();

		// Need to invert one side
		rightMotors.setInverted(true);

		differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
	}

	public double getAverageEncoder() {
		return (Math.abs(encoderLeft1.getPosition()) + Math.abs(encoderLeft2.getPosition())
				+ Math.abs(encoderRight1.getPosition()) + Math.abs(encoderRight2.getPosition()))
				/ 4;
	}

	public void setZeroEncoders() {
		encoderLeft1.setPosition(0.0);
		encoderLeft2.setPosition(0.0);
		encoderRight1.setPosition(0.0);
		encoderRight2.setPosition(0.0);
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
