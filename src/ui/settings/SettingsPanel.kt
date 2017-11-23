package ui.settings

import SETTINGS_HEIGHT
import SETTINGS_WIDTH
import data.model.transformation.Transformation
import data.model.transformation.TransformationType
import util.BooleanFilter
import util.DoubleOnlyFilter
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.text.AbstractDocument

class SettingsPanel(private val listener: ImageTransformListener) : JPanel(), SettingsContract.View, ActionListener {

    private val presenter = SettingsPresenter()
    private val loadTransformationButton = JButton("LOAD TRANSFORMATION")
    private val transformButton = JButton("\\~T R A N S F O R M~/")

    private val rotateButton = JButton("ADD")
    private val rotateFirstTextField = JTextField()
    private val rotateSecondTextField = JTextField()

    private val transitionButton = JButton("ADD")
    private val transitionFirstTextField = JTextField()
    private val transitionSecondTextField = JTextField()

    private val scaleButton = JButton("ADD")
    private val scaleFirstTextField = JTextField()
    private val scaleSecondTextField = JTextField()

    private var transformationList = ListPanel()


    init {
        presenter.attachView(this)
        preferredSize = Dimension(SETTINGS_WIDTH, SETTINGS_HEIGHT)
        layout = GridBagLayout()
        createGUI()

    }

    interface ImageTransformListener {
        fun transformImage(transformations: List<Transformation>)
    }


    override fun actionPerformed(e: ActionEvent) {
        when (e.source) {
            rotateButton -> {
                if (!rotateFirstTextField.text.isEmpty() && !rotateFirstTextField.text.equals("-") &&
                        !rotateSecondTextField.text.isEmpty() && !rotateSecondTextField.text.equals("-"))
                    transformationList.transformations.addElement(
                            Transformation(rotateFirstTextField.text.toDouble(), rotateSecondTextField.text.toDouble(),
                                    TransformationType.ROTATE))
            }
            scaleButton -> {
                if (!scaleFirstTextField.text.isEmpty() && !scaleFirstTextField.text.equals("-") &&
                        !scaleSecondTextField.text.isEmpty() && !scaleSecondTextField.text.equals("-"))
                transformationList.transformations.addElement(
                        Transformation(scaleFirstTextField.text.toDouble(), scaleSecondTextField.text.toDouble(),
                                TransformationType.SCALE))
            }
            transitionButton -> {
                if (!transitionFirstTextField.text.isEmpty() && !transitionFirstTextField.text.equals("-") &&
                        !transitionSecondTextField.text.isEmpty() && !transitionSecondTextField.text.equals("-"))
                transformationList.transformations.addElement(
                        Transformation(transitionFirstTextField.text.toDouble(), transitionSecondTextField.text.toDouble(),
                                TransformationType.TRANSITION))
            }
            loadTransformationButton -> {
                transformationList.setListModel(presenter.getTransformations())
            }
            transformButton -> {
                val transInList = mutableListOf<Transformation>()
                for (item in transformationList.transformations.elements())
                    transInList.add(item)
                transformationList.setListModel(DefaultListModel())
                listener.transformImage(transInList)
            }
        }
    }

    private fun createGUI() {
        val constraints = GridBagConstraints()
        constraints.insets = Insets(5, 5, 5, 5)
        constraints.fill = GridBagConstraints.HORIZONTAL

        //setting pattern matching
        (rotateSecondTextField.document as AbstractDocument).documentFilter = BooleanFilter()
        (rotateFirstTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (scaleFirstTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (scaleSecondTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (transitionFirstTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (transitionSecondTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()

        //setting button listeners
        transformButton.addActionListener(this)
        loadTransformationButton.addActionListener(this)
        rotateButton.addActionListener(this)
        transitionButton.addActionListener(this)
        scaleButton.addActionListener(this)

        //Rotate Area y = 0 , 1
        val rotateFirstParLabel = JLabel("Angle:")
        constraints.gridx = 0
        constraints.gridy = 0
        add(rotateFirstParLabel, constraints)


        val rotateSecondParLabel = JLabel("Clockwise(0/1):")
        constraints.gridx = 1
        constraints.gridy = 0
        add(rotateSecondParLabel, constraints)

        constraints.gridx = 0
        constraints.gridy = 1
        add(rotateFirstTextField, constraints)

        constraints.gridx = 1
        constraints.gridy = 1
        add(rotateSecondTextField, constraints)

        constraints.gridx = 2
        constraints.gridy = 1
        add(rotateButton, constraints)

        //TRANSITION AREA y = 2,3

        val transitionFirstParLabel = JLabel("x:")
        constraints.gridx = 0
        constraints.gridy = 2
        add(transitionFirstParLabel, constraints)

        val transitionSecondParLabel = JLabel("y:")
        constraints.gridx = 1
        constraints.gridy = 2
        add(transitionSecondParLabel, constraints)

        constraints.gridx = 0
        constraints.gridy = 3
        add(transitionFirstTextField, constraints)

        constraints.gridx = 1
        constraints.gridy = 3
        add(transitionSecondTextField, constraints)

        constraints.gridx = 2
        constraints.gridy = 3
        add(transitionButton, constraints)

        //SCALE AREA y = 4,5

        val scaleFirstParLabel = JLabel("x:")
        constraints.gridx = 0
        constraints.gridy = 4
        add(scaleFirstParLabel, constraints)


        val scaleSecondParLabel = JLabel("Angle:")
        constraints.gridx = 1
        constraints.gridy = 4
        add(scaleSecondParLabel, constraints)


        constraints.gridx = 0
        constraints.gridy = 5
        add(scaleFirstTextField, constraints)

        constraints.gridx = 1
        constraints.gridy = 5
        add(scaleSecondTextField, constraints)

        constraints.gridx = 2
        constraints.gridy = 5
        add(scaleButton, constraints)


        //LIST PANEL
        constraints.gridx = 0
        constraints.gridy = 6
        constraints.gridwidth = 3
        add(transformationList, constraints)

        //LOAD TRANSFORMATION BUTTON
        constraints.gridx = 0
        constraints.gridy = 7
        constraints.gridwidth = 3
        add(loadTransformationButton, constraints)

        //DO TRANSFORMATION BUTTON
        constraints.gridx = 0
        constraints.gridy = 8
        constraints.gridwidth = 3
        add(transformButton, constraints)
    }
}