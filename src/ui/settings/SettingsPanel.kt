package ui.settings

import SETTINGS_HEIGHT
import SETTINGS_WIDTH
import java.awt.Dimension
import javax.swing.JPanel

class SettingsPanel : JPanel(), SettingsContract.View {

    private val presenter = SettingsPresenter()

    init {
        presenter.attachView(this)
        preferredSize = Dimension(SETTINGS_WIDTH, SETTINGS_HEIGHT)

    }
}