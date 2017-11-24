package ui.settings

import SETTINGS_HEIGHT
import SETTINGS_WIDTH
import data.model.transformation.Transformation
import data.model.transformation.TransformationType
import util.inputfilters.BooleanFilter
import util.inputfilters.DoubleOnlyFilter
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
    private val transformButton = JButton("\\\\ ~ T R A N S F O R M ~ //")

    private val lineButton = JButton("SET")
    private val lineFirstTextField = JTextField()
    private val lineSecondTextField = JTextField()

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

        fun drawLine(a: Double, b: Double)
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
            lineButton -> {
                listener.drawLine(lineFirstTextField.text.toDouble(), lineSecondTextField.text.toDouble())
            }
        }
    }

    private fun createGUI() {
        val constraints = GridBagConstraints()
        constraints.fill = GridBagConstraints.HORIZONTAL

        //setting pattern matching
        (lineFirstTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (lineSecondTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (rotateSecondTextField.document as AbstractDocument).documentFilter = BooleanFilter()
        (rotateFirstTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (scaleFirstTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (scaleSecondTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (transitionFirstTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()
        (transitionSecondTextField.document as AbstractDocument).documentFilter = DoubleOnlyFilter()

        //setting button listeners
        lineButton.addActionListener(this)
        transformButton.addActionListener(this)
        loadTransformationButton.addActionListener(this)
        rotateButton.addActionListener(this)
        transitionButton.addActionListener(this)
        scaleButton.addActionListener(this)

        //LINE AREA
        val lineLabel = JLabel("LINE")
        constraints.gridx = 1
        constraints.gridy = 0
        constraints.gridwidth = 2
        constraints.insets = Insets(0, 0, 10, 0)
        add(lineLabel, constraints)

        val lineFirstParLabel = JLabel("a:")
        constraints.gridx = 0
        constraints.gridy = 1
        constraints.gridwidth = 1
        constraints.insets = Insets(5, 5, 5, 5)
        add(lineFirstParLabel, constraints)

        val lineSecondParLabel = JLabel("b:")
        constraints.gridx = 1
        constraints.gridy = 1
        add(lineSecondParLabel, constraints)

        constraints.gridx = 0
        constraints.gridy = 2
        add(lineFirstTextField, constraints)

        constraints.gridx = 1
        constraints.gridy = 2
        add(lineSecondTextField, constraints)

        constraints.gridx = 2
        constraints.gridy = 2
        add(lineButton, constraints)

        //TRANSFORTMATION AREA
        val titleLabel = JLabel("TRANSFORMATIONS")
        constraints.gridx = 1
        constraints.gridy = 3
        constraints.gridwidth = 2
        constraints.insets = Insets(20, 0, 0, 0)
        add(titleLabel, constraints)


        //Rotate Area y = 4,5,6
        val rotateLabel = JLabel("Rotate:")
        constraints.gridx = 0
        constraints.gridy = 4
        constraints.gridwidth = 1
        constraints.insets = Insets(10, 0, 0, 0)
        add(rotateLabel, constraints)
        constraints.insets = Insets(5, 5, 5, 5)


        val rotateFirstParLabel = JLabel("Angle:")
        constraints.gridx = 0
        constraints.gridy = 5
        add(rotateFirstParLabel, constraints)


        val rotateSecondParLabel = JLabel("Clockwise(0/1):")
        constraints.gridx = 1
        constraints.gridy = 5
        add(rotateSecondParLabel, constraints)

        constraints.gridx = 0
        constraints.gridy = 6
        add(rotateFirstTextField, constraints)

        constraints.gridx = 1
        constraints.gridy = 6
        add(rotateSecondTextField, constraints)

        constraints.gridx = 2
        constraints.gridy = 6
        add(rotateButton, constraints)

        //TRANSITION AREA y = 7,8,9
        val transitionLabel = JLabel("Transition:")
        constraints.gridx = 0
        constraints.gridy = 7
        constraints.gridwidth = 1
        constraints.insets = Insets(10, 0, 0, 0)
        add(transitionLabel, constraints)
        constraints.insets = Insets(5, 5, 5, 5)

        val transitionFirstParLabel = JLabel("x:")
        constraints.gridx = 0
        constraints.gridy = 8
        add(transitionFirstParLabel, constraints)

        val transitionSecondParLabel = JLabel("y:")
        constraints.gridx = 1
        constraints.gridy = 8
        add(transitionSecondParLabel, constraints)

        constraints.gridx = 0
        constraints.gridy = 9
        add(transitionFirstTextField, constraints)

        constraints.gridx = 1
        constraints.gridy = 9
        add(transitionSecondTextField, constraints)

        constraints.gridx = 2
        constraints.gridy = 9
        add(transitionButton, constraints)

        //SCALE AREA y = 10,11,12
        val scaleLabel = JLabel("Scale:")
        constraints.gridx = 0
        constraints.gridy = 10
        constraints.gridwidth = 1
        constraints.insets = Insets(10, 0, 0, 0)
        add(scaleLabel, constraints)
        constraints.insets = Insets(5, 5, 5, 5)

        val scaleFirstParLabel = JLabel("x:")
        constraints.gridx = 0
        constraints.gridy = 11
        add(scaleFirstParLabel, constraints)


        val scaleSecondParLabel = JLabel("y:")
        constraints.gridx = 1
        constraints.gridy = 11
        add(scaleSecondParLabel, constraints)


        constraints.gridx = 0
        constraints.gridy = 12
        add(scaleFirstTextField, constraints)

        constraints.gridx = 1
        constraints.gridy = 12
        add(scaleSecondTextField, constraints)

        constraints.gridx = 2
        constraints.gridy = 12
        add(scaleButton, constraints)


        //LIST PANEL
        constraints.gridx = 0
        constraints.gridy = 13
        constraints.gridwidth = 3
        add(transformationList, constraints)

        //LOAD TRANSFORMATION BUTTON
        constraints.gridx = 0
        constraints.gridy = 14
        constraints.gridwidth = 3
        add(loadTransformationButton, constraints)

        //DO TRANSFORMATION BUTTON
        constraints.gridx = 0
        constraints.gridy = 15
        constraints.gridwidth = 3
        add(transformButton, constraints)
    }
}