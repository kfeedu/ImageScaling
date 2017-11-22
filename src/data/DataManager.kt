package data

import data.model.Figure
import data.remote.FileManager
import java.awt.Component
import java.awt.image.BufferedImage

class DataManager(val context: Component) {

    val fileManager = FileManager()

    fun saveRasterImage(image: BufferedImage) =
            fileManager.saveRasterImage(image, context)

    fun loadRasterImage(): BufferedImage? =
            fileManager.loadRasterImage(context)

    fun loadVectorImage(): List<Figure> =
            fileManager.loadVectorImage(context)
}