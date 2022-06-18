package com.primefootball.timelineservice.controllers

import com.primefootball.timelineservice.models.Timeline
import com.primefootball.timelineservice.services.TimelineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin
@RestController
@RequestMapping("/timeline")
class TimelineController(private val timelineService: TimelineService) {

    @GetMapping("/{requesterId}")
    fun getTimeline(@PathVariable requesterId: UUID): ResponseEntity<Timeline> =
        ResponseEntity.ok(timelineService.getTimeline(requesterId))

    @GetMapping("/{requesterId}/latest")
    fun getLatestTimeline(@PathVariable requesterId: UUID): ResponseEntity<Timeline> =
        ResponseEntity.ok(timelineService.getLatestTimeline(requesterId))
}
