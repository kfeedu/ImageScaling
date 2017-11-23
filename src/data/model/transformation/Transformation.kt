package data.model.transformation

import org.ejml.simple.SimpleMatrix

class Transformation(val x: Double, val y: Double, val type: TransformationType) {
}

enum class TransformationType {
    SCALE,
    TRANSITION,
    ROTATE
}