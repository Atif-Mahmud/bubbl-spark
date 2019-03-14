package tech.gaggle.util

object StringUtil {

  implicit class StringExtensions(private val string: String) extends AnyVal {
    def equalsMaybeIgnoreCase(obj: String, ignoreCase: Boolean = false): Boolean = {
      if (ignoreCase)
        string.equalsIgnoreCase(obj)
      else
        string.equals(obj)
    }
  }

}
