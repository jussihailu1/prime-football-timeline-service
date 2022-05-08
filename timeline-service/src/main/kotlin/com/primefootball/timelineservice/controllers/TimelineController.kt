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

    @GetMapping("/test")
    fun test(){
        timelineService.requestTimeline("1")
    }

    @GetMapping("/{requesterId}")
    fun getTimeline(@PathVariable requesterId: String): ResponseEntity<Timeline>{
        return ResponseEntity<Timeline>(timelineService.getTimeline(requesterId), HttpStatus.OK)
    }
}
