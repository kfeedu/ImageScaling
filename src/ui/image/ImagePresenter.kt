package ui.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import util.image.VectorImageManipulator
import java.awt.Dimension
import java.awt.Graphics2D

class ImagePresenter : ImageContract.Presenter {

    lateinit var view: ImageContract.View

    override fun transformVectorImage(transformations: List<Transformation>, figuresManipulator: VectorImageManipulator) {
        figuresManipulator.transformImage(transformations)
        view.setImageOffset(figuresManipulator.imageOffsetX, figuresManipulator.imageOffsetY)
        view.setOffset(figuresManipulator.offsetX, figuresManipulator.offsetY)
        view.setPrefferedSize(Dimension(figuresManipulator.width, figuresManipulator.height))
        view.setVectors(figuresManipulator.rawVectors)
        view.setManipulator(figuresManipulator)
        view.refresh()
    }

    override fun drawCoordinateSystem(g: Graphics2D, dimension: Dimension, offsetX: Int, offsetY: Int) {
        g.drawLine(0, offsetY, dimension.width, offsetY)
        g.drawLine(offsetX, 0, offsetX, dimension.height)

        for (i in dimension.width / 2..dimension.width step 20) {
            g.drawLine(i, offsetY + 3, i, offsetY - 3)
            g.drawLine(dimension.width - i, offsetY + 3, dimension.width - i, offsetY - 3)
        }
        for (j in dimension.height / 2..dimension.height step 20) {
            g.drawLine(offsetX + 3, j, offsetX - 3, j)
            g.drawLine(offsetX + 3, dimension.height - j, offsetX - 3, dimension.height - j)
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

    override fun drawVectors(figures: List<Figure>, g: Graphics2D, offsetX: Int, offsetY: Int) {
        figures.forEach { figure ->
            figure.draw(g, offsetX, offsetY)
        }
    }

    override fun attachView(view: ImageContract.View) {
        this.view = view
    }
}