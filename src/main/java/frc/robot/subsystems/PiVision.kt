package frc.robot.subsystems

import edu.wpi.first.apriltag.AprilTagDetection
import edu.wpi.first.apriltag.AprilTagDetector
import edu.wpi.first.apriltag.AprilTagPoseEstimator
import edu.wpi.first.cscore.CvSink
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class PiVision( // Image source generator
    private val source: CvSink
) {
    // Capturing Mat for input image
    private val imgMat = Mat()

    // `Mats` for ping-pong buffer
    private val imgX = Mat()
    private val imgZ = Mat()

    // Image processor
    private val aprilCamera = AprilTagDetector()
    private val aprilPose = AprilTagPoseEstimator(AprilTagPoseEstimator.Config(1.0, 1.0, 1.0, 1.0, 1.0))

    // Initialize pi-vision AFTER starting capture,
    // otherwise OpenCV won't be initialized
    init {
        aprilCamera.addFamily("tag16h5")
    }

    val image: AprilTagDetection?
        get() {
            source.grabFrame(imgMat)
            Imgproc.cvtColor(imgMat, imgX, Imgproc.COLOR_RGB2GRAY)
            Imgproc.resize(imgX, imgZ, Size(320.0, 180.0))
            val detections = aprilCamera.detect(imgZ)
            aprilPose.estimate(detections[0]).x
            return try {
                detections[0]
            } catch (e: ArrayIndexOutOfBoundsException) {
                null
            }
        }
}