package eightbitlab.server

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

//TODO Files.write(...) opens and closes file each time. Can be optimized
object Log {
    private val executor = Executors.newSingleThreadExecutor()
    private val logFileName = "./logs.txt"
    private val errorsFileName = "./errors.txt"
    private val formatter = SimpleDateFormat.getTimeInstance()

    init {
        File(logFileName).createNewFile()
        File(errorsFileName).createNewFile()
    }

    fun print(message: String) {
        println(message)

        executor.execute {
            try {
                writeToFile(logFileName, message, getCurrentFormattedTime())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun error(error: String) {
        System.err.println(error)

        executor.execute {
            try {
                writeToFile(errorsFileName, error, getCurrentFormattedTime())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getCurrentFormattedTime() = formatter.format(Date())

    private fun writeToFile(fileName: String, message: String, formattedTime: String?) {
        Files.write(Paths.get(fileName), "$formattedTime:\n$message\n".toByteArray(), StandardOpenOption.APPEND)
    }
}