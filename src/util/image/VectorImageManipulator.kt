package util.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import data.model.transformation.TransformationType
import org.ejml.simple.SimpleMatrix
import util.MatrixTransformationHelper

class VectorImageManipulator(val rawVectors: List<Figure>) {

    var width = 0
    var height = 0
    var initialOffsetX = 0
    var initialOffsetY = 0
    var offsetX = 0
    var offsetY = 0
    var imageOffsetX = 0
    var imageOffsetY = 0

    init {
        setImageDimension()
        initialOffsetX = width / 2
        initialOffsetY = height / 2
        offsetX = initialOffsetX
        offsetY = initialOffsetX
    }

    fun transformImage(transforms: List<Transformation>) {
        val transformationMatrix = getTransformationMatrix(transforms)
        rawVectors.forEach { figure ->
            val newPoints = mutableListOf<Pair<Int, Int>>()
            figure.getPointPairs().forEach { pair ->
                val point = SimpleMatrix(1,3)
                point.set(0,0, pair.first.toDouble())
                point.set(0,1, pair.second.toDouble())
                point.set(0,2, 1.0)
                val resultPoint = point.mult(transformationMatrix)
                newPoints.add(Pair(resultPoint.get(0,0).toInt(), resultPoint.get(0,1).toInt()))
            }
            figure.setPointPairs(newPoints)
        }
        setImageDimension()
    }

    private fun getTransformationMatrix(transforms: List<Transformation>): SimpleMatrix {
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

    private fun setImageDimension() {
        var minHeight = 0
        var maxHeight = 0
        var minWidth = 0
        var maxWidth = 0

        rawVectors.forEach { figure ->
            figure.getPointPairs().forEach { point ->
                if (point.first > maxWidth)
                    maxWidth = point.first
                if (point.second > maxHeight)
                    maxHeight = point.second
                if(point.first < minWidth)
                    minWidth = point.first
                if(point.second < minHeight)
                    minHeight = point.second
            }
        }
        if(minHeight < 0){
            height = maxHeight + Math.abs(minHeight)
            offsetY = initialOffsetY + Math.abs(minHeight)
            imageOffsetY = Math.abs(minHeight)
        }else{
            height = maxHeight
            offsetY = initialOffsetY
            imageOffsetY = 0
        }

        if(minWidth < 0){
            width = maxWidth + Math.abs(minWidth)
            offsetX = initialOffsetX + Math.abs(minWidth)
            imageOffsetX = Math.abs(minWidth)
        }else{
            width = maxWidth
            offsetX = initialOffsetX
            imageOffsetX = 0
        }
        println("height:$height minHeight:$minHeight maxHeight:$maxHeight offsetY:$offsetY imageOffY:$imageOffsetY")
    }
}