package org.allesoft.kafka.algs

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.processor.ProcessorSupplier
import java.util.*


/*
    Author: Kirill Abramovich
*/

class KafkaRunner {
    fun doRunStream(topology: Topology, processorName: String) {
        val streamingConfig = createStreamingConfig(processorName)
        val streaming = KafkaStreams(topology, streamingConfig)
        streaming.start()
    }

    private fun createStreamingConfig(processorName: String): Properties {
        val streamingConfig = Properties()
        streamingConfig[StreamsConfig.APPLICATION_ID_CONFIG] = processorName
        streamingConfig[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        streamingConfig[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        streamingConfig[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        return streamingConfig
    }

    private fun createCalculationTopology(): Topology {
        val topology = Topology()
        val sourceChain = "CalculationChain"
        topology.addSource(sourceChain, "mapView")
        val calculationProcessorName = "CalculationProcessor"
        topology.addProcessor(calculationProcessorName, ProcessorSupplier<String, String> { MapCalculationProcessor() },
                sourceChain)
        topology.addSink("ResultSink", "mapCalculation", calculationProcessorName)
        return topology
    }

    private fun createMapGenerationTopology(): Topology {
        val topology = Topology()
        val sourceName = "ProcessorChain"
        topology.addSource(sourceName, "mapGenerator")
        val processorName = "MainProcessor"
        topology.addProcessor(processorName, ProcessorSupplier<String, String> { MapGenerationProcessor() },
                sourceName)
        topology.addSink("MapsSink", "mapView", processorName)
        return topology
    }

    fun runStream() {
        doRunStream(createMapGenerationTopology(), "mapGen")
        doRunStream(createCalculationTopology(), "mapCalc")
    }
}