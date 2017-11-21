package ui.buttons

import java.awt.image.BufferedImage

interface ButtonsContract {
    interface Presenter {

        fun loadImage(): BufferedImage?

        fun saveImage(image: BufferedImage)

        fun attachView(view: ButtonsContract.View)
    }

    interface View {

    }
}