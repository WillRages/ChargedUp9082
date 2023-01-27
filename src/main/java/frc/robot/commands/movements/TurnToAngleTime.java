// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.movements;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TurnToAngleTime extends CommandBase {
	/** Creates a new TurnToAngleTime. */
	private Drivetrain drivetrain;
	private final Timer autoTimer;
	private final double time;
	private final double speed;

	public TurnToAngleTime(Drivetrain drivetrain, double time, double speed) {
		this.time = time;
		this.speed = speed;
		this.drivetrain = drivetrain;
		this.autoTimer = new Timer();
		addRequirements(drivetrain);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		autoTimer.reset();
		autoTimer.start();

		drivetrain.arcadeDrive(0, speed);
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
		SmartDashboard.putNumber("Auto Running", ((double) ((int) (autoTimer.get() * 10))) / 10);
		return autoTimer.get() >= time;
	}
}
