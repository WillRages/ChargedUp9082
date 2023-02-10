// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.movements.DriveForwardEncoders;
import frc.robot.commands.movements.TurnToAngleGyro;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroSubsystem;

public class RobotLoop extends CommandBase {
	// private final Drivetrain drivetrain;
	private final TurnToAngleGyro turnCommand;
	private final DriveForwardEncoders driveCommand;
	private int loops;
	// is_turn is false when we are driving, testing with off
	// private boolean is_turn = false;

	public RobotLoop(Drivetrain drivetrain, GyroSubsystem gyro, double distance, double speed,
					 int loops) {
		addRequirements(drivetrain);
		this.loops = loops;
//		this.turn_command = null;
//		this.drive_command = null;
		this.turnCommand = new TurnToAngleGyro(drivetrain, gyro, 90, speed);
		this.driveCommand = new DriveForwardEncoders(distance, speed, drivetrain);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		driveCommand.schedule();
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		SmartDashboard.putBoolean("Drive Fin", driveCommand.isFinished());
		SmartDashboard.putBoolean("Turn Finished", turnCommand.isFinished());
		if (driveCommand.isFinished()) {
			loops -= 1;
			turnCommand.schedule();
		}

		if (turnCommand.isFinished()) {
			driveCommand.schedule();
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		driveCommand.cancel();
		turnCommand.cancel();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return loops == 0;
	}
}
