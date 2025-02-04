package com.postly.api.repository

import com.postly.api.domain.topic.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface TopicRepository extends JpaRepository<Topic, UUID> {
}
