package ui.settings

import data.DataManager
import data.model.transformation.Transformation
import java.awt.Component
import javax.swing.DefaultListModel

class SettingsPresenter: SettingsContract.Presenter {

    private lateinit var view: SettingsContract.View

    private lateinit var dataManager : DataManager

    override fun attachView(view: SettingsContract.View) {
        this.view = view
        dataManager = DataManager(view as Component)
    }

    override fun getTransformations(): DefaultListModel<Transformation> {
        val transformationList = DefaultListModel<Transformation>()
        dataManager.getTransformations().forEach { transformation ->
            transformationList.addElement(transformation)
        }
        return transformationList
    }

}
