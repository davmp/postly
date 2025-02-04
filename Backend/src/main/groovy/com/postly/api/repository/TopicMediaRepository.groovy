package com.postly.api.repository


import com.postly.api.domain.topic.TopicMedia
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.NativeQuery

interface TopicMediaRepository extends JpaRepository<TopicMedia, UUID> {
    @NativeQuery(
            value = "select * from public.topic_media where topic_id = ?1",
            countQuery = "select count(*) from public.topic_media where topic_id = ?1")
    Page<TopicMedia> findByTopicId(UUID topicId, Pageable pageable)
}