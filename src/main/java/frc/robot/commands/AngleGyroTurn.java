// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AngleGyroTurn extends CommandBase {
  /** Creates a new AngleGyroTurn. */
  private double angle;
  private Drivetrain drivetrain;
  private double speed;
  private double target_head;

  public AngleGyroTurn(Drivetrain drivetrain, double angle, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.speed = speed;
    this.angle = angle;
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    angle = target_head;
    if (target_head < 0) {
      speed = -speed;
      while (drivetrain.getHeading() > target_head) {
        drivetrain.arcadeDrive(0, speed);
      }
    }
    while (drivetrain.getHeading() < target_head) {
      drivetrain.arcadeDrive(0, speed);
    }
    drivetrain.getHeading();
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
    return false;
  }
}
