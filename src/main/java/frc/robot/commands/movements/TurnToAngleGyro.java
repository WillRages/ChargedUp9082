// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.movements;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro_sub;

public class TurnToAngleGyro extends CommandBase {
	/** Creates a new AngleGyroTurn. */
	private final Drivetrain drivetrain;
	private final Gyro_sub gyro;
	private double speed;
	private final double target_head;

	// TurnToAngleGyro x = new TurnToAngleGyro();
	// x.initialize();

	public TurnToAngleGyro(Drivetrain drivetrain, Gyro_sub gyro, double angle, double speed) {
		this.drivetrain = drivetrain;
		this.gyro = gyro;
		addRequirements(drivetrain, gyro);
		this.speed = angle < 0 ? speed : -speed;
		this.target_head = angle;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		gyro.zeroHeading();
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		drivetrain.arcadeDrive(0, speed);
		SmartDashboard.putNumber("Heading", gyro.getHeading());
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		drivetrain.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		// check if we are within <epsilon> degrees of the target
		return Math.abs(gyro.getHeading() - target_head) < AutoConstants.GYRO_TURN_EPSILON;
	}
}
