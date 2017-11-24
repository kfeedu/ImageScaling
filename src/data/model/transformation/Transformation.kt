package data.model.transformation

class Transformation(val x: Double, val y: Double, val type: TransformationType)

enum class TransformationType {
    SCALE,
    TRANSITION,
    ROTATE
}