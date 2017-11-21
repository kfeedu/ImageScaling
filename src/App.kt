import ui.MainWindow
import ui.buttons.ButtonsPanel
import ui.image.ImagePanel
import ui.settings.SettingsPanel
import java.awt.BorderLayout

class App {

    var imagePanel = ImagePanel()
    var settingsPanel = SettingsPanel()
    var buttonsPanel = ButtonsPanel(imagePanel)

    fun init() {
        val mainWindow = MainWindow(APP_NAME)
        mainWindow.add(buttonsPanel, BorderLayout.LINE_START)
        mainWindow.add(imagePanel, BorderLayout.CENTER)
        mainWindow.add(settingsPanel, BorderLayout.PAGE_END)
        mainWindow.pack()
        mainWindow.isVisible = true
    }
}