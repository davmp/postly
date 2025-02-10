package com.postly.api.service

import com.postly.api.domain.member.Member
import com.postly.api.domain.topic.Topic
import com.postly.api.domain.topic.TopicRequestDto
import com.postly.api.domain.topic.TopicResponseDto
import com.postly.api.domain.topic.TopicUpdateRequestDto
import com.postly.api.repository.TopicRepository
import jakarta.validation.Valid
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicService {
    @Autowired
    private MemberService memberService

    @Autowired
    private TopicMediaService topicMediaService

    @Autowired
    private TopicRepository topicRepository

    List<TopicResponseDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size)
        Page<Topic> topicPage = this.topicRepository.findAll(pageable)
        return topicPage.map(topic -> new TopicResponseDto(topic.getId(), topic.getTitle(), topic.getContent(), topic.getMember().getId(), topic.getCreatedAt()))
                .stream().toList()
    }

    Optional<Topic> _getById(UUID id) {
        return this.topicRepository.findById(id)
    }

    TopicResponseDto getById(UUID id) {
        Topic topic = this.topicRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Topic does not exist"))
        return new TopicResponseDto(
                topic.getId(), topic.getTitle(), topic.getContent(), topic.getMember().getId(), topic.getCreatedAt()
        )
    }

    TopicResponseDto create(@Valid TopicRequestDto data) {
        Member member = this.memberService.loadUserByUsername(data.username())

        Topic newTopic = new Topic()
        newTopic.setTitle(data.title().strip())
        newTopic.setContent(data.content().strip())
        newTopic.setUpdatedAt(new Date())
        newTopic.setCreatedAt(new Date())
        newTopic.setMember(member)

        this.topicRepository.save(newTopic)

        data.media().forEach {
            media ->
                {
                    this.topicMediaService.addMediaToTopic(media, newTopic)
                }
        }

        return new TopicResponseDto(newTopic.getId(), newTopic.getTitle(), newTopic.getContent(), newTopic.getMember().getId(), newTopic.getCreatedAt())
    }

    TopicResponseDto update(@Valid TopicUpdateRequestDto data) {
        Topic topic = this._getById(data.id())
                .orElseThrow(() -> new BadRequestException("Topic does not exist"))

        if (data.title().isPresent()) {
            topic.setTitle(data.title().get().strip())
        }

        if (data.content().isPresent()) {
            topic.setContent(data.content().get().strip())
        }

        topic.setUpdatedAt(new Date())

        this.topicRepository.save(topic)
        return new TopicResponseDto(
                id: topic.getId(),
                title: topic.getTitle(),
                content: topic.getContent(),
                member_id: topic.getMember().getId(),
                created_at: topic.getCreatedAt()
        )
    }

    void delete(UUID id) {
        Topic topic = this._getById(id)
                .orElseThrow(() -> new BadRequestException("Topic does not exist"))

        this.topicMediaService.getByTopicId(id).forEach { topicMedia ->
            {
                this.topicMediaService.removeMedia(topicMedia.id())
            }
        }

        this.topicRepository.delete(topic)
    }
}
