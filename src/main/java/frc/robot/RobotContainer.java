// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveArcade;
import frc.robot.commands.movements.DriveBackwardsEncoders;
import frc.robot.commands.movements.DriveForwardEncoders;
import frc.robot.commands.movements.DriveTime;
import frc.robot.commands.movements.TurnToAngleEncoders;
import frc.robot.commands.movements.TurnToAngleGyro;
import frc.robot.commands.movements.TurnToAngleTime;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.Sensors;

/*
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	public static final Drivetrain drivetrain = new Drivetrain();
	public static final GyroSubsystem gyroSub = new GyroSubsystem();
	public static final Sensors sensors = new Sensors();
	public static Joystick driverController = new Joystick(Constants.DRIVER_CONTROLLER);

	// Replace with CommandPS4Controller or CommandJoystick if needed
	// private final CommandXboxController m_driverController
	// = new CommandXboxController(OperatorConstants.kDriverControllerPort);

	final Command DriveForwardEncodersAuto =
			new DriveForwardEncoders(72, Constants.AutoConstants.AUTO_DRIVE_SPEED, drivetrain);

	final Command DriveBackwardsEncodersAuto =
			new DriveBackwardsEncoders(60, Constants.AutoConstants.AUTO_DRIVE_SPEED, drivetrain);

	final Command DriveTimeAuto = new DriveTime(10, -.2, drivetrain);

	final Command TurnToAngleTimeAuto = new TurnToAngleTime(drivetrain, .8, .5);

	final Command TurnToAngleEncodersAuto = new TurnToAngleEncoders(drivetrain, 90, .35);

	final Command TurnToAngleGyroAuto = new TurnToAngleGyro(drivetrain, gyroSub, 90, .35);
	// Create the chooser for autonomous commands
	SendableChooser<Command> autoChooser = new SendableChooser<>();

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		// Configure the trigger bindings
		configureBindings();

		// Set default commands of subsystems
		drivetrain.setDefaultCommand(new DriveArcade());

		// Autonomous Chooser
		autoChooser.setDefaultOption("Drive Forward Encoders", DriveForwardEncodersAuto);
		autoChooser.addOption("Drive Backwards Encoders", DriveBackwardsEncodersAuto);
		autoChooser.addOption("Drive for Time", DriveTimeAuto);
		autoChooser.addOption("Turn for time", TurnToAngleTimeAuto);
		autoChooser.addOption("Turn with encoders", TurnToAngleEncodersAuto);
		autoChooser.addOption("Turn with gyro", TurnToAngleGyroAuto);

		SmartDashboard.putData("Auto Chooser", autoChooser);
	}

	/**
	 * Use this method to define your trigger->command mappings. Triggers can be created via the
	 * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
	 * predicate, or via the named factories in
	 * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
	 * {@link CommandXboxController
	 * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4} controllers or
	 * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
	 */

	private void configureBindings() {}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		// An example command will be run in autonomous
		return autoChooser.getSelected();
	}
}
