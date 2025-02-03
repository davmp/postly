package com.postly.api.domain.member

import groovy.transform.ImmutableOptions
import org.springframework.web.multipart.MultipartFile

@ImmutableOptions(knownImmutableClasses = [MultipartFile])
record MemberRequestDto(String username, MultipartFile picture) {
}
