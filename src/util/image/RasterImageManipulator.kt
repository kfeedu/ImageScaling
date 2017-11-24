package util.image

import data.model.transformation.Transformation
import org.ejml.simple.SimpleMatrix
import java.awt.Dimension
import java.awt.image.BufferedImage
import kotlin.math.nextDown
import kotlin.math.nextUp
import kotlin.math.roundToInt

class RasterImageManipulator(private var rawImage: BufferedImage) : ImageManipulator() {

    var resultImage: BufferedImage
    private var xPositionMatrix: SimpleMatrix
    private var yPositionMatrix: SimpleMatrix
    private var cumulativeTransformMatrix: SimpleMatrix

    init {
        resultImage = rawImage
        //setting initial values
        width = rawImage.width
        height = rawImage.height
        initialOffsetX = rawImage.width / 2
        initialOffsetY = rawImage.height / 2
        offsetX = initialOffsetX
        offsetY = initialOffsetY

        //setting initial unitary matrix as transform matrix
        cumulativeTransformMatrix = SimpleMatrix(3, 3)
        cumulativeTransformMatrix.set(0, 0, 1.0)
        cumulativeTransformMatrix.set(1, 1, 1.0)
        cumulativeTransformMatrix.set(2, 2, 1.0)

        //setting initial position matrixes
        xPositionMatrix = SimpleMatrix(rawImage.width, rawImage.height)
        yPositionMatrix = SimpleMatrix(rawImage.width, rawImage.height)
        for (x in 0 until rawImage.width) {
            for (y in 0 until rawImage.height) {
                xPositionMatrix.set(x, y, x.toDouble())
                yPositionMatrix.set(x, y, y.toDouble())
            }
        }
    }

    override fun transformImage(transforms: List<Transformation>) {
        var actualTransformMatrix = getTransformationMatrix(transforms)
        //adding new transformations
        cumulativeTransformMatrix = cumulativeTransformMatrix.mult(actualTransformMatrix)

        //updating position matrixes
        for (x in 0 until rawImage.width) {
            for (y in 0 until rawImage.height) {
                var point = SimpleMatrix(1, 3)
                point.set(0, 0, xPositionMatrix.get(x, y))
                point.set(0, 1, yPositionMatrix.get(x, y))
                point.set(0, 2, 1.0)
                point = point.mult(actualTransformMatrix)
                xPositionMatrix.set(x, y, point.get(0, 0))
                yPositionMatrix.set(x, y, point.get(0, 1))
            }
        }
        val resultDimension = getActualDimension()
        resultImage = BufferedImage(resultDimension.width, resultDimension.height, BufferedImage.TYPE_INT_RGB)
        refreshImageProperites()

        val invertedTransformMatrix = cumulativeTransformMatrix.invert()
        //offsets to position image in BufferedImage
        val xOffset = getOffset(xPositionMatrix)
        val yOffset = getOffset(yPositionMatrix)

        for (x in xOffset.first until resultDimension.width - Math.abs(xOffset.first)) {
            for (y in yOffset.first until resultDimension.height - Math.abs(yOffset.first)) {
                var point = SimpleMatrix(1, 3)
                point.set(0, 0, x.toDouble())
                point.set(0, 1, y.toDouble())
                point.set(0, 2, 1.0)
                point = point.mult(invertedTransformMatrix)
                val xDst = point.get(0, 0) //* ratioX
                val yDst = point.get(0, 1) //* ratioY

                //to je hack xdd
                try {
                    resultImage.setRGB(x + Math.abs(xOffset.first), y + Math.abs(yOffset.first), getRgbBilinear(xDst, yDst))
                } catch (ex: Exception) {

                }
            }
        }
    }

    private fun getRgbBilinear(xDst: Double, yDst: Double): Int {
        //to je kolejny hack XD
        try {
            val point00 = getIntFromRGB(rawImage.getRGB(xDst.nextDown().roundToInt(), yDst.nextUp().roundToInt()))
            val point01 = getIntFromRGB(rawImage.getRGB(xDst.nextUp().roundToInt(), yDst.nextUp().roundToInt()))
            val point10 = getIntFromRGB(rawImage.getRGB(xDst.nextDown().roundToInt(), yDst.nextDown().roundToInt()))
            val point11 = getIntFromRGB(rawImage.getRGB(xDst.nextUp().roundToInt(), yDst.nextDown().roundToInt()))

            val a = xDst - xDst.toInt()
            val b = yDst - yDst.toInt()

            val red = bilinearInterpolation(a, b, point00.first.toDouble(), point01.first.toDouble(),
                    point10.first.toDouble(), point11.first.toDouble()).roundToInt()
            val green = bilinearInterpolation(a, b, point00.second.toDouble(), point01.second.toDouble(),
                    point10.second.toDouble(), point11.second.toDouble()).roundToInt()
            val blue = bilinearInterpolation(a, b, point00.third.toDouble(), point01.third.toDouble(),
                    point10.third.toDouble(), point11.third.toDouble()).roundToInt()

            return int2RGB(red, green, blue)
        } catch (ex: Exception) {
        }
        return int2RGB(255, 255, 255)
    }

    private fun bilinearInterpolation(a: Double, b: Double, point00: Double, point01: Double, point10: Double, point11: Double): Double {
        val fA0 = (1.0 - a) * point00 + a * point10
        val fA1 = (1.0 - a) * point01 + a * point11
        return (1.0 - b) * fA0 + b * fA1
    }

    private fun getActualDimension(): Dimension {
        val xOffset = getOffset(xPositionMatrix)
        val yOffset = getOffset(yPositionMatrix)

        return Dimension(Math.abs(xOffset.first) + Math.abs(xOffset.second),
                Math.abs(yOffset.first) + Math.abs(yOffset.second))
    }

    private fun refreshImageProperites() {
        width = resultImage.width
        height = resultImage.height

        val minWidth = getOffset(xPositionMatrix).first
        val minHeight = getOffset(yPositionMatrix).first

        if (minWidth < 0) {
            offsetX = initialOffsetX + Math.abs(minWidth)
            imageOffsetX = Math.abs(minWidth)
        } else {
            offsetX = initialOffsetX
            imageOffsetX = 0
        }
        if (minHeight < 0) {
            offsetY = initialOffsetY + Math.abs(minHeight)
            imageOffsetY = Math.abs(minHeight)
        } else {
            offsetY = initialOffsetY
            imageOffsetY = 0
        }
    }

    private fun getOffset(matrix: SimpleMatrix): Pair<Int, Int> {
        var min = 0.0
        var max = 0.0
        for (x in 0 until matrix.numRows()) {
            for (y in 0 until matrix.numCols()) {
                val pointX = matrix.get(x, y)
                if (pointX < min)
                    min = pointX
                if (pointX > max)
                    max = pointX
            }
        }
        return Pair(min.roundToInt(), max.roundToInt())
    }

    private fun getIntFromRGB(rgb: Int): Triple<Int, Int, Int> {
        val red = rgb shr 16 and 0xFF
        val green = rgb shr 8 and 0xFF
        val blue = rgb and 0xFF
        return Triple(red, green, blue)
    }

    private fun int2RGB(red: Int, green: Int, blue: Int): Int {
        var r = red
        var g = green
        var b = blue
        // Make sure that color intensities are in 0..255 range
        r = r and 0x000000FF
        g = g and 0x000000FF
        b = b and 0x000000FF
        // Assemble packed RGB using bit shift operations
        return (r shl 16) + (g shl 8) + b
    }
}