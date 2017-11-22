package util.filefilter

import java.io.File
import javax.swing.filechooser.FileFilter

class TextFileFilter private constructor(): FileFilter() {

    private object Holder {val INSTANCE = TextFileFilter()}

    companion object {
        val instance: TextFileFilter by lazy {Holder.INSTANCE}
    }

    object Extensions {
        const val txt = "txt"
    }

    override fun accept(file: File): Boolean {
        if (file.isDirectory)
            return true

        val extension = getExtension(file)
        return if (extension != null) {
            extension == Extensions.txt
        } else false
    }

    override fun getDescription(): String = "Vector txt files"

    private fun getExtension(f: File): String? {
        var ext: String? = null
        val s = f.name
        val i = s.lastIndexOf('.')

        if (i > 0 && i < s.length - 1)
            ext = s.substring(i + 1).toLowerCase()

        return ext
    }
}