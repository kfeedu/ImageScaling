package ui.image

import data.model.Figure
import java.awt.Dimension
import java.awt.Graphics2D

class ImagePresenter: ImageContract.Presenter{

    lateinit var view: ImageContract.View

    override fun drawCoordinateSystem(g: Graphics2D, dimension: Dimension) {
        val x_center = dimension.width/2
        val y_center = dimension.height/2

        for(i in 0..dimension.width step 14){
            g.drawLine(i, y_center, i+7, y_center)
        }

        for(j in 0..dimension.height step 14){
            g.drawLine(x_center, j, x_center, j+7)
        }
    }

    override fun setVectorImageDimension(figures: List<Figure>) {
        var maxHeight = 0
        var maxWidth = 0

        figures.forEach { figure ->
            figure.getPointPairs().forEach { point ->
                if(point.first > maxWidth)
                    maxWidth = point.first
                if(point.second > maxHeight)
                    maxHeight = point.second
            }
        }
        view.setPrefferedSize(Dimension(maxWidth, maxHeight))
    }

    override fun drawVectors(figures: List<Figure>, g: Graphics2D) {
        figures.forEach { figure ->
            figure.draw(g)
        }
    }

    override fun attachView(view: ImageContract.View) {
        this.view = view
    }
}