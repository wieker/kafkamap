package org.allesoft.kafka.controller

import org.allesoft.kafka.algs.KafkaRunner
import org.allesoft.kafka.algs.MapProducer
import org.allesoft.kafka.dto.MapCreationResponse
import org.springframework.web.bind.annotation.*


/*
    Author: Kirill Abramovich
*/

@RestController
class KafkaController {

    @PostMapping("/createMap")
    fun createMap(@RequestParam width: Int, @RequestParam height: Int): MapCreationResponse {
        MapProducer().send(width, height)
        return MapCreationResponse("OK")
    }

    @PostMapping("/configure")
    fun configure(): MapCreationResponse {
        val kafkaRunner = KafkaRunner()
        kafkaRunner.runStream()
        return MapCreationResponse("OK")
    }
}
