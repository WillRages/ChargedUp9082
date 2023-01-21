// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Command m_autonomousCommand;

	private RobotContainer m_robotContainer;

	private final DigitalInput button_0 = new DigitalInput(0);
	private final DigitalInput button_1 = new DigitalInput(1);
	private final DigitalInput button_2 = new DigitalInput(2);
	private final DigitalInput button_3 = new DigitalInput(3);
	private final DigitalInput button_4 = new DigitalInput(4);
	private final DigitalInput button_5 = new DigitalInput(5);
	private final DigitalInput button_6 = new DigitalInput(6);
	private final DigitalInput button_7 = new DigitalInput(7);
	private final DigitalInput button_8 = new DigitalInput(8);
	private final DigitalInput button_9 = new DigitalInput(9);

	private final AnalogInput analog_0 = new AnalogInput(0);
	private final AnalogInput analog_1 = new AnalogInput(1);
	private final AnalogInput analog_2 = new AnalogInput(2);
	private final AnalogInput analog_3 = new AnalogInput(3);

	// private static final SPI.Port port = SPI.Port.kOnboardCS0;

	// private static final ADXRS450_Gyro gyro = new ADXRS450_Gyro(port);

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// SmartDashboard.putString("Port", "" + port.value);
		// Instantiate our RobotContainer. This will perform all our button bindings,
		// and put our
		// autonomous chooser on the dashboard.

		// The CameraServer class keeps a registry of all objects created with
		// CameraServer functions, so sources and sinks created in that way effectively
		// never go out of scope (unless explicitly removed).
		CameraServer.startAutomaticCapture();

		// gyro.calibrate();

		m_robotContainer = new RobotContainer();

	}

	/**
	 * This function is called every 20 ms, no matter the mode. Use this for items
	 * like diagnostics
	 * that you want ran during disabled, autonomous, teleoperated and test.
	 *
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and
	 * SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		// there has to be a better way to do this, but it's good enough for now.
		SmartDashboard.putBoolean("Button 0", button_0.get());
		SmartDashboard.putBoolean("Button 1", button_1.get());
		SmartDashboard.putBoolean("Button 2", button_2.get());
		SmartDashboard.putBoolean("Button 3", button_3.get());
		SmartDashboard.putBoolean("Button 4", button_4.get());
		SmartDashboard.putBoolean("Button 5", button_5.get());
		SmartDashboard.putBoolean("Button 6", button_6.get());
		SmartDashboard.putBoolean("Button 7", button_7.get());
		SmartDashboard.putBoolean("Button 8", button_8.get());
		SmartDashboard.putBoolean("Button 9", button_9.get());

		SmartDashboard.putNumber("Voltage 0", analog_0.getVoltage());
		SmartDashboard.putNumber("Voltage 1", analog_1.getVoltage());
		SmartDashboard.putNumber("Voltage 2", analog_2.getVoltage());
		SmartDashboard.putNumber("Voltage 3", analog_3.getVoltage());

		// SmartDashboard.putNumber("Gyro", gyro.getAngle());

		// Runs the Scheduler. This is responsible for polling buttons, adding
		// newly-scheduled
		// commands, running already-scheduled commands, removing finished or
		// interrupted commands,
		// and running subsystem periodic() methods. This must be called from the
		// robot's periodic
		// block in order for anything in the Command-based framework to work.
		CommandScheduler.getInstance().run();

	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	/**
	 * This autonomous runs the autonomous command selected by your
	 * {@link RobotContainer} class.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {
	}

	/** This function is called once when the robot is first started up. */
	@Override
	public void simulationInit() {
	}

	/** This function is called periodically whilst in simulation. */
	@Override
	public void simulationPeriodic() {
	}
}
