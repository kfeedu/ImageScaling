package ui.buttons

import data.model.figure.Figure
import java.awt.image.BufferedImage

interface ButtonsContract {
    interface Presenter {

        fun loadImage(): BufferedImage?

        fun saveImage(image: BufferedImage)

        fun loadVectorImage(): List<Figure>

        fun attachView(view: ButtonsContract.View)
    }

    interface View {

    }
}