package com.postly.api.domain.topic

record TopicResponseDto(UUID id, String title, String content, UUID member_id, Date created_at) {

}