package data

import data.model.figure.Figure
import data.model.transformation.Transformation
import data.remote.FileManager
import java.awt.Component
import java.awt.image.BufferedImage

class DataManager(val context: Component) {

    val fileManager = FileManager.instance

    fun saveRasterImage(image: BufferedImage) =
            fileManager.saveRasterImage(image, context)

    fun loadRasterImage(): BufferedImage? =
            fileManager.loadRasterImage(context)

    fun saveVectorImage(list: List<Figure>) =
            fileManager.saveVectorImage(list, context)

    fun loadVectorImage(): List<Figure> =
            fileManager.loadVectorImage(context)

    fun getTransformations(): List<Transformation> {
        val transformationMap = fileManager.loadTransformationMatrix(context)
        val transList = mutableListOf<Transformation>()
        transformationMap.forEach { type, transformation ->
            transList.add(Transformation(transformation.first, transformation.second, type))
        }
        return transList
    }
}