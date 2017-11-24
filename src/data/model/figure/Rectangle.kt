package data.model.figure

import java.awt.Graphics2D

class Rectangle(var startPoint: Pair<Int, Int>, var endPoint: Pair<Int, Int>) : Figure() {
    override fun draw(graphics2D: Graphics2D, offsetX: Int, offsetY: Int) {
        var width = endPoint.first - startPoint.first
        var height = endPoint.second - startPoint.second
        var x = startPoint.first
        var y = startPoint.second
        if (width < 0) {
            x += width
            width = -width
        }
        if (height < 0) {
            y += height
            height = -height
        }
        graphics2D.drawRect(x + offsetX, y + offsetY, width, height)
    }

    override fun getPointPairs(): List<Pair<Int, Int>> {
        return listOf(startPoint, endPoint)
    }

    override fun setPointPairs(newPoints: List<Pair<Int, Int>>) {
        startPoint = newPoints[0]
        endPoint = newPoints[1]
    }
    override fun toString(): String = "R(${startPoint.first},${startPoint.second})(${endPoint.first},${endPoint.second})"
}