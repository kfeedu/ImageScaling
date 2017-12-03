package data.model.figure

import java.awt.Graphics2D
import java.awt.Point
import java.awt.Rectangle

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
        var rectangle = Rectangle(Point(startPoint.first + offsetX, startPoint.second + offsetY))
        rectangle.add(Point(endPoint.first + offsetX, endPoint.second + offsetY))
        graphics2D.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
//        graphics2D.drawRect(x + offsetX, y + offsetY, width, height)
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