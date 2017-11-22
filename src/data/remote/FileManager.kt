package data.remote

import data.model.Figure
import data.model.Line
import data.model.Polygon
import data.model.Rectangle
import util.FileExtensionHelper
import java.awt.Component
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import jdk.nashorn.internal.objects.NativeArray.forEach
import util.FileFilterType
import java.net.URI
import java.nio.file.Paths
import java.nio.file.Files
import java.util.function.Consumer
import java.util.stream.Stream


class FileManager {
    val fc = JFileChooser()

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

    fun saveVectorImage() {

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
                            'L' -> figures.add(Line(pointsList[0],pointsList[1]))
                            'R' -> figures.add(Rectangle(pointsList[0],pointsList[1]))
                            'P' -> figures.add(Polygon(pointsList))
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        print(figures.size)
        figures.forEach { f ->
            print(f.getPointPairs())
        }
        return figures
    }

    private fun getPointsFromLine(line: String): List<Pair<Int, Int>>{
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

    fun loadTransformationMatrix() {

    }
}