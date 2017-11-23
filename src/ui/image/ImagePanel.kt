package ui.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import ui.buttons.ButtonsPanel
import ui.settings.SettingsPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JPanel
class ImagePanel : JPanel(), ImageContract.View, ButtonsPanel.ImageIOListener, SettingsPanel.ImageTransformListener {

    private lateinit var image: BufferedImage
    private lateinit var figures: List<Figure>
    private var presenter = ImagePresenter()
    var imageType = ImageType.RASTER

    init {
        presenter.attachView(this)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        when(imageType){
            ImageType.RASTER ->
                if (::image.isInitialized) g.drawImage(image, 0, 0, null)
            ImageType.VECTOR ->
                presenter.drawVectors(figures, g as Graphics2D)
        }
        presenter.drawCoordinateSystem(g as Graphics2D, preferredSize)
    }

    //MVP functions
    override fun setPrefferedSize(prefferedSize: Dimension) {
        this.preferredSize = prefferedSize
    }

    //ImageTransformListener functions
    override fun transformImage(transformations: List<Transformation>) {

    }

    //ImageIOListener functions
    override fun getRasterImage(): BufferedImage? = image

    override fun setRasterImage(image: BufferedImage) {
        this.image =  image
        imageType = ImageType.RASTER
        preferredSize = Dimension(image.width, image.height)
        repaint()
        revalidate()
    }

    override fun getVectorImage(): List<Figure>? = figures

    override fun setVectorImage(figures: List<Figure>) {

        this.figures = figures
        imageType = ImageType.VECTOR
        presenter.setVectorImageDimension(figures)
        repaint()
        revalidate()
    }
}


enum class ImageType {
    RASTER,
    VECTOR
}

