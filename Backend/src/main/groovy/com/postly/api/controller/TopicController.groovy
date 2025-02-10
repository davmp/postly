package com.postly.api.controller

import com.postly.api.domain.member.Member
import com.postly.api.domain.topic.TopicRequestDto
import com.postly.api.domain.topic.TopicResponseDto
import com.postly.api.domain.topic.TopicUpdateRequestDto
import com.postly.api.service.TopicService
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
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
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("files") List<MultipartFile> files
    ) {
        Member member = (Member) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()

        if (title.trim().size() < 5 || title.trim().size() > 20) {
            throw new BadRequestException("Title must be between 5 and 100 characters long")
        }
        if (files.size() > fileMax) {
            throw new BadRequestException("Limit of ${fileMax} reached")
        }

        return ResponseEntity.ok(this.topicService.create(
                new TopicRequestDto(
                        username: member.getUsername(),
                        title: title,
                        content: content,
                        media: files
                )))
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    ResponseEntity<TopicResponseDto> update(
            @PathVariable("id") String id,
            @RequestParam("title") Optional<String> title,
            @RequestParam("content") Optional<String> content,
            @RequestParam("files") Optional<List<MultipartFile>> files
    ) {
        if (title.isPresent() && (title.get().trim().size() < 5 || title.get().trim().size() > 20)) {
            throw new BadRequestException("Title must be between 5 and 100 characters long")
        }

        if (files.isPresent() && files.get().size() > fileMax) {
            throw new BadRequestException("Limit of ${fileMax} reached")
        }

        return ResponseEntity.ok(this.topicService.update(new TopicUpdateRequestDto(
                id: UUID.fromString(id),
                title: title,
                content: content
        )))
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") String id) {
        this.topicService.delete(UUID.fromString(id))
    }
}
