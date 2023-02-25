// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.commands.DriveArcade
import frc.robot.commands.EndEffectorCommand
import frc.robot.commands.movements.*
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.EndEffector
import frc.robot.subsystems.GyroSubsystem
import frc.robot.subsystems.Sensors

/*
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
class RobotContainer {
    // Replace with CommandPS4Controller or CommandJoystick if needed
    // private final CommandXboxController m_driverController
    // = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    private val driveForwardEncodersAuto: Command =
        DriveForwardEncoders(72.0, Constants.getDouble("Auto.drive_speed"), drivetrain)
    private val driveBackwardsEncodersAuto: Command =
        DriveBackwardsEncoders(60.0, Constants.getDouble("Auto.drive_speed"), drivetrain)
    private val driveTimeAuto: Command = DriveTime(10.0, -.2, drivetrain)
    private val turnToAngleTimeAuto: Command = TurnToAngleTime(drivetrain, .8, .5)
    private val turnToAngleEncodersAuto: Command = TurnToAngleEncoders(drivetrain, 90.0, .35)
    private val turnToAngleGyroAuto: Command = TurnToAngleGyro(drivetrain, gyroSub, 90.0, .35)

    // Create the chooser for autonomous commands
    private val autoChooser = SendableChooser<Command>()

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    init {
        // Configure the trigger bindings
        configureBindings()

        // Set default commands of subsystems
        drivetrain.defaultCommand = DriveArcade().alongWith(EndEffectorCommand())

        // Autonomous Chooser
        autoChooser.setDefaultOption("Drive Forward Encoders", driveForwardEncodersAuto)
        autoChooser.addOption("Drive Backwards Encoders", driveBackwardsEncodersAuto)
        autoChooser.addOption("Drive for Time", driveTimeAuto)
        autoChooser.addOption("Turn for time", turnToAngleTimeAuto)
        autoChooser.addOption("Turn with encoders", turnToAngleEncodersAuto)
        autoChooser.addOption("Turn with gyro", turnToAngleGyroAuto)
        SmartDashboard.putData("Auto Chooser", autoChooser)
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     *  constructor with an arbitrary
     * predicate, or via the named factories in
     * [edu.wpi.first.wpilibj2.command.button.CommandGenericHID]'s subclasses for
     * [ Xbox]/[PS4][edu.wpi.first.wpilibj2.command.button.CommandPS4Controller] controllers or
     * [Flight joysticks][edu.wpi.first.wpilibj2.command.button.CommandJoystick].
     */
    private fun configureBindings() {}
    val autonomousCommand: Command
        get() = autoChooser.selected

    companion object {
        // The robot's subsystems and commands are defined here...
        val drivetrain = Drivetrain()
        val gyroSub = GyroSubsystem()
        val sensors = Sensors()
        val endEffector = EndEffector()
        val driverController = Joystick(Constants.getInt("Operator.drive.stick_index"))
        val armController = Joystick(Constants.getInt("Operator.lift.stick_index"))
    }
}