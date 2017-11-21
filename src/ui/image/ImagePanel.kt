package ui.image

import IMAGE_HEIGHT
import IMAGE_WIDTH
import ui.buttons.ButtonsPanel
import java.awt.Dimension
import java.awt.image.BufferedImage
import javax.swing.JPanel

class ImagePanel : JPanel(), ButtonsPanel.ImageIOListener {

    lateinit var img: BufferedImage

    init {
        preferredSize = Dimension(IMAGE_WIDTH, IMAGE_HEIGHT)
    }

    override fun getImage(): BufferedImage? = img

    override fun setImage(image: BufferedImage) {
        img =  image
    }
}