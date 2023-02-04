// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.movements;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants.AutoConstants;

public class TurnToAngleEncoders extends CommandBase {

	private final Drivetrain drivetrain;
	private final double degrees;
	private final double speed;

	public TurnToAngleEncoders(Drivetrain drivetrain, double degrees, double speed) {
		this.speed = speed;
		this.degrees = degrees * 0.9; // adjust for overshoot
		this.drivetrain = drivetrain;
		addRequirements(drivetrain);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		drivetrain.setZeroEncoders();
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		drivetrain.arcadeDrive(0, speed);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		drivetrain.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return drivetrain.getAverageEncoder() >= degrees * AutoConstants.TICKS_PER_DEGREE;
	}
}
