package com.postly.api.controller

import com.postly.api.domain.topic.TopicMediaResponseDto
import com.postly.api.service.TopicMediaService
import com.postly.api.service.TopicService
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/topic/media")
class TopicMediaController {
    @Autowired
    private TopicService topicService

    @Autowired
    private TopicMediaService topicMediaService

    @Value("\${postly.topic.files.max}")
    private int fileMax

    @GetMapping("/{topic_id}")
    ResponseEntity<List<TopicMediaResponseDto>> getMedia(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable("topic_id") String topicId
    ) {
        return ResponseEntity.ok(this.topicMediaService.getByTopicId(UUID.fromString(topicId), page, size))
    }

    @PostMapping("/{topic_id}")
    void addMediaToTopic(
            @PathVariable("topic_id") String topicId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        int numFiles = this.topicMediaService
                .getByTopicId(UUID.fromString(topicId)).size()

        if (numFiles + files.size() > fileMax || files.size() > fileMax) {
            throw new BadRequestException("Limit of 3 files reached")
        }

        files.collect { file ->
            this.topicMediaService.addMediaToTopic(file, this.topicService._getById(UUID.fromString(topicId))
                    .orElseThrow(() -> new BadRequestException("Topic does not exist")))
            return file.getOriginalFilename()
        }
    }

    @DeleteMapping("/{id}")
    void deleteMedia(@PathVariable("id") String id) {
        this.topicMediaService.removeMedia(UUID.fromString(id))
    }
}
