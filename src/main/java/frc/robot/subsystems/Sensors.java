// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.opencv.core.Mat;

import edu.wpi.first.apriltag.AprilTagDetector;
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

	public final AprilTagDetector april_camera = new AprilTagDetector();
	public final CvSink april_sink;
	public final Mat image_Mat;

	public Sensors() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new DigitalInput(i);
		}

		for (int i = 0; i < analogs.length; i++) {
			analogs[i] = new AnalogInput(i);
		}

		CameraServer.startAutomaticCapture();
		april_sink = CameraServer.getVideo();
		image_Mat = new Mat();
		april_camera.addFamily("tag16h5");
	}

	public int detect(Mat imgMat) {
		var detections = april_camera.detect(imgMat);
		try {
			return detections[0].getId();
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
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

		SmartDashboard.putNumber("Distance", Math.round(getDistance() * 100) / 100d);

		// if (april_sink.grabFrame(image_Mat, 10) != 0) {
		// SmartDashboard.putNumber("Tag ID", detect(image_Mat));
		// }
	}
}
