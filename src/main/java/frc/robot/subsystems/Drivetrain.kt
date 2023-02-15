// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.RelativeEncoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.getInt

class Drivetrain : SubsystemBase() {
    // Declare Variables
    // Declare Motors
    var motorLeftFront: CANSparkMax
    var motorLeftBack: CANSparkMax
    var motorRightFront: CANSparkMax
    var motorRightBack: CANSparkMax

    // Declare Encoders
    @JvmField
    var encoderLeft1: RelativeEncoder

    @JvmField
    var encoderLeft2: RelativeEncoder

    @JvmField
    var encoderRight1: RelativeEncoder

    @JvmField
    var encoderRight2: RelativeEncoder

    // Speed Controls
    var leftMotors: MotorControllerGroup
    var rightMotors: MotorControllerGroup

    // Differential Drive
    var differentialDrive: DifferentialDrive

    /** Creates a new Drivetrain.  */
    init {
        // CANSparkMax Controllers
        motorLeftFront = CANSparkMax(getInt("Robot.motors.left_front"), CANSparkMaxLowLevel.MotorType.kBrushless)
        motorLeftBack = CANSparkMax(getInt("Robot.motors.left_back"), CANSparkMaxLowLevel.MotorType.kBrushless)
        motorRightFront = CANSparkMax(getInt("Robot.motors.right_front"), CANSparkMaxLowLevel.MotorType.kBrushless)
        motorRightBack = CANSparkMax(getInt("Robot.motors.right_back"), CANSparkMaxLowLevel.MotorType.kBrushless)
        leftMotors = MotorControllerGroup(motorLeftFront, motorLeftBack)
        rightMotors = MotorControllerGroup(motorRightFront, motorRightBack)
        encoderLeft1 = motorLeftBack.encoder
        encoderLeft2 = motorLeftFront.encoder
        encoderRight1 = motorRightBack.encoder
        encoderRight2 = motorRightFront.encoder

        // Need to invert one side
        rightMotors.inverted = true
        differentialDrive = DifferentialDrive(leftMotors, rightMotors)
    }

    val averageEncoder: Double
        get() = ((Math.abs(encoderLeft1.position) + Math.abs(encoderLeft2.position)
                + Math.abs(encoderRight1.position) + Math.abs(encoderRight2.position))
                / 4)

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
    }
}