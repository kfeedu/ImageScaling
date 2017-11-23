package util.filefilter

import javax.swing.filechooser.FileFilter

class FileExtensionHelper {

    fun getFileFilter(fileFilterType: FileFilterType): FileFilter =
            when (fileFilterType) {
                FileFilterType.IMAGE -> ImageFileFilter.instance
                FileFilterType.TEXT -> TextFileFilter.instance
            }
}

enum class FileFilterType {
    IMAGE,
    TEXT
}