package tech.gaggle.util

import java.nio.charset.StandardCharsets

import org.intellij.lang.annotations.Language

import scala.io.Source
import scala.sys.process.{BasicIO, Process}

object PythonUtil {
  def runPythonScript(@Language("Python") scriptCode: String, stdin: String): String = {
    var stdoutString = Option.empty[String]

    val proc = Process(List("python", "-c", scriptCode)) run
      BasicIO.standard(true)
        .withInput { input =>
          input.write(scriptCode.getBytes(StandardCharsets.UTF_8))
        }
        .withOutput { output =>
          IOUtil.using(Source.fromInputStream(output)) { outputSource =>
            stdoutString = Option(outputSource.mkString)
          }
        }

    proc.exitValue() // Wait for process to exit and discard exit value

    stdoutString.get
  }
}
