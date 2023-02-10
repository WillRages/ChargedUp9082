// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Sensors extends SubsystemBase {
	/** Creates a new Sensors. */

	public final DigitalInput[] buttons = new DigitalInput[10];
	public final AnalogInput[] analogs = new AnalogInput[4];

	public final CvSink april_sink;
	public final PiVision visionThread;

	public Sensors() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new DigitalInput(i);
		}

		for (int i = 0; i < analogs.length; i++) {
			analogs[i] = new AnalogInput(i);
		}

		CameraServer.startAutomaticCapture().setResolution(960, 720);
		april_sink = CameraServer.getVideo();
		visionThread = new PiVision(april_sink);
		visionThread.start();
	}

	public double getDistance() {
		return analogs[Constants.DISTANCE_PORT].getVoltage() * Constants.VOLTAGE_TO_INCH;
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

		// Multiplying is for rounding to hundreds place
		SmartDashboard.putNumber("Distance", Math.round(getDistance() * 100) / 100d);

		// Grab current detection buffer
		var detection = visionThread.getCurrentDetection();
		if (detection == null) {
			return;
		}

		SmartDashboard.putNumber("Tag ID", detection.getId());
		SmartDashboard.putNumber("Tag X", detection.getCenterX());
		SmartDashboard.putNumber("Tag Y", detection.getCenterY());

		var estimation = visionThread.getCurrentEstimates();
		if (estimation == null) {
			return;
		}

		SmartDashboard.putNumber("Err. 1", estimation.error1);
		SmartDashboard.putNumber("Err. 2", estimation.error2);

		if (estimation.error1 < estimation.error2) {
			SmartDashboard.putNumber("Est. X", estimation.pose1.getX());
			SmartDashboard.putNumber("Est. Y", estimation.pose1.getY());
		} else {
			SmartDashboard.putNumber("Est. X", estimation.pose2.getX());
			SmartDashboard.putNumber("Est. Y", estimation.pose2.getY());
		}

	}
}
