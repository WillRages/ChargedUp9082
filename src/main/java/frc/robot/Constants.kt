// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot

import com.moandjiezana.toml.Toml
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.InvalidPathException
import java.nio.file.Path

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


class ConfigReader(var currentConfigPath: String) {
    fun getInt(path: String?): Int {
        return configReader.getLong(currentConfigPath + path).toInt()
    }

    fun getNestedInt(path: String?): Int {
        return configReader.getLong(configReader.getString(currentConfigPath + path)).toInt()
    }

    fun getDouble(path: String?): Double {
        return configReader.getDouble(currentConfigPath + path)
    }

    private companion object Constants {
        const val INLINE_CONFIG = false

        private val configFile: String = if (INLINE_CONFIG) {
            configString
        } else {
            val paths = arrayOf("src/main/java/frc/robot/config.toml", "/home/config.toml")
            var x: String? = null
            for (i in paths) {
                try {
                    x = Files.readString(Path.of(i))
                } catch (ignored: InvalidPathException) {
                }
            }
            x ?: throw FileNotFoundException("Could not find config file")
        }

        private val configReader = Toml().read(configFile)
    }
}