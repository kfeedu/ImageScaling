package ui.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import util.image.ImageManipulator
import util.image.VectorImageManipulator
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class ImagePresenter : ImageContract.Presenter {

    lateinit var view: ImageContract.View

    override fun transformImage(transformations: List<Transformation>, imageManipulator: ImageManipulator) {
        imageManipulator.transformImage(transformations)
        view.setImageOffset(imageManipulator.imageOffsetX, imageManipulator.imageOffsetY)
        view.setOffset(imageManipulator.offsetX, imageManipulator.offsetY)
        view.setPrefferedSize(Dimension(imageManipulator.width, imageManipulator.height))
        view.setManipulator(imageManipulator)
        view.refresh()
    }

    override fun transformVectorImage(transformations: List<Transformation>, figuresManipulator: VectorImageManipulator) {
        figuresManipulator.transformImage(transformations)
        view.setImageOffset(figuresManipulator.imageOffsetX, figuresManipulator.imageOffsetY)
        view.setOffset(figuresManipulator.offsetX, figuresManipulator.offsetY)
        view.setPrefferedSize(Dimension(figuresManipulator.width, figuresManipulator.height))
        view.setManipulator(figuresManipulator)
        view.refresh()
    }

    override fun drawRaster(g: Graphics2D, image: BufferedImage, offsetX: Int, offsetY: Int){
        var x = image.width
        var y = image.height
        if(offsetX > image.width)
            x = offsetX
        if(offsetY > image.height)
            y = offsetY
        g.color = Color.WHITE
        g.fillRect(0 , 0, x , y)
        g.drawImage(image, 0, 0, null)
    }

    override fun drawCoordinateSystem(g: Graphics2D, dimension: Dimension, offsetX: Int, offsetY: Int) {
        g.color = Color.BLACK
        var x = dimension.width
        var y = dimension.height
        if(offsetX > dimension.width)
            x = offsetX
        if(offsetY > dimension.height)
            y = offsetY

        g.drawLine(0, offsetY, x, offsetY)
        g.drawLine(offsetX, 0, offsetX, y)

        for (i in x / 2..x step 20) {
            g.drawLine(i, offsetY + 3, i, offsetY - 3)
            g.drawLine(x - i, offsetY + 3, x - i, offsetY - 3)
        }
        for (j in y / 2..y step 20) {
            g.drawLine(offsetX + 3, j, offsetX - 3, j)
            g.drawLine(offsetX + 3, y - j, offsetX - 3, y - j)
        }


//        g.drawLine(0, offsetY, dimension.width, offsetY)
//        g.drawLine(offsetX, 0, offsetX, dimension.height)
//
//        for (i in dimension.width / 2..dimension.width step 20) {
//            g.drawLine(i, offsetY + 3, i, offsetY - 3)
//            g.drawLine(dimension.width - i, offsetY + 3, dimension.width - i, offsetY - 3)
//        }
//        for (j in dimension.height / 2..dimension.height step 20) {
//            g.drawLine(offsetX + 3, j, offsetX - 3, j)
//            g.drawLine(offsetX + 3, dimension.height - j, offsetX - 3, dimension.height - j)
//        }
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

    override fun drawVector(figures: List<Figure>, g: Graphics2D, offsetX: Int, offsetY: Int) {
        figures.forEach { figure ->
            figure.draw(g, offsetX, offsetY)
        }
    }

    override fun attachView(view: ImageContract.View) {
        this.view = view
    }
}