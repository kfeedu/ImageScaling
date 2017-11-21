package ui.buttons

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import java.awt.image.BufferedImage
import javax.swing.JFileChooser
import util.FileExtensionHelper


class ButtonsPresenter : ButtonsContract.Presenter {
    lateinit var view: ButtonsContract.View
    val fc = JFileChooser()


    override fun loadImage(): BufferedImage {

        val returnVal = fc.showOpenDialog(view as ButtonsPanel)

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            val file = fc.selectedFile
            fc.isAcceptAllFileFilterUsed = false
            fc.addChoosableFileFilter(FileExtensionHelper())
            log.append("Opening: " + file.name + "." )
        } else {
            log.append("Open command cancelled by user.")
        }
    }

    override fun saveImage(image: BufferedImage) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attachView(view: ButtonsContract.View) {
        this.view = view
    }
}