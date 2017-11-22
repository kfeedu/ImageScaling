package ui.image

import data.model.Figure
import java.awt.Dimension
import java.awt.Graphics2D

interface ImageContract{

    interface Presenter{
        fun drawCoordinateSystem(g: Graphics2D, dimension: Dimension)

        fun attachView(view: ImageContract.View)

        fun setVectorImageDimension(figures: List<Figure>)

        fun drawVectors(figures: List<Figure>, g: Graphics2D)
    }

    interface View {
        fun setPrefferedSize(prefferedSize: Dimension)


    }
}