// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Packages
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

// Functioning Code Starts Here
public class DriveArcade extends CommandBase {
	/** Creates a new DriveArcade. */
	public DriveArcade() {
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(RobotContainer.drivetrain);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		RobotContainer.gyro_sub.zeroHeading();
		RobotContainer.drivetrain.encoder_left_1.setPosition(0);
		RobotContainer.drivetrain.encoder_left_2.setPosition(0);
		RobotContainer.drivetrain.encoder_right_1.setPosition(0);
		RobotContainer.drivetrain.encoder_right_2.setPosition(0);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		var moveSpeed =
				RobotContainer.driverController.getRawAxis(Constants.DRIVER_CONTROLLER_MOVE_AXIS);
		var rotateSpeed = -RobotContainer.driverController
				.getRawAxis(Constants.DRIVER_CONTROLLER_ROTATE_AXIS);
		var damping =
				1 - ((RobotContainer.driverController.getRawAxis(Constants.DRIVER_DAMPING_AXIS) + 1)
						/ 2);
		RobotContainer.drivetrain.arcadeDrive(moveSpeed * damping, rotateSpeed * damping);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		RobotContainer.drivetrain.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
