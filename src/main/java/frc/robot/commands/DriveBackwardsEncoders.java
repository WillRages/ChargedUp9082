// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.Constants;

public class DriveBackwardsEncoders extends CommandBase {
	/** Creates a new DriveForwardTime. */

	private final Drivetrain m_drive;
	private final double m_distance;
	private final double m_speed;

	public DriveBackwardsEncoders(double inches, double speed, Drivetrain drive) {
		m_distance = inches * Constants.AutoConstants.INCH_TO_ENCODER;
		m_speed = speed;
		m_drive = drive;
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(m_drive);

	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		// Reset Motor Controller Encoders
		m_drive.motor_left_back.restoreFactoryDefaults();
		m_drive.motor_left_front.restoreFactoryDefaults();
		m_drive.motor_right_back.restoreFactoryDefaults();
		m_drive.motor_right_front.restoreFactoryDefaults();

		// Initialize ArcadeDrive
		m_drive.arcadeDrive(-m_speed, 0);

	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		m_drive.arcadeDrive(-m_speed, 0);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_drive.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return Math.abs((m_drive.m_encoder_left_1.getPosition() + m_drive.m_encoder_left_2.getPosition()
				+ m_drive.m_encoder_right_1.getPosition() + m_drive.m_encoder_right_2.getPosition())
				/ 4.0) >= m_distance;
	}

}
