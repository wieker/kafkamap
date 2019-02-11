package org.allesoft.kafka.algs

import org.apache.kafka.streams.processor.AbstractProcessor
import java.lang.StringBuilder


/*
    Author: Kirill Abramovich
*/

abstract class AbstractMapProcessor : AbstractProcessor<String, String>() {
    protected fun convertStringToArray(value: String?): Array<IntArray>? {
        if (value == null) {
            return null;
        }
        val lines = value.split("#")
        val height = lines.size
        if (height < 1) {
            return null
        }
        val width = lines[0].length

        val array = Array(height, { IntArray(width) })
        var i = 0;
        for (line in lines) {
            for (j in (0..(line.length - 1))) {
                if (line[j] == '1') {
                    array[i][j] = 1;
                }
            }
            i++
        }
        return array
    }

    protected fun convertArrayToString(array: Array<IntArray>, key: String?) {
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