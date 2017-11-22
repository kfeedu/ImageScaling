package ui.settings

class SettingsPresenter: SettingsContract.Presenter {

    private lateinit var view: SettingsContract.View

    override fun attachView(view: SettingsContract.View) {
        this.view = view
    }
}
