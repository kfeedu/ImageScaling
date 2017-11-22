package ui.buttons

import data.DataManager
import data.model.Figure
import java.awt.image.BufferedImage
import java.awt.Component

class ButtonsPresenter : ButtonsContract.Presenter {

    lateinit var view: ButtonsContract.View
    lateinit var dataManager:  DataManager

    override fun loadImage(): BufferedImage? =
            dataManager.loadRasterImage()

    override fun saveImage(image: BufferedImage) =
            dataManager.saveRasterImage(image)

    override fun loadVectorImage(): List<Figure> =
            dataManager.loadVectorImage()

    override fun attachView(view: ButtonsContract.View) {
        this.view = view
        dataManager = DataManager(view as Component)
    }
}