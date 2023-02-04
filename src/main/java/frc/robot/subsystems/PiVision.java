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
    private final Mat img_mat = new Mat();

    // `Mats` for ping-pong buffer
    private final Mat img_x = new Mat();
    private final Mat img_z = new Mat();

    // Image source generator
    private final CvSink source;

    // Image processor
    private final AprilTagDetector april_camera = new AprilTagDetector();
    private final AprilTagPoseEstimator april_pose =
            new AprilTagPoseEstimator(new AprilTagPoseEstimator.Config(1, 1, 1, 1, 1));

    // Initialize pi-vision AFTER starting capture,
    // otherwise OpenCV won't be initialized
    public PiVision(CvSink source) {
        this.source = source;
        april_camera.addFamily("tag16h5");
    }

    public AprilTagDetection getImage() {
        source.grabFrame(img_mat);
        Imgproc.cvtColor(img_mat, img_x, Imgproc.COLOR_RGB2GRAY);
        Imgproc.resize(img_x, img_z, new Size(320, 180));

        var detections = april_camera.detect(img_z);

        april_pose.estimate(detections[0]).getX();

        try {
            return detections[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
