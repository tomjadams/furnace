package au.id.adams.file.io

sealed trait FilePath

private final case class FilePath_(path: String) extends FilePath

object FilePath {
  import java.io.File

  implicit def filepath(filePath: String): FilePath = FilePath_(filePath)

  implicit def filePathToString(filePath: FilePath): String = filePath match {
    case FilePath_(p) => p
  }

  implicit def filePathToFile(filePath: FilePath): File = new File(filePath)

  implicit def filePathToScalazFile(filePath: FilePath): scalaz.io.File = new scalaz.io.File(filePath)
}
