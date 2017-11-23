package data.model.figure

import data.model.figure.Figure
import java.awt.Graphics2D

class Rectangle(val startPoint: Pair<Int, Int>, val endPoint: Pair<Int, Int>): Figure() {
    override fun draw(graphics2D: Graphics2D) {
        var width = endPoint.first - startPoint.first
        var height = endPoint.second - startPoint.second
        var x = startPoint.first
        var y = startPoint.second
        if (width < 0)
        {
            x+=width
            width = -width
        }
        if (height < 0){
            y+=height
            height = -height
        }
        graphics2D.drawRect(x, y, width, height )
    }

    override fun getPointPairs(): List<Pair<Int, Int>> {
        return listOf(startPoint, endPoint)
    }
}