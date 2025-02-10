package com.postly.api.domain.member

import groovy.transform.ImmutableOptions
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.web.multipart.MultipartFile

@ImmutableOptions(knownImmutableClasses = [MultipartFile])
record RegisterRequestDto(@Min(3) @Max(20) String username, @Min(3) String password, MultipartFile picture) {
}
