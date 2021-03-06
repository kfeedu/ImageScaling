package data.remote

import data.model.figure.Figure
import data.model.figure.Line
import data.model.figure.Polygon
import data.model.figure.Rectangle
import data.model.transformation.TransformationType
import util.filefilter.FileExtensionHelper
import java.awt.Component
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import util.filefilter.FileFilterType
import java.nio.file.Paths
import java.nio.file.Files
import java.io.PrintWriter


class FileManager private constructor() {

    private object Holder {
        val INSTANCE = FileManager()
    }

    companion object {
        val instance: FileManager by lazy { Holder.INSTANCE }
    }

    private val fc = JFileChooser()

    init {
        fc.isAcceptAllFileFilterUsed = false
    }

    fun saveRasterImage(image: BufferedImage, context: Component) {
        val returnVal = fc.showSaveDialog(context)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ImageIO.write(image, "jpg", fc.selectedFile)
        }
    }

    fun loadRasterImage(context: Component): BufferedImage? {
        fc.addChoosableFileFilter(FileExtensionHelper().getFileFilter(FileFilterType.IMAGE))
        var image: BufferedImage? = null
        val returnVal = fc.showOpenDialog(context)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                image = ImageIO.read(fc.selectedFile)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
        return image
    }

    fun saveVectorImage(list: List<Figure>, context: Component) {
        val returnVal = fc.showSaveDialog(context)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            PrintWriter(Files.newBufferedWriter(
                    Paths.get(fc.selectedFile.canonicalPath))).use { pw ->
                list.forEach { figure ->
                    pw.println(figure.toString())
                }
            }
        }
    }

    fun loadVectorImage(context: Component): List<Figure> {
        fc.addChoosableFileFilter(FileExtensionHelper().getFileFilter(FileFilterType.TEXT))
        val figures = mutableListOf<Figure>()

        val returnVal = fc.showOpenDialog(context)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            val filePath = fc.selectedFile.canonicalPath
            try {
                Files.lines(Paths.get(filePath)).use { stream ->
                    stream.forEach { line ->
                        val pointsList = getPointsFromLine(line)
                        when (line[0]) {
                            'L' -> figures.add(Line(pointsList[0], pointsList[1]))
                            'R' -> figures.add(Rectangle(pointsList[0], pointsList[1]))
                            'P' -> figures.add(Polygon(pointsList))
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return figures
    }

    private fun getPointsFromLine(line: String): List<Pair<Int, Int>> {
        val pointsList = mutableListOf<Pair<Int, Int>>()

        //deleting figure type
        val lineWithoutType = line.substring(1)
        //splitting to single coordinates
        lineWithoutType.split(':').forEach { point ->
            point.substring(1, point.length - 1).split(")(").forEach { coordinate ->
                val xy = coordinate.split(",")
                pointsList.add(Pair(Integer.decode(xy[0]), Integer.decode(xy[1])))
            }
        }
        return pointsList
    }

    fun loadTransformationMatrix(context: Component): Map<TransformationType, Pair<Double, Double>> {
        val transformations = mutableMapOf<TransformationType, Pair<Double, Double>>()

        fc.addChoosableFileFilter(FileExtensionHelper().getFileFilter(FileFilterType.TEXT))
        val returnVal = fc.showOpenDialog(context)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            val filePath = fc.selectedFile.canonicalPath
            try {
                Files.lines(Paths.get(filePath)).use { lines ->
                    lines.forEach { line ->
                        val transformation = getTransformationFromLine(line)
                        when (line[0]) {
                            'R' -> transformations.put(TransformationType.ROTATE, transformation)
                            'S' -> transformations.put(TransformationType.SCALE, transformation)
                            'T' -> transformations.put(TransformationType.TRANSITION, transformation)
                        }

                    }
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
        return transformations
    }

    private fun getTransformationFromLine(line: String): Pair<Double, Double> {
        val lineWithoutBrackets = line.substring(2, line.length - 1)
        val xy = lineWithoutBrackets.split(',')
        return Pair(xy[0].toDouble(), xy[1].toDouble())
    }
}