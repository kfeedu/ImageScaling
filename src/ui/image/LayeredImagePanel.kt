package ui.image

import IMAGE_HEIGHT
import IMAGE_WIDTH
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JLayeredPane
import javax.swing.JScrollPane

class LayeredImagePanel(imagePanel: ImagePanel) : JLayeredPane(), ActionListener {

    val rasterButton = JButton("R")
    val vektorButton = JButton("V")

    init {
        size = Dimension(IMAGE_WIDTH, IMAGE_HEIGHT)
        rasterButton.setBounds(0, 0, 50, 50)
        rasterButton.addActionListener(this)
        add(rasterButton)

        vektorButton.setBounds(50, 0, 50, 50)
        vektorButton.addActionListener(this)
        add(vektorButton)

        val scrollableImage = JScrollPane(imagePanel)
        scrollableImage.setSize(IMAGE_WIDTH, IMAGE_HEIGHT)
        add(scrollableImage)

    }

    //ButtonListener
    override fun actionPerformed(e: ActionEvent) {
        when (e.source) {
            rasterButton -> {
            }
            vektorButton -> {
            }
        }
    }
}