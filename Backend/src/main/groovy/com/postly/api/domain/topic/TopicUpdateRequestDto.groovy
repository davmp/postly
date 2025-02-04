package com.postly.api.domain.topic

import groovy.transform.ImmutableOptions
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

@ImmutableOptions(knownImmutableClasses = [Optional<String>, Optional<Date>])
record TopicUpdateRequestDto(UUID id,
                             @Min(3) @Max(100) @NotBlank Optional<String> title,
                             @Min(10) @Max(500) @NotBlank Optional<String> content) {
}
