// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems

import edu.wpi.first.apriltag.AprilTagDetection
import edu.wpi.first.apriltag.AprilTagDetector
import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.cscore.CvSink
import edu.wpi.first.wpilibj.AnalogInput
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getDouble
import frc.robot.Constants.getInt
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class Sensors : SubsystemBase() {
    /** Creates a new Sensors.  */
    private val buttons = arrayOfNulls<DigitalInput>(10)
    private val analogs = arrayOfNulls<AnalogInput>(4)
    private val aprilCamera = AprilTagDetector()
    private val aprilSink: CvSink
    private val imageMat: Mat
    private val imgX: Mat
    private val imgZ: Mat

    init {
        for (i in buttons.indices) {
            buttons[i] = DigitalInput(i)
        }
        for (i in analogs.indices) {
            analogs[i] = AnalogInput(i)
        }
        CameraServer.startAutomaticCapture()
        aprilSink = CameraServer.getVideo()
        imageMat = Mat()
        imgX = Mat()
        imgZ = Mat()
        aprilCamera.addFamily("tag16h5")
    }

    fun detect(imgMat: Mat?): AprilTagDetection? {
        Imgproc.cvtColor(imgMat, imgX, Imgproc.COLOR_RGB2GRAY)
        Imgproc.resize(imgX, imgZ, Size(320.0, 180.0))
        val detections = aprilCamera.detect(imgZ)
        return try {
            detections[0]
        } catch (e: ArrayIndexOutOfBoundsException) {
            null
        }
    }

    private val distance: Double
        get() = analogs[getInt("Robot.distance.port")]!!.voltage * getDouble("Robot.distance.voltage_to_inch")

    // This method will be called once per scheduler run
    override fun periodic() {
        // there has to be a better way to do this, but it's good enough for now.
        for (i in buttons.indices) {
            SmartDashboard.putBoolean("Button $i", buttons[i]!!.get())
        }
        for (i in analogs.indices) {
            SmartDashboard.putNumber("Analog $i", analogs[i]!!.voltage)
        }

        // Multiplying is for rounding to hundreds place
        SmartDashboard.putNumber("Distance", Math.round(distance * 100) / 100.0)

//		if (aprilSink.grabFrame(imageMat, 10) != 0) {
//			var detection = detect(imageMat);
//			if (detection == null)
//				return;
//			SmartDashboard.putNumber("Tag ID", detection.getId());
//			SmartDashboard.putNumber("Tag X", detection.getCenterX() * 4);
//			SmartDashboard.putNumber("Tag Y", detection.getCenterY() * 4);
//		}
    }
}