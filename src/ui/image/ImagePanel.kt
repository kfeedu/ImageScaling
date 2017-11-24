package ui.image

import data.model.figure.Figure
import data.model.transformation.Transformation
import ui.buttons.ButtonsPanel
import ui.settings.SettingsPanel
import util.image.ImageManipulator
import util.image.RasterImageManipulator
import util.image.VectorImageManipulator
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JPanel

class ImagePanel : JPanel(), ImageContract.View, ButtonsPanel.ImageIOListener, SettingsPanel.ImageTransformListener {

    private var presenter = ImagePresenter()
    var imageType = ImageType.RASTER
    private lateinit var imageManipulator: ImageManipulator
    var offsetX = 0
    var offsetY = 0
    var imageOffsetX = 0
    var imageOffsetY = 0

    init {
        presenter.attachView(this)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        when (imageType) {
            ImageType.RASTER -> {
                if (::imageManipulator.isInitialized)
                  presenter.drawRaster(g as Graphics2D, (imageManipulator as RasterImageManipulator).resultImage, offsetX, offsetY)
            }
            ImageType.VECTOR ->
                presenter.drawVector((imageManipulator as VectorImageManipulator).rawVectors, g as Graphics2D, imageOffsetX, imageOffsetY)
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

    override fun refresh() {
        repaint()
        revalidate()
    }

    override fun setImageOffset(offsetX: Int, offsetY: Int) {
        imageOffsetX = offsetX
        imageOffsetY = offsetY
    }

    override fun setManipulator(imageManipulator: ImageManipulator) {
        this.imageManipulator = imageManipulator
    }

    //ImageTransformListener functions
    override fun transformImage(transformations: List<Transformation>) {
        presenter.transformImage(transformations, imageManipulator)
//
//        when(imageType){
//            ImageType.VECTOR ->
//                presenter.transformVectorImage(transformations, figuresManipulator)
//            ImageType.RASTER ->{
//                //TESTY
//                presenter.transformVectorImage(transformations, imageManipulator)
//            }
//        }
    }

    //ImageIOListener functions
    override fun getRasterImage(): BufferedImage? {
        if (imageType == ImageType.RASTER) {
            return (imageManipulator as RasterImageManipulator).resultImage
        }
        return null
    }

    override fun setRasterImage(image: BufferedImage) {
        imageManipulator = RasterImageManipulator(image)
        imageType = ImageType.RASTER
        preferredSize = Dimension(image.width, image.height)
        offsetX = image.width / 2
        offsetY = image.height / 2
        refresh()
    }

    override fun getVectorImage(): List<Figure>? {
        if (imageType == ImageType.VECTOR)
            return (imageManipulator as VectorImageManipulator).rawVectors
        return null
    }

    override fun setVectorImage(figures: List<Figure>) {
        imageManipulator = VectorImageManipulator(figures)
        imageType = ImageType.VECTOR
        presenter.setVectorImageDimension(figures)
        refresh()
    }
}

enum class ImageType {
    RASTER,
    VECTOR
}

