package ui.buttons

import java.awt.image.BufferedImage
import javax.swing.JFileChooser
import util.FileExtensionHelper
import java.io.IOException
import javax.imageio.ImageIO

class ButtonsPresenter : ButtonsContract.Presenter {

    lateinit var view: ButtonsContract.View
    val fc = JFileChooser()

    init {
        fc.isAcceptAllFileFilterUsed = false
        fc.addChoosableFileFilter(FileExtensionHelper())
    }

    override fun loadImage(): BufferedImage? {
        var image: BufferedImage? = null
        val returnVal = fc.showOpenDialog(view as ButtonsPanel)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                image = ImageIO.read(fc.selectedFile)
            }catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
        return image
    }

    override fun saveImage(image: BufferedImage) {
        //fc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        val returnVal = fc.showSaveDialog(view as ButtonsPanel)
        if(returnVal == JFileChooser.APPROVE_OPTION){
            ImageIO.write(image, "jpg", fc.selectedFile)
        }
    }

    override fun attachView(view: ButtonsContract.View) {
        this.view = view
    }
}