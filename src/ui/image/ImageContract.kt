package ui.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import util.image.VectorImageManipulator
import java.awt.Dimension
import java.awt.Graphics2D

interface ImageContract{

    interface Presenter{
        fun drawCoordinateSystem(g: Graphics2D, dimension: Dimension, offsetX: Int, offsetY: Int)

        fun attachView(view: ImageContract.View)

        fun setVectorImageDimension(figures: List<Figure>)

        fun drawVectors(figures: List<Figure>, g: Graphics2D, offsetX: Int, offsetY: Int)

        fun transformVectorImage(transformations: List<Transformation>, figuresManipulator: VectorImageManipulator)
    }

    interface View {
        fun setPrefferedSize(prefferedSize: Dimension)

        fun setOffset(offsetX: Int, offsetY: Int)

        fun setVectors(newVectors: List<Figure>)

        fun refresh()

        fun setImageOffset(offsetX: Int, offsetY: Int)

        fun setManipulator(figuresManipulator: VectorImageManipulator)
    }
}