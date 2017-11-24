package util.image

import data.model.transformation.Transformation
import data.model.transformation.TransformationType
import org.ejml.simple.SimpleMatrix
import util.MatrixTransformationHelper

abstract class ImageManipulator {
    var width = 0
    var height = 0
    var initialOffsetX = 0
    var initialOffsetY = 0
    var offsetX = 0
    var offsetY = 0
    var imageOffsetX = 0
    var imageOffsetY = 0

    abstract fun transformImage(transforms: List<Transformation>)

    protected fun getTransformationMatrix(transforms: List<Transformation>): SimpleMatrix {
        var matrixTransformationHelper = MatrixTransformationHelper()
        //uklad jednorodny
        matrixTransformationHelper.transit(-initialOffsetX.toDouble(), -initialOffsetY.toDouble())
        transforms.forEach { transformation ->
            matrixTransformationHelper = when (transformation.type) {
                TransformationType.TRANSITION ->
                    //TUTAJ -y bo odwrócony uklad wspolrzednych w osi y
                    matrixTransformationHelper.transit(transformation.x, -transformation.y)
                TransformationType.SCALE ->
                    matrixTransformationHelper.scale(transformation.x, transformation.y)
                TransformationType.ROTATE ->
                    matrixTransformationHelper.rotate(transformation.x, transformation.y == 1.0)
            }
        }
        //powrot do zwyklego ukladu
        matrixTransformationHelper.transit(initialOffsetX.toDouble(), initialOffsetY.toDouble())
        return matrixTransformationHelper.transformationMatrix
    }
}