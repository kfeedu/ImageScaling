package ui.buttons

import BUTTONS_HEIGHT
import BUTTONS_WIDTH
import data.model.figure.Figure
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import javax.swing.JButton
import javax.swing.JPanel

class ButtonsPanel(var imageIOListener: ImageIOListener): JPanel(), ButtonsContract.View , ActionListener {

    private val loadButton = JButton("LOAD")
    private val saveButton = JButton("SAVE")
    private val loadVectorImageButton = JButton("LOAD VECTOR IMAGE")
    private val saveVectorImageButton = JButton("SAVE VECTOR IMAGE")
    private val presenter = ButtonsPresenter()

    init {
        presenter.attachView(this)
        loadButton.addActionListener(this)
        saveButton.addActionListener(this)
        loadVectorImageButton.addActionListener(this)
        saveVectorImageButton.addActionListener(this)
        preferredSize = Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT)
        add(loadButton)
        add(saveButton)
        add(loadVectorImageButton)
        add(saveVectorImageButton)
    }

    //ButtonListener
    override fun actionPerformed(e: ActionEvent) {
        when(e.source){
            loadButton -> {
                val image = presenter.loadImage()
                if(image != null)
                    imageIOListener.setRasterImage(image)
            }
            saveButton -> {
                val image = imageIOListener.getRasterImage()
                if(image != null)
                    presenter.saveImage(image)

            }
            loadVectorImageButton -> {
                val figures = presenter.loadVectorImage()
                if(!figures.isEmpty())
                    imageIOListener.setVectorImage(figures)
            }
        }
    }

    interface ImageIOListener {

        fun getRasterImage(): BufferedImage?

        fun setRasterImage(image: BufferedImage)

        fun getVectorImage(): List<Figure>?

        fun setVectorImage(figures: List<Figure>)
    }
}