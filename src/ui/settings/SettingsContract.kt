package ui.settings

interface SettingsContract {

    interface Presenter {
        fun attachView(view: SettingsContract.View)
    }

    interface View {

    }
}