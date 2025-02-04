package com.postly.api.service

import com.postly.api.domain.topic.Topic
import com.postly.api.domain.topic.TopicMedia
import com.postly.api.domain.topic.TopicMediaResponseDto
import com.postly.api.repository.TopicMediaRepository
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class TopicMediaService {
    @Autowired
    private MediaService mediaService

    @Autowired
    private TopicMediaRepository topicMediaRepository

    List<TopicMediaResponseDto> getByTopicId(UUID id, int page = 0, int size = 10) {
        Pageable pageable = PageRequest.of(page, size)
        Page<TopicMedia> topicMediaPage = this.topicMediaRepository.findByTopicId(id, pageable)
        return topicMediaPage.map(topicMedia -> new TopicMediaResponseDto(topicMedia.id, topicMedia.url))
                .stream().toList()
    }

    void addMediaToTopic(MultipartFile file, Topic topic) {
        String mediaUrl = this.mediaService.uploadFile(file)

        if (mediaUrl == null) {
            throw new Error("Could not upload files to S3")
        }

        TopicMedia newTopicMedia = new TopicMedia()
        newTopicMedia.setTopic(topic)
        newTopicMedia.setUrl(mediaUrl)

        this.topicMediaRepository.save(newTopicMedia)
    }

    void removeMedia(UUID id) {
        TopicMedia media = this.topicMediaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Media does not exist"))

        this.mediaService.deleteFile(media.getUrl())

        this.topicMediaRepository.deleteById(media.getId())
    }
}
