package com.primefootball.timelineservice.repositories

import com.primefootball.timelineservice.models.Timeline
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface TimelineRepository : CrudRepository<Timeline, UUID>
