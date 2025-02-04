package com.postly.api.domain.topic

import groovy.transform.ImmutableOptions
import org.springframework.web.multipart.MultipartFile

@ImmutableOptions(knownImmutableClasses = [MultipartFile])
record TopicRequestDto(UUID member_id, String title, String content, List<MultipartFile> media) {
}
