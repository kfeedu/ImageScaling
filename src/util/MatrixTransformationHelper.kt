package util

import org.ejml.simple.SimpleMatrix

class MatrixTransformationHelper private constructor() {

    private object Holder { val INSTANCE = MatrixTransformationHelper()}

    companion object {
        val instance: MatrixTransformationHelper by lazy { Holder.INSTANCE }
    }

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
        matrix.set(1, 1, Math.cos(angle))
        matrix.set(2, 2, Math.cos(angle))
        if(clockwise){
            matrix.set(1, 2, -Math.sin(angle))
            matrix.set(2, 1, Math.sin(angle))
        }else{
            matrix.set(1, 2, Math.sin(angle))
            matrix.set(2, 1, -Math.sin(angle))
        }
        return matrix
    }

    private fun getTransitionMatrix(x: Double, y: Double): SimpleMatrix {
        val matrix = SimpleMatrix.identity(3)
        matrix.set(3,1, x)
        matrix.set(3,2, y)
        return matrix
    }

    private fun getScalingMatrix(x: Double, y: Double): SimpleMatrix {
        val matrix = SimpleMatrix.identity(3)
        matrix.set(1, 1, x)
        matrix.set(2, 2, y)
        return matrix
    }
}