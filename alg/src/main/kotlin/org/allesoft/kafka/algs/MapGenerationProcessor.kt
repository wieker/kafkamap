package org.allesoft.kafka.algs

import org.apache.kafka.streams.processor.AbstractProcessor
import java.lang.StringBuilder
import java.util.*

class MapGenerationProcessor : AbstractProcessor<String, String>() {
    override fun process(key: String?, value: String?) {
        val parameters = value?.split(" ")
        if (parameters != null && parameters.size > 1) {
            val width = parameters.get(0).toString().toInt()
            val height = parameters.get(1).toString().toInt()
            val array = createRandomClouds(height, width)
            convertArrayToString(array, key)
        } else {
            context().forward(key, "aaa " + parameters?.size + " " + parameters?.get(0))
        }
        context().commit()
    }

    private fun createRandomClouds(height: Int, width: Int): Array<IntArray> {
        val array = Array(height, { IntArray(width) })
        for (i in (0..(0..10).random())) {
            var rndsX: Int = (0..(height - 1)).random()
            var rndsY = (0..(width - 1)).random()
            array[rndsX][rndsY] = 1;
            for (j in (0..(0..10).random())) {
                if (0 == (0..1).random()) {
                    rndsX += (-1..1).random()
                } else {
                    rndsY += (-1..1).random()
                }
                if (rndsX == array.size || rndsY == array.size ||
                        rndsX == -1 || rndsY == -1) {
                    break
                }
                array[rndsX][rndsY] = 1;
            }
        }
        return array
    }

    private fun convertArrayToString(array: Array<IntArray>, key: String?) {
        val builder = StringBuilder()
        for (line in array) {
            for (element in line) {
                builder.append(element)
            }
            builder.append("#")
        }
        context().forward(key, builder.toString())
    }
}
