// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.movements.DriveForwardEncoders;
import frc.robot.commands.movements.TurnToAngleGyro;
import frc.robot.subsystems.Drivetrain;

public class RobotLoop extends CommandBase {
	private final Drivetrain drivetrain;
	private final double distance, speed;
	private final int loops;
	private final TurnToAngleGyro turn_command;
	private final DriveForwardEncoders drive_command;

	public RobotLoop(Drivetrain drivetrain, double distance, double speed, int loops, TurnToAngleGyro turn_command,
			DriveForwardEncoders drive_command) {
		this.drivetrain = drivetrain;
		addRequirements(drivetrain);
		this.distance = distance;
		this.speed = speed;
		this.loops = loops;
		this.turn_command = turn_command;
		this.drive_command = drive_command;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
