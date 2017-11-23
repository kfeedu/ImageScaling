package ui.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import ui.buttons.ButtonsPanel
import ui.settings.SettingsPanel
import util.image.VectorImageManipulator
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JPanel
class ImagePanel : JPanel(), ImageContract.View, ButtonsPanel.ImageIOListener, SettingsPanel.ImageTransformListener {

    private lateinit var image: BufferedImage
    private lateinit var figures: List<Figure>
    private lateinit var figuresManipulator: VectorImageManipulator
    private var presenter = ImagePresenter()
    var imageType = ImageType.RASTER
    var offsetX = 0
    var offsetY = 0
    var imageOffsetX = 0
    var imageOffsetY = 0

    init {
        presenter.attachView(this)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        when(imageType){
            ImageType.RASTER ->
                if (::image.isInitialized) g.drawImage(image, 0, 0, null)
            ImageType.VECTOR ->
                presenter.drawVectors(figures, g as Graphics2D, imageOffsetX, imageOffsetY)
        }
        presenter.drawCoordinateSystem(g as Graphics2D, preferredSize, offsetX, offsetY)
    }

    //MVP functions
    override fun setPrefferedSize(prefferedSize: Dimension) {
        this.preferredSize = prefferedSize
    }

    override fun setOffset(offsetX: Int, offsetY: Int) {
        this.offsetX = offsetX
        this.offsetY = offsetY
    }

    override fun setVectors(newVectors: List<Figure>) {
        figures = newVectors
    }

    override fun refresh() {
        repaint()
        revalidate()
    }

    override fun setImageOffset(offsetX: Int, offsetY: Int){
        imageOffsetX = offsetX
        imageOffsetY = offsetY
    }

    override fun setManipulator(figuresManipulator: VectorImageManipulator) {
        this.figuresManipulator = figuresManipulator
    }

    //ImageTransformListener functions
    override fun transformImage(transformations: List<Transformation>) {
        presenter.transformVectorImage(transformations, figuresManipulator)
    }

    //ImageIOListener functions
    override fun getRasterImage(): BufferedImage? = image

    override fun setRasterImage(image: BufferedImage) {
        this.image =  image
        imageType = ImageType.RASTER
        preferredSize = Dimension(image.width, image.height)
        refresh()
    }

    override fun getVectorImage(): List<Figure>? = figures

    override fun setVectorImage(figures: List<Figure>) {
        this.figures = figures
        figuresManipulator = VectorImageManipulator(figures)
        imageType = ImageType.VECTOR
        presenter.setVectorImageDimension(figures)
        refresh()
    }
}

enum class ImageType {
    RASTER,
    VECTOR
}

