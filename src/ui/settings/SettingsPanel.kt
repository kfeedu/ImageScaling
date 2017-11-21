package ui.settings

import SETTINGS_HEIGHT
import SETTINGS_WIDTH
import java.awt.Dimension
import javax.swing.JPanel

class SettingsPanel : JPanel() {

    init {
        preferredSize = Dimension(SETTINGS_WIDTH, SETTINGS_HEIGHT)

    }
}