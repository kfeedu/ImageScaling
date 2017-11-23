package data.model.figure

import java.awt.Graphics2D

abstract class Figure {
    abstract fun draw(graphics2D: Graphics2D, offsetX: Int, offsetY: Int)

    abstract fun getPointPairs(): List<Pair<Int, Int>>

    abstract fun setPointPairs(newPoints: List<Pair<Int,Int>>)
}

enum class Figures{
    RECTANGLE,
    LINE,
    POLYGON
}