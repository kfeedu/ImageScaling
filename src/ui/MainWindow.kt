package ui

import WINDOW_HEIGHT
import WINDOW_WIDTH
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants

class MainWindow(appTitle: String) : JFrame() {

    var verticalGap = 5
    var horizontalGap = 5

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
        title = appTitle
        layout = BorderLayout(verticalGap, horizontalGap)
        isResizable = false
    }
}