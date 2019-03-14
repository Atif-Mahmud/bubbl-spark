package tech.gaggle.util

import java.io.File

import scala.language.reflectiveCalls

object IOUtil {
  /**
    * Create a temp file, execute the lambda with the temp file and discard the temp file
    * after the lambda has executed.
    *
    * @param prefix The prefix for the temp file
    * @param suffix The suffix for the temp file
    * @param block  The lambda to execute
    */
  def useTemporaryFile(block: File => Unit,
                       prefix: String = "gtmp-",
                       suffix: String = ".tmp"): Unit = {
    val tmpFile = File.createTempFile(prefix, suffix)
    tmpFile.deleteOnExit() // No idea why createTempFile doesn't run this automatically
    try {
      block(tmpFile)
    } finally {
      tmpFile.delete()
    }
  }

  def using[T <: {def close()}]
  (resource: T)
  (block: T => Unit) {
    try {
      block(resource)
    } finally {
      if (resource != null) resource.close()
    }
  }
}