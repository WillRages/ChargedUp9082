package frc.robot.subsystems

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.ADXRS450_Gyro
import edu.wpi.first.wpilibj.SerialPort
import edu.wpi.first.wpilibj2.command.SubsystemBase
import kotlin.math.IEEErem

object NavigationSubsystem : SubsystemBase() {

    var targetYaw = 0.0

    private val navx = AHRS(SerialPort.Port.kMXP)
    private val gyro = ADXRS450_Gyro()

    init {
        gyro.calibrate()
        navx.calibrate()
    }

    val connected get() = navx.isConnected

    val yaw get() = if (connected) navx.yaw else gyro.angle.IEEErem(360.0).toFloat()

    val pitch get() = if (connected) navx.pitch - pitchOffset else null

    val roll get() = if (connected) navx.roll - rollOffset else null


    private var pitchOffset = 0f
    private var rollOffset = 0f

    fun zeroYaw() = navx.zeroYaw().also { gyro.reset() }

    fun zeroPitch() = pitch?.let { pitchOffset += it }

    fun zeroRoll() = roll?.let { rollOffset += it }
}
