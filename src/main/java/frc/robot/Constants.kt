// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot

import com.moandjiezana.toml.Toml
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 *
 *
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
object Constants {
    private val configFile = File("/home/lvuser/config.toml")
    private val configReader: Toml

    init {
        try {
            configReader = Toml().read(FileInputStream(configFile))
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }
    }

    @JvmStatic
    fun getInt(path: String?): Int {
        return configReader.getLong(path).toInt()
    }

    @JvmStatic
    fun getNestedInt(path: String?): Int {
        return getInt(configReader.getString(path))
    }

    @JvmStatic
    fun getDouble(path: String?): Double {
        return configReader.getDouble(path)
    }
}