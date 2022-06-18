package com.primefootball.timelineservice.controllers

import com.primefootball.timelineservice.services.TimelineService
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin
@RestController
@RequestMapping("/hello-timeline")
class RootController() {
    @GetMapping()
    fun root(): String = "Prime Football Timeline-service"
}
