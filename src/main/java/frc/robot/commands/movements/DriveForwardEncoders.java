// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.movements;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardEncoders extends CommandBase {
	private final Drivetrain drive;
	private final double distance;
	private final double speed;

	public DriveForwardEncoders(double inches, double speed, Drivetrain drive) {
		this.drive = drive;
		addRequirements(drive);
		this.distance = inches * Constants.AutoConstants.INCH_TO_ENCODER;
		this.speed = -speed;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		drive.setZeroEncoders();
		drive.arcadeDrive(speed, 0);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		drive.arcadeDrive(speed, 0);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		drive.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return drive.getAverageEncoder() / 4 >= distance;
	}
}
