package ui.settings

import data.model.transformation.Transformation
import javax.swing.DefaultListModel

interface SettingsContract {

    interface Presenter {
        fun getTransformations(): DefaultListModel<Transformation>

        fun attachView(view: SettingsContract.View)
    }

    interface View {

    }
}