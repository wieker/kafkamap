package org.allesoft.kafka.algs

import java.lang.StringBuilder

class MapCalculationProcessor : AbstractMapProcessor() {
    override fun process(key: String?, value: String?) {
       convertStringToArray(value)?.let {
           val height = it.size
           val width = it[0].size
           val partitionSize = 10
           if (height > partitionSize) {
               val array = ArrayList<IntArray>()
               for (line in it) {
                   array.add(line)
                   if (array.size == partitionSize) {
                       array.clear()
                   }
               }
           }
           var result = calculateClouds(height, width, it)
            context().forward(key, result.toString())
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
