package data.model

import java.awt.Graphics2D

class Line(val startPoint: Pair<Int, Int>, val endPoint: Pair<Int, Int>): Figure(){

    override fun draw(graphics2D: Graphics2D) =
            graphics2D.drawLine(startPoint.first, startPoint.second, endPoint.first, endPoint.second )

    override fun getPointPairs(): List<Pair<Int, Int>> =
            listOf(startPoint, endPoint)
}