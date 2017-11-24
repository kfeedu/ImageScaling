package data.model.figure

import java.awt.Graphics2D

class Line(var startPoint: Pair<Int, Int>, var endPoint: Pair<Int, Int>) : Figure() {

    override fun draw(graphics2D: Graphics2D, offsetX: Int, offsetY: Int) =
            graphics2D.drawLine(startPoint.first + offsetX, startPoint.second + offsetY, endPoint.first + offsetX, endPoint.second + offsetY)

    override fun getPointPairs(): List<Pair<Int, Int>> =
            listOf(startPoint, endPoint)

    override fun setPointPairs(newPoints: List<Pair<Int, Int>>) {
        startPoint = newPoints[0]
        endPoint = newPoints[1]
    }

    override fun toString(): String = "L(${startPoint.first},${startPoint.second})(${endPoint.first},${endPoint.second})"
}