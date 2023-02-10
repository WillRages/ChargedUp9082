package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.apriltag.AprilTagPoseEstimate;
import edu.wpi.first.apriltag.AprilTagPoseEstimator;
import edu.wpi.first.cscore.CvSink;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PiVision extends Thread {
    // Capturing Mat for input image
    private final Mat img_mat = new Mat();

    // Mats for ping-pong buffer
    private final Mat img_x = new Mat();
    private final Mat img_z = new Mat();

    // Image source generator
    private final CvSink source;

    // Image processor
    private final AprilTagDetector aprilCamera = new AprilTagDetector();

    // Output Buffers
    private final AprilTagDetection currentDetection = null;
    private final AprilTagPoseEstimate currentEstimates = null;

    // Interrupter
    public volatile boolean is_running = true;

    // focal length found at https://www.chiefdelphi.com/t/focal-length-of-microsoft-lifecam/157480
    // 960w x 544h
    private final AprilTagPoseEstimator aprilTagPoseEstimator = new AprilTagPoseEstimator(
            new AprilTagPoseEstimator.Config(0.1524, 160.4551767, 160.4551767, 80, 80));

    // Initialize pi-vision AFTER starting capture,
    // otherwise OpenCV won't be initialized
    public PiVision(CvSink source) {
        this.source = source;
        aprilCamera.addFamily("tag16h5");
    }

    private Mat grabCurrentFrame() {
        source.grabFrame(img_mat);

        if (img_mat.empty()) return null;

        // System.out.println(img_mat.width());
        // System.out.println(img_mat.height());

        Imgproc.cvtColor(img_mat, img_x, Imgproc.COLOR_RGB2GRAY);
        Imgproc.resize(img_x, img_z, new Size(320, 180));

        return img_z;
    }

    // Point Array for storing checkerboard corners
    private final MatOfPoint2f checkerboard = new MatOfPoint2f();

    // criteria object for corner refinement
    private final TermCriteria criteria = new TermCriteria(new double[]{100});

    // Various sizes for things
    private final Size board_dims = new Size(9, 6); // col, row
    private final Size search_size = new Size(11, 11);
    private final Size zero_zone = new Size(-1, -1);

    // Arrays to hold detections
    private final ArrayList<MatOfPoint3f> object_points = new ArrayList<>();
    private final ArrayList<MatOfPoint2f> image_points = new ArrayList<>();

    private boolean calibrateStep(MatOfPoint3f object_plane) {
        img_mat.setTo(grabCurrentFrame());

        // return early if no corners found
        if (!Calib3d.findChessboardCorners(img_mat, board_dims, checkerboard)) return false;

        object_points.add(object_plane);
        aprilTagPoseEstimator.estimate(null);

        // Refines corner detections
        Imgproc.cornerSubPix(img_mat, checkerboard, search_size, zero_zone, criteria);
        image_points.add((MatOfPoint2f) checkerboard.clone());

        return true;
    }

    // Object plane mat
    private final ArrayList<Point3> object_plane_arr = new ArrayList<>((int) (board_dims.height * board_dims.width));
    private final MatOfPoint3f object_plane = new MatOfPoint3f();

    private final ArrayList<Mat> asdf = new ArrayList<>();
    private final ArrayList<Mat> fdsa = new ArrayList<>();
    private void calibrateCamera() {
        for (int i = 0; i < board_dims.height; i++) {
            for (int j = 0; j < board_dims.width; j++) {
                // 10 mm squares
                object_plane_arr.add(new Point3(j * 10, i * 10, 0));
            }
        }
        object_plane.fromList(object_plane_arr);

        // Try to calibrate 10 times
        for (int i = 0; i < 10; i++) {
            //noinspection StatementWithEmptyBody
            while (!calibrateStep(object_plane));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }

        Calib3d.calibrateCamera(
                object_points.stream().map(i -> (Mat) i).collect(Collectors.toList()),
                image_points.stream().map(i -> (Mat) i).collect(Collectors.toList()),
                img_mat.size(), img_x, img_z, asdf, fdsa);
        System.out.println(""+img_x);
        System.out.println(""+img_z);
    }

    public AprilTagDetection getImage() {
        var detections = aprilCamera.detect(grabCurrentFrame());
        if (detections.length == 0) return null;
        return detections[0];
    }

    @Override
    public void run() {
        calibrateCamera();
//        while (is_running) {
//            this.currentDetection = getImage();
//            if (currentDetection == null) {
//                continue;
//            }
//            this.currentEstimates = aprilTagPoseEstimator.estimateOrthogonalIteration(currentDetection, 50);
//        }
    }

    public AprilTagDetection getCurrentDetection() {
        return currentDetection;
    }

    public AprilTagPoseEstimate getCurrentEstimates() {
        return currentEstimates;
    }
}
