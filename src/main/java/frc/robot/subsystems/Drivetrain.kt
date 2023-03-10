// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.RelativeEncoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.ConfigReader
import kotlin.math.abs

class Drivetrain : SubsystemBase() {
    // Declare Variables
    // Declare Motors
    private val config = ConfigReader("Robot.motors.")
    private val motorLeftFront: CANSparkMax =
        CANSparkMax(config.getInt("left_front"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val motorLeftBack: CANSparkMax =
        CANSparkMax(config.getInt("left_back"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val motorRightFront: CANSparkMax =
        CANSparkMax(config.getInt("right_front"), CANSparkMaxLowLevel.MotorType.kBrushless)
    private val motorRightBack: CANSparkMax =
        CANSparkMax(config.getInt("right_back"), CANSparkMaxLowLevel.MotorType.kBrushless)

    init {
        motorLeftFront.idleMode = CANSparkMax.IdleMode.kBrake
        motorLeftBack.idleMode = CANSparkMax.IdleMode.kBrake
        motorRightFront.idleMode = CANSparkMax.IdleMode.kBrake
        motorRightBack.idleMode = CANSparkMax.IdleMode.kBrake
    }

    // Declare Encoders
    val encoderLeft1: RelativeEncoder = motorLeftBack.encoder

    val encoderLeft2: RelativeEncoder = motorLeftFront.encoder

    val encoderRight1: RelativeEncoder = motorRightBack.encoder

    val encoderRight2: RelativeEncoder = motorRightFront.encoder

    // Speed Controls
    private val leftMotors: MotorControllerGroup = MotorControllerGroup(motorLeftFront, motorLeftBack)
    private val rightMotors: MotorControllerGroup = MotorControllerGroup(motorRightFront, motorRightBack)

    // Differential Drive
    private val differentialDrive: DifferentialDrive

    /** Creates a new Drivetrain.  */
    init {
        // CANSparkMax Controllers

        // Need to invert one side
        rightMotors.inverted = true
        differentialDrive = DifferentialDrive(leftMotors, rightMotors)
    }

    val averageEncoder: Double
        get() = ((abs(encoderLeft1.position)
                + abs(encoderLeft2.position)
                + abs(encoderRight1.position)
                + abs(encoderRight2.position)
                ) / 4)

    fun setZeroEncoders() {
        encoderLeft1.position = 0.0
        encoderLeft2.position = 0.0
        encoderRight1.position = 0.0
        encoderRight2.position = 0.0
    }

    fun arcadeDrive(moveSpeed: Double, rotateSpeed: Double) {
        differentialDrive.arcadeDrive(moveSpeed, rotateSpeed)
    }

    fun tankDrive(moveSpeed: Double, rotateSpeed: Double) {
        differentialDrive.tankDrive(moveSpeed, rotateSpeed)
    }

    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
     *
     * @param maxOutput the maximum output to which the drive will be constrained
     */
    fun setMaxOutput(maxOutput: Double) {
        differentialDrive.setMaxOutput(maxOutput)
    }

    override fun periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Left 1 Encoder", encoderLeft1.position)
        SmartDashboard.putNumber("Left 2 Encoder", encoderLeft2.position)
        SmartDashboard.putNumber("Right 1 Encoder", encoderRight1.position)
        SmartDashboard.putNumber("Right 2 Encoder", encoderRight2.position)
    }
}