package ui.image

import ui.buttons.ButtonsPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JPanel

class ImagePanel : JPanel(), ImageContract.View, ButtonsPanel.ImageIOListener {

    private lateinit var image: BufferedImage
    private var presenter = ImagePresenter()

    init {
        presenter.attachView(this)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (::image.isInitialized){
            g.drawImage(image, 0, 0, null)
            presenter.drawCoordinateSystem(g as Graphics2D, image.width, image.height)
        }
    }

    override fun getImage(): BufferedImage? = image

    override fun setImage(image: BufferedImage) {
        this.image =  image
        preferredSize = Dimension(image.width, image.height)
        repaint()
        revalidate()
    }
}