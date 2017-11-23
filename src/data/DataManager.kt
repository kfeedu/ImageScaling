package data

import data.model.figure.Figure
import data.model.transformation.Transformation
import data.model.transformation.TransformationType
import data.remote.FileManager
import org.ejml.simple.SimpleMatrix
import util.MatrixTransformationHelper
import java.awt.Component
import java.awt.image.BufferedImage

class DataManager(val context: Component) {

    val fileManager = FileManager.instance

    fun saveRasterImage(image: BufferedImage) =
            fileManager.saveRasterImage(image, context)

    fun loadRasterImage(): BufferedImage? =
            fileManager.loadRasterImage(context)

    fun loadVectorImage(): List<Figure> =
            fileManager.loadVectorImage(context)

    fun getTransformations(): List<Transformation> {
        val transformationMap = fileManager.loadTransformationMatrix(context)
        val transList = mutableListOf<Transformation>()
        transformationMap.forEach { type, transformation ->
            transList.add(Transformation(transformation.first, transformation.second, type))
        }

//        val matrixTransformationHelper = MatrixTransformationHelper.instance
//        transformationMap.forEach { type, transformation ->
//            when(type){
//                TransformationType.TRANSITION ->
//                    matrixTransformationHelper.transit(transformation.first, transformation.second)
//                TransformationType.SCALE ->
//                    matrixTransformationHelper.scale(transformation.first, transformation.second)
//                TransformationType.ROTATE ->
//                        matrixTransformationHelper.rotate(transformation.first, transformation.second == 1.0)
//            }
//        }
        return transList
    }
}