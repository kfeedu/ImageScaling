package ui.buttons

import BUTTONS_HEIGHT
import BUTTONS_WIDTH
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import javax.swing.JButton
import javax.swing.JPanel

class ButtonsPanel(var imagePanel: ImageIOListener): JPanel(), ButtonsContract.View , ActionListener {

    val loadButton = JButton("LOAD")
    val saveButton = JButton("SAVE")
    val presenter = ButtonsPresenter()

    init {
        presenter.attachView(this)
        loadButton.addActionListener(this)
        saveButton.addActionListener(this)
        preferredSize = Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT)
        add(loadButton)
        add(saveButton)
    }

    //ButtonListener
    override fun actionPerformed(e: ActionEvent) {
        when(e.source){
            loadButton -> {
                val image = presenter.loadImage()
                if(image != null)
                    imagePanel.setImage(image)
            }
            saveButton -> {
                val image = imagePanel.getImage()
                if(image != null)
                    presenter.saveImage(image)

            }
        }
    }

    interface ImageIOListener {

        fun getImage(): BufferedImage?

        fun setImage(image: BufferedImage)
    }
}