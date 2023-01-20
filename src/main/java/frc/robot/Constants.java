// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static class OperatorConstants {
        // Constants for driver controller port (likely unnecessary)
        public static final int kDriverControllerPort = 0;

    }

    public static class DriveConstants {
        public static final int kEncoderCPR = 1024;
        public static final double kWheelDiameterInches = 6;
        public static final double kEncoderDistancePerPulse =
                // Assumes the encoders are directly mounted on the wheel shafts
                (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;

        public static final boolean kGyroReversed = false;

        public static final double kStabilizationP = 1;
        public static final double kStabilizationI = 0.5;
        public static final double kStabilizationD = 0;

        public static final double kTurnP = 1;
        public static final double kTurnI = 0;
        public static final double kTurnD = 0;

        public static final double kMaxTurnRateDegPerS = 10;
        public static final double kMaxTurnAccelerationDegPerSSquared = 30;

        public static final double kTurnToleranceDeg = 10;
        public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
    }

    public static class AutoConstants {
        // These are constants for autonomous
        public static final double kAutoDriveDistanceInches = 200;
        public static final double kinchToEncoder = 1.7525;
        public static final double kAutoDriveSpeed = .2;
    }

    // Motor Controllers for Drive Train
    public static final int DRIVETRAIN_LEFT_FRONT_CANSPARKMAX = 3;
    public static final int DRIVETRAIN_LEFT_BACK_CANSPARKMAX = 4;
    public static final int DRIVETRAIN_RIGHT_FRONT_CANSPARKMAX = 1;
    public static final int DRIVETRAIN_RIGHT_BACK_CANSPARKMAX = 2;

    // Joysticks
    public static final int DRIVER_CONTROLLER = 0;
    public static final int DRIVER_CONTROLLER_MOVE_AXIS = 1;
    public static final int DRIVER_CONTROLLER_ROTATE_AXIS = 2;

}
