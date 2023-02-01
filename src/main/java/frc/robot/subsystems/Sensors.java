// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Sensors extends SubsystemBase {
	/** Creates a new Sensors. */

	private final DigitalInput[] buttons = new DigitalInput[10];
	private final AnalogInput[] analogs = new AnalogInput[4];

	public Sensors() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new DigitalInput(i);
		}

		for (int i = 0; i < analogs.length; i++) {
			analogs[i] = new AnalogInput(i);
		}
	}

	// This method will be called once per scheduler run
	@Override
	public void periodic() {
		// there has to be a better way to do this, but it's good enough for now.

		for (int i = 0; i < buttons.length; i++) {
			SmartDashboard.putBoolean("Button " + i, buttons[i].get());
		}

		for (int i = 0; i < analogs.length; i++) {
			SmartDashboard.putNumber("Analog " + i, analogs[i].getVoltage());
		}

		SmartDashboard.putNumber("Distance",
				Math.round(analogs[Constants.DISTANCE_PORT].getVoltage() * Constants.VOLTAGE_TO_INCH * 100) / 100d);

	}
}
