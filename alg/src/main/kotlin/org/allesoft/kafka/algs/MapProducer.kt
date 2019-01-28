package org.allesoft.kafka.algs

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.streams.StreamsConfig
import java.util.*


/*
    Author: Kirill Abramovich
*/

class MapProducer {
    private fun createProducer(): Producer<String, String> {
        val props = Properties()
        props[StreamsConfig.APPLICATION_ID_CONFIG] = "mapCreator"
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "kafka:9092"
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = StringSerializer::class.java.canonicalName
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = StringSerializer::class.java.canonicalName
        props["key.serializer"] = StringSerializer::class.java.canonicalName
        props["value.serializer"] = StringSerializer::class.java.canonicalName
        return KafkaProducer<String, String>(props)
    }

    fun send(width: Int, height: Int) {
        createProducer().send(ProducerRecord("mapGenerator", "" +
            width.toString() + " " + height.toString()))
    }
}
