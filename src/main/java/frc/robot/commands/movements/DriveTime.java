// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.movements;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class DriveTime extends CommandBase {
	/* Creates a new DriveTime. */

	private final Drivetrain drive;
	private final double time;
	private final double speed;
	private final Timer autoTimer;

	public DriveTime(double seconds, double speed, Drivetrain drive) {
		this.time = seconds;
		this.speed = speed;
		this.drive = drive;
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(drive);
		autoTimer = new Timer();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		autoTimer.reset();
		autoTimer.start();
		// Reset Motor Controller Encoders
		drive.motor_left_back.restoreFactoryDefaults();
		drive.motor_left_front.restoreFactoryDefaults();
		drive.motor_right_back.restoreFactoryDefaults();
		drive.motor_right_front.restoreFactoryDefaults();

		// Initialize ArcadeDrive
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
		SmartDashboard.putNumber("Auto Running", ((double) ((int) (autoTimer.get() * 10))) / 10);
		return autoTimer.get() >= time;
	}
}
