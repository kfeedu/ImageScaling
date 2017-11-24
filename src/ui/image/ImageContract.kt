package ui.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import util.image.ImageManipulator
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.image.BufferedImage

interface ImageContract {

    interface Presenter {

        fun attachView(view: ImageContract.View)

        fun drawCoordinateSystem(g: Graphics2D, dimension: Dimension, offsetX: Int, offsetY: Int)

        fun transformImage(transformations: List<Transformation>, imageManipulator: ImageManipulator)

        fun drawRaster(g: Graphics2D, image: BufferedImage, offsetX: Int, offsetY: Int)

        fun drawVector(figures: List<Figure>, g: Graphics2D, offsetX: Int, offsetY: Int)

        fun setVectorImageDimension(figures: List<Figure>)

        fun drawLine(g: Graphics2D, a: Double, b: Double, dimension: Dimension, offsetX: Int, offsetY: Int)
    }

    interface View {

        fun setPrefferedSize(prefferedSize: Dimension)

        fun setOffset(offsetX: Int, offsetY: Int)

        fun setImageOffset(offsetX: Int, offsetY: Int)

        fun setManipulator(imageManipulator: ImageManipulator)

        fun refresh()
    }
}