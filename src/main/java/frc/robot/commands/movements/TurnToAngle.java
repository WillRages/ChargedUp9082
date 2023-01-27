
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.movements;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.PIDCommand;

/** A command that will turn the robot to the specified angle. */
public class TurnToAngle extends PIDCommand {
	/**
	 * Turns to robot to the specified angle.
	 *
	 * @param targetAngleDegrees The angle to turn to
	 * @param drive              The drive subsystem to use
	 */
	public TurnToAngle(double targetAngleDegrees, Drivetrain drive) {
		super(
				new PIDController(Constants.DriveConstants.TURN_P, Constants.DriveConstants.TURN_I,
						Constants.DriveConstants.TURN_D),
				// Close loop on heading
				drive::getHeading,
				// Set reference to target
				targetAngleDegrees,
				// Pipe output to turn robot
				output -> drive.arcadeDrive(0, (output/* /40 */)),
				// Require the drive
				drive);

		// Set the controller to be continuous (because it is an angle controller)
		getController().enableContinuousInput(-180, 180);
		// Set the controller tolerance - the delta tolerance ensures the robot is
		// stationary at the
		// setpoint before it is considered as having reached the reference
		getController()
				.setTolerance(Constants.DriveConstants.TURN_TOLERANCE_DEG,
						Constants.DriveConstants.TURN_RATE_TOLERANCE_DPS);
	}

	@Override
	public boolean isFinished() {
		// End when the controller is at the reference.
		return getController().atSetpoint();
	}
}