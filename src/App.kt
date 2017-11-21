import ui.MainWindow
import ui.buttons.ButtonsPanel
import ui.image.ImagePanel
import ui.image.LayeredImagePanel
import ui.settings.SettingsPanel
import java.awt.BorderLayout


class App {

    private val mainWindow = MainWindow(APP_NAME)
    private val imagePanel = ImagePanel()
    private val settingsPanel = SettingsPanel()
    private val buttonsPanel = ButtonsPanel(imagePanel)
    private val layeredPanel = LayeredImagePanel(imagePanel)

    fun init() {
        mainWindow.add(layeredPanel, BorderLayout.CENTER)
        mainWindow.add(buttonsPanel, BorderLayout.PAGE_START)
        mainWindow.add(settingsPanel, BorderLayout.LINE_END)
        mainWindow.pack()
    }
}