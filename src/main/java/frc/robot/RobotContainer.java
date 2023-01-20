// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveArcade;
import frc.robot.commands.DriveBackwardsEncoders;
import frc.robot.commands.DriveForwardEncoders;
import frc.robot.commands.DriveTime;
import frc.robot.commands.TurnToAngleProfiled;
import frc.robot.subsystems.Drivetrain;

/*
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	public static final Drivetrain m_drivetrain = new Drivetrain();
	public static Joystick driverController = new Joystick(Constants.DRIVER_CONTROLLER);

	// Replace with CommandPS4Controller or CommandJoystick if needed
	// private final CommandXboxController m_driverController =
	// new CommandXboxController(OperatorConstants.kDriverControllerPort);

	// The Autonomous Routines

	// Drive Forward Encoders
	// A simple auto routine that drives forward for a specified amount of distance,
	// and then stops.

	private final Command m_DriveForwardEncodersAuto = new DriveForwardEncoders(60,
			Constants.AutoConstants.kAutoDriveSpeed, m_drivetrain);

	private final Command m_DriveBackwardsEncodersAuto = new DriveBackwardsEncoders(60,
			Constants.AutoConstants.kAutoDriveSpeed, m_drivetrain);

	private final Command m_DriveTimeAuto = new DriveTime(300, -.2, m_drivetrain);
	// Create the chooser for autonomous commands
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		// Configure the trigger bindings
		configureBindings();

		// Set default commands of subsystems
		m_drivetrain.setDefaultCommand(new DriveArcade());

		SmartDashboard.putNumber("Encoder Value Left 1", m_drivetrain.m_encoder_left_1.getPosition());
		SmartDashboard.putNumber("Encoder Value Left 2", m_drivetrain.m_encoder_left_2.getPosition());
		SmartDashboard.putNumber("Encoder Value Right 1", m_drivetrain.m_encoder_right_1.getPosition());
		SmartDashboard.putNumber("Encoder Value Right 2", m_drivetrain.m_encoder_right_2.getPosition());

		// Autonomous Chooser
		m_chooser.setDefaultOption("Drive Forward Encoders", m_DriveForwardEncodersAuto);
		m_chooser.addOption("Drive Backwards Encoders", m_DriveBackwardsEncodersAuto);
		m_chooser.addOption("Drive for Time", m_DriveTimeAuto);

		Shuffleboard.getTab("Autonomous").add(m_chooser);

	}

	/**
	 * Use this method to define your trigger->command mappings. Triggers can be
	 * created via the
	 * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
	 * an arbitrary
	 * predicate, or via the named factories in {@link
	 * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
	 * {@link
	 * CommandXboxController
	 * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
	 * PS4} controllers or
	 * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
	 * joysticks}.
	 */
	private void configureBindings() {
		// Schedule `ExampleCommand` when `exampleCondition` changes to `true`
		// new Trigger(m_exampleSubsystem::exampleCondition)
		// .onTrue(new ExampleCommand(m_exampleSubsystem));

		// Schedule `exampleMethodCommand` when the Xbox controller's B button is
		// pressed,
		// cancelling on release.
		// m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
		// Drive at half speed when the right bumper is held
		new JoystickButton(driverController, 3)
				.onTrue(new InstantCommand(() -> m_drivetrain.setMaxOutput(0.5)))
				.onFalse(new InstantCommand(() -> m_drivetrain.setMaxOutput(1)));

		// Stabilize robot to drive straight with gyro when left bumper is held
		new JoystickButton(driverController, 4)
				.whileTrue(
						new PIDCommand(
								new PIDController(
										Constants.DriveConstants.kStabilizationP,
										Constants.DriveConstants.kStabilizationI,
										Constants.DriveConstants.kStabilizationD),
								// Close the loop on the turn rate
								m_drivetrain::getTurnRate,
								// Setpoint is 0
								0,
								// Pipe the output to the turning controls
								output -> m_drivetrain.arcadeDrive(
										-driverController.getY(), output),
								// Require the robot drive
								m_drivetrain));

		// Turn to 90 degrees when the 'X' button is pressed, with a 5 second timeout
		new JoystickButton(driverController, 5)
				.onTrue(new TurnToAngleProfiled(90, m_drivetrain).withTimeout(5));

		// Turn to -90 degrees with a profile when the Circle button is pressed, with a
		// 5 second timeout
		new JoystickButton(driverController, 6)
				.onTrue(new TurnToAngleProfiled(-90, m_drivetrain).withTimeout(5));

	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		// An example command will be run in autonomous
		return m_chooser.getSelected();
	}
}
