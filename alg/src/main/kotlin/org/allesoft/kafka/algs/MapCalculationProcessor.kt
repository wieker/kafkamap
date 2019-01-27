package org.allesoft.kafka.algs

import org.apache.kafka.streams.processor.AbstractProcessor
import java.lang.StringBuilder

class MapCalculationProcessor : AbstractProcessor<String, String>() {
    override fun process(key: String?, value: String?) {
        val lines = value?.split("#")
        val height = lines?.size
        if (lines != null && height != null && height > 1) {
            val width = lines[0].length
            val array = convertStringToArray(height, width, lines)
            var result = calculateClouds(height, width, array)
            val builder = StringBuilder()
            builder.append(result)
            context().forward(key, builder.toString())
        } else {
            context().forward(key, "aaa " + lines?.size + " " + lines?.get(0))
        }
        context().commit()
    }

    private fun calculateClouds(height: Int, width: Int, array: Array<IntArray>): Int {
        var result = 0;
        for (i in (0..(height - 1))) {
            for (j in (0..(width - 1))) {
                if (array[i][j] == 1) {
                    deepSearch(array, i, j, height, width)
                    result++
                }
            }
        }
        return result
    }

    private fun convertStringToArray(height: Int, width: Int, parameters: List<String>): Array<IntArray> {
        val array = Array(height, { IntArray(width) })
        var i = 0;
        for (parameter in parameters) {
            for (j in (0..(parameter.length - 1))) {
                if (parameter[j] == '1') {
                    array[i][j] = 1;
                }
            }
            i++
        }
        return array
    }

    fun deepSearch(matrix: Array<IntArray>, h: Int, w: Int, height: Int, width: Int) {
        matrix[h][w] = 2;
        if (h > 0 && matrix[h-1][w] == 1) {
            deepSearch(matrix, h-1, w, height, width)
        }
        if (w > 0 && matrix[h][w-1] == 1) {
            deepSearch(matrix, h, w-1, height, width)
        }
        if (h < height - 1 && matrix[h+1][w] == 1) {
            deepSearch(matrix, h+1, w, height, width)
        }
        if (w < width - 1 && matrix[h][w+1] == 1) {
            deepSearch(matrix, h, w+1, height, width)
        }
    }
}
