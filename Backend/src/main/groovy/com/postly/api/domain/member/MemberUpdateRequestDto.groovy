package com.postly.api.domain.member

import groovy.transform.ImmutableOptions
import org.springframework.web.multipart.MultipartFile

@ImmutableOptions(knownImmutableClasses = [Optional<String>, Optional<MultipartFile>])
record MemberUpdateRequestDto(UUID id, Optional<String> username, Optional<MultipartFile> picture) {
}
