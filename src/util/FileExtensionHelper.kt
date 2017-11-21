package util

import java.io.File
import javax.swing.filechooser.FileFilter

class FileExtensionHelper: FileFilter() {

    object Extensions {
        const val jpeg = "jpeg"
        const val jpg = "jpg"
        const val gif = "gif"
        const val tiff = "tiff"
        const val tif = "tif"
        const val png = "png"
    }

    override fun accept(file: File): Boolean {
        if (file.isDirectory)
            return true

        val extension = getExtension(file)
        return if (extension != null) {
            extension == Extensions.tiff ||
                    extension == Extensions.tif ||
                    extension == Extensions.gif ||
                    extension == Extensions.jpeg ||
                    extension == Extensions.jpg ||
                    extension == Extensions.png
        } else false
    }

    override fun getDescription(): String = "Image files"

    private fun getExtension(f: File): String? {
        var ext: String? = null
        val s = f.name
        val i = s.lastIndexOf('.')

        if (i > 0 && i < s.length - 1)
            ext = s.substring(i + 1).toLowerCase()

        return ext
    }

}