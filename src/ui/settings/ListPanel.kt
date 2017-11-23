package ui.settings

import data.model.transformation.Transformation
import data.model.transformation.TransformationType
import java.awt.Color
import java.awt.Component
import javax.swing.*

class ListPanel : JList<Transformation>() {

    var selectedTransformation: Transformation?
    var transformations = DefaultListModel<Transformation>()

    init {
        model = transformations
        setCellRenderer(ListRenderer())
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        selectedTransformation = null
    }

    fun setListModel(newListModel: DefaultListModel<Transformation>){
        transformations = newListModel
        model = newListModel
    }

    fun getSelected(): Transformation? = selectedTransformation

    private inner class ListRenderer : JLabel(), ListCellRenderer<Transformation> {

        override fun getListCellRendererComponent(list: JList<out Transformation>, value: Transformation, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
            isOpaque = true
            text = "$index " + getClassText(value)

            if (isSelected) {
                selectedTransformation = value
                background = Color(0, 0, 255, 90)
            } else {
                background = Color(255, 255, 255)
            }
            return this
        }

        private fun getClassText(transformation: Transformation): String =
                when (transformation.type) {
                    TransformationType.ROTATE ->
                        "Rotate (${transformation.x}, ${transformation.y})"
                    TransformationType.TRANSITION ->
                        "Transition (${transformation.x}, ${transformation.y})"
                    TransformationType.SCALE -> {
                        "Scale (${transformation.x}, ${transformation.y})"
                    }
                }
    }
}
