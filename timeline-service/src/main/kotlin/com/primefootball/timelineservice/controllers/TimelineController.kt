package com.primefootball.timelineservice.controllers

import com.primefootball.timelineservice.models.Post
import com.primefootball.timelineservice.models.Timeline
import com.primefootball.timelineservice.services.TimelineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/timeline")
class TimelineController(private val timelineService: TimelineService) {

    @GetMapping("/{requesterId}")
    fun getTimeline(@PathVariable requesterId: String): ResponseEntity<Timeline>{
        return ResponseEntity.ok(timelineService.getTimeline(requesterId))
    }

    @GetMapping("/{requesterId}/latest")
    fun getLatestTimeline(@PathVariable requesterId: String): ResponseEntity<Timeline>{
        return ResponseEntity.ok(timelineService.getLatestTimeline(requesterId))
    }
}
