// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.commands.*
import frc.robot.commands.autos.AutoBalanceCommand
import frc.robot.commands.detection.ZeroHeadingCommand
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.EndEffector

class RobotContainer {
    // Replace with CommandPS4Controller or CommandJoystick if needed
    // private final CommandXboxController m_driverController
    // = new CommandXboxController(OperatorConstants.kDriverControllerPort);

    // Create the chooser for autonomous commands
    private val autoChooser = SendableChooser<Command>()

    init {
        // Configure the trigger bindings
        configureBindings()
        drivetrain.defaultCommand = DriveArcade().alongWith(EndEffectorCommand())

        // Set default commands of subsystems

        // Autonomous Chooser
        autoChooser.setDefaultOption("Simple Drive Auto", driveForwardAuto)
        autoChooser.addOption("Park on charge", driveBalanceAuto)
        autoChooser.addOption("Drop Cube", dropCubeAuto)
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
    private fun configureBindings() {
        driverController.button(1).onTrue(ZeroHeadingCommand(gyroSub))
        driverController.button(2).onTrue(AutoBalanceCommand(drivetrain, gyroSub))
    }

    val autonomousCommand: Command?
        get() = autoChooser.selected

    companion object {
        private val config = ConfigReader("Operator.")

        // The robot's subsystems and commands are defined here...
        val drivetrain = Drivetrain()
        val gyroSub = frc.robot.subsystems.NavigationSubsystem

        //        val sensors = Sensors()
        val endEffector = EndEffector()

        //        val driverController = CommandJoystick(config.getInt("drive.stick_index"))
        val driverController = CommandXboxController(config.getInt("drive.controller_index"))
        val armController = Joystick(config.getInt("lift.stick_index"))
    }
}