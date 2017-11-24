package ui.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import org.ejml.simple.SimpleMatrix
import util.MatrixTransformationHelper
import util.image.ImageManipulator
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

class ImagePresenter : ImageContract.Presenter {

    lateinit var view: ImageContract.View
    val additionalOffset = 150

    override fun transformImage(transformations: List<Transformation>, imageManipulator: ImageManipulator) {
        imageManipulator.transformImage(transformations)
        view.setImageOffset(imageManipulator.imageOffsetX, imageManipulator.imageOffsetY)
        view.setOffset(imageManipulator.offsetX, imageManipulator.offsetY)

        var width =
                if (imageManipulator.width < imageManipulator.offsetX)
                    imageManipulator.offsetX + additionalOffset
                else
                    imageManipulator.width
        var height =
                if (imageManipulator.height < imageManipulator.offsetY)
                    imageManipulator.offsetY + additionalOffset
                else
                    imageManipulator.height
        view.setPrefferedSize(Dimension(width, height))
        view.setManipulator(imageManipulator)
        view.refresh()
    }

    override fun drawRaster(g: Graphics2D, image: BufferedImage, offsetX: Int, offsetY: Int) {
        var x = image.width
        var y = image.height
        if (offsetX > image.width)
            x = offsetX + additionalOffset
        if (offsetY > image.height)
            y = offsetY + additionalOffset
        g.color = Color.WHITE

        println("x:$x y:$y offX:$offsetX offY:$offsetY imageX:${image.width} imageY:${image.height}")
        g.fillRect(0, 0, x, y)
        g.drawImage(image, 0, 0, null)
    }

    override fun drawCoordinateSystem(g: Graphics2D, dimension: Dimension, offsetX: Int, offsetY: Int) {
        g.color = Color.BLACK
        var x = dimension.width
        var y = dimension.height
        if (offsetX > dimension.width)
            x = offsetX
        if (offsetY > dimension.height)
            y = offsetY

        g.drawLine(0, offsetY, x, offsetY)
        g.drawLine(offsetX, 0, offsetX, y)

        for (i in offsetX..dimension.width step 20) {
            g.drawLine(i, offsetY + 3, i, offsetY - 3)
        }
        for (i in offsetX downTo 0 step 20) {
            g.drawLine(i, offsetY + 3, i, offsetY - 3)
        }
        for (j in offsetY..dimension.height step 20) {
            g.drawLine(offsetX + 3, j, offsetX - 3, j)
        }
        for (j in offsetY downTo 0 step 20) {
            g.drawLine(offsetX + 3, j, offsetX - 3, j)
        }
    }

    override fun setVectorImageDimension(figures: List<Figure>) {
        var maxHeight = 0
        var maxWidth = 0

        figures.forEach { figure ->
            figure.getPointPairs().forEach { point ->
                if (point.first > maxWidth)
                    maxWidth = point.first
                if (point.second > maxHeight)
                    maxHeight = point.second
            }
        }
        view.setOffset(maxWidth / 2, maxHeight / 2)
        view.setImageOffset(0, 0)
        view.setPrefferedSize(Dimension(maxWidth, maxHeight))
    }

    override fun drawLine(g: Graphics2D, a: Double, b: Double, dimension: Dimension, offsetX: Int, offsetY: Int) {
        val matrixHelper = MatrixTransformationHelper()
        val transformationMatrix = matrixHelper.transit(-offsetX.toDouble(), -offsetY.toDouble()).
                rotate(Math.toDegrees(Math.atan(a)), false).transit(0.0, -b).transit(offsetX.toDouble(), offsetY.toDouble())
        var point1 = SimpleMatrix(1, 3)
        point1.set(0, 0, dimension.width.toDouble() + 1000)
        point1.set(0, 1, offsetY.toDouble())
        point1.set(0, 2, 1.0)

        var point2 = SimpleMatrix(1, 3)
        point2.set(0, 0, -1000.0)
        point2.set(0, 1, offsetY.toDouble())
        point2.set(0, 2, 1.0)

        point1 = point1.mult(transformationMatrix.transformationMatrix)
        point2 = point2.mult(transformationMatrix.transformationMatrix)

        g.drawLine(point1.get(0, 0).roundToInt(), point1.get(0, 1).roundToInt(),
                point2.get(0, 0).roundToInt(), point2.get(0, 1).roundToInt())
    }

    override fun drawVector(figures: List<Figure>, g: Graphics2D, offsetX: Int, offsetY: Int) {
        figures.forEach { figure ->
            figure.draw(g, offsetX, offsetY)
        }
    }

    override fun attachView(view: ImageContract.View) {
        this.view = view
    }
}