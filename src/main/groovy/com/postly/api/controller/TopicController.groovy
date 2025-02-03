package com.postly.api.controller

import com.postly.api.domain.topic.TopicRequestDto
import com.postly.api.domain.topic.TopicResponseDto
import com.postly.api.service.TopicService
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/topic")
class TopicController {
    @Autowired
    private TopicService topicService

    @Value("\${postly.topic.files.max}")
    private int fileMax

    @GetMapping
    ResponseEntity<List<TopicResponseDto>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "page", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(this.topicService.getAll(page, size))
    }

    @GetMapping("/{id}")
    ResponseEntity<TopicResponseDto> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.topicService.getById(UUID.fromString(id)))
    }

    @PostMapping(consumes = "multipart/form-data")
    ResponseEntity<TopicResponseDto> create(
            @RequestParam("member_id") String member_id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("files") List<MultipartFile> files
    ) {
        if (files.size() > fileMax) {
            throw new BadRequestException("Limit of ${fileMax} reached")
        }

        TopicRequestDto data = new TopicRequestDto(
                member_id: UUID.fromString(member_id),
                title: title,
                content: content,
                media: files
        )
        return ResponseEntity.ok(this.topicService.create(data))
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    ResponseEntity<TopicResponseDto> update(
            @PathVariable("id") Optional<String> id,
            @RequestParam("member_id") Optional<String> member_id,
            @RequestParam("title") Optional<String> title,
            @RequestParam("content") Optional<String> content,
            @RequestParam("files") Optional<List<MultipartFile>> files
    ) {
        if (files.isPresent() and files.get().size() > fileMax) {
            throw new BadRequestException("Limit of ${fileMax} reached")
        }

        return "Not implemented"
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") String id) {
        this.topicService.delete(UUID.fromString(id))
    }
}
