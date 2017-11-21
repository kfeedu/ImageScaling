package ui.image

import java.awt.Graphics2D

class ImagePresenter: ImageContract.Presenter{

    lateinit var view: ImageContract.View

    override fun drawCoordinateSystem(g: Graphics2D, width: Int, height: Int) {
        val x_center = width/2
        val y_center = height/2

        for(i in 0..width step 14){
            g.drawLine(i, y_center, i+7, y_center)
        }

        for(j in 0..height step 14){
            g.drawLine(x_center, j, x_center, j+7)
        }
    }

    override fun attachView(view: ImageContract.View) {
        this.view = view
    }
}