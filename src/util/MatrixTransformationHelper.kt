package util

import org.ejml.simple.SimpleMatrix

class MatrixTransformationHelper {

    var transformationMatrix: SimpleMatrix = SimpleMatrix.identity(3)

    fun rotate(angle: Double, clockwise: Boolean): MatrixTransformationHelper {
        transformationMatrix = transformationMatrix.mult(getRotationMatrix(angle, clockwise))
        return this
    }

    fun transit(x: Double, y: Double): MatrixTransformationHelper {
        transformationMatrix = transformationMatrix.mult(getTransitionMatrix(x, y))
        return this
    }

    fun scale(x: Double, y: Double): MatrixTransformationHelper {
        transformationMatrix = transformationMatrix.mult(getScalingMatrix(x, y))
        return this
    }

    private fun getRotationMatrix(angle: Double, clockwise: Boolean): SimpleMatrix {
        val matrix = SimpleMatrix.identity(3)
        matrix.set(0, 0, Math.cos(Math.toRadians(angle)))
        matrix.set(1, 1, Math.cos(Math.toRadians(angle)))
        if (clockwise) {
            matrix.set(0, 1, Math.sin(Math.toRadians(angle)))
            matrix.set(1, 0, -Math.sin(Math.toRadians(angle)))
        } else {
            matrix.set(0, 1, -Math.sin(Math.toRadians(angle)))
            matrix.set(1, 0, Math.sin(Math.toRadians(angle)))
        }
        return matrix
    }

    private fun getTransitionMatrix(x: Double, y: Double): SimpleMatrix {
        val matrix = SimpleMatrix.identity(3)
        matrix.set(2, 0, x)
        matrix.set(2, 1, y)
        return matrix
    }

    private fun getScalingMatrix(x: Double, y: Double): SimpleMatrix {
        val matrix = SimpleMatrix.identity(3)
        matrix.set(0, 0, x)
        matrix.set(1, 1, y)
        return matrix
    }
}