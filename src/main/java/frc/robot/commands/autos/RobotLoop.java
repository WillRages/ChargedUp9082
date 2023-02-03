// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.movements.DriveForwardEncoders;
import frc.robot.commands.movements.TurnToAngleGyro;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro_sub;

@Deprecated
public class RobotLoop extends CommandBase {
	// private final Drivetrain drivetrain;
	private final TurnToAngleGyro turn_command;
	private final DriveForwardEncoders drive_command;
	private int loops;
	// is_turn is false when we are driving, testing with off
	// private boolean is_turn = false;

	public RobotLoop(Drivetrain drivetrain, Gyro_sub gyro, double distance, double speed, int loops) {
		addRequirements(drivetrain);
		this.loops = loops;
		this.turn_command = null;
		this.drive_command = null;
		// this.turn_command = new TurnToAngleGyro(drivetrain, gyro, 90, speed);
		// this.drive_command = new DriveForwardEncoders(distance, speed, drivetrain);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		drive_command.schedule();
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		SmartDashboard.putBoolean("Drive Fin", drive_command.isFinished());
		SmartDashboard.putBoolean("Turn Finished", turn_command.isFinished());
		if (drive_command.isFinished()) {
			loops -= 1;
			turn_command.schedule();
		}

		if (turn_command.isFinished()) {
			drive_command.schedule();
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		drive_command.cancel();
		turn_command.cancel();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return loops == 0;
	}
}
