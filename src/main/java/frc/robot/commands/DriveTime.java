// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class DriveTime extends CommandBase {
    /* Creates a new DriveTime. */

    private final Drivetrain m_drive;
    private final double m_time;
    private final double m_speed;
    private final Timer autoTimer;

    public DriveTime(double seconds, double speed, Drivetrain drive) {
        m_time = seconds;
        m_speed = speed;
        m_drive = drive;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_drive);
        autoTimer = new Timer();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        autoTimer.reset();
        autoTimer.start();
        // Reset Motor Controller Encoders
        m_drive.m_motor_left_back.restoreFactoryDefaults();
        m_drive.m_motor_left_front.restoreFactoryDefaults();
        m_drive.m_motor_right_back.restoreFactoryDefaults();
        m_drive.m_motor_right_front.restoreFactoryDefaults();

        // Initialize ArcadeDrive
        m_drive.arcadeDrive(m_speed, 0);

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_drive.arcadeDrive(m_speed, 0);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return autoTimer.get() >= m_time;
    }
}
