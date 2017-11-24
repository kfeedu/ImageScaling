package data.model.figure

import java.awt.Graphics2D
import java.util.*

class Polygon(var points: List<Pair<Int, Int>>) : Figure() {
    override fun draw(graphics2D: Graphics2D, offsetX: Int, offsetY: Int) {
        val xPoints = points.map { it.first + offsetX }.toIntArray()
        val yPoints = points.map { it.second + offsetY }.toIntArray()
        val nPoints = points.size
        graphics2D.drawPolygon(xPoints, yPoints, nPoints)
    }

    override fun getPointPairs(): List<Pair<Int, Int>> {
        return points
    }

    override fun setPointPairs(newPoints: List<Pair<Int, Int>>) {
        points = newPoints
    }
    override fun toString(): String {
        return "P" + points.joinToString(":") { point ->
            "(${point.first},${point.second})"
        }
    }
}