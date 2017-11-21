package ui.image

import java.awt.Graphics2D

interface ImageContract{

    interface Presenter{
        fun drawCoordinateSystem(g: Graphics2D, width: Int, height: Int)

        fun attachView(view: ImageContract.View)
    }

    interface View {

    }
}