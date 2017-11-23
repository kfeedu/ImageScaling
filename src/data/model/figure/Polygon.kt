package data.model.figure

import java.awt.Graphics2D

class Polygon(val points: List<Pair<Int, Int>>): Figure(){
    override fun draw(graphics2D: Graphics2D) {
        val xPoints = points.map { it.first }.toIntArray()
        val yPoints = points.map { it.second }.toIntArray()
        val nPoints = points.size
        graphics2D.drawPolygon(xPoints, yPoints, nPoints)
    }

    override fun getPointPairs(): List<Pair<Int, Int>> {
        return points
    }
}