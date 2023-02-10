package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.apriltag.AprilTagPoseEstimator;
import edu.wpi.first.cscore.CvSink;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class PiVision {
    // Capturing Mat for input image
    private final Mat imgMat = new Mat();

    // `Mats` for ping-pong buffer
    private final Mat imgX = new Mat();
    private final Mat imgZ = new Mat();

    // Image source generator
    private final CvSink source;

    // Image processor
    private final AprilTagDetector aprilCamera = new AprilTagDetector();
    private final AprilTagPoseEstimator aprilPose =
            new AprilTagPoseEstimator(new AprilTagPoseEstimator.Config(1, 1, 1, 1, 1));

    // Initialize pi-vision AFTER starting capture,
    // otherwise OpenCV won't be initialized
    public PiVision(CvSink source) {
        this.source = source;
        aprilCamera.addFamily("tag16h5");
    }

    public AprilTagDetection getImage() {
        source.grabFrame(imgMat);
        Imgproc.cvtColor(imgMat, imgX, Imgproc.COLOR_RGB2GRAY);
        Imgproc.resize(imgX, imgZ, new Size(320, 180));

        var detections = aprilCamera.detect(imgZ);

        aprilPose.estimate(detections[0]).getX();

        try {
            return detections[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
